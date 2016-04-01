package main;

import javafx.scene.canvas.GraphicsContext;

/**
 * Created by michael on 3/15/16.
 */
public class bresenhamLine {

	private bresenhamLine(GraphicsContext graphicsContext, double x0, double y0, double x1, double y1)
	{
		double dx =  Math.abs(x1-x0), sx = x0<x1 ? 1. : -1.;
		double dy = -Math.abs(y1-y0), sy = y0<y1 ? 1. : -1.;
		double err = dx+dy, e2; /* error value e_xy */

		while( true){
		//	graphicsContext.drawImage( brush, x0 , y0);
			if (x0==x1 && y0==y1) break;
			e2 = 2.*err;
			if (e2 > dy) { err += dy; x0 += sx; } /* e_xy+e_x > 0 */
			if (e2 < dx) { err += dx; y0 += sy; } /* e_xy+e_y < 0 */
		}
	}

}
