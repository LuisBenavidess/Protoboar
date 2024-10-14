package com.example.protoboar;

import javafx.scene.shape.Circle;
import java.util.ArrayList;
import java.util.List;

// Esta clase es la que utilizamos para cada círculo que se encuentra en el protoboard
public class bus extends Circle {
    // Atributos
    String carga;
    int columna;
    int fila;
    double x;
    double y;

    // Constructor
    public bus() {
        // Lista de conexiones (cables)
        List<conection> connections = new ArrayList<>(); // Inicializamos la lista
    }

    // Ingresar columna
    public void setColumna(int columna) {
        this.columna = columna;
    }

    // Ingresar fila
    public void setFila(int fila) {
        this.fila = fila;
    }

    // Ingresar carga
    public void setCarga(String carga) {
        this.carga = carga;
    }

    // Devolver carga
    public String getCarga() {
        return carga;
    }

    public int getColumna() {
        return columna;
    }

    public int getFila() {
        return fila;
    }
}