package main;

import javafx.geometry.Point2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by michael on 4/4/16.
 *
 * This class takes data from fill controllers and launches the right algorithms
 */
public class ColorFill {

	//TODO:BIGASS ONE HERE - set limit per shape
	public static void flood(int row, int col, Color tgtColor) {

		boolean mark[][] = new boolean[ (int) Controller.staticMyCanvas.getWidth()][ (int) Controller.staticMyCanvas.getHeight()];
		int height = (int) Controller.staticMyCanvas.getHeight();
		int width = (int) Controller.staticMyCanvas.getWidth();

		WritableImage image = Controller.staticMyCanvas.snapshot(new SnapshotParameters(), null);
		PixelReader reader = image.getPixelReader();
		Queue<Point2D> queue = new LinkedList<>();
		Controller.staticGraphicsContext.setStroke(tgtColor);
/**  Loops run once, 1 break added and code commented only to verify first values on line 39*/
		for (int i = col; i < col+1; i++)
			for (int j = row; j < row+1; j++)
				if (! mark[j][i]) {
					queue.add(new Point2D(j, i));

					while (! queue.isEmpty()) {

						Point2D p = queue.remove();
                        System.out.println(p.getX()+" "+p.getY()+" "+Commons.pointInPolygon(p.getX(), p.getY()));
                        break;
//						if ((p.getX() < width) && (p.getY() < height) && (p.getX() >= 0) && (p.getY() >= 0)) {
//							if ((Commons.pointInPolygon(p.getX(), p.getY())) && (!mark[(int) p.getX()][(int) p.getY()])) {
//								if (reader.getColor((int) p.getX(), (int) p.getY()) != Color.BLACK) {
//
//                                    System.out.println("Coloring");
//									Controller.staticGraphicsContext.strokeLine(p.getX(), p.getY(), p.getX(), p.getY());
//									mark[(int) p.getX()][(int) p.getY()] = true;
//
//									queue.add(new Point2D(p.getX() + 1, p.getY()));
//									queue.add(new Point2D(p.getX() - 1, p.getY()));
//									queue.add(new Point2D(p.getX(), p.getY() + 1));
//									queue.add(new Point2D(p.getX(), p.getY() - 1));
//								}
//							}
//						}
					}
				}
	}
}
