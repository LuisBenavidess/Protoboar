package com.example.protoboar;

import javafx.scene.shape.Circle;

public class bus extends Circle {
    private String carga;

    public bus() {
    }

    public void setColumna(){
    }

    public void setFila(){
    }

    public void setCarga(String carga){
        this.carga = carga;
    }

    public String getCarga() {
        return carga;
    }

}
