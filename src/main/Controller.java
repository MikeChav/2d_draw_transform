
package main;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller  implements Initializable {

	@FXML
	MenuBar menuBar;
	@FXML
	Canvas myCanvas;
	@FXML
	Pane Container;
	@FXML
	VBox myBox;
	@FXML
	RadioButton xAxis, yAxis, yEqualsX, yMinusX, customLine;
	@FXML
	TextField customX, customY;

    private int AxisWidth, AxisHeight, XRange=8, YRange=6;

	public static GraphicsContext graphicsContext;

	Point2D mouseLocation = null, prevMouseLocation = null;

	LinkedList<Point2D> listOfPoints = new LinkedList<>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		graphicsContext = myCanvas.getGraphicsContext2D();

		myCanvas.setHeight(Commons.getHeight() - menuBar.getHeight());
		myCanvas.setWidth(Commons.getWidth());

		AxisWidth = (int) myCanvas.getWidth();
        AxisHeight = (int) myCanvas.getHeight();

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

		myCanvas.addEventFilter(MouseEvent.MOUSE_CLICKED, this::drawPolygon);
	}

	public static void staticExitAll(){
		(new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?")).showAndWait()
				.filter(response -> response == ButtonType.OK)
				.ifPresent(response -> Platform.exit());

	}

	@FXML
	private  void exitAll(){
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

	private List<Point2D> drawPolygon(MouseEvent e){
		if (mouseLocation == null && prevMouseLocation == null)
			listOfPoints = new LinkedList<>();

		if (mouseLocation != null)
			prevMouseLocation = mouseLocation;
		mouseLocation = new Point2D(e.getX(), e.getY());
		Point2D newPoint = translatePoint(mouseLocation);

		System.out.println("Translated: ("+newPoint.getX()+", "+newPoint.getY()+")");

		listOfPoints.add(mouseLocation);
		if (listOfPoints.size() == 1)
			Commons.bresenhamLine(mouseLocation.getX(), mouseLocation.getY(), mouseLocation.getX(), mouseLocation.getY());
		else{
			if(e.getClickCount() == 1)
				Commons.bresenhamLine(prevMouseLocation.getX(), prevMouseLocation.getY(), mouseLocation.getX(), mouseLocation.getY());
			if (e.getClickCount() == 2 || listOfPoints.size() == 8) {
				Point2D firstPoint = listOfPoints.get(0);
				Commons.bresenhamLine(mouseLocation.getX(), mouseLocation.getY(), firstPoint.getX(), firstPoint.getY());
				mouseLocation = null;
				prevMouseLocation = null;
				listOfPoints.add(firstPoint);
			}
		}
		return listOfPoints;
	}

	/**To be added before transforming or filling**/


	@FXML
	private void doReflection(){
		Alert choosePolygon = new Alert(Alert.AlertType.CONFIRMATION, "About which line do you want to reflect?");
		try {
			choosePolygon.getDialogPane().setContent(FXMLLoader.load(getClass().getResource("layouts/reflection.fxml")));
		}
		catch (IOException e){
			return;
		}

		choosePolygon.showAndWait()
				.filter(response -> response == ButtonType.OK)
				.ifPresent(response -> {
					if (yAxis.isSelected()) {

					}
					else if (xAxis.isSelected()) {

					}
					else if (yEqualsX.isSelected()) {

					}
					else if (yMinusX.isSelected()) {

					}
					else {

					}
				});

	}

}
