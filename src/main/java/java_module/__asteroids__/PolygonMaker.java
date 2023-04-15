package java_module.__asteroids__;

import javafx.scene.shape.Polygon;

import java.util.Random;

public class PolygonMaker {
    private double scale;
    public Polygon createAsteroid(AsteroidSizes size){
        // create a new polygon of angular shape
        Random rnd = new Random();

        // set size depending on desired asteroid
        switch (size){
            case SMALL -> scale = 10;
            case MEDIUM -> scale = 25;
            case LARGE -> scale = 40;
        }

        Polygon polygon = new Polygon();
        double c1 = Math.cos(Math.PI * 2 / 5);
        double c2 = Math.cos(Math.PI / 5);
        double s1 = Math.sin(Math.PI * 2 / 5);
        double s2 = Math.sin(Math.PI * 4 / 5);

        // set corner points of polygon
        polygon.getPoints().addAll(
                scale, 0.0,
                scale * c1, -1 * scale * s1,
                -1 * scale * c2, -1 * scale * s2,
                -1 * scale * c2, scale * s2,
                scale * c1, scale * s1);

        polygon.getPoints().replaceAll(aDouble -> aDouble + rnd.nextInt(5) - 2);

        return polygon;
    }
}
