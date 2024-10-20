package com.example.protoboar;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import java.util.Objects;

public class Cable {
    private ImageView imageView;

    // Constructor
    public Cable(Pane pane, conection cable) {
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/cable.png")));
        imageView = new ImageView(image);
        imageView.setFitWidth(50); // Ancho
        imageView.setFitHeight(50); // Alto
        actualizarImagen(cable);
        pane.getChildren().add(imageView);
        imageView.toFront();
    }

    // Método para actualizar la posición de la imagen del cable
    public void actualizarImagen(conection cable) {
        double startX = cable.getStartX();
        double startY = cable.getStartY();
        double endX = cable.getEndX();
        double endY = cable.getEndY();

        // Calcular el ángulo y la longitud para rotar la imagen si es necesario
        double angle = Math.toDegrees(Math.atan2(endY - startY, endX - startX));
        imageView.setRotate(angle);

        // Actualizar la posición del ImageView
        double centerX = (startX + endX) / 2;
        double centerY = (startY + endY) / 2;
        imageView.setX(centerX - (imageView.getFitWidth() / 2));
        imageView.setY(centerY - (imageView.getFitHeight() / 2));
    }
}
