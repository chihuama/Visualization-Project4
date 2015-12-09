import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import processing.core.*;


public class PieChart {
	PApplet parent = null;
	float x=0;
	float y=0;
	float diameter=0;
	float labelX = 0;
	float labelY = 0;
	int mode = 1;
	float split = 0;
	String qry = "";
	String curCountry = "";
	String curGenre = "";
	int scaleFactor = 1;
	String label = "";
	String title = "Sickness Percentages";
	boolean isVisible = false;
	float x1 = 0;
	float x2 = 0;
	float y1 = 0;
	float y2 = 0;
	CopyOnWriteArrayList<pieCatagory> catagories;
	
	public PieChart(PApplet parent, int scaleFactor, float xBorder1, float xBorder2, float yBorder1, float yBorder2){
		this.parent = parent;
		this.scaleFactor = scaleFactor;
		x1 = xBorder1;
		x2 = xBorder2;
		y1 = yBorder1;
		y2 = yBorder2;
		
		x = xBorder1; //newX is width
		y = 6*scaleFactor;
		diameter = ((53*scaleFactor)-(2*y)); //newY is top of graph frame
		isVisible = true;
		
		catagories = new CopyOnWriteArrayList<pieCatagory>();
		
	}
	public void insertCatagory(String label, float percent, int color){
		pieCatagory o = new pieCatagory(label,percent,color);
		if(!catagories.contains(o)){
			catagories.add(o);
		}
	}
	public void removeCatagory(String label){
		pieCatagory o = new pieCatagory(label, 0,0);
		if(catagories.contains(o)){
			catagories.remove(o);
		}
	}
	
	public void draw(){
		if(isVisible){
			parent.fill(254);
			parent.textAlign(parent.LEFT);
			//parent.textSize(11*scaleFactor);			
			//parent.text(title,x1-5,y1+(15*scaleFactor));
                        parent.textSize(15*scaleFactor);
                        parent.text(title,parent.width-0.35f*parent.width,y1+(15*scaleFactor)+0.06f*parent.height);
			parent.rectMode(parent.CORNERS);
			int sg_pos = 0;
			//float gYPos = (y2-y1)/2;
			for(pieCatagory c : catagories){
				//float sgXPos = parent.map(sg_pos, 0, catagories.size(), x1+5, x2);
                                float sgYPos = parent.map(sg_pos, 0, catagories.size(), y1+(15*scaleFactor)+0.4f*parent.height, y1+(15*scaleFactor)+0.8f*parent.height);
				if( (sg_pos) % 4 == 0 && sg_pos != 0){
					// if it is even add a new line
					//gYPos += 15*scaleFactor;
					sg_pos = 0;
					//sgXPos = parent.map(sg_pos, 0, catagories.size(), x1+5, x2);
                                        sgYPos = parent.map(sg_pos, 0, catagories.size(), y1+(15*scaleFactor)+0.4f*parent.height, y1+(15*scaleFactor)+0.8f*parent.height);
				}
				parent.fill(c.color);
				//parent.rect(sgXPos, gYPos+10-(6*scaleFactor), sgXPos+(6*scaleFactor), gYPos+10);
                                parent.rect(parent.width-0.33f*parent.width, sgYPos, parent.width-0.32f*parent.width+(10*scaleFactor), sgYPos+(10*scaleFactor));
                               // parent.rect(parent.width-0.35f*parent.width, y1+(15*scaleFactor)+0.08f*parent.height+5*diameter, parent.width-0.35f*parent.width+(10*scaleFactor), y1+(25*scaleFactor)+0.08f*parent.height+5*diameter);
				parent.fill(254);
				//parent.text(c.label,sgXPos+(10*scaleFactor),gYPos+10);
                                parent.text(c.label,parent.width-0.33f*parent.width,sgYPos-(10*scaleFactor));
				sg_pos++;
			}
			float pieX1 = x2- diameter;
			float pieY1 = y1+5*scaleFactor;
			
			split = 1.0f;
			parent.fill(0, 64, 128);
			parent.ellipseMode(parent.CORNER);
			//parent.arc(pieX1, pieY1, diameter, diameter, 0, (float) ((float)2*Math.PI));
			int pos = 0;
			for(pieCatagory c : catagories){
							
				split = ((float)(c.value*(2*(float)Math.PI)));		
				parent.fill(c.color);
				if(pos == 0){
					parent.arc(pieX1 - 0.3f*parent.width, pieY1+(15*scaleFactor)+0.08f*parent.height, diameter*2, diameter*2, 0, (float) (c.value*2*Math.PI));
				}
				else{
					float cur_radianVal = ((float) (c.value*2*Math.PI));
					float prev_radianVal = 0;//(float) (catagories.get(pos-1).value*2*Math.PI);
					for(int p = pos-1; p >= 0; p--){
						if(catagories.get(p) != null){
							prev_radianVal += (float) (catagories.get(p).value*2*Math.PI);
						}						
					}									
					parent.arc(pieX1 - 0.3f*parent.width, pieY1+(15*scaleFactor)+0.08f*parent.height, diameter*2, diameter*2, prev_radianVal, prev_radianVal+cur_radianVal);
				}
				
				pos++;
				
			}
			
			
			
			
			

			parent.textAlign(parent.LEFT);

			parent.textSize(8*((int)(1.2*scaleFactor)));
		}
	}
	protected class pieCatagory{
		public String label;
		public int color;
		public float value;
		public pieCatagory(String label, float value, int color){
			this.label = label;
			this.value = value;
			this.color = color;
		}
		@Override public boolean equals(Object aThat) {
			if ( !(aThat instanceof pieCatagory) ) return false;		
			pieCatagory that = (pieCatagory)aThat;
			return this.label.equals(that.label);		
		}
		
	}
}
