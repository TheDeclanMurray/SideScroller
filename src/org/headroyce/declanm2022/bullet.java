package org.headroyce.declanm2022;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class bullet {

    public Point location;
    public Boolean orientation;
    private double radius, VelX;

    public bullet(Point p, Boolean RightFacing){
        location = new Point(p.x,p.y);
        orientation = RightFacing;
        radius = 4;
        VelX = 5;
    }


    public void render(Canvas view){
        if(orientation){
            location.x += VelX;
        }else{
            location.x -= VelX;
        }

        GraphicsContext gc = view.getGraphicsContext2D();

        gc.fillOval(location.x-radius, location.y-radius, 2*radius, 2*radius);

    }

}