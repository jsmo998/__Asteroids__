package java_module.__asteroids__;

import javafx.scene.shape.Polygon;

import java.util.Random;

public class PolygonMaker {
    public Polygon createAsteroid(){
        // create a new polygon of angular shape
        Random rnd = new Random();

        double size = 10 + rnd.nextInt(20);

        Polygon polygon = new Polygon();
        double c1 = Math.cos(Math.PI * 2 / 5);
        double c2 = Math.cos(Math.PI / 5);
        double s1 = Math.sin(Math.PI * 2 / 5);
        double s2 = Math.sin(Math.PI * 4 / 5);

        // set corner points of polygon
        polygon.getPoints().addAll(
                size, 0.0,
                size * c1, -1 * size * s1,
                -1 * size * c2, -1 * size * s2,
                -1 * size * c2, size * s2,
                size * c1, size * s1);

        polygon.getPoints().replaceAll(aDouble -> aDouble + rnd.nextInt(5) - 2);

        return polygon;
    }
}
