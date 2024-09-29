package com.example.protoboar;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

//Controlador
public class HelloController {
    //Atributos
    @FXML
    private Pane pane;
    @FXML
    private int columnas;
    @FXML
    private int filas;
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
    private fabrica_proto fabrica;
    @FXML
    private ArrayList<Protoboard> protos;
    @FXML
    private double ratonx;
    @FXML
    private double ratony;

    private int cantidad;
    ///////////////////////////////77

    private double initialMouseX;
    private double initialMouseY;
    private double initialGroupX;
    private double initialGroupY;


    //Metodos
    @FXML
    private void initialize() {
        cantidad=0;
        protos = new ArrayList<>();
        fabrica = new fabrica_proto();
        protos.add(fabrica.protoboard());
        protos.get(cantidad).getBase().setOnMouseEntered(Click::presiona);
        pane.getChildren().add(protos.get(cantidad));
        // Configurar el clic en el basurero
        if (basurero != null) {
            basurero.setOnMouseClicked(this::borraBasura);
        }
        //System.out.println("Led falso");

        //Genera la matriz con los buses
        Bateria bateria = new Bateria(pane);
        clickHandler = new Click(pane, protos, ledClicked, cableClicked, bateria);
        bateria.getPositivo().setOnMouseClicked(clickHandler::presionarCirculo);
        bateria.getNegativo().setOnMouseClicked(clickHandler::presionarCirculo);




        //System.out.println("Etapa 2");
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
                //System.out.println("circulo: " + circulo);
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
            //
            //Se cambia el booleano para identificar si este esta prendido o apagado
            clickHandler.setLedClicked(true);
            System.out.println("Led true");
            ledClicked=true;
        } else{
            //Se cambia el booleano para identificar si este esta prendido o apagado
            clickHandler.setLedClicked(false);
            System.out.println("Led false");
            ledClicked=false;
        }
    }

    @FXML
    // Metodo que crea la imagen del cable y controla si esta activa o no
    private void crearCable() {
        if (cableClicked) {
            System.out.println("Cable false");
            //Se cambia el booleano para identificar si este esta prendido o apagado
            clickHandler.setCableClicked(false);
            cableClicked = false;
        } else {
            //Se cambia el booleano para identificar si este esta prendido o apagado
            clickHandler.setCableClicked(true);
            System.out.println("Cable true");
            cableClicked = true;
        }
    }



    @FXML
    //Metodo que llama a la funcion borrar basura atravez de un evento con la foto de el basurero
    private void borraBasura(MouseEvent event) {
        //llama a la funcion
        clickHandler.ClickEnBasurero();
    }

    /*@FXML
    //Metodo que inicia el proceso de verificar cargas atravez de los buses, cables y swich, esto se genera atravez del evento de presionar el boton
    public void iniciar() {
        System.out.println("paso");
        //Revovina todos los circulos a neutro(negro) para verificar de forma correcta
        clickHandler.revovinar();
        int i=0;
        // Verifica los cables y switch para trasladar la carga
        while(i<clickHandler.getCables().size() || i<clickHandler.getswitch().size()){
            System.out.println("Entro a verificar");
            clickHandler.verificar_cables();
            clickHandler.verificar_switch();
            i++;
        }
        i=0;
        //Verifica los leds
        while(i<clickHandler.getCables_led().size()){
            clickHandler.prender_led();
            i++;
        }
    }*/

    @FXML
    // Genera y controla la creacion de switch
    private void crearSwitch() {
        if(!switchClicked) {
            //Se cambia el booleano para identificar si este esta prendido o apagado
            clickHandler.setSwitchClicked(true);
            System.out.println("Switch true");
            switchClicked=true;
        } else{
            //Se cambia el booleano para identificar si este esta prendido o apagado
            clickHandler.setSwitchClicked(false);

            System.out.println("Switch false");
            switchClicked=false;
        }
    }

    @FXML
    private void crear_proto(ActionEvent event) {
        System.out.println("creo");
        protos.add(fabrica.protoboard());
        protos.get(cantidad).getBase().setOnMouseEntered(Click::presiona);
        pane.getChildren().add(protos.get(cantidad));
        accion_presionar((protos.get(cantidad)));
        cantidad++;

    }
}