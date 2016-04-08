package main;

import javafx.scene.SnapshotParameters;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 * Created by michael on 4/4/16.
 */
public class ColorFill {

	public static void flood(boolean[][] mark, int row, int col, Color tgtColor) {
		// make sure row and col are inside the image
		if (row < 0) return;
		if (col < 0) return;
		if (row >= Controller.staticMyCanvas.getHeight()) return;
		if (col >= Controller.staticMyCanvas.getWidth()) return;

		// make sure this pixel hasn't been visited yet
		if (mark[row][col]) return;

		// make sure this pixel is the right color to fill
		WritableImage image = Controller.staticMyCanvas.snapshot(new SnapshotParameters(), null);
		PixelReader reader = image.getPixelReader();

		if (reader.getColor(col, row).equals(Color.WHITE))

			// fill pixel with target color and mark it as visited
			Controller.staticGraphicsContext.setFill(tgtColor);
		Controller.staticGraphicsContext.strokeLine(col, row, col, row);
		mark[row][col] = true;


//		// recursively fill surrounding pixels
//		// (this is equivalent to depth-first search)
//		flood(mark, row - 1, col, tgtColor);
//		flood(mark, row + 1, col, tgtColor);
//		flood(mark, row, col - 1, tgtColor);
//		flood(mark, row, col + 1, tgtColor);
	}

}
