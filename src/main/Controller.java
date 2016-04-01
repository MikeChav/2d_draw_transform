package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller  implements Initializable {

	public Canvas myCanvas;
	public VBox myBox;

	GraphicsContext graphicsContext;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		graphicsContext = myCanvas.getGraphicsContext2D();

//		myBox.prefWidthProperty().bind(myCanvas.widthProperty());
//		myBox.prefHeightProperty().bind(myCanvas.heightProperty());

		myCanvas.widthProperty().bind(myBox.minWidthProperty());
		myCanvas.heightProperty().bind(myBox.minHeightProperty());

		graphicsContext.setFill(Color.RED);
		graphicsContext.fillRect(0, 0, myCanvas.getWidth(), myCanvas.getHeight());

		graphicsContext.setStroke(Color.BLACK);
		graphicsContext.setLineWidth(2.0);
		graphicsContext.strokeLine(200.0, 500.0, 200.0, 500.0);


	}

	@FXML
	private void exitAll(ActionEvent actionEvent) throws Exception{
/*
		Implemented for testing purposes
*/
		(new AlertBox()).confirmExit();
	}

}
