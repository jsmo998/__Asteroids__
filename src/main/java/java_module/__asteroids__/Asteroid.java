package java_module.__asteroids__;


import java.util.Random;

public class Asteroid extends GameObject implements Movable{
    private final double rotation;
    private final AsteroidSizes size;
    public Asteroid(double x, double y, AsteroidSizes size){
        //create new polygon by calling asteroidMaker
        super(new PolygonMaker().createAsteroid(size), x, y, "asteroid", ScreenUse.INFINITE);
        this.size = size;

        // set random rotation
        Random rnd = new Random();
        super.getCharacter().setRotate(rnd.nextInt(360));
        this.rotation = 0.5 - rnd.nextDouble();

        // set speed depending on size
        switch (size){
            case SMALL -> {for(int i=0; i<12; i++){accelerate();}}
            case MEDIUM -> {for(int i=0; i<8; i++){accelerate();}}
            case LARGE -> {for(int i=0; i<3; i++){accelerate();}}
        }
    }
    public void move(){
        // extends super.move() to set character to constantly rotate
        super.move();
        super.getCharacter().setRotate(super.getCharacter().getRotate() + rotation);
    }

    public AsteroidSizes getSize(){
        return this.size;
    }
}