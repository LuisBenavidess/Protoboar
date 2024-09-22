package com.example.protoboar;

import javafx.scene.shape.Line;
//Clase que se encarga de las coneciones de las cargas
public class conection extends Line {

    //Atributos
    private bus inicio;
    private bus fin;
    private Led foto;

    //Constructor
    public conection(double startX, double startY, double endX, double endY) {
        super(startX, startY, endX, endY);
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

    public void set_foto(Led led){
        foto=led;
    }

    public Led get_foto(){
        return foto;
    }
}
