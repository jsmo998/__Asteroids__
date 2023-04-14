package java_module.__asteroids__;

import javafx.scene.shape.Polygon;

public class Fragment extends GameObject{
    // creates breaking fragments as collision animation
    public double time_to_live;
    private double rotation;
    public Fragment(double x, double y){
        // create new polygon fragment
        super(new Polygon(2, -3, 2, 3, -3, 2, -3, -2), x, y, "fragment");
    }
    public void move(){
        // extend super.move() to add rotation to fragment
    }
}