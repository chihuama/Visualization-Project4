

import omicronAPI.*;
import processing.core.*;

public class TouchListener implements OmicronTouchListener {

	private PApplet parent = null;

	 public TouchListener(PApplet parent){
		 this.parent = parent;
	 }

	// Called on a touch down event
	// mousePressed events also call this with an ID of -1 and an xWidth and
	// yWidth of 10.
	public void touchDown(int ID, float xPos, float yPos, float xWidth,
			float yWidth) {
		if (((P4Sketch) parent).scaleFactor >= 6) {
			//parent.fill(255, 0, 0);
			//parent.noStroke();
			//parent.ellipse(xPos, yPos, xWidth, yWidth);
		}

		// This is an optional call if you want the function call in the main
		// applet class.
		// 'OmicronExample' should be replaced with the sketch name i.e.
		// ((SketchName)applet).touchDown( ID, xPos, yPos, xWidth, yWidth );
		// Make sure applet is defined as PApplet and that 'applet = this;' is
		// in setup().
		((P4Sketch) parent).touchDown(ID, xPos, yPos, xWidth, yWidth);
	}// touchDown

	// Called on a touch move event
	// mouseDragged events also call this with an ID of -1 and an xWidth and
	// yWidth of 10.
	public void touchMove(int ID, float xPos, float yPos, float xWidth,
			float yWidth) {
		if (((P4Sketch) parent).scaleFactor >= 6) {
			//parent.fill(0, 255, 0);
			//parent.noStroke();
			//parent.ellipse(xPos, yPos, xWidth, yWidth);
		}

		((P4Sketch) parent).touchMove(ID, xPos, yPos, xWidth, yWidth);
	}// touchMove

	// Called on a touch up event
	// mouseReleased events also call this with an ID of -1 and an xWidth and
	// yWidth of 10.
	public void touchUp(int ID, float xPos, float yPos, float xWidth,
			float yWidth) {
		if (((P4Sketch) parent).scaleFactor >= 6) {
			//parent.fill(0, 0, 255);
			//parent.noStroke();
			//parent.ellipse(xPos, yPos, xWidth, yWidth);
		}

		((P4Sketch) parent).touchUp(ID, xPos, yPos, xWidth, yWidth);
	}// touchUp

}// TouchListener