package com.example.protoboar;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
//Clase se encarga de los circulos
public class ManejarCirculos {

    //Atributos
    private final Pane pane;
    private final bus[][] alimentacion;
    private conection linea;
    private boolean ledClicked;
    private boolean cableClicked;
    private boolean switchClicked;
    private bus primercircle;
    private final ArrayList<conection> cables;
    private final ArrayList<conection> cables_led;
    private final ArrayList<Switch> switches;
    private final Bateria bateria;
    private final Motor motor;
    private final ManejarCarga manejarCarga;

    //Constructor
    public ManejarCirculos(Pane pane, bus[][] alimentacion, boolean ledClicked, boolean cableClicked, Bateria bateria, Motor motor) {
        this.pane = pane;
        this.alimentacion = alimentacion;
        this.ledClicked = ledClicked;
        this.cableClicked = cableClicked;
        this.bateria = bateria;
        this.motor = motor;
        this.switchClicked = false;
        cables = new ArrayList<>();
        cables_led = new ArrayList<>();
        switches = new ArrayList<>();
        this.manejarCarga = new ManejarCarga(alimentacion);
    }

    // get y set

    public ArrayList<conection> getCables() {
        return cables;
    }

    public ArrayList<Switch> getswitches() {
        System.out.println(" cuantos se pasan");
        System.out.println(switches.size());
        return switches;
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
            crearCableEntreCirculos(primercircle, circulo);
            primercircle = null; // Reiniciar para permitir la selección de nuevos círculos
        } else if (primercircle == null && switchClicked) {
            primercircle = circulo; // Asignación del primer círculo
            System.out.println("Primer círculo asignado");
        } else if (primercircle != null && switchClicked) {
            System.out.println("Segundo círculo");
            crearCableEntreCirculos(primercircle, circulo);
            primercircle = null; // Reiniciar para permitir la selección de nuevos círculos
        } else if (cableClicked) {
            inicio(event);
        }
    }

    //Metodo que genera cable para los led y switch
    private void crearCableEntreCirculos(bus c1, bus c2) {
        if (c1 == null || c2 == null) {
            System.out.println("uno de los círculos es null.");
            return;
        }
        //Genera un cable de tipo conection con los dos circulos presionados
        conection cable = new conection(c1.getCenterX(), c1.getCenterY(), c2.getCenterX(), c2.getCenterY());
        cable.setInicio(c1);
        cable.setFin(c2);

        //Agregar funcion para borrar al presionar si el modo borrar este activo
        cable.setOnMouseClicked(Click::eliminarElemento);

        //Condicion para saber si esto se trata de un switch o led
        if (ledClicked) {
            //Edita el cable y crea el led
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

        } else if (switchClicked) {
            //Edita el cable y crea el switch
            cable.setStroke(Color.BLUE);
            cable.setStrokeWidth(5);
            pane.getChildren().add(cable);

            // Calcular el punto medio de la línea
            double midX = (c1.getCenterX() + c2.getCenterX()) / 2;
            double midY = (c1.getCenterY() + c2.getCenterY()) / 2;

            // Colocar la imagen del LED en el punto medio
            crearSwitch(pane, midX, midY, cable);
        }
    }

    //Metodo que empieza la creacion del cable
    public void inicio(MouseEvent event) {
        //Atraves del bus este genera un cable anclado
        bus circulo_apret = (bus) event.getSource();
        linea = new conection(circulo_apret.getCenterX(), circulo_apret.getCenterY(),
                circulo_apret.getCenterX(), circulo_apret.getCenterY());
        linea.setStroke(Color.GREEN);

        linea.setStrokeWidth(5);
        linea.setInicio(circulo_apret);
        //Se agrega el cable al pane
        ((Pane) circulo_apret.getParent()).getChildren().add(linea);
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
        if (linea != null) {
            //Viaja atraves de la matriz que contiene todos los buses bucando el cual coincida con el presionado
            for (int i = 0; i < 14; i++) {
                for (int j = 0; j < 30; j++) {
                    bus targetCircle = alimentacion[i][j];
                    if (targetCircle != null) {
                        double dx = event.getX() - targetCircle.getCenterX();
                        double dy = event.getY() - targetCircle.getCenterY();
                        double distance = Math.sqrt(dx * dx + dy * dy);
                        //mide la distancia de el circulo para saber si el cable se encuentra dentro de este
                        if (distance <= targetCircle.getRadius()) {
                            // Detener el dibujo
                            linea.setFin(targetCircle);

                            conection nuevo = linea;
                            nuevo.setOnMouseClicked(Click::eliminarElemento);
                            //Se agrega el nuevo cable al ArrayList
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
            if (motor != null) {
                verificarCírculoMotor(event, motor.getPositivo());
                verificarCírculoMotor(event, motor.getNegativo());
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

    //Metodo para verificar los circulos de la bateria
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

    private void verificarCírculoMotor(MouseEvent event, bus circuloMotor) {
        double dx = event.getX() - circuloMotor.getCenterX();
        double dy = event.getY() - circuloMotor.getCenterY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        if (distance <= circuloMotor.getRadius()) {
            // Detener el dibujo en el círculo del Motor
            linea.setFin(circuloMotor);
            conection nuevo = linea;
            nuevo.setOnMouseClicked(Click::eliminarElemento);
            cables.add(nuevo);
            linea = null;
            circuloMotor.getParent().setOnMouseMoved(null);
            circuloMotor.getParent().setOnMouseClicked(null);
        }
    }

    // Metodo que crea Switch
    public void crearSwitch(Pane pane, double x, double y, conection cables) {
        // Crear el objeto Switch en la posición calculada
        Switch SW = new Switch(pane, x, y, cables);
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
        manejarCarga.prenderLed(cables_led);
    }

    //Metodo que llama a la funcion verificar switch en la clase manejarCarga
    public void verificar_switch() {
        manejarCarga.verificarSwitches(switches);
    }

}
