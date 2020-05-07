import java.awt.Color;
import java.awt.Graphics2D;

public class Spike {

	public double x, y;
	public double spikeHeight; // hoogte van de spike --> zullen we mee moeten experimenteren
	public double spikeWidth;

	public Spike(double x, double y, double spikeHeight, double spikeWidth) {
		this.x = x;
		this.y = y;
		this.spikeHeight = spikeHeight;
		this.spikeWidth = spikeWidth;
	}
	
	public void draw(Graphics2D g2D) {
		int polygonX[] = {(int) x, (int) (x + spikeWidth / 2), (int) (x + spikeWidth)};
		int polygonY[] = {(int) y, (int) (y - spikeHeight), (int) y};
		g2D.setColor(Color.DARK_GRAY);	//Kleur mag je veranderen naar wat je wil
		g2D.fillPolygon(polygonX, polygonY, 3); //3 staat voor hoeveel hoeken bY tHe WaY 
	}
}

