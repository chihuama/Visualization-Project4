import java.util.Date;

import processing.core.PApplet;
import processing.core.PImage;


public class Tweet {
	public int ID;
	public Date date_time;
	public float lat;
	public float lon;
	public String msg;
	public TweetType type;
	private PApplet parent;
	private int scaleFactor = 1;
	private float lastXPos = 0;
	private float lastYPos = 0;
	PImage img = null;
	public int eventType = 0;
	
	public Tweet(int ID, Date date, float lat, float lon, String msg, TweetType type, PApplet parent, int scaleFactor, float pointRadius, int event, PImage img){
		this.ID = ID;
		this.date_time = date;
		this.lat = lat;
		this.lon = lon;
		this.msg = new String( msg);
		this.type = type;
		this.parent = parent;
		this.scaleFactor = scaleFactor;				
		this.img = img;		
		eventType = event;
	}
	
	
	public void draw(float xPos, float yPos, int zoomLevel, float pointRadius){
		this.lastXPos = xPos;
		this.lastYPos = yPos;
		
		if(isOnScreen(zoomLevel,pointRadius)){
			
			if(this.type == TweetType.StomachProblem ||type == TweetType.FluProblem){
				
				parent.fill(type.red(),type.green(),type.blue(),120);
				parent.noStroke();
				parent.rectMode(parent.CORNERS);				
				parent.rect(lastXPos, lastYPos,lastXPos+pointRadius, lastYPos+pointRadius);
				parent.fill(0);
				parent.stroke(0);
				//parent.ellipseMode(parent.CORNER);
				/*parent.tint(type.red(), type.green(), type.blue(),220);
				parent.imageMode(parent.CENTER);
				if(img.width != (int)pointRadius){		
					parent.imageMode(parent.CENTER);
					img = parent.loadImage("tweet2.png");
					parent.imageMode(parent.CORNER);
					img.resize((int)(pointRadius), (int)(pointRadius));
					//System.out.println((lastXPos-img.width)+","+(lastXPos+img.width)+","+(lastYPos-img.height)+","+(lastYPos+img.height));			
				}
				parent.image(img, lastXPos, lastYPos);
				parent.imageMode(parent.CORNER);
				parent.noTint();*/
			}
			else{
				parent.tint(TweetType.AllTweet.red(), TweetType.AllTweet.green(), TweetType.AllTweet.blue(),220);
				resize(pointRadius);
				parent.imageMode(parent.CENTER);
				parent.image(img, lastXPos, lastYPos);
				parent.imageMode(parent.CORNER);
				parent.noTint();
			}

		}			
	}
	
	public boolean isOnScreen(int zoomLevel,float pointRadius){
		if(lastXPos-img.width >= 0 &&
				lastXPos+img.width <= parent.width &&
				lastYPos-img.height >= 0 &&				
				lastYPos+img.height <= parent.height){
			return true;
		}
		else{
			return false;
		}
	}
	
	public boolean touchHandler(float xPos, float yPos, int zoomLevel, float pointRadius){
		return xPos >= this.lastXPos-pointRadius &&
				xPos <= this.lastXPos+(pointRadius) &&
				yPos >= this.lastYPos-(pointRadius) &&
				yPos <= (this.lastYPos+(pointRadius));	
	}
	
	private void resize(float pointRadius){
		if(type == TweetType.carCrash){
			if(img.width != (int)pointRadius*2){		
				parent.imageMode(parent.CENTER);
				img = parent.loadImage("carCrash.png");
				parent.imageMode(parent.CORNER);
				img.resize((int)(pointRadius*2), (int)(pointRadius*2));								
			}
		}
		else if(type == TweetType.planeCrash){
			if(img.width != (int)pointRadius*2){		
				parent.imageMode(parent.CENTER);
				img = parent.loadImage("planeCrash.png");
				parent.imageMode(parent.CORNER);
				img.resize((int)(pointRadius*2), (int)(pointRadius*2));								
			}
		}
		else if(type == TweetType.heatTweet){
			if(img.width != (int)pointRadius*2){		
				parent.imageMode(parent.CENTER);
				img = parent.loadImage("heatAdv.png");
				parent.imageMode(parent.CORNER);
				img.resize((int)(pointRadius*2), (int)(pointRadius*2));								
			}
		}
		else if(type == TweetType.riotTweet){
			if(img.width != (int)pointRadius*2){		
				parent.imageMode(parent.CENTER);
				img = parent.loadImage("looting.png");
				parent.imageMode(parent.CORNER);
				img.resize((int)(pointRadius*2), (int)(pointRadius*2));								
			}
		}
		else if(type == TweetType.bombTweet){
			if(img.width != (int)pointRadius*2){		
				parent.imageMode(parent.CENTER);
				img = parent.loadImage("bomb.png");
				parent.imageMode(parent.CORNER);
				img.resize((int)(pointRadius*2), (int)(pointRadius*2));								
			}
		}
		else if(type == TweetType.truckCrash){
			if(img.width != (int)pointRadius*2){		
				parent.imageMode(parent.CENTER);
				img = parent.loadImage("truckCrash.png");
				parent.imageMode(parent.CORNER);
				img.resize((int)(pointRadius*2), (int)(pointRadius*2));								
			}
		}
	}
	
	public void setXYLocation(float xPos, float yPos, float pointRadius){
		this.lastXPos = xPos;
		this.lastYPos = yPos;
		//resize(pointRadius);
	}
}
