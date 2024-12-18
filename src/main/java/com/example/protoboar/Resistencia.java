package com.example.protoboar;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Resistencia extends Node{
    private final String imagen;
    public boolean quemado = false;
    private final ImageView imageView;
    private final conection cables;
    public boolean cargado = false;

    public Resistencia(Group group, double x, double y, conection cables) {
        this.cables = cables;

        //If para decidir la imagen que corresponde
        if(cables.getInicio().getCenterX() > cables.getFin().getCenterX()){
            Image image = new Image("/resistencia4.png");
            this.imageView = new ImageView(image);
            imagen = "resistencia4.png";
            // Ajustar las dimensiones del ImageView
            imageView.setFitWidth(25);
            imageView.setFitHeight(40);
        }else{
            if(cables.getInicio().getCenterX() < cables.getFin().getCenterX()){
                Image image = new Image("/resistencia1.png");
                this.imageView = new ImageView(image);
                imagen = "resistencia1.png";
                // Ajustar las dimensiones del ImageView
                imageView.setFitWidth(25);
                imageView.setFitHeight(40);
            }else{
                if(cables.getInicio().getCenterY() > cables.getFin().getCenterY()){
                    Image image = new Image("/resistencia3.png");
                    this.imageView = new ImageView(image);
                    imagen = "resistencia3.png";
                    // Ajustar las dimensiones del ImageView
                    imageView.setFitWidth(40);
                    imageView.setFitHeight(25);
                }else{
                    Image image = new Image("/resistencia2.png");
                    this.imageView = new ImageView(image);
                    imagen = "resistencia2.png";
                    // Ajustar las dimensiones del ImageView
                    imageView.setFitWidth(40);
                    imageView.setFitHeight(25);
                }
            }
        }
        // Posicionar el ImageView en las coordenadas dadas
        imageView.setX(x - imageView.getFitWidth() / 2);
        imageView.setY(y - imageView.getFitHeight() / 2);

        // Configurar el evento del basurero
        imageView.setOnMousePressed(Click::eliminarElemento);

        group.getChildren().add(imageView);
        imageView.toFront();
    }

    public conection getCable() {
        return cables;
    }

    public ImageView getImageView(){
        return imageView;
    }

    public String getImagen() {
        return imagen;
    }

}