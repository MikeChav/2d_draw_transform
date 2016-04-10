package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

    @Override
    public void start(Stage Window) throws Exception {

		Commons.initSize();
        Parent mainLayout = FXMLLoader.load(getClass().getResource("layouts/main.fxml"));
        Window.setTitle("Graphics Part 2");
        Window.setScene(new Scene(mainLayout, Commons.getWidth(), Commons.getHeight()));
        Window.show();

//        Window.setOnCloseRequest(e -> {
//            e.consume();
//            Controller.staticExitAll();
//        });
	}

    public static void main(String[] args) {
        launch(args);
    }
}
