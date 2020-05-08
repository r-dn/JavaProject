import java.awt.Color;
import java.awt.Graphics2D;

public class Spike {

	public double x1, y1, x2, y2, y3;
	public double spikeHeight; // hoogte van de spike --> zullen we mee moeten experimenteren
	public double spikeWidth;

	public Spike(double x1, double y1, double x2, double y2, double y3, double spikeHeight, double spikeWidth) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.y3 = y3;
		this.spikeHeight = spikeHeight;
		this.spikeWidth = spikeWidth;
	}
	
	
	
	public void draw(Graphics2D g2D) {
		int polygonX[] = {(int) x1, (int) (x2), (int) (x1+spikeWidth/2)};
		int polygonY[] = {(int) y1, (int) (y2), (int) (y3)};
		g2D.setColor(Color.DARK_GRAY);	//Kleur mag je veranderen naar wat je wil
		g2D.fillPolygon(polygonX, polygonY, 3); //3 staat voor hoeveel hoeken bY tHe WaY 
	}
}

