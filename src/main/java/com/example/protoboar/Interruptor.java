package com.example.protoboar;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class Interruptor extends Pane {
    private boolean encendido = true;
    private ImageView Imagen;
    private int x;
    private int y;
    private bus bus1;
    private bus bus2;
    Interruptor(int x, int y, bus bus1, bus bus2) {
        this.x = x;
        this.y = y;
        this.bus1 = bus1;
        this.bus2 = bus2;

        Image image = new Image("Switch1.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(25);
        imageView.setFitWidth(12);
        imageView.setX(x);
        imageView.setY(y);

        this.getChildren().add(imageView);
        this.Imagen = imageView;

        imageView.setOnMouseClicked(this::cambiarCarga);
    }

    public void cambiarCarga(MouseEvent event) {
        Image image;
        if (!encendido) {
            image = new Image("/Switch1.png");
            encendido = true;
        } else {
            image = new Image("/Switch2.png");
            encendido = false;

        }
        Imagen.setImage(image);
        Imagen.toFront();
        Click.iniciar();
    }

    public void setEncendido(boolean encendido) {
        this.encendido = encendido;
    }

    public boolean getEncendido() {
        return encendido;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public ImageView getImagen() {
        return Imagen;
    }

}
