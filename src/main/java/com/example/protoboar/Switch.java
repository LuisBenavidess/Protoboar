package com.example.protoboar;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.util.Objects;

public class Switch {

    private String Carga;
    private final ImageView imageView;

    private bus c1;
    private bus c2;

    public Switch(Pane pane, double x, double y, bus c1, bus c2) {
        this.c1 = c1;
        this.c2 = c2;
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
        // Configurar el evento de click
        imageView.setOnMouseClicked(this::cambiarCarga);
        // Configurar el evento de quitarCarga
        imageView.setOnMouseClicked(this::trabajarCarga);

        // Posicionar el switch en la ubicación adecuada
        pane.getChildren().add(imageView);
        imageView.toFront();

        System.out.println("bus: "+c1.carga);
        this.Carga = "-";
    }

    public void cambiarCarga(MouseEvent event) {
        if(Carga == " ") {
            Encender();
        }else{
            Apagar();
        }
    }

    public void Encender() {
        Image image = new Image("/Switch1.png");
        this.imageView.setImage(image);
        this.Carga = "o";
    }

    public void Apagar() {
        Image image = new Image("/Switch2.png");
        this.imageView.setImage(image);
        this.Carga = "-";
    }

    public void quitarCarga() {
        Click.quitarCarga(c1.fila, c1.columna, c2.fila, c2.columna);
        Apagar();
    }

    public void darCarga() {
        Click.darCarga(c1.fila, c1.columna, c2.fila, c2.columna);
        Encender();
    }

    public void trabajarCarga(MouseEvent event) {
        if(Carga == "o") {
            quitarCarga();
        } else{
            darCarga();
        }
    }

    public String getCarga() {
        return Carga;
    }


}
