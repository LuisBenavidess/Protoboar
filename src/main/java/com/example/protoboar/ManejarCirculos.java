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
    private final Pane pane;
    private ArrayList<Protoboard> protos;
    //private final bus[][] alimentacion;
    private conection linea;
    private boolean ledClicked;
    private boolean cableClicked;
    private boolean switchClicked;
    private bus primercircle;
    private boolean circulo_bateria;
    private ArrayList<conection> cables;
    private final ArrayList<conection> cables_led;
    private final ArrayList<Switch> switches;
    private final Bateria bateria;
    private final ManejarCarga manejarCarga;

    //Constructor
    public ManejarCirculos(Pane pane, ArrayList<Protoboard> protos, boolean ledClicked, boolean cableClicked, Bateria bateria) {
        this.pane = pane;
        this.protos = protos;
        //this.alimentacion = alimentacion;
        this.ledClicked = ledClicked;
        this.cableClicked = cableClicked;
        this.bateria = bateria;
        this.switchClicked = false;
        cables = new ArrayList<>();
        cables_led = new ArrayList<>();
        switches = new ArrayList<>();
        this.manejarCarga = new ManejarCarga(protos);
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

    public void setprotos(ArrayList<Protoboard> protos) {
        this.protos = protos;
        System.out.println(protos.get(0).alimentacion[0][0].getCenterX());
    }
    //////////////////////////////////////////////////

    //Metodos

    //Metodo que se encarga de el presionado de los circulos
    public void presionarCirculo(MouseEvent event) {
        bus circulo = (bus) event.getSource();

        System.out.println("se preciono");
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
    }

    //Metodo que genera cable para los led y switch
    private void crearCableEntreCirculos(bus c1, bus c2,Protoboard proto) {
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
            proto.getChildren().add(cable);
            // Calcular el punto medio de la línea
            double midX = (c1.getCenterX() + c2.getCenterX()) / 2;
            double midY = (c1.getCenterY() + c2.getCenterY()) / 2;
            // Colocar la imagen del LED en el punto medio
            Led led = new Led(proto, midX, midY);
            led.setConectado();
            cable.set_foto(led);
            cables_led.add(cable);
            primercircle = null;
        } else if (switchClicked) {
            //Edita el cable y crea el switch
            cable.setStroke(Color.BLUE);
            cable.setStrokeWidth(5);
            proto.getChildren().add(cable);
            // Calcular el punto medio de la línea
            double midX = (c1.getCenterX() + c2.getCenterX()) / 2;
            double midY = (c1.getCenterY() + c2.getCenterY()) / 2;
            // Colocar la imagen del SWITCH en el punto medio

            crearSwitch(proto, midX, midY, cable);
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
            System.out.println("Paso la bateria");
            linea.setStartX(circulo_apret.getCenterX());
            circulo_bateria=true;
        } else if (parent instanceof Group) {
            System.out.println("pasoooooooooooo");
            ((Group) circulo_apret.getParent()).getChildren().add(linea);  // Si fuera Group, agregaría aquí

        } else {
            System.out.println("El Parent no es ni Pane ni Group.");
        }

        // Movimiento de la línea
        circulo_apret.getParent().setOnMouseMoved(this::movimiento);
    }

    //Metodo que actualiza la pocision del cable hasta presionar un circulo
    void movimiento(MouseEvent event) {
        //System.out.println("Paso esta chingada");
        if (linea != null) {
            linea.setEndX(event.getX());
            linea.setEndY(event.getY());
            System.out.println("movimiento");
            linea.getParent().setOnMouseClicked(this::parar);
        }else{
            System.out.println("El Linea no existe");
        }
    }

    //Metodo para parar la creacion del cable
   /* void parar(MouseEvent event) {
        System.out.println("Termino esta chingada");
        if (linea != null) {
            //Viaja atraves de la matriz que contiene todos los buses bucando el cual coincida con el presionado
            int x=0;
            while(x<protos.size()){

                for (int i = 0; i < 14; i++) {
                    for (int j = 0; j < 30; j++) {
                        bus targetCircle = protos.get(x).alimentacion[i][j];
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
                                System.out.println("termino esta chingada para siempre");
                                return; // Salir del método una vez que se haya encontrado un círculo
                            }
                        }
                    }
                }

                x++;
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

    }*/

    void parar(MouseEvent event) {
       // System.out.println("Termino esta chingada");

        // Desactivar temporalmente eventos
        if (event.getSource() instanceof Parent) {
            Parent parent = (Parent) event.getSource();
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
                                   System.out.println("entroooooo");
                                   protos.get(x).setConections(linea);
                                   linea.endXProperty().bind(protos.get(x).layoutXProperty().add(targetCircle.getCenterX()));
                                   linea.endYProperty().bind(protos.get(x).layoutYProperty().add(targetCircle.getCenterY()));
                               }
                               circulo_bateria=false;

                                cables.add(nuevo);
                                linea = null;

                                System.out.println("termino esta chingada para siempre");

                                // Reactivar eventos después de la creación
                                /*parent.setOnMouseMoved(this::movimiento);
                                parent.setOnMouseClicked(this::parar);*/
                                return; // Salir del método una vez que se haya encontrado un círculo
                            }
                        }
                    }
                }
                x++;
            }

            // Verificar los círculos de la batería
            if (bateria != null) {
                verificarCírculoBateria(event, bateria.getPositivo());
                verificarCírculoBateria(event, bateria.getNegativo());
            }
            System.out.println("No entra");
        }

        // Reactivar eventos si no se completó la operación
        if (event.getSource() instanceof Parent) {
            Parent parent = (Parent) event.getSource();
            parent.setOnMouseMoved(this::movimiento);
            parent.setOnMouseClicked(this::parar);
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
        manejarCarga.prenderLed(cables_led);
    }

    //Metodo que llama a la funcion verificar switch en la clase manejarCarga
    public void verificar_switch() {
        manejarCarga.verificarSwitches(switches);
    }

}
