package java_module.__asteroids__;

import javafx.application.Application;

import java.io.FileNotFoundException;
import javafx.stage.Stage;

public class main extends Application {
    @Override
    public void start(Stage stage) throws FileNotFoundException{
        //launch game from here
        var s = new SceneController();
        s.home(stage);
    }
    public static void main(String[] args){launch(args);}
}
