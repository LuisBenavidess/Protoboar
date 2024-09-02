package com.example.protoboar;


import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import java.util.ArrayList;

public class Click {
    private static Pane pane;
    private conection linea;
    private double x;
    private double y;
    private boolean circuloClicked = false;
    private double circuloX, circuloY;
    private final Label label;
    private final ImageView led;
    public static bus[][] alimentacion;
    private boolean ledClicked;
    private boolean cableClicked;
    private boolean switchClicked;
    private static boolean cambiarSwitch;
    private static boolean eliminarProximaImagen = false;
    private bus primercircle;
    private static ArrayList<conection> cables;
    private static ArrayList<conection> cables_led;
    private static ArrayList<Switch> switches;

    public Click(Pane pane, Label label, ImageView led, bus[][] alimentacion, boolean ledClicked, boolean cableClicked, boolean switchClicked) {
        Click.pane = pane;
        this.label = label;
        this.led = led;
        this.alimentacion = alimentacion;
        this.ledClicked = ledClicked;
        this.cableClicked = cableClicked;
        this.switchClicked = false;
        cables = new ArrayList<>();
        cables_led = new ArrayList<>();
        switches = new ArrayList<Switch>();
    }

    public ArrayList<conection> getCables() {
        return cables;
    }

    public ArrayList<conection> getCables_led() {
        return cables_led;
    }

    public ArrayList<Switch> getSwitches() {
        return switches;
    }

    public void setLedClicked(boolean ledClicked) {
        this.ledClicked = ledClicked;
    }

    public void setCableClicked(boolean cableClicked) {
        this.cableClicked = cableClicked;
    }

    public void setSwitchClicked(boolean switchClicked){
        this.switchClicked = switchClicked;
    }

    // Manejar la acción cuando se presiona un círculo
    public void presionarCirculo(MouseEvent event) {
        bus circulo = (bus) event.getSource();
        circuloClicked = true;
        circuloX = circulo.getCenterX();
        circuloY = circulo.getCenterY() - 10;
        x = circulo.getCenterX();
        y = circulo.getCenterY();
        if (primercircle == null && ledClicked) {
            primercircle = circulo; // Asignación del primer círculo
            System.out.println("Primer círculo asignado");
        } else if (ledClicked && primercircle != null) {
            bus segundoCirculo = circulo;
            System.out.println("Segundo círculo");
            crearCableEntreCirculos(primercircle, segundoCirculo);
            primercircle = null; // Reiniciar para permitir la selección de nuevos círculos

        }  else if (primercircle == null && switchClicked) {
            primercircle = circulo; // Asignación del primer círculo
            System.out.println("Primer círculo asignado");
        } else if (primercircle != null && switchClicked) {
            bus segundoCirculo = circulo;
            System.out.println("Segundo círculo");
            crearSwitch(primercircle, segundoCirculo);
            primercircle = null; // Reiniciar para permitir la selección de nuevos círculos

        } else if (cableClicked) {
            inicio(event);

        }
    }

