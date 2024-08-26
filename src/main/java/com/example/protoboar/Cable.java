package com.example.protoboar;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import java.util.Objects;

public class Cable {
    private final ImageView imageView;
    private boolean conectado;

    public Cable(Pane pane, double x, double y) {
        // Crea la imagen del cable
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/cable.png")));
        this.imageView = new ImageView(image);
        imageView.setFitWidth(50); // Ajusta el ancho si es necesario
        imageView.setFitHeight(50); // Ajusta el alto si es necesario
        imageView.setX(x - (imageView.getFitWidth() / 2));
        imageView.setY(y - (imageView.getFitHeight() / 2));
        // Agregar el ImageView al Pane
        pane.getChildren().add(imageView);

        imageView.toFront();

        this.conectado = false;
    }

    public void setConectado(boolean conectado) {
        this.conectado = conectado;
    }

    public boolean isConectado() {
        return conectado;
    }
    public ImageView getImageView() {
        return imageView;
    }
}
