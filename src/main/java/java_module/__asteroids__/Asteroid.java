package java_module.__asteroids__;


public class Asteroid extends GameObject{

    private int size;
    private double rotation;
    public Asteroid(double x, double y, int size){
        //create new polygon by calling asteroidMaker
        super(new PolygonMaker().createAsteroid(), x, y, "asteroid");

    }
    public void move(){
        // extends super.move() to set character to constantly rotate
    }
}