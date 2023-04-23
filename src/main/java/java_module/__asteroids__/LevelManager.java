package java_module.__asteroids__;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Random;

public class LevelManager {
    private final AtomicInteger level;
    private final SceneController sc;
    private final Ship player;
    private final List<Asteroid> asteroidList;
    public final List<Alien> alienList;
    private int spawnChance;
    public LevelManager(SceneController sc, Ship player){
        this.sc=sc;
        this.player=player;
        this.level = new AtomicInteger(1);
        this.spawnChance=0;
        this.asteroidList = new ArrayList<>();
        this.alienList= new ArrayList<>();
        this.addAsteroids();

    }
    private void addAsteroids(){
        Random rnd = new Random();
        for (int i=0; i<this.level.get(); i++){
            boolean hit = true;
            Asteroid a;
            do {
                double x = rnd.nextDouble(800);
                double y = rnd.nextDouble(600);
                AsteroidSizes size = AsteroidSizes.LARGE;
                a = new Asteroid(x, y, size);
                hit=a.checkHit(this.player.getCharacter());
            }
            while (hit==true);
            this.asteroidList.add(a);
            this.sc.addAsteroid(a);
        }
    }
    public void addAliens(Alien a){
        this.alienList.add(a);
    }

    public void spawnChance(){

        if (this.level.get()>1){
            Random rnd = new Random();
            int i = rnd.nextInt(spawnChance);
            if (i==0){
                System.out.println("Alien time");
                Alien alien= Alien.spawnRandom();
                this.addAliens(alien);
                sc.addAlien(alien);
            }
        }
    }

        

    public void moveAsteroids(){
        asteroidList.forEach(Asteroid::move);
    }
    public void moveAliens(){
        alienList.forEach(Alien::move);
        for (int i = 0; i < alienList.size(); i++){
            alienList.get(i).update(player);
        }
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

    public void alienHit(Alien a){
        a.setLife(false);
        this.sc.addPoints(100);
        this.sc.removeAlien(a);
        this.alienList.remove(a);
    }

    public boolean bulletHitAlien(Bullet bullet){
        boolean hit = false;
        for (Alien a: this.alienList){
            hit=a.checkHit(bullet.getCharacter());
            if (hit){
                System.out.println("Ladies and gentlemen, we got 'em");
                this.alienHit(a);
                bullet.setLife(false);
                return hit;
            }
        }
        return hit;
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
            this.spawnChance=(int)Math.round(5000/Math.log(this.level.get()));
            System.out.println(spawnChance);
            if (sc.JUMPS<3){
                sc.JUMPS+=1;
            }

            return true;}
        return false;
    }
    public AtomicInteger getLevel(){
        return this.level;
    }

}