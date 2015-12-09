import java.util.Calendar;
import java.util.Date;

import omicronAPI.*;
import processing.core.PApplet;

//TimeSiler timeSiler;


public class TimeSlider {
	PApplet parent;
	int scaleFactor = 1;
	String StartDate, EndDate;
  int sWidth, sHeight;    // width and height of bar
  int sLeft, sTop;         // x and y position of bar
  float sposStart, newsposStart;    // x position of slider
  float sposEnd, newsposEnd;    // x position of slider
  int sposMin, sposMax;   // max and min values of slider
  int loose = 2;              // how loose/heavy
  boolean over;           // is the mouse over the slider?
  boolean locked;
  boolean lockedStart;
  boolean lockedEnd;
  //float ratio;
  int days = 21;
  
  public int startDay,endDay,Day;
  
  int touchX, touchY;
  //boolean plus;
  
  
  TimeSlider (PApplet p,int scaleFactor,int xp, int yp, int sw) {
	  this.parent = p;
	  this.scaleFactor = scaleFactor;
    sWidth = sw; // 210
    sHeight = sWidth/days; //sh;

    //int widthtoheight = sw - sh;
    //ratio = (float)sw / (float)widthtoheight;

    sLeft = xp;
    sTop = yp-sHeight/2;

    sposStart = sLeft;
    newsposStart = sposStart;
    
    sposEnd = sLeft + sWidth - sHeight;
    newsposEnd = sposEnd;

    sposMin = sLeft;
    sposMax = sLeft + sWidth - sHeight;
    
  }
  
  void Draw() {
    update();
    display();
  }
  
  void update() {

    /*if(over()) {
      over = true;
    } else {
      over = false;
    }

    if(mousePressed && over) {
      //locked = true;
      if (abs(mouseX - (sposStart+sHeight/2)) <= 2*scaleFactor) {
        lockedStart = true;
      }
      if (abs(mouseX - (sposEnd+sHeight/2)) <= 2*scaleFactor) {
        lockedEnd = true;
      }
    }

    if(!mousePressed) {
      //locked = false;
      lockedStart = false;
      lockedEnd = false;
    }*/

    
    if(lockedStart) {
      //newsposStart = constrain(parent.mouseX-sHeight/2, sposMin, (int)sposEnd - sHeight);
      newsposStart = constrain(touchX-sHeight/2, sposMin, (int)sposEnd);// - sHeight);
    }
    
    if(lockedEnd) {
      //newsposEnd = constrain(parent.mouseX-sHeight/2, (int)sposStart + sHeight, sposMax);
      newsposEnd = constrain(touchX-sHeight/2, (int)sposStart + sHeight, sposMax);
    }

    if(parent.abs(newsposStart - sposStart) > 1) {
      sposStart = sposStart + (newsposStart-sposStart)/loose;
    }
    
    if(parent.abs(newsposEnd - sposEnd) > 1) {
      sposEnd = sposEnd + (newsposEnd-sposEnd)/loose;
    }
  }
  
  int constrain(int val, int minv, int maxv) {
    return parent.min(parent.max(val, minv), maxv);
  }
  
  /*boolean over() {
    if(mouseX > sLeft && mouseX < sLeft+sWidth && mouseY > sTop && mouseY < sTop+sHeight) {
      return true;
    } else {
      return false;
    }
  }*/
  
