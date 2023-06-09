package java_module.__asteroids__;

import javafx.geometry.Point2D;

public interface Controllable {
    // defines objects which can be manipulated by the user
    void turnLeft();
    void turnRight();
    void setLocation(int x, int y);
    Point2D getLocation();
    Point2D getStartHere();
    void hyperspaceJump();
}
