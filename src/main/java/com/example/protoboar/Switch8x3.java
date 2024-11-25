package com.example.protoboar;

import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;

public class Switch8x3 extends Group {
    public double initX;
    public double initY;
    private Rectangle base;
    private ArrayList<Protoboard> protos;
    private final ArrayList<Pata> patas;
    private final ArrayList<Interruptor> interruptores;
    public int pos_proto;
    public boolean terminado;
    public boolean agregado;

    //Construcción inicial
    Switch8x3(){
        //Atributos utilizados (objetos vinculados al chip, coordenadas, etc.)
        protos = new ArrayList<>();
        patas = new ArrayList<>();
        interruptores = new ArrayList<>();
        terminado = false;
    }

    //Metodos gets y sets
    public void addInterruptores(Interruptor interruptor) {
        interruptores.add(interruptor);
    }

    public void addPat(Pata pat){
        patas.add(pat);
    }

    public void setProtos(ArrayList<Protoboard> protos){
        this.protos = protos;
    }

    public void setBase(Rectangle base){
        this.base = base;
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

    public Rectangle getBase(){
        return base;
    }

    public ArrayList<Interruptor> getInterruptores(){
        return interruptores;
    }
}