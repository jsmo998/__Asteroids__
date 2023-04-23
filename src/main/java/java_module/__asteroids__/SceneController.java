package java_module.__asteroids__;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class SceneController extends SceneFiller{
    private Pane pane;
    public static int WIDTH = 800;
    public static int HEIGHT = 600;
    public final AtomicInteger LIVES  = new AtomicInteger();
    public int JUMPS;
    public int highscore;
    public String LEVEL;
    private final AtomicInteger points = new AtomicInteger(); // dynamically count points during the game loop

    public void home(Stage stage){
        //method to show home screen of game

        // create highscores file if not exists
        try {
            File highscore = new File("highscores.txt");
            if (highscore.createNewFile()){
                PrintWriter writer = new PrintWriter("highscores.txt");
                writer.println("000");
                writer.close();
            }
        } catch (IOException e){
            System.out.println("An error occurred when creating highscores file.");
            e.printStackTrace();
        }

        // once file exists, read highscore and set variable
        try {
            BufferedReader reader = new BufferedReader(new FileReader("highscores.txt"));
            String line;
            line = reader.readLine();
            highscore = Integer.parseInt(line);
            reader.close();
        } catch (IOException e){
            e.printStackTrace();
        }

        // create and add all static objects for the screen
        pane = createBackground();
        Label title = createLabel("Asteroids", WIDTH/3.5, HEIGHT/5.0, "header");
        Button start = createButton("start", WIDTH/3.0, HEIGHT/2.2);
        Button info = createButton("info", WIDTH/3.0, HEIGHT/1.7);
        Label fame = createLabel("high-score: "+highscore, WIDTH/3.5, HEIGHT/1.3, "fame");

        start.setOnAction(actionEvent -> startGame(stage));
        info.setOnAction(actionEvent -> info(stage));

        pane.getChildren().add(title);
        pane.getChildren().add(start);
        pane.getChildren().add(info);
        pane.getChildren().add(fame);

        Scene scene = new Scene(pane);
        // link stylesheet
        scene.getStylesheets().add(Objects.requireNonNull(SceneController.class.getResource("stylesheet.css")).toExternalForm());
        stage.setTitle("Asteroids - Home");
        stage.setScene(scene);
        stage.show();
    }
    public void startGame(Stage stage){
        // method to start game loop
        points.set(0);
        List<Bullet> bulletList = new ArrayList<>();
        List<Node> staticElementsList = new ArrayList<>();
        

        List<Bullet> alienBullets=new ArrayList<>();

        // create and add all static elements
        pane = createBackground();
        Button exit = createButton("< exit", 15, 550);
        Label level = createLabel("level 1", WIDTH/2.5, 15, "level");
        Label score = createLabel("score: 0", 20, 510, "score");
        Label lives = createLabel("lives: ♥ ♥ ♥", WIDTH*0.3, 550, "lives");
        Label jumps = createLabel("hyperspace jumps: ✷ ✷ ✷", WIDTH*0.6, 550, "lives");

        // keep all static objects in list and add to pane
        Collections.addAll(staticElementsList, exit, level, score, lives, jumps);

        // create player at center
        Ship player = new Ship(WIDTH/2, HEIGHT/2);
        LIVES.set(3);
        JUMPS = 3;
        LevelManager levelManager = new LevelManager(this, player);


        // add all objects to pane
        pane.getChildren().add(player.getCharacter());
        staticElementsList.forEach(node -> pane.getChildren().add(node));
        //set different rotate of each bullet and time gap between them, so they can show seperately on the screen


        Scene scene = new Scene(pane);
        stage.setTitle("Asteroids");
        scene.getStylesheets().add(Objects.requireNonNull(SceneController.class.getResource("stylesheet.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();

        Map<KeyCode, Boolean> pressedKeys = new HashMap<>();
        Map<KeyCode, Boolean> pressedOnce = new HashMap<>();
        scene.setOnKeyPressed(KeyEvent -> {
            pressedKeys.put(KeyEvent.getCode(), Boolean.TRUE);
            pressedOnce.put(KeyEvent.getCode(), Boolean.TRUE);
        });
        scene.setOnKeyReleased(KeyEvent -> {
            pressedKeys.put(KeyEvent.getCode(), Boolean.FALSE);
            pressedOnce.put(KeyEvent.getCode(), Boolean.FALSE);
        });


        Alien alien= Alien.spawnRandom();
        levelManager.addAliens(alien);
        Timeline bulletTimeline = new Timeline(new KeyFrame(Duration.millis(500), event -> {
                    if (alien.isAlive()){
                            Bullet alienBullet = alien.shootBullet(player);
                            alienBullet.setDirection(player.getCharacter().getTranslateX(),player.getCharacter().getTranslateY());
                            alienBullet.getCharacter().setRotate(alien.getCharacter().getRotate());
                            alienBullets.add(alienBullet);
                            alienBullet.accelerate();
                            alienBullet.setMovement(alienBullet.getMovement().normalize().multiply(3));
        
                            // add player velocity to bullet so that bullet is always faster than ship
                            // var v = player.getMovement().add(alienBullet.getMovement());
                            // alienBullet.setMovement(v);
        
                            pane.getChildren().add(alienBullet.getCharacter());
                    }
                }));
        // set Timeline cycle times
                bulletTimeline.setCycleCount(1000);
        // launch Timeline
                bulletTimeline.play();
                pane.getChildren().add(alien.getCharacter());

        AnimationTimer gameLoop = new AnimationTimer(){
            public void handle(long now){
                
                if (pressedKeys.getOrDefault(KeyCode.A, false)) {
                    player.turnLeft();
                }
                if (pressedKeys.getOrDefault(KeyCode.D, false)) {
                    player.turnRight();
                }
                if (pressedKeys.getOrDefault(KeyCode.W, false)) {
                    player.accelerate();
                }
                // use separate hash map to read bullet call - clear on input to shoot only one bullet on key press rather than a whole stream
                if (pressedOnce.getOrDefault(KeyCode.E, false)) {
                    Bullet bullet = new Bullet((int) player.getCharacter().getTranslateX(), (int) player.getCharacter().getTranslateY());
                    bullet.getCharacter().setRotate(player.getCharacter().getRotate());
                    bulletList.add(bullet);
                    bullet.accelerate();
                    bullet.setMovement(bullet.getMovement().normalize().multiply(3));

                    // add player velocity to bullet so that bullet is always faster than ship
                    var v = player.getMovement().add(bullet.getMovement());
                    bullet.setMovement(v);
                    pane.getChildren().add(bullet.getCharacter());
                    pressedOnce.clear();
                }
                if (pressedOnce.getOrDefault(KeyCode.J, false)){
                    // uses same hashmap as bullet call
                    if (JUMPS>0){
                        player.hyperspaceJump();
                        JUMPS-=1;
                    }
                    pressedOnce.clear();
                }
                if (JUMPS==3){
                    jumps.setText("hyperspace jumps: ✷ ✷ ✷");
                }
                else if (JUMPS==2){
                    jumps.setText("hyperspace jumps: ✷ ✷ -");
                }
                else if (JUMPS==1){
                    jumps.setText("hyperspace jumps: ✷ - -");
                }
                else if (JUMPS==0){
                    jumps.setText("hyperspace jumps: - - -");
                }
                
                levelManager.spawnChance();

                // call all objects to move
                player.move();
                levelManager.moveAsteroids();
                bulletList.forEach(Bullet::move);
                alienBullets.forEach(Bullet::move);
                levelManager.moveAliens();
                //alien.accelerate();

                // check if any bullets hit asteroids
                ArrayList<Bullet> bulletListCopy = new ArrayList<>(bulletList);
                bulletListCopy.forEach(levelManager::bulletHitAsteroid);
                bulletListCopy.forEach(levelManager::bulletHitAlien);
                
                // remove bullets from game if they collide with enemies
                bulletList.stream()
                        .filter(bullet -> !bullet.isAlive())
                        .forEach(bullet -> pane.getChildren().remove(bullet.getCharacter()));
                bulletList.removeAll(bulletList.stream()
                        .filter(bullet -> !bullet.isAlive())
                        .toList());

                //remove aliens that leave screen bounds
                levelManager.alienList.stream()
                        .filter(alien -> !alien.isAlive())
                        .forEach(alien -> pane.getChildren().remove(alien.getCharacter()));
                levelManager.alienList.removeAll(levelManager.alienList.stream()
                        .filter(alien -> !alien.isAlive())
                        .toList());
                
                score.setText("score: "+ points);

                // check if player collides with asteroid or bullets - activate respawn and decrease lives
                alienBullets.forEach(bullet -> {
                    if(bullet.checkHit(player.getCharacter()) && !player.isSafe()){
                        LIVES .decrementAndGet();
                        player.respawn(WIDTH/2,HEIGHT/2);
                    }

                });
                if(levelManager.playerHitAsteroid(player) && !Ship.respawnCalled){
                    LIVES.decrementAndGet();
                    player.respawn(WIDTH/2,HEIGHT/2);
                }
                if(levelManager.levelup()){
                    level.setText("level: "+levelManager.getLevel().toString());
                }

                if (player.isAlive() && LIVES.get()==2){
                    lives.setText("lives: ♥ ♥ -");
                } else if (player.isAlive() && LIVES.get()==1){
                    lives.setText("lives: ♥ - -");
                } else if(player.isAlive() && LIVES.get()==0){
                    lives.setText("lives: - - -");
                    LEVEL = levelManager.getLevel().toString();
                    player.setLife(false);
                }
                if (!player.isAlive()) {
                    stop();
                    System.out.println("you died");
                    gameOver(stage);
                }
            }
        };
        // set button functionality
        exit.setOnAction(actionEvent -> {home(stage); gameLoop.stop();});
        gameLoop.start();
    }
    public void addAsteroid(Asteroid a){
        pane.getChildren().add(a.getCharacter());
    }
    public void removeAsteroid(Asteroid a){
        pane.getChildren().remove(a.getCharacter());
    }
    public void addAlien(Alien a){
        pane.getChildren().add(a.getCharacter());
    }
    public void removeAlien(Alien a){
        pane.getChildren().remove(a.getCharacter());
    }

    public void addPoints(int numpoints){
        this.points.addAndGet(numpoints);
    }

    // method to show information screen for game:
    public void info(Stage stage){
        // create all static objects
        pane = createBackground();
        Label title = createLabel("Game Info", WIDTH/3.5, HEIGHT/5.0,"header");
        Label info = createLabel("turn left:\t\tA\nturn right:\tD\nforward:\t\tW\nshoot:\t\tE\nhyperjump:\tJ", WIDTH/3.5, HEIGHT/2.3, "info");
        Button back = createButton("< back", 15, 550);
        Button reset = createButton("reset highscore", 15, 500);

        // set button functionality
        back.setOnAction(actionEvent -> home(stage));
        reset.setOnAction(actionEvent -> {
            try {
                PrintWriter writer = new PrintWriter("highscores.txt");
                writer.println("000");
                writer.close();
            } catch (IOException e){
                e.printStackTrace();
            }
            home(stage);
        });

        // keep all static objects in list and add to pane
        List<Node> staticElementsList = new ArrayList<>();
        Collections.addAll(staticElementsList, title, info, back, reset);
        staticElementsList.forEach(node -> pane.getChildren().add(node));

        Scene scene = new Scene(pane);
        scene.getStylesheets().add(Objects.requireNonNull(SceneController.class.getResource("stylesheet.css")).toExternalForm());
        stage.setTitle("Asteroids - Game Info");
        stage.setScene(scene);
        stage.show();
    }

    // screen to display when game over:
    public void gameOver(Stage stage){

        // set new highscore if higher than previous
        int currentScore = points.get();
        try{
            if (currentScore > highscore){
                PrintWriter writer = new PrintWriter("highscores.txt");
                writer.println(currentScore);
                writer.close();}
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        // create all static objects
        Pane pane = createBackground();
        Label title = createLabel("Game Over", WIDTH/3.5, HEIGHT/5.0,"overHeader");
        Label scoreboard = createLabel("score: "+points.get(), WIDTH/3.0, HEIGHT/2.3, "scoreboard");
        Label levelboard = createLabel("level: "+LEVEL, WIDTH/3.0, HEIGHT/1.9, "levelboard");
        Button home = createButton("< home", 15, 550);

        //set button functionality
        home.setOnAction(actionEvent -> home(stage));

        // keep all static objects in list and add to pane
        List<Node> staticElementsList = new ArrayList<>();
        Collections.addAll(staticElementsList, title, scoreboard, home, levelboard);
        staticElementsList.forEach(node -> pane.getChildren().add(node));

        Scene scene = new Scene(pane);
        scene.getStylesheets().add(Objects.requireNonNull(SceneController.class.getResource("stylesheet.css")).toExternalForm());
        stage.setTitle("Asteroids - Game Over");
        stage.setScene(scene);
        stage.show();

    }
}