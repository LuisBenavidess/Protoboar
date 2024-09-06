package com.example.protoboar;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Bateria {
    private final bus positivo = new bus();
    private final bus negativo;

    public Bateria(Pane pane) {
        this.positivo.setCenterX(593.0);
        this.positivo.setCenterY(480.0);
        this.positivo.setRadius(8.0);
        this.positivo.setFill(Color.RED);
        this.positivo.setCarga("+");
        this.negativo = new bus();
        this.negativo.setCenterX(645.0);
        this.negativo.setCenterY(480.0);
        this.negativo.setRadius(8.0);
        this.negativo.setFill(Color.BLUE);
        this.negativo.setCarga("-");
        pane.getChildren().add(this.positivo);
        pane.getChildren().add(this.negativo);
        this.positivo.setUserData(new busData("+"));
        this.negativo.setUserData(new busData("-"));
    }

    public bus getPositivo() {
        return this.positivo;
    }

    public bus getNegativo() {
        return this.negativo;
    }

    static class busData {
        String carga;
        busData(String carga) {
            this.carga = carga;
        }
    }
}
