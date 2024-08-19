package com.example.protoboar;

import javafx.scene.layout.GridPane;

import javafx.scene.shape.Circle;

public class bus extends Circle {
    String carga;
    int columna;
    int fila;
    public bus() {

    }
    public bus(int radius) {
        super(radius);
        carga=null;
    }
    public void setColumna(int columna){
        this.columna=columna;
    }
    public void setFila(int fila){
        this.fila=fila;
    }
    public void presionado(bus pancho,GridPane gridPane ){

        System.out.println("presionado");
        System.out.println("Punto:"+columna+","+fila);
    }




}

