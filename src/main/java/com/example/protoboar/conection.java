package com.example.protoboar;

import javafx.scene.shape.Line;
//Clase que se encarga de las coneciones de las cargas
public class conection extends Line {

    private bus inicio;
    private bus fin;

    // Constructor
    public conection(double startX, double startY, double endX, double endY) {
        super(startX, startY, endX, endY);
    }

    // Setters y Getters
    public void setInicio(bus inicio) {
        this.inicio = inicio;
    }

    public void setFin(bus fin) {
        this.fin = fin;
    }

    public bus getInicio() {
        return inicio;
    }

    public bus getFin() {
        return fin;
    }

    // Método para actualizar la posición de la punta del cable
    public void updatePosition(bus bus) {
        if (bus == inicio) {
            setStartX(bus.getCenterX());
            setStartY(bus.getCenterY());
        } else if (bus == fin) {
            setEndX(bus.getCenterX());
            setEndY(bus.getCenterY());
        }
    }
}

