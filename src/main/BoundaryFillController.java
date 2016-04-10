package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;

/**
 * Created by michael on 4/6/16.
 *
 * Handles call to do the boundary fill
 */
public class BoundaryFillController {

    @FXML
    private ColorPicker targetColor;

    @FXML
    private void doBoundaryFill(ActionEvent e) {
        ColorFill.boundary(Commons.startPoint, targetColor.getValue());
        closeMe(e);
    }

    @FXML
    private void closeMe (ActionEvent e){
        AlertBox.closeAlertBox(e);
    }
}
