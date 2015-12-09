
import java.util.ArrayList;
import java.util.Hashtable;

import omicronAPI.*;

import processing.core.*;


public class TouchMapImage {
	PApplet parent = null;


	public float borderx1 = 0;
	public float borderx2 = 0;
	public float bordery1 = 0;
	public float bordery2 = 0;

	float anchorPositionx = 0;
	float anchorPositiony = 0;
	int scaleFactor = 1;
	boolean isModest = false;
	
	
	Hashtable touchList;

	
	PVector lastTouchPos;
	PVector lastTouchPos2;
	int touchID1;
	int touchID2;

	PVector initTouchPos;
	PVector initTouchPos2;
	PImage mapImage = null;
	private int imageDefaultWidth = 0;
	private int imageDefaultHeight = 0;
	private int imageCurrentWidth = 0;
	private int imageCurrentHeight = 0;
	
	private ScreenLocation mouseLoc; 	
	private int zoomLevel = 1;
	private float centerX = 0;
	private float centerY = 0;
	ArrayList<Integer> screenSizes = null;
	public TouchMapImage(PApplet parent, int scaleFactor){
		// Image size is 5216 x 2653
		mapImage = parent.loadImage("Vastopolis_Map.png");
		//scaleFactor = 6;
		imageDefaultWidth = 869*scaleFactor;
		imageDefaultHeight = 442*scaleFactor;
		
		imageCurrentWidth = imageDefaultWidth;
		imageCurrentHeight = imageDefaultHeight;
		borderx1 = 0 ;
		borderx2 = imageCurrentWidth;
	    bordery1 = 0;
	    bordery2 = mapImage.height;
	    centerX = getWidth()/2;
	    centerY = getHeight()/2;
		mapImage.resize(imageCurrentWidth, imageCurrentHeight);
		screenSizes = new ArrayList<Integer>();
		screenSizes.add(imageCurrentWidth);
		screenSizes.add(imageCurrentHeight);
		
		lastTouchPos = new PVector();
		lastTouchPos2 = new PVector();
		initTouchPos = new PVector();
		initTouchPos2 = new PVector();
		PVector initTouchPos = new PVector();
		PVector initTouchPos2 = new PVector();
		
		
		this.parent = parent;		
		
	    
	    
	    mouseLoc = new ScreenLocation(0,0);
	    this.scaleFactor = scaleFactor;

	    touchList = new Hashtable();
	    

	}
	
	public float getWidth(){
		return mapImage.width;
	}
	
	public float getHeight(){
		return mapImage.height;
	}
	
	public float getOffsetX(){
		return borderx1;
	}
	
	public float getOffsetY(){
		return bordery1;
	}
	
	public ScreenLocation getPosByGIS(float lat, float longit){
		// get screen postion given lat and longit
		
	
		// try 2
		//float xPos = parent.map(lat,  42.3017f, 42.1609f,  borderx1, borderx2);
		//float yPos = parent.map(longit,93.5673f, 93.1923f, bordery1, bordery2);		

        float yPos = parent.map(lat,  42.1619f, 42.3015f, bordery2, bordery1);
        float xPos = parent.map(longit,93.1906f, 93.5673f, borderx2, borderx1);    

		return new ScreenLocation(xPos,yPos);
	}
	
	public MapGISPoint getGISByScreenPos(float xPos, float yPos){
		// get GIS by screen position
	
		// try 2		
		//float lat = parent.map(xPos, borderx1, borderx2,42.3017f, 42.1609f);
		//float longit  = parent.map(yPos, bordery1, bordery2, 93.5673f , 93.1923f );
		// try 3
        float longit = parent.map(xPos, borderx2, borderx1,42.1619f, 42.3015f);
        float lat  = parent.map(yPos, bordery2, bordery1, 93.1906f, 93.5673f );

		
		return new MapGISPoint(lat,longit);
	}
	
	public int getZoomLevel(){
		return this.zoomLevel;
	}
	
