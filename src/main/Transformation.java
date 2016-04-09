package main;

import javafx.geometry.Point2D;
import javafx.scene.control.Alert;

import java.util.LinkedList;

/**
 * Created by michael on 4/3/16.
 *
 * Holds methods for each transformation.
 * Call:
 *  (new Transformation()).<transformation>(<parameters>)
 *
 */
class Transformation {

	private LinkedList<Point2D> listOfPoints;


	Transformation(){
		this.listOfPoints = Controller.listOfPoints;
	}

	private boolean ensureDrawnPolygon(){
		if (listOfPoints.isEmpty()) {
			(new Alert(Alert.AlertType.ERROR, "Please draw something first")).showAndWait();
			return false;
		}
		else if (listOfPoints.size() < 3) {
			(new Alert(Alert.AlertType.ERROR, "Polygons need at least 3 vertices")).showAndWait();
			return false;
		}
		if (listOfPoints.getFirst() != listOfPoints.getLast())
			Commons.bresenhamLine(listOfPoints.getFirst().getX(), listOfPoints.getFirst().getY(),
					listOfPoints.getLast().getX(), listOfPoints.getLast().getY());
		return true;
	}

	void translation(double tx, double ty) {
		if (! ensureDrawnPolygon())
			return;

		double[][] TranslationMatrix = {{1, 0, tx}, { 0, 1, ty}, {0, 0, 1}};

		transformMe(TranslationMatrix, 0, 0);
	}

	void reflection(char type) {
		if (! ensureDrawnPolygon())
			return;
		double [][] ReflectionMatrix ={{1, 0, 0}, {0, -1, 0}, {0, 0, 1}};
		switch(type){
			case 'x' :
				break;
			case 'y' :
				ReflectionMatrix[0][0] = -1;
				ReflectionMatrix[1][1] = 1;
				break;
			case 'b' :
				ReflectionMatrix[0][0] = -1;
				break;
			case 'p' :
				ReflectionMatrix[0][0] = 0;
				ReflectionMatrix[0][1] = 1;
				ReflectionMatrix[1][0] = 1;
				ReflectionMatrix[1][1] = 0;
				break;
			case 'n' :
				ReflectionMatrix[0][0] = 0;
				ReflectionMatrix[0][1] = -1;
				ReflectionMatrix[1][0] = -1;
				ReflectionMatrix[1][1] = 0;
				break;
			default:
				new Alert(Alert.AlertType.ERROR, "We're sorry, an error has occurred");
				break;
		}
		transformMe(ReflectionMatrix, 0, 0);
	}

	void Rotation(double angleDegree, int x, int y){
		if (! ensureDrawnPolygon())
			return;
		double angle = Math.PI*(angleDegree/180.0);
		double[][] RotationMatrix = {{Math.cos(angle), Math.sin(angle)}, {-Math.sin(angle), Math.cos(angle)}};
		transformMe(RotationMatrix, x, y);

	}

	void Scaling(double ScaleX, double ScaleY, int x, int y){
		if (! ensureDrawnPolygon())
			return;
		double[][] ScalingMatrix = {{ScaleX,0},{0,ScaleY}};
		transformMe(ScalingMatrix, x, y);

	}

	void Shear(double ShX, double ShY, double refX, double refY){
		if (! ensureDrawnPolygon())
			return;

		double[][] ShearMatrix = {{1,ShX,-ShX*refY},{ShY,1,-ShY*refX},{0,0,1}};
		transformMe(ShearMatrix,0,0);

	}

	private void transformMe(double[][] matrix, int x, int y) {
		Point2D[] PointsResult = new Point2D[listOfPoints.size()];

		double xc,yc;

		for(int z=0;z<listOfPoints.size();z++){

			Point2D translatedPoint = Commons.translatePoint(listOfPoints.get(z));
			if(matrix.length>2) {
				xc = ((translatedPoint.getX() - x) * matrix[0][0]) + ((translatedPoint.getY() - y) * matrix[0][1]) + (1 * matrix[0][2]);
				yc = ((translatedPoint.getX() - x) * matrix[1][0]) + ((translatedPoint.getY() - y) * matrix[1][1]) + (1 * matrix[1][2]);
			}
			else
			{
				xc = ((translatedPoint.getX() - x) * matrix[0][0]) + ((translatedPoint.getY() - y) * matrix[0][1]) ;
				yc = ((translatedPoint.getX() - x) * matrix[1][0]) + ((translatedPoint.getY() - y) * matrix[1][1]);
			}

			PointsResult[z] =(Commons.translatebackPoint(new Point2D(xc,yc)));
		}
		Commons.Draw(PointsResult);
	}
}
