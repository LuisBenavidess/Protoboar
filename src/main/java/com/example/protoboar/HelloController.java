package com.example.protoboar;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
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
    private void initialize() {
        // Configurar el clic en el basurero
        if (basurero != null) {
            basurero.setOnMouseClicked(this::borraBasura);
        }
        System.out.println("Led falso");
        alimentacion = new bus[14][30];
        Bateria bateria = new Bateria(pane);
        clickHandler = new Click(pane, alimentacion, ledClicked, cableClicked, bateria);
        bateria.getPositivo().setOnMouseClicked(clickHandler::presionarCirculo);
        bateria.getNegativo().setOnMouseClicked(clickHandler::presionarCirculo);
        crear_buses(37, 52, 2,true);
        crear_buses(37, 122, 5,false);
        crear_buses(37, 276, 5,false);
        crear_buses(37, 413, 2,false);
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
                }
                j++;
            }
            i++;
        }
    }

    @FXML
    //Funcion que crea los circulos
    private void crear_buses(int X, int Y, int FIL,boolean bandera) {
        // Variables a utilizar
        int carga=0;
        int col = 0;
        int fil = 0;
        int x = X;
        int y = Y;
        //bucle
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
                if(bandera){
                    if(carga==0){
                        alimentacion[filas][columnas].setCarga("-");
                    }else{
                        alimentacion[filas][columnas].setCarga("+");
                    }
                }else{
                    alimentacion[filas][columnas].setCarga(" ");
                }

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
    private void crearLed() {
        if(!ledClicked) {
            System.out.println("crear led");
            clickHandler.setLedClicked(true);
            System.out.println("Led true");
            ledClicked=true;
        } else{
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
    private void borraBasura(MouseEvent event) {
        clickHandler.ClickEnBasurero();
    }

    @FXML
    private void iniciar() {
        System.out.println("paso");
        clickHandler.revovinar();
        int i=0;
        while(i<clickHandler.getCables().size()){
            clickHandler.verificar_cables();
            clickHandler.verificar_switch();
            i++;
        }
        i=0;
        while(i<clickHandler.getCables_led().size()){
            clickHandler.prender_led();
            i++;
        }
    }

    @FXML
    private void crearSwitch() {
        if(!switchClicked) {
            System.out.println("Crear switch");
            clickHandler.setSwitchClicked(true);
            System.out.println("Switch true");
            switchClicked=true;
        } else{
            clickHandler.setSwitchClicked(false);
            System.out.println("Switch false");
            switchClicked=false;
        }
    }
}