package com.example.protoboar;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.util.Objects;

public class Switch {

    private String Carga;
    private final ImageView imageView;

    public Switch(Pane pane, double x, double y) {
        Image image = new Image("/Switch1.png");
        this.imageView = new ImageView(image);

        // Ajustar las dimensiones del ImageView
        imageView.setFitWidth(40);
        imageView.setFitHeight(20);

        // Posicionar el ImageView en las coordenadas dadas
        imageView.setX(x - imageView.getFitWidth() / 2);
        imageView.setY(y - imageView.getFitHeight() / 2);

        // Configurar el evento del basurero
        imageView.setOnMouseClicked(Click::eliminarElemento);
        // Configurar el evento de clic
        imageView.setOnMouseClicked(this::cambiarCarga);

        // Posicionar el switch en la ubicación adecuada
        pane.getChildren().add(imageView);
        imageView.toFront();

        this.Carga = "-";
    }

    public void cambiarCarga(MouseEvent event) {
        if(Carga == "-") {
            Encender();
        }else{
            Apagar();
        }
    }

    public void Encender() {
        Image image = new Image("/Switch1.png");
        this.imageView.setImage(image);
        this.Carga = "+";
    }

    public void Apagar() {
        Image image = new Image("/Switch2.png");
        this.imageView.setImage(image);
        this.Carga = "-";
    }

    public String getCarga() {
        return Carga;
    }

}
