package com.example.protoboar;

import javafx.fxml.FXML;
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
    private ImageView basurero;
    @FXML
    private boolean basureroActivo = false;
    private int cantidad;
    ///////////////////////////////



    //Metodos
    @FXML
    private void initialize() {
        cantidad=0;
        ArrayList<Protoboard> protos = new ArrayList<>();
        fabrica_proto fabrica = new fabrica_proto();
        protos.add(fabrica.protoboard());
        protos.get(cantidad).getBase().setOnMouseEntered(Click::presiona);
        pane.getChildren().add(protos.get(cantidad));
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
        cantidad++;
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
        if(!ledClicked) {
            desactivarOpciones();
            //Se cambia el booleano para identificar si este esta prendido o apagado
            clickHandler.setLedClicked(true);
            ledClicked=true;
        } else{
            //Se cambia el booleano para identificar si este esta prendido o apagado
            clickHandler.setLedClicked(false);
            ledClicked=false;
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
            //Se cambia el booleano para identificar si este esta prendido o apagado
            clickHandler.setCableClicked(true);
            cableClicked = true;
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
    // Genera y controla la creacion de switch
    private void crearSwitch() {
        if(!switchClicked) {
            desactivarOpciones();
            //Se cambia el booleano para identificar si este esta prendido o apagado
            clickHandler.setSwitchClicked(true);
            switchClicked=true;
        } else{
            //Se cambia el booleano para identificar si este esta prendido o apagado
            clickHandler.setSwitchClicked(false);
            switchClicked=false;
        }
    }

    private void desactivarOpciones() {
        clickHandler.setLedClicked(false);
        ledClicked = false;
        System.out.println("Led false");
        System.out.println("Switch false");
        switchClicked=false;
        clickHandler.setCableClicked(false);
        cableClicked = false;
        System.out.println("Cable false");
        clickHandler.setSwitchClicked(false);
        switchClicked = false;
        System.out.println("Switch false");
        basureroActivo = false;
        Image image = new Image("/basurero.png"); // Imagen del basurero en estado inactivo
        basurero.setImage(image);
        System.out.println("Basurero false");
    }
}