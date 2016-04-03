package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

/**
 * Created by michael on 4/3/16.
 *
 * Handles interact with reflection template and calls the transformation method
 */
public class ReflectionController {

	@FXML
	RadioButton xAxis, yAxis, yEqualsX, yMinusX, customLine;
	@FXML
	TextField p1X, p1Y, p2X, p2Y;

	public void getPoints() {
		Point2D p1 = new Point2D(0,0);
		Point2D p2;

		if (yAxis.isSelected())
			p2 = new Point2D(0,100);
		else if (xAxis.isSelected())
			p2 = new Point2D(100,0);
		else if (yEqualsX.isSelected())
			p2 = new Point2D(100,100);
		else if (yMinusX.isSelected())
			p2 = new Point2D(100,-100);
		else if (customLine.isSelected()){
			p1 = new Point2D(Double.parseDouble(p1X.getText()),
							 Double.parseDouble(p1Y.getText()));
			p2 = new Point2D(Double.parseDouble(p2X.getText()),
							 Double.parseDouble(p2Y.getText()));
		}
		else return;

		(new Transformation()).reflection(p1, p2);
	}

	@FXML
	public void doNothing(ActionEvent e) {
		AlertBox.chooseNo(e);
	}

}
