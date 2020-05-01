// 15/3/20


import java.awt.Color;

// Voor als je een standaard fiets wil maken zonder u te moeten bezighouden met de wielen etc
public class DefaultBike extends Bike {
	public double maxSpeed;
	public double efficiency;
	public double jumpPower;
	
	public DefaultBike(double x, double y, double size, double tilt, double beginSpeed, double maxSpeed, double efficiency, double jumpPower) {
		super(
				new Wheel(x + size*Math.cos(tilt), y - size*Math.sin(tilt), size/3, 16, beginSpeed/(size/3), new Color(80, 80, 80)), 
				new Wheel(x, y, size/3, 16, beginSpeed/(size/3), new Color(80, 80, 80)), 
				new Color(60,60,80)
				);
		this.maxSpeed = maxSpeed;
		this.efficiency = efficiency;
		this.jumpPower = jumpPower;
	}
}
