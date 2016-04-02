package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

    private static double height;
    private static double width;

    public static double getHeight() {
        return height;
    }

    public static double getWidth() {
        return width;
    }

    @Override
    public void start(Stage Window) throws Exception {
		height = javafx.stage.Screen.getPrimary().getVisualBounds().getHeight();
		width = javafx.stage.Screen.getPrimary().getVisualBounds().getWidth();

        Parent mainLayout = FXMLLoader.load(getClass().getResource("layouts/main.fxml"));
        Window.setTitle("Graphics Part 2");
        Window.setScene(new Scene(mainLayout, width, height));
        Window.show();

        Window.setOnCloseRequest(e -> {
            e.consume();
            Controller.staticExitAll();
        });
	}

    public static void main(String[] args) {
        launch(args);
    }
}
