package com.example.protoboar;

import javafx.scene.shape.Line;
//Clase que se encarga de las coneciones de las cargas
public class conection extends Line {

    private bus inicio;
    private bus fin;
    public int pos_proto;
    public boolean bateria;

    //Constructor
    public conection(double startX, double startY, double endX, double endY) {
        super(startX, startY, endX, endY);
        bateria=false;
        pos_proto = -1;
    }

    // set y get
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

    public void actualizarPosicion(double deltaX, double deltaY) {
        // Actualiza las posiciones de los puntos de inicio y fin del cable
        inicio.setCenterX(inicio.getCenterX() + deltaX);
        inicio.setCenterY(inicio.getCenterY() + deltaY);
        fin.setCenterX(fin.getCenterX() + deltaX);
        fin.setCenterY(fin.getCenterY() + deltaY);
    }
}