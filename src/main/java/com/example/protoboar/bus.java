package com.example.protoboar;

import javafx.scene.shape.Circle;

public class bus extends Circle {
    String carga;
    int columna;
    int fila;
    public bus() {
    }
    public void setColumna(int columna){
        this.columna=columna;
    }
    public void setFila(int fila){
        this.fila=fila;
    }

    public void get_ubicacion(){
        System.out.println("Fila: "+fila+" Columna: "+columna);
    }

    public void setCarga(String carga){
        this.carga=carga;
    }
    public String getCarga() {
        return carga;
    }
}

