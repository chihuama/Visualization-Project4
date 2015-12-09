import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;


import omicronAPI.OmicronAPI;
import processing.core.PApplet;
import processing.core.PImage;


public class InnerSketch {
	PApplet parent;
	public int scaleFactor = 1;
	boolean isOnWall = false;
	boolean Play = false;
	boolean showWind = false;
	boolean loopPlay = false;
	int minuteInterval = 1;
	private float percentHigh = 0;
	int canvasWidth = 1360; // * 6 = 8160
	int canvasHeight = 384; // * 6 = 2304
	int fluCount,stomachCount,generalCount = 0;
	
	public TouchMapImage map = null;
	public PImage bleep; 
	public PImage bombImg;
	public PImage heatImg;
	public PImage carImg;
	public PImage truckImg;
	public PImage planeImg;
	public PImage riotImg;
	
	public DataManager dataManager;
	public CopyOnWriteArrayList<Tweet> riotTweets;
	public CopyOnWriteArrayList<Tweet> carTweets;
	public CopyOnWriteArrayList<Tweet> truckTweets;
	public CopyOnWriteArrayList<Tweet> planeTweets;
	public CopyOnWriteArrayList<Tweet> bombTweets;
	public CopyOnWriteArrayList<Tweet> heatTweets;
	public CopyOnWriteArrayList<Tweet> noEventTweets;
	public CopyOnWriteArrayList<Tweet> stomachTweets;
	public CopyOnWriteArrayList<Tweet> fluTweets;
	
	int CurrentDayCount = 0;
	public CopyOnWriteArrayList<WeatherUnit> weather;
	public float pointRadius = 6;
	public Date startDate;
	public Date endDate;
	public Date floatingDate;
	
	public TimeSlider slider; 
	
	private PImage compass;
	private PImage weatherImg;
	
	
	
	public int currentWId = 1;


	private TouchButton ZoomInButton;
	private TouchButton ZoomOutButton;
	private TouchButton playButton;
	private TouchButton resetButton;
	private TouchButton fasterButton;
	private TouchButton slowerButton;
	WordClouds cloud;
	PieChart pieChart;
	private boolean displayStomach,displayFlu,displayRiot,displayCar,displayTruck,displayPlane,displayBomb,displayHeat,displayNoEvent = false;

		
	
	private TouchCheckBox stomachTweetsCB = null;
	private TouchCheckBox fluTweetsCB = null;
	private TouchCheckBox riotCB = null;
	private TouchCheckBox carCrashCB = null;
	private TouchCheckBox truckCrashCB = null;
	private TouchCheckBox planeCrashCB = null;
	private TouchCheckBox bombCB = null;
	private TouchCheckBox heatAdvCB = null;
	private TouchCheckBox noEventCB = null;
	
	private TouchCheckBox loopCB = null;
	private TouchCheckBox windCB = null;	
	
		


	float menuX,menuY,menuWidth,menuHeight;
	
	int mapZoomLevel = 1;


