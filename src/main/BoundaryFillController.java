package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * Created by michael on 4/6/16.
 */
public class BoundaryFillController {

    @FXML
    private void closeMe (ActionEvent e){
        AlertBox.closeAlertBox(e);
    }
}
