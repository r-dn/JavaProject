import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

// 10/4/2020

public class EnergySlider extends Slider {
	public double startEnergy;
	public double currentEnergy;
	
	private Color color;

	public EnergySlider(double x, double y, double width, double height, double startEnergy) {
		super(x, y, width, height, 0);
		this.startEnergy = startEnergy;
		this.currentEnergy = 0;
		
		color = new Color(0.0f, 1.0f, 0.0f);
	}

	public void setCurrentEnergy(double currentEnergy) {
		this.currentEnergy = currentEnergy;
		this.current = currentEnergy/startEnergy;
		
		// color gradient
		if (current >= 0.5 && current <= 1) {
			color = new Color(2*(float) (1 - current), 1.0f, 0.0f);
		} else if (current <= 0.5 && current >= 0) {
			color = new Color(1.0f, 2*(float) current, 0.0f);
		}
		
		
	}
	
	public void draw(Graphics2D g2D) {
		super.draw(g2D, color);
		
		g2D.setFont(new Font(Font.MONOSPACED,  Font.BOLD, 15));
		g2D.drawString("Energy: "+(int) Math.round(currentEnergy), (float) x+5, (float) y+17); 
	}
}
