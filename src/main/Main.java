package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent mainLayout = FXMLLoader.load(getClass().getResource("main.fxml"));
        primaryStage.setTitle("Graphics Part 2");
        primaryStage.setScene(new Scene(
                mainLayout,
                javafx.stage.Screen.getPrimary().getVisualBounds().getWidth(),
                javafx.stage.Screen.getPrimary().getVisualBounds().getHeight()
        ));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
