package com.example.protoboar;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.layout.Pane;
import java.util.ArrayList;

//Clase se encarga de los circulos
public class ManejarCirculos {

    //Atributos
    private final Pane pane;
    private final ArrayList<Protoboard> protos;
    private conection linea;
    private boolean ledClicked;
    private String ledColor;
    private boolean cableClicked;
    private boolean resistClicked;
    private bus primercircle;
    private boolean circulo_bateria;
    private boolean circulo_motor;
    private final ArrayList<conection> cables;
    private final ArrayList<Led> leds;
    private static ArrayList<Resistencia> resistencias;
    private static ArrayList<Switch3x3> switches3x3;
    private static ArrayList<Switch8x3> switches8x3;
    private final Bateria bateria;
    private final Motor motor;
    private final ManejarCarga manejarCarga;
    private final ArrayList<Chip> chips;
    private final ArrayList<Display> displays;
    private Scene scene;

    //Constructor
    public ManejarCirculos(Pane pane, ArrayList<Protoboard> protos, boolean ledClicked, boolean cableClicked, Bateria bateria, Motor motor, String ledColor) {
        this.pane = pane;
        this.protos = protos;
        this.ledClicked = ledClicked;
        this.ledColor = ledColor;
        this.cableClicked = cableClicked;
        this.bateria = bateria;
        this.motor = motor;
        this.resistClicked = false;
        cables = new ArrayList<>();
        switches3x3 = new ArrayList<>();
        switches8x3 = new ArrayList<>();
        displays = new ArrayList<>();
        leds = new ArrayList<>();
        resistencias = new ArrayList<>();
        chips=new ArrayList<>();
        this.manejarCarga = new ManejarCarga(protos);
        this.primercircle = null;
    }

    // get y set

