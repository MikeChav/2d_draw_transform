package main;

import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

/*
 * Created by michael on 4/1/16.
 */
public class CanvasController implements Initializable {

	public Canvas myCanvas;

	GraphicsContext graphicsContext;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		graphicsContext = myCanvas.getGraphicsContext2D();
		graphicsContext.setFill(Color.RED);
		graphicsContext.fillRect(0, 0, myCanvas.getWidth(), myCanvas.getHeight());

		graphicsContext.setStroke(Color.BLACK);
		graphicsContext.setLineWidth(2.0);
		graphicsContext.strokeLine(200.0, 500.0, 200.0, 500.0);
	}


}
