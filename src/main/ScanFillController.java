package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;

/**
 * Created by michael on 4/6/16.
 */
public class ScanFillController {

    @FXML
    private ColorPicker targetColor;

    public void doScanFill(ActionEvent e) {
        ColorFill.Scanline(targetColor.getValue());

        closeMe(e);
    }

    @FXML
    private void closeMe (ActionEvent e){
        AlertBox.closeAlertBox(e);
    }
}
