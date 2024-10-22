package main;

import javafx.geometry.Point2D;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Formatter;
import java.util.LinkedList;

/**
 * Created by michael on 4/3/16.
 *
 * Holds Common code that may be used in different classes (mostly static calls)
 *
 */
public class Commons{

	private static double height;
	private static double width;

	static LinkedList<Point2D> listOfPoints = new LinkedList<>();


	public static Point2D startPoint;

	public static double getHeight() {
		return height;
	}

	public static double getWidth() {
		return width;
	}

	public static void initSize() {
		height = javafx.stage.Screen.getPrimary().getVisualBounds().getHeight();
		width = javafx.stage.Screen.getPrimary().getVisualBounds().getWidth();
	}

	public static Point2D translatePoint(Point2D point) {
		return (new Point2D(translateX(point.getX()), translateY(point.getY())));
	}

	public static double translateX(double x){
		return ((x-(Controller.AxisWidth/2))/(Controller.AxisWidth/(2*Controller.XRange)));
	}

	public static double translateY(double y){
		return (((Controller.AxisHeight/2) - y)/(Controller.AxisHeight/(2*Controller.YRange)));
	}

	public static Point2D translatebackPoint(Point2D point) {
		return (new Point2D(translatebackX(point.getX()), translatebackY(point.getY())));
	}

	public static double translatebackX(double x){
		return  ((x*(Controller.AxisWidth/(2*Controller.XRange)))+Controller.AxisWidth/2);
	}

	public static double translatebackY(double y){
		return  (-(y*(Controller.AxisHeight/(2*Controller.YRange)))+Controller.AxisHeight/2);
	}


	public static void bresenhamLine(double x0, double y0, double x1, double y1) {
		double dx =  Math.abs(x1-x0), sx = x0<x1 ? 1. : -1.;
		double dy = -Math.abs(y1-y0), sy = y0<y1 ? 1. : -1.;
		double err = dx+dy, e2; /* error value e_xy */

		while(true){
			Controller.staticGraphicsContext.strokeLine(x0, y0, x0, y0);

			if (x0==x1 && y0==y1)
				break;
			e2 = 2.*err;
			if (e2 > dy) { err += dy; x0 += sx; } /* e_xy+e_x > 0 */
			if (e2 < dx) { err += dx; y0 += sy; } /* e_xy+e_y < 0 */
		}
        NumberFormat formatter = new DecimalFormat("#0.00");
        Controller.staticGraphicsContext.setFont(Font.font(null, FontWeight.LIGHT, 11));
        String s = "("+formatter.format((translateX(x0)))+", "+formatter.format(translateY(y0))+")";
        Controller.staticGraphicsContext.strokeText(s, x0, y0-5);

	}

	public static void Draw(Point2D[] list){
		int length = list.length;
		for(int x=0;x<length;x++)
			if(x+1<length)
				bresenhamLine((int)list[x].getX(), (int)list[x].getY(), (int)list[x + 1].getX(),(int) list[x + 1].getY());
	}


	public static boolean pointInPolygon(double x, double y) {

		int  i, j = listOfPoints.size() - 2;

		boolean  oddNodes = false;
		for (i = 0; i < listOfPoints.size() -1; i++) {
			Point2D firstPoint = listOfPoints.get(i);
			Point2D secondPoint = listOfPoints.get(j);
			if ((((firstPoint.getY() < y) && (secondPoint.getY() >= y))
                    || ((secondPoint.getY() < y) && (firstPoint.getY() >= y)))
                    && ((firstPoint.getX() <= x) || (secondPoint.getX() <= x))) {
                if (firstPoint.getX() + (y - firstPoint.getY()) / (secondPoint.getY() - firstPoint.getY()) * (secondPoint.getX() - firstPoint.getX()) < x) {
                    oddNodes = !oddNodes;
                }
            }
			j=i;
		}
		return oddNodes;
	}
}
