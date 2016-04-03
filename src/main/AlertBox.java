package main;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by michael on 3/15/16.
 *
 * Acts as main controller for all types of alert boxes
 *
 */
public class AlertBox{

	AlertBox(String layoutName, String title) {
		createAlertBox(layoutName, title, Commons.getWidth(), Commons.getHeight());
	}

	AlertBox(String layoutName, String title, double width, double height) {
		createAlertBox(layoutName, title, width, height);
	}

	public void createAlertBox(String layoutName, String title, double width, double height){
		Stage alertBox = new Stage();
		alertBox.initModality(Modality.APPLICATION_MODAL);

		Parent layout;
		try {
			layout = FXMLLoader.load(getClass().getResource("layouts/"+layoutName+".fxml"));
		} catch (IOException ignore) {
			return;
		}
		alertBox.setTitle(title);
		alertBox.setScene(new Scene(layout, width, height));
		alertBox.showAndWait();
	}

	@FXML
	public void chooseYes(){
		Platform.exit();
	}

	@FXML
	public static void chooseNo(ActionEvent actionEvent) {
		((Node) (actionEvent.getSource())).getScene().getWindow().hide();
	}
}