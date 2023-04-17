package java_module.__asteroids__;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
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
        Random rnd = new Random();
        int x =0;
        int y=0;
        int side = rnd.nextInt(1, 5);
        if (side == 1){
            System.out.println("left");
            x = 0;
            y = rnd.nextInt(600);
        } else if(side == 2){
            System.out.println("right");
            x=800;
            y = rnd.nextInt(600);
        } else if(side==3){
            System.out.println("top");
            x = rnd.nextInt(800);
            y = 0;
        } else if(side == 4){
            System.out.println("bottom");
            x = rnd.nextInt(800);
            y = 600;
        }

        // randomly decide which side is 0 - top, bottom, left or right
        // then choose int in range from other side top/botton = 800, left/right = 600
        // set movement to point to other side of screen
        return new Alien(x, y);
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
