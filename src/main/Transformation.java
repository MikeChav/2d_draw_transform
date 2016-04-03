package main;

import javafx.geometry.Point2D;
import javafx.scene.control.Alert;

import java.lang.reflect.Array;
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
		if (listOfPoints.size() < 3)
			(new Alert(Alert.AlertType.ERROR, "Polygons need at least 3 vertices")).showAndWait();

	}

	public void reflection(Point2D p1, Point2D p2) {
		ensureDrawnPolygon();
	}

	public void Rotation(double angledegree,int x,int y){

		ensureDrawnPolygon();

		double angle = Math.PI*(angledegree/180.0);

		double[][] RotationMatrix = {{Math.cos(angle), Math.sin(angle)}, {-Math.sin(angle), Math.cos(angle)}};

		Point2D[] PointsResult = new Point2D[listOfPoints.size()];

		for(int z=0;z<listOfPoints.size();z++){

			Point2D translatedPoint = Commons.translatePoint(listOfPoints.get(z));
			double xc =  ((translatedPoint.getX()-x)*RotationMatrix[0][0])+((translatedPoint.getY()-y)*RotationMatrix[0][1]);
			double yc = ((translatedPoint.getX()-x)*RotationMatrix[1][0])+((translatedPoint.getY()-y)*RotationMatrix[1][1]);

			PointsResult[z] =(Commons.translatebackPoint(new Point2D(xc,yc)));
		}
		Commons.Draw(PointsResult);
	}

	public void Scaling(int ScaleX,int ScaleY,int x,int y){

		ensureDrawnPolygon();

		int[][] ScalingMatrix = {{ScaleX,0},{0,ScaleY}};

		Point2D[] PointsResult = new Point2D[listOfPoints.size()];

		for(int z=0;z<listOfPoints.size();z++){

			Point2D translatedPoint = Commons.translatePoint(listOfPoints.get(z));
			double xc =  ((translatedPoint.getX()-x)*ScalingMatrix[0][0])+((translatedPoint.getY()-y)*ScalingMatrix[0][1]);
			double yc = ((translatedPoint.getX()-x)*ScalingMatrix[1][0])+((translatedPoint.getY()-y)*ScalingMatrix[1][1]);

			PointsResult[z] =(Commons.translatebackPoint(new Point2D(xc,yc)));
		}
		Commons.Draw(PointsResult);
	}



}
