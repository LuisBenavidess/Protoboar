package com.example.protoboar;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import java.util.Objects;
//Clase que genera la imagen del led
public class Led {
    //Atributo
    private final ImageView imageView;

    //Constructor
    public Led(Protoboard proto, double x, double y) {
        // Crea la imagen del LED
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/led-off.png")));
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

    //Funcion que cambia la imagen dependiendo si este esta prendido o apagado
    public void prender(){
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/led-on.png")));
        imageView.setImage(image);
    }

    public void apagar(){
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/led-off.png")));
        imageView.setImage(image);
    }

}
