import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

// 10/4/2020

public class SpeedSlider extends Slider {
	public double maxSpeed, currentSpeed;
	
	private Color color;

	public SpeedSlider(double x, double y, double width, double height, double maxSpeed) {
		super(x, y, width, height, 0);
		this.maxSpeed = maxSpeed;
		this.currentSpeed = 0;
		
		color = new Color(0.0f, 1.0f, 0.0f);
	}

	public void setCurrentSpeed(double currentSpeed) {
		this.currentSpeed = currentSpeed;
		this.current = currentSpeed/maxSpeed;
		
		// color gradient
		if (current <= 0.5) {
			color = new Color(2*(float) current, 1.0f, 0.0f);
		} else {
			color = new Color(1.0f, (float) (1 - current)*2, 0.0f);
		}
		
		
	}
	
	
	public void draw(Graphics2D g2D) {
		super.draw(g2D, color);
		
		g2D.setFont(new Font(Font.MONOSPACED,  Font.BOLD, 15));
		g2D.drawString("Speed: "+(int) Math.round(currentSpeed), (float) x+5, (float) y+17); 
	}
}
