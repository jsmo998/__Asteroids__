package java_module.__asteroids__;


import java.util.Random;

public class Asteroid extends GameObject{
    private final double rotation;
    public Asteroid(double x, double y, AsteroidSizes size){
        //create new polygon by calling asteroidMaker
        super(new PolygonMaker().createAsteroid(), x, y, "asteroid", ScreenUse.INFINITE);

        // set random rotation
        Random rnd = new Random();
        super.getCharacter().setRotate(rnd.nextInt(360));
        this.rotation = 0.5 - rnd.nextDouble();

        // set speed
        int acc = 1 + rnd.nextInt(10);
        for (int i=0; i<acc; i++){
            accelerate();
        }

        // scale asteroid depending on size
        switch (size) {
            case SMALL -> setScale(0.7);
            case MEDIUM -> setScale(1.2);
            case LARGE -> setScale(2);
        }
    }
    public void move(){
        // extends super.move() to set character to constantly rotate
        super.move();
        super.getCharacter().setRotate(super.getCharacter().getRotate() + rotation);
    }
    public void setScale(double x){
        super.getCharacter().setScaleX(x);
        super.getCharacter().setScaleY(x);
    }
}