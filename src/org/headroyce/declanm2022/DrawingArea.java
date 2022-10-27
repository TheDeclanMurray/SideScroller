package org.headroyce.declanm2022;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * The main drawing canvas for our application
 *
 * @author Brian Sea
 */
public class DrawingArea extends StackPane {

    private Point mousePoint;

    private BoxCharacter character;
    private double angle;
    private Pane superPane;

    public static final int GAME_STEP_TIMER = 17;
    private Animation anim;

    // The main drawing canvas
    private Canvas mainCanvas;

    private DrawingWorkspace mainWorkspace;

    public DrawingArea(DrawingWorkspace mw){
        mousePoint = new Point(0,0);
        angle = 0;
        superPane = new Pane();


        anim = new Animation();
        this.onResume();

        mainWorkspace = mw;
        mainCanvas = new Canvas();

        // Force the canvas to resize to the screen's size

        mainCanvas.widthProperty().bind(this.widthProperty());
        mainCanvas.heightProperty().bind(this.heightProperty());

        // Attach mouse handlers to the canvas
        EventHandler<MouseEvent> handler = new MouseHandler();
        mainCanvas.addEventHandler(MouseEvent.MOUSE_PRESSED, handler);
        mainCanvas.addEventHandler(MouseEvent.MOUSE_RELEASED, handler);
        mainCanvas.addEventHandler(MouseEvent.MOUSE_MOVED, handler);
        mainCanvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, handler);

        this.getChildren().add(mainCanvas);
    }

    /**
     * Render the viewable canvas
     */
    public void renderWorld(){

        this.getChildren().remove(superPane);
        this.getChildren().remove(mainCanvas);

        try{
            FileInputStream input = new FileInputStream("assets/gun-AK47(2).png");
            Image image = new Image(input);

            javafx.scene.transform.Translate translate = new Translate();

            Rotate rt = new Rotate();

            superPane = new Pane();

            if (mousePoint.x > 205) {   //mousePoint.x > 205
                rt.setPivotX(205);
                rt.setPivotY(215);
                translate.setX(200);
                translate.setY(200);
                double xDif = mousePoint.x - 205;
                double yDif = mousePoint.y - 215;
                double tan = Math.atan(yDif/xDif);
                double deg = Math.toDegrees(tan);
                angle = deg;
                if(angle > 0){
                    angle = 0;
                }
                if(angle < -60){
                    angle = -60;
                }
            }

            if(mousePoint.x < 205){
                superPane.setScaleX(-1);
                rt.setPivotY(215);
                rt.setPivotX(mainCanvas.getWidth()-200+5);
                translate.setY(200);
                translate.setX(mainCanvas.getWidth()-200);
                double xDif = mousePoint.x - 205;
                double yDif = mousePoint.y - 215;
                double tan = Math.atan(-yDif/xDif);
                double deg = Math.toDegrees(tan);
                angle = deg;
                if(angle > 0){
                    angle = 0;
                }
                if(angle < -60){
                    angle = -60;
                }
            }

            //angle+=1;
            rt.setAngle(angle);


            superPane.getTransforms().addAll(rt);
            superPane.getTransforms().addAll(translate);
            superPane.getChildren().addAll(new ImageView(image));


            this.getChildren().addAll(superPane);
            this.getChildren().add(mainCanvas);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        GraphicsContext gc = mainCanvas.getGraphicsContext2D();
        gc.clearRect(0,0, mainCanvas.getWidth(), mainCanvas.getHeight());

        character.move(mousePoint);
    }

    /**
     * Helps to handle all of the mouse events on the canvas
     */
    private class MouseHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event){

            Point p = new Point(event.getX(), event.getY());

            if(event.getEventType().equals(MouseEvent.MOUSE_PRESSED)){
                character.shot();
            }
            else if( event.getEventType().equals(MouseEvent.MOUSE_RELEASED)){

            }
            else if( event.getEventType().equals(MouseEvent.MOUSE_MOVED)){
                mousePoint = p;
            }
            else if( event.getEventType().equals(MouseEvent.MOUSE_DRAGGED)){

            }
        }
    }


    public void onPause(){
        anim.stop();
    }
    public void onResume(){
        System.out.println("Game Resumed");
        anim.start();
    }

    private class Animation extends AnimationTimer {

        private long lastUpdate = 0;
        private long timeElapsed;
        private boolean FirstRun = true;

        @Override
        public void handle(long now) {
            timeElapsed = (now - lastUpdate)/100000;

            if(timeElapsed > GAME_STEP_TIMER){

                if(FirstRun){
                    character = new BoxCharacter(mainCanvas);
                    FirstRun = false;
                }
                renderWorld();

                lastUpdate = now;
            }
        }
    }

    public void setRight(Boolean a){
        character.Right = a;
    }
    public void setLeft(Boolean a){
        character.Left = a;
    }
    public void setJump(Boolean a){
        character.Jump = a;
    }

}