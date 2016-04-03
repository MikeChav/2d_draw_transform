package main;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;

import java.util.LinkedList;

/**
 * Created by michael on 4/3/16.
 *
 * Holds methods for each transformation.
 * Call:
 *  (new Transformation(listofPoints)).<transformation>(<parameters>)
 *
 */
public class Transformation {

	LinkedList<Point2D> listOfPoints;

	public Transformation(){
		this.listOfPoints = Controller.listOfPoints;
	}

	private void ensureDrawnPolygon(){
		if (listOfPoints.isEmpty())
			(new Alert(Alert.AlertType.ERROR, "Please draw something first")).showAndWait();
		if (listOfPoints.getFirst() != listOfPoints.getLast())
			Commons.bresenhamLine(listOfPoints.getFirst().getX(), listOfPoints.getFirst().getY(),
					listOfPoints.getLast().getX(), listOfPoints.getLast().getY());
	}

	public void reflection(Point2D p1, Point2D p2) {
		ensureDrawnPolygon();

	}

}
