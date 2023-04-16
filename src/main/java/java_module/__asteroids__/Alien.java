package java_module.__asteroids__;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;

import java.util.Random;

public class Alien extends GameObject{
    private Point2D target;
    private Point2D movement;
    double changeX = Math.cos(Math.toRadians(20));
    double changeY = Math.sin(Math.toRadians(20));
    public Alien(int x, int y){
        // create new polygon - same shape as ship
        super(new Polygon(-15, 0, 0, 10, 15, 0, 0, -10), x, y,"alien", ScreenUse.SINGULAR);
        target = new Point2D(0,0);
        Random rnd = new Random();

        super.getCharacter().setRotate(rnd.nextInt(360));

        int accelerationAmount = 20 + rnd.nextInt(10,20);
        for (int i = 0; i < accelerationAmount; i++) {
            accelerate();
        }
        this.movement = new Point2D(changeX * 5, changeY * 5);
    }
    public void update(Ship player){
        // set target of alien to player movement
        target = player.getLocation();
        updateAngle();
    }
    public Bullet shootBullet(){
        // periodically create bullets to shoot towards target
        return new Bullet((int) getCharacter().getTranslateX(), (int) getCharacter().getTranslateY());
    }
    private void updateAngle(){
        // update rotation of enemy as target moves
        double deltaX = target.getX();
        double deltaY =target.getY();
        double angle = Math.atan2(deltaY,deltaX);
        getCharacter().setRotate(Math.toDegrees(angle)  - 90);
    }
    public static Alien spawnRandom(){
        // create new Alien at random location ------- implement in constructor instead -------
        Random rnd = new Random();
        int x = rnd.nextInt(SceneController.WIDTH);
        int y = rnd.nextInt(SceneController.HEIGHT);
        return new Alien(x,y);
    }
    public void move(){
        // extend super.move to remove ship when beyond screen bounds
        if (this.beyondScreenBounds()){
            this.setLife(false);
        }
        super.move();
//        super.getCharacter().setRotate(super.getCharacter().getRotate());
    }
}
