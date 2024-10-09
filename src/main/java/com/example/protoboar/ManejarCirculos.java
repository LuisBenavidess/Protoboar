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
    private boolean resistClicked;
    private bus primercircle;
    private boolean circulo_bateria;
    private final ArrayList<conection> cables;
    private final ArrayList<Led> leds;
    private final ArrayList<Switch> switches;
    private static ArrayList<Resistencia> resistencias;
    private final Bateria bateria;
    private final ManejarCarga manejarCarga;
    private ArrayList<Chip> chips;

    //Constructor
    public ManejarCirculos(Pane pane, ArrayList<Protoboard> protos, boolean ledClicked, boolean cableClicked, Bateria bateria,Motor motor) {
        this.pane = pane;
        this.protos = protos;
        this.ledClicked = ledClicked;
        this.cableClicked = cableClicked;
        this.bateria = bateria;
        this.switchClicked = false;
        this.resistClicked = false;
        cables = new ArrayList<>();
        leds = new ArrayList<>();
        switches = new ArrayList<>();
        resistencias = new ArrayList<>();
        chips=new ArrayList<>();
        this.manejarCarga = new ManejarCarga(protos);
    }

    // get y set

    public ArrayList<conection> getCables() {
        return cables;
    }

    public ArrayList<Switch> getswitches() {
        return switches;
    }

    public ArrayList<Led> get_leds() {
        return leds;
    }

    public ArrayList<Resistencia> getResistencias() {
        return resistencias;
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

    public void setResistClicked(boolean resistClicked){
        this.resistClicked = resistClicked;
        if(!resistClicked){
            primercircle=null;
        }
    }

    public void setprotos(ArrayList<Protoboard> protos) {
        this.protos = protos;
        System.out.println(protos.getFirst().alimentacion[0][0].getCenterX());
    }

    public ArrayList<Protoboard> getprotos() {
        return protos;
    }

    public ArrayList<Chip> getChips() {
        return chips;
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
        } else if (primercircle == null && resistClicked) {
            primercircle = circulo; // Asignación del primer círculo
            System.out.println("Primer círculo asignado");
        }else if (primercircle != null && resistClicked) {
            System.out.println("Segundo círculo");
            int i=0;
            while(i<protos.size()){
                if(protos.get(i).getChildren().contains(primercircle)){
                    crearCableEntreCirculos(primercircle, circulo,protos.get(i));
                }
                i++;
            }
            primercircle = null; // Reiniciar para permitir la selección de nuevos círculos
        }else if (cableClicked) {
            inicio(event);
        }
    }

    // Metodo que genera cable para los led y switch
    private void crearCableEntreCirculos(bus c1, bus c2, Protoboard proto) {
        if (c1 == null || c2 == null) {
            return;
        }

        // Genera un cable de tipo conection con los dos círculos presionados
        conection cable = new conection(c1.getCenterX(), c1.getCenterY(), c2.getCenterX(), c2.getCenterY());
        cable.setInicio(c1); //positivo


        // Agregar función para borrar al presionar si el modo borrar está activo
        cable.setOnMouseClicked(Click::eliminarElemento);

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

        } else if (resistClicked) {

            System.out.println("Se creó una resistencia");
            cable.setFin(c2);
            cable.setStroke(Color.GRAY);
            cable.setStrokeWidth(5);
            proto.getChildren().add(cable);

            // Calcular el punto medio de la línea
            double midX = (c1.getCenterX() + c2.getCenterX()) / 2;
            double midY = (c1.getCenterY() + c2.getCenterY()) / 2;

            // Colocar la imagen del switch en el punto medio
            crearResistencia(proto, midX, midY, cable);
        }else {
            // Para cables básicos (sin LED, switch o otro elemento), mantener la lógica genérica
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
    public void crearSwitch(Protoboard proto, double x, double y, conection cables) {
        // Crear el objeto Switch en la posición calculada
        Switch SW = new Switch(proto, x, y, cables);
        setSwitchClicked(true);
        switches.add(SW);
        primercircle = null;
    }

    // Metodo que crea Resistencia
    public void crearResistencia(Protoboard proto, double x, double y, conection cables) {
        // Crear el objeto Resitencia en la posición calculada
        Resistencia R = new Resistencia(proto, x, y, cables);
        setResistClicked(true);
        resistencias.add(R);
        primercircle = null;
    }

    public void crearChip(){
        Fabrica_Chip fabrica = new Fabrica_Chip();
        Chip chip=fabrica.crear();
        chip.setProtos(getprotos());
        pane.getChildren().add(chip);
        chips.add(chip);

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

    //Metodo que llama a la funcion verificar switch en la clase manejarCarga
    public void verificar_resistencia() {
        manejarCarga.verificarResistencias(resistencias);
    }

    public void verificar_chip(){
        manejarCarga.verificar_chips(chips);
    }

}