    public ArrayList<conection> getCables() {
        return cables;
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

    public void setLedColor(String ledColor) {
        this.ledColor = ledColor;
    }

    public void setCableClicked(boolean cableClicked) {
        this.cableClicked = cableClicked;
    }

    public void setResistClicked(boolean resistClicked){
        this.resistClicked = resistClicked;
        if(!resistClicked){
            primercircle=null;
        }
    }

    public ArrayList<Protoboard> getprotos() {
        return protos;
    }

    public ArrayList<Chip> getChips() {
        return chips;
    }

    public ArrayList<Display> getdisplays() {
        return displays;
    }

    public ArrayList<Switch3x3> getswitches3x3() {
        return switches3x3;
    }

    public ArrayList<Switch8x3> getswitches8x3() {
        return switches8x3;
    }

    public conection getLinea() {
        return linea;
    }

   public void eliminar_linea(){
        eliminar_linea_creando(1);
   }

    //Metodos//////////////////////////////////////////////////
    //Metodo que se encarga de el presionado de los circulos
    public void presionarCirculo(MouseEvent event) {
        bus circulo = (bus) event.getSource();
        // Verificar si ya hay un componente en el círculo
        if (!circulo.puedeCrearComponente()) {
            return; // No hacer nada si el círculo ya está ocupado
        }
        // Condiciones para saber si este bus es para cable, led o switch
        if (primercircle == null && ledClicked) {
            primercircle = circulo; // Asignación del primer círculo
        } else if (primercircle != null && ledClicked) {
            // Solo permite la creación si ambos círculos pueden aceptar componentes
            if (circulo.puedeCrearComponente() && primercircle.puedeCrearComponente()) {
                for (Protoboard proto : protos) {
                    if (proto.getChildren().contains(primercircle)) {
                        crearCableEntreCirculos(primercircle, circulo, proto);
                        primercircle.crearComponente();
                        circulo.crearComponente(); // Marca que se ha creado un componente
                    }
                }
                primercircle = null; // Reiniciar para permitir la selección de nuevos círculos
            }
        } else if (primercircle == null && resistClicked) {
            primercircle = circulo; // Asignación del primer círculo
        } else if (primercircle != null && resistClicked) {
            if (circulo.puedeCrearComponente() && primercircle.puedeCrearComponente()) {
                for (Protoboard proto : protos) {
                    if (proto.getChildren().contains(primercircle)) {
                        primercircle.crearComponente();
                        circulo.crearComponente();
                        crearCableEntreCirculos(primercircle, circulo, proto);
                        // Marca que se ha creado un componente
                    }
                }
                primercircle = null; // Reiniciar para permitir la selección de nuevos círculos
            }
        } else if (cableClicked) {
            inicio(event);
        }
    }

    // Metodo que genera cable para los led y switch
    private void crearCableEntreCirculos(bus c1, bus c2, Protoboard proto) {
        if (c1 == null || c2 == null) {
            return;
        }
        c2.crearComponente();
        // Genera un cable de tipo conection con los dos círculos presionados
        conection cable = new conection(c1.getCenterX(), c1.getCenterY(), c2.getCenterX(), c2.getCenterY());
        cable.setInicio(c1); // Positivo
        // Agregar función para borrar al presionar si el modo borrar está activo
        cable.setOnMouseClicked(Click::eliminarElemento);
            // Configuración del cable para LED
            if (ledClicked) {
                cable.setStroke(Color.RED); //led
                cable.setStrokeWidth(5);
                proto.getChildren().add(cable);
                double midX = (c1.getCenterX() + c2.getCenterX()) / 2;
                double midY = (c1.getCenterY() + c2.getCenterY()) / 2;
                conection cableAzul = new conection(midX, midY, c2.getCenterX(), c2.getCenterY());
                cableAzul.setStroke(Color.BLUE);
                cableAzul.setStrokeWidth(5);
                cableAzul.setFin(c2); // Negativo
                proto.getChildren().add(cableAzul);
                Led led = new Led(proto, midX, midY);
                led.setConectado();
                led.setCable_azul(cableAzul);
                led.setCable_rojo(cable);
                leds.add(led);
            } else if (resistClicked) {
                cable.setFin(c2);  //resistencia
                cable.setStroke(Color.GRAY);
                cable.setStrokeWidth(5);
                proto.getChildren().add(cable);
                double midX = (c1.getCenterX() + c2.getCenterX()) / 2;
                double midY = (c1.getCenterY() + c2.getCenterY()) / 2;
                crearResistencia(proto, midX, midY, cable);
            } else {
                cable.setStroke(Color.GREEN); //cable basico
                cable.setStrokeWidth(5);
                cables.add(cable);
                pane.getChildren().add(cable);
            }
    }

    //Metodo que empieza la creacion del cable
    public void inicio(MouseEvent event) {
        //Atraves del bus este genera un cable anclado
        bus circulo_apret = (bus) event.getSource();
        if (!circulo_apret.puedeCrearComponente()){
            return;  // Salir si ya tiene un componente
        }
        circulo_apret.crearComponente();
        linea = new conection(circulo_apret.getCenterX(), circulo_apret.getCenterY(),
                circulo_apret.getCenterX(), circulo_apret.getCenterY());
        linea.setStroke(Color.GREEN);
        linea.setEndX(circulo_apret.getCenterX());
        linea.setEndY(circulo_apret.getCenterY());
        linea.setStrokeWidth(5);
        linea.setInicio(circulo_apret);
        linea.toFront();
        linea.pos_proto=-1;
       //La linea se agrega la pane para poder moverla mas facilmente y este este siempre al frente y no se queden atras
        pane.getChildren().add(linea);  // Añadir la línea al Pane
        // depende si se presiono una bateria o motor o el protoboard para saber como actuar
        if(circulo_apret == bateria.getNegativo() || circulo_apret == bateria.getPositivo() || circulo_apret == motor.getNegativo()
                || circulo_apret == motor.getPositivo()){
            // si es la bateria "circulo_bateria" sera true para saber que es la bateria y se coloca el star de la linea
            linea.setStartX(circulo_apret.getCenterX());
            circulo_bateria=true;
        }else{
            // si es el protoboard viaja por todos los protoboards buscando el bus presionado y guardas en que protoboard esta
            int x=0;
            while(x<protos.size()){
                for(Node node:protos.get(x).getChildren()){
                    if(node instanceof bus){
                        if(node==circulo_apret){
                            linea.pos_proto=x;
                        }
                    }
                }
                x++;
            }
            // se coloca en el bus si este es el extremo incial "star de la linea" o el final "end de la linea"
            circulo_apret.setExtremo();
            // se coloca las coordenadas de este
            linea.setStartX(circulo_apret.getCenterX());
            linea.setStartY(circulo_apret.getCenterY());
        }
        if (circulo_apret == motor.getPositivo() || circulo_apret == motor.getNegativo()) {
            circulo_motor = true; // Activar estado del motor
        }
        // Movimiento de la línea
        scene = circulo_apret.getScene();
        scene.setOnMouseMoved(this::movimiento);
    }

    //Metodo que actualiza la pocision del cable hasta presionar un circulo
    void movimiento(MouseEvent event) {
        // Condicion para saber si la linea en movimiento aun existe
        if (linea != null) {
            // este guarda su posisicon actual
            Parent parent = linea.getParent();
            double localX = parent.sceneToLocal(event.getSceneX(), event.getSceneY()).getX();
            double localY = parent.sceneToLocal(event.getSceneX(), event.getSceneY()).getY();
            // Establecer el extremo de la línea basado en las coordenadas locales
            // guardando su posicion el en final de la linea
            linea.setEndX(localX);
            linea.setEndY(localY);
            linea.getParent().setOnMouseClicked(this::parar);
        } else{
            // si la linea no existe este se elimina
            Node sourceNode = (Node) event.getTarget(); // Hacer un casting a Node
            Scene scene = sourceNode.getScene();
            scene.setOnMouseMoved(null);
        }
    }

    void parar(MouseEvent event) {
        // Desactivar temporalmente eventos
        if (event.getSource() instanceof Parent parent) {
            parent.setOnMouseMoved(null);
            parent.setOnMouseClicked(null);
        }
        // si la linea esxiste comprueba si se en el lugar presionado se encuentra un bus
        if (linea != null) {
            int x = 0;
            while (x < protos.size()) {
                // viaja pot el protoboard buscando los buses
                for (int i = 0; i < 14; i++) {
                    for (int j = 0; j < 30; j++) {
                        bus targetCircle = protos.get(x).alimentacion[i][j];
                        if (targetCircle != null) {
                            if (targetCircle.puedeCrearComponente()) {
                                double dx = event.getX() - targetCircle.getCenterX();
                                double dy = event.getY() - targetCircle.getCenterY();
                                double distance = Math.sqrt(dx * dx + dy * dy);
                                if (distance <= targetCircle.getRadius()) {
                                    linea.setFin(targetCircle);
                                    conection nuevo = linea;
                                    nuevo.setOnMouseClicked(Click::eliminarElemento);
                                    // Anclar el extremo inicial de la línea a la posición del grupo
                                    //se agregan los cables al protoboard
                                    if (linea.pos_proto > -1) {
                                        if (!protos.get(linea.pos_proto).getChildren().contains(targetCircle)) {
                                            //linea.salio = true;
                                            protos.get(linea.pos_proto).setConections(linea);
                                        }
                                    }
                                    // agregar el cable a la bateria
                                    if (circulo_bateria) {
                                        bateria.addCable(nuevo); // Añade el cable a la batería
                                    }
                                    // agregar el cable al motor
                                    if (circulo_motor) {
                                        motor.addCable(nuevo); // Añade el cable a la batería
                                    }
                                    protos.get(x).setConections(linea);
                                    linea.endXProperty().bind(protos.get(x).layoutXProperty().add(targetCircle.getCenterX()));
                                    linea.endYProperty().bind(protos.get(x).layoutYProperty().add(targetCircle.getCenterY()));
                                    targetCircle.setExtremo();
                                    targetCircle.crearComponente();
                                    circulo_bateria = false;
                                    circulo_motor = false;
                                    cables.add(nuevo);
                                    linea = null;
                                    scene = null;
                                    return; // Salir del método una vez que se haya encontrado un círculo
                                }
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
            if (motor != null) {
                verificarCírculoMotor(event, motor.getPositivo());
                verificarCírculoMotor(event, motor.getNegativo());
            }
            eliminar_linea_creando(0);
            return;
        }
        // Reactivar eventos si no se completó la operación
        if (event.getSource() instanceof Parent parent) {
            parent.setOnMouseMoved(this::movimiento);
            parent.setOnMouseClicked(this::parar);
        }
    }

    private void eliminar_linea_creando(int entrada) {
        if (linea != null) {
            if (entrada == 1) {
                if (linea.getParent() != null) {
                    linea.getParent().setOnMouseClicked(null);
                }
            }
            if (linea.getInicio() != null) {
                linea.getInicio().componenteCreado = false;
            }
            // Eliminar la línea de la vista solo si no es nula
            if (pane != null) {
                pane.getChildren().remove(linea);
            }
            linea = null;
            scene = null;
            circulo_bateria = false;
            circulo_motor = false;
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
            // Agregar el cable a la lista de cables de batería
            bateria.addCable(nuevo);
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

    // Metodo que crea Resistencia
    public void crearResistencia(Protoboard proto, double x, double y, conection cables) {
        // Crear el objeto Resitencia en la posición calculada
        Resistencia R = new Resistencia(proto, x, y, cables);
        setResistClicked(true);
        resistencias.add(R);
        primercircle = null;
    }

    public void crearChip(String tipo){
        Fabrica_Chip fabrica = new Fabrica_Chip();
        Chip chip=fabrica.crear(tipo);
        chip.setProtos(getprotos());
        pane.getChildren().add(chip);
        chips.add(chip);
    }

    public void creardisplay(){
        Fabrica_display fabrica = new Fabrica_display();
        Display display=fabrica.display();
        display.setProtos(getprotos());
        displays.add(display);
        pane.getChildren().add(display);
    }

    public void crearSwitch3x3(){
        Fabrica_Switch3x3 fabrica = new Fabrica_Switch3x3();
        Switch3x3 switch3x3 = fabrica.crear();
        switch3x3.setOnMouseClicked(_ -> switch3x3.cambiarCarga());
        switch3x3.setProtos(getprotos());
        pane.getChildren().add(switch3x3);
        switches3x3.add(switch3x3);
    }

    public void crearSwitch8x3(){
        Fabrica_Switch8x3 fabrica = new Fabrica_Switch8x3();
        Switch8x3 switch8x3 = fabrica.crear();
        switch8x3.setProtos(getprotos());
        pane.getChildren().add(switch8x3);
        switches8x3.add(switch8x3);
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
        manejarCarga.prenderLed(leds, ledColor);
    }

    //Metodo que llama a la funcion verificar switch en la clase manejarCarga
    public void verificar_resistencia() {
        manejarCarga.verificarResistencias(resistencias);
    }

    public void verificar_chip(){
        manejarCarga.verificar_chips(chips);
    }

    public void verificar_display(){
        revovinar_displays();
        manejarCarga.verificar_display(displays);
    }

    public void revovinar_displays(){
        int i=0;
        while(i<displays.size()){
            if(displays.get(i).agregado){
                int j=0;
                Display disp=displays.get(i);
                while(j<disp.getLeds().size()){
                    if(!(disp.getLeds(j).getFill().equals(Color.YELLOW))){
                        disp.getLeds(j).setFill(Color.GRAY);
                    }

                    j++;
                }
                if(!(disp.getPunto().getFill().equals(Color.YELLOW))){
                    disp.getPunto().setFill(Color.GRAY);
                }
            }


            i++;
        }
    }

    public void verificar_sw3x3(){
        manejarCarga.verificar_sw3x3(switches3x3);
    }

    public void verificar_sw8x3(){
        manejarCarga.verificar_sw8x3(switches8x3);
    }
}