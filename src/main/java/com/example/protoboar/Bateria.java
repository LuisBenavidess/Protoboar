package com.example.protoboar;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.ArrayList;
import java.util.Objects;

public class Bateria {
    // Atributos
    private final bus positivo = new bus();
    private final bus negativo;
    private final ImageView batteryImagen;
    private double initialX;
    private double initialY;
    private final ArrayList<conection> cables;

    // Constructor
    public Bateria(Pane pane) {
        cables = new ArrayList<>();
        this.batteryImagen = new ImageView();
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/battery.png")));
        this.batteryImagen.setImage(image);
        this.batteryImagen.setFitHeight(100.0);
        this.batteryImagen.setFitWidth(100.0);
        this.batteryImagen.setLayoutX(568.0);
        this.batteryImagen.setLayoutY(468.0);
        pane.getChildren().add(this.batteryImagen);

        this.positivo.setCenterX(593.0);
        this.positivo.setCenterY(480.0);
        this.positivo.setRadius(8.0);
        this.positivo.setFill(Color.RED);
        this.positivo.setCarga("+");

        this.negativo = new bus();
        this.negativo.setCenterX(645.0);
        this.negativo.setCenterY(480.0);
        this.negativo.setRadius(8.0);
        this.negativo.setFill(Color.BLUE);
        this.negativo.setCarga("-");

        pane.getChildren().add(this.positivo);
        pane.getChildren().add(this.negativo);

        this.positivo.setUserData(new busData("+"));
        this.negativo.setUserData(new busData("-"));
        this.batteryImagen.setOnMousePressed(this::handleMousePressed);
        this.batteryImagen.setOnMouseDragged(this::handleMouseDragged);
    }

    private void handleMousePressed(MouseEvent event) {
        initialX = event.getX();
        initialY = event.getY();
    }

    private void handleMouseDragged(MouseEvent event) {
        double newX = batteryImagen.getLayoutX() + event.getX() - initialX;
        double newY = batteryImagen.getLayoutY() + event.getY() - initialY;
        batteryImagen.setLayoutX(newX);
        batteryImagen.setLayoutY(newY);
        // Actualizar posiciones de los buses
        positivo.setCenterX(newX + 25);
        positivo.setCenterY(newY + 12);
        negativo.setCenterX(newX + 75);
        negativo.setCenterY(newY + 12);
        // Actualizar los cables conectados a los buses
        updateCables();
    }

    public void addCable(conection cable) {
        cables.add(cable);
    }

    private void updateCables() {
        for (conection cable : cables) {
            System.out.println("xd");
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

    public bus getPositivo() {
        return this.positivo;
    }

    public bus getNegativo() {
        return this.negativo;
    }

    static class busData {
        String carga;
        busData(String carga) {
            this.carga = carga;
        }
    }
}

