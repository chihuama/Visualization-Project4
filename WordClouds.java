import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import processing.core.PApplet;
import processing.core.PImage;

public class WordClouds {
  PApplet parent; 
  int scaleFactor = 1;
  public int imageLeft, imageTop;
  public int imageWidth, imageHeight;
  HashMap<Integer,PImage> timeToIndex;
  public WordClouds(PApplet parent,int scaleFactor,int left, int top, int w) {
    this.imageLeft = left;
    this.imageTop = top;
    this.imageWidth = w;
    this.imageHeight = (int) ( w * 0.75);
    this.parent = parent;
    this.scaleFactor = scaleFactor;
    
  }
  
  public void Setup() {
    timeToIndex = new HashMap<Integer,PImage>();        
    for (int i=0; i<21; i++) {
      int j = (i%2)*20;
      timeToIndex.put(i, parent.loadImage("wordCloud_D"+i+".png"));      

    }
    //wordCloudImage = loadImage("wordCloud_D20.png");
  }
  
  public void Draw(int currentDayIndex) {
	  if( timeToIndex.get(currentDayIndex) != null){		  	
		  	parent.image(timeToIndex.get(currentDayIndex), imageLeft, imageTop, imageWidth, imageHeight);
	  }	   
  }
}