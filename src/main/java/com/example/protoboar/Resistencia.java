package com.example.protoboar;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Resistencia extends Node{
    //Atributos
    private String Reduccion;

    private final ImageView imageView;

    private final conection cables;

    public Resistencia(Group group, double x, double y, conection cables) {
        this.cables = cables;
        Image image = new Image("/resistencia1.png");
        this.imageView = new ImageView(image);
        this.Reduccion = "10";

        // Ajustar las dimensiones del ImageView
        imageView.setFitWidth(40);
        imageView.setFitHeight(20);

        // Posicionar el ImageView en las coordenadas dadas
        imageView.setX(x - imageView.getFitWidth() / 2);
        imageView.setY(y - imageView.getFitHeight() / 2);

        // Configurar el evento del basurero
        imageView.setOnMousePressed(Click::eliminarElemento);

        group.getChildren().add(imageView);
        imageView.toFront();


    }

    public String getReduccion() {
        return Reduccion;
    }

    public conection getCable() {
        return cables;
    }

    public ImageView getImageView(){
        return imageView;
    }

}
