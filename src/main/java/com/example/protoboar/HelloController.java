package com.example.protoboar;

import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.shape.Circle;
import java.util.ArrayList;
import java.util.Objects;
import javafx.scene.control.MenuItem;
import javafx.scene.paint.Color;

//Controlador
public class HelloController {
    //Atributos
    @FXML
    private Pane pane;
    @FXML
    private Click clickHandler;
    @FXML
    private boolean ledClicked=false;
    private String ledColor = "";
    @FXML
    private boolean cableClicked=false;
    @FXML
    private boolean resistClicked;
    @FXML
    private ImageView basurero;
    @FXML
    private ImageView cableID;
    @FXML
    private ImageView ledID;
    @FXML
    private ImageView resistenciaID;
    @FXML
    private Fabrica_Proto fabrica;
    @FXML
    private ArrayList<Protoboard> protos;
    @FXML
    private boolean basureroActivo = false;
    @FXML
    private ContextMenu ledMenu;

    //Metodos
    @FXML
    private void initialize() {
        ledMenu = new ContextMenu();
        // Crear círculos de color para los leds
        Circle greenCircle = new Circle(10, Color.GREEN);
        MenuItem greenItem = new MenuItem();
        greenItem.setGraphic(greenCircle);
        greenItem.setOnAction(_ -> crearLed("green"));
        Circle pinkCircle = new Circle(10, Color.PINK);
        MenuItem pinkItem = new MenuItem();
        pinkItem.setGraphic(pinkCircle);
        pinkItem.setOnAction(_ -> crearLed("pink"));
        Circle redCircle = new Circle(10, Color.RED);
        MenuItem redItem = new MenuItem();
        redItem.setGraphic(redCircle);
        redItem.setOnAction(_ -> crearLed("red"));
        Circle blueCircle = new Circle(10, Color.BLUE);
        MenuItem blueItem = new MenuItem();
        blueItem.setGraphic(blueCircle);
        blueItem.setOnAction(_ -> crearLed("blue"));
        Circle purpleCircle = new Circle(10, Color.PURPLE);
        MenuItem purpleItem = new MenuItem();
        purpleItem.setGraphic(purpleCircle);
        purpleItem.setOnAction(_ -> crearLed("purple"));
        ledMenu.getItems().addAll(greenItem, pinkItem, redItem, blueItem, purpleItem);
        //Crea el protoboard inicial
        int cantidad = 0;
        protos = new ArrayList<>();
        fabrica = new Fabrica_Proto();
        protos.add(fabrica.protoboard());
        pane.getChildren().add(protos.get(cantidad));
        // Configurar el clic en el basurero
        if (basurero != null) {
            basurero.setOnMouseClicked(this::activarBasurero);
        }
        //Genera la matriz con los buses
        Bateria bateria = new Bateria(pane);
        Motor motor = new Motor(pane);
        clickHandler = new Click(pane, protos, ledClicked, cableClicked,bateria, motor, ledColor);
        bateria.getPositivo().setOnMouseClicked(clickHandler::presionarCirculo);
        bateria.getNegativo().setOnMouseClicked(clickHandler::presionarCirculo);
        motor.getPositivo().setOnMouseClicked(clickHandler::presionarCirculo);
        motor.getNegativo().setOnMouseClicked(clickHandler::presionarCirculo);
        accion_presionar((protos.get(cantidad)));
    }

    @FXML
    private void mostrarMenuLed(MouseEvent event) {
        ledMenu.show(ledID, event.getScreenX(), event.getScreenY());
    }

    @FXML
    //Metodo que coloca a cada bus del protoboard el evento al momentode precionar el bus(ciruclo)
    private void accion_presionar(Protoboard proto){
        int i = 0;
        while (i < 14) {
            int j = 0;
            while (j < 30) {
                bus circulo = proto.alimentacion[i][j];
                if (circulo != null) {
                    circulo.setOnMouseClicked(clickHandler::presionarCirculo);
                }
                j++;
            }
            i++;
        }
    }

    @FXML
    // Metodo que crea la imagen led y controla si esta activa o no
    private void crearLed(String color) {
        ledColor = color;
        desactivarOpciones();
        if(ledClicked) {
            Image image = new Image("/led.png"); // Imagen del led en estado activo
            ledID.setImage(image);
            clickHandler.setLedClicked(false);  //Se cambia el booleano para identificar si este esta prendido o apagado
            ledClicked=false;
        }else{
            Image image = new Image("/ledOn.png"); // Imagen del led en estado inactivo
            ledID.setImage(image);
            clickHandler.setLedClicked(true); //Se cambia el booleano para identificar si este esta prendido o apagado
            ledClicked=true;
            clickHandler.setLedColor(ledColor);
        }
    }

