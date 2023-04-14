package java_module.__asteroids__;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;

public class Ship extends GameObject{
    private final static int RESPAWN_WINDOW = 3; // respawn window in seconds
    public boolean respawnCalled; // respawn call flag
    private LocalDateTime spawned; // creation time to measure respawn window
    public Ship(int x, int y){
        // constructor creates new polygon, sets spawn time and calls isSafe
        super(new Polygon(-10,-10,20,0,-10,10), x, y, "ship", ScreenUse.INFINITE);
        this.spawned = LocalDateTime.now();
        isSafe();
    }
    public void respawn(int x, int y){
        // move character to starting position, set spawn and call isSafe
        respawnCalled = true;
        this.spawned = LocalDateTime.now();
        this.setLocation(x, y);
        this.setMovement(getStartHere());
        isSafe();
    }
    public boolean isSafe(){
        // sets character safe for 3 seconds when called
        Duration respawn_time = Duration.between(spawned, LocalDateTime.now());
        return respawn_time.toSeconds() < RESPAWN_WINDOW;
    }
    public void move(){
        // extends super.move() to include respawn
        if (isSafe()){
            this.getCharacter().setStroke(Color.LIGHTGREEN);
        }
        else this.getCharacter().setStroke(Color.WHITE);
        this.respawnCalled = false;
        super.move();

    }
    public void hyperspaceJump(){
        // relocate character in random location and call respawn
        Random rnd = new Random();
        var rndX = rnd.nextInt(SceneController.WIDTH);
        var rndY = rnd.nextInt(SceneController.HEIGHT);
        this.respawn(rndX, rndY);
    }
}