package com.example.protoboar;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;


import java.util.Objects;
import java.util.Set;

public class HelloController {
    @FXML
    private Pane pane;
    @FXML
    private bus[][] alimentacion;
    @FXML
    private int columnas;
    @FXML
    private int filas;
    @FXML
    private Label label;
    @FXML
    private Line linea;
    @FXML
    private ImageView led;
    @FXML
    private double x;
    @FXML
    private double y;
    @FXML
    private VBox imageContainerVBox;
    private ImageView draggedImageView;
    private double initialMouseX;
    private double initialMouseY;

    // La instancia actual del LED
    private boolean circuloClicked = false;
    private boolean circulo1Clicked = false;


    @FXML
    private double circuloX, circuloY;

    @FXML
    //Funcion inicial
    private void initialize() {
        alimentacion = new bus[14][30];

        //Creacion de circulos y numeros (Interfaz)
        crear_buses(37,52,2);
        crear_buses(37,122,5);
        crear_buses(37,276,5);
        crear_buses(37,413,2);
        numeros(33,376);
        numeros(33,100);

        System.out.println("Etapa 2");
        int i=0;
        while(i<14) {
            int j=0;
            while(j<30) {
                bus circulo = alimentacion[i][j];
                System.out.println("circulo: " + circulo);
                if(circulo != null) {
                    circulo.setOnMouseClicked(this::presionar_circulo);
                    circulo.setOnMouseEntered(event -> circulo.setFill(Color.RED));
                    circulo.setOnMouseExited(event -> circulo.setFill(Color.BLACK));
                }
                j++;
            }
            i++;
        }


    }

    @FXML
    //Funcion que crea los circulos
    private void crear_buses(int X,int Y, int FIL){
        // Variables a utilizar
        int col=0;
        int fil=0;
        int x=X;
        int y=Y;

        //bucle
        while(fil<FIL){
            col=0;

            while(col<29){
                //circulo
                bus circulo = new bus();
                circulo.setCenterX(x);
                circulo.setCenterY(y);
                circulo.setRadius(6);
                circulo.setFill(Color.BLACK);

                //Guardar el circulo dentro de la matriz
                alimentacion[filas][columnas] = circulo;
                alimentacion[filas][columnas].setFila(filas);
                alimentacion[filas][columnas].setColumna(columnas);
                pane.getChildren().add(circulo);
                x=x+18;
                col++;
                columnas++;
                if(col==29){
                    fil=fil+1;
                    filas=filas+1;
                    x=37;
                    y=y+22;
                }
            }
            col=0;
            columnas=0;
        }

    }

    @FXML
    private void mostrarImagen() {
        System.out.println("mostrar imagen");
        if (circuloClicked) {
            // Se crea una nueva instancia del LED en la posición del círculo clickeado
            Led led = new Led(pane, circuloX, circuloY);

            // Configura el estado de conexión si es necesario
            led.setConectado(true);
            System.out.println("mostrar imagen 2");
            // Reinicia el estado de clic del círculo
            circuloClicked = false;
        }
    }

    @FXML
    //Funcion que crea los numeros
    private void numeros(int X,int Y){
        int i=0;
        while(i<30){

            Label label = new Label(String.valueOf(i+1));
            label.setLayoutY(Y);
            label.setLayoutX(X);
            label.setRotate(-90);
            pane.getChildren().add(label);
            X=X+18;
            i++;
        }
    }

    @FXML
    private void presionar_circulo(MouseEvent event) {
        System.out.println("entra");
        circuloClicked = true;
        bus circulo = (bus) event.getSource();
        circulo.setOnMousePressed(this::inicio);
        // Almacena la posición del círculo clickeado
        circuloX = circulo.getCenterX();
        circuloY = circulo.getCenterY() - 10;
        x = circulo.getCenterX();
        y = circulo.getCenterY();
        // Llama a mostrarImagen para mostrar el LED en el círculo clickeado
        mostrarImagen();


    }

    @FXML
    private void inicio(MouseEvent event) {
        Circle circulo_apret = (Circle) event.getSource();
        linea = new Line(circulo_apret.getCenterX(), circulo_apret.getCenterY(),
                circulo_apret.getCenterX(), circulo_apret.getCenterY());
        linea.setStroke(Color.GREEN);
        linea.setStrokeWidth(5);
        ((Pane) circulo_apret.getParent()).getChildren().add(linea);

        // Manejar el movimiento del ratón para actualizar la línea
        circulo_apret.getParent().setOnMouseMoved(this::movimiento);
    }

    @FXML
    private void movimiento(MouseEvent event) {
        if (linea != null) {
            // Actualizar la posición de la línea para seguir el cursor
            linea.setEndX(event.getX());
            linea.setEndY(event.getY());
            linea.getParent().setOnMouseClicked(this::parar);

        }
    }

    @FXML
    private void parar(MouseEvent event) {

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
    @FXML
    private void onImagePressed(MouseEvent event) {
        draggedImageView = (ImageView) event.getSource();
        initialMouseX = event.getSceneX();
        initialMouseY = event.getSceneY();
    }

    @FXML
    private void onImageDragged(MouseEvent event) {
        if (draggedImageView != null) {
            double offsetX = event.getSceneX() - initialMouseX;
            double offsetY = event.getSceneY() - initialMouseY;

            draggedImageView.setTranslateX(draggedImageView.getTranslateX() + offsetX);
            draggedImageView.setTranslateY(draggedImageView.getTranslateY() + offsetY);

            initialMouseX = event.getSceneX();
            initialMouseY = event.getSceneY();
        }
    }

    @FXML
    private ImageView basurero;
    private boolean isOverTrashBin(ImageView imageView) {
        // Obtener las coordenadas del basurero
        double trashBinX = basurero.getLayoutX();
        double trashBinY = basurero.getLayoutY();
        double trashBinWidth = basurero.getFitWidth();
        double trashBinHeight = basurero.getFitHeight();

        // Obtener las coordenadas del ImageView arrastrado
        double imageViewX = imageView.getLayoutX() + imageView.getTranslateX();
        double imageViewY = imageView.getLayoutY() + imageView.getTranslateY();

        // Verificar si las coordenadas del ImageView están dentro del área del basurero
        return imageViewX >= trashBinX ||
                imageViewX <= trashBinX + trashBinWidth ||
                imageViewY >= trashBinY ||
                imageViewY <= trashBinY + trashBinHeight;
    }

    @FXML
    private void onImageReleased(MouseEvent event) {
        if (draggedImageView != null && isOverTrashBin(draggedImageView)) {
            // Eliminar la imagen del VBox
            imageContainerVBox.getChildren().remove(draggedImageView);
        }
        draggedImageView = null;
    }

}