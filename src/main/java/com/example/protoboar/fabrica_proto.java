package com.example.protoboar;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

// Clase que se encargara de generar los protoboards
public class fabrica_proto {

    //Constructor
    public fabrica_proto(){
    }

    //Metodo que genera un grupo y lo devuelve, que seria el protoboard
    public Protoboard protoboard(){

        Protoboard proto = new Protoboard();
        proto=decoracion(proto);
        // Generar los circulos

        return proto;
    }

    // Metodo que generar de decoracion, base, numeros, letras, simbolos etc
    public Protoboard decoracion(Protoboard proto){
        //Decoracion
        Rectangle base = new Rectangle(5,26,582,435);
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
        menos_inferior.setLayoutY(402);
        menos_inferior.setRotate(-90);
        Rectangle negativo_inferior = new Rectangle(25,394,544,6);
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
        mas_inferior.setLayoutY(453);
        mas_inferior.setRotate(-90);
        Rectangle positivo_inferior = new Rectangle(25,445,544,6);
        positivo_inferior.setFill(Color.web("#c43238"));
        positivo_inferior.setArcWidth(5);  // Curvatura horizontal
        positivo_inferior.setArcHeight(5); // Curvatura vertical

        proto.getChildren().add(base);

        //Genera numeros
        proto=numeros(33, 376,proto);
        proto=numeros(33, 100,proto);
        proto=letras(9,369,proto);
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
    private Protoboard numeros(int X, int Y,Protoboard proto) {
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
        return proto;
    }

    //Funcion que crea letra
    private Protoboard letras(int X, int Y,Protoboard proto) {
        int num=65;
        int alto=Y;
        int i=0;
        while (i < 10) {
            Character letra=(char) num;
            String nuevaLetra=String.valueOf(letra);
            Text text = new Text(nuevaLetra);
            //System.out.println(nuevaLetra);
            text.setLayoutX(X);
            text.setLayoutY(alto);
            text.setRotate(-90);
            if(alto==281){
                alto=alto-63;
            }else{
                alto=alto-22;
            }
            proto.getChildren().add(text);
            num++;
            i++;
        }
        return proto;
    }
}
