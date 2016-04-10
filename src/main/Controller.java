
package main;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
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

	@FXML
	MenuBar menuBar;

	@FXML
	private Canvas myCanvas;

	static Canvas staticMyCanvas;

	@FXML
	private Pane container;
	private static Pane staticContainer;

	@FXML
	VBox myBox;

    static int AxisWidth, AxisHeight, XRange=8, YRange=6;

	static GraphicsContext staticGraphicsContext;

	private Point2D mouseLocation = null, prevMouseLocation = null;

	static LinkedList<Point2D> listOfPoints = Commons.listOfPoints;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		staticContainer = container;
		staticGraphicsContext = myCanvas.getGraphicsContext2D();
		staticMyCanvas = myCanvas;

		staticMyCanvas.setHeight(Commons.getHeight() - menuBar.getHeight());
		staticMyCanvas.setWidth(Commons.getWidth());

		AxisWidth = (int) staticMyCanvas.getWidth();
        AxisHeight = (int) staticMyCanvas.getHeight();

		setScales(XRange, YRange);

		final ContextMenu contextMenu = new ContextMenu();


		MenuItem item1 = new MenuItem("Flood Fill");
		item1.setOnAction(e-> doFloodFill());
		MenuItem item2 = new MenuItem("Boundary Fill");
		item2.setOnAction(e-> doBoundaryFill());
		MenuItem item3 = new MenuItem("Scan Line");
		item3.setOnAction(e-> doScanFill());
		contextMenu.getItems().addAll(item1, item2, item3);


		staticGraphicsContext.setFill(Color.TRANSPARENT);
		staticGraphicsContext.fillRect(0, 0, staticMyCanvas.getWidth(), staticMyCanvas.getHeight());
		staticGraphicsContext.setStroke(Color.BLACK);
		staticGraphicsContext.setLineWidth(1.0);

		staticMyCanvas.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
				if (e.getButton() == MouseButton.SECONDARY) {
					Commons.startPoint = new Point2D(e.getX(), e.getY());
					contextMenu.show(staticMyCanvas, e.getScreenX(), e.getScreenY());
				}
				else
					drawPolygon(e);
			}
		);
	}

	static void staticExitAll(){
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

	static void setScales(int maxX, int maxY) {
		Axes axes = new Axes(
				AxisWidth, AxisHeight,
				-maxX, maxX, 1,
				-maxY, maxY,1
		);

		StackPane layout = new StackPane(axes);
		layout.setStyle("-fx-background-color: white");
		Canvas v;
		try {
			v  = (Canvas) getContainer().getChildren().get(1);
		}
		catch (IndexOutOfBoundsException e) {
			v  = (Canvas) getContainer().getChildren().get(0);
		}
		getContainer().getChildren().clear();
		getContainer().getChildren().addAll(layout, v);
	}

	private static Pane getContainer() {
		return staticContainer;
	}

	@FXML
	private void doTranslation(){
		new AlertBox("translation", "How to translate", 315, 99);
	}

	@FXML
	private void doReflection(){
		new AlertBox("reflection", "Reflect About a Line", 266, 280);
	}

	@FXML
	private void doRotation(){
		new AlertBox("rotation","rotate about point",266,280);
	}

	@FXML
	private void doScaling(){
		new AlertBox("scaling","",266,280);
	}

	@FXML
	private void doShear(){
		new AlertBox("shear","",266,280);
	}

	@FXML
	private void doFloodFill(){
		new AlertBox("floodfill", "", 208, 151);
	}

	@FXML
	private void doBoundaryFill(){
		new AlertBox("boundaryfill", "", 236, 151);
	}

	@FXML
	private void doScanFill(){
		new AlertBox("scanfill", "", 216, 151);
	}

	@FXML
	private void changeScale() {
		new AlertBox("changescale", "", 337, 124);
	}
}
