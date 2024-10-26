package com.example.protoboar;

import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

// Clase que se encargara de generar los protoboards
public class Fabrica_Proto {

    //Constructor
    public Fabrica_Proto(){
    }

    //Metodo que genera un grupo y lo devuelve, que seria el protoboard
    public Protoboard protoboard(){
        Protoboard proto = new Protoboard();
        proto=decoracion(proto);
        proto.setOnMousePressed(this::iniciar_Arrastre);
        proto.setOnMouseDragged(this::arrastrar);
        //proto.setOnMouseClicked(Click::eliminarElemento);
       // proto.setOnMouseReleased(this::terminar);
        // Generar los circulos
        return proto;
    }

    // Metodo que generar de decoracion, base, numeros, letras, simbolos etc
    public Protoboard decoracion(Protoboard proto){
        //Decoracion

        Rectangle base = new Rectangle(5,26,582,413/*435*/);
        base.setFill(Color.web("#dad8d9"));
        base.setArcWidth(15);  // Curvatura horizontal
        base.setArcHeight(15); // Curvatura vertical
        proto.setBase(base);
        Text menos_superior = new Text("-");
        menos_superior.setLayoutX(12);
        menos_superior.setLayoutY(45);
        menos_superior.setRotate(-90);
        Rectangle negativo_superior = new Rectangle(25,36,544,6);
        negativo_superior.setFill(Color.web("#0ea4e0"));
        negativo_superior.setArcWidth(5);  // Curvatura horizontal
        negativo_superior.setArcHeight(5); // Curvatura vertical

        Text menos_inferior = new Text("-");
        menos_inferior.setLayoutX(12);
        menos_inferior.setLayoutY(380);
        menos_inferior.setRotate(-90);
        Rectangle negativo_inferior = new Rectangle(25,372/*394*/,544,6);
        negativo_inferior.setFill(Color.web("#0ea4e0"));
        negativo_inferior.setArcWidth(5);  // Curvatura horizontal
        negativo_inferior.setArcHeight(5); // Curvatura vertical

        Text mas_superior = new Text("+");
        mas_superior.setLayoutX(10);
        mas_superior.setLayoutY(97);
        mas_superior.setRotate(-90);
        Rectangle positivo_superior = new Rectangle(24,89,544,6);
        positivo_superior.setFill(Color.web("#c43238"));
        positivo_superior.setArcWidth(5);  // Curvatura horizontal
        positivo_superior.setArcHeight(5); // Curvatura vertical

        Text mas_inferior = new Text("+");
        mas_inferior.setLayoutX(10);
        mas_inferior.setLayoutY(431);
        mas_inferior.setRotate(-90);
        Rectangle positivo_inferior = new Rectangle(25,423,544,6);
        positivo_inferior.setFill(Color.web("#c43238"));
        positivo_inferior.setArcWidth(5);  // Curvatura horizontal
        positivo_inferior.setArcHeight(5); // Curvatura vertical

        proto.getChildren().add(base);

        //Genera numeros
        numeros(33, 352/*376*/,proto);
        numeros(33, 95,proto);

        //Generar letras
        letras(9,348,proto);

        //Generar buses
        crear_buses(proto.x, proto.y, 2,proto);
        crear_buses(proto.x, proto.y+70, 5,proto);
        crear_buses(proto.x, proto.y+200/*224*/, 5,proto);
        crear_buses(proto.x, proto.y+337, 2,proto);

        proto.getChildren().add(menos_superior);
        proto.getChildren().add(mas_superior);
        proto.getChildren().add(menos_inferior);
        proto.getChildren().add(mas_inferior);
        proto.getChildren().add(negativo_superior);
        proto.getChildren().add(negativo_inferior);
        proto.getChildren().add(positivo_superior);
        proto.getChildren().add(positivo_inferior);
        return proto;
    }

    //Funcion que crea los numeros
    private void numeros(int X, int Y, Protoboard proto) {
        int i = 0;
        //A travez de rangos definidos se generan los numeros;
        while (i < 30) {
            Label label = new Label(String.valueOf(i + 1));
            label.setLayoutY(Y);
            label.setLayoutX(X);
            label.setRotate(-90);
            proto.getChildren().add(label);
            X = X + 18;
            i++;
        }
    }

    //Funcion que crea letra
    private void letras(int X, int Y, Protoboard proto) {
        int num=65;
        int alto=Y;
        int i=0;
        while (i < 10) {
            Character letra=(char) num;
            String nuevaLetra=String.valueOf(letra);
            Text text = new Text(nuevaLetra);
            text.setLayoutX(X);
            text.setLayoutY(alto);
            text.setRotate(-90);
            if(alto==260){
                alto=alto-42;
            }else{
                alto=alto-22;
            }
            proto.getChildren().add(text);
            num++;
            i++;
        }
    }

