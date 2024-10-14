package com.example.protoboar;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Bateria {
    // Atributos
    private final bus positivo = new bus();
    private final bus negativo;
    private final ImageView batteryImagen;
    private double initialX;
    private double initialY;
    private final List<conection> cablesConectados = new ArrayList<>();

    // Constructor
    public Bateria(Pane pane) {
        ArrayList<conection> cables = new ArrayList<>();
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
        this.positivo.voltaje = 9.0; // Voltaje activo de 9V
        this.negativo = new bus();
        this.negativo.setCenterX(645.0);
        this.negativo.setCenterY(480.0);
        this.negativo.setRadius(8.0);
        this.negativo.setFill(Color.BLUE);
        this.negativo.setCarga("-");
        this.negativo.voltaje = 0.0; // Voltaje activo de 0V

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
        double deltaX = batteryImagen.getLayoutX() + event.getX() - initialX;
        double deltaY = batteryImagen.getLayoutY() + event.getY() - initialY;
        batteryImagen.setLayoutX(deltaX);
        batteryImagen.setLayoutY(deltaY);
        // Actualizar posiciones de los buses
        positivo.setCenterX(deltaX + 25);
        positivo.setCenterY(deltaY + 12);
        negativo.setCenterX(deltaX + 75);
        negativo.setCenterY(deltaY + 12);
        // Actualizar los cables conectados a los buses
        for (conection cable : cablesConectados) {
            System.out.println("XD");
        }
    }

    // Metodo para añadir cables conectados
    public void addCable(conection cable) {
        cablesConectados.add(cable);
    }

    // Metodo para obtener los cables conectados
    public List<conection> getCablesConectados() {
        return cablesConectados;
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