import java.util.ArrayList;
import java.util.HashMap;

import processing.core.*;


public class MultiCatChart {
	PApplet parent;
	int scaleFactor = 1;
	float xPos;
	float yPos;
	float width;
	float height;
	
	float yUnitLabelMin;
	public float yUnitLabelMax; 
	float yInterval = 0;
	
	
	public String title = "";
	ArrayList<String> groupings;
	ArrayList<Subgrouping> subGroups;
	HashMap<String,ArrayList<Float>> subGroupsToValues;
	HashMap<String, HashMap<Subgrouping, Float>> groupsToSubgroupsToValues;
	MultiCatChart(PApplet parent, int scaleFactor, String title, float xPos, float yPos, float width, float height){
		this.parent = parent;
		this.scaleFactor = scaleFactor;
		this.xPos = xPos;
		this.yPos = yPos;
		this.width = width;
		this.height = height;
		this.title = title;
		groupings = new ArrayList<String>();
		subGroups = new ArrayList<Subgrouping>();		
		subGroupsToValues = new HashMap<String,ArrayList<Float>>();
		groupsToSubgroupsToValues = new HashMap<String,HashMap<Subgrouping,Float>>();
		yUnitLabelMin = 0;
		yUnitLabelMax = 20;
	}
	public void draw(){	
		parent.fill(150);
		parent.strokeWeight(1);
		parent.stroke(170);
		float outlinex1 = xPos;
		float outlinex2 = xPos+width;
		float outliney1 = yPos;
		float outliney2 = yPos+height;
		parent.noStroke();
		parent.rect(outlinex1, outliney1, outlinex2, outliney2);
		parent.stroke(170);
		float graphx1 = outlinex1 + (((float)(0.04))*width);
		float graphx2 = outlinex2 - (((float)(0.02))*width);
		float graphy1 = outliney1 + (((float)(0.21))*height);
		float graphy2 = outliney2 - (((float)(0.25))*height);
		parent.fill(254);
		parent.textSize(15*scaleFactor);
		float titleYOff = ((float)((graphy1-outliney1)/2));
		float titleXOff = ((float)((outlinex2-outlinex1)/2));
		parent.textAlign(parent.CENTER);
		parent.text(title, outlinex1+titleXOff,outliney1+titleYOff);
		parent.textSize(8*scaleFactor);
		parent.textAlign(parent.LEFT);
		for(float i = yUnitLabelMin; i <= yUnitLabelMax; i += yInterval){
			//float percent = ((float)i)/((float)100);
			//float labelVal = parent.lerp(yUnitLabelMin, yUnitLabelMax, percent);			
			float yLabelPos = parent.map(i, yUnitLabelMin, yUnitLabelMax, graphy2, graphy1);
			parent.textSize(9*scaleFactor);
			parent.fill(254);
			parent.text(FormatDataValue(""+i, 1)+"%", graphx1- 15*scaleFactor, yLabelPos+2);
			parent.strokeWeight(3*scaleFactor);
			parent.line(graphx1+10*scaleFactor, yLabelPos, graphx2, yLabelPos);
			parent.strokeWeight(1*scaleFactor);
			parent.fill(170);
			parent.textSize(8*scaleFactor);			
		}
		int pos = 1;
		float groupYPos = graphy2 + 10*scaleFactor;
		ArrayList<Float> centers = new ArrayList<Float>();
		for(String group : groupings){
			//this will be the cluster of bar graphs
			float groupXPos = parent.map(pos, 0, groupings.size(), graphx1, graphx2-(100*scaleFactor));
			centers.add(new Float(groupXPos));

			parent.fill(254);
			parent.textAlign(parent.CENTER);
			parent.text(group, groupXPos, groupYPos);
			parent.textAlign(parent.LEFT);
			float distBtwSubGroups = centers.get(0) - graphx1;			
			
			int sb_pos = 0;
			float TestclusterBg =  centers.get(0)-((float)distBtwSubGroups/(float)4);
			float TestclusterEd =  centers.get(0)+((float)distBtwSubGroups/(float)4);
			float bar_width = parent.map(1, 0, subGroups.size(), TestclusterBg, TestclusterEd); 
			bar_width = (float)(bar_width/(float)subGroups.size());
			for(Subgrouping sg : subGroups){
				if(sg != null && group != null){
					float clusterBg =  centers.get(pos-1)-((float)distBtwSubGroups/(float)4);
					float clusterEd =  centers.get(pos-1)+((float)distBtwSubGroups/(float)4);
					float barx1 = parent.map(sb_pos, 0, subGroups.size(), clusterBg, clusterEd);
					float bary1 = yUnitLabelMin;
					if(groupsToSubgroupsToValues.get(group).get(sg) != null){
						bary1 = parent.map(groupsToSubgroupsToValues.get(group).get(sg), yUnitLabelMin, yUnitLabelMax, graphy2, graphy1);
					}
					
					parent.fill(sg.color);
					parent.noStroke();					
					parent.rect(barx1, bary1, barx1+bar_width, graphy2);
					parent.stroke(170);
				}				
				sb_pos+=1;

			}
			
			
			
			pos++;
		}
		int sg_pos = 0;
		for(Subgrouping sg : subGroups){
			float sgXPos = parent.map(sg_pos, 0, subGroups.size(), graphx1, graphx2);
			float sgYPos = outliney2 - 10*scaleFactor;
			parent.fill(sg.color);
			parent.rect(sgXPos, sgYPos-(6*scaleFactor), sgXPos+(6*scaleFactor), sgYPos);
			parent.fill(254);
			parent.text(sg.label,sgXPos+(6*scaleFactor)+4,sgYPos);
			sg_pos++;
		}
		
		
	}
	public void insertGrouping(String label){
		if(!groupings.contains(label)){
			groupings.add(label);
			groupsToSubgroupsToValues.put(label, new HashMap<Subgrouping,Float>());
		}
	}
	
