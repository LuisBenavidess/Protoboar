package com.example.protoboar;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

import java.util.Set;

public class HelloController {
    @FXML
    private Label label;
    @FXML
    private Pane pane;

    private Led led;  // La instancia actual del LED
    private boolean circuloClicked = false;
    private boolean circulo1Clicked = false;

    private double circuloX, circuloY, circulo1X, circulo1Y;

    @FXML
    public void initialize() {
        Set<Node> circles = pane.lookupAll(".circle");

        for (Node circle : circles) {
            circle.setOnMouseClicked(this::handleCircleClick);
        }

        // Inicializar la instancia de Led en null para indicar que aún no se ha creado
        led = null;
    }

    private void handleCircleClick(MouseEvent event) {
        Circle clickedCircle = (Circle) event.getSource();

        // Mostrar el mensaje de clic en la consola
        System.out.println("Presionado");

        if (!circuloClicked) {
            circuloClicked = true;
            circuloX = event.getSceneX();
            circuloY = event.getSceneY();
        } else if (!circulo1Clicked) {
            circulo1Clicked = true;
            circulo1X = event.getSceneX();
            circulo1Y = event.getSceneY();
        }

        mostrarImagen();
    }

    @FXML
    private void mostrarImagen() {
        if (circuloClicked && circulo1Clicked) {
            double medioX = (circuloX + circulo1X) / 2;
            double medioY = (circuloY + circulo1Y) / 2;

            double localX = pane.sceneToLocal(medioX, medioY).getX();
            double localY = pane.sceneToLocal(medioX, medioY).getY();

            // Crear una nueva instancia de Led en la posición calculada
            new Led(pane, localX, localY);

            circuloClicked = false;
            circulo1Clicked = false;
        }
    }
}