  void display() {

    parent.noStroke();
    parent.fill(255);
    parent.rectMode(parent.CORNER);
    parent.rect(sLeft, sTop, sWidth, sHeight);
    
    //parent.rect(sLeft, sTop - 3*sHeight/2, sHeight, sHeight);
    //parent.rect(sLeft + 3*sHeight/2, sTop - 3*sHeight/2, sHeight, sHeight);

    if(locked) {
      //fill(153, 102, 0);
      parent.fill(100);
    } else {
      parent.fill(100,128);
    }
    parent.rect(sposStart, sTop - sHeight/4, sHeight, 3*sHeight/2);
    parent.rect(sposEnd, sTop - sHeight/4, sHeight, 3*sHeight/2);
    
    parent.fill(100);
    parent.rect(sLeft, sTop, sposStart - sLeft, sHeight);
    parent.rect(sposEnd + sHeight, sTop, sLeft + sWidth - sposEnd - sHeight, sHeight);
    
    parent.fill(0);
    parent.textSize(sHeight*3/5);
    parent.textAlign(parent.LEFT,parent.CENTER);
    parent.text("30", scaleFactor + sLeft, sTop + sHeight/2);
    for (int i=1; i<days; i++) {
      parent.text(i, scaleFactor + sLeft + i*sWidth/days, sTop + sHeight/2);
    } 
    
    //parent.textSize(sHeight);
    //parent.textAlign(parent.CENTER,parent.CENTER);
    //parent.text("_", sLeft + sHeight/2, sTop - 3*sHeight/2 - scaleFactor);
    //parent.text("+", sLeft + 2*sHeight - scaleFactor, sTop - sHeight);  
    
    for (int i=0; i<days; i++) {
      float sStart = sposStart-sLeft;
      if (parent.abs(sStart - i*sWidth/days) <= 3*scaleFactor) {
        //println("Day : "+i);
        if (i==0) {
          StartDate = "2011-4-30";
        } else {
          StartDate = "2011-5-"+i;
        }
        //println("Date Start: "+StartDate);
        startDay = i;
        Day = i;
      }
      float sEnd = sposEnd-sLeft;
      if (parent.abs(sEnd - i*sWidth/days) <= 3*scaleFactor) {
        if (i==0) {
          EndDate = "2011-4-30";
        } else {
          EndDate = "2011-5-"+i;
        }
        endDay = i;
        //println("Date End: "+EndDate);
      }
    } // end - for
    parent.textSize(8*scaleFactor);
    parent.stroke(0);
    
  }
  
  void touchDown(int ID, float xPos, float yPos, float xWidth, float yWidth) {
    
    if (parent.abs(xPos - (sposStart+sHeight/2)) <= 3*scaleFactor) {
      lockedStart = true;
      locked = true;
      touchX = (int)xPos;
      touchY = (int)yPos;
    } else {
      lockedStart = false;
      locked = false;
    }
    
    if (parent.abs(xPos - (sposEnd+sHeight/2)) <= 3*scaleFactor) {
      lockedEnd = true;
      //locked = true;
      touchX = (int)xPos;
      touchY = (int)yPos;
    } else {
      lockedEnd = false;
      //locked = false;
    }

  }
  
  void touchMove(int ID, float xPos, float yPos, float xWidth, float yWidth) {
    if(xPos > sLeft && xPos < sLeft+sWidth && yPos > sTop && yPos < sTop+sHeight) {
      over = true;
      touchX = (int)xPos;
      touchY = (int)yPos;
    } else {
      over = false;
    }
  }
  void touchUp(int ID, float xPos, float yPos, float xWidth, float yWidth) {
    lockedStart = false;
    lockedEnd = false;
    locked = false;
    //plus = false;
    //touchX = (int)xPos;
    //touchY = (int)yPos;
  }  
  
  public float getOriginX(){
	  return sLeft;
  }
  public float getOriginY(){
	  return sTop;
  }
  public float getHeight(){
	  return sHeight;
  }
  public float getWidth(){
	  return sWidth;
  }
  public Date getStartDate(){
	  Calendar c = Calendar.getInstance();
	  c.set(Calendar.YEAR, 2011);
	  String[] l = StartDate.split("-");
	  int month = Integer.parseInt(l[1]);
	  int day = Integer.parseInt(l[2]);
	  c.set(Calendar.YEAR, 2011);
	  c.set(Calendar.MONTH, month);
	  c.set(Calendar.DAY_OF_MONTH, day);
	  c.set(Calendar.HOUR_OF_DAY, 0);
	  c.set(Calendar.MINUTE, 0);
	  c.set(Calendar.SECOND, 0);	  
	  return c.getTime();
  }
  public Date getEndDate(){
	  Calendar c = Calendar.getInstance();
	  c.set(Calendar.YEAR, 2011);
	  String[] l = EndDate.split("-");
	  int month = Integer.parseInt(l[1]);
	  int day = Integer.parseInt(l[2]);
	  c.set(Calendar.YEAR, 2011);
	  c.set(Calendar.MONTH, month);
	  c.set(Calendar.DAY_OF_MONTH, day);
	  c.set(Calendar.HOUR_OF_DAY, 0);
	  c.set(Calendar.MINUTE, 0);
	  c.set(Calendar.SECOND, 0);	  
	  return c.getTime();
  }
  public int getStartDayIndex(){
	  return startDay;
  }
  public int getEndDayIndex(){
	  return endDay;
  }
}