    //Funcion que crea los circulos
    @FXML
    private void crear_buses(double X, double Y, int FIL,Protoboard proto) {
        // Variables a utilizar
        int carga=0;
        int col = 0;
        int fil = 0;
        double x = X;
        double y = Y;

        int mas_x=0;
        int mas_y=0;
        //bucle que viaja atravez de la matriz alimentacion generando buses con su respectiva posicion
        while (fil < FIL) {
            while (col < 30) {
                //circulo
                bus circulo = new bus();
                circulo.setCenterX(x+mas_x);
                circulo.setCenterY(y+mas_y);
                circulo.setRadius(6);
                circulo.setFill(Color.BLACK);
                circulo.toFront();
                circulo.setOnMouseDragged(this::arrastrar);
                //Guardar el circulo dentro de la matriz
                proto.alimentacion[proto.filas][proto.columnas] = circulo;
                proto.alimentacion[proto.filas][proto.columnas].setFila(proto.filas);
                proto.alimentacion[proto.filas][proto.columnas].setColumna(proto.columnas);
                proto.alimentacion[proto.filas][proto.columnas].setCarga(" ");
                proto.alimentacion[proto.filas][proto.columnas].x = circulo.getCenterX();
                proto.alimentacion[proto.filas][proto.columnas].y = circulo.getCenterY();
                circulo.setVoltaje(null);

                // Agregar el bus al protoboard
                proto.getChildren().add(circulo);
                // Agregar el display del voltaje al protoboard
                proto.getChildren().add(circulo.getVoltajeDisplay());

                mas_x = mas_x + 18;
                col++;
                proto.columnas++;
                if (col == 30) {
                    fil = fil + 1;
                    proto.filas = proto.filas + 1;
                    x = 37;
                    mas_x=0;
                    mas_y=mas_y+22;
                    carga = carga + 1;
                }
            }
            col = 0;
            proto.columnas = 0;
        }
    }

    private void iniciar_Arrastre(MouseEvent event){
        Protoboard proto = (Protoboard) event.getSource();
        proto.initialX = event.getSceneX();
        proto.initialY = event.getSceneY();
        proto.movimientoX=proto.initialX;
        proto.movimientoY= proto.y;
    }

    private void arrastrar(MouseEvent event) {
        if(event.getSource() instanceof Protoboard proto){
            double deltaX = event.getSceneX() - proto.initialX;
            double deltaY = event.getSceneY() - proto.initialY;
            for (Node node : proto.getChildren()) {
                if (node instanceof bus circle) {
                    circle.setCenterX(circle.getCenterX() + deltaX);
                    circle.setCenterY(circle.getCenterY() + deltaY);
                    int i=0;
                    while(i<proto.getConections().size()){
                        if(proto.getConections().get(i).getFin()==circle){
                            proto.getConections().get(i).endXProperty().unbind();
                            proto.getConections().get(i).setEndX(circle.getCenterX() + deltaX);
                            proto.getConections().get(i).endYProperty().unbind();
                            proto.getConections().get(i).setEndY(circle.getCenterY() + deltaY);
                        }
                        if(proto.getConections().get(i).getInicio()==circle){
                            proto.getConections().get(i).startXProperty().unbind();
                            proto.getConections().get(i).setStartX(circle.getCenterX() + deltaX);
                            proto.getConections().get(i).startYProperty().unbind();
                            proto.getConections().get(i).setStartY(circle.getCenterY() + deltaY);
                        }
                        i++;
                    }
                }
                if(node instanceof Rectangle rectangle){
                    rectangle.setX(rectangle.getX() + deltaX);
                    rectangle.setY(rectangle.getY() + deltaY);
                }
                if(node instanceof Text text){
                    text.setX(text.getX() + deltaX);
                    text.setY(text.getY() + deltaY);
                }
                if(node instanceof Label label){
                    label.setLayoutX(label.getLayoutX() + deltaX);
                    label.setLayoutY(label.getLayoutY() + deltaY);
                }
                if (node instanceof conection cable) {
                   // cable.moverInicio(deltaX, deltaY);
                    //cable.moverFin(deltaX, deltaY);
                }
                if(node instanceof ImageView image){
                    image.setLayoutX(image.getLayoutX() + deltaX);
                    image.setLayoutY(image.getLayoutY() + deltaY);
                }
                if(node instanceof Chip chip){
                    chip.setLayoutX(chip.getLayoutX() + deltaX);
                    chip.setLayoutY(chip.getLayoutY() + deltaY);
                }
                if(node instanceof Switch3x3 switch3x3){
                    switch3x3.setLayoutX(switch3x3.getLayoutX() + deltaX);
                    switch3x3.setLayoutY(switch3x3.getLayoutY() + deltaY);
                }
            }
            int i=0;
            while(i<proto.getConections().size()){
                proto.getConections().get(i).setMovimiento(false);
                i++;
            }
            // Actualizar la posición inicial
            proto.initialX = event.getSceneX();
            proto.initialY = event.getSceneY();
        }
    }
}