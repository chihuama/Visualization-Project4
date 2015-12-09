


public class Subgrouping {
	String label;
	int color = 0;
	public Subgrouping(String label, int color){
		this.label = label;
		this.color = color;
	}
	public String getLabel(){
		return label;
	}
	public int getColor(){
		return color;
	}
	@Override public boolean equals(Object aThat) {
		if ( !(aThat instanceof Subgrouping) ) return false;		
		Subgrouping that = (Subgrouping)aThat;
		return this.label.equals(that.label);		
	}
}
