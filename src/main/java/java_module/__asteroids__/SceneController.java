package java_module.__asteroids__;

import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class SceneController extends SceneFiller{
    // width and height of all panes used for game
    public static int WIDTH = 800;
    public static int HEIGHT = 600;
    // lives tally
    public int LIVES;
    // high-score variable used to update file
    public int highscore;
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
        Pane pane = createBackground();
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

        List<Bullet> bulletList = new ArrayList<>();
        List<Asteroid> asteroidList = new ArrayList<>();
        List<Node> staticElementsList = new ArrayList<>();

        // create and add all static elements
        Pane pane = createBackground();
        Button exit = createButton("< exit", 15, 550);
        Label level = createLabel("leval 1", 20, 400, "level");
        Label score = createLabel("score: 0", 20, 510, "score");
        Label lives = createLabel("lives: ♥︎ ♥︎ ♥︎", WIDTH/3.0, 550, "lives");

        // set button functionality
        exit.setOnAction(actionEvent -> home(stage));
        // keep all static objects in list and add to pane
        Collections.addAll(staticElementsList, exit, level, score, lives);

        // create player at center
        Ship player = new Ship(WIDTH/2, HEIGHT/2);
        LIVES = 3;

        Asteroid a = new Asteroid(200, 100,AsteroidSizes.MEDIUM);
        asteroidList.add(a);

        // add all objects to pane
        pane.getChildren().add(player.getCharacter());
        asteroidList.forEach(asteroid -> pane.getChildren().add(asteroid.getCharacter()));
        staticElementsList.forEach(node -> pane.getChildren().add(node));

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
        scene.setOnKeyPressed(KeyEvent -> {
            pressedKeys.put(KeyEvent.getCode(), Boolean.FALSE);
            pressedOnce.put(KeyEvent.getCode(), Boolean.FALSE);
        });

        stage.setScene(scene);
        stage.show();

        AnimationTimer gameLoop = new AnimationTimer(){
            public void handle(long now){
                if (pressedOnce.getOrDefault(KeyCode.K, false)){
                    System.out.println("K pressed");
                    Alien enemy = Alien.spawnRandom();
                    enemy.update(player);
                    pane.getChildren().add(enemy.shootBullet().getCharacter());
                    pane.getChildren().add(enemy.getCharacter());
                    enemy.move();
                    pressedOnce.clear();
                }
                if (pressedKeys.getOrDefault(KeyCode.A, false)) {
                    System.out.println("A pressed");
                    player.turnLeft();
                }
                if (pressedKeys.getOrDefault(KeyCode.D, false)) {
                    System.out.println("D pressed");
                    player.turnRight();
                }
                if (pressedKeys.getOrDefault(KeyCode.W, false)) {
                    System.out.println("W pressed");
                    player.accelerate();
                }
                // use separate hash map to read bullet call - clear on input to shoot only one bullet on key press rather than a whole stream
                if (pressedOnce.getOrDefault(KeyCode.E, false)) {
                    System.out.println("E pressed");
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
                if (pressedKeys.getOrDefault(KeyCode.J, false)){
                    System.out.println("J pressed");
                    // flies all around the screen due to animationTimer ------------ wip ------------
                    player.hyperspaceJump();
                }

                // call all objects to move - bullet has its own method to disappear at edge of screen unlike other objects
                player.move();
                asteroidList.forEach(Asteroid::move);
                bulletList.forEach(Bullet::move);

                // check if any bullets hit asteroids - increase score if true
                bulletList.forEach(bullet -> asteroidList.forEach(asteroid -> {
                    if (bullet.checkHit(asteroid.getCharacter())){
                        bullet.setLife(false);
                        asteroid.setLife(false);
//                            trial shrapnel for collision animation ------------- wip --------------
                        var s1 = new Fragment(bullet.getCharacter().getTranslateX(), bullet.getCharacter().getTranslateY());
                        var s2 = new Fragment(asteroid.getCharacter().getTranslateX(), asteroid.getCharacter().getTranslateY());
                        pane.getChildren().add(s1.getCharacter());
                        pane.getChildren().add(s2.getCharacter());
                        s1.move();
                        s2.move();
                        score.setText("Score: "+ points.addAndGet(10));
                    }
                }));
                // remove bullets and asteroids from game if they collide
                bulletList.stream()
                        .filter(bullet -> !bullet.isAlive())
                        .forEach(bullet -> pane.getChildren().remove(bullet.getCharacter()));
                bulletList.removeAll(bulletList.stream()
                        .filter(bullet -> !bullet.isAlive())
                        .toList());

                asteroidList.stream()
                        .filter(asteroid -> !asteroid.isAlive())
                        .forEach(asteroid -> pane.getChildren().remove(asteroid.getCharacter()));
                asteroidList.removeAll(asteroidList.stream()
                        .filter(asteroid -> !asteroid.isAlive())
                        .toList());
                // check if player hit asteroid - activate respawn and decrease lives
                asteroidList.forEach(asteroid ->{
                    if(asteroid.checkHit(player.getCharacter()) && !player.respawnCalled){
                        LIVES -=1;
                        player.respawn(WIDTH/2,HEIGHT/2);
                    }
                });
                if (player.isAlive() && LIVES==2){
                    lives.setText("lives: ♥︎ ♥︎ -");
                } else if (player.isAlive() && LIVES==1){
                    lives.setText("lives: ♥︎ - -");
                } else if(player.isAlive() && LIVES==0){
                    lives.setText("lives: - - -");
                    player.setLife(false);
                }
                if (!player.isAlive()) {
                    stop();
                    System.out.println("you died");
                    gameOver(stage);
                }
            }
        };
        gameLoop.start();
    }

    // method to show information screen for game:
    public void info(Stage stage){
        // create all static objects
        Pane pane = createBackground();
        Label title = createLabel("Game Info", WIDTH/3.5, HEIGHT/5.0,"header");
        Label info = createLabel("move:\t\tA & D\nthrust:\t\tW\nshoot:\t\tE\nhyperjump:\tJ", WIDTH/3.0, HEIGHT/2.3, "info");
        Button back = createButton("< back", 15, 550);
        Button reset = createButton("reset highscore", 40, 500);

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
        Button home = createButton("< home", 15, 550);

        //set button functionality
        home.setOnAction(actionEvent -> home(stage));

        // keep all static objects in list and add to pane
        List<Node> staticElementsList = new ArrayList<>();
        Collections.addAll(staticElementsList, title, scoreboard, home);
        staticElementsList.forEach(node -> pane.getChildren().add(node));

        Scene scene = new Scene(pane);
        stage.setTitle("Asteroids - Game Over");
        stage.setScene(scene);
        stage.show();

    }
}