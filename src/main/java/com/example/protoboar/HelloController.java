package com.example.protoboar;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class HelloController {
    @FXML
    private Pane pane;
    @FXML
    private bus[][] alimentacion;
    @FXML
    private int columnas;
    @FXML
    private int filas;
    @FXML
    private Label label;
    @FXML
    private Line linea;
    @FXML
    private ImageView led;
    @FXML
    private ImageView cable;
    @FXML
    private double x;
    @FXML
    private double y;
    @FXML
    private Click clickHandler;
    private boolean ledClicked=false;
    private boolean cableClicked=false;
    private boolean circuloClicked = false;


    @FXML
    private double circuloX, circuloY;

    @FXML
    private ImageView basurero;

    @FXML
    private void initialize() {
        // Configurar el clic en el basurero
        if (basurero != null) {
            basurero.setOnMouseClicked(this::borraBasura);
        }
        System.out.println("Led falso");
        alimentacion = new bus[14][30];
        clickHandler = new Click(pane, label, led, alimentacion, ledClicked, cableClicked);
        crear_buses(37, 52, 2);
        crear_buses(37, 122, 5);
        crear_buses(37, 276, 5);
        crear_buses(37, 413, 2);
        numeros(33, 376);
        numeros(33, 100);
        System.out.println("Etapa 2");
        int i = 0;
        while (i < 14) {
            int j = 0;
            while (j < 30) {
                bus circulo = alimentacion[i][j];
                System.out.println("circulo: " + circulo);
                if (circulo != null) {
                    circulo.setOnMouseClicked(clickHandler::presionarCirculo);
                    circulo.setOnMouseEntered(event -> circulo.setFill(Color.RED));
                    circulo.setOnMouseExited(event -> circulo.setFill(Color.BLACK));
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
        int col = 0;
        int fil = 0;
        int x = X;
        int y = Y;

        //bucle
        while (fil < FIL) {
            col = 0;

            while (col < 29) {
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
                pane.getChildren().add(circulo);
                x = x + 18;
                col++;
                columnas++;
                if (col == 29) {
                    fil = fil + 1;
                    filas = filas + 1;
                    x = 37;
                    y = y + 22;
                }
            }
            col = 0;
            columnas = 0;
        }

    }

    @FXML
    private void crearLed() {
        if(!ledClicked) {
            System.out.println("crear led");
            clickHandler.setLedClicked(true);
            System.out.println("Led true");
            ledClicked=true;
        } else if (ledClicked){
            clickHandler.setLedClicked(false);
            System.out.println("Led false");
            ledClicked=false;
        }
    }

    @FXML
    private void crearCable() {
        if (cableClicked) {
            System.out.println("Cable false");
            clickHandler.setCableClicked(false);
            cableClicked = false;
        } else {
            System.out.println("crear cable");
            clickHandler.setCableClicked(true);
            System.out.println("Cable true");
            cableClicked = true;
        }
    }

    @FXML
    //Funcion que crea los numeros
    private void numeros(int X, int Y) {
        int i = 0;
        while (i < 30) {

            Label label = new Label(String.valueOf(i + 1));
            label.setLayoutY(Y);
            label.setLayoutX(X);
            label.setRotate(-90);
            pane.getChildren().add(label);
            X = X + 18;
            i++;
        }
    }

    @FXML
    private void inicio(MouseEvent event) {
        clickHandler.inicio(event);
    }

    @FXML
    private void movimiento(MouseEvent event) {
        clickHandler.movimiento(event);
    }

    @FXML
    private void parar(MouseEvent event) {
        clickHandler.parar(event);
    }

    @FXML
    private void borraBasura(MouseEvent event) {
        clickHandler.manejarClickEnBasurero(event);
    }

    // Método que asignas a los LEDs y cables
    @FXML
    private void asignarManejadorEliminar(ImageView elemento) {
        elemento.setOnMouseClicked(event -> clickHandler.eliminarElemento(event));
    }

}