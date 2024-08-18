package com.example.protoboar;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Led {
    private ImageView imageView;
    private boolean conectado;

    public Led(Pane pane, double x, double y) {
        // Crear el ImageView con la imagen del LED
        Image image = new Image(getClass().getResourceAsStream("/Led-on.png"));
        this.imageView = new ImageView(image);
        this.imageView.setFitWidth(50);
        this.imageView.setFitHeight(50);
        this.imageView.setX(x - imageView.getFitWidth() / 2);
        this.imageView.setY(y - imageView.getFitHeight() / 2);

        // Agregar el ImageView al Pane
        pane.getChildren().add(this.imageView);

        this.conectado = false;
    }

    public void mostrarEnPosicion(double x, double y) {
        // Establecer la posición del LED
        imageView.setX(x - imageView.getFitWidth() / 2);
        imageView.setY(y - imageView.getFitHeight() / 2);
    }

    public void setConectado(boolean conectado) {
        this.conectado = conectado;
    }

    public boolean isConectado() {
        return conectado;
    }
}
