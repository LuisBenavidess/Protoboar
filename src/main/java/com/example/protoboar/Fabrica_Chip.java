package com.example.protoboar;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Fabrica_Chip {
    public Fabrica_Chip() {}

    public Chip crear(){

        Chip chip = new Chip();
        chip=decoracion(chip);
        chip.setOnMousePressed(this::iniciar_mov);
        chip.setOnMouseDragged(this::arrastrar);
        chip.setOnMouseReleased(this::terminar);
        chip.setOnMouseClicked(Click::eliminarElemento);

        return chip;

    }


    public Chip decoracion(Chip chip){

        Rectangle base = new Rectangle(700,300,65,38/*40*/);
        Text letra = new Text("Chip");
        letra.setFill(Color.WHITE);
        letra.setFont(Font.font("Bodoni", FontWeight.BOLD, 10));
        letra.setLayoutX(700);
        letra.setLayoutY(325);
        chip.getChildren().add(base);
        chip.getChildren().add(letra);
        chip.setBase(base);
        int i=0;
        int x=700;
        int y=295;
        while(i<8){
            Pata pata= new Pata(x,y,10,10);
            pata.setFill(Color.GRAY);
            pata.setArcWidth(5);  // Curvatura horizontal
            pata.setArcHeight(5);
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
            double centerX = (chip.getPats(0).getX() + chip.getPats(3).getX() + chip.getPats(4).getX() +
                    chip.getPats(7).getX() + chip.getPats(0).getWidth() + chip.getPats(4).getWidth()) / 4;
            double centerY = (chip.getPats(0).getY() + chip.getPats(3).getY() + chip.getPats(4).getY() +
            chip.getPats(7).getY() + chip.getPats(0).getHeight() + chip.getPats(3).getHeight()) / 4;
            chip.getBase().setX(centerX - chip.getBase().getWidth() / 2);
            chip.getBase().setY(centerY - chip.getBase().getHeight() / 2);
            chip.setOnMousePressed(null);
            chip.setOnMouseDragged(null);
            chip.setOnMouseReleased(null);
            chip.terminado=true;
        }


        }

    public void detectar(MouseEvent event) {
        Chip chip = (Chip) event.getSource();
        int x = 0;
        while (x < chip.getProtos().size()) {
            Protoboard proto = chip.getProtos().get(x);
            for (Node node : proto.getChildren()) {
                if (node instanceof bus bus) {
                    int i = 0;
                    boolean chipEncimaDelBus = false;
                    while(i<chip.getPatas().size()){
                        boolean bandera=false;
                        if(6==bus.fila || 7==bus.fila || 1==bus.fila || 2==bus.fila || 11==bus.fila || 12==bus.fila){
                            bandera= pasa_50(bus,chip.getPats(i));
                        }
                        if (bandera) {
                            bus.setFill(Color.RED);
                            chip.getPats(i).setPata(1); // Marcamos que la pata está conectada
                            chip.getPats(i).setBus_conectado(bus); // Asignamos el bus a la pata
                            chipEncimaDelBus = true; // Marcamos que hay al menos una pata encima del bus
                            chip.pos_proto = x;
                        }
                        i++;
                    }
                    // Si hay una pata encima del bus, marcamos el bus como ocupado por un componente
                    if (chipEncimaDelBus) {
                        bus.crearComponente(); // Cambiamos a true
                    } else {
                        bus.componenteCreado = false; // Cambiamos a false si no hay patas encima
                    }
                }
            }
            x++;
        }
    }

    public boolean pasa_50(bus bus, Pata pata){
        // Obtener los bounds del círculo y del rectángulo
        if (pata.localToScene(pata.getBoundsInLocal()).intersects(bus.localToScene(bus.getBoundsInLocal()))) {
            //System.out.println("pasa");
            // Calcular el área cubierta
            double circleArea = Math.PI * Math.pow(bus.getRadius(), 2);
            double Area = Area(bus, pata);

            // Verificar si más del 50% del círculo está cubierto
            return Area >= (0.1 * circleArea);
        }
        return false;
    }

    private double Area(bus bus,Pata pata) {
        Shape inter = Shape.intersect(bus, pata);
        //Calcular la interseccion entre el bus y la pata
        return inter.getBoundsInLocal().getWidth() * inter.getBoundsInLocal().getHeight();
    }


}
