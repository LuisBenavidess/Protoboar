package com.example.protoboar;

import javafx.scene.shape.Line;
//Clase que se encarga de las coneciones de las cargas
public class conection extends Line {

    private bus inicio;
    private bus fin;
    public int pos_proto;
    public boolean salio=false;
    public boolean bateria;
    private boolean movimiento;
    //Constructor
    public conection(double startX, double startY, double endX, double endY) {
        super(startX, startY, endX, endY);
        movimiento=false;
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

    public void setMovimiento(boolean movimiento) {
        this.movimiento = movimiento;
    }
    public boolean getMovimiento() {
        return movimiento;
    }
}