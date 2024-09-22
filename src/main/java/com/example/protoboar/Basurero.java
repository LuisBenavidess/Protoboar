package com.example.protoboar;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class Basurero {

    private static Pane pane;

    private static boolean eliminarProximaImagen = false;

    private final ImageView basurero;

    public Basurero(){

        Image image = new Image("/basurero.png");
        this.basurero = new ImageView(image);

        // Configurar el clic en el basurero
        basurero.setOnMouseClicked(this::ClickEnBasurero);

        // Ajustar las dimensiones del ImageView
        basurero.setFitWidth(80);
        basurero.setFitHeight(80);

        // Posicionar el ImageView en las coordenadas dadas
        basurero.setY(468);

        pane.getChildren().add(basurero);
    }

    //Metodo para cuando se preciona el basurero (Borrar)
    public void ClickEnBasurero(MouseEvent e) {
        System.out.println("Modo borrar");
        activar(basurero);
        eliminarProximaImagen = true;
        while(eliminarProximaImagen) {
            if( basurero.equals(e)){
                desactivar(basurero);
                eliminarProximaImagen = false;
            }
        }
    }

    //Metodo que llama a eliminar elemento de otra clase
    public static void eliminarElemento(MouseEvent event) {
        //Condcion para saber si se desea eliminar un objeto
        if (eliminarProximaImagen) {
            //Obtiene el objeto presionado y lo borra
            Object basura = event.getSource();
            pane.getChildren().remove(basura);

            eliminarProximaImagen = false;
            System.out.println("Se eliminó algo");

            //Para poder borrar un cable o un switch este tambien debe de borrar el ArrayList de cada uno y
            // verificar si el objeto obtenido es el mismo que alguno de los array
            int i = 0;
            //bucle cables
            while (i < Click.getCables().size()) {
                System.out.println("busca cable");
                if (basura.equals(Click.getCables().get(i))) {
                    System.out.println("se elimino cable");
                    Click.getCables().remove(i);
                }
                i++;
            }
            i=0;
            //bucles switch
            System.out.println(Click.getswitch().size());
            while (i < Click.getswitch().size()) {
                System.out.println("busca switch");
                if (basura.equals(Click.getswitch().get(i).getImageView())) {
                    System.out.println("se elimino switch");
                    pane.getChildren().remove(Click.getswitch().get(i).getCable());
                    Click.getswitch().remove(i);
                }
                i++;
            }

        }

    }

    public void activar(ImageView imageView) {
        Image image = new Image("/basureroActivo.png");
        this.basurero.setImage(image);
    }

    public void desactivar(ImageView imageView){
        Image image = new Image("/basurero.png");
        this.basurero.setImage(image);
    }
}