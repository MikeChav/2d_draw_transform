package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

/**
 * Created by michael on 4/3/16.
 *
 * Handles interact with reflection template and calls the transformation method
 */
public class ReflectionController {

	@FXML
	RadioButton xAxis, yAxis, yEqualsX, yMinusX, xyAxis;
	@FXML
	TextField p1X, p1Y, p2X, p2Y;

	public void getPoints(ActionEvent e) {
		char reflectionType;

		if (yAxis.isSelected())
			reflectionType = 'y';
		else if (xAxis.isSelected())
			reflectionType = 'x';
		else if (yEqualsX.isSelected())
			reflectionType = 'p';
		else if (yMinusX.isSelected())
			reflectionType = 'n';
		else if (xyAxis.isSelected())
			reflectionType = 'b';
		else return;

		(new Transformation()).reflection(reflectionType);

		doNothing(e);
	}

	@FXML
	public void doNothing(ActionEvent e) {
		AlertBox.closeAlertBox(e);
	}

}
