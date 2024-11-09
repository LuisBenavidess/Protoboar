package com.example.protoboar;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.util.ArrayList;

//Controlador
public class HelloController {
    //Atributos
    @FXML
    private Pane pane;
    @FXML
    private Click clickHandler;
    @FXML
    private boolean ledClicked=false;
    @FXML
    private boolean cableClicked=false;
    @FXML
    private boolean switchClicked;
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
    private Motor motor;
    @FXML
    private Fabrica_Proto fabrica;
    @FXML
    private ArrayList<Protoboard> protos;
    @FXML
    private boolean basureroActivo = false;

    private conection linea;

    ///////////////////////////////
    private int cantidad;


    //Metodos
    @FXML
    private void initialize() {


        int cantidad = 0;
        protos = new ArrayList<>();
        fabrica = new Fabrica_Proto();
        protos.add(fabrica.protoboard());
        protos.get(cantidad).getBase().setOnMouseEntered(Click::presiona);
        pane.getChildren().add(protos.get(cantidad));
        //pane.getChildren().add(grupo_principal);
        // Configurar el clic en el basurero
        if (basurero != null) {
            basurero.setOnMouseClicked(this::activarBasurero);
        }

        //Genera la matriz con los buses
        Bateria bateria = new Bateria(pane);
        Motor motor = new Motor(pane);
        clickHandler = new Click(pane, protos, ledClicked, cableClicked,bateria, motor);

        bateria.getPositivo().setOnMouseClicked(clickHandler::presionarCirculo);
        bateria.getNegativo().setOnMouseClicked(clickHandler::presionarCirculo);
        motor.getPositivo().setOnMouseClicked(clickHandler::presionarCirculo);
        motor.getNegativo().setOnMouseClicked(clickHandler::presionarCirculo);

        accion_presionar((protos.get(cantidad)));
        cantidad= cantidad+1;

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
    private void crearLed() {
        if(ledClicked) {
            //Se cambia el booleano para identificar si este esta prendido o apagado
            clickHandler.setLedClicked(false);
            ledClicked=false;
        } else{
            desactivarOpciones();
            Image image = new Image("/ledOn.png"); // Imagen del led en estado inactivo
            ledID.setImage(image);

            //Se cambia el booleano para identificar si este esta prendido o apagado
            clickHandler.setLedClicked(true);
            ledClicked=true;
        }
    }

    @FXML
    // Metodo que crea la imagen del cable y controla si esta activa o no
    private void crearCable() {
        if (cableClicked) {
            //Se cambia el booleano para identificar si este esta prendido o apagado
            clickHandler.setCableClicked(false);
            cableClicked = false;
        } else {
            desactivarOpciones();
            Image image = new Image("/cableOn.png"); // Imagen del cable en estado inactivo
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
            //Se cambia el booleano para identificar si este esta prendido o apagado
            clickHandler.setResistencias(false);
            System.out.println("Resistencia false");
            resistClicked=false;
        } else{
            desactivarOpciones();
            Image image = new Image(getClass().getResource("/resistenciaOn.png").toExternalForm());
            resistenciaID.setImage(image); // Imagen del resistencia en estado inactivo


            //Se cambia el booleano para identificar si este esta prendido o apagado
            clickHandler.setResistencias(true);
            System.out.println("Resitencia true");
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
    // Controla la creacion de switch
    private void crearSwitch() {
        if(!switchClicked) {
            desactivarOpciones();
            //Se cambia el booleano para identificar si este esta prendido o apagado
            clickHandler.setSwitchClicked(true);
            switchClicked=true;
        } else{
            desactivarOpciones();
            //Se cambia el booleano para identificar si este esta prendido o apagado
            clickHandler.setSwitchClicked(false);
            System.out.println("Switch false");
            switchClicked=false;
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
    private void crearSwitch3x3(MouseEvent event) {
        desactivarOpciones();
        clickHandler.CrearSwitch3x3();
    }

    @FXML
    private void toggleMotor() {
        if (motor.isEncendido()) {
            motor.apagarMotor(); // Apagar el motor si está encendido
            System.out.println("Motor apagado");
        } else {
            motor.encenderMotor(); // Encender el motor si está apagado
            System.out.println("Motor encendido");
        }
    }

    @FXML
    private void crear_proto(ActionEvent event) {
        cantidad= clickHandler.getCantidad();
        cantidad++;
        System.out.println(cantidad-1);
        System.out.println("creo");
        protos.add(fabrica.protoboard());
        protos.get(cantidad-1).getBase().setOnMouseEntered(Click::presiona);
        pane.getChildren().add(protos.get(cantidad-1));
        accion_presionar((protos.get(cantidad-1)));
        //cantidad++;

    }

    @FXML
    private void desactivarOpciones() {

        Image image;
        linea=clickHandler.getlinea();
        if(linea!=null){
            clickHandler.get_manejarciruclo().eliminar_linea();
        }
        clickHandler.setResistencias(false);
        resistClicked = false;
        image = new Image("/resistencia2.png"); // Imagen de la resistencia en estado inactivo
        resistenciaID.setImage(image);
        System.out.println("Resistencia false");

        clickHandler.setLedClicked(false);
        ledClicked = false;
        image = new Image("/led.png"); // Imagen del led en estado inactivo
        ledID.setImage(image);
        System.out.println("Led false");

        clickHandler.setCableClicked(false);
        cableClicked=false;
        image = new Image("/cable.png"); // Imagen del cable en estado inactivo
        cableID.setImage(image);
        System.out.println("Cable false");

        clickHandler.setEliminarProximaImagen(false);
        basureroActivo = false;
        image = new Image("/basurero.png"); // Imagen del basurero en estado inactivo
        basurero.setImage(image);
        System.out.println("Basurero false");

        /*clickHandler.setSwitchClicked(false);
        switchClicked = false;
        System.out.println("Switch false");*/

    }
}