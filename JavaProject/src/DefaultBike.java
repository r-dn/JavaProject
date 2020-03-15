// 15/3/20


import java.awt.Color;

public class DefaultBike extends Bike {
	
	public DefaultBike(double x, double y, double size, double tilt, double angularVelocity) {
		super(new Wheel(x + size*Math.cos(tilt), y - size*Math.sin(tilt), size/3, 16, angularVelocity, new Color(80, 80, 80)), 
				new Wheel(x, y, size/3, 16, angularVelocity, new Color(80, 80, 80)), 
				new Color(60,60,80));
		
		double r = size/3;
		
		Wheel front = new Wheel(x + size*Math.cos(tilt), y + size*Math.sin(tilt()), r, 12, angularVelocity, new Color(80, 80, 80));
		Wheel back = new Wheel(x, y, r, 12, angularVelocity, new Color(80, 80, 80));
		
		
	}
}
