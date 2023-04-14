package java_module.__asteroids__;

import javafx.scene.shape.Polygon;

public class Bullet extends GameObject{
    public Bullet(int x, int y){
        // constructor creates new polygon in Bullet dimensions
        super(new Polygon(1, -1, 1, 1, -1, 1, -1, -1), x, y, "bullet", ScreenUse.SINGULAR);
    }
}
