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

/*
 * Created by michael on 3/15/16.
 */
public class AlertBox {

	public void confirmExit() throws Exception{
		Stage alertBox = new Stage();
		alertBox.initModality(Modality.APPLICATION_MODAL);
		alertBox.setTitle("Exiting the application");

		Parent layout = FXMLLoader.load(getClass().getResource("confirmExit.fxml"));
		alertBox.setTitle("Confirm");
		alertBox.setScene(new Scene(layout, 300, 100));
		alertBox.showAndWait();
	}

	@FXML
	private void chooseYes(ActionEvent actionEvent){
		Platform.exit();
	}

	@FXML
	private void chooseNo(ActionEvent actionEvent) {
		((Node) (actionEvent.getSource())).getScene().getWindow().hide();
	}
}