    //  Crea cables
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
        led.setConectado(true);
        cable.set_foto(led);
        cables_led.add(cable);
        primercircle = null;
    }

    // Manejar el inicio de un arrastre
    public void inicio(MouseEvent event) {
        bus circulo_apret = (bus) event.getSource();
        linea = (conection) new conection(circulo_apret.getCenterX(), circulo_apret.getCenterY(),
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
                            System.out.println("entra");
                            // Detener el dibujo
                            linea.setFin(targetCircle);
                            conection nuevo=linea;
                            nuevo.setOnMouseClicked(Click::eliminarElemento);
                            cables.add(nuevo);

                            linea=null;
                            ((Pane) targetCircle.getParent()).setOnMouseMoved(null);
                            ((Pane) targetCircle.getParent()).setOnMouseClicked(null);
                            return; // Salir del metodo una vez que se haya encontrado un círculo
                        }
                    }
                }
            }
            System.out.println("no entra");
        }
    }

    /// Metodo para activar el modo de eliminación al hacer clic en el basurero
    public void ClickEnBasurero(MouseEvent event) {
        System.out.println("Modo borrar");
        eliminarProximaImagen = true;
    }

    /// Metodo para eliminar un elemento del pane
    public static void eliminarElemento(MouseEvent event) {
        if (eliminarProximaImagen) {
            Object basura = event.getSource();
            pane.getChildren().remove(basura);
            eliminarProximaImagen = false;
            System.out.println("Se eliminó algo");
            int i=0;
            while(i<cables.size()){
                if(basura.equals(cables.get(i))){
                    cables.remove(i);
                }
                i++;
            }
            while(i<switches.size()){
                if(basura.equals(switches.get(i))){
                    switches.remove(i);
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
/*
    public void mostrarElemento() {
        int i=0;
        while (i < cables.size()) {
            bus ini=cables.get(i).getInicio();
            bus fin=cables.get(i).getFin();
            System.out.println("Primer bus");
            ini.get_ubicacion();
            System.out.println(ini.getCarga());
            System.out.println("Segundo bus");
            fin.get_ubicacion();
            System.out.println(fin.getCarga());
            i++;
        }
        System.out.println("fin");
    }*/

    public void corriente(){
        int i=0;
        while(i<14){
            int j=0;
            while(j<30){
                if (alimentacion[i][j].getCarga().equals("+")) {
                    marcar(i,j,"+");
                }else{
                    if (alimentacion[i][j].getCarga().equals("-")) {
                        marcar(i,j,"-");
                    }
                }
                j++;
            }
            i++;
        }
    }

    public boolean get_switch(int i, int j){
        int x=0;
        while(x<switches.size()){
            if(switches.get(x).getC1().fila==i && switches.get(x).getC1().columna==j){
                System.out.println("entra 1");
                if(switches.get(x).getCarga().equals("+")){
                    System.out.println("true");
                    return true;
                }else{
                    return false;
                }
            }
            if(switches.get(x).getC2().fila==i && switches.get(x).getC2().columna==j){
                System.out.println("entra 2");
                if(switches.get(x).getCarga().equals("+")){
                    return true;
                }else{
                    return false;
                }
            }
            x++;
        }
        return true;
    }

    public void marcar(int i, int j,String carga){
        boolean swi=true;
       if(i>=0 && i<=1 && swi){
           int col=0;
           while(col<30 && swi){
               if(carga.equals("+")){
                   swi=get_switch(i,col);
                   if(swi){
                       alimentacion[i][col].setFill(Color.RED);
                       alimentacion[i][col].setCarga("+");
                   }
               }else{
                   if(carga.equals("-")){
                       swi=get_switch(i,col);
                       if(swi){
                           alimentacion[i][col].setFill(Color.BLUE);
                           alimentacion[i][col].setCarga("-");
                       }
                   }
               }
               col++;
           }
       }else{
           if(i>=2 && i<=6 && swi){
               int fil=2;
               while(fil<=6 && swi){
                   if(carga.equals("+")){
                       swi=get_switch(fil,j);
                       System.out.println(swi);
                       if(swi){
                           alimentacion[fil][j].setFill(Color.RED);
                           alimentacion[fil][j].setCarga("+");
                       }

                   }else{
                       if(carga.equals("-")){
                           swi=get_switch(fil,j);
                           if(swi){
                               alimentacion[fil][j].setFill(Color.BLUE);
                               alimentacion[fil][j].setCarga("-");
                           }

                       }
                   }
                   fil++;
               }
           }else{
               if(i>=7 &&i<=11 && swi){
                   int fil=7;
                   while(fil<=11 && swi){
                       if(carga.equals("+")){
                           swi=get_switch(fil,j);
                           if(swi){
                               alimentacion[fil][j].setFill(Color.RED);
                               alimentacion[fil][j].setCarga("+");
                           }
                       }else{
                           if(carga.equals("-")){
                               swi=get_switch(fil,j);
                               if(swi){
                                   alimentacion[fil][j].setFill(Color.BLUE);
                                   alimentacion[fil][j].setCarga("-");
                               }

                           }/*else{
                               alimentacion[fil][j].setFill(Color.BLACK);
                           }*/
                       }
                       fil++;
                   }
               }else{
                   int col=0;
                   while(col<30 && swi){
                       if(carga.equals("+")){
                           swi=get_switch(i,col);
                           if(swi){
                               alimentacion[i][col].setFill(Color.RED);
                               alimentacion[i][col].setCarga("+");
                           }
                       }else{
                           if(carga.equals("-")){
                               swi=get_switch(i,col);
                               if(swi){
                                   alimentacion[i][col].setFill(Color.BLUE);
                                   alimentacion[i][col].setCarga("-");
                               }

                           }/*else{
                               alimentacion[i][col].setFill(Color.BLACK);
                           }*/
                       }
                       col++;
                   }
               }
           }
       }
    }

    public void verificar_cables(){
        int i=0;
        while(i<cables.size()){
            System.out.println("entra");
            conection lin=cables.get(i);
            String ini=lin.getInicio().getCarga();
            String fin=lin.getFin().getCarga();
            if(!ini.equals(" ")){
                if(fin.equals(" ")){
                   lin.getFin().setCarga(ini);
                   corriente();
                }else{
                    if(!fin.equals(ini)){

                        System.out.println("Exploto");
                    }
                }
            }else{
                if(!fin.equals(" ")){
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

    ////
    public void crearSwitch(bus c1, bus c2){
        if (c1 == null || c2 == null) {
            System.out.println("uno de los círculos es null.");
            return;
        }

        // Calcular la posición media entre los dos círculos
        double x = (c1.getCenterX() + c2.getCenterX()) / 2;
        double y = (c1.getCenterY() + c2.getCenterY()) / 2;

        // Crear el objeto Switch en la posición calculada
        Switch SW = new Switch(pane, x, y, c1, c2);
        setSwitchClicked(true);

        switches.add(SW);
        primercircle = null;
    }

    ////
    /*public static void quitarCarga(int F1, int C1, int F2, int C2){
        int fila1 = F1;
        int columna1 = C1;

        int fila2 = F2;
        int columna2 = C2;

        while(fila1 != 6 && fila1 != 12){
            alimentacion[fila1][columna1].setCarga(" ");
            alimentacion[fila1][columna1].setFill(Color.BLACK);
            fila1++;
        }
        fila1 = F1;
        while(fila1 != 1 && fila1 != 7){
            alimentacion[fila1][columna1].setCarga(" ");
            alimentacion[fila1][columna1].setFill(Color.BLACK);
            fila1--;
        }
        while(fila2 != 6 && fila2 != 12){
            System.out.println(fila2);
            alimentacion[fila2][columna2].setCarga(" ");
            alimentacion[fila2][columna2].setFill(Color.BLACK);
            fila2++;
        }
        fila2 = F2;
        while(fila2 != 1 && fila2 != 7){
            alimentacion[fila2][columna2].setCarga(" ");
            alimentacion[fila2][columna2].setFill(Color.BLACK);
            fila2--;
        }
    }*/

    public static void darCarga(int F1, int C1, int F2, int C2){
        int fila1 = F1;
        int columna1 = C1;

        int fila2 = F2;
        int columna2 = C2;

        while(fila1 != 7 && fila1 != 12){
            alimentacion[fila1][columna1].setCarga("-");
            alimentacion[fila1][columna1].setFill(Color.RED);
            fila1++;
        }
        fila1 = F1;
        while(fila1 != 1 && fila1 != 6){
            alimentacion[fila1][columna1].setCarga("-");
            alimentacion[fila1][columna1].setFill(Color.RED);
            fila1--;
        }

        while(fila2 != 6 && fila2 != 12){
            alimentacion[fila2][columna2].setCarga("+");
            alimentacion[fila2][columna2].setFill(Color.BLUE);
            fila2++;
        }
        fila2 = F2;
        while(fila2 != 1 && fila2 != 6){
            alimentacion[fila2][columna2].setCarga("+");
            alimentacion[fila2][columna2].setFill(Color.BLUE);
            fila2--;
        }
    }
}