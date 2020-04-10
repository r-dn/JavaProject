import java.awt.Font;
import java.awt.Graphics2D;

// 10/4/2020

public class SpeedSlider extends Slider {
	public double maxSpeed, currentSpeed;

	public SpeedSlider(double x, double y, double width, double height, double maxSpeed, double currentSpeed) {
		super(x, y, width, height, currentSpeed/maxSpeed);
		this.maxSpeed = maxSpeed;
		this.currentSpeed = currentSpeed;
	}

	public void setCurrentSpeed(double currentSpeed) {
		this.currentSpeed = currentSpeed;
		this.current = currentSpeed/maxSpeed;
	}
	
	@Override
	public void draw(Graphics2D g2D) {
		super.draw(g2D);
		
		g2D.setFont(new Font(Font.MONOSPACED,  Font.BOLD, 15));
		g2D.drawString("Speed: "+(int) Math.round(currentSpeed), (float) x+5, (float) y+17); 
	}
}
