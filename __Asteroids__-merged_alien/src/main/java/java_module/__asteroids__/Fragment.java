package java_module.__asteroids__;

import javafx.scene.shape.Polygon;

import java.time.Duration;
import java.time.LocalDateTime;

public class Fragment extends GameObject{
    // creates breaking fragments as collision animation
    public double timeToLive;
    private final double rotation;
    private final LocalDateTime created;
    public Fragment(double x, double y){
        // create new polygon fragment
        super(new Polygon(2, -3, 2, 3, -3, 2, -3, -2), x, y, "fragment", ScreenUse.SINGULAR);
        this.created = LocalDateTime.now();
        this.rotation = 5;

        for (int i=0; i<13; i++){
            accelerate();
        }
    }
    public boolean removeIn(){
        Duration removeIn = Duration.between(created, LocalDateTime.now());
        return removeIn.toSeconds() < timeToLive;
    }
    public void move(){
        // extend super.move() to add rotation to fragment
        if (removeIn()){
            getCharacter().setRotate(getCharacter().getRotate() + rotation);
            super.move();
        }
    }
}