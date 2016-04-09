package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * Created by michael on 4/9/16.
 **/

public class TranslationController {

    @FXML
    private TextField valX;
    @FXML
    private TextField valY;

    @FXML
    private void translateMe(ActionEvent e) {
        (new Transformation()).translation(
                Double.parseDouble(valX.getText()),
                Double.parseDouble(valY.getText())
        );
        closeMe(e);
    }

    @FXML
    private void closeMe (ActionEvent e){
        AlertBox.closeAlertBox(e);
    }
}
