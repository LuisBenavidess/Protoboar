package com.example.protoboar;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


import javafx.scene.input.MouseEvent;

public class Fabrica_Chip {
    public Fabrica_Chip() {}

    public Chip crear(){

        Chip chip = new Chip();
        chip=decoracion(chip);
        chip.setOnMousePressed(this::iniciar_mov);
        chip.setOnMouseDragged(this::arrastrar);
        chip.setOnMouseReleased(this::terminar);

        return chip;

    }


    public Chip decoracion(Chip chip){

        Rectangle base = new Rectangle(700,300,65,38/*40*/);
        chip.getChildren().add(base);
        int i=0;
        int x=700;
        int y=295;
        while(i<8){
            Pata pata= new Pata(x,y,10,10);
            pata.setFill(Color.GRAY);
            chip.addPat(pata);
            chip.getChildren().add(pata);

            x=x+18;
            if(i==3){
                x=700;
                y=y+38;
            }
            i++;
        }

        return chip;
    }

    private void iniciar_mov(MouseEvent event){
        Chip chip = (Chip) event.getSource();
        chip.initX= event.getSceneX();
        chip.initY= event.getSceneY();
    }

    public void arrastrar(MouseEvent event){
        Chip chip = (Chip) event.getSource();
        double deltaX = event.getSceneX() - chip.initX;
        double deltaY = event.getSceneY() - chip.initY;

        // Mover el grupo completo
        chip.setLayoutX(chip.getLayoutX() + deltaX);
        chip.setLayoutY(chip.getLayoutY() + deltaY);



        // Actualizar las coordenadas iniciales para el próximo movimiento
        chip.initX = event.getSceneX();
        chip.initY = event.getSceneY();
    }

    public void terminar(MouseEvent event){
        detectar(event);
        Chip chip = (Chip) event.getSource();
        int x=0;
        boolean bandera=true;
        while(x<chip.getPatas().size() && bandera){
            if(chip.getPats(x).getPata()!=1){
                bandera=false;
            }
            x++;
        }
        if(bandera){
            x=0;
            while(x<chip.getPatas().size()){
                Pata pata=chip.getPats(x);
                double bus_x = pata.getBus_conectado().localToScene(pata.getBus_conectado().getCenterX(), pata.getBus_conectado().getCenterY()).getX();
                double bus_y = pata.getBus_conectado().localToScene(pata.getBus_conectado().getCenterX(), pata.getBus_conectado().getCenterY()).getY();

                double pata_W = pata.getWidth();
                double pata_H = pata.getHeight();

                double newX = pata.sceneToLocal(bus_x - pata_W / 2, bus_y - pata_H / 2).getX();
                double newY = pata.sceneToLocal(bus_x - pata_W / 2, bus_y - pata_H / 2).getY();

                pata.setX(newX);
                pata.setY(newY);
                x++;

            }
            chip.setOnMousePressed(null);
            chip.setOnMouseDragged(null);
            chip.setOnMouseReleased(null);
            chip.terminado=true;
        }


    }

    public void detectar(MouseEvent event){
        Chip chip = (Chip) event.getSource();
        int x=0;
        while(x<chip.getProtos().size()){
            Protoboard proto = chip.getProtos().get(x);
            for(Node node : proto.getChildren()){
                if(node instanceof bus){
                    bus bus = (bus) node;
                    int i=0;

                    while(i<chip.getPatas().size()){


                        if (chip.getPats(i).localToScene(chip.getPats(i).getBoundsInLocal()).intersects(bus.localToScene(bus.getBoundsInLocal()))) {
                            bus.setFill(Color.RED);
                            chip.getPats(i).setPata(1);
                            chip.getPats(i).setBus_conectado(bus);
                            chip.pos_proto=x;
                        }
                        i++;
                    }

                }
            }
            x++;
        }

    }


}