	public void insertSubGroup(String subGroupLabel, int color){
		for(String group : groupings){
			Subgrouping sub = new Subgrouping(subGroupLabel, color);
			if(!subGroups.contains(sub)){
				subGroups.add(sub);
				groupsToSubgroupsToValues.get(group).put(sub, (float) 0);
			}
		}
		
	}
	
	public void resetGraph(){
		yUnitLabelMin = 0;
		yUnitLabelMax = 100; 
		groupings.clear();
		subGroups.clear();
		groupsToSubgroupsToValues.clear();
	}
	
	public void setDataGroupToSubGroup(Object group, Object subgroup, float value){
		// TODO for every case in the state calc the statistics and display them
		// gather the maxium y value and set it here...
		
/*		Subgrouping sb = new Subgrouping(subgroup.label(),0);
		int pos = 0;
		for(Subgrouping  s : subGroups){
			// TODO do a switch statement to determine what value goes to the right column
			
			if(s.equals(sb)){				
				break;
			}
			pos++;
		}
		groupsToSubgroupsToValues.get(group.label()).put(subGroups.get(pos), value);
		
*/
	}
	
	public void setAxisYInterval(float f){
		yInterval = f;
	}
	
	
	
	
	private String FormatDataValue(String original, int places)
	{
		if(original.contains(".")){
			String[] sp = original.split("\\.");
			String leading = sp[0];
			String ending = sp[1];
			if(places > ending.length()){
				for(int i = 0; i < places; i++){
					ending.concat("0");
				}
				return String.format("%s.%s", leading, ending);
			}
			else{
				String roundingD = "";
				if(ending.length()>places+2){
					roundingD = ending.substring(places+1, places+2);
				}
				else{
					roundingD = ending.substring(ending.length()-1,ending.length());
				}
				
				if(places+2 == ending.length()){
					int d = Integer.parseInt(roundingD);				
					ending = ending.substring(0,places);
					ending.concat(String.valueOf(d));	
					if(places == 0){
						return String.format("%s%s", leading,ending);
					}
					else{
						return String.format("%s.%s", leading,ending);
					}
					
				}
				else{
					int d = Integer.parseInt(roundingD);	
					String hintD = "";
					if(ending.length() > places+2){
						hintD = ending.substring(places+2,places+3);
					}
					else{
						hintD = ending.substring(ending.length()-1, ending.length());
					}
							
					int h = Integer.parseInt(hintD);
					if(h >= 5){
						d++;
					}
					ending = ending.substring(0,places);
					ending.concat(String.valueOf(d));	
					if(places == 0){
						return String.format("%s%s", leading,ending);
					}
					else{
						return String.format("%s.%s", leading,ending);
					}
					
				}
			}
		}
		else{
			if(places > 0){
				original.concat(".");
			}			
			for(int i = 0; i < places; i++){
				original.concat("0");
			}
			return original;
		}
	
	}
	
}
