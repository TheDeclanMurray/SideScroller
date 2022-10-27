package org.headroyce.declanm2022;

import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;

public class BoxCharacter {

    protected Canvas view;
    public double x,y,width,height,VelY,grav;
    public boolean Right,Left,Jump,DoubleJump,lookingRight;
    private Point gunner;
    private LList<bullet> bullets;


    public BoxCharacter(Canvas c){
        view = c;
        width = 30;
        height = 46;
        VelY = 0;
        grav = 0.2;
        x = view.getWidth()/2 - width/2;
        y = view.getHeight()/2 - height/2;
        Right = false;
        Left = false;
        Jump = false;
        lookingRight = true;
        gunner = new Point(12,16);
        bullets = new LList<>();
    }

    public void shot(){

        Point origin = new Point(x+ width/2,y+29);

        bullet b = new bullet(origin,lookingRight);
        bullets.add(b);

    }

    public void move(Point mouse){

        VelY += grav;
        y += VelY;
        if(y+height > view.getHeight()){
            y = view.getHeight()-height;
            VelY = 0;
        }

        if(Right && Left){

        }else if(Right){
            x += 4;
            if(x+width > view.getWidth()){
                x = view.getWidth()-width;
            }
            lookingRight = true;
        }else if(Left){
            x -= 4;
            if(x < 0){
                x = 0;
            }
            lookingRight = false;
        }
        if(Jump && VelY == 0){
            VelY -= 7;
            Jump = false;
            DoubleJump = true;
        }else if(DoubleJump && Jump){
            if(VelY > -3){
                VelY -= 6;
            }else{
                VelY -= 2.5;
            }
            DoubleJump = false;
        }

        this.render(mouse);
    }

    private void render(Point p){
        GraphicsContext gc = view.getGraphicsContext2D();
        FileInputStream input;
        Image image;


        for(bullet bul:bullets){
            bul.render(view);
        }


        //Draw Gun
        if(p != null){

            try{
                input = new FileInputStream("assets/gun-AK47(2).png");
                image = new Image(input);


                Rotate rt = new Rotate();
                rt.setPivotX(x+gunner.x);
                rt.setPivotY(y+gunner.y);
                rt.setAngle(30);

                GridPane gridpane = new GridPane();
                gridpane.getChildren().add(new ImageView(image));

                Pane pane = new Pane();
                pane.getTransforms().addAll(rt);
                pane.getChildren().addAll(gridpane);

                if(lookingRight){
                    gc.drawImage(image,x+gunner.x,y+gunner.y);

                }else{
                    gc.drawImage(image,x+width-gunner.x,y+ gunner.y,-image.getWidth(), image.getHeight());

                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }


        //Draw Character
        try{
            input = new FileInputStream("assets/skin-punk2.png");
            image = new Image(input);

            if(lookingRight){
                gc.drawImage(image,x,y);

            }else{
                gc.drawImage(image,x+width,y,-width,height);

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }




    }

}