	public boolean isMapTouch(float xPos, float yPos){
		
		//System.out.println(xPos+", "+yPos+"\t "+scaledX+", "+scaledY);
		boolean isOnMap = borderx1 <= xPos &&
				borderx2 >= xPos &&
				bordery1 <= yPos &&
				bordery2 >= yPos;
		
		return isOnMap;
	}
	
	
	public void draw(){
		// Image size is 5216x2653
		float per = (zoomLevel-1)*0.1f;		

		mouseLoc.xPos = parent.mouseX;
		mouseLoc.yPos = parent.mouseY;
		
		
		
		parent.image(mapImage, borderx1 , bordery1 );
		

		
		
		//Border which fits the image perfectly
		parent.noFill();
		parent.stroke(255);
		parent.strokeWeight(2*scaleFactor);
		parent.rect(borderx1, bordery1, borderx2, bordery2);
		parent.stroke(0);
		parent.strokeWeight(1*scaleFactor);		
		
	
		parent.fill(255,243,52,90);
		parent.ellipseMode(parent.CENTER);
		parent.ellipse(lastTouchPos.x, lastTouchPos.y, 24*scaleFactor, 24*scaleFactor);
		parent.fill(0);
		
	
		
		// I need the border in push matrix = border outside the push matrix
		if(isMapTouch(mouseLoc.xPos,mouseLoc.yPos)){
			MapGISPoint location = getGISByScreenPos(mouseLoc.xPos,mouseLoc.yPos);
			
			parent.textSize(14*scaleFactor);
			ScreenLocation scrLoc = getPosByGIS(location.latitude,location.longitude);
			parent.fill(255);
			parent.text("POSByGIS "+location.latitude + ", " + location.longitude, scrLoc.xPos, scrLoc.yPos-(25*scaleFactor));
			parent.fill(254);
			parent.textSize(14*scaleFactor);
			parent.text("GISByPOS "+location.latitude + ", " + location.longitude, mouseLoc.xPos, mouseLoc.yPos-(10*scaleFactor));
			parent.textSize(8*scaleFactor);
			parent.fill(0);
		}
		
	}
	
	

	void touchDown(int ID, float xPos, float yPos, float xWidth, float yWidth) {
		if (isMapTouch(xPos, yPos)) {
			// Update the last touch position
			lastTouchPos.x = xPos;
			lastTouchPos.y = yPos;			
			// Add a new touch ID to the list
			touchID1 = ID;
			initTouchPos.x = xPos;
			initTouchPos.y = yPos;	
		
			ScreenLocation t = new ScreenLocation(xPos, yPos);
			touchList.put(ID, t);
		}
	}// touchDown

	void touchMove(int ID, float xPos, float yPos, float xWidth, float yWidth) {

		if (isMapTouch(xPos, yPos)) {

			if (touchList.size() < 5) {
				// Only one touch, drag map based on last position

				if (lastTouchPos.x < xPos) {
					// touch is moving right
					borderx1 += (5 * scaleFactor)*(1f+(.05f*zoomLevel));
				} else if (lastTouchPos.x > xPos) {
					// touch is moving left
					borderx1 -= (5 * scaleFactor)*(1f+(.05f*zoomLevel));
				}

				if (lastTouchPos.y < yPos) {
					// touch is going up
					bordery1 += (5 * scaleFactor)*(1f+(.05f*zoomLevel));
				} else if (lastTouchPos.y > yPos) {
					// touch is going down
					bordery1 -= (5 * scaleFactor)*(1f+(.05f*zoomLevel));
				}
				borderx2 = borderx1 + mapImage.width;
				bordery2 = bordery1 + mapImage.height;
				centerX = getWidth() / 2;
				centerY = getHeight() / 2;

			
			} else if (touchList.size() >= 5) {
				imageCurrentWidth = imageDefaultWidth;
				imageCurrentHeight = imageDefaultHeight;
				this.screenSizes.clear();
				screenSizes.add(imageDefaultWidth);
				screenSizes.add(imageDefaultHeight);
			    centerX = getWidth()/2;
			    centerY = getHeight()/2;
			    borderx1 = 0 ;
				borderx2 = imageCurrentWidth;
			    bordery1 = 0;
			    bordery2 = imageCurrentHeight;
			    System.out.println("zoomOut ("+zoomLevel+")\t"+imageCurrentWidth+","+imageCurrentHeight);
				mapImage = new PImage(imageCurrentWidth,imageCurrentHeight,parent.RGB);
				mapImage = parent.loadImage("Vastopolis_Map.png"); 
				mapImage.resize(imageCurrentWidth, imageCurrentHeight);

			}

			// Update touch IDs 1 and 2
			if (ID == touchID1) {
				lastTouchPos.x = xPos;
				lastTouchPos.y = yPos;
			} else if (ID == touchID2) {
				lastTouchPos2.x = xPos;
				lastTouchPos2.y = yPos;
			}

			// Update touch list
			ScreenLocation t = new ScreenLocation(xPos, yPos);
			touchList.put(ID, t);
		}

	}// touchMove

