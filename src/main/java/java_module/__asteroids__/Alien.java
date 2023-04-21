package java_module.__asteroids__;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;

import java.util.Random;

public class Alien extends GameObject implements Movable{
    private Point2D target;
    private static final double SPEED =1 ;
    public enum AlienSpawn {
        // used to decide which side of the screen the alien will appear
        TOP,
        BOTTOM,
        LEFT,
        RIGHT,
    }
    public Alien(int x, int y){
        // create new polygon - same shape as ship
        super(new Polygon(-15, 0, 0, 10, 15, 0, 0, -10), x, y,"alien", ScreenUse.SINGULAR);
        target = new Point2D(0,0);
    }
    public void update(Ship player){
        // set target of alien to player movement
        target = new Point2D(player.getCharacter().getTranslateX(),player.getCharacter().getTranslateY());
        updateAngle();
    }
    public Bullet shootBullet(Ship player){
        // periodically create bullets to shoot towards target
        Bullet bullet = new Bullet((int) getCharacter().getTranslateX(), (int) getCharacter().getTranslateY());
        double dx = player.getLocation().getX() - bullet.getCharacter().getTranslateX();
        double dy = player.getLocation().getY() - bullet.getCharacter().getTranslateY();
        double angle = Math.atan2(dy, dx);
        bullet.getCharacter().setRotate(Math.toDegrees(angle));
        bullet.setMovement(new Point2D(Math.cos(angle), Math.sin(angle)).normalize().multiply(3));
        return bullet;
    }
    private void updateAngle(){
        // update rotation of enemy as target moves
        double deltaX = target.getX();
        double deltaY =target.getY();
        double angle = Math.atan2(deltaY,deltaX);
        getCharacter().setRotate(Math.toDegrees(angle)  - 90);
    }
    public static Alien spawnRandom(){
        Random rnd = new Random();
        int x = 0;
        int y = 0;
        AlienSpawn side = AlienSpawn.values()[rnd.nextInt(AlienSpawn.values().length)];
        switch (side){
            case TOP -> x = rnd.nextInt(800);
            case BOTTOM -> {x = rnd.nextInt(800); y = 600;}
            case LEFT -> y = rnd.nextInt(600);
            case RIGHT -> {x=800; y = rnd.nextInt(600);}
        }
        // set movement to point to other side of screen
        return new Alien(x, y);
    }

    public void move() {
        if (this.beyondScreenBounds()) {
            this.setLife(false);
        } else {
            double dx, dy;
            double spawnX = this.getSpawnX();
            double spawnY = this.getSpawnY();

            // Determine direction towards opposite corner
            dx = Double.compare(spawnX, this.getCharacter().getTranslateX());

            dy = Double.compare(spawnY, this.getCharacter().getTranslateY());

            // Move the Alien towards opposite corner
            this.getCharacter().setTranslateX(this.getCharacter().getTranslateX() + dx*SPEED);
            this.getCharacter().setTranslateY(this.getCharacter().getTranslateY() + dy*SPEED);
        }
    }

    public double getSpawnX() {
        if (this.getCharacter().getTranslateX() < 0) { // moving right
            return SceneController.WIDTH;
        } else { // moving left
            return 0;
        }
    }

    public double getSpawnY() {
        if (this.getCharacter().getTranslateY() < 0) { // moving down
            return SceneController.HEIGHT;
        } else { // moving up
            return 0;
        }
    }
}
