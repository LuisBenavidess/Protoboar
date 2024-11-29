package com.example.protoboar;

import javafx.scene.Group;

import java.util.ArrayList;

//Clase que genera el protoboard
public class Protoboard extends Group {

    //Atributos
    public bus[][] alimentacion;
    public int columnas;
    public int filas;
    public double x;
    public double y;
    public double initialX;
    public double initialY;
    public double movimientoX;
    public double movimientoY;
    private final ArrayList<conection> conections;

    //Constructor
    public Protoboard() {
        conections = new ArrayList<>();
        alimentacion = new bus[14][30];
        x=37;
        y=52;
    }

    public void setConections(conection conection) {
        conections.add(conection);
    }

    public ArrayList<conection> getConections() {
        return conections;
    }
}