package java_module.__asteroids__;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class GameObject implements Movable{
    private final Polygon character; // variable name for accessing polygon of character
    private Point2D movement; // direction and speed of object
    private boolean isAlive; // flag to check life
    private final ScreenUse time;
    public GameObject(Polygon polygon, double x, double y, String s, ScreenUse time){
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
        // starting position
        this.movement = new Point2D(0, 0);
    }
    public Polygon getCharacter(){
        // returns polygon of character object
        return character;
    }
    public void move(){
        // changes x and y coordinates of object
        switch (time) {
            case SINGULAR -> {
                // singular items get from one side to the other
                this.character.setTranslateX(this.character.getTranslateX() + this.movement.getX());
                this.character.setTranslateY(this.character.getTranslateY() + this.movement.getY());
            }
            case INFINITE -> {
                this.character.setTranslateX(this.character.getTranslateX() + this.movement.getX());
                this.character.setTranslateY(this.character.getTranslateY() + this.movement.getY());
                // infinite items loop through the edges of the screen
                if (this.character.getTranslateX() < 0) {
                    this.character.setTranslateX(this.character.getTranslateX() + SceneController.WIDTH);
                }
                if (this.character.getTranslateX() > SceneController.WIDTH) {
                    this.character.setTranslateX(this.character.getTranslateX() % SceneController.WIDTH);
                }
                if (this.character.getTranslateY() < 0) {
                    this.character.setTranslateY(this.character.getTranslateY() + SceneController.HEIGHT);
                }
                if (this.character.getTranslateY() > SceneController.HEIGHT) {
                    this.character.setTranslateY(this.character.getTranslateY() % SceneController.HEIGHT);
                }
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
    public boolean beyondScreenBounds(){
        // check if items which don't loop the screen edges have gone beyond them
        return this.character.getTranslateX() < 0 || this.character.getTranslateX() > SceneController.WIDTH || this.character.getTranslateY() < 0 || this.character.getTranslateY() > SceneController.HEIGHT;
    }

}
