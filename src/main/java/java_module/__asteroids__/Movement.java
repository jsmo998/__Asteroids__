package java_module.__asteroids__;

public interface Movement {
    // this interface defines the ways a GameObject must be able to be manipulated
    void turnLeft();
    void turnRight();
    void move();
    void accelerate();
}
