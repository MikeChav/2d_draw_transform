package main;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * Created by tmaha on 04/03/16.
 */
public class RotationController {

    @FXML
    TextField XCoordinate,YCoordinate,RotationAngle;

    @FXML
    public void getData(){

        new Transformation().Rotation(Double.parseDouble(RotationAngle.getText()),Integer.parseInt(XCoordinate.getText()),Integer.parseInt(YCoordinate.getText()));

    }
}
