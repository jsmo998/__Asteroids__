module java_module.__asteroids__ {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens java_module.__asteroids__ to javafx.fxml;
    exports java_module.__asteroids__;
}