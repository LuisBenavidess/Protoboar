package com.example.protoboar;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;

public class Click {
    private final Pane pane;
    private Line linea;
    private double x;
    private double y;
    private boolean circuloClicked = false;
    private double circuloX, circuloY;
    private ImageView draggedImageView;
    private double initialMouseX;
    private double initialMouseY;
    private final Label label;
    private final ImageView led;
    private final ImageView basurero;
    private final bus[][] alimentacion;
    private boolean ledClicked;
    private boolean cableClicked;
    private Circle primercircle; //Utilizado en la creacion del led para almacenar posicion del primer circulo


    public Click(Pane pane, Label label, ImageView led, ImageView basurero, bus[][] alimentacion, boolean ledClicked, boolean cableClicked) {
        this.pane = pane;
        this.label = label;
        this.led = led;
        this.basurero = basurero;
        this.alimentacion = alimentacion;
        this.ledClicked = ledClicked;
        this.cableClicked = cableClicked;
    }

    public void setLedClicked(boolean ledClicked) {
        this.ledClicked = ledClicked;
    }

    public void setCableClicked(boolean cableClicked) {
        this.cableClicked = cableClicked;
    }

    // Manejar la acción cuando se presiona un círculo
    public void presionarCirculo(MouseEvent event) {
        Circle circulo = (Circle) event.getSource();
        circuloClicked = true;
        circuloX = circulo.getCenterX();
        circuloY = circulo.getCenterY() - 10;
        x = circulo.getCenterX();
        y = circulo.getCenterY();
        if (primercircle == null && ledClicked) {
            primercircle = circulo; // Asignación del primer círculo
            System.out.println("Primer círculo asignado");
        } else if (ledClicked && primercircle != null) {
            Circle segundoCirculo = circulo;
            System.out.println("Segundo círculo");
            crearCableEntreCirculos(primercircle, segundoCirculo);
            primercircle = null; // Reiniciar para permitir la selección de nuevos círculos
            System.out.println("Primer círculo reiniciado");
        } else if (cableClicked) {
            inicio(event);
        }
    }

    private void crearCableEntreCirculos(Circle c1, Circle c2) {
        if (c1 == null || c2 == null) {
            System.out.println("uno de los círculos es null.");
            return;
        }
        Line cable = new Line(c1.getCenterX(), c1.getCenterY(), c2.getCenterX(), c2.getCenterY());
        cable.setStroke(Color.RED);
        cable.setStrokeWidth(5);
        pane.getChildren().add(cable);
        // Calcular el punto medio de la línea
        double midX = (c1.getCenterX() + c2.getCenterX()) / 2;
        double midY = (c1.getCenterY() + c2.getCenterY()) / 2;
        // Colocar la imagen del LED en el punto medio
        Led led = new Led(pane, midX, midY);
        led.setConectado(true);
        primercircle = null;
    }

    // Manejar el inicio de un arrastre
    public void inicio(MouseEvent event) {
        Circle circulo_apret = (Circle) event.getSource();
        linea = new Line(circulo_apret.getCenterX(), circulo_apret.getCenterY(),
                circulo_apret.getCenterX(), circulo_apret.getCenterY());
        linea.setStroke(Color.GREEN);
        linea.setStrokeWidth(5);
        ((Pane) circulo_apret.getParent()).getChildren().add(linea);
        //movimiento de la línea
        circulo_apret.getParent().setOnMouseMoved(this::movimiento);
    }

    // Manejar el movimiento del ratón para actualizar la línea
    void movimiento(MouseEvent event) {
        if (linea != null) {
            linea.setEndX(event.getX());
            linea.setEndY(event.getY());
            linea.getParent().setOnMouseClicked(this::parar);
        }
    }

    // Detener el dibujo y finalizar el arrastre
    void parar(MouseEvent event) {
        if (linea != null) {
            for (int i = 0; i < 14; i++) {
                for (int j = 0; j < 30; j++) {
                    bus targetCircle = alimentacion[i][j];
                    if (targetCircle != null) {
                        double dx = event.getX() - targetCircle.getCenterX();
                        double dy = event.getY() - targetCircle.getCenterY();
                        double distance = Math.sqrt(dx * dx + dy * dy);

                        if (distance <= targetCircle.getRadius()) {
                            System.out.println("entra");
                            // Detener el dibujo
                            linea = null;
                            ((Pane) targetCircle.getParent()).setOnMouseMoved(null);
                            ((Pane) targetCircle.getParent()).setOnMouseClicked(null);
                            return; // Salir del método una vez que se haya encontrado un círculo
                        }
                    }
                }
            }
            System.out.println("no entra");
        }
    }

    // Manejar el inicio del arrastre de una imagen
    public void onImagePressed(MouseEvent event) {
        draggedImageView = (ImageView) event.getSource();
        initialMouseX = event.getSceneX();
        initialMouseY = event.getSceneY();
    }

    // Manejar el arrastre de una imagen
    public void onImageDragged(MouseEvent event) {
        if (draggedImageView != null) {
            double offsetX = event.getSceneX() - initialMouseX;
            double offsetY = event.getSceneY() - initialMouseY;
            draggedImageView.setTranslateX(draggedImageView.getTranslateX() + offsetX);
            draggedImageView.setTranslateY(draggedImageView.getTranslateY() + offsetY);
            initialMouseX = event.getSceneX();
            initialMouseY = event.getSceneY();
        }
    }

    // Manejar la liberación de una imagen
    public void onImageReleased(MouseEvent event) {
        if (draggedImageView != null && isOverTrashBin(draggedImageView)) {
            pane.getChildren().remove(draggedImageView);
        }
        draggedImageView = null;
    }

    // Verificar si una imagen está sobre el basurero
    private boolean isOverTrashBin(ImageView imageView) {
        double trashBinX = basurero.getLayoutX();
        double trashBinY = basurero.getLayoutY();
        double trashBinWidth = basurero.getFitWidth();
        double trashBinHeight = basurero.getFitHeight();
        double imageViewX = imageView.getLayoutX() + imageView.getTranslateX();
        double imageViewY = imageView.getLayoutY() + imageView.getTranslateY();
        return imageViewX >= trashBinX &&
                imageViewX <= trashBinX + trashBinWidth &&
                imageViewY >= trashBinY &&
                imageViewY <= trashBinY + trashBinHeight;
    }
}