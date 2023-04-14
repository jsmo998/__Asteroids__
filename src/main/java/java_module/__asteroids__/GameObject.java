package java_module.__asteroids__;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;

public class GameObject implements Movement{
    private Polygon character;
    private Point2D movement; // direction and speed of object
    private final Point2D startHere = new Point2D(0,0); // starting position
    private boolean isAlive; // flag to check life
    public GameObject(Polygon polygon, double x, double y, String s){
        // constructor creates new Polygon for all objects in game

    }
    public Polygon getCharacter(){
        // returns polygon of character object

    }
    public void turnLeft(){
        // changes orientation of object left

    }
    public void turnRight(){
        // changes orientation of object right

    }
    public void move(){
        // changes x and y coordinates of object - sets bounds to edge of window

    }
    public void accelerate(){
        // changes x and y coordinates according to orientation
    }
    public Point2D getMovement(){
        // returns velocity of object

    }
    public void setMovement(Point2D mv){
        // sets velocity of object
        movement = mv;
    }
    public Point2D getStartHere(){
        // gets starting point for object

    }
    public void checkHit(Polygon other){
        // checks object collision with other

    }
    public boolean isAlive(){
        // return flag value for life
        return isAlive;
    }
    public void setLife(boolean b){
        // set flag value for life
        isAlive = b;
    }
}
