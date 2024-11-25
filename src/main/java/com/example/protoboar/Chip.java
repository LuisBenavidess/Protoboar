package com.example.protoboar;

import javafx.scene.Group;
import java.util.ArrayList;
import javafx.scene.shape.Rectangle;

//Clase chip
public class Chip extends Group {
    //Atributos utilizados (objetos vinculados al chip, coordenadas, etc.)
    private final String tipo;
    public double initX;
    public double initY;
    private Rectangle base;
    private ArrayList<Protoboard> protos;
    private final ArrayList<Pata> patas;
    public int pos_proto;
    public boolean terminado;
    public boolean agregado=false;
    //Construcción inicial
    Chip(String tipo){
        protos = new ArrayList<>();
        patas = new ArrayList<>();
        terminado = false;
        this.tipo=tipo;
    }

    public void addPat(Pata pat){
        patas.add(pat);
    }

    public void setProtos(ArrayList<Protoboard> protos){
        this.protos = protos;
    }

    public ArrayList<Protoboard> getProtos(){
        return protos;
    }

    public ArrayList<Pata> getPatas(){
        return patas;
    }

    public Pata getPats(int x){
        return patas.get(x);
    }

    public void setBase(Rectangle base){
        this.base = base;
    }

    public Rectangle getBase(){
        return base;
    }

    public String getTipo(){
        return tipo;
    }
}