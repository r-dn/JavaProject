// 15/3/20


import java.awt.Color;

// Voor als je een standaard fiets wil maken zonder u te moeten bezighouden met de wielen etc
public class DefaultBike extends Bike {
	
	public DefaultBike(double x, double y, double size, double tilt) {
		super(
				new Wheel(x + size*Math.cos(tilt), y - size*Math.sin(tilt), size/3, 16, 0, new Color(80, 80, 80)), 
				new Wheel(x, y, size/3, 16, 0, new Color(80, 80, 80)), 
				new Color(60,60,80));
		
	}
}
