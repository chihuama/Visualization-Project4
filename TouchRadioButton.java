import processing.core.PApplet;


public class TouchRadioButton extends TouchButton {
	boolean isChecked = false;
	public TouchRadioButton(PApplet parent, String content, float width,
			float height, float xPos, float yPos) {
		super(parent, content, width, height, xPos, yPos);
	}
	@Override
	public boolean Clicked(float xPos, float yPos){
		boolean clicked = xPos >= xPosition && xPos <= (xPosition+Width) &&
				yPos >= yPosition && yPos <= (yPosition+Height) && isVisible; 		
		return clicked;
	}
	
	public void setChecked(boolean checked){
		isChecked = checked;
	}
	
	@Override	
	public void draw(){
		if(isVisible){
			int font_color = 254;
			if(isChecked){
				background_color = 0;
				font_color = 254;
			}
			else{
				background_color = 130;
				font_color = 0;
			}
			parent.fill(background_color);
			parent.rectMode(parent.CORNERS);
			parent.rect(xPosition, yPosition, xPosition+Width, yPosition+Height);
			parent.fill(font_color);
			parent.textAlign(parent.CENTER);
			parent.text(content, xPosition+(Width/2), (float) (yPosition+(Height/2)+2));
			parent.textAlign(parent.LEFT);
		}		
	}
}
