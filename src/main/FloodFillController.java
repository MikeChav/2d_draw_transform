package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;

/**
 * Created by michael on 4/4/16.
 *
 * Get required data and passes starts the flood fill
 */
public class FloodFillController {

	@FXML
	private ColorPicker targetColor;

	public void doFloodFill(ActionEvent e) {
		ColorFill.flood((int) Commons.startPoint.getX(), (int) Commons.startPoint.getY(), targetColor.getValue());
		closeMe(e);
	}

	@FXML
	private void closeMe (ActionEvent e){
		AlertBox.closeAlertBox(e);
	}
}
