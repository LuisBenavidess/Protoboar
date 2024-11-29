package com.example.protoboar;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Fabrica_display {

    public Display display(){
        Display display = new Display();
        decoracion(display);
        display.setOnMousePressed(this::iniciar_mov);
        display.setOnMouseDragged(this::arrastrar);
        display.setOnMouseReleased(this::terminar);
        display.setOnMouseClicked(Click::eliminarElemento);
        return display;
    }

    private void decoracion(Display display){

        Rectangle base = new Rectangle(700,104,85,90);
        base.setArcWidth(15);  // Curvatura horizontal
        base.setArcHeight(15); // Curvatura vertical
        base.setFill(Color.web("#3c4344"));
        display.setBase(base);

        Rectangle luz1 = new Rectangle(720,110,30,5);
        luz1.setFill(Color.GRAY);
        luz1.setArcWidth(15);  // Curvatura horizontal
        luz1.setArcHeight(15); // Curvatura vertical
        display.setLeds(luz1);

        Rectangle luz2 = new Rectangle(715,115,5,30);
        luz2.setFill(Color.GRAY);
        luz2.setArcWidth(15);  // Curvatura horizontal
        luz2.setArcHeight(15); // Curvatura vertical
        display.setLeds(luz2);

        Rectangle luz3 = new Rectangle(750,115,5,30);
        luz3.setFill(Color.GRAY);
        luz3.setArcWidth(15);  // Curvatura horizontal
        luz3.setArcHeight(15); // Curvatura vertical
        display.setLeds(luz3);

        Rectangle luz4 = new Rectangle(720,145,30,5);
        luz4.setFill(Color.GRAY);
        luz4.setArcWidth(15);  // Curvatura horizontal
        luz4.setArcHeight(15); // Curvatura vertical
        display.setLeds(luz4);

        Rectangle luz5 = new Rectangle(715,150,5,30);
        luz5.setFill(Color.GRAY);
        luz5.setArcWidth(15);  // Curvatura horizontal
        luz5.setArcHeight(15); // Curvatura vertical
        display.setLeds(luz5);

        Rectangle luz6 = new Rectangle(750,150,5,30);
        luz6.setFill(Color.GRAY);
        luz6.setArcWidth(15);  // Curvatura horizontal
        luz6.setArcHeight(15); // Curvatura vertical
        display.setLeds(luz6);

        Rectangle luz7 = new Rectangle(720,180,30,5);
        luz7.setFill(Color.GRAY);
        luz7.setArcWidth(15);  // Curvatura horizontal
        luz7.setArcHeight(15); // Curvatura vertical
        display.setLeds(luz7);

        Circle punto= new Circle(3);
        punto.setFill(Color.GRAY);
        punto.setCenterX(765);
        punto.setCenterY(180);
        display.setPunto(punto);

        int i=0;
        int x=700;
        int y=100;
        while(i<10){
            Pata pata= new Pata(x,y,10,10);
            pata.setFill(Color.GRAY);
            pata.setArcWidth(5);  // Curvatura horizontal
            pata.setArcHeight(5);
            display.addPat(pata);
            display.getChildren().add(pata);

            x=x+18;
            if(i==4){
                x=700;
                y=y+88;
            }
            i++;
        }

        display.getChildren().add(base);

       display.getChildren().add(luz1);
        display.getChildren().add(luz2);
        display.getChildren().add(luz3);
        display.getChildren().add(luz4);
        display.getChildren().add(luz5);
        display.getChildren().add(luz6);
        display.getChildren().add(luz7);
        display.getChildren().add(punto);

    }



    // este es un evento que se llama al presionar el objeto, guardando la posicion inicial
    private void iniciar_mov(MouseEvent event){
        Display display = (Display) event.getSource();
        display.initX= event.getSceneX();
        display.initY= event.getSceneY();
    }

    // el metodo arrastre se utiliza cuando se mueve un objeto, actualizando las coordenas
    private void arrastrar(MouseEvent event){
        Display display = (Display) event.getSource();
        double deltaX = event.getSceneX() - display.initX;
        double deltaY = event.getSceneY() - display.initY;

        // Mover el grupo completo
        display.setLayoutX(display.getLayoutX() + deltaX);
        display.setLayoutY(display.getLayoutY() + deltaY);



        // Actualizar las coordenadas iniciales para el próximo movimiento
        display.initX = event.getSceneX();
        display.initY = event.getSceneY();
    }

    // el evento terminar se activa cuando se suelta el obejto y realiza unas verificaciones para saber si esta dentro de los buses
    private void terminar(MouseEvent event){
        Display display = (Display) event.getSource();
        int con=0;
        while(con<display.getPatas().size()){
            display.getPats(con).setPata(0);
            con++;
        }

        detectar(event);

        int x=0;
        boolean bandera=true;
        while(x<display.getPatas().size() && bandera){
            if(display.getPats(x).getPata()!=1){
                bandera=false;
            }
            x++;
        }
        if(bandera){
            x=0;
            while(x<display.getPatas().size()){
                Pata pata=display.getPats(x);
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
            double centerX = (display.getPats(0).getX() + display.getPats(4).getX() + display.getPats(5).getX() +
                    display.getPats(9).getX() + display.getPats(0).getWidth() + display.getPats(5).getWidth()) / 4;
            double centerY = (display.getPats(0).getY() + display.getPats(4).getY() + display.getPats(5).getY() +
                    display.getPats(9).getY() + display.getPats(0).getHeight() + display.getPats(5).getHeight()) / 4;
            display.getBase().setX(centerX - display.getBase().getWidth() / 2);
            display.getBase().setY(centerY - display.getBase().getHeight() / 2);
            display.setOnMousePressed(null);
            display.setOnMouseDragged(null);
            display.setOnMouseReleased(null);
            display.terminado=true;
        }


    }

    // este metodo verifica si cada pata se encuentra en un bus
    private void detectar(MouseEvent event) {
        Display display = (Display) event.getSource();
        int x = 0;
        while (x < display.getProtos().size()) {
            Protoboard proto = display.getProtos().get(x);
            //viajara por cada nodo del protoboard correspondiente
            for (Node node : proto.getChildren()) {
                // si se encuntra un bus este comenzara con las verificacion de las patas
                if (node instanceof bus bus) {
                    int i = 0;
                    boolean chipEncimaDelBus = false;
                    while(i<display.getPatas().size()){
                        boolean bandera=false;
                        // si los buses son los que estan el los surcos se realizara la verificacion de las patas, ya que el chip solo se puede colocar en los surcos
                        if(4==bus.fila || 5==bus.fila||6==bus.fila || 7==bus.fila || 8==bus.fila || 9==bus.fila){
                            //pasa 50 verifica si se encuentra ensima del bus y entraga un booleano si es verdad o no
                            if(bus.puedeCrearComponente()){
                                bandera= pasa_50(bus,display.getPats(i));
                            }


                        }
                        //si el pasa_50 es verdadero quiere decir que la pata esta ensima de un bus de los surcos
                        if (bandera) {
                            bus.setFill(Color.RED);
                            display.getPats(i).setPata(1); // Marcamos que la pata está conectada
                            display.getPats(i).setBus_conectado(bus); // Asignamos el bus a la pata
                            chipEncimaDelBus = true; // Marcamos que hay al menos una pata encima del bus
                            display.pos_proto = x;
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

    private boolean pasa_50(bus bus, Pata pata){
        // Obtener los bounds del círculo y del rectángulo
        if (pata.localToScene(pata.getBoundsInLocal()).intersects(bus.localToScene(bus.getBoundsInLocal()))) {
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
