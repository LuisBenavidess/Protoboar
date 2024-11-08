package com.example.protoboar;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Switch8x3 extends Group {
    //Atributos utilizados (objetos vinculados al chip, coordenadas, etc.)
    private String tipo;
    public double initX;
    public double initY;
    private Rectangle base;
    private ArrayList<Protoboard> protos;
    private ArrayList<Pata> patas;
    private ArrayList<Interruptor> interruptores;
    public int pos_proto;
    public boolean terminado;

    //Construcción inicial
    Switch8x3(){
        protos = new ArrayList<>();
        patas = new ArrayList<>();
        interruptores = new ArrayList<>();
        terminado = false;
        this.tipo=tipo;
    }

    //Metodos gets y sets
    public void addInterruptores(Interruptor interruptor) {
        interruptores.add(interruptor);
    }
    public void addProto(Protoboard proto){
        protos.add(proto);
    }
    public void addPat(Pata pat){
        patas.add(pat);
    }

    public void setProtos(ArrayList<Protoboard> protos){
        this.protos = protos;
    }
    public void setPats(ArrayList<Pata> pats){
        this.patas = pats;
    }
    public void setBase(Rectangle base){
        this.base = base;
    }

    public void removeProto(Protoboard proto){
        protos.remove(proto);
    }
    public void removePat(Rectangle pat){
        patas.remove(pat);
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
