package main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;

/**
 * Created by michael on 3/15/16.
 */
public class AlertBox extends Application {

	public static void main(String args[]){
		launch(args);
	}

	@Override
	public void start(Stage alertBox) throws Exception{


		alertBox.initModality(Modality.APPLICATION_MODAL);
		alertBox.setTitle("Exiting the application");
		alertBox.setMinHeight(300);

//		Button btn_yes  = new Button("Yes");
//		Button btn_no  = new Button("No");
//
//		btn_no.setOnAction(e -> alertBox.close());
//		btn_yes.setOnAction(e -> Platform.exit());


//		VBox layout = new VBox(20);
//		layout.getChildren().addAll(btn_yes, btn_no);
//		layout.setAlignment(Pos.CENTER);

		Parent layout = FXMLLoader.load(getClass().getResource("confirmExit.fxml"));
		alertBox.setTitle("Confirm");
		alertBox.setScene(new Scene(layout, 100, 100));
		alertBox.showAndWait();


	}

	@FXML
	private void chooseYes(ActionEvent actionEvent){
		Platform.exit();
	}

//	@FXML
//	private void chooseNo(ActionEvent actionEvent){
//		alertBox.close();
//	}
}
