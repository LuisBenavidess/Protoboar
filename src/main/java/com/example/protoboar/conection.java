package com.example.protoboar;

import javafx.scene.shape.Line;

public class conection extends Line {
    private bus inicio;
    private bus fin;

    public conection(double startX, double startY, double endX, double endY) {
        super(startX, startY, endX, endY);
    }

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
}
