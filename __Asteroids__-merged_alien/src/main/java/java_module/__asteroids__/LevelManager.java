package java_module.__asteroids__;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class LevelManager {
    private final AtomicInteger level;
    private final SceneController sc;
    private final List<Asteroid> asteroidList;
    public LevelManager(SceneController sc){
        this.sc=sc;
        this.level = new AtomicInteger(1);
        this.asteroidList = new ArrayList<>();
        this.addAsteroids();
    }
    private void addAsteroids(){
        // TODO: make sure not spawned on top of player
        Random rnd = new Random();
        double x = rnd.nextDouble(800);
        double y = rnd.nextDouble(600);

        for (int i=0; i<this.level.get(); i++){
            AsteroidSizes size = AsteroidSizes.LARGE;
            Asteroid a = new Asteroid(x, y, size);
            this.asteroidList.add(a);
            this.sc.addAsteroid(a);
        }

    }
    public void moveAsteroids(){
        asteroidList.forEach(Asteroid::move);
    }
    public void asteroidHit(Asteroid a){
        a.setLife(false);
        AsteroidSizes size = a.getSize();
        switch (size) {
            case LARGE -> {
                Asteroid a1 = new Asteroid(a.getCharacter().getTranslateX(), (int) a.getCharacter().getTranslateY(), AsteroidSizes.MEDIUM);
                Asteroid a2 = new Asteroid(a.getCharacter().getTranslateX(), (int) a.getCharacter().getTranslateY(), AsteroidSizes.MEDIUM);
                Collections.addAll(this.asteroidList, a1, a2);
                this.sc.addAsteroid(a1);
                this.sc.addAsteroid(a2);
                this.sc.addPoints(20);
            }
            case MEDIUM -> {
                Asteroid a3 = new Asteroid(a.getCharacter().getTranslateX(), (int) a.getCharacter().getTranslateY(), AsteroidSizes.SMALL);
                Asteroid a4 = new Asteroid(a.getCharacter().getTranslateX(), (int) a.getCharacter().getTranslateY(), AsteroidSizes.SMALL);
                Collections.addAll(this.asteroidList, a3, a4);
                this.sc.addAsteroid(a3);
                this.sc.addAsteroid(a4);
                this.sc.addPoints(10);
            }
            case SMALL -> this.sc.addPoints(5);
        }
        this.sc.removeAsteroid(a);
        this.asteroidList.remove(a);
    }

    public boolean bulletHitAsteroid(Bullet bullet){
        boolean hit = false;
        for (Asteroid a: this.asteroidList){
            hit=a.checkHit(bullet.getCharacter());
            if (hit){
                this.asteroidHit(a);
                bullet.setLife(false);
                return hit;
            }
        }
        return hit;
    }
    public boolean playerHitAsteroid(Ship player){
        boolean hit = false;
        for (Asteroid a: this.asteroidList){
            hit=a.checkHit(player.getCharacter());
            if (hit){
                return hit;
            }
        }
        return hit;
    }
    public boolean levelup(){
        if (this.asteroidList.size()==0){
            this.level.addAndGet(1);
            this.addAsteroids();
            //TODO: Increase score?
            return true;}
        return false;
    }
    public AtomicInteger getLevel(){
        return this.level;
    }

}


// - List of asteroids
// - Level tracker
// Methods
// - Start Level
//     - Generate Asteroids
// - Track Level
// - Increment Level