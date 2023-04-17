package java_module.__asteroids__;

import javafx.application.Application;

import javafx.stage.Stage;

public class main extends Application {
    @Override
    public void start(Stage stage) {
        //launch game from here
        var s = new SceneController();
        s.home(stage);
    }
}