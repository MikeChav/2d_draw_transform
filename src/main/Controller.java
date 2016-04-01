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
import javafx.scene.layout.StackPane;
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
	private int AxisWidth ;
	private int AxisHeight ;

    int XRange=8;
    int YRange=6;

	GraphicsContext graphicsContext;

	Point2D mouseLocation = null;
	Point2D prevMouseLocation = null;

	List<Point2D> listOfPoints = new LinkedList<>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		graphicsContext = myCanvas.getGraphicsContext2D();

         AxisWidth = (int)myCanvas.getWidth();
        AxisHeight = (int)myCanvas.getHeight();

        Axes axes = new Axes(
                AxisWidth, AxisHeight,
                -XRange, XRange, 1,
                -YRange, YRange,1
        );

        StackPane layout = new StackPane(axes);
        layout.setStyle("-fx-background-color: slategrey");
        Canvas v  = (Canvas)Container.getChildren().get(0);
        Container.getChildren().clear();
        Container.getChildren().addAll(layout,v);

		graphicsContext.setFill(Color.TRANSPARENT);
		graphicsContext.fillRect(0, 0, myCanvas.getWidth(), myCanvas.getHeight());
		graphicsContext.setStroke(Color.WHITE);
		graphicsContext.setLineWidth(1.0);

		myCanvas.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
			if (mouseLocation != null)
				prevMouseLocation = mouseLocation;
			mouseLocation = new Point2D(e.getX(), e.getY());
            System.out.println(myCanvas.getWidth()+";"+myCanvas.getHeight());
            System.out.println(mouseLocation.getX()+","+mouseLocation.getY());
			Point2D newPoint = translatePoint(mouseLocation);
			System.out.println("Translated: ("+newPoint.getX()+", "+newPoint.getY()+")");

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
	}

	private void bresenhamLine( double x0, double y0, double x1, double y1) {
		double dx =  Math.abs(x1-x0), sx = x0<x1 ? 1. : -1.;
		double dy = -Math.abs(y1-y0), sy = y0<y1 ? 1. : -1.;
		double err = dx+dy, e2; /* error value e_xy */

		while(true){
			graphicsContext.strokeLine(x0, y0, x0, y0);
			if (x0==x1 && y0==y1)
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

	private Point2D translatePoint(Point2D point) {
		return (new Point2D(translateX(point.getX()), translateY(point.getY())));
	}

	private double translateX(double x){
		return ((x-(AxisWidth/2))/(AxisWidth/(2*XRange)));
	}

	private double translateY(double y){
		return (((AxisHeight/2) - y)/(AxisHeight/(2*YRange)));
	}

}
