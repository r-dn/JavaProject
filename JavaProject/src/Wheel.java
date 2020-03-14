import java.awt.Color;
import java.awt.Graphics;
import java.lang.reflect.Array;

public class Wheel {
	public double x,y,radius;			// het centrum en de straal van het wiel
	public int spokes;					// het aantal spaken
	Color color;						// de kleur van het wiel
	public double angularVelocity; 		// de rotatiesnelheid van het wiel, in rad/s
	private double[][] spokePositions;	// een array van de coordinaten van de eindpunten van elke spaak op de velg
	
	public Wheel(double x , double y, double radius, int spokes, double angularVelocity, Color color) {
		this.x = x;
		this.y = y;
		this.radius = radius;
		this.angularVelocity = angularVelocity;
		this.color = color;
		
		spokePositions = new double[spokes][2];
		for (int i = 0; i < spokes; i++) {
			spokePositions[i][0] = x + radius*Math.cos(2*Math.PI*i/spokes);
			spokePositions[i][1] = y + radius*Math.sin(2*Math.PI*i/spokes);
		}
		
	}
	
	public void draw(Graphics g) {
		
		g.setColor(color);
		g.drawOval((int) Math.round(x-radius), (int) Math.round(y-radius), (int) Math.round(2*radius), (int) Math.round(2*radius));
		for (int i = 0; i < spokes; i++) {
			g.drawLine((int) Math.round(x), (int) Math.round(y), (int) Math.round(spokePositions[i][0]), (int) Math.round(spokePositions[i][1]));
		}
	}
	
	public void update(int periode) {
		double phi = angularVelocity*periode; // de hoek waarmee we de spaken draaien
		for (int i = 0; i < spokes; i++) {
			spokePositions[i][0] = x + radius*Math.cos(2*Math.PI*i/spokes + phi);
			spokePositions[i][0] = y + radius*Math.sin(2*Math.PI*i/spokes + phi);
		}
	}
}
