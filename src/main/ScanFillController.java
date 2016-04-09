package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * Created by michael on 4/6/16.
 */
public class ScanFillController {

    @FXML
    private void closeMe (ActionEvent e){
        AlertBox.closeAlertBox(e);
    }
}
