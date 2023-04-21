package java_module.__asteroids__;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;

public class Bullet extends GameObject{
    public double directionX;
    public double directionY;

    private boolean directionSet;
    private double velocityX; // add x speed
    private double velocityY;

    public Bullet(int x, int y){
        // constructor creates new polygon in Bullet dimensions
        super(new Polygon(1, -1, 1, 1, -1, 1, -1, -1), x, y, "bullet", ScreenUse.SINGULAR);

    }
    public void move(){
        // extend super.move to remove bullet if x,y is beyond screen bounds
        if (this.beyondScreenBounds()){
            this.setLife(false);
        }
        super.move();
    }
    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    // set y speed
    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    public void setDirection(double translateX,double translateY) {
        directionX = translateX;
        directionY = translateY;
        directionSet = true;
    }
    public void update() {
        if (directionSet) { // if there is new direction
            setVelocityX(directionX); // apply new direction on x
            setVelocityY(directionY); // apply new direction on y
            directionSet = false; // set redirection to false
        }
        super.update();
    }

}
