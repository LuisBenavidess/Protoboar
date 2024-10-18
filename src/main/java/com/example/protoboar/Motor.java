package com.example.protoboar;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Motor {
    // Atributos
    private final bus positivo = new bus();
    private final bus negativo = new bus();
    private boolean encendido = false; // Estado del motor
    private final ImageView motorImagen;
    private final Button encenderButton; // Botón para encender el motor
    private double offsetX;
    private double offsetY;
    private final List<conection> cablesConectados = new ArrayList<>(); // Lista de cables conectados

    // Constructor
    public Motor(Pane pane) {
        this.motorImagen = new ImageView();
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/motor.png")));
        this.motorImagen.setImage(image);
        this.motorImagen.setFitHeight(100.0);
        this.motorImagen.setFitWidth(100.0);
        this.motorImagen.setLayoutX(668.0);
        this.motorImagen.setLayoutY(268.0);
        pane.getChildren().add(this.motorImagen);

        // Inicialización del botón
        this.encenderButton = new Button("Encender");
        this.encenderButton.setLayoutX(688.0);
        this.encenderButton.setLayoutY(350.0);
        this.encenderButton.setOnAction(_ -> toggleMotor()); // clic
        pane.getChildren().add(this.encenderButton);

        // Círculo positivo
        this.positivo.setCenterX(690.0);
        this.positivo.setCenterY(308.0);
        this.positivo.setRadius(8.0);
        this.positivo.setFill(Color.BLACK);
        this.positivo.setCarga(" "); // Sin carga

        // Círculo negativo
        this.negativo.setCenterX(690.0);
        this.negativo.setCenterY(338.0);
        this.negativo.setRadius(8.0);
        this.negativo.setFill(Color.BLACK);
        this.negativo.setCarga(" "); // Sin carga

        pane.getChildren().add(this.positivo);
        pane.getChildren().add(this.negativo);

        this.motorImagen.setOnMousePressed(this::handleMousePressed);
        this.motorImagen.setOnMouseDragged(this::handleMouseDragged);
    }

    // Manejo del clic
    private void handleMousePressed(MouseEvent event) {
        offsetX = event.getX();
        offsetY = event.getY();
    }

    // Manejo del arrastre
    private void handleMouseDragged(MouseEvent event) {
        // Posición actual de la imagen
        double newX = motorImagen.getLayoutX() + event.getX() - offsetX;
        double newY = motorImagen.getLayoutY() + event.getY() - offsetY;
        // Mueve la imagen del motor
        motorImagen.setLayoutX(newX);
        motorImagen.setLayoutY(newY);
        // Mueve el botón de encendido
        encenderButton.setLayoutX(newX + 20);
        encenderButton.setLayoutY(newY + 80);
        // Mueve los buses
        positivo.setCenterX(newX + 15);
        positivo.setCenterY(newY + 40);
        negativo.setCenterX(newX + 15);
        negativo.setCenterY(newY + 70);

        // Actualizar posiciones de los cables conectados
        for (conection cable : cablesConectados) {
            if (cable.getInicio() == positivo) {
                cable.setStartX(positivo.getCenterX());
                cable.setStartY(positivo.getCenterY());
            } else if (cable.getInicio() == negativo) {
                cable.setStartX(negativo.getCenterX());
                cable.setStartY(negativo.getCenterY());
            }
            if (cable.getFin() == positivo) {
                cable.setEndX(positivo.getCenterX());
                cable.setEndY(positivo.getCenterY());
            } else if (cable.getFin() == negativo) {
                cable.setEndX(negativo.getCenterX());
                cable.setEndY(negativo.getCenterY());
            }
        }
    }

    public void addCable(conection cable) {
        cablesConectados.add(cable);
    }

    // Alternar el motor
    private void toggleMotor() {
        if (encendido) {
            apagarMotor();
        } else {
            encenderMotor();
        }
    }

    // Encender/apagar el motor
    public void encenderMotor() {
        if (!encendido) {
            encendido = true;
            distribuirCargas();
        }
    }

    public void apagarMotor() {
        if (encendido) {
            encendido = false;
            removerCargas();
        }
    }

    // Distribuir cargas cuando el motor esté encendido/apagado
    private void distribuirCargas() {
        positivo.setFill(Color.RED);
        positivo.setCarga("+");
        negativo.setFill(Color.BLUE);
        negativo.setCarga("-");
    }

    private void removerCargas() {
        positivo.setFill(Color.BLACK);
        positivo.setCarga(" ");
        negativo.setFill(Color.BLACK);
        negativo.setCarga(" ");
    }

    public bus getPositivo() {
        return this.positivo;
    }

    public bus getNegativo() {
        return this.negativo;
    }

    public boolean isEncendido() {
        return encendido;
    }
}