	void touchUp(int ID, float xPos, float yPos, float xWidth, float yWidth) {
		if (isMapTouch(xPos, yPos)) {
			touchList.remove(ID);
			centerX = getWidth() / 2;
			centerY = getHeight() / 2;
		}
		
	}// touchUp

	public void zoomIn() {
		zoomLevel++;
		float per = (zoomLevel-1)*0.1f;
		screenSizes.add(mapImage.width);
		screenSizes.add(mapImage.height);
		
		borderx1 = 0;
		bordery1 = 0;
		
		imageCurrentWidth = (int) (this.imageDefaultWidth*(1f+per));
		imageCurrentHeight = (int) (this.imageDefaultHeight*(1f+per));
					
		System.out.println("zoomIn ("+zoomLevel+")\t"+imageCurrentWidth+","+imageCurrentHeight);
		centerX = getWidth() / 2;
		centerY = getHeight() / 2;		
		
		mapImage = new PImage(imageCurrentWidth,imageCurrentHeight,parent.RGB);
		mapImage = parent.loadImage("Vastopolis_Map.png");
		mapImage.resize(imageCurrentWidth, imageCurrentHeight);
		borderx2 = (borderx1 + imageCurrentWidth);
		bordery2 = (bordery1 + imageCurrentHeight);
	}

	public void zoomOut() {
		
		if(zoomLevel == 2){
			zoomLevel--;
			imageCurrentWidth = imageDefaultWidth;
			imageCurrentHeight = imageDefaultHeight;
			this.screenSizes.clear();
			screenSizes.add(imageDefaultWidth);
			screenSizes.add(imageDefaultHeight);
		    centerX = getWidth()/2;
		    centerY = getHeight()/2;
		    borderx1 = 0;
			borderx2 = imageCurrentWidth;
		    bordery1 = 0;
		    bordery2 = imageCurrentHeight;
		    System.out.println("zoomOut ("+zoomLevel+")\t"+imageCurrentWidth+","+imageCurrentHeight);
			mapImage = new PImage(imageCurrentWidth,imageCurrentHeight,parent.RGB);
			mapImage = parent.loadImage("Vastopolis_Map.png"); 
			mapImage.resize(imageCurrentWidth, imageCurrentHeight);
			
			
		}
		else if(zoomLevel > 2){
			zoomLevel--;
			float per = (zoomLevel-1)*0.1f;			
			this.imageCurrentWidth = screenSizes.get(screenSizes.size()-2);
			this.imageCurrentHeight = screenSizes.get(screenSizes.size()-1);
			screenSizes.remove(screenSizes.size()-2);
			screenSizes.remove(screenSizes.size()-1);
			borderx1 = 0;
			borderx2 = imageCurrentWidth;
		    bordery1 = 0;
		    bordery2 = imageCurrentHeight;
			
			System.out.println("zoomOut ("+zoomLevel+")\t"+imageCurrentWidth+","+imageCurrentHeight);
			centerX = getWidth() / 2;
			centerY = getHeight() / 2;		
			
			mapImage = new PImage(imageCurrentWidth,imageCurrentHeight,parent.RGB);
			mapImage = parent.loadImage("Vastopolis_Map.png"); 
			mapImage.resize(imageCurrentWidth, imageCurrentHeight);
		}
	}

}
