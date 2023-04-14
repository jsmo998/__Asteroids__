package java_module.__asteroids__;

import javafx.scene.shape.Polygon;

import java.time.LocalDateTime;

public class Ship extends GameObject{
    private final static int respawn_window = 3; // respawn window in seconds
    public boolean respawn_called; // respawn call flag
    private LocalDateTime spawned; // creation time to measure respawn window
    public Ship(int x, int y){
        // construcor creates new polygon, sets spawn time and calls isSafe
        super(new Polygon(-10,-10,20,0,-10,10), x, y, "ship");

    }
    public void respawn(int x, int y){
        // move character to starting position, set spawn and call isSafe

    }
    public boolean isSafe(){
        // sets character safe for 3 seconds when called

    }
    public void move(){
        // extends super.move() to include respawn

    }
    public void hyperspaceJump(){
        // relocate character in random location and call respawn

    }
}