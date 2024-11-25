package com.example.protoboar;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.image.Image;
import java.util.ArrayList;

public class Fabrica_Switch8x3 {

    //Metodo inicial para crear el chip
    public Switch8x3 crear(){
        Switch8x3 sw = new Switch8x3();
        sw=decoracion(sw);
        sw.setOnMousePressed(this::iniciar_mov);
        sw.setOnMouseDragged(this::arrastrar);
        sw.setOnMouseReleased(this::terminar);
        sw.setOnMouseClicked(Click::eliminarElemento);
        return sw;
    }

    //decoracion crea la base y las patas de cada chip, tanto su tamaño como sus coordenadas
    public Switch8x3 decoracion(Switch8x3 sw){
        Rectangle base = new Rectangle(700,300,136,38);
        base.setFill(Color.RED);
        sw.getChildren().add(base);
        sw.setBase(base);
        int i=0;
        int x=700;
        int y=295;
        while(i<16){
            Pata pata= new Pata(x,y,10,10);
            pata.setFill(Color.GRAY);
            pata.setArcWidth(5);  // Curvatura horizontal
            pata.setArcHeight(5);
            sw.addPat(pata);
            sw.getChildren().add(pata);
            x=x+18;
            if(i==7){
                x=700;
                y=y+38;
            }
            i++;
        }
        i = 0;
        x = 699;
        y = 307;
        while (i < 8){
            Interruptor interruptor = new Interruptor();
            interruptor.setFitHeight(25);
            interruptor.setFitWidth(12);
            interruptor.setX(x);
            interruptor.setY(y);
            interruptor.setOnMouseClicked(this::cambiarCarga);
            sw.addInterruptores(interruptor);
            sw.getChildren().add(interruptor);
            i++;
            x = x+18;
        }
        return sw;
    }

    // este es un evento que se llama al presionar el objeto, guardando la posicion inicial
    private void iniciar_mov(MouseEvent event){
        Switch8x3 sw = (Switch8x3) event.getSource();
        sw.initX= event.getSceneX();
        sw.initY= event.getSceneY();
    }

    // el metodo arrastre se utiliza cuando se mueve un objeto, actualizando las coordenas
    public void arrastrar(MouseEvent event){
        Switch8x3 sw = (Switch8x3) event.getSource();
        double deltaX = event.getSceneX() - sw.initX;
        double deltaY = event.getSceneY() - sw.initY;
        // Mover el grupo completo
        sw.setLayoutX(sw.getLayoutX() + deltaX);
        sw.setLayoutY(sw.getLayoutY() + deltaY);
        // Actualizar las coordenadas iniciales para el próximo movimiento
        sw.initX = event.getSceneX();
        sw.initY = event.getSceneY();
    }

