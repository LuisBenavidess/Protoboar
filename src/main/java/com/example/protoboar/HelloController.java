package com.example.protoboar;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.image.ImageView;

public class HelloController {
    @FXML
    private Label label;
    @FXML
    private Circle circulo;

    @FXML
    private Circle circulo1;

    @FXML
    private ImageView led;

    @FXML
    private double x;

    @FXML
    private double y;

    @FXML
    private Pane pane;

    @FXML
    public void initialize() {
        
        circulo.setOnMouseClicked(this::presionar_circulo);
        circulo1.setOnMouseClicked(this::presionar_circulo);

    }

    private void pos_ini(MouseEvent event) {
        led.setUserData(new double[]{event.getSceneX(), event.getSceneY()});
    }


    @FXML
    protected void presionar_circulo(MouseEvent event) {
         System.out.println("Pressionado");


    }
    @FXML
    protected void mover_foto(MouseEvent event) {

    }
    @FXML
    protected void presionar(){
        label.setText("En proceso");
    }
}