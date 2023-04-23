package java_module.__asteroids__;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;

public interface Movable {
    // defines objects which move across the game window
    void move();
    void accelerate();
    Point2D getMovement();
    void setMovement(Point2D mv);
    boolean checkHit(Polygon other);
    boolean isAlive();
    void setLife(boolean b);
    boolean beyondScreenBounds();
}
