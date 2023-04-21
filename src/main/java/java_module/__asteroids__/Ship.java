package java_module.__asteroids__;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;

public class Ship extends GameObject implements Movable, Controllable{
    private final static int RESPAWN_WINDOW = 3; // respawn window in seconds
    public static boolean respawnCalled; // respawn call flag
    private LocalDateTime spawned; // creation time to measure respawn window

    public Ship(int x, int y){
        // constructor creates new polygon, sets spawn time and calls isSafe
        super(new Polygon(-10,-10,20,0,-10,10), x, y, "ship", ScreenUse.INFINITE);
        this.spawned = LocalDateTime.now();
        isSafe();
    }
    public void respawn(int x, int y){
        // move character to starting position, set spawn and call isSafe
        this.spawned = LocalDateTime.now();
        this.setLocation(x, y);
        this.setMovement(getStartHere());
        isSafe();
    }
    public void turnLeft(){
        // changes orientation of object left
        this.getCharacter().setRotate(this.getCharacter().getRotate() - 3);
    }
    public void turnRight(){
        // changes orientation of object right
        this.getCharacter().setRotate(this.getCharacter().getRotate() + 3);
    }
    public void setLocation(int x, int y){
        this.getCharacter().setTranslateX(x);
        this.getCharacter().setTranslateY(y);

    }
    public Point2D getLocation(){
        double x = this.getCharacter().getTranslateX();
        double y = this.getCharacter().getTranslateY();
        return new Point2D(x,y);
    }
    public Point2D getStartHere(){
        double x = this.getCharacter().getTranslateX();
        double y = this.getCharacter().getTranslateY();
        return new Point2D(x,y);
    }
    public boolean isSafe(){
        // sets character safe for 3 seconds when called
        Duration respawnTime = Duration.between(spawned, LocalDateTime.now());
        return respawnTime.toSeconds() < RESPAWN_WINDOW;
    }
    public void move(){
        // extends super.move() to include respawn
        if (isSafe()){
                respawnCalled = true;
                this.getCharacter().setStroke(Color.GOLD);
        }
        else {
            respawnCalled = false;
            this.getCharacter().setStroke(Color.WHITE);
        }
        super.move();

    }
    public void hyperspaceJump(){
        // relocate character in random location and call respawn
        Random rnd = new Random();
        var rndX = rnd.nextInt(SceneController.WIDTH);
        var rndY = rnd.nextInt(SceneController.HEIGHT);
        this.respawn(rndX, rndY);
    }
    public void checkJump(){

    }
}