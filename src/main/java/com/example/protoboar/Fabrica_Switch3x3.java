package com.example.protoboar;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

import java.util.Objects;

public class Fabrica_Switch3x3 {

    public Switch3x3 crear(){
        Switch3x3 sw = new Switch3x3();
        sw = decoracion(sw);
        sw.setOnMousePressed(this::iniciar_mov);
        sw.setOnMouseDragged(this::arrastrar);
        sw.setOnMouseReleased(this::terminar);
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
        Image image = new Image(Objects.requireNonNull(Switch3x3.class.getResourceAsStream("/Switch3x3.png")));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(60);
        imageView.setFitWidth(52);
        imageView.setX(700);
        imageView.setY(400);
        sw.setImageView(imageView);
        sw.getChildren().add(imageView);
        return sw;
    }

    //Metodo para iniciar el arrastre
    private void iniciar_mov(MouseEvent event){
        Switch3x3 sw = (Switch3x3) event.getSource();
        sw.initX= event.getSceneX();
        sw.initY= event.getSceneY();
    }

    //Metodo de arrastre
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

    //Metodo que poisiciona el switch
    public void terminar(MouseEvent event){
        Switch3x3 sw = (Switch3x3) event.getSource();
        int con=0;
        while(con<sw.getPatas().size()){
            sw.getPats(con).setPata(0);
            con++;
        }

        detectar(event);

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

    //Metodo que detecta si las patas estan sobre un bus usando el metodo "pasa_50"
    public void detectar(MouseEvent event){
        Switch3x3 sw = (Switch3x3) event.getSource();
        int x=0;
        while(x<sw.getProtos().size()){
            Protoboard proto = sw.getProtos().get(x);
            for(Node node : proto.getChildren()){
                if(node instanceof bus bus){
                    int i=0;
                    boolean SwitchEncimaDelBus = false;
                    while(i<sw.getPatas().size()){
                        sw.getPats(i);
                        boolean bandera= false;
                        if(bus.puedeCrearComponente()){
                            bandera= pasa_50(bus,sw.getPats(i));
                        }
                        if (bandera) {
                            bus.setFill(Color.RED);
                            sw.getPats(i).setPata(1);
                            sw.getPats(i).setBus_conectado(bus);
                            SwitchEncimaDelBus = true;
                            sw.pos_proto=x;
                        }
                        i++;
                    }
                    // Si hay una pata encima del bus, marcamos el bus como ocupado por un componente
                    if (SwitchEncimaDelBus) {
                        bus.crearComponente(); // Cambiamos a true
                    } else {
                        bus.componenteCreado = false; // Cambiamos a false si no hay patas encima
                    }
                }
            }
            x++;
        }

    }

    //Metodo que evita errores al posicionar el switch usando el metodo "Area"
    public boolean pasa_50(bus bus, Pata pata){
        // Obtener los bounds del círculo y del rectángulo
        if (pata.localToScene(pata.getBoundsInLocal())
                .intersects(bus.localToScene(bus.getBoundsInLocal()))) {
            // Calcular el área cubierta
            double circleArea = Math.PI * Math.pow(bus.getRadius(), 2);
            double Area = Area(bus, pata);

            // Verificar si más del 50% del círculo está cubierto
            return Area >= (0.5 * circleArea);
        }
        return false;
    }

    //Metodo que detecta el areca intersectada con las patas del switch y un bus
    private double Area(bus bus,Pata pata) {
        Shape inter = Shape.intersect(bus, pata);
        //Calcular la interseccion entre el bus y la pata
        return inter.getBoundsInLocal().getWidth() * inter.getBoundsInLocal().getHeight();
    }

}
