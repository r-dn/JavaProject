import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class SpeedSlider extends Slider {
	public double maxSpeed;
	public double currentSpeed;
	
	private Color color;

	public SpeedSlider(double x, double y, double width, double height, double maxSpeed) {
		super(x, y, width, height, 0);
		this.maxSpeed = maxSpeed;
		this.currentSpeed = 0;
		
		color = Color.orange;
	}

	public void setCurrentSpeed(double currentSpeed) {
		this.currentSpeed = currentSpeed;
		super.current = currentSpeed/maxSpeed;
	}
	
	
	public void draw(Graphics2D g2D) {
		super.draw(g2D, color);
		
		g2D.setFont(new Font(Font.MONOSPACED,  Font.BOLD, 15));
		g2D.drawString("Speed: "+(int) Math.round(currentSpeed), (float) x+5, (float) y+17); 
	}

}
