package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage Window) throws Exception{
        Parent mainLayout = FXMLLoader.load(getClass().getResource("main.fxml"));
        Window.setTitle("Graphics Part 2");
        Window.setScene(new Scene(
				mainLayout,
				javafx.stage.Screen.getPrimary().getVisualBounds().getWidth(),
				javafx.stage.Screen.getPrimary().getVisualBounds().getHeight()
		));
        Window.show();

	}


    public static void main(String[] args) {
        launch(args);
    }
}
