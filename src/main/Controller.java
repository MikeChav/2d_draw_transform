package main;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller  implements Initializable {


	public Canvas myCanvas;
	public Pane Container;
	public VBox myBox;


	GraphicsContext graphicsContext;

	Point2D mouseLocation = null;
	Point2D prevMouseLocation = null;

	List<Point2D> listOfPoints = new LinkedList<>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		graphicsContext = myCanvas.getGraphicsContext2D();

		graphicsContext.setFill(Color.SLATEGRAY);
		graphicsContext.fillRect(0, 0, myCanvas.getWidth(), myCanvas.getHeight());
		graphicsContext.setStroke(Color.WHITE);
		graphicsContext.setLineWidth(2.0);

		myCanvas.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
			if (mouseLocation != null)
				prevMouseLocation = mouseLocation;
			mouseLocation = new Point2D(e.getX(), e.getY());

			if (listOfPoints.isEmpty())
				bresenhamLine(mouseLocation.getX(), mouseLocation.getY(), mouseLocation.getX(), mouseLocation.getY());
			else{
				if(e.getClickCount() == 2) {
					Point2D firstPoint = listOfPoints.get(0);
					bresenhamLine(mouseLocation.getX(), mouseLocation.getY(), firstPoint.getX(), firstPoint.getY());
				}
				else if (e.getClickCount() == 1)
					bresenhamLine(prevMouseLocation.getX(), prevMouseLocation.getY(), mouseLocation.getX(), mouseLocation.getY());
			}
			listOfPoints.add(mouseLocation);
		});


//		Axes axes = new Axes(
//				1366, 768,
//				-8, 8, 1,
//				-6, 6,1
//		);
//
//		Plot plot = new Plot(
//				x -> .25 * (x + 4) * (x + 1) * (x - 2),
//				0, 0, 0,
//				axes
//		);
//
//
//		addListeners();
//
	}

	private void bresenhamLine( double x0, double y0, double x1, double y1) {
		double dx =  Math.abs(x1-x0), sx = x0<x1 ? 1. : -1.;
		double dy = -Math.abs(y1-y0), sy = y0<y1 ? 1. : -1.;
		double err = dx+dy, e2; /* error value e_xy */

		while(true){
			graphicsContext.strokeLine(x0, y0, x0, y0);
			if (x0>=x1 && y0>=y1)
				break;
			e2 = 2.*err;
			if (e2 > dy) { err += dy; x0 += sx; } /* e_xy+e_x > 0 */
			if (e2 < dx) { err += dx; y0 += sy; } /* e_xy+e_y < 0 */
		}
	}

	@FXML
	public static void staticExitAll(){
		(new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?")).showAndWait()
				.filter(response -> response == ButtonType.OK)
				.ifPresent(response -> Platform.exit());

	}
	public  void exitAll(){
		staticExitAll();
	}

//	class Axes extends Pane {
//		private NumberAxis xAxis;
//		private NumberAxis yAxis;
//
//		public Axes(
//				int width, int height,
//				double xLow, double xHi, double xTickUnit,
//				double yLow, double yHi, double yTickUnit
//		) {
//			setMinSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
//			setPrefSize(width, height);
//			setMaxSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
//
//			xAxis = new NumberAxis(xLow, xHi, xTickUnit);
//			xAxis.setSide(Side.BOTTOM);
//			xAxis.setMinorTickVisible(false);
//			xAxis.setPrefWidth(width);
//			xAxis.setLayoutY(height / 2);
//
//			yAxis = new NumberAxis(yLow, yHi, yTickUnit);
//			yAxis.setSide(Side.LEFT);
//			yAxis.setMinorTickVisible(false);
//			yAxis.setPrefHeight(height);
//			yAxis.layoutXProperty().bind(
//					Bindings.subtract(
//							(width / 2) + 1,
//							yAxis.widthProperty()
//					)
//			);
//
//			getChildren().setAll(xAxis, yAxis);
//		}
//
//		public NumberAxis getXAxis() {
//			return xAxis;
//		}
//
//		public NumberAxis getYAxis() {
//			return yAxis;
//		}
//	}
//

//
//	class Plot extends Pane {
//		public Plot(
//				Function<Double, Double> f,
//				double xMin, double xMax, double xInc,
//				Axes axes
//		) {
//			Path path = new Path();
//			path.setStroke(Color.ORANGE.deriveColor(0, 1, 1, 0.6));
//			path.setStrokeWidth(2);
//
//			path.setClip(
//					new Rectangle(
//							0, 0,
//							axes.getPrefWidth(),
//							axes.getPrefHeight()
//					)
//			);
//
//			double x = xMin;
//			double y = f.apply(x);
//
//			path.getElements().add(
//					new MoveTo(
//							mapX(x, axes), mapY(y, axes)
//					)
//			);
//
//			x += xInc;
//			while (x < xMax) {
//				y = f.apply(x);
//
//				path.getElements().add(
//						new LineTo(
//								mapX(x, axes), mapY(y, axes)
//						)
//				);
//
//				x += xInc;
//			}
//
//			setMinSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
//			setPrefSize(axes.getPrefWidth(), axes.getPrefHeight());
//			setMaxSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
//
//			getChildren().setAll(axes, path);
//		}
//
//		private double mapX(double x, Axes axes) {
//			double tx = axes.getPrefWidth() / 2;
//			double sx = axes.getPrefWidth() /
//					(axes.getXAxis().getUpperBound() -
//							axes.getXAxis().getLowerBound());
//
//			return x * sx + tx;
//		}
//
//		private double mapY(double y, Axes axes) {
//			double ty = axes.getPrefHeight() / 2;
//			double sy = axes.getPrefHeight() /
//					(axes.getYAxis().getUpperBound() -
//							axes.getYAxis().getLowerBound());
//
//			return -y * sy + ty;
//		}
//
//	}


}
