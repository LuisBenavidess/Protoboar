package com.example.protoboar;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Click {
    private static Pane pane;
    private conection linea;
    private final bus[][] alimentacion;
    private boolean ledClicked;
    private boolean cableClicked;
    private static boolean eliminarProximaImagen = false;
    private bus primercircle;
    private static ArrayList<conection> cables;
    private static ArrayList<conection> cables_led;
    private final Bateria bateria;

    public Click(Pane pane, bus[][] alimentacion, boolean ledClicked, boolean cableClicked, Bateria bateria) {
        Click.pane = pane;
        this.alimentacion = alimentacion;
        this.ledClicked = ledClicked;
        this.cableClicked = cableClicked;
        this.bateria = bateria;
        cables = new ArrayList<>();
        cables_led = new ArrayList<>();
    }

    public ArrayList<conection> getCables() {
        return cables;
    }

    public ArrayList<conection> getCables_led() {
        return cables_led;
    }

    public void setLedClicked(boolean ledClicked) {
        this.ledClicked = ledClicked;
    }

    public void setCableClicked(boolean cableClicked) {
        this.cableClicked = cableClicked;
    }

    // Manejar la acción cuando se presiona un círculo
    public void presionarCirculo(MouseEvent event) {
        if (event.getSource() instanceof bus circulo) {
            circulo.getCenterX();
            circulo.getCenterY();
            circulo.getCenterX();
            circulo.getCenterY();
            if (primercircle == null && ledClicked) {
                primercircle = circulo; // Asignación del primer círculo
                System.out.println("Primer círculo asignado");
            } else if (ledClicked) {
                System.out.println("Segundo círculo");
                crearCableEntreCirculos(primercircle, circulo);
                primercircle = null; // Reiniciar para permitir la selección de nuevos círculos
                System.out.println("Primer círculo reiniciado");
            } else if (cableClicked) {
                inicio(event);
            }
        }
    }

    private void crearCableEntreCirculos(bus c1, bus c2) {
        if (c1 == null || c2 == null) {
            System.out.println("uno de los círculos es null.");
            return;
        }
        conection cable = new conection(c1.getCenterX(), c1.getCenterY(), c2.getCenterX(), c2.getCenterY());
        cable.setInicio(c1);
        cable.setFin(c2);

        cable.setStroke(Color.BLUE);
        cable.setStrokeWidth(5);
        cables_led.add(cable);
        pane.getChildren().add(cable);
        // Calcular el punto medio de la línea
        double midX = (c1.getCenterX() + c2.getCenterX()) / 2;
        double midY = (c1.getCenterY() + c2.getCenterY()) / 2;
        // Colocar la imagen del LED en el punto medio
        Led led = new Led(pane, midX, midY);
        led.setConectado();
        cable.set_foto(led);
        cables_led.add(cable);
        primercircle = null;
    }

    // Manejar el inicio de un arrastre
    public void inicio(MouseEvent event) {
        bus circulo_apret = (bus) event.getSource();
        linea = new conection(circulo_apret.getCenterX(), circulo_apret.getCenterY(),
                circulo_apret.getCenterX(), circulo_apret.getCenterY());
        linea.setStroke(Color.GREEN);
        linea.setStrokeWidth(5);

        linea.setInicio(circulo_apret);

        ((Pane) circulo_apret.getParent()).getChildren().add(linea);
        //movimiento de la línea
        circulo_apret.getParent().setOnMouseMoved(this::movimiento);
    }

    // Manejar el movimiento del ratón para actualizar la línea
    void movimiento(MouseEvent event) {
        if (linea != null) {
            linea.setEndX(event.getX());
            linea.setEndY(event.getY());
            linea.getParent().setOnMouseClicked(this::parar);
        }
    }

    // Detener el dibujo y finalizar el arrastre
    void parar(MouseEvent event) {
        if (linea != null) {
            for (int i = 0; i < 14; i++) {
                for (int j = 0; j < 30; j++) {
                    bus targetCircle = alimentacion[i][j];
                    if (targetCircle != null) {
                        double dx = event.getX() - targetCircle.getCenterX();
                        double dy = event.getY() - targetCircle.getCenterY();
                        double distance = Math.sqrt(dx * dx + dy * dy);
                        if (distance <= targetCircle.getRadius()) {
                            // Detener el dibujo
                            linea.setFin(targetCircle);
                            conection nuevo = linea;
                            nuevo.setOnMouseClicked(Click::eliminarElemento);
                            cables.add(nuevo);
                            linea = null;
                            targetCircle.getParent().setOnMouseMoved(null);
                            targetCircle.getParent().setOnMouseClicked(null);
                            return; // Salir del método una vez que se haya encontrado un círculo
                        }
                    }
                }
            }
            // Verificar los círculos de la batería (positivo y negativo)
            if (bateria != null) {
                verificarCírculoBateria(event, bateria.getPositivo());
                verificarCírculoBateria(event, bateria.getNegativo());
            }
            System.out.println("No entra");
        } else if (event.getSource() instanceof bus) {
            event.getSource();
            conection nuevo = linea;
            nuevo.setOnMouseClicked(Click::eliminarElemento);
            cables.add(nuevo);
            linea = null;
        }
    }


    private void verificarCírculoBateria(MouseEvent event, bus circuloBateria) {
        double dx = event.getX() - circuloBateria.getCenterX();
        double dy = event.getY() - circuloBateria.getCenterY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        if (distance <= circuloBateria.getRadius()) {
            // Detener el dibujo en el círculo de la batería
            linea.setFin(circuloBateria);
            conection nuevo = linea;
            nuevo.setOnMouseClicked(Click::eliminarElemento);
            cables.add(nuevo);
            linea = null;
            circuloBateria.getParent().setOnMouseMoved(null);
            circuloBateria.getParent().setOnMouseClicked(null);
        }
    }


    // Método para activar el modo de eliminación al hacer clic en el basurero
    public void manejarClickEnBasurero() {
        System.out.println("Modo borrar");
        eliminarProximaImagen = true;
    }

    // Método para eliminar un elemento del pane
    public static void eliminarElemento(MouseEvent event) {
        if (eliminarProximaImagen) {
            Object source = event.getSource();
            pane.getChildren().remove(source);
            eliminarProximaImagen = false;
            System.out.println("Se eliminó algo");
            int i=0;
            while(i<cables.size()){
                if(source.equals(cables.get(i))){
                    cables.remove(i);
                }
                i++;
            }
        }

    }

    public void revovinar(){
        int i=2;
        while(i<14){
            int j=0;
            while(j<30){
                alimentacion[i][j].setCarga(" ");
                alimentacion[i][j].setFill(Color.BLACK);
                j++;
            }
            i++;
        }
    }

    public void corriente() {
        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 30; j++) {
                if (alimentacion[i][j] != null) {
                    if (alimentacion[i][j].getCarga().equals("+")) {
                        marcar(i, j, "+");
                    } else if (alimentacion[i][j].getCarga().equals("-")) {
                        marcar(i, j, "-");
                    }
                }
            }
        }
    }


    public void marcar(int i, int j, String carga) {
        if (i == 0 || i == 12) {
            if (carga.equals("-")) {
                for (int col = 0; col < 30; col++) {
                    if (alimentacion[i][col] != null) {
                        alimentacion[i][col].setFill(Color.BLUE);
                        alimentacion[i][col].setCarga("-");
                    }
                }
            } else {
                // Si la carga no es negativa, no se aplica a las filas 0 y 13
                for (int col = 0; col < 30; col++) {
                    if (alimentacion[i][col] != null) {
                        alimentacion[i][col].setFill(Color.BLACK);
                        alimentacion[i][col].setCarga(" ");
                    }
                }
            }
        } else if (i == 1 || i == 13) {
            if (carga.equals("+")) {
                for (int col = 0; col < 30; col++) {
                    if (alimentacion[i][col] != null) {
                        alimentacion[i][col].setFill(Color.RED);
                        alimentacion[i][col].setCarga("+");
                    }
                }
            } else {
                // Si la carga no es positiva, no se aplica a las filas 1 y 14
                for (int col = 0; col < 30; col++) {
                    if (alimentacion[i][col] != null) {
                        alimentacion[i][col].setFill(Color.BLACK);
                        alimentacion[i][col].setCarga(" ");
                    }
                }
            }
        } else {
            // Para otras filas, aplicar color basado en la carga como antes
            if (i >= 2 && i <= 6) {
                int fil = 2;
                while (fil <= 6) {
                    if (alimentacion[fil][j] != null) {
                        if (carga.equals("+")) {
                            alimentacion[fil][j].setFill(Color.RED);
                            alimentacion[fil][j].setCarga("+");
                        } else if (carga.equals("-")) {
                            alimentacion[fil][j].setFill(Color.BLUE);
                            alimentacion[fil][j].setCarga("-");
                        }
                    }
                    fil++;
                }
            } else if (i >= 7 && i <= 11) {
                int fil = 7;
                while (fil <= 11) {
                    if (alimentacion[fil][j] != null) {
                        if (carga.equals("+")) {
                            alimentacion[fil][j].setFill(Color.RED);
                            alimentacion[fil][j].setCarga("+");
                        } else if (carga.equals("-")) {
                            alimentacion[fil][j].setFill(Color.BLUE);
                            alimentacion[fil][j].setCarga("-");
                        }
                    }
                    fil++;
                }
            }
        }
    }





    public void verificar_cables() {
        int i = 0;
        while (i < cables.size()) {
            conection lin = cables.get(i);
            String ini = lin.getInicio().getCarga();
            String fin = lin.getFin().getCarga();
            if (!ini.equals(" ")) {
                if (fin.equals(" ")) {
                    lin.getFin().setCarga(ini);
                    corriente();
                } else if (!fin.equals(ini)) {
                    System.out.println("Exploto");
                }
            } else {
                if (!fin.equals(" ")) {
                    lin.getInicio().setCarga(fin);
                    corriente();
                }
            }
            i++;
        }
    }

    public void prender_led(){
        int i=0;
        while(i<cables_led.size()){
            if(cables_led.get(i).getInicio().getCarga().equals("+") && cables_led.get(i).getFin().getCarga().equals("-") ||
                    cables_led.get(i).getInicio().getCarga().equals("-") && cables_led.get(i).getFin().getCarga().equals("+")){
                cables_led.get(i).get_foto().prender();
            }else{
                cables_led.get(i).get_foto().apagar();
            }
            i++;
        }
    }
}
