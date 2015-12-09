

import processing.core.*;

public class TouchButton {
	String content = "";
	float Width = 0;
	float Height = 0;
	float xPosition = 0;
	float yPosition = 0;
	boolean isVisible = true;
	protected PApplet parent = null;
	int background_color = 150;
	public TouchButton(PApplet parent,String content, float width, float height, float xPos, float yPos){
		this.parent = parent;
		this.content = content;
		Width = width;
		Height = height;
		xPosition = xPos;
		yPosition = yPos;
	}
	public boolean isVisible(){
		return isVisible;
	}
	public void setVisible(){
		isVisible = true;
	}
	public void setCollapsed(){
		isVisible = false;
	}
	
	public void draw(){
		if(isVisible){
			parent.fill(background_color);
			parent.rectMode(parent.CORNERS);
			parent.rect(xPosition, yPosition, xPosition+Width, yPosition+Height,15);
			parent.fill(0);
			parent.textAlign(parent.CENTER);
			parent.text(content, xPosition+(Width/2), (float) (yPosition+(Height/2)+2));
			parent.textAlign(parent.LEFT);
		}		
	}
	
	public boolean Clicked(float xPos, float yPos){
		boolean clicked = xPos >= xPosition && xPos <= (xPosition+Width) &&
				yPos >= yPosition && yPos <= (yPosition+Height) && isVisible;

		return clicked;
	}
	
	public String getContent(){
		return content;
	}
	
	public void setContent(String content){
		this.content = content;
	}
	
	public void resetBackground(){
		background_color = 150;
	}
	public void setBackground(int color){
		background_color = color;
	}
	public void setPosition(float xPos, float yPos){
		this.xPosition = xPos;
		this.yPosition = yPos;
	}
	
	public float getWidth(){
		return Width;
	}
	public float getHeight(){
		return Height;
	}
}
