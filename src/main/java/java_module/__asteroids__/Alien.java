package java_module.__asteroids__;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;

public class Alien extends GameObject{
    private Point2D target;
    public Alien(int x, int y){
        // create new polygon - same shape as ship
        super(new Polygon(-10,-10, 20, 0, -10, -10), x, y,"alien");

    }
    public void update(Ship player){
        // set target of alien to player movement
    }
    public Bullet shootBullet(){
        // periodically create bullets to shoot towards target
    }
    private void updateAngle(){
        // update rotation of enemy as target moves
    }
    public static Alien spawnRandom(){
        // create new Alien at random location ------- implement in constructor instead -------
    }
}
