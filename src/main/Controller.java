
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
	TextField p1X, p1Y, p2X, p2Y;

    public static int AxisWidth, AxisHeight, XRange=8, YRange=6;

	public static GraphicsContext graphicsContext;

	Point2D mouseLocation = null, prevMouseLocation = null;

	public static LinkedList<Point2D> listOfPoints = new LinkedList<>();

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

	private List<Point2D> drawPolygon(MouseEvent e){
		if (mouseLocation == null && prevMouseLocation == null)
			listOfPoints = new LinkedList<>();

		if (mouseLocation != null)
			prevMouseLocation = mouseLocation;
		mouseLocation = new Point2D(e.getX(), e.getY());
		Point2D newPoint = Commons.translatePoint(mouseLocation);

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

					Point2D p1 = new Point2D(0,0);
					Point2D p2 = new Point2D(0,0);

					if (yAxis.isSelected()) {
						p2 = new Point2D(0,100);
					}
					else if (xAxis.isSelected()) {
						p2 = new Point2D(100,0);
					}
					else if (yEqualsX.isSelected()) {
						p2 = new Point2D(100,100);
					}
					else if (yMinusX.isSelected()) {
						p2 = new Point2D(100,-100);
					}
					else if (customLine.isSelected()){
						p1 = new Point2D(Double.parseDouble(p1X.getText()),
										 Double.parseDouble(p1Y.getText()));
						p2 = new Point2D(Double.parseDouble(p2X.getText()),
										 Double.parseDouble(p2Y.getText()));
						System.out.println(p1X.getText()+"\n "+p1Y.getText()+"\n "+p2X.getText()+"\n "+p2Y.getText());
					}
					else return;

					(new Transformation()).reflection(p1, p2);
				});
	}

}
