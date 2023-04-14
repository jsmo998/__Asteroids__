package java_module.__asteroids__;

import javafx.scene.shape.Polygon;

public class Bullet extends GameObject{
    public Bullet(int x, int y){
        // constructor creates new polygon in Bullet dimensions
        super(new Polygon(1, -1, 1, 1, -1, 1, -1, -1), x, y, "bullet");
    }
    public void move(){
        // unlike super.move(), bullets will not move on the infinite screen but finish at edge
    }
}
