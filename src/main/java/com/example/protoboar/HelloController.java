package com.example.protoboar;

import javafx.fxml.FXML;
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
    private bus[][] alimentacion;
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
    private Motor motor;

    ///////////////////////////////77

    //Metodos
    @FXML
    private void initialize() {
        protos = new ArrayList<>();
        fabrica = new fabrica_proto();
        protos.add(fabrica.protoboard());
        protos.get(0).getBase().setOnMouseEntered(Click::presiona);
        pane.getChildren().add(protos.get(0));
        // Configurar el clic en el basurero
        if (basurero != null) {
            basurero.setOnMouseClicked(this::borraBasura);
        }

        //Genera la matriz con los buses
        alimentacion = new bus[14][30];
        Bateria bateria = new Bateria(pane);
        motor = new Motor(pane);

        clickHandler = new Click(pane, alimentacion, ledClicked, cableClicked, bateria, motor);
        bateria.getPositivo().setOnMouseClicked(clickHandler::presionarCirculo);
        bateria.getNegativo().setOnMouseClicked(clickHandler::presionarCirculo);
        motor.getPositivo().setOnMouseClicked(clickHandler::presionarCirculo);
        motor.getNegativo().setOnMouseClicked(clickHandler::presionarCirculo);
        //Funcion que llama el creado de los circulos(buses)
        crear_buses(37, 52, 2);
        crear_buses(37, 122, 5);
        crear_buses(37, 276, 5);
        crear_buses(37, 413, 2);

        //Viaja por la matriz para indentificar al circulo presionado
        int i = 0;
        while (i < 14) {
            int j = 0;
            while (j < 30) {
                bus circulo = alimentacion[i][j];
                if (circulo != null) {
                    circulo.setOnMouseClicked(clickHandler::presionarCirculo);
                }
                j++;
            }
            i++;
        }
    }

    @FXML
    //Funcion que crea los circulos
    private void crear_buses(int X, int Y, int FIL) {
        // Variables a utilizar
        int carga=0;
        int col = 0;
        int fil = 0;
        int x = X;
        int y = Y;
        //bucle que viaja atravez de la matriz alimentacion generando buses con su respectiva posicion
        while (fil < FIL) {
            while (col < 30) {
                //circulo
                bus circulo = new bus();
                circulo.setCenterX(x);
                circulo.setCenterY(y);
                circulo.setRadius(6);
                circulo.setFill(Color.BLACK);

                //Guardar el circulo dentro de la matriz
                alimentacion[filas][columnas] = circulo;
                alimentacion[filas][columnas].setFila(filas);
                alimentacion[filas][columnas].setColumna(columnas);
                alimentacion[filas][columnas].setCarga(" ");
                //Agregar
                pane.getChildren().add(circulo);
                x = x + 18;
                col++;
                columnas++;
                if (col == 30) {
                    fil = fil + 1;
                    filas = filas + 1;
                    x = 37;
                    y = y + 22;
                    carga = carga + 1;
                }
            }
            col = 0;
            columnas = 0;
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

    @FXML
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
    }

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
    private void toggleMotor() {
        if (motor.isEncendido()) {
            motor.apagarMotor(); // Apagar el motor si está encendido
            System.out.println("Motor apagado");
        } else {
            motor.encenderMotor(); // Encender el motor si está apagado
            System.out.println("Motor encendido");
        }
    }
}