package com.example.protoboar;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

// Clase que genera el motor
public class Motor {
    // Atributos
    private final bus positivo = new bus();
    private final bus negativo = new bus();
    private final bus estado = new bus();
    private boolean encendido = false; // Estado del motor apagado por defecto

    // Constructor
    public Motor(Pane pane) {
        // Círculo positivo
        this.positivo.setCenterX(440.0);
        this.positivo.setCenterY(485.0);
        this.positivo.setRadius(8.0);
        this.positivo.setFill(Color.BLACK); // Inicialmente sin color
        this.positivo.setCarga(" "); // Sin carga inicialmente

        // Círculo negativo
        this.negativo.setCenterX(440.0);
        this.negativo.setCenterY(515.0);
        this.negativo.setRadius(8.0);
        this.negativo.setFill(Color.BLACK); // Inicialmente sin color
        this.negativo.setCarga(" "); // Sin carga inicialmente

        pane.getChildren().add(this.positivo);
        pane.getChildren().add(this.negativo);

        // Inicializar el estado del motor
        this.estado.setCenterX(520);
        this.estado.setCenterY(495);
        this.estado.setRadius(8.0);
        this.estado.setFill(Color.BLACK); // Inicialmente apagado
        pane.getChildren().add(this.estado); // Añadir el estado visual al pane
    }

    // Métodos para encender/apagar el motor
    public void encenderMotor() {
        if (!encendido) {
            encendido = true;
            estado.setFill(Color.GREEN); // Cambia el color para indicar que está encendido
            distribuirCargas(); // Asignar las cargas y colores
        }
    }

    public void apagarMotor() {
        if (encendido) {
            encendido = false;
            estado.setFill(Color.BLACK); // Cambia el color para indicar que está apagado
            removerCargas(); // Remover las cargas y colores
        }
    }

    // Distribuir cargas cuando el motor esté encendido
    private void distribuirCargas() {
        positivo.setFill(Color.RED);
        positivo.setCarga("+"); // Asigna la carga positiva
        negativo.setFill(Color.BLUE);
        negativo.setCarga("-"); // Asigna la carga negativa
    }

    // Remover cargas cuando el motor esté apagado
    private void removerCargas() {
        positivo.setFill(Color.BLACK);
        positivo.setCarga(" "); // Elimina la carga
        negativo.setFill(Color.BLACK);
        negativo.setCarga(" "); // Elimina la carga
    }

    // Getters
    public bus getPositivo() {
        return this.positivo;
    }

    public bus getNegativo() {
        return this.negativo;
    }

    public boolean isEncendido() {
        return encendido;
    }
}