    // el evento terminar se activa cuando se suelta el obejto y realiza unas verificaciones para saber si esta dentro de los buses
    public void terminar(MouseEvent event){
        detectar(event);
        Switch8x3 sw = (Switch8x3) event.getSource();
        int x = 0;
        boolean bandera = true;
        // Verifica que todas las patas estén conectadas
        while (x < sw.getPatas().size() && bandera) {
            if (sw.getPats(x).getPata() != 1) {
                bandera = false;
            }
            x++;
        }
        if (bandera) {
            x=0;
            while(x<8){
                bus bus1 = sw.getPats(x).getBus_conectado();
                sw.getInterruptores().get(x).setBus1(bus1);
                bus bus2 = sw.getPats(x+8).getBus_conectado();
                sw.getInterruptores().get(x).setBus2(bus2);
                x++;
            }
            // Ubica cada pata en su posición en el bus correspondiente
            x = 0;
            while (x < sw.getPatas().size()) {
                Pata pata = sw.getPats(x);
                double bus_x = pata.getBus_conectado().localToScene(
                        pata.getBus_conectado().getCenterX(),
                        pata.getBus_conectado().getCenterY()
                ).getX();
                double bus_y = pata.getBus_conectado().localToScene(
                        pata.getBus_conectado().getCenterX(),
                        pata.getBus_conectado().getCenterY()
                ).getY();
                double pata_W = pata.getWidth();
                double pata_H = pata.getHeight();
                double newX = pata.sceneToLocal(bus_x - pata_W / 2, bus_y - pata_H / 2).getX();
                double newY = pata.sceneToLocal(bus_x - pata_W / 2, bus_y - pata_H / 2).getY();
                pata.setX(newX);
                pata.setY(newY);
                x++;
            }
            // Centra la base en función de las patas
            double centerX = (sw.getPats(0).getX() + sw.getPats(7).getX() + sw.getPats(8).getX() +
                    sw.getPats(15).getX() + sw.getPats(0).getWidth() + sw.getPats(8).getWidth()) / 4;
            double centerY = (sw.getPats(0).getY() + sw.getPats(7).getY() + sw.getPats(8).getY() +
                    sw.getPats(15).getY() + sw.getPats(0).getHeight() + sw.getPats(8).getHeight()) / 4;
            sw.getBase().setX(centerX - sw.getBase().getWidth() / 2);
            sw.getBase().setY(centerY - sw.getBase().getHeight() / 2);
            ArrayList<Interruptor> interruptores = sw.getInterruptores();
            ArrayList<Pata> patas = sw.getPatas();
            for (int i = 0; i < interruptores.size(); i++) {
                Interruptor interruptor = interruptores.get(i);
                // Calculamos la posición en la escena para obtener las coordenadas globales de la pata
                double Xpata = patas.get(i).getBus_conectado().getLayoutX();
                // Actualizamos solo la posición X del interruptor
                interruptor.setLayoutX(Xpata);
            }
            // Desactiva eventos de movimiento y marca el Switch como terminado
            sw.setOnMousePressed(null);
            sw.setOnMouseDragged(null);
            sw.setOnMouseReleased(null);
            sw.terminado = true;
        }
    }

    // este metodo verifica si cada pata se encuentra en un bus
    public void detectar(MouseEvent event) {
        Switch8x3 sw = (Switch8x3) event.getSource();
        int x = 0;
        while (x < sw.getProtos().size()) {
            Protoboard proto = sw.getProtos().get(x);
            //viajara por cada nodo del protoboard correspondiente
            for (Node node : proto.getChildren()) {
                // si se encuntra un bus este comenzara con las verificacion de las patas
                if (node instanceof bus bus) {
                    int i = 0;
                    boolean chipEncimaDelBus = false;
                    while(i<sw.getPatas().size()){
                        boolean bandera=false;
                        // si los buses son los que estan el los surcos se realizara la verificacion de las patas, ya que el chip solo se puede colocar en los surcos
                        if(6==bus.fila || 7==bus.fila || 1==bus.fila || 2==bus.fila || 11==bus.fila || 12==bus.fila){
                            //pasa 50 verifica si se encuentra ensima del bus y entraga un booleano si es verdad o no
                            bandera= pasa_50(bus,sw.getPats(i));
                        }
                        //si el pasa_50 es verdadero quiere decir que la pata esta ensima de un bus de los surcos
                        if (bandera) {
                            bus.setFill(Color.RED);
                            sw.getPats(i).setPata(1); // Marcamos que la pata está conectada
                            sw.getPats(i).setBus_conectado(bus); // Asignamos el bus a la pata
                            chipEncimaDelBus = true; // Marcamos que hay al menos una pata encima del bus
                            sw.pos_proto = x;
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

    public void cambiarCarga(MouseEvent event) {
        Image image;
        Interruptor interruptor = (Interruptor) event.getSource();
        // Verificación de que los buses no sean nulos
        if(interruptor.getQuemado()){
            if (!interruptor.getEncendido()) {
                image = new Image("/Interruptor1.png");
                interruptor.setEncendido(true);
                String bus1 = interruptor.getBus1().carga;
                String bus2 = interruptor.getBus2().carga;
                if(bus1.equals("-") && bus2.equals("+") || bus1.equals("+") && bus2.equals("-")){
                    image = new Image("/Interruptor1Quemado.png");
                    interruptor.setQuemado(true);
                }
            } else {
                image = new Image("/Interruptor2.png");
                interruptor.setEncendido(false);

            }
            interruptor.setImage(image);
        }
        interruptor.toFront();
        Click.iniciar();
    }
}