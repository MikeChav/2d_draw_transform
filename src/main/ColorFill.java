package main;

import javafx.geometry.Point2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.*;

import static main.Controller.*;

/**
 * Created by michael on 4/4/16.
 *
 * This class takes data from fill controllers and launches the right algorithms
 */
class ColorFill {

	static void flood(int row, int col, Color tgtColor) {

		boolean mark[][] = new boolean[ (int) staticMyCanvas.getWidth()][ (int) staticMyCanvas.getHeight()];
		int height = (int) staticMyCanvas.getHeight();
		int width = (int) staticMyCanvas.getWidth();

		Queue<Point2D> queue = new LinkedList<>();
		staticGraphicsContext.setStroke(tgtColor);
		for (int i = col; i < height; i++)
			for (int j = row; j < width; j++) {
				if (! mark[j][i]) {
                queue.add(new Point2D(j, i));

                while (!queue.isEmpty()) {

                    Point2D p = queue.remove();

                    if ((p.getX() < width) && (p.getY() < height) && (p.getX() >= 0) && (p.getY() >= 0)) {
                        if (Commons.pointInPolygon(p.getX(), p.getY()) && (!mark[(int) p.getX()][(int) p.getY()]))  {

                                staticGraphicsContext.strokeLine(p.getX(), p.getY(), p.getX(), p.getY());
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
        reDrawBorder();
	}

    static void boundary(Point2D p, Color targetColor){
        Stack<Point2D> points = new Stack<>();
        points.add(p);
        staticGraphicsContext.setStroke(targetColor);
//        Color borderColor = Color.BLACK;

        WritableImage img = staticMyCanvas.snapshot(new SnapshotParameters(), null);
        PixelReader px = img.getPixelReader();
        Color currentColor;

        while(! points.isEmpty()) {
            Point2D currentPoint = points.pop();
            double x = currentPoint.getX();
            double y = currentPoint.getY();
            if (!(((x > staticMyCanvas.getWidth()) || (x < 0) || (y < 0) || (y > staticMyCanvas.getHeight()))) && Commons.pointInPolygon(x, y)) {
                currentColor = px.getColor((int) x, (int) y);
                if ((currentColor != targetColor)) {
                    staticGraphicsContext.strokeLine(x, y, x, y);
                    points.push(new Point2D(x + 1, y));
                    points.push(new Point2D(x - 1, y));
                    points.push(new Point2D(x, y + 1));
                    points.push(new Point2D(x, y - 1));
                }
            }
        }
        reDrawBorder();
    }


    static void Scanline(Color tgtColor) {
        for (double yTemp = ymin; yTemp <= ymax; yTemp++) {
            ArrayList<Point2D> intersectionPoints = new ArrayList<>();

            for (int p = 0; p < Commons.listOfPoints.size() - 1; p++) {
                double x1, x2, y1, y2;
                double deltax, deltay, x;
                x1 = Commons.listOfPoints.get(p).getX();
                y1 = Commons.listOfPoints.get(p).getY();
                x2 = Commons.listOfPoints.get(p + 1).getX();
                y2 = Commons.listOfPoints.get(p + 1).getY();

                deltax = x2 - x1;
                deltay = y2 - y1;

                int roundedX;
                x = x1 + deltax / deltay * (yTemp - y1);
                roundedX = (int) Math.round(x);
                if ((y1 <= yTemp && y2 > yTemp) || (y2 <= yTemp && y1 > yTemp)) {
                    intersectionPoints.add(new Point2D(roundedX, yTemp));
                }
            }
            //for the last interval
            double x1, x2, y1, y2;
            x1 = Commons.listOfPoints.get(Commons.listOfPoints.size() - 1).getX();
            y1 = Commons.listOfPoints.get(Commons.listOfPoints.size() - 1).getY();
            x2 = Commons.listOfPoints.get(0).getX();
            y2 = Commons.listOfPoints.get(0).getY();
            if ((y1 <= yTemp && y2 > yTemp) || (y2 <= yTemp && y1 > yTemp)) {
                intersectionPoints.add(new Point2D(x1 + (x2 - x1) / (y2 - y1) * yTemp - y1, yTemp));
            }
            //you have to sort the intersection points of every scanline from the lowest x value to thr highest
            Collections.sort(intersectionPoints, FruitNameComparator);

            pointsOfScanline.addAll(intersectionPoints);

            ArrayList<Point2D> list = pointsOfScanline;
            staticGraphicsContext.setStroke(tgtColor);
            int length = pointsOfScanline.size();
            for(int x=0;x<length;x++) {
                if (x + 1 < length)
                    Commons.bresenhamLine((int) list.get(x).getX(), (int) list.get(x).getY(), (int) list.get(x + 1).getX(), (int) list.get(x + 1).getY());
            }
        }
        reDrawBorder();
    }

    private static Comparator<Point2D> FruitNameComparator
            = (o1, o2) -> {

        double point1 = o1.getX();
        double point2 = o1.getX();

        if(point1>point2)
            return 1;
        if(point1<point2)
            return -1;
        return 0;
    };

    private static void reDrawBorder() {
        staticGraphicsContext.setStroke(Color.BLACK);
        Commons.Draw(Commons.listOfPoints.toArray(new Point2D[Commons.listOfPoints.size()]));
    }
}
