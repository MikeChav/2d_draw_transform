package main;

import javafx.animation.AnimationTimer;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.*;


import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Function;

public class Controller  implements Initializable {


	public Canvas myCanvas;
	public VBox myBox;
	public Pane Container;


	public StackPane layout;

	GraphicsContext graphicsContext;

	Point2D mouseLocation = new Point2D( 0, 0);
	boolean mousePressed = false;
	Point2D prevMouseLocation = new Point2D( 0, 0);

	AnimationTimer loop;

	Image brush = createBrush( 1.0, Color.CHOCOLATE);
	double brushWidthHalf = brush.getWidth() / 2.0;
	double brushHeightHalf = brush.getHeight() / 2.0;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		graphicsContext = myCanvas.getGraphicsContext2D();

//		myBox.prefWidthProperty().bind(myCanvas.widthProperty());
//		myBox.prefHeightProperty().bind(myCanvas.heightProperty());

		myCanvas.widthProperty().bind(myBox.minWidthProperty());
		myCanvas.heightProperty().bind(myBox.minHeightProperty());

		graphicsContext.setFill(Color.RED);
		graphicsContext.fillRect(0, 0, myCanvas.getWidth(), myCanvas.getHeight());

		graphicsContext.setStroke(Color.BLACK);
		graphicsContext.setLineWidth(2.0);
		graphicsContext.strokeLine(200.0, 500.0, 200.0, 500.0);

		Axes axes = new Axes(
				1366, 768,
				-8, 8, 1,
				-6, 6,1
		);

		Plot plot = new Plot(
				x -> .25 * (x + 4) * (x + 1) * (x - 2),
				0, 0, 0,
				axes
		);


		addListeners();

		startAnimation();

		layout = new StackPane(plot);

		layout.setPadding(new Insets(20));
		layout.setStyle("-fx-background-color: rgb(35, 39, 50);");
		Container.getChildren().add(layout);
	}

	@FXML
	private void exitAll(ActionEvent actionEvent) throws Exception{
/*
		Implemented for testing purposes
*/
		(new AlertBox()).confirmExit();
	}

	class Axes extends Pane {
		private NumberAxis xAxis;
		private NumberAxis yAxis;

		public Axes(
				int width, int height,
				double xLow, double xHi, double xTickUnit,
				double yLow, double yHi, double yTickUnit
		) {
			setMinSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
			setPrefSize(width, height);
			setMaxSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);

			xAxis = new NumberAxis(xLow, xHi, xTickUnit);
			xAxis.setSide(Side.BOTTOM);
			xAxis.setMinorTickVisible(false);
			xAxis.setPrefWidth(width);
			xAxis.setLayoutY(height / 2);

			yAxis = new NumberAxis(yLow, yHi, yTickUnit);
			yAxis.setSide(Side.LEFT);
			yAxis.setMinorTickVisible(false);
			yAxis.setPrefHeight(height);
			yAxis.layoutXProperty().bind(
					Bindings.subtract(
							(width / 2) + 1,
							yAxis.widthProperty()
					)
			);

			getChildren().setAll(xAxis, yAxis);
		}

		public NumberAxis getXAxis() {
			return xAxis;
		}

		public NumberAxis getYAxis() {
			return yAxis;
		}
	}

	private void bresenhamLine( double x0, double y0, double x1, double y1)
	{
		double dx =  Math.abs(x1-x0), sx = x0<x1 ? 1. : -1.;
		double dy = -Math.abs(y1-y0), sy = y0<y1 ? 1. : -1.;
		double err = dx+dy, e2; /* error value e_xy */

		while( true){
			graphicsContext.drawImage( brush, x0 , y0);
			if (x0==x1 && y0==y1) break;
			e2 = 2.*err;
			if (e2 > dy) { err += dy; x0 += sx; } /* e_xy+e_x > 0 */
			if (e2 < dx) { err += dx; y0 += sy; } /* e_xy+e_y < 0 */
		}
	}


	public static Image createImage(Node node) {

		WritableImage wi;

		SnapshotParameters parameters = new SnapshotParameters();
		parameters.setFill(Color.TRANSPARENT);

		int imageWidth = (int) node.getBoundsInLocal().getWidth();
		int imageHeight = (int) node.getBoundsInLocal().getHeight();

		wi = new WritableImage(imageWidth, imageHeight);
		node.snapshot(parameters, wi);

		return wi;

	}

	private void addListeners() {

		myCanvas.addEventFilter(MouseEvent.ANY, e -> {
			System.out.println("dsds");

			mouseLocation = new Point2D(e.getX(), e.getY());

			mousePressed = e.isPrimaryButtonDown();

		});


	}

	public static Image createBrush(double radius, Color color) {

		// create gradient image with given color
		Circle brush = new Circle(radius);

		RadialGradient gradient1 = new RadialGradient(0, 0, 0, 0, radius, false, CycleMethod.NO_CYCLE, new Stop(0, color.deriveColor(1, 1, 1, 0.3)), new Stop(1, color.deriveColor(1, 1, 1, 0)));

		brush.setFill(gradient1);

		// create image
		return createImage(brush);

	}

	private void startAnimation() {

		loop = new AnimationTimer() {

			@Override
			public void handle(long now) {

				if( mousePressed) {

					// try this
					// graphicsContext.drawImage( brush, mouseLocation.getX() - brushWidthHalf, mouseLocation.getY() - brushHeightHalf);

					// then this
					//bresenhamLine( prevMouseLocation.getX(), prevMouseLocation.getY(), mouseLocation.getX(), mouseLocation.getY());
					bresenhamLine(0,0,200,800);

				}

				prevMouseLocation = new Point2D( mouseLocation.getX(), mouseLocation.getY());

			}
		};

		loop.start();

	}

	class Plot extends Pane {
		public Plot(
				Function<Double, Double> f,
				double xMin, double xMax, double xInc,
				Axes axes
		) {
			Path path = new Path();
			path.setStroke(Color.ORANGE.deriveColor(0, 1, 1, 0.6));
			path.setStrokeWidth(2);

			path.setClip(
					new Rectangle(
							0, 0,
							axes.getPrefWidth(),
							axes.getPrefHeight()
					)
			);

			double x = xMin;
			double y = f.apply(x);

			path.getElements().add(
					new MoveTo(
							mapX(x, axes), mapY(y, axes)
					)
			);

			x += xInc;
			while (x < xMax) {
				y = f.apply(x);

				path.getElements().add(
						new LineTo(
								mapX(x, axes), mapY(y, axes)
						)
				);

				x += xInc;
			}

			setMinSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
			setPrefSize(axes.getPrefWidth(), axes.getPrefHeight());
			setMaxSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);

			getChildren().setAll(axes, path);
		}

		private double mapX(double x, Axes axes) {
			double tx = axes.getPrefWidth() / 2;
			double sx = axes.getPrefWidth() /
					(axes.getXAxis().getUpperBound() -
							axes.getXAxis().getLowerBound());

			return x * sx + tx;
		}

		private double mapY(double y, Axes axes) {
			double ty = axes.getPrefHeight() / 2;
			double sy = axes.getPrefHeight() /
					(axes.getYAxis().getUpperBound() -
							axes.getYAxis().getLowerBound());

			return -y * sy + ty;
		}

	}


}
