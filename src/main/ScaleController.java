package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by michael on 4/7/16.
 *
 * Handles the changes to scale
 *
 */
public class ScaleController implements Initializable {

	@FXML
	private TextField scaleX;

	@FXML
	private TextField scaleY;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@FXML
	private void changeThyScale(ActionEvent e){
		Controller.setScales(Integer.parseInt(scaleX.getText()), Integer.parseInt(scaleY.getText()));
		close(e);
	}

	@FXML
	private void close(ActionEvent e) {
		AlertBox.closeAlertBox(e);
	}
}
