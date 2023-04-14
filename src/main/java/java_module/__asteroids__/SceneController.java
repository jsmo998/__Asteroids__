package java_module.__asteroids__;

import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class SceneController extends SceneFiller{
    // width and height of all panes used for game
    public static int WIDTH = 800;
    public static int HEIGHT = 600;
    // lives tally
    public int LIVES;
    // highscore variable used to update file
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
        Label title = createLabel("Asteroids", WIDTH/3.5, HEIGHT/5, "header");
        Button start = createButton("start", WIDTH/3, HEIGHT/2.2);
        Button info = createButton("info", WIDTH/3, HEIGHT/1.7);
        Label fame = createLabel("high-score: "+highscore, WIDTH/3.5, HEIGHT/1.3, "fame");

        start.setOnAction(actionEvent -> startGame(stage));
        info.setOnAction(actionEvent -> info(stage));

        pane.getChildren().add(title);
        pane.getChildren().add(start);
        pane.getChildren().add(info);
        pane.getChildren().add(fame);

        Scene scene = new Scene(pane);
        // link stylesheet
        scene.getStylesheets().add(SceneController.class.getResource("stylesheet.css").toExternalForm());
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
        Label lives = createLabel("lives: ♥︎ ♥︎ ♥︎", WIDTH/3, 550, "lives");
        staticElementsList.add(exit);
        staticElementsList.add(level);
        staticElementsList.add(score);
        staticElementsList.add(lives);

        exit.setOnAction(actionEvent -> home(stage));

        // create player at center
        Ship player = new Ship(WIDTH/2, HEIGHT/2);
        LIVES = 3;

        Asteroid a = new Asteroid(200, 100,AsteroidSizes.MEDIUM);
        asteroidList.add(a);

        asteroidList.forEach(asteroid -> pane.getChildren().add(asteroid.getCharacter()));
        staticElementsList.forEach(node -> pane.getChildren().add(node));

        Scene scene = new Scene(pane);
        stage.setTitle("Asteroids");
        scene.getStylesheets().add(SceneController.class.getResource("stylesheet.css").toExternalForm());
        stage.setScene(scene);
        stage.show();

        traceKeypress(scene);

        AnimationTimer game_loop = new AnimationTimer(){
            public void handle(long now){

            }
        };

    }
    public void traceKeypress(Scene scene){
        // track key presses and update hash map accordingly
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
    }
    public void info(Stage stage){
        // method to show information screen for game
        List<Node> static_elements_list = new ArrayList<>();

        Pane pane = createBackground();
        Label title = createLabel("Game Info", WIDTH/3.5, HEIGHT/5,"header");
        Label info = createLabel("move:\t\tA & D\nthrust:\t\tW\nshoot:\t\tE\nhyperjump:\tJ", WIDTH/3, HEIGHT/2.3, "info");
        Button back = createButton("< back", 15, 550);
        Button reset = createButton("reset highscore", 40, 500);
        static_elements_list.add(title);
        static_elements_list.add(info);
        static_elements_list.add(back);
        static_elements_list.add(reset);

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

        static_elements_list.forEach(node -> pane.getChildren().add(node));

        Scene scene = new Scene(pane);
        scene.getStylesheets().add(Scene.class.getResource("stylesheet.css").toExternalForm());
        stage.setTitle("Asteroids - Game Info");
        stage.setScene(scene);
        stage.show();

    }
    public void gameOver(Stage stage){
        // screen to display when game over
        List<Node> static_elements_list = new ArrayList<>();

        // set new highscore if higher than previous
        if (points != null){
            int currentScore = points.get();
            try{
                if (currentScore > highscore){
                    PrintWriter writer = new PrintWriter("highscores.txt");
                    writer.println(currentScore);
                    writer.close();}
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        Pane pane = createBackground();
        Label title = createLabel("Game Over", WIDTH/3.5, HEIGHT/5,"overHeader");
        Label scoreboard = createLabel("score: "+points.get(), WIDTH/3, HEIGHT/2.3, "scoreboard");
        Button home = createButton("< home", 15, 550);
        static_elements_list.add(title);
        static_elements_list.add(scoreboard);
        static_elements_list.add(home);

        home.setOnAction(actionEvent -> home(stage));

        static_elements_list.forEach(node -> pane.getChildren().add(node));

        Scene scene = new Scene(pane);
        stage.setTitle("Asteroids - Game Over");
        stage.setScene(scene);
        stage.show();

    }
}