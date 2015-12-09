

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;


import processing.core.*;


// Don't know how java SQL client works?
// http://docs.oracle.com/javase/1.4.2/docs/api/java/sql/package-summary.html
// mysql databases? http://www.uic.edu/depts/accc/mysql/


// then the selection manager will display data depending on what is called from the graph.
public class DataManager {
	Connection mysql = null;
	PApplet parent = null;
	public DataManager(PApplet parent,boolean isOnWall){
		this.parent = parent;
		try{
			
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			//mysql = DriverManager.getConnection("rstahm2.mysql.uic.edu/rstahm2", "rstahm2-rw", "Project2");
			
			if(isOnWall){
				mysql = DriverManager.getConnection("jdbc:mysql://omgtracker.evl.uic.edu:3306/project4_group8?user=cs424&password=cs424");
			}
			else{
				//mysql = DriverManager.getConnection("jdbc:mysql://localhost:3306/pdatabase?user=root&password=ewwt2006wc");
                                mysql = DriverManager.getConnection("jdbc:mysql://omgtracker.evl.uic.edu:3306/project4_group8?user=cs424&password=cs424");
			}			
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		
	}
	
	

	private String formatDate(int year, int month, int day){
		String ret = year+"-";
		if(month == 0){
			ret+="00-";
		}
		else{
			ret+=month+"-";
		}
		
		if(day == 0){
			ret+="00";
		}
		else{
			ret+=""+day;
		}
		return ret;
	}
	
	private String formatTime(int hour, int min, int sec){
		String ret = "";		
		if(hour >= 0 && hour < 10){
			ret+="0"+hour+":";
		}
		else{
			ret+=hour+":";
		}
		
		if(min >= 0 && min < 10){
			ret+="0"+min+":";
		}
		else{
			ret+=min+":";
		}
		
		if(sec >= 0 && sec < 10){
			ret+="0"+sec;
		}
		else{
			ret+=""+sec;
		}
		return ret;
	}
	
	
	
	public int getFluCount(Date from, Date to) {
		Statement stmt = null;
		ResultSet rs = null;
		try {
		    stmt = mysql.createStatement();
		    
		    
		    Calendar c = Calendar.getInstance();
		    c.setTime(from);
		    String fromD = formatDate(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
		    c.setTime(to);
		    String toD = formatDate(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
		    c.setTime(from);		   
		    String fromT = formatTime(c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),c.get(Calendar.SECOND));
		    c.setTime(to);
		    String toT = formatTime(c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),c.get(Calendar.SECOND));
		    String qry = "SELECT count(*) FROM `blogs` WHERE  (`date` >= '"+fromD+"' and `date` <= '"+toD +
		    		"' and (`sicknessInd` = '1' or `sicknessInd` = '3'))";

		    //System.out.println(qry);
		    rs = stmt.executeQuery(qry);
		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		    return 0;
		}
		// go through each row and build a Selection
		try{
			
			rs.first();
			return rs.getInt(1);
			
			

			
		}
		catch(Exception ex){
			//ex.printStackTrace();
			return 0;
		}
	}
	
	public int getStomachCount(Date from, Date to) {
		Statement stmt = null;
		ResultSet rs = null;
		try {
		    stmt = mysql.createStatement();
		    
		    
		    Calendar c = Calendar.getInstance();
		    c.setTime(from);
		    String fromD = formatDate(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
		    c.setTime(to);
		    String toD = formatDate(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
		    c.setTime(from);		   
		    String fromT = formatTime(c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),c.get(Calendar.SECOND));
		    c.setTime(to);
		    String toT = formatTime(c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),c.get(Calendar.SECOND));
		    String qry = "SELECT count(*) FROM `blogs` WHERE  (`date` >= '"+fromD+"' and `date` <= '"+toD +
		    		"' and `sicknessInd` = '2')";

		    //System.out.println(qry);
		    rs = stmt.executeQuery(qry);
		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		    return 0;
		}
		// go through each row and build a Selection
		try{
			
			
			rs.first();
			return rs.getInt(1);
			
			
		}
		catch(Exception ex){
			//ex.printStackTrace();
			return 0;
		}
	}
	
	public int getGeneralCount(Date from, Date to) {
		Statement stmt = null;
		ResultSet rs = null;
		try {
		    stmt = mysql.createStatement();
		    
		    
		    Calendar c = Calendar.getInstance();
		    c.setTime(from);
		    String fromD = formatDate(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
		    c.setTime(to);
		    String toD = formatDate(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
		    c.setTime(from);		   
		    String fromT = formatTime(c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),c.get(Calendar.SECOND));
		    c.setTime(to);
		    String toT = formatTime(c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),c.get(Calendar.SECOND));
		    String qry = "SELECT count(*) FROM `blogs` WHERE  (`date` >= '"+fromD+"' and `date` <= '"+toD +
		    		"' and `time` >='"+fromT+ "' and `time` <= '"+toT+"' and `sicknessInd` = '0')";

		    //System.out.println(qry);
		    rs = stmt.executeQuery(qry);
		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		    return 0;
		}
		try{						
			rs.first();
			return rs.getInt(1);
		}
		catch(Exception ex){
			//ex.printStackTrace();
			return 0;
		}
	}
	
	
	public Collection<? extends Tweet> getFluTweets(Date from, Date to, int scaleFactor, float pointRadius, PImage img) {
		Statement stmt = null;
		ResultSet rs = null;
		try {
		    stmt = mysql.createStatement();
		    
		    
		    Calendar c = Calendar.getInstance();
		    c.setTime(from);
		    String fromD = formatDate(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
		    c.setTime(to);
		    String toD = formatDate(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
		    c.setTime(from);		   
		    String fromT = formatTime(c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),c.get(Calendar.SECOND));
		    c.setTime(to);
		    String toT = formatTime(c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),c.get(Calendar.SECOND));
		    //String qry = "SELECT * FROM `blogs` WHERE  (`date` >= '"+fromD+"' and `date` <= '"+toD +
		    //		"' and (`sicknessInd` = '1' or `sicknessInd` = '3')) ORDER BY `date` asc";
		    String qry = "SELECT * FROM `blogs` WHERE  (`date` >= '"+fromD+"' and `date` <= '"+toD +
				    "' and `time` >='"+fromT+"' and `time` <='"+toT+"' and (`sicknessInd` = '1' or `sicknessInd` = '3')) ORDER BY `date` asc";
		    //System.out.println(qry);
		    rs = stmt.executeQuery(qry);
		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		    return null;
		}
		// go through each row and build a Selection
		ArrayList<Tweet> selections = new ArrayList<Tweet>();
		try{
			
			rs.first();

			while (!rs.isAfterLast()) {

				int id = rs.getInt("pId");
				if(rs.wasNull()){
					return selections;
				}
				else{
					Calendar c = Calendar.getInstance();
					c.setTimeInMillis(rs.getDate("date").getTime()+rs.getTime("time").getTime());
					
					Date dated = c.getTime();

					float lat = rs.getFloat("lat");
					float lon = rs.getFloat("lon");
					int event = rs.getInt("eventInd");
					String msg = rs.getNString("text");

					Tweet t = new Tweet(id, dated, lat, lon, msg,
							TweetType.FluProblem, parent, scaleFactor, pointRadius, event, img);
					selections.add(t);
					rs.next();
				}
				
			}

			return selections;
		}
		catch(Exception ex){
			//ex.printStackTrace();
			return selections;
		}
	}
	
	
	public Collection<? extends Tweet> getStomachTweets(Date from, Date to, int scaleFactor, float pointRadius,PImage img) {
		Statement stmt = null;
		ResultSet rs = null;
		try {
		    stmt = mysql.createStatement();
		    
		    
		    Calendar c = Calendar.getInstance();
		    c.setTime(from);
		    String fromD = formatDate(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
		    c.setTime(to);
		    String toD = formatDate(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
		    c.setTime(from);		   
		    String fromT = formatTime(c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),c.get(Calendar.SECOND));
		    c.setTime(to);
		    String toT = formatTime(c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),c.get(Calendar.SECOND));
		    String qry = "SELECT * FROM `blogs` WHERE  (`date` >= '"+fromD+"' and `date` <= '"+toD +
		    		"' and `time` >='"+fromT+"' and `time` <='"+toT+"' and `sicknessInd` = '2') ORDER BY `date` asc";

		    //System.out.println(qry);
		    rs = stmt.executeQuery(qry);
		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		    return null;
		}
		// go through each row and build a Selection
		ArrayList<Tweet> selections = new ArrayList<Tweet>();
		try{
			
			rs.first();

			while (!rs.isAfterLast()) {

				int id = rs.getInt("pId");
				if(rs.wasNull()){
					return selections;
				}
				else{
					Calendar c = Calendar.getInstance();
					c.setTimeInMillis(rs.getDate("date").getTime()+rs.getTime("time").getTime());
					
					Date dated = c.getTime();

					float lat = rs.getFloat("lat");
					float lon = rs.getFloat("lon");
					int event = rs.getInt("eventInd");
					String msg = rs.getNString("text");

					Tweet t = new Tweet(id, dated, lat, lon, msg,
							TweetType.StomachProblem, parent, scaleFactor, pointRadius, event,img);
					selections.add(t);
					rs.next();
				}
				
			}

			return selections;
		}
		catch(Exception ex){
			//ex.printStackTrace();
			return selections;
		}
	}
	
	public Collection<? extends Tweet> getGeneralTweets(Date from, Date to, int scaleFactor, float pointRadius, PImage img) {
		Statement stmt = null;
		ResultSet rs = null;
		try {
		    stmt = mysql.createStatement();
		    
		    
		    Calendar c = Calendar.getInstance();
		    c.setTime(from);
		    String fromD = formatDate(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
		    c.setTime(to);
		    String toD = formatDate(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
		    c.setTime(from);		   
		    String fromT = formatTime(c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),c.get(Calendar.SECOND));
		    c.setTime(to);
		    String toT = formatTime(c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),c.get(Calendar.SECOND));
		    String qry = "SELECT * FROM `blogs` WHERE  (`date` >= '"+fromD+"' and `date` <= '"+toD +
		    		"' and`eventInd` = '7') ORDER BY `date` asc";

		    //System.out.println(qry);
		    rs = stmt.executeQuery(qry);
		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		    return null;
		}
		// go through each row and build a Selection
		ArrayList<Tweet> selections = new ArrayList<Tweet>();
		try{
			
			rs.first();

			while (!rs.isAfterLast()) {

				int id = rs.getInt("pId");
				if(rs.wasNull()){
					return selections;
				}
				else{
					Calendar c = Calendar.getInstance();
					c.setTimeInMillis(rs.getDate("date").getTime()+rs.getTime("time").getTime());
					
					Date dated = c.getTime();

					float lat = rs.getFloat("lat");
					float lon = rs.getFloat("lon");
					int event = rs.getInt("eventInd");
					String msg = rs.getNString("text");

					Tweet t = new Tweet(id, dated, lat, lon, msg,
							TweetType.AllTweet, parent, scaleFactor, pointRadius, event,img);
					selections.add(t);
					rs.next();
				}
				
			}

			return selections;
		}
		catch(Exception ex){
			//ex.printStackTrace();
			return selections;
		}
	}

	public Collection<? extends Tweet> noEventsTweets(Date from, Date to, int scaleFactor, float pointRadius, PImage img) {
		Statement stmt = null;
		ResultSet rs = null;
		try {
		    stmt = mysql.createStatement();
		    
		    
		    Calendar c = Calendar.getInstance();
		    c.setTime(from);
		    String fromD = formatDate(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
		    c.setTime(to);
		    String toD = formatDate(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
		    c.setTime(from);		   
		    String fromT = formatTime(c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),c.get(Calendar.SECOND));
		    c.setTime(to);
		    String toT = formatTime(c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),c.get(Calendar.SECOND));
		    String qry = "SELECT * FROM `blogs` WHERE  (`date` >= '"+fromD+"' and `date` <= '"+toD +
		    		"' and `eventInd` = '0') ORDER BY `date` asc";

		    //System.out.println(qry);
		    rs = stmt.executeQuery(qry);
		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		    return null;
		}
		// go through each row and build a Selection
		ArrayList<Tweet> selections = new ArrayList<Tweet>();
		try{
			
			rs.first();

			while (!rs.isAfterLast()) {

				int id = rs.getInt("pId");
				if(rs.wasNull()){
					return selections;
				}
				else{
					Calendar c = Calendar.getInstance();
					c.setTimeInMillis(rs.getDate("date").getTime()+rs.getTime("time").getTime());
					
					Date dated = c.getTime();

					float lat = rs.getFloat("lat");
					float lon = rs.getFloat("lon");
					int event = rs.getInt("eventInd");
					String msg = rs.getNString("text");

					Tweet t = new Tweet(id, dated, lat, lon, msg,
							TweetType.AllTweet, parent, scaleFactor, pointRadius, event,img);
					selections.add(t);
					rs.next();
				}
				
			}

			return selections;
		}
		catch(Exception ex){
			//ex.printStackTrace();
			return selections;
		}
	}
	
	public Collection<? extends Tweet> getRiotTweets(Date from, Date to, int scaleFactor, float pointRadius, PImage img) {
		Statement stmt = null;
		ResultSet rs = null;
		try {
		    stmt = mysql.createStatement();
		    
		    
		    Calendar c = Calendar.getInstance();
		    c.setTime(from);
		    String fromD = formatDate(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
		    c.setTime(to);
		    String toD = formatDate(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
		    c.setTime(from);		   
		    String fromT = formatTime(c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),c.get(Calendar.SECOND));
		    c.setTime(to);
		    String toT = formatTime(c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),c.get(Calendar.SECOND));
		    String qry = "SELECT * FROM `blogs` WHERE  (`date` >= '"+fromD+"' and `date` <= '"+toD +
		    		"' and `eventInd` = '1') ORDER BY `date` asc";

		    //System.out.println(qry);
		    rs = stmt.executeQuery(qry);
		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		    return null;
		}
		// go through each row and build a Selection
		ArrayList<Tweet> selections = new ArrayList<Tweet>();
		try{
			
			rs.first();

			while (!rs.isAfterLast()) {

				int id = rs.getInt("pId");
				if(rs.wasNull()){
					return selections;
				}
				else{
					Calendar c = Calendar.getInstance();
					c.setTimeInMillis(rs.getDate("date").getTime()+rs.getTime("time").getTime());
					
					Date dated = c.getTime();

					float lat = rs.getFloat("lat");
					float lon = rs.getFloat("lon");
					int event = rs.getInt("eventInd");
					String msg = rs.getNString("text");

					Tweet t = new Tweet(id, dated, lat, lon, msg,
							TweetType.riotTweet, parent, scaleFactor, pointRadius, event,img);
					selections.add(t);
					rs.next();
				}
				
			}

			return selections;
		}
		catch(Exception ex){
			//ex.printStackTrace();
			return selections;
		}
	}
	
	public Collection<? extends Tweet> getCarTweets(Date from, Date to, int scaleFactor, float pointRadius, PImage img) {
		Statement stmt = null;
		ResultSet rs = null;
		try {
		    stmt = mysql.createStatement();
		    
		    
		    Calendar c = Calendar.getInstance();
		    c.setTime(from);
		    String fromD = formatDate(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
		    c.setTime(to);
		    String toD = formatDate(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
		    c.setTime(from);		   
		    String fromT = formatTime(c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),c.get(Calendar.SECOND));
		    c.setTime(to);
		    String toT = formatTime(c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),c.get(Calendar.SECOND));
		    String qry = "SELECT * FROM `blogs` WHERE  (`date` >= '"+fromD+"' and `date` <= '"+toD +
		    		"' and `eventInd` = '2') ORDER BY `date` asc";

		    //System.out.println(qry);
		    rs = stmt.executeQuery(qry);
		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		    return null;
		}
		// go through each row and build a Selection
		ArrayList<Tweet> selections = new ArrayList<Tweet>();
		try{
			
			rs.first();

			while (!rs.isAfterLast()) {

				int id = rs.getInt("pId");
				if(rs.wasNull()){
					return selections;
				}
				else{
					Calendar c = Calendar.getInstance();
					c.setTimeInMillis(rs.getDate("date").getTime()+rs.getTime("time").getTime());
					
					Date dated = c.getTime();

					float lat = rs.getFloat("lat");
					float lon = rs.getFloat("lon");
					int event = rs.getInt("eventInd");
					String msg = rs.getNString("text");

					Tweet t = new Tweet(id, dated, lat, lon, msg,
							TweetType.carCrash, parent, scaleFactor, pointRadius, event,img);
					selections.add(t);
					rs.next();
				}
				
			}

			return selections;
		}
		catch(Exception ex){
			//ex.printStackTrace();
			return selections;
		}
	}
	
	public Collection<? extends Tweet> getTruckTweets(Date from, Date to, int scaleFactor, float pointRadius, PImage img) {
		Statement stmt = null;
		ResultSet rs = null;
		try {
		    stmt = mysql.createStatement();
		    
		    
		    Calendar c = Calendar.getInstance();
		    c.setTime(from);
		    String fromD = formatDate(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
		    c.setTime(to);
		    String toD = formatDate(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
		    c.setTime(from);		   
		    String fromT = formatTime(c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),c.get(Calendar.SECOND));
		    c.setTime(to);
		    String toT = formatTime(c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),c.get(Calendar.SECOND));
		    String qry = "SELECT * FROM `blogs` WHERE  (`date` >= '"+fromD+"' and `date` <= '"+toD +
		    		"' and `eventInd` = '3') ORDER BY `date` asc";

		    //System.out.println(qry);
		    rs = stmt.executeQuery(qry);
		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		    return null;
		}
		// go through each row and build a Selection
		ArrayList<Tweet> selections = new ArrayList<Tweet>();
		try{
			
			rs.first();

			while (!rs.isAfterLast()) {

				int id = rs.getInt("pId");
				if(rs.wasNull()){
					return selections;
				}
				else{
					Calendar c = Calendar.getInstance();
					c.setTimeInMillis(rs.getDate("date").getTime()+rs.getTime("time").getTime());
					
					Date dated = c.getTime();

					float lat = rs.getFloat("lat");
					float lon = rs.getFloat("lon");
					int event = rs.getInt("eventInd");
					String msg = rs.getNString("text");

					Tweet t = new Tweet(id, dated, lat, lon, msg,
							TweetType.truckCrash, parent, scaleFactor, pointRadius, event,img);
					selections.add(t);
					rs.next();
				}
				
			}

			return selections;
		}
		catch(Exception ex){
			//ex.printStackTrace();
			return selections;
		}
	}
	
	public Collection<? extends Tweet> getPlaneTweets(Date from, Date to, int scaleFactor, float pointRadius, PImage img) {
		Statement stmt = null;
		ResultSet rs = null;
		try {
		    stmt = mysql.createStatement();
		    
		    
		    Calendar c = Calendar.getInstance();
		    c.setTime(from);
		    String fromD = formatDate(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
		    c.setTime(to);
		    String toD = formatDate(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
		    c.setTime(from);		   
		    String fromT = formatTime(c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),c.get(Calendar.SECOND));
		    c.setTime(to);
		    String toT = formatTime(c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),c.get(Calendar.SECOND));
		    String qry = "SELECT * FROM `blogs` WHERE  (`date` >= '"+fromD+"' and `date` <= '"+toD +
		    		"' and `eventInd` = '4') ORDER BY `date` asc";

		    //System.out.println(qry);
		    rs = stmt.executeQuery(qry);
		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		    return null;
		}
		// go through each row and build a Selection
		ArrayList<Tweet> selections = new ArrayList<Tweet>();
		try{
			
			rs.first();

			while (!rs.isAfterLast()) {

				int id = rs.getInt("pId");
				if(rs.wasNull()){
					return selections;
				}
				else{
					Calendar c = Calendar.getInstance();
					c.setTimeInMillis(rs.getDate("date").getTime()+rs.getTime("time").getTime());
					
					Date dated = c.getTime();

					float lat = rs.getFloat("lat");
					float lon = rs.getFloat("lon");
					int event = rs.getInt("eventInd");
					String msg = rs.getNString("text");

					Tweet t = new Tweet(id, dated, lat, lon, msg,
							TweetType.planeCrash, parent, scaleFactor, pointRadius, event,img);
					selections.add(t);
					rs.next();
				}
				
			}
			System.out.println("plane size: "+selections.size());
			return selections;
		}
		catch(Exception ex){
			//ex.printStackTrace();
			System.out.println("plane size (ex): "+selections.size());
			return selections;
		}
	}
	
	public Collection<? extends Tweet> getBombTweets(Date from, Date to, int scaleFactor, float pointRadius, PImage img) {
		Statement stmt = null;
		ResultSet rs = null;
		try {
		    stmt = mysql.createStatement();
		    
		    
		    Calendar c = Calendar.getInstance();
		    c.setTime(from);
		    String fromD = formatDate(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
		    c.setTime(to);
		    String toD = formatDate(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
		    c.setTime(from);		   
		    String fromT = formatTime(c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),c.get(Calendar.SECOND));
		    c.setTime(to);
		    String toT = formatTime(c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),c.get(Calendar.SECOND));
		    String qry = "SELECT * FROM `blogs` WHERE  (`date` >= '"+fromD+"' and `date` <= '"+toD +
		    		"' and `eventInd` = '5') ORDER BY `date` asc";

		    //System.out.println(qry);
		    rs = stmt.executeQuery(qry);
		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		    return null;
		}
		// go through each row and build a Selection
		ArrayList<Tweet> selections = new ArrayList<Tweet>();
		try{
			
			rs.first();

			while (!rs.isAfterLast()) {

				int id = rs.getInt("pId");
				if(rs.wasNull()){
					return selections;
				}
				else{
					Calendar c = Calendar.getInstance();
					c.setTimeInMillis(rs.getDate("date").getTime()+rs.getTime("time").getTime());
					
					Date dated = c.getTime();

					float lat = rs.getFloat("lat");
					float lon = rs.getFloat("lon");
					int event = rs.getInt("eventInd");
					String msg = rs.getNString("text");

					Tweet t = new Tweet(id, dated, lat, lon, msg,
							TweetType.bombTweet, parent, scaleFactor, pointRadius, event,img);
					selections.add(t);
					rs.next();
				}
				
			}

			return selections;
		}
		catch(Exception ex){
			//ex.printStackTrace();
			return selections;
		}
	}
	
	public Collection<? extends Tweet> getHeatTweets(Date from, Date to, int scaleFactor, float pointRadius, PImage img) {
		Statement stmt = null;
		ResultSet rs = null;
		try {
		    stmt = mysql.createStatement();
		    
		    
		    Calendar c = Calendar.getInstance();
		    c.setTime(from);
		    String fromD = formatDate(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
		    c.setTime(to);
		    String toD = formatDate(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
		    c.setTime(from);		   
		    String fromT = formatTime(c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),c.get(Calendar.SECOND));
		    c.setTime(to);
		    String toT = formatTime(c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),c.get(Calendar.SECOND));
		    String qry = "SELECT * FROM `blogs` WHERE  (`date` >= '"+fromD+"' and `date` <= '"+toD +
		    		"' and `eventInd` = '6') ORDER BY `date` asc";

		    //System.out.println(qry);
		    rs = stmt.executeQuery(qry);
		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		    return null;
		}
		// go through each row and build a Selection
		ArrayList<Tweet> selections = new ArrayList<Tweet>();
		try{
			
			rs.first();

			while (!rs.isAfterLast()) {

				int id = rs.getInt("pId");
				if(rs.wasNull()){
					return selections;
				}
				else{
					Calendar c = Calendar.getInstance();
					c.setTimeInMillis(rs.getDate("date").getTime()+rs.getTime("time").getTime());
					
					Date dated = c.getTime();

					float lat = rs.getFloat("lat");
					float lon = rs.getFloat("lon");
					int event = rs.getInt("eventInd");
					String msg = rs.getNString("text");

					Tweet t = new Tweet(id, dated, lat, lon, msg,
							TweetType.heatTweet, parent, scaleFactor, pointRadius, event,img);
					selections.add(t);
					rs.next();
				}
				
			}

			return selections;
		}
		catch(Exception ex){
			//ex.printStackTrace();
			return selections;
		}
	}
	
	public Collection<? extends Tweet> getOtherTweets(Date from, Date to, int scaleFactor, float pointRadius, PImage img) {
		Statement stmt = null;
		ResultSet rs = null;
		try {
		    stmt = mysql.createStatement();
		    
		    
		    Calendar c = Calendar.getInstance();
		    c.setTime(from);
		    String fromD = formatDate(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
		    c.setTime(to);
		    String toD = formatDate(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
		    c.setTime(from);		   
		    String fromT = formatTime(c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),c.get(Calendar.SECOND));
		    c.setTime(to);
		    String toT = formatTime(c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),c.get(Calendar.SECOND));
		    String qry = "SELECT * FROM `blogs` WHERE  (`date` >= '"+fromD+"' and `date` <= '"+toD +
		    		"' and `eventInd` = '7') ORDER BY `date` asc";

		    //System.out.println(qry);
		    rs = stmt.executeQuery(qry);
		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		    return null;
		}
		// go through each row and build a Selection
		ArrayList<Tweet> selections = new ArrayList<Tweet>();
		try{
			
			rs.first();

			while (!rs.isAfterLast()) {

				int id = rs.getInt("pId");
				if(rs.wasNull()){
					return selections;
				}
				else{
					Calendar c = Calendar.getInstance();
					c.setTimeInMillis(rs.getDate("date").getTime()+rs.getTime("time").getTime());
					
					Date dated = c.getTime();

					float lat = rs.getFloat("lat");
					float lon = rs.getFloat("lon");
					int event = rs.getInt("eventInd");
					String msg = rs.getNString("text");

					Tweet t = new Tweet(id, dated, lat, lon, msg,
							TweetType.AllTweet, parent, scaleFactor, pointRadius, event,img);
					selections.add(t);
					rs.next();
				}
				
			}

			return selections;
		}
		catch(Exception ex){
			//ex.printStackTrace();
			return selections;
		}
	}
	
	
	public ArrayList<WeatherUnit> getWeather() {
		Statement stmt = null;
		ResultSet rs = null;
		try {
		    stmt = mysql.createStatement();
		    String qry = "SELECT * FROM `weather`";

		    //System.out.println(qry);
		    rs = stmt.executeQuery(qry);
		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		    return null;
		}
		// go through each row and build a Selection
		try{
			rs.first();
			ArrayList<WeatherUnit> selections = new ArrayList<WeatherUnit>();
			while(!rs.isAfterLast()){
			
				
				Date date = rs.getDate(1);
				int weatherId = rs.getInt(2);
				String weatherDesc = rs.getString(3);
				int speed = rs.getInt(4);	
				int windId = rs.getInt(5);
				String windDesc = rs.getString(6);
		
				WeatherUnit u = new WeatherUnit(date, weatherId, weatherDesc, windId, windDesc, speed);
				selections.add(u);
				rs.next();
			}
			
			return selections;
		}
		catch(Exception ex){
			return null;
		}
	}
	
	
}
