package com.example.protoboar;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Bateria {
    private final bus positivo;
    private final bus negativo;

    public Bateria(Pane pane) {
        // Crear el círculo positivo
        positivo = new bus();
        positivo.setCenterX(593);
        positivo.setCenterY(480);
        positivo.setRadius(8);
        positivo.setFill(Color.RED);
        positivo.setCarga("+");
        // Crear el círculo negativo
        negativo = new bus();
        negativo.setCenterX(645);
        negativo.setCenterY(480);
        negativo.setRadius(8);
        negativo.setFill(Color.BLUE);
        negativo.setCarga("-");
        pane.getChildren().add(positivo);
        pane.getChildren().add(negativo);
        positivo.setUserData(new busData("+"));
        negativo.setUserData(new busData("-"));
    }

    public bus getPositivo() {
        return positivo;
    }

    public bus getNegativo() {
        return negativo;
    }

    // Clase auxiliar para almacenar la información del bus
    static class busData {
        String carga;
        busData(String carga) {
            this.carga = carga;
        }
    }
}
