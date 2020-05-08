import java.awt.Color;
import java.awt.Graphics2D;

public class Spike {

	public double x1, y1, x2, y2, x3, y3;
	public LineSegment base; // de basis van de driehoek
	public double spikeHeight; // hoogte van de spike --> zullen we mee moeten experimenteren

	public Spike(double x1, double y1, double x2, double y2, double spikeHeight) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		base = new LineSegment(x1, y1, x2, y2, false);
		this.x3 = (x1+x2)/2 + spikeHeight*Math.sin(base.tilt());
		this.y3 = (y1+y2)/2 - spikeHeight*Math.cos(base.tilt());
		this.spikeHeight = spikeHeight;
	}
	
	
	
	public void draw(Graphics2D g2D) {
		int polygonX[] = {(int) x1, (int) (x2), (int) (x3)};
		int polygonY[] = {(int) y1, (int) (y2), (int) (y3)};
		g2D.setColor(Color.DARK_GRAY);	//Kleur mag je veranderen naar wat je wil
		g2D.fillPolygon(polygonX, polygonY, 3); //3 staat voor hoeveel hoeken bY tHe WaY 
	}
}

