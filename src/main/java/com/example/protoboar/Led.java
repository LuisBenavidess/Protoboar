package com.example.protoboar;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import java.util.Objects;

public class Led {
    private final ImageView imageView;
    private boolean conectado;

    public Led(Pane pane, double x, double y) {
        // Crear el ImageView con la imagen del LED
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/led-on.png")));
        this.imageView = new ImageView(image);
        this.imageView.setFitWidth(50); // Ajusta el ancho si es necesario
        this.imageView.setFitHeight(50); // Ajusta el alto si es necesario

        // Ajustar la posición del LED para centrarlo en las coordenadas especificadas
        // Nota: Asegúrate de que el offset es correcto
        this.imageView.setX(x - (this.imageView.getFitWidth() / 2));
        this.imageView.setY(y - (this.imageView.getFitHeight() / 2));

        // Agregar el ImageView al Pane
        pane.getChildren().add(this.imageView);

        // Asegúrate de que el LED esté encima de otros elementos
        this.imageView.toFront();
        this.imageView.setStyle("-fx-border-color: red; -fx-border-width: 2;"); // Opcional para depuración

        this.conectado = false;
    }

    public void mostrarEnPosicion(double x, double y) {
        // Ajusta la posición del LED para centrarlo en las coordenadas especificadas
        this.imageView.setX(x - (this.imageView.getFitWidth() / 2));
        this.imageView.setY(y - (this.imageView.getFitHeight() / 2));
    }

    public void setConectado(boolean conectado) {
        this.conectado = conectado;
    }

    public boolean isConectado() {
        return conectado;
    }
}
