
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.Timer;


import omicronAPI.OmicronAPI;

import processing.core.*;
import processing.net.*;
  int canvasWidth = 1360; // * 6 = 8160
  int canvasHeight = 384; // * 6 = 2304
  public int scaleFactor = 1;

  TouchListener listener = null;
  OmicronAPI omicronManager = null;
  InnerSketch innerSketch;
  public PFont font = null;
  public boolean isOnWall;


  public void init() {
      super.init();
      // Creates the OmicronAPI object. This is placed in init() since we want to use fullscreen
      isOnWall = false;

      omicronManager = new OmicronAPI(this);      
      
      // Removes the title bar for full screen mode (present mode will not work on Cyber-commons wall)
      // will not work unless converted to pde
      if(isOnWall){
        omicronManager.setFullscreen(true);  
      }
     
  }

  
  public void setup() {
    // if on the wall set scale to 6
    if(!isOnWall){
      scaleFactor = 1;
    }
    else{
      scaleFactor = 6;
    }
    listener = new TouchListener(this);  
    
    canvasWidth = 1360 * scaleFactor;
    canvasHeight = 384 * scaleFactor;
        
    if(!isOnWall){
      size(canvasWidth, canvasHeight, JAVA2D);
      omicronManager.setTouchListener(listener);
    }
    else{
      noSmooth();
      size(canvasWidth, canvasHeight, P3D); // P3D doesn't work java applets
      omicronManager.setTouchListener(listener);
      omicronManager.ConnectToTracker(7001, 7340, "131.193.77.159");
    }        
    


    background(130);          
    font = createFont("Arial",8*((int)(1.2*scaleFactor)),true);
    textFont(font);      
  
    innerSketch = new InnerSketch(this,isOnWall,scaleFactor);
    innerSketch.Setup();
  }

  public void draw() {
    innerSketch.Draw();
    // For event and fullscreen processing, this must be called in draw()
    omicronManager.process();
  }

  public void bkThread(){
    
    Calendar cal = Calendar.getInstance();      
    cal.setTime(innerSketch.floatingDate);
    cal.set(Calendar.MINUTE, 0);
    Date fromGen = cal.getTime();
    cal.add(Calendar.DAY_OF_MONTH, 1);
    cal.set(Calendar.MINUTE, 0);
    Date toGen = cal.getTime();
  
    //System.out.println("next set from "+fromGen.toString()+"\t to "+toGen.toString()+" size: ");
    
  

    innerSketch.riotTweets.addAll(innerSketch.dataManager.getRiotTweets(fromGen, toGen, scaleFactor, innerSketch.pointRadius,innerSketch.riotImg));
    innerSketch.carTweets.addAll(innerSketch.dataManager.getCarTweets(fromGen, toGen, scaleFactor, innerSketch.pointRadius,innerSketch.carImg));
    innerSketch.truckTweets.addAll(innerSketch.dataManager.getTruckTweets(fromGen, toGen, scaleFactor, innerSketch.pointRadius,innerSketch.truckImg));
    innerSketch.planeTweets.addAll(innerSketch.dataManager.getPlaneTweets(fromGen, toGen, scaleFactor, innerSketch.pointRadius,innerSketch.planeImg));
    innerSketch.bombTweets.addAll(innerSketch.dataManager.getBombTweets(fromGen, toGen, scaleFactor, innerSketch.pointRadius,innerSketch.bombImg));
    innerSketch.heatTweets.addAll(innerSketch.dataManager.getHeatTweets(fromGen, toGen, scaleFactor, innerSketch.pointRadius,innerSketch.heatImg));
    innerSketch.noEventTweets.addAll(innerSketch.dataManager.getOtherTweets(fromGen, toGen, scaleFactor, innerSketch.pointRadius,innerSketch.bleep));      

    
    innerSketch.fluCount += innerSketch.dataManager.getFluCount(fromGen, toGen);
    innerSketch.stomachCount += innerSketch.dataManager.getStomachCount(fromGen, toGen);
    innerSketch.generalCount += innerSketch.dataManager.getGeneralCount(fromGen, toGen);    
    float percentFlu = (float)innerSketch.fluCount/(float)(innerSketch.fluCount+innerSketch.stomachCount+innerSketch.generalCount);
    float percentStomach = (float)innerSketch.stomachCount/(float)(innerSketch.fluCount+innerSketch.stomachCount+innerSketch.generalCount);;
    float percentOther = 1-(percentFlu+percentStomach);
    innerSketch.pieChart.removeCatagory("Flu Sickness");
    innerSketch.pieChart.removeCatagory("Stomach Sickness");
    innerSketch.pieChart.removeCatagory("No Sickness");
    innerSketch.pieChart.insertCatagory("Flu Sickness", percentFlu, color(TweetType.FluProblem.red(),TweetType.FluProblem.green(),TweetType.FluProblem.blue()));
    innerSketch.pieChart.insertCatagory("Stomach Sickness", percentStomach, color(TweetType.StomachProblem.red(),TweetType.StomachProblem.green(),TweetType.StomachProblem.blue()));
    innerSketch.pieChart.insertCatagory("No Sickness", percentOther, color(TweetType.AllTweet.red(),TweetType.AllTweet.green(),TweetType.AllTweet.blue()));    
  }

  public void fluThread(){
    Calendar cal = Calendar.getInstance();      
    cal.setTime(innerSketch.floatingDate);
    cal.set(Calendar.MINUTE, 0);
    Date fromGen = cal.getTime();
    cal.add(Calendar.HOUR_OF_DAY, 2);
    cal.set(Calendar.MINUTE, 59);
    Date toGen = cal.getTime();
  
    System.out.println("getFlu from "+fromGen.toString()+"\t to "+toGen.toString()+" ");
    
    innerSketch.fluTweets.addAll(innerSketch.dataManager.getFluTweets(fromGen, toGen, scaleFactor, innerSketch.pointRadius,innerSketch.bleep));
  
  }
  public void stoThread(){
    Calendar cal = Calendar.getInstance();      
    cal.setTime(innerSketch.floatingDate);
    cal.set(Calendar.MINUTE, 0);
    Date fromGen = cal.getTime();
    cal.add(Calendar.HOUR_OF_DAY, 2);
    cal.set(Calendar.MINUTE, 59);
    Date toGen = cal.getTime();  
    //System.out.println("getSto from "+fromGen.toString()+"\t to "+toGen.toString()+" size: ");
    innerSketch.stomachTweets.addAll(innerSketch.dataManager.getStomachTweets(fromGen, toGen, scaleFactor, innerSketch.pointRadius,innerSketch.bleep));
  }
  
  public void changeSlider(){
    innerSketch.CurrentDayCount = innerSketch.slider.getStartDayIndex();
    innerSketch.startDate = innerSketch.slider.getStartDate();
    innerSketch.endDate = innerSketch.slider.getEndDate();
    innerSketch.floatingDate = innerSketch.startDate;
    
    Calendar c = Calendar.getInstance();
    c.setTime(innerSketch.startDate);
    innerSketch.lastDay = c.get(Calendar.DAY_OF_MONTH); 
    Date fromGeneral = c.getTime();
    c.add(Calendar.DAY_OF_MONTH, 1);
    c.set(Calendar.HOUR_OF_DAY, 23);
    c.set(Calendar.MINUTE, 59);
    c.set(Calendar.SECOND, 59);    
    Date toGeneral = c.getTime();
          
    innerSketch.fluTweets.clear();
    innerSketch.stomachTweets.clear();    
    innerSketch.riotTweets.clear();
    innerSketch.carTweets.clear();;
    innerSketch.truckTweets.clear();;
    innerSketch.planeTweets.clear();
    innerSketch.bombTweets.clear();
    innerSketch.heatTweets.clear();
    innerSketch.noEventTweets.clear();
    innerSketch.fluTweets.addAll(innerSketch.dataManager.getFluTweets(fromGeneral, toGeneral, scaleFactor, innerSketch.pointRadius,innerSketch.bleep));
    innerSketch.stomachTweets.addAll(innerSketch.dataManager.getStomachTweets(fromGeneral, toGeneral, scaleFactor, innerSketch.pointRadius,innerSketch.bleep));    
    innerSketch.riotTweets.addAll(innerSketch.dataManager.getRiotTweets(fromGeneral, toGeneral, scaleFactor, innerSketch.pointRadius,innerSketch.riotImg));
    innerSketch.carTweets.addAll(innerSketch.dataManager.getCarTweets(fromGeneral, toGeneral, scaleFactor, innerSketch.pointRadius,innerSketch.carImg));
    innerSketch.truckTweets.addAll(innerSketch.dataManager.getTruckTweets(fromGeneral, toGeneral, scaleFactor, innerSketch.pointRadius,innerSketch.truckImg));
    innerSketch.planeTweets.addAll(innerSketch.dataManager.getPlaneTweets(fromGeneral, toGeneral, scaleFactor, innerSketch.pointRadius,innerSketch.planeImg));
    innerSketch.bombTweets.addAll(innerSketch.dataManager.getBombTweets(fromGeneral, toGeneral, scaleFactor, innerSketch.pointRadius,innerSketch.bombImg));
    innerSketch.heatTweets.addAll(innerSketch.dataManager.getHeatTweets(fromGeneral, toGeneral, scaleFactor, innerSketch.pointRadius,innerSketch.heatImg));
    innerSketch.noEventTweets.addAll(innerSketch.dataManager.getOtherTweets(fromGeneral, toGeneral, scaleFactor, innerSketch.pointRadius,innerSketch.bleep));
    innerSketch.fluCount = innerSketch.stomachCount = innerSketch.generalCount = 0;
    innerSketch.fluCount += innerSketch.dataManager.getFluCount(fromGeneral, toGeneral);
    innerSketch.stomachCount += innerSketch.dataManager.getStomachCount(fromGeneral, toGeneral);
    innerSketch.generalCount += innerSketch.dataManager.getGeneralCount(fromGeneral, toGeneral);    
    float percentFlu = (float)innerSketch.fluCount/(float)(innerSketch.fluCount+innerSketch.stomachCount+innerSketch.generalCount);
    float percentStomach = (float)innerSketch.stomachCount/(float)(innerSketch.fluCount+innerSketch.stomachCount+innerSketch.generalCount);;
    float percentOther = 1-(percentFlu+percentStomach);
    innerSketch.pieChart.removeCatagory("Flu Sickness");
    innerSketch.pieChart.removeCatagory("Stomach Sickness");
    innerSketch.pieChart.removeCatagory("No Sickness");
    innerSketch.pieChart.insertCatagory("Flu Sickness", percentFlu, color(TweetType.FluProblem.red(),TweetType.FluProblem.green(),TweetType.FluProblem.blue()));
    innerSketch.pieChart.insertCatagory("Stomach Sickness", percentStomach, color(TweetType.StomachProblem.red(),TweetType.StomachProblem.green(),TweetType.StomachProblem.blue()));
    innerSketch.pieChart.insertCatagory("No Sickness", percentOther, color(TweetType.AllTweet.red(),TweetType.AllTweet.green(),TweetType.AllTweet.blue()));
  }
  
  public void removeOldTweets(){
    Calendar cal = Calendar.getInstance();      
    cal.setTime(innerSketch.floatingDate);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.HOUR_OF_DAY, 0);
    Date fromGen = cal.getTime();    
    cal.add(Calendar.HOUR_OF_DAY, 2);
    cal.set(Calendar.MINUTE, 0);
    Date toGen = cal.getTime();  
  
    
  
    System.out.println("rmFluSto from "+fromGen.toString()+"\t to "+toGen.toString()+" size: "+innerSketch.fluTweets.size()+"\t"+innerSketch.stomachTweets.size());
    innerSketch.fluTweets.clear();
    innerSketch.stomachTweets.clear();
    
    innerSketch.fluTweets.addAll(innerSketch.dataManager.getFluTweets(fromGen, toGen, scaleFactor, innerSketch.pointRadius,innerSketch.bleep));
    innerSketch.stomachTweets.addAll(innerSketch.dataManager.getStomachTweets(fromGen, toGen, scaleFactor, innerSketch.pointRadius,innerSketch.bleep));
    System.out.println("addFluSto from "+fromGen.toString()+"\t to "+toGen.toString()+" size: "+innerSketch.fluTweets.size()+"\t"+innerSketch.stomachTweets.size());    
    
    cal.setTime(innerSketch.floatingDate);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.HOUR_OF_DAY, 0);
    fromGen = cal.getTime();
    cal.add(Calendar.DAY_OF_MONTH, 1);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.HOUR_OF_DAY, 0);
    toGen = cal.getTime();
    
    /*innerSketch.riotTweets.clear();
    innerSketch.carTweets.clear();;
    innerSketch.truckTweets.clear();;
    innerSketch.planeTweets.clear();
    innerSketch.bombTweets.clear();
    innerSketch.heatTweets.clear();
    innerSketch.noEventTweets.clear();
        
    innerSketch.riotTweets.addAll(innerSketch.dataManager.getRiotTweets(fromGen, toGen, scaleFactor, innerSketch.pointRadius,innerSketch.riotImg));
    innerSketch.carTweets.addAll(innerSketch.dataManager.getCarTweets(fromGen, toGen, scaleFactor, innerSketch.pointRadius,innerSketch.carImg));
    innerSketch.truckTweets.addAll(innerSketch.dataManager.getTruckTweets(fromGen, toGen, scaleFactor, innerSketch.pointRadius,innerSketch.truckImg));
    innerSketch.planeTweets.addAll(innerSketch.dataManager.getPlaneTweets(fromGen, toGen, scaleFactor, innerSketch.pointRadius,innerSketch.planeImg));
    innerSketch.bombTweets.addAll(innerSketch.dataManager.getBombTweets(fromGen, toGen, scaleFactor, innerSketch.pointRadius,innerSketch.bombImg));
    innerSketch.heatTweets.addAll(innerSketch.dataManager.getHeatTweets(fromGen, toGen, scaleFactor, innerSketch.pointRadius,innerSketch.heatImg));
    innerSketch.noEventTweets.addAll(innerSketch.dataManager.getOtherTweets(fromGen, toGen, scaleFactor, innerSketch.pointRadius,innerSketch.bleep));*/
  }
  

  // NOTE: Mouse pressed, dragged, and released events will also trigger these
  // using an ID of -1 and an xWidth and yWidth value of 10.
  void touchDown(int ID, float xPos, float yPos, float xWidth, float yWidth) {    
    innerSketch.touchDown(ID, xPos, yPos, xWidth, yWidth);
    
  }// touchDown

  void touchMove(int ID, float xPos, float yPos, float xWidth, float yWidth) {
    innerSketch.touchMove(ID, xPos, yPos, xWidth, yWidth);

  }// touchMove

  void touchUp(int ID, float xPos, float yPos, float xWidth, float yWidth) {
    innerSketch.touchUp(ID, xPos, yPos, xWidth, yWidth);
    
  }// touchUp
