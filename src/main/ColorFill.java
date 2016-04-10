package main;

import javafx.geometry.Point2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

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

    static void boundary(Point2D p, Color targetColor){
        Stack<Point2D> points = new Stack<>();
        points.add(p);
        Controller.staticGraphicsContext.setStroke(targetColor);
        Color borderColor = Color.BLACK;

        WritableImage img = Controller.staticMyCanvas.snapshot(new SnapshotParameters(), null);
        PixelReader px = img.getPixelReader();
        Color currentColor;

        while(! points.isEmpty()) {
            Point2D currentPoint = points.pop();
            double x = currentPoint.getX();
            double y = currentPoint.getY();
            if (!(x > Controller.staticMyCanvas.getWidth() || x < 0 || y < 0 || y > Controller.staticMyCanvas.getHeight())) {
                if (Commons.pointInPolygon(x, y)) {
                    currentColor = px.getColor((int) x, (int) y);
                    if ((currentColor != borderColor) && (currentColor != targetColor))
                        Controller.staticGraphicsContext.strokeLine(x, y, x, y);
                    points.push(new Point2D(++x, y));
                    points.push(new Point2D(--x, y));
                    points.push(new Point2D(x, ++y));
                    points.push(new Point2D(x, --y));
                }
            }
        }
    }
}