	public InnerSketch(P4Sketch p4Sketch, boolean isOnWall, int scaleFactor2) {
		parent = p4Sketch;
		this.isOnWall = isOnWall;
		this.scaleFactor = scaleFactor2;
		pointRadius = pointRadius*scaleFactor*(1f+(.5f*mapZoomLevel));
//		
		stomachTweets = new CopyOnWriteArrayList<Tweet>();
		fluTweets = new CopyOnWriteArrayList<Tweet>();
		riotTweets = new CopyOnWriteArrayList<Tweet>();
		carTweets = new CopyOnWriteArrayList<Tweet>();
		truckTweets = new CopyOnWriteArrayList<Tweet>();
		planeTweets = new CopyOnWriteArrayList<Tweet>();
		bombTweets = new CopyOnWriteArrayList<Tweet>();
		heatTweets = new CopyOnWriteArrayList<Tweet>();
		noEventTweets = new CopyOnWriteArrayList<Tweet>();

		weather = new CopyOnWriteArrayList<WeatherUnit>();
	}
	public void Init(){
		
	}
	public void Setup(){
		  
		  //map = new TouchMap(parent, scaleFactor);
		map = new TouchMapImage(parent, scaleFactor);
		    
		float itemHeight = 17*scaleFactor;
		float itemWidth = 44*scaleFactor;

		
		float largBtnWidth = 64*scaleFactor;
		
		dataManager = new DataManager(parent,isOnWall);
		startDate = new Date();
		endDate = new Date();
		floatingDate = new Date();
		ZoomOutButton= new TouchButton(parent,"Zoom Out",itemWidth, itemHeight, parent.width-itemWidth,  parent.height-(2*itemHeight));
		playButton = new TouchButton(parent,"Play",itemWidth, itemHeight, parent.width-(2*itemWidth),  parent.height-(2*itemHeight));
		resetButton = new TouchButton(parent,"Reset",itemWidth, itemHeight, parent.width-(3*itemWidth),  parent.height-(2*itemHeight));
		
		fasterButton = new TouchButton(parent,"Faster",itemWidth, itemHeight, parent.width-(2*itemWidth),  parent.height-(1*itemHeight));
		slowerButton = new TouchButton(parent,"Slower",itemWidth, itemHeight, parent.width-(3*itemWidth),  parent.height-(1*itemHeight));
		
		loopCB = new TouchCheckBox(parent, "Loop", itemWidth, itemHeight, parent.width-(2*itemWidth), parent.height-(3*itemHeight));
		windCB = new TouchCheckBox(parent, "Overlay", itemWidth, itemHeight, parent.width-(3*itemWidth), parent.height-(3*itemHeight));
		ZoomInButton = new TouchButton(parent,"Zoom In",itemWidth, itemHeight, parent.width-itemWidth,  parent.height-(3*itemHeight));
		
		float sliderWidth = 5*largBtnWidth;
		pieChart = new PieChart(parent, scaleFactor, 0.75f*parent.width, parent.width, 0, 50*scaleFactor);
		cloud = new WordClouds(parent,scaleFactor,(int)(parent.width-sliderWidth),50*scaleFactor,(int)sliderWidth);
		cloud.Setup();
		
		compass = parent.loadImage("Compass_Rose_English_North_svg.png");
		compass.resize((int)(compass.width*.4f), (int)(compass.height*.4f));
		compass.resize(compass.width*scaleFactor, compass.height*scaleFactor);
		
		
		
		
		riotCB = new TouchCheckBox(parent, "Riot/Looting", largBtnWidth, itemHeight, parent.width-(3*itemWidth)-(3*largBtnWidth), parent.height-(2*itemHeight));
		carCrashCB = new TouchCheckBox(parent, "Car Crash", largBtnWidth, itemHeight, parent.width-(3*itemWidth)-(4*largBtnWidth), parent.height-(2*itemHeight));;
		truckCrashCB = new TouchCheckBox(parent, "Truck Crash", largBtnWidth, itemHeight, parent.width-(3*itemWidth)-(4*largBtnWidth), parent.height-(1*itemHeight));
		planeCrashCB = new TouchCheckBox(parent, "Plane Crash", largBtnWidth, itemHeight, parent.width-(3*itemWidth)-(5*largBtnWidth), parent.height-(2*itemHeight));
		bombCB = new TouchCheckBox(parent, "Bomb Rumors", largBtnWidth, itemHeight, parent.width-(3*itemWidth)-(3*largBtnWidth), parent.height-(1*itemHeight));
		heatAdvCB = new TouchCheckBox(parent, "Heat Advisory", largBtnWidth, itemHeight, parent.width-(3*itemWidth)-(5*largBtnWidth), parent.height-(1*itemHeight));
		noEventCB = new TouchCheckBox(parent, "No Event", largBtnWidth, itemHeight, parent.width-(3*itemWidth)-(6*largBtnWidth), parent.height-(1*itemHeight));		
		
		slider = new TimeSlider(parent,scaleFactor, (int)(parent.width-sliderWidth-(3*itemWidth)),(int)(parent.height-(3*itemHeight)+(.5f*itemHeight)),(int)sliderWidth);
		
				
		stomachTweetsCB = new TouchCheckBox(parent, "Stomach Tweets", largBtnWidth, itemHeight, parent.width-(3*itemWidth)-(1*largBtnWidth), parent.height-(2*itemHeight));
		fluTweetsCB = new TouchCheckBox(parent, "Flu Tweets", largBtnWidth, itemHeight, parent.width-(3*itemWidth)-(1*largBtnWidth), parent.height-(1*itemHeight));
	
		weather.addAll(dataManager.getWeather());
		weatherImg = parent.loadImage(weather.get(0).getWeatherFile());
		weatherImg.resize((int)(weatherImg.width*.8f), (int)(weatherImg.height*.8f));
		weatherImg.resize(weatherImg.width*scaleFactor, weatherImg.height*scaleFactor);
		 
		bombImg = parent.loadImage("bomb.png");
		heatImg = parent.loadImage("heatAdv.png");
		carImg = parent.loadImage("carCrash.png");
		truckImg = parent.loadImage("truckCrash.png");
		planeImg = parent.loadImage("planeCrash.png");
		riotImg = parent.loadImage("looting.png");
		bleep = parent.loadImage("tweet2.png");
		bombImg.resize((int)(pointRadius*2), (int)(pointRadius*2));
		heatImg.resize((int)(pointRadius*2), (int)(pointRadius*2));
		truckImg.resize((int)(pointRadius*2), (int)(pointRadius*2));
		planeImg.resize((int)(pointRadius*2), (int)(pointRadius*2));
		riotImg.resize((int)(pointRadius*2), (int)(pointRadius*2));
		carImg.resize((int)(pointRadius*2), (int)(pointRadius*2));	
		bleep.resize((int)(pointRadius*2), (int)(pointRadius*2));
		lastSliderDate = new Date();
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.DAY_OF_MONTH, 30);
		c.set(Calendar.MONTH, 4);
		c.set(Calendar.YEAR, 2011);
		Date from = c.getTime();
		startDate = c.getTime();
		floatingDate = c.getTime();
		Date fromGeneral = c.getTime();
		c.add(Calendar.DAY_OF_MONTH, 2);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);		
		Date toGeneral = c.getTime();
		
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.DAY_OF_MONTH, 21);
		c.set(Calendar.MONTH, 5);
		c.set(Calendar.YEAR, 2011);
		endDate = c.getTime(); 
		

		fluTweets.addAll(dataManager.getFluTweets(fromGeneral, toGeneral, scaleFactor, pointRadius,bleep));
		stomachTweets.addAll(dataManager.getStomachTweets(fromGeneral, toGeneral, scaleFactor, pointRadius,bleep));		
		riotTweets.addAll(dataManager.getRiotTweets(fromGeneral, toGeneral, scaleFactor, pointRadius,riotImg));
		carTweets.addAll(dataManager.getCarTweets(fromGeneral, toGeneral, scaleFactor, pointRadius,carImg));
		truckTweets.addAll(dataManager.getTruckTweets(fromGeneral, toGeneral, scaleFactor, pointRadius,truckImg));
		planeTweets.addAll(dataManager.getPlaneTweets(fromGeneral, toGeneral, scaleFactor, pointRadius,planeImg));
		bombTweets.addAll(dataManager.getBombTweets(fromGeneral, toGeneral, scaleFactor, pointRadius,bombImg));
		heatTweets.addAll(dataManager.getHeatTweets(fromGeneral, toGeneral, scaleFactor, pointRadius,heatImg));
		noEventTweets.addAll(dataManager.getOtherTweets(fromGeneral, toGeneral, scaleFactor, pointRadius,bleep));
		fluCount = dataManager.getFluCount(fromGeneral, toGeneral);
		stomachCount = dataManager.getStomachCount(fromGeneral, toGeneral);
		generalCount = dataManager.getGeneralCount(fromGeneral, toGeneral);		
		float percentFlu = (float)fluCount/(float)(fluCount+stomachCount+generalCount);
		float percentStomach = (float)stomachCount/(float)(fluCount+stomachCount+generalCount);;
		float percentOther = 1-(percentFlu+percentStomach);
		pieChart.insertCatagory("Flu Sickness", percentFlu, parent.color(TweetType.FluProblem.red(),TweetType.FluProblem.green(),TweetType.FluProblem.blue()));
		pieChart.insertCatagory("Stomach Sickness", percentStomach, parent.color(TweetType.StomachProblem.red(),TweetType.StomachProblem.green(),TweetType.StomachProblem.blue()));
		pieChart.insertCatagory("No Sickness", percentOther, parent.color(TweetType.AllTweet.red(),TweetType.AllTweet.green(),TweetType.AllTweet.blue()));
		//touch show wind
		touchHandler(parent.width-(3*itemWidth), parent.height-(3*itemHeight));
		map.touchDown(0, map.borderx1+4, map.bordery1+4, 1, 1);
		map.touchMove(0, map.borderx1+4, map.bordery1+4, 1, 1);
		map.touchUp(0, map.borderx1+4, map.bordery1+4, 1, 1);
		CurrentDayCount = 0;

	}
	public void Draw(){
		parent.background(170);
		this.mapZoomLevel = map.getZoomLevel();
		//map.draw();
		map.draw();
		
		if(Play){
			parent.frameRate = 15;	
			incrementTime();
		}
		else{
			parent.frameRate = 60;
		}
		
		drawTweets();
				
		drawWind();
		drawDateTime();
		if(this.showWind){
			cloud.Draw(CurrentDayCount);
		}
		
		playButton.draw();
		resetButton.draw();
		ZoomInButton.draw();
		ZoomOutButton.draw();
		loopCB.draw();
		windCB.draw();
		fasterButton.draw();
		slowerButton.draw();
		
		float itemHeight = 17*scaleFactor;
		float itemWidth = 44*scaleFactor;
		float largBtnWidth = 64*scaleFactor;		
		parent.fill(TweetType.AllTweet.red(),TweetType.AllTweet.green(),TweetType.AllTweet.blue());
		parent.rect(parent.width-(3*itemWidth)-(6*largBtnWidth), parent.height-(2*itemHeight), parent.width-(3*itemWidth)-(2*largBtnWidth), parent.height,15);
		parent.fill(0);

		riotCB.draw();
		carCrashCB.draw();
		truckCrashCB.draw();
		planeCrashCB.draw();
		bombCB.draw();
		heatAdvCB.draw();
		noEventCB.draw();		

		slider.Draw();
		if(this.showWind){
			pieChart.draw();
			
			float sgXPos1 = parent.map(0, 0, 3, 0.75f*parent.width+5, parent.width);
			float sgXPos2 = parent.map(1, 0, 3, 0.75f*parent.width+5, parent.width);
			float sgXPos3 = parent.map(2, 0, 3, 0.75f*parent.width+5, parent.width);
			parent.textSize(10*scaleFactor);
			parent.fill(255);
			
		     parent.textSize(14*scaleFactor);
             parent.text(""+fluCount, parent.width-0.30f*parent.width, (6*scaleFactor)+0.45f*parent.height);
             parent.text(""+stomachCount, parent.width-0.30f*parent.width, (8*scaleFactor)+0.58f*parent.height);
             parent.text(""+generalCount, parent.width-0.30f*parent.width, (12*scaleFactor)+0.70f*parent.height);
			//parent.text(""+fluCount, sgXPos1, 50*scaleFactor);
			//parent.text(""+stomachCount, sgXPos2, 50*scaleFactor);
			//parent.text(""+generalCount, sgXPos3, 50*scaleFactor);
			parent.textSize(8*scaleFactor);
			parent.fill(0);
		}		
		
		
		this.stomachTweetsCB.draw();
		this.fluTweetsCB.draw();
	
		parent.fill(TweetType.FluProblem.red(),TweetType.FluProblem.green(),TweetType.FluProblem.blue(),90);
		parent.rect(parent.width-(3*itemWidth)-(1*largBtnWidth), parent.height-(1*itemHeight), parent.width-(3*itemWidth), parent.height,15);
		parent.fill(TweetType.StomachProblem.red(),TweetType.StomachProblem.green(),TweetType.StomachProblem.blue(),90);
		parent.rect(parent.width-(3*itemWidth)-(1*largBtnWidth), parent.height-(2*itemHeight), parent.width-(3*itemWidth), parent.height-(1*itemHeight),15);		
		parent.fill(0);
		
	}
	private void drawTweets() {
		
		if(displayNoEvent){
			if(!noEventTweets.isEmpty()){
				for(Tweet t : this.noEventTweets){
					ScreenLocation sc = map.getPosByGIS(t.lat, t.lon);
					t.setXYLocation(sc.xPos, sc.yPos,pointRadius);							
					if(t.isOnScreen(mapZoomLevel,pointRadius)){
						if((t.date_time.before(floatingDate))){										
							t.draw(sc.xPos, sc.yPos, mapZoomLevel,pointRadius);						
						}						
					}					
				}
			}			
		}
		
		if(this.displayPlane){
			if(!planeTweets.isEmpty()){
				for(Tweet t : this.planeTweets){
					ScreenLocation sc = map.getPosByGIS(t.lat, t.lon);
					t.setXYLocation(sc.xPos, sc.yPos,pointRadius);							
					if(t.isOnScreen(mapZoomLevel,pointRadius)){
						if((t.date_time.before(floatingDate))){										
							t.draw(sc.xPos, sc.yPos, mapZoomLevel,pointRadius);						
						}						
					}					
				}
			}			
		}
		
		if(this.displayCar){
			if(!carTweets.isEmpty()){
				for(Tweet t : this.carTweets){
					ScreenLocation sc = map.getPosByGIS(t.lat, t.lon);
					t.setXYLocation(sc.xPos, sc.yPos,pointRadius);							
					if(t.isOnScreen(mapZoomLevel,pointRadius)){
						if((t.date_time.before(floatingDate))){										
							t.draw(sc.xPos, sc.yPos, mapZoomLevel,pointRadius);						
						}						
					}					
				}
			}			
		}
		
		if(this.displayHeat){
			if(!heatTweets.isEmpty()){
				for(Tweet t : this.heatTweets){
					ScreenLocation sc = map.getPosByGIS(t.lat, t.lon);
					t.setXYLocation(sc.xPos, sc.yPos,pointRadius);							
					if(t.isOnScreen(mapZoomLevel,pointRadius)){
						if((t.date_time.before(floatingDate))){										
							t.draw(sc.xPos, sc.yPos, mapZoomLevel,pointRadius);						
						}						
					}					
				}
			}			
		}
		
		if(this.displayBomb){
			if(!bombTweets.isEmpty()){
				for(Tweet t : this.bombTweets){
					ScreenLocation sc = map.getPosByGIS(t.lat, t.lon);
					t.setXYLocation(sc.xPos, sc.yPos,pointRadius);							
					if(t.isOnScreen(mapZoomLevel,pointRadius)){
						if((t.date_time.before(floatingDate))){										
							t.draw(sc.xPos, sc.yPos, mapZoomLevel,pointRadius);						
						}						
					}					
				}
			}
		}
		
		if(this.displayRiot){
			if(!riotTweets.isEmpty()){
				for(Tweet t : this.riotTweets){
					ScreenLocation sc = map.getPosByGIS(t.lat, t.lon);
					t.setXYLocation(sc.xPos, sc.yPos,pointRadius);							
					if(t.isOnScreen(mapZoomLevel,pointRadius)){
						if((t.date_time.before(floatingDate))){										
							t.draw(sc.xPos, sc.yPos, mapZoomLevel,pointRadius);						
						}						
					}					
				}
			}			
		}
		
		if(this.displayTruck){
			if(!truckTweets.isEmpty()){
				for(Tweet t : this.truckTweets){
					ScreenLocation sc = map.getPosByGIS(t.lat, t.lon);
					t.setXYLocation(sc.xPos, sc.yPos,pointRadius);							
					if(t.isOnScreen(mapZoomLevel,pointRadius)){
						if((t.date_time.before(floatingDate))){										
							t.draw(sc.xPos, sc.yPos, mapZoomLevel,pointRadius);						
						}						
					}					
				}
			}			
		}
				
		if(displayFlu){
			if(!fluTweets.isEmpty()){
				for(Tweet t : this.fluTweets){					
					ScreenLocation sc = map.getPosByGIS(t.lat, t.lon);
					t.setXYLocation(sc.xPos, sc.yPos,pointRadius);							
					if(t.isOnScreen(mapZoomLevel,pointRadius)){
						if((t.date_time.before(floatingDate))){										
							t.draw(sc.xPos, sc.yPos, mapZoomLevel,pointRadius);						
						}						
					}							
				}
			}			
		}		
		if(displayStomach){
			if(!stomachTweets.isEmpty()){
				for(Tweet t : this.stomachTweets){
					ScreenLocation sc = map.getPosByGIS(t.lat, t.lon);
					t.setXYLocation(sc.xPos, sc.yPos,pointRadius);							
					if(t.isOnScreen(mapZoomLevel,pointRadius)){
						if((t.date_time.before(floatingDate))){										
							t.draw(sc.xPos, sc.yPos, mapZoomLevel,pointRadius);						
						}						
					}	
				}
			}			
		}	
		
	
		
	}
	int lastHour = 0;
	int lastDay = 30;

	public void incrementTime(){
		if(floatingDate.before(endDate)){			
			Calendar cal = Calendar.getInstance();			
			cal.setTime(floatingDate);
			int fDay = cal.get(Calendar.DAY_OF_MONTH);
			int fMonth = cal.get(Calendar.MONTH);
			int currentHour = cal.get(Calendar.HOUR_OF_DAY);	
			int currentMin = this.minuteInterval;
					
			if(currentHour % 3 == 0  && currentHour != lastHour){				
				parent.thread("fluThread");
				parent.thread("stoThread");
			}

			if((lastDay == 5 || lastDay == 10 || lastDay == 15) && currentHour == 1 && currentHour != lastHour){

				parent.thread("removeOldTweets");
				
			}
			
			if(currentHour == 0 && currentHour != lastHour){

				parent.thread("bkThread");
				CurrentDayCount += 1;
			}
			
			cal.setTime(floatingDate);
			cal.add(Calendar.MINUTE, minuteInterval);			
			floatingDate = cal.getTime();			
			lastHour = currentHour;	
			lastDay = fDay;
		}
		else{
			if(loopPlay){
				Play = true;
				floatingDate = startDate;
				Calendar cmp = Calendar.getInstance();
				cmp.set(Calendar.MINUTE, 0);
				cmp.set(Calendar.HOUR_OF_DAY, 0);
				cmp.set(Calendar.SECOND, 0);
				cmp.set(Calendar.DAY_OF_MONTH, 30);
				cmp.set(Calendar.MONTH, 4);
				cmp.set(Calendar.YEAR, 2011);
				Calendar sd = Calendar.getInstance();
				sd.setTime(startDate);
				sd.set(Calendar.MINUTE, 0);
				sd.set(Calendar.HOUR_OF_DAY, 0);
				sd.set(Calendar.SECOND, 0);
				int count = 0;
				while(!cmp.equals(sd)){
					count++;
					cmp.add(Calendar.DAY_OF_MONTH, 1);
				}
				CurrentDayCount = count;
			}
			else{
				Play = false;
			}
			
		}	
	}
	


	
	

	public void drawDateTime(){
		if(showWind){
			parent.fill(0);
			parent.textSize(14*scaleFactor);
			Calendar tmp = Calendar.getInstance();
			tmp.setTime(floatingDate);
			String am_pm = "am";
			switch(tmp.get(Calendar.AM_PM)){
			case Calendar.AM:
				am_pm = "am";
				break;
			case Calendar.PM:
				am_pm = "pm";
				break;
			}
			int month = tmp.get(Calendar.MONTH);
			String min = "";
			if(tmp.get(Calendar.MINUTE) < 10){
				min = "0"+tmp.get(Calendar.MINUTE);
			}
			else{
				min = ""+tmp.get(Calendar.MINUTE);
			}
			
			int hour = 0;
			if(tmp.get(Calendar.HOUR) == 0){
				hour = 12;
			}
			else{
				hour = tmp.get(Calendar.HOUR);
			}
			
			parent.text(month+"/"+tmp.get(Calendar.DAY_OF_MONTH)+" "+hour+":"+min+" "+am_pm+" ",5,compass.height+45*scaleFactor); // only have data for 8, 9
			
			parent.textSize(8*((int)(1.2*scaleFactor)));
		}
	}

	public void drawWind(){
		if(showWind){
			parent.fill(190,235);
			parent.noStroke();
			parent.rect(0, 0, (weatherImg.width)+10*scaleFactor,compass.height+35*scaleFactor+((weatherImg.height)+10));
			parent.fill(0);
			parent.textSize(14*scaleFactor);
			parent.text("Wind Direction", 5*scaleFactor, 12*scaleFactor);
			parent.textSize(8*scaleFactor);
			
			parent.strokeWeight(1*scaleFactor);
			parent.stroke(0);
			parent.fill(0);
			parent.imageMode(parent.CENTER);
			parent.image(compass, ((weatherImg.width)+10*scaleFactor)/2f, 15+compass.height/2f);
			parent.imageMode(parent.CORNER);
			Calendar tmp = Calendar.getInstance();
			tmp.setTime(floatingDate);
			int day = tmp.get(Calendar.DAY_OF_MONTH);
			float centerX = ((weatherImg.width)+10*scaleFactor)/2f;
			float centerY = 15+compass.height/2f;
			for(WeatherUnit u : weather){
				tmp.setTime(u.date);
				if(day ==  tmp.get(Calendar.DAY_OF_MONTH)){
					if(u.getWeatherId() != this.currentWId){
						weatherImg = parent.loadImage(u.getWeatherFile());
						weatherImg.resize((int)(weatherImg.width*.8f), (int)(weatherImg.height*.8f));
						weatherImg.resize(weatherImg.width*scaleFactor, weatherImg.height*scaleFactor);
						currentWId = u.getWeatherId();
					}
					parent.image(weatherImg, 0, compass.height+45*scaleFactor);
					
					parent.textAlign(parent.CENTER);
					parent.fill(0);
					parent.textSize(14*scaleFactor);
					parent.text(u.weatherDesc, weatherImg.width/2f, compass.height+35*scaleFactor+weatherImg.height/2f+5);
					parent.textSize(8*scaleFactor);
					parent.textAlign(parent.LEFT);
					
					parent.strokeWeight(4*scaleFactor);			
					float dist = parent.map(u.windSpeed, 0, 14, 0, (compass.width*.40f));
					
					parent.textSize(14*scaleFactor);		
					parent.textAlign(parent.CENTER);
					parent.text(u.windSpeed+"mph", weatherImg.width/2f,compass.height+30*scaleFactor);
					parent.textAlign(parent.LEFT);
					parent.textSize(8*scaleFactor);
					
					parent.pushMatrix();
					
					parent.translate(centerX, centerY);					
					parent.rotate(u.getWindAngle());
					parent.stroke(255);
					parent.line(0, 0, dist, 0);
					parent.stroke(0);
					parent.fill(0);

					parent.popMatrix();
										
					parent.fill(0);
					parent.strokeWeight(1*scaleFactor);					
				}
				
			}
		}
	}
	
	public void touchDown(int ID, float xPos, float yPos, float xWidth, float yWidth){
		
		boolean isSliderTouch = false;
		float sborderx1 = slider.getOriginX();
		float sborderx2 = slider.getOriginX()+slider.getWidth();
		float sbordery1 = slider.getOriginY();
		float sbordery2 = slider.getOriginY()+slider.getHeight();
		if(sborderx1 <= xPos && sborderx2 >= xPos && sbordery1 <= yPos && sbordery2 >= yPos && !Play){
			isSliderTouch = true;
			wasSliderTouched = true;
		}
		
		if(!isSliderTouch){
			map.touchDown(ID, xPos, yPos, xWidth, yWidth);
		}
		else{
			slider.touchDown(ID, xPos, yPos, xWidth, yWidth);
		}
		touchHandler(xPos,yPos);

	}
	boolean wasSliderTouched = false;
	public void touchMove(int ID, float xPos, float yPos, float xWidth, float yWidth) {
		boolean isSliderTouch = false;
		float sborderx1 = slider.getOriginX();
		float sborderx2 = slider.getOriginX()+slider.getWidth();
		float sbordery1 = slider.getOriginY();
		float sbordery2 = slider.getOriginY()+slider.getHeight();
		if(sborderx1 <= xPos && sborderx2 >= xPos && sbordery1 <= yPos && sbordery2 >= yPos && !Play){
			isSliderTouch = true;
		}
		if(!isSliderTouch){
			map.touchMove(ID, xPos, yPos, xWidth, yWidth);
		}
		else{
			slider.touchMove(ID, xPos, yPos, xWidth, yWidth);
		}
		
	}
	Date lastSliderDate;
	void touchUp(int ID, float xPos, float yPos, float xWidth, float yWidth) {		
		map.touchUp(ID, xPos, yPos, xWidth, yWidth);
		
		//map.touchUp(ID, xPos, yPos, xWidth, yWidth);
		if(wasSliderTouched && !Play){
			wasSliderTouched = false;
			slider.touchUp(ID, xPos, yPos, xWidth, yWidth);

			parent.thread("changeSlider");
			
			
			
		}
	}
	


	
	
	
	
	
	void touchHandler(float xPos, float yPos){	
		if(ZoomInButton.Clicked(xPos, yPos)){
			
			map.zoomIn();
			mapZoomLevel = map.getZoomLevel();
			if(mapZoomLevel < 6){
				pointRadius = pointRadius*(1.25f);
			}
			else{
				pointRadius = pointRadius*(.9f);
			}
			System.out.println("radius = "+pointRadius);
			adjustTweets();
		}
		if(ZoomOutButton.Clicked(xPos, yPos)){
			map.zoomOut();
			mapZoomLevel = map.getZoomLevel();
			if(mapZoomLevel < 6){
				pointRadius = pointRadius*(.9f);
				if(mapZoomLevel == 1){
					pointRadius = 6*scaleFactor*(1f+(.5f*mapZoomLevel));
				}
			}
			else{
				pointRadius = pointRadius*(1.25f);
			}
			
			System.out.println("radius = "+pointRadius);
			adjustTweets();
		}
		
		
		if(this.riotCB.Clicked(xPos, yPos)){
			this.displayRiot = !displayRiot;
			System.out.println("display riot Tweets: "+displayRiot);
		}
		

		
		if(this.carCrashCB.Clicked(xPos, yPos)){
			this.displayCar = !displayCar;
			System.out.println("display car Tweets: "+displayCar);
		}
		
		
		if(this.truckCrashCB.Clicked(xPos, yPos)){
			this.displayTruck = !displayTruck;
			System.out.println("display truck Tweets: "+displayTruck);
		}
		
		if(this.planeCrashCB.Clicked(xPos, yPos)){
			this.displayPlane = !displayPlane;
			System.out.println("display plane Tweets: "+displayPlane);
		}
		
		if(this.bombCB.Clicked(xPos, yPos)){
			this.displayBomb = !displayBomb;
			System.out.println("display bomb Tweets: "+displayBomb);
		}
		
		if(this.heatAdvCB.Clicked(xPos, yPos)){
			this.displayHeat = !displayHeat;
			System.out.println("display heat Tweets: "+displayHeat);
		}
		
		if(this.noEventCB.Clicked(xPos, yPos)){
			this.displayNoEvent = !displayNoEvent;
			System.out.println("display no event Tweets: "+displayNoEvent);
		}
		


		
		
		
		
		
		if(this.fluTweetsCB.Clicked(xPos, yPos)){
			this.displayFlu = !displayFlu;
			System.out.println("clicked on flu Tweets");
		}
		if(this.stomachTweetsCB.Clicked(xPos, yPos)){
			this.displayStomach = !displayStomach;
			System.out.println("clicked on general Tweets");
		}
		
		if(fasterButton.Clicked(xPos, yPos)){
			if(minuteInterval+5 < 60){
				minuteInterval+=5;
				System.out.println("fps= "+parent.frameRate+"\tinterval= "+minuteInterval);
			}			
		}
		if(slowerButton.Clicked(xPos, yPos)){
			if(parent.frameRate > 1 && minuteInterval > 1){
				minuteInterval-=5;
				System.out.println("fps= "+parent.frameRate+"\tinterval= "+minuteInterval);
			}
		}
		
		//handle touches on clusters
		
		if(windCB.Clicked(xPos, yPos)){
			showWind = !showWind;
		}
	
		if(loopCB.Clicked(xPos, yPos)){
			loopPlay = !loopPlay;
		}
		
		
		if(playButton.Clicked(xPos, yPos)){
			Play = !Play;			
			System.out.println("play: "+Play);
		}
	
		if(resetButton.Clicked(xPos, yPos)){
			floatingDate = startDate;	
			CurrentDayCount = slider.getStartDayIndex();
			fluTweets.clear();
			this.startDate = slider.getStartDate();
			this.endDate = slider.getEndDate();
			this.floatingDate = startDate;
			
			Calendar c = Calendar.getInstance();
			c.setTime(startDate);
			lastDay = c.get(Calendar.DAY_OF_MONTH); 
			Date fromGeneral = c.getTime();
			c.add(Calendar.DAY_OF_MONTH, 1);
			c.set(Calendar.HOUR_OF_DAY, 23);
			c.set(Calendar.MINUTE, 59);
			c.set(Calendar.SECOND, 59);		
			Date toGeneral = c.getTime();
						
			fluTweets.clear();
			stomachTweets.clear();		
			riotTweets.clear();
			carTweets.clear();;
			truckTweets.clear();;
			planeTweets.clear();
			bombTweets.clear();
			heatTweets.clear();
			noEventTweets.clear();
			fluTweets.addAll(dataManager.getFluTweets(fromGeneral, toGeneral, scaleFactor, pointRadius,bleep));
			stomachTweets.addAll(dataManager.getStomachTweets(fromGeneral, toGeneral, scaleFactor, pointRadius,bleep));		
			riotTweets.addAll(dataManager.getRiotTweets(fromGeneral, toGeneral, scaleFactor, pointRadius,riotImg));
			carTweets.addAll(dataManager.getCarTweets(fromGeneral, toGeneral, scaleFactor, pointRadius,carImg));
			truckTweets.addAll(dataManager.getTruckTweets(fromGeneral, toGeneral, scaleFactor, pointRadius,truckImg));
			planeTweets.addAll(dataManager.getPlaneTweets(fromGeneral, toGeneral, scaleFactor, pointRadius,planeImg));
			bombTweets.addAll(dataManager.getBombTweets(fromGeneral, toGeneral, scaleFactor, pointRadius,bombImg));
			heatTweets.addAll(dataManager.getHeatTweets(fromGeneral, toGeneral, scaleFactor, pointRadius,heatImg));
			noEventTweets.addAll(dataManager.getOtherTweets(fromGeneral, toGeneral, scaleFactor, pointRadius,bleep));
			fluCount = stomachCount = generalCount = 0;
			fluCount += dataManager.getFluCount(fromGeneral, toGeneral);
			stomachCount += dataManager.getStomachCount(fromGeneral, toGeneral);
			generalCount += dataManager.getGeneralCount(fromGeneral, toGeneral);		
			float percentFlu = (float)fluCount/(float)(fluCount+stomachCount+generalCount);
			float percentStomach = (float)stomachCount/(float)(fluCount+stomachCount+generalCount);;
			float percentOther = 1-(percentFlu+percentStomach);
			pieChart.removeCatagory("Flu Sickness");
			pieChart.removeCatagory("Stomach Sickness");
			pieChart.removeCatagory("No Sickness");
			pieChart.insertCatagory("Flu Sickness", percentFlu, parent.color(TweetType.FluProblem.red(),TweetType.FluProblem.green(),TweetType.FluProblem.blue()));
			pieChart.insertCatagory("Stomach Sickness", percentStomach, parent.color(TweetType.StomachProblem.red(),TweetType.StomachProblem.green(),TweetType.StomachProblem.blue()));
			pieChart.insertCatagory("No Sickness", percentOther, parent.color(TweetType.AllTweet.red(),TweetType.AllTweet.green(),TweetType.AllTweet.blue()));
	
			Play = false;
		}
		

		
	
		
	}

	private void adjustTweets(){

		for(Tweet t : this.noEventTweets){
			ScreenLocation sc = map.getPosByGIS(t.lat, t.lon);
			t.setXYLocation(sc.xPos, sc.yPos,pointRadius);																
		}

		for(Tweet t : this.carTweets){
			ScreenLocation sc = map.getPosByGIS(t.lat, t.lon);
			t.setXYLocation(sc.xPos, sc.yPos,pointRadius);																	
		}

		for(Tweet t : this.heatTweets){
			ScreenLocation sc = map.getPosByGIS(t.lat, t.lon);
			t.setXYLocation(sc.xPos, sc.yPos,pointRadius);																
		}


		for(Tweet t : this.riotTweets){
			ScreenLocation sc = map.getPosByGIS(t.lat, t.lon);
			t.setXYLocation(sc.xPos, sc.yPos,pointRadius);														
		}		
				
		for(Tweet t : this.truckTweets){
			ScreenLocation sc = map.getPosByGIS(t.lat, t.lon);
			t.setXYLocation(sc.xPos, sc.yPos,pointRadius);														
		}
		for(Tweet t : this.fluTweets){					
			ScreenLocation sc = map.getPosByGIS(t.lat, t.lon);
			t.setXYLocation(sc.xPos, sc.yPos,pointRadius);																	
		}
		
		for(Tweet t : this.stomachTweets){
			ScreenLocation sc = map.getPosByGIS(t.lat, t.lon);
			t.setXYLocation(sc.xPos, sc.yPos,pointRadius);											
		}		
				
	}
	
	
	  
	
	
	
	
	
}
