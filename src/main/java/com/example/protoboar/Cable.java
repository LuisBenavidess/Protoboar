package com.example.protoboar;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import java.util.Objects;
//Esta clase es solo para crear la imagen de los cables
public class Cable {
    //Constructor
    public Cable(Pane pane, double x, double y) {
        // Crea la imagen del cable
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/cable.png")));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(50); // Ancho
        imageView.setFitHeight(50); // Alto
        imageView.setX(x - (imageView.getFitWidth() / 2));
        imageView.setY(y - (imageView.getFitHeight() / 2));
        // Agregar el ImageView al Pane
        pane.getChildren().add(imageView);
        imageView.toFront();
    }
}
