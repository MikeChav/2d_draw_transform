package main;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;


/**
 * Created by tmaha on 04/03/16.
 */
public class ScalingController {

    @FXML
    TextField ScaleX,ScaleY,CentreX,CentreY;

    public void doScale(){

        if(CentreX.getText().equals(""))
            CentreX.setText("0");

        if(CentreY.getText().equals(""))
            CentreY.setText("0");


        new Transformation().Scaling(Double.parseDouble(ScaleX.getText()), Double.parseDouble(ScaleY.getText()), Integer.parseInt(CentreX.getText()),Integer.parseInt(CentreY.getText()));

    }
}
