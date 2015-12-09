import java.util.Date;


public class WeatherUnit {
	public Date date;
	private Integer weatherId;
	public String weatherDesc;
	private Integer windId;
	private String windDesc;
	public int windSpeed = 1;
	public WeatherUnit(Date date, Integer weatherId, String weatherDesc, Integer windId, String windDesc, int windSpeed){
		this.date = date;
		this.setWeatherId(weatherId);
		this.weatherDesc = weatherDesc;
		this.windId = windId;
		this.windDesc = windDesc;
		this.windSpeed = windSpeed;
	}
	
	public float getWindAngle(){
		float ret = 0;
		switch(windId){
		case 1:
			// north
			ret = (float)(3f*Math.PI/2f);
			break;
		case 2:
			// east
			ret = (float) Math.PI;
			break;
		case 3:
			// west
			ret = (float) 0;
			break;
		case 4:
			// NW
			ret = (float) (7f*Math.PI/4f);
			break;
		case 5:
			// SE
			ret = (float) (3f*Math.PI/4f);
			break;
		case 6:
			// NNW
			ret = (float) (5f*Math.PI/3f);
			break;
		case 7:
			// WNW
			ret = (float) (11f*Math.PI/6f);
			break;
		}
		return ret;
	}
	
	public String getWeatherFile(){
		String file = "";
		switch(this.getWeatherId()){
		case 1:
			// clear
			file = "clear.png";
			break;
		case 2:
			file = "cloudy.png";
			// cloudy
			break;
		case 3:
			file = "drizzle.png";
			// showers
			break;
		case 4:
			file = "rain.png";
			// rain
			break;			
		}
		return file;
	}

	public Integer getWeatherId() {
		return weatherId;
	}

	public void setWeatherId(Integer weatherId) {
		this.weatherId = weatherId;
	}
	
}
