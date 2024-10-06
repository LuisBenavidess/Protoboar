package com.example.protoboar;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.MouseEvent;

public class Fabrica_Chip {
    public Fabrica_Chip() {}

    public Chip crear(){

        Chip chip = new Chip();
        chip=decoracion(chip);
        chip.setOnMousePressed(this::iniciar_mov);
        chip.setOnMouseDragged(this::arrastrar);

        return chip;

    }


    public Chip decoracion(Chip chip){

        Rectangle rectangle = new Rectangle(600,600,100,100);
        chip.getChildren().add(rectangle);

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
        for (Node node : chip.getChildren()) {
            if(node instanceof Rectangle){
                Rectangle rectangle = (Rectangle) node;
                rectangle.setX(rectangle.getX() + deltaX);
                rectangle.setY(rectangle.getY() + deltaY);
            }
        }
    }


}
