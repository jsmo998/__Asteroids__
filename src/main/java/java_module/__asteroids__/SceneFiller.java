package java_module.__asteroids__;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class SceneFiller {
    // create static elements for the different game screens
    public Pane createBackground(){
        // background pane set to specific dimensions and color for all
        Pane pane = new Pane();
        pane.setPrefSize(SceneController.WIDTH, SceneController.HEIGHT);
        pane.setStyle("-fx-background-color: #0C092A");
        return pane;
    }
    public Button createButton(String s, double x, double y){
        // buttons with text and position
        Button button = new Button(s);
        button.setTranslateX(x);
        button.setTranslateY(y);
        return button;
    }
    public Label createLabel(String s, double x, double y, String i){
        // labels with text, id, and position. ID used for styling
        Label label = new Label(s);
        label.setTranslateX(x);
        label.setTranslateY(y);
        label.setId(i);
        return label;
    }
}
