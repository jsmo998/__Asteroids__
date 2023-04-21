package java_module.__asteroids__;

public interface Movement {
    // TODO: implement other interfaces to define objects properly: game object, controllabel object (player), ... ?
    // this interface defines the basic ways a GameObject must be able to be manipulated
    void turnLeft();
    void turnRight();
    void move();
    void accelerate();

}
