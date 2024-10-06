package com.example.protoboar;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.layout.Pane;
import java.util.ArrayList;
//Clase se encarga de los circulos
public class ManejarCirculos {

    //Atributos
    private conection nuevoCable;
    private final Pane pane;
    private final ArrayList<Protoboard> protos;
    private conection linea;
    private boolean ledClicked;
    private boolean cableClicked;
    private boolean switchClicked;
    private bus primercircle;
    private boolean circulo_bateria;
    private final ArrayList<conection> cables;
    private final ArrayList<Led> leds;
    private final ArrayList<Switch> switches;
    private final Bateria bateria;
    private final ManejarCarga manejarCarga;

    //Constructor
    public ManejarCirculos(Pane pane, ArrayList<Protoboard> protos, boolean ledClicked, boolean cableClicked, Bateria bateria) {
        this.pane = pane;
        this.protos = protos;
        this.ledClicked = ledClicked;
        this.cableClicked = cableClicked;
        this.bateria = bateria;
        this.switchClicked = false;
        cables = new ArrayList<>();
        leds = new ArrayList<>();
        switches = new ArrayList<>();
        this.manejarCarga = new ManejarCarga(protos);
    }

    // get y set
    public ArrayList<conection> getCables() {
        return cables;
    }

    public ArrayList<Switch> getswitches() {
        System.out.println(switches.size());
        return switches;
    }

    public ArrayList<Led> get_leds() {
        return leds;
    }

    public void setLedClicked(boolean ledClicked) {
        this.ledClicked = ledClicked;
    }

    public void setCableClicked(boolean cableClicked) {
        this.cableClicked = cableClicked;
    }

    public void setSwitchClicked(boolean switchClicked){
        this.switchClicked = switchClicked;
        if(!switchClicked){
            primercircle=null;
        }
    }
    //////////////////////////////////////////////////

    //Metodos

    //Metodo que se encarga de el presionado de los circulos
    public void presionarCirculo(MouseEvent event) {
        bus circulo = (bus) event.getSource();
        //Condiciones para saber si este bus es para cable, led o switch
        if (primercircle == null && ledClicked) {
            primercircle = circulo; // Asignación del primer círculo
            System.out.println("Primer círculo asignado");
        } else if (ledClicked) {
            System.out.println("Segundo círculo");
            int i=0;
            while(i<protos.size()){
                if(protos.get(i).getChildren().contains(primercircle)){
                    crearCableEntreCirculos(primercircle, circulo,protos.get(i));
                }
                i++;
            }
            primercircle = null; // Reiniciar para permitir la selección de nuevos círculos
        } else if (primercircle == null && switchClicked) {
            primercircle = circulo; // Asignación del primer círculo
            System.out.println("Primer círculo asignado");
        } else if (primercircle != null && switchClicked) {
            System.out.println("Segundo círculo");
            int i=0;
            while(i<protos.size()){
                if(protos.get(i).getChildren().contains(primercircle)){
                    crearCableEntreCirculos(primercircle, circulo,protos.get(i));
                }
                i++;
            }
            primercircle = null; // Reiniciar para permitir la selección de nuevos círculos
        } else if (cableClicked) {
            inicio(event);
        }
        // Verificar si uno de los círculos es la batería
        if (cableClicked && (cableConectadoABateria(circulo) || cableConectadoABateria(primercircle))) {
            // Si se conecta a la batería, agregar el cable a la batería
            System.out.println("PASA");
            bateria.addCable(nuevoCable);
        }
    }

    private boolean cableConectadoABateria(bus circulo) {
        return circulo == bateria.getPositivo() || circulo == bateria.getNegativo();
    }

    // Método que genera cable para los led y switch
    private void crearCableEntreCirculos(bus c1, bus c2, Protoboard proto) {
        if (c1 == null || c2 == null) {
            return;
        }

        // Genera un cable de tipo conection con los dos círculos presionados
        conection cable = new conection(c1.getCenterX(), c1.getCenterY(), c2.getCenterX(), c2.getCenterY());
        // Agregar función para borrar al presionar si el modo borrar está activo
        cable.setOnMouseClicked(Click::eliminarElemento);
        cable.setInicio(c1); // positivo
        cable.setFin(c2); // negativo

        if (cableConectadoABateria(c1) || cableConectadoABateria(c2)) {
            bateria.addCable(cable);
        }

        if (ledClicked) {
            cable.setStroke(Color.RED);
            cable.setStrokeWidth(5);
            proto.getChildren().add(cable);

            // Calcular el punto medio de la línea
            double midX = (c1.getCenterX() + c2.getCenterX()) / 2;
            double midY = (c1.getCenterY() + c2.getCenterY()) / 2;

            // Crear una segunda línea azul para la segunda mitad del cable
            conection cableAzul = new conection(midX, midY, c2.getCenterX(), c2.getCenterY());
            cableAzul.setStroke(Color.BLUE);
            cableAzul.setStrokeWidth(5);
            cableAzul.setFin(c2);//negativo
            proto.getChildren().add(cableAzul);

            // Colocar la imagen del LED en el punto medio
            Led led = new Led(proto, midX, midY);
            led.setConectado();
            led.setCable_azul(cableAzul);
            led.setCable_rojo(cable);
            leds.add(led);

        } else if (switchClicked) {
            cable.setFin(c2);
            cable.setStroke(Color.BLUE);
            cable.setStrokeWidth(5);
            proto.getChildren().add(cable);
            // Calcular el punto medio de la línea
            double midX = (c1.getCenterX() + c2.getCenterX()) / 2;
            double midY = (c1.getCenterY() + c2.getCenterY()) / 2;
            // Colocar la imagen del switch en el punto medio
            crearSwitch(proto, midX, midY, cable);
        } else {
            // Para cables básicos (sin LED o switch), mantener la lógica genérica
            cable.setStroke(Color.GREEN);  // O cualquier color específico para cables básicos
            cable.setStrokeWidth(5);
            cables.add(cable);
            pane.getChildren().add(cable);
        }
    }

