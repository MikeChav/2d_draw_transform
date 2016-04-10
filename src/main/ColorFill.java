package main;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by michael on 4/4/16.
 *
 * This class takes data from fill controllers and launches the right algorithms
 */
class ColorFill {

	static void flood(int row, int col, Color tgtColor) {

		boolean mark[][] = new boolean[ (int) Controller.staticMyCanvas.getWidth()][ (int) Controller.staticMyCanvas.getHeight()];
		int height = (int) Controller.staticMyCanvas.getHeight();
		int width = (int) Controller.staticMyCanvas.getWidth();

		Queue<Point2D> queue = new LinkedList<>();
		Controller.staticGraphicsContext.setStroke(tgtColor);
		for (int i = col; i < height; i++)
			for (int j = row; j < width; j++) {
				if (! mark[j][i]) {
                queue.add(new Point2D(j, i));

                while (!queue.isEmpty()) {

                    Point2D p = queue.remove();

                    if ((p.getX() < width) && (p.getY() < height) && (p.getX() >= 0) && (p.getY() >= 0)) {
                        if (Commons.pointInPolygon(p.getX(), p.getY()) && (!mark[(int) p.getX()][(int) p.getY()]))  {

                                Controller.staticGraphicsContext.strokeLine(p.getX(), p.getY(), p.getX(), p.getY());
                                mark[(int) p.getX()][(int) p.getY()] = true;

                                queue.add(new Point2D(p.getX() + 1, p.getY()));
                                queue.add(new Point2D(p.getX() - 1, p.getY()));
                                queue.add(new Point2D(p.getX(), p.getY() + 1));
                                queue.add(new Point2D(p.getX(), p.getY() - 1));
                            }
                        }
                    }
                }
            }
        Controller.staticGraphicsContext.setStroke(Color.BLACK);
        Commons.Draw(Commons.listOfPoints.toArray(new Point2D[Commons.listOfPoints.size()]));
	}
}
