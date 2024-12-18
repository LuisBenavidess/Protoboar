package com.example.protoboar;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.Objects;
//Clase que genera la imagen del led
public class Led extends Node{
    //Atributo
    private final ImageView imageView;
    private boolean quemado=false;
    private conection cable_rojo;
    private conection cable_azul;
    private String color;
    private boolean terminado=false;

    //Constructor
    public Led(Protoboard proto, double x, double y) {   // Crea la imagen del LED
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/LedOff.png")));
        imageView = new ImageView(image);
        imageView.setFitWidth(30); // Ancho
        imageView.setFitHeight(50); // Alto
        imageView.setX(x - (imageView.getFitWidth() / 2));
        imageView.setY(y - (imageView.getFitHeight() / 2));
        // Agrega la función de ser borrado con el basurero
        imageView.setOnMouseClicked(Click::eliminarElemento);
        // Agregar el ImageView al Pane
        proto.getChildren().add(imageView);
        imageView.toFront();
    }

    // set
    public void setConectado() {
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setTerminado(boolean terminado) {
        this.terminado = terminado;
    }

    public boolean getTerminado() {
        return terminado;
    }

    //Funcion que cambia la imagen dependiendo si este esta prendido o apagado
    public void prender(){
        if(quemado){
            return;
        }
        switch (color) {
            case "red" -> {
                Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/LedRojo.png")));
                imageView.setImage(image);
                imageView.toFront();
            }
            case "blue" -> {
                Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/LedAzul.png")));
                imageView.setImage(image);
                imageView.toFront();
            }
            case "green" -> {
                Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/LedVerde.png")));
                imageView.setImage(image);
                imageView.toFront();
            }
            case "purple" -> {
                Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/LedMorado.png")));
                imageView.setImage(image);
                imageView.toFront();
            }
            case "pink" -> {
                Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/LedRosado.png")));
                imageView.setImage(image);
                imageView.toFront();
            }
        }
    }

    public void apagar(){
        if(quemado){
            return;
        }
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/LedOff.png")));
        imageView.setImage(image);
        imageView.toFront();
    }

    public void setQuemado(boolean quemado) {
        this.quemado = quemado;
        if (quemado) {
            quemar();
        }
    }

    public boolean getQuemado() {
        return quemado;
    }

    public void quemar() {
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/led-dead.png")));
        imageView.setImage(image);
        imageView.toFront();
    }

    public void setCable_rojo(conection cable_rojo) {
        this.cable_rojo = cable_rojo;
    }

    public void setCable_azul(conection cable_azul) {
        this.cable_azul = cable_azul;
    }

    public conection getCable_rojo() {
        return cable_rojo;
    }

    public conection getCable_azul() {
        return cable_azul;
    }

    public ImageView getImageView() {
        return imageView;
    }
}