    @FXML
    // Metodo que crea la imagen del cable y controla si esta activa o no
    private void crearCable() {
        if (cableClicked) {
            Image image = new Image("/cable.png"); // Imagen del cable en estado inactivo
            cableID.setImage(image);
            //Se cambia el booleano para identificar si este esta prendido o apagado
            clickHandler.setCableClicked(false);
            cableClicked = false;
        } else {
            desactivarOpciones();
            Image image = new Image("/cableOn.png"); // Imagen del cable en estado activo
            cableID.setImage(image);
            //Se cambia el booleano para identificar si este esta prendido o apagado
            clickHandler.setCableClicked(true);
            cableClicked = true;
        }
    }

    @FXML
    // Controla la creacion de la resistencia
    private void crearResistencia() {
        if(resistClicked) {
            Image image = new Image(Objects.requireNonNull(getClass().getResource("/resistencia.png")).toExternalForm());
            resistenciaID.setImage(image); // Imagen del resistencia en estado activo
            //Se cambia el booleano para identificar si este esta prendido o apagado
            clickHandler.setResistencias(false);
            resistClicked=false;
        } else{
            desactivarOpciones();
            Image image = new Image(Objects.requireNonNull(getClass().getResource("/resistenciaOn.png")).toExternalForm());
            resistenciaID.setImage(image); // Imagen del resistencia en estado activo
            //Se cambia el booleano para identificar si este esta prendido o apagado
            clickHandler.setResistencias(true);
            resistClicked=true;
        }
    }

    @FXML
    //Metodo que llama a la funcion borrar basura atravez de un evento del basurero
    private void activarBasurero(MouseEvent event) {
        if(basureroActivo) {
            Image image = new Image("/basurero.png");
            basurero.setImage(image);
            clickHandler.ClickEnBasurero();
            basureroActivo=false;
        }else{
            desactivarOpciones();
            Image image = new Image("/basureroOn.png");
            basurero.setImage(image);
            clickHandler.ClickEnBasurero();
            basureroActivo=true;
        }
    }

    @FXML
    private void crearChip_or(){
        crearChip("OR");
    }

    @FXML
    private void crearChip_and(){
        crearChip("AND");
    }

    @FXML
    private void crearChip_not(){
        crearChip("NOT");
    }

    private void crearChip(String tipo){
        desactivarOpciones();
        clickHandler.CrearChip(tipo);
    }

    @FXML
    private void crear_display(){
        desactivarOpciones();
        clickHandler.creardisplay();
    }

    @FXML
    private void crearSwitch3x3() {
        desactivarOpciones();
        clickHandler.CrearSwitch3x3();
    }

    @FXML
    private void crearSwitch8x3() {
        desactivarOpciones();
        clickHandler.CrearSwitch8x3();
    }

    @FXML
    private void crear_proto() {
        int cantidad = clickHandler.getCantidad();
        cantidad++;
        protos.add(fabrica.protoboard());
        pane.getChildren().add(protos.get(cantidad -1));
        accion_presionar((protos.get(cantidad -1)));
    }

    @FXML
    private void desactivarOpciones() {
        Image image;
        conection linea = clickHandler.getlinea();
        if(linea !=null){
            clickHandler.get_manejarciruclo().eliminar_linea();
        }
        clickHandler.setResistencias(false);
        resistClicked = false;
        image = new Image("/resistencia.png"); // Imagen de la resistencia en estado inactivo
        resistenciaID.setImage(image);
        clickHandler.setLedClicked(false);
        ledClicked = false;
        image = new Image("/led.png"); // Imagen del led en estado inactivo
        ledID.setImage(image);
        clickHandler.setCableClicked(false);
        cableClicked=false;
        image = new Image("/cable.png"); // Imagen del cable en estado inactivo
        cableID.setImage(image);
        clickHandler.setEliminarProximaImagen(false);
        basureroActivo = false;
        image = new Image("/basurero.png"); // Imagen del basurero en estado inactivo
        basurero.setImage(image);
    }
}