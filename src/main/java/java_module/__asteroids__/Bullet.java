package java_module.__asteroids__;

import javafx.scene.shape.Polygon;

public class Bullet extends GameObject{
    public Bullet(int x, int y){
        // constructor creates new polygon in Bullet dimensions
        super(new Polygon(1, -1, 1, 1, -1, 1, -1, -1), x, y, "bullet", ScreenUse.SINGULAR);
    }
    public void move(){
        // extend super.move to remove bullet if x,y is beyond screen bounds
        if (this.beyondScreenBounds()){
            this.setLife(false);
        }
        super.move();
    }
}
