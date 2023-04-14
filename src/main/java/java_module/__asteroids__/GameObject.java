package java_module.__asteroids__;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class GameObject implements Movement{
    private final Polygon character; // variable name for accessing polygon of character
    private Point2D movement; // direction and speed of object
    private final Point2D startHere = new Point2D(0,0); // starting position
    private boolean isAlive; // flag to check life
    private final ScreenTime time;
    public GameObject(Polygon polygon, double x, double y, String s, ScreenTime time){
        // constructor creates new Polygon for all objects in game
        this.character = polygon;
        this.character.setTranslateX(x);
        this.character.setTranslateY(y);
        this.character.setStroke(Color.WHITE);
        this.character.setFill(Color.TRANSPARENT);
        this.character.setRotate(-90);
        this.character.setId(s);
        this.time = time;
        this.isAlive = true;
        this.movement = startHere;
    }
    public Polygon getCharacter(){
        // returns polygon of character object
        return character;
    }
    public void turnLeft(){
        // changes orientation of object left
        this.character.setRotate(this.character.getRotate() - 5);
    }
    public void turnRight(){
        // changes orientation of object right
        this.character.setRotate(this.character.getRotate() + 5);
    }
    public void move(){
        // changes x and y coordinates of object
        switch (time){
            case SINGULAR:
                // singular items get from one side to the other
                this.character.setTranslateX(this.character.getTranslateX() + this.movement.getX());
                this.character.setTranslateY(this.character.getTranslateY() + this.movement.getY());
                break;
            case INFINITE:
                // infinite items loop through the edges of the screen
                if(this.character.getTranslateX() < 0){
                    this.character.setTranslateX(this.character.getTranslateX() + SceneController.WIDTH);
                }
                if (this.character.getTranslateX() > SceneController.WIDTH){
                    this.character.setTranslateX(this.character.getTranslateX() % SceneController.WIDTH);
                }
                if(this.character.getTranslateY() < 0){
                    this.character.setTranslateY(this.character.getTranslateY() + SceneController.HEIGHT);
                }
                if(this.character.getTranslateY() > SceneController.HEIGHT){
                    this.character.setTranslateY(this.character.getTranslateY() % SceneController.HEIGHT);
                }
        }
    }
    public void accelerate(){
        // changes x and y coordinates according to rotation
        double changeX = Math.cos(Math.toRadians(this.character.getRotate()));
        double changeY = Math.sin(Math.toRadians(this.character.getRotate()));

        changeX *= 0.05;
        changeY *= 0.05;

        this.movement = this.movement.add(changeX,changeY);
    }
    public Point2D getMovement(){
        // returns velocity of object
        return movement;
    }
    public void setMovement(Point2D mv){
        // sets velocity of object
        movement = mv;
    }
    public Point2D getStartHere(){
        // gets starting point for object
        return startHere;
    }
    public boolean checkHit(Polygon other){
        // checks object collision with other
        return this.character.getBoundsInParent().intersects(other.getBoundsInParent());
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
