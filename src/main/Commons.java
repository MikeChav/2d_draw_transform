package main;

import javafx.geometry.Point2D;

/**
 * Created by michael on 4/3/16.
 *
 * Holds Common code that may be used in different classes (mostly static calls)
 *
 */
public class Commons{

	private static double height;
	private static double width;

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

	public static void bresenhamLine(double x0, double y0, double x1, double y1) {
		double dx =  Math.abs(x1-x0), sx = x0<x1 ? 1. : -1.;
		double dy = -Math.abs(y1-y0), sy = y0<y1 ? 1. : -1.;
		double err = dx+dy, e2; /* error value e_xy */

		while(true){
			Controller.graphicsContext.strokeLine(x0, y0, x0, y0);
			if (x0==x1 && y0==y1)
				break;
			e2 = 2.*err;
			if (e2 > dy) { err += dy; x0 += sx; } /* e_xy+e_x > 0 */
			if (e2 < dx) { err += dx; y0 += sy; } /* e_xy+e_y < 0 */
		}
	}


}