    //Metodo que empieza la creacion del cable
    public void inicio(MouseEvent event) {
        //Atraves del bus este genera un cable anclado
        bus circulo_apret = (bus) event.getSource();
        linea = new conection(circulo_apret.getCenterX(), circulo_apret.getCenterY(),
                circulo_apret.getCenterX(), circulo_apret.getCenterY());
        linea.setStroke(Color.GREEN);
        linea.setEndX(circulo_apret.getCenterX());
        linea.setEndY(circulo_apret.getCenterY());
        linea.setStrokeWidth(5);
        linea.setInicio(circulo_apret);
        Parent parent = circulo_apret.getParent();
        if (parent instanceof Pane) {
            ((Pane) parent).getChildren().add(linea);  // Añadir la línea al Pane
            linea.setStartX(circulo_apret.getCenterX());
            circulo_bateria=true;
        } else if (parent instanceof Group) {
            ((Group) circulo_apret.getParent()).getChildren().add(linea);  // Si fuera Group, agregaría aquí
        }
        // Movimiento de la línea
        circulo_apret.getParent().setOnMouseMoved(this::movimiento);
    }

    //Metodo que actualiza la pocision del cable hasta presionar un circulo
    void movimiento(MouseEvent event) {
        if (linea != null) {
            linea.setEndX(event.getX());
            linea.setEndY(event.getY());
            linea.getParent().setOnMouseClicked(this::parar);
        }
    }

    //Metodo para parar la creacion del cable
    void parar(MouseEvent event) {
        // Desactivar temporalmente eventos
        if (event.getSource() instanceof Parent parent) {
            parent.setOnMouseMoved(null);
            parent.setOnMouseClicked(null);
        }
        if (linea != null) {
            int x = 0;
            while (x < protos.size()) {
                for (int i = 0; i < 14; i++) {
                    for (int j = 0; j < 30; j++) {
                        bus targetCircle = protos.get(x).alimentacion[i][j];
                        if (targetCircle != null) {
                            double dx = event.getX() - targetCircle.getCenterX();
                            double dy = event.getY() - targetCircle.getCenterY();
                            double distance = Math.sqrt(dx * dx + dy * dy);
                            if (distance <= targetCircle.getRadius()) {
                                linea.setFin(targetCircle);
                                conection nuevo = linea;
                                nuevo.setOnMouseClicked(Click::eliminarElemento);
                                // Anclar el extremo inicial de la línea a la posición del grupo
                               if(circulo_bateria){
                                   protos.get(x).setConections(linea);
                                   linea.endXProperty().bind(protos.get(x).layoutXProperty().add(targetCircle.getCenterX()));
                                   linea.endYProperty().bind(protos.get(x).layoutYProperty().add(targetCircle.getCenterY()));
                               }
                               circulo_bateria=false;
                                cables.add(nuevo);
                                linea = null;
                                return; // Salir del método una vez que se haya encontrado un círculo
                            }
                        }
                    }
                }
                x++;
            }

            // Verificar los círculos de la batería
            if (bateria != null) {
                if (verificarCírculoBateria(event, bateria.getPositivo()) ||
                        verificarCírculoBateria(event, bateria.getNegativo())) {
                    return;
                }
            }
        }

        // Reactivar eventos si no se completó la operación
        if (event.getSource() instanceof Parent parent) {
            parent.setOnMouseMoved(this::movimiento);
            parent.setOnMouseClicked(this::parar);
        }
    }

    //Metodo para verificar los circulos de la bateria
    private boolean verificarCírculoBateria(MouseEvent event, bus circuloBateria) {
        double dx = event.getX() - circuloBateria.getCenterX();
        double dy = event.getY() - circuloBateria.getCenterY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        if (distance <= circuloBateria.getRadius()) {
            // Detener el dibujo en el círculo de la batería
            linea.setFin(circuloBateria);
            conection nuevo = linea;
            nuevo.setOnMouseClicked(Click::eliminarElemento);
            cables.add(nuevo);
            if (linea.getInicio() == null) {
                linea.setInicio(circuloBateria);
            } else {
                linea.setFin(circuloBateria);
            }

            bateria.addCable(nuevo);
            circuloBateria.getParent().setOnMouseMoved(null);
            circuloBateria.getParent().setOnMouseClicked(null);
            linea = null;
            return true;
        }
        return false;
    }

    // Metodo que crea Switch
    public void crearSwitch(Protoboard proto, double x, double y, conection cables) {
        // Crear el objeto Switch en la posición calculada
        Switch SW = new Switch(proto, x, y, cables);
        setSwitchClicked(true);
        switches.add(SW);
        primercircle = null;
    }

    //Metodo que llama a la funcion revovinar en la clase manejarCarga
    public  void revovinar() {
        manejarCarga.revovinar();
    }

    //Metodo que llama a la funcion verificar cables en la clase manejarCarga
    public void verificar_cables() {
        manejarCarga.verificarCables(cables);
    }

    //Metodo que llama a la funcion verificar led en la clase manejarCarga
    public void prender_led() {
        manejarCarga.prenderLed(leds);
    }

    //Metodo que llama a la funcion verificar switch en la clase manejarCarga
    public void verificar_switch() {
        manejarCarga.verificarSwitches(switches);
    }

}
