package main;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * Created by tmaha on 04/04/16.
 */
public class ShearController {

    @FXML
    TextField ShX,ShY,refX,refY;

    @FXML
    public void getData(){

        if(ShX.getText().equals(""))
            ShX.setText("0");

        if(ShY.getText().equals(""))
            ShY.setText("0");

        if(refX.getText().equals(""))
            ShX.setText("0");

        if(refY.getText().equals(""))
            refY.setText("0");

        new Transformation().Shear(Double.parseDouble(ShX.getText()),Double.parseDouble(ShY.getText()),Double.parseDouble(refX.getText()),Double.parseDouble(refY.getText()));
    }
}
