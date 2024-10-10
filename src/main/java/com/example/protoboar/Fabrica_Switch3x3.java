package com.example.protoboar;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public class Fabrica_Switch3x3 {

    public Switch3x3 crear(){

        Switch3x3 sw = new Switch3x3();
        sw = decoracion(sw);
        sw.setOnMousePressed(this::iniciar_mov);
        sw.setOnMouseDragged(this::arrastrar);
        sw.setOnMouseReleased(this::terminar);

        sw.setOnMouseClicked(Switch3x3::cambiarCarga);

        return sw;
    }

    public Switch3x3 decoracion(Switch3x3 sw){

        int i=0;
        int x=702;
        int y=402;
        while(i<4){
            Pata pata= new Pata(x,y,13,13);
            pata.setFill(Color.GRAY);
            pata.setArcWidth(5);  // Curvatura horizontal
            pata.setArcHeight(5);
            sw.setPatas(pata);
            sw.getChildren().add(pata);

            x=x+35;
            if(i==1){
                x=702;
                y=y+43;
            }
            i++;
        }

        Image image = new Image(Switch3x3.class.getResourceAsStream("/Switch3x3.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(60);
        imageView.setFitWidth(52);
        imageView.setX(700);
        imageView.setY(400);

        sw.setImageView(imageView);
        sw.getChildren().add(imageView);



        return sw;
    }

    private void iniciar_mov(MouseEvent event){
        Switch3x3 sw = (Switch3x3) event.getSource();
        sw.initX= event.getSceneX();
        sw.initY= event.getSceneY();
    }

    public void arrastrar(MouseEvent event){
        Switch3x3 sw = (Switch3x3) event.getSource();
        double deltaX = event.getSceneX() - sw.initX;
        double deltaY = event.getSceneY() - sw.initY;

        // Mover el grupo completo
        sw.setLayoutX(sw.getLayoutX() + deltaX);
        sw.setLayoutY(sw.getLayoutY() + deltaY);



        // Actualizar las coordenadas iniciales para el próximo movimiento
        sw.initX = event.getSceneX();
        sw.initY = event.getSceneY();
    }

    public void terminar(MouseEvent event){
        detectar(event);
        Switch3x3 sw = (Switch3x3) event.getSource();
        int x=0;
        boolean bandera=true;
        while(x<sw.getPatas().size() && bandera){
            if(sw.getPats(x).getPata()!=1){
                bandera=false;
            }
            x++;
        }
        if(bandera){
            x=0;
            while(x<sw.getPatas().size()){
                Pata pata=sw.getPats(x);
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
            double centerX = (sw.getPats(0).getX() + sw.getPats(1).getX() + sw.getPats(2).getX() +
                    sw.getPats(3).getX() + sw.getPats(0).getWidth() + sw.getPats(2).getWidth()) / 4;
            double centerY = (sw.getPats(0).getY() + sw.getPats(1).getY() + sw.getPats(2).getY() +
                    sw.getPats(3).getY() + sw.getPats(0).getHeight() + sw.getPats(2).getHeight()) / 4;
            sw.getImageView().setX(centerX - sw.getImageView().getFitWidth() / 2);
            sw.getImageView().setY(centerY - sw.getImageView().getFitHeight() / 2);
            sw.setOnMousePressed(null);
            sw.setOnMouseDragged(null);
            sw.setOnMouseReleased(null);
            sw.setOnMousePressed(Click::eliminarElemento);
            sw.terminado=true;
        }


    }

    public void detectar(MouseEvent event){
        Switch3x3 sw = (Switch3x3) event.getSource();
        int x=0;
        while(x<sw.getProtos().size()){
            Protoboard proto = sw.getProtos().get(x);
            for(Node node : proto.getChildren()){
                if(node instanceof bus){
                    bus bus = (bus) node;
                    int i=0;

                    while(i<sw.getPatas().size()){

                        boolean bandera= pasa_50(bus,sw.getPats(i));
                        //System.out.println(bandera);
                        if (bandera/*chip.getPats(i).localToScene(chip.getPats(i).getBoundsInLocal())
                                .intersects(bus.localToScene(bus.getBoundsInLocal()))*/) {
                            bus.setFill(Color.RED);
                            sw.getPats(i).setPata(1);
                            sw.getPats(i).setBus_conectado(bus);
                            sw.pos_proto=x;
                        }
                        i++;
                    }

                }
            }
            x++;
        }

    }

    public boolean pasa_50(bus bus, Pata pata){
        // Obtener los bounds del círculo y del rectángulo
        if (pata.localToScene(pata.getBoundsInLocal())
                .intersects(bus.localToScene(bus.getBoundsInLocal()))) {
            //System.out.println("pasa");
            // Calcular el área cubierta
            double circleArea = Math.PI * Math.pow(bus.getRadius(), 2);
            double Area = Area(bus, pata);

            // Verificar si más del 50% del círculo está cubierto
            return Area >= (0.5 * circleArea);
        }
        return false;
    }

    private double Area(bus bus,Pata pata) {
        Shape inter = Shape.intersect(bus, pata);
        //Calcular la interseccion entre el bus y la pata
        return inter.getBoundsInLocal().getWidth() * inter.getBoundsInLocal().getHeight();
    }

}
