package com.example.protoboar;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.util.Objects;
//Clase del Switch
public class Switch extends Node {
    //Atributos
    private String Carga;

    private final ImageView imageView;

    private final conection cables;

    //Constructor
    public Switch(Group group, double x, double y, conection cables) {
        this.cables = cables;
        Image image = new Image("/Switch1.png");
        this.imageView = new ImageView(image);

        // Ajustar las dimensiones del ImageView
        imageView.setFitWidth(40);
        imageView.setFitHeight(20);

        // Posicionar el ImageView en las coordenadas dadas
        imageView.setX(x - imageView.getFitWidth() / 2);
        imageView.setY(y - imageView.getFitHeight() / 2);

        // Configurar el evento del basurero
        imageView.setOnMousePressed(Click::eliminarElemento);
        //imageView.setOnMouseReleased(Click::presiona);
        // Configurar el evento de click
        this.Carga = "+";
        imageView.setOnMouseClicked(this::cambiarCarga);
        // imageView.setOnTouchPressed((Click::presiona));
        // Posicionar el switch en la ubicación adecuada
        group.getChildren().add(imageView);
        imageView.toFront();

    }

    //Metodos

    // get y set

    public String getCarga() {
        return Carga;
    }

    public conection getCable() {
        return cables;
    }

    public ImageView getImageView(){
        return imageView;
    }
    ///////////////////////////7

    //Metodo que recibe el evento de apretar el switch y este cambia la carga y la imagen
    public void cambiarCarga(MouseEvent event) {

        if(Objects.equals(Carga, "+")) {
            Apagar();
            System.out.println("apagado");
        }else{
            Encender();
            System.out.println("prendido");
        }
        Click.iniciar();

    }

    //Metodo que cambia la carga a positivo y cambia la imagen a 1
    public void Encender() {
        Image image = new Image("/Switch1.png");
        this.imageView.setImage(image);
        imageView.toFront();
        this.Carga = "+";
    }

    //Metodo que cambia la carga a negativo y cambia la imagen a 2
    public void Apagar() {
        Image image = new Image("/Switch2.png");
        this.imageView.setImage(image);
        imageView.toFront();
        this.Carga = "-";
    }

}
