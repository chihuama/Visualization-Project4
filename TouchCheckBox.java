

import processing.core.PApplet;


public class TouchCheckBox extends TouchButton {

	public boolean isChecked = false;
	public TouchCheckBox(PApplet parent, String content, float width,
			float height, float xPos, float yPos) {
		super(parent, content, width, height, xPos, yPos);
	}
	
	@Override
	public boolean Clicked(float xPos, float yPos){		
		boolean clicked = xPos >= xPosition && xPos <= (xPosition+Width) && yPos >= yPosition && yPos <= (yPosition+Height) && isVisible;
		if(clicked){			
			if(isChecked){
				isChecked = false;
			}
			else{
				isChecked = true;
			}
			return true;
		}
		else{
			return false;
		}
	}
	
	public boolean LongPressed(float xPos, float yPos){
		return xPos >= xPosition && xPos <= (xPosition+Width) && yPos >= yPosition && yPos <= (yPosition+Height) && isVisible;
	}
	
	@Override
	public void draw(){
		if(isChecked){
			if(isVisible){
				parent.fill(0);
				parent.rectMode(parent.CORNERS);
				parent.rect(xPosition, yPosition, xPosition+Width, yPosition+Height,15);
				parent.fill(254);
				parent.textAlign(parent.CENTER);
				parent.text(content, xPosition+(Width/2), (float) (yPosition+(Height/2)+2));
				parent.textAlign(parent.LEFT);
			}
		}
		else{
			super.draw();
		}
	}
}
