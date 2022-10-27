package org.headroyce.declanm2022;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;

import javafx.scene.control.Tooltip;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;


/**
 * The entire workspace of the application.
 *
 * @author Brian Sea
 */
public class DrawingWorkspace extends Pane {

    private DrawingArea drawingArea;

    private Button openPlans;

    public DrawingWorkspace(){
        drawingArea = new DrawingArea(this);


        Button delete = new Button();
        delete.setTooltip(new Tooltip("Delete"));
        delete.setText("Delete");
        delete.setMinWidth(30);
        delete.setMinHeight(30);

        delete.setAlignment(Pos.BOTTOM_LEFT);
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {


            }
        });

        openPlans = new Button();
        openPlans.setTooltip(new Tooltip("Plans"));
        openPlans.setText("OP");
        openPlans.setMinWidth(50);
        openPlans.setMinHeight(50);

        openPlans.setLayoutY(0);
        openPlans.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(openPlans.getStyleClass().contains("active")){
                    openPlans.getStyleClass().remove("active");
                    openPlans.setEffect(null);
                }
                else {
                    openPlans.getStyleClass().add("active");
                    ColorAdjust ca = new ColorAdjust();
                    ca.setBrightness(-0.5);
                    openPlans.setEffect(ca);
                }
            }
        });

        drawingArea.prefHeightProperty().bind(this.heightProperty());
        drawingArea.prefWidthProperty().bind(this.widthProperty());
        this.getChildren().addAll(drawingArea);
        delete.layoutYProperty().bind(this.heightProperty().subtract(delete.heightProperty()));
        this.getChildren().add(delete);
        this.getChildren().add(openPlans);




        this.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {

                //System.out.println("Key Pressed: "+keyEvent.getCode());

                switch(keyEvent.getCode() ){
                    case D:
                        drawingArea.setRight(true);
                        break;
                    case A:
                        drawingArea.setLeft(true);
                        break;
                    case W:
                        drawingArea.setJump(true);
                        break;
                }
            }
        });

        this.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {

                //System.out.println("Key Released: "+keyEvent.getCode());

                switch(keyEvent.getCode() ){
                    case D:
                        drawingArea.setRight(false);
                        break;
                    case A:
                        drawingArea.setLeft(false);
                        break;
                    case W:
                        drawingArea.setJump(false);
                        break;
                }
            }
        });




    }


}