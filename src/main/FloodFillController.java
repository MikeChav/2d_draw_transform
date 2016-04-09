package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;

/**
 * Created by michael on 4/4/16.
 *
 * Get required data and passes starts the flood fill
 */
public class FloodFillController {

	public void doFloodFill() {
		boolean mark[][] = new boolean[ (int) Controller.staticMyCanvas.getWidth()][ (int) Controller.staticMyCanvas.getHeight()];
		ColorFill.flood(mark, 1,2, Color.BLUE);
	}

	@FXML
	private void closeMe (ActionEvent e){
		AlertBox.closeAlertBox(e);
	}
}
