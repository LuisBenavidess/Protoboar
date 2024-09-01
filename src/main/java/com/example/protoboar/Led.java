package com.example.protoboar;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import java.util.Objects;

public class Led {

    private boolean conectado;
    private ImageView imageView;
    public Led(Pane pane, double x, double y) {
        // Crea la imagen del LED
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/led-off.png")));
        imageView = new ImageView(image);

        imageView.setFitWidth(30); // Ajusta el ancho si es necesario
        imageView.setFitHeight(50); // Ajusta el alto si es necesario
        imageView.setX(x - (imageView.getFitWidth() / 2));
        imageView.setY(y - (imageView.getFitHeight() / 2));

        // Agrega la función de ser borrado con el basurero
        imageView.setOnMouseClicked(Click::eliminarElemento);

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

    public void prender(){
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/led-on.png")));
        imageView.setImage(image);
    }

    public void apagar(){
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/led-off.png")));
        imageView.setImage(image);
    }

}
