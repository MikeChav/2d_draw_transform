package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class Controller {

	@FXML
	private void exitAll(ActionEvent actionEvent) throws Exception{
/*
		Implemented for testing purposes
*/
		(new AlertBox()).confirmExit();
	}

}
