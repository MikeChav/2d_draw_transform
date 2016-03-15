package main;

import javafx.scene.shape.Line;

/**
 * Created by michael on 3/15/16.
 */
public class BLine {

	float Xstart;
	float Ystart;
	float Xend;
	float Yend;

	public BLine(float xstart, float ystart, float xend, float yend) {
		Xstart = xstart;
		Ystart = ystart;
		Xend = xend;
		Yend = yend;
		draw();
	}

	public void draw(){

		Line line = new Line(Xstart, Ystart, Xend, Yend);

	}
}
