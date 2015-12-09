import java.util.ArrayList;

import processing.core.PApplet;



public class TouchRadioButtonGroup {
	private PApplet parent;
	private float item_width;
	private float item_height;
	private float xPos;
	private float yPos;
	private boolean isVertical = false;
	public boolean isVisible = true;
	
	public ArrayList<TouchRadioButton> Buttons;
	
	public TouchRadioButtonGroup(PApplet parent, float xPos, float yPos, boolean isVertical){
		this.parent = parent;
		
		this.xPos = xPos;
		this.yPos = yPos;
		this.isVertical = isVertical;
		this.Buttons = new ArrayList<TouchRadioButton>();
	}
	
	public float getXPos(){
		return xPos;
	}
	
	
	public void AddItem(TouchRadioButton item){
		if(item != null){
			Buttons.add(item);
		}
	}
	public void draw(){
		//draw each menu item if open
		if(isVisible){
			if(isVertical){
				int p = 0;
				for(TouchRadioButton ck : Buttons){
					ck.setPosition(xPos, yPos+(p*ck.Height));
					ck.draw();
					p++;
				}
			}
			else{
				int p = 0;
				for(TouchRadioButton ck : Buttons){
					ck.setPosition(xPos+(p*ck.Width), yPos);
					ck.draw();
					p++;
				}
			}

			
		}
	}
	public void setChecked(int index, boolean checked){
		if(Buttons.size() >= index){
			Buttons.get(index).setChecked(checked);
		}
	}
	
	public boolean isChecked(int index){
		if(Buttons.size() >= index){
			return Buttons.get(index).isChecked;
		}
		return false;
	}
	public int TouchHandler(float xPos, float yPos){
		if(isVisible){
			boolean clicked = false;
			int p = 0;
			for(TouchRadioButton ck : Buttons){
				clicked = ck.Clicked(xPos, yPos);				
				if(clicked){					
					Buttons.get(p).isChecked = true;
					int skip = p;
					int start = 0;
					for(TouchRadioButton c : Buttons){
						if(start != skip){
							c.isChecked = false;
						}
						start++;
					}
					return p;
				}
				p++;
			}			
		}
		return -1;
	}
	
}
