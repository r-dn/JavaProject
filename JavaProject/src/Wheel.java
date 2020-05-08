// 14/3/20


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/* Het wiel
 * 
 * 		Belangrijke opmerking: 	alle punten enz worden gegeven en berekend als doubles, omdat dit nauwkeuriger is
 * 								dit houdt echter in dat ze eerst afgerond en omgezet (naar int) moeten worden vooraleer ze te tekenen
 */

public class Wheel {
	public double x,y,radius;			// het centrum en de straal van het wiel
	public int spokes;					// het aantal spaken
	Color tyreColor;					// de kleur van de band/velg
	Color spokeColor;					// de kleur van de spaken
	public double angularVelocity; 		// de rotatiesnelheid van het wiel, in rad/s, (+) voorwaarts, (-) achterwaarts
	
	private double theta;				// de hoek tussen twee spaken
	private double phase;				// de hoek waarmee het wiel gedraaid is

	private int strokeWidth;
	private BasicStroke spokeStroke;
	private BasicStroke tyreStroke;
	
	
	
	public Wheel(double x , double y, double radius, int spokes, double angularVelocity, Color spokeColor) {
		this.x = x;
		this.y = y;
		this.radius = radius;
		this.spokes = spokes;
		this.angularVelocity = angularVelocity;
		this.tyreColor = new Color(0);
		this.spokeColor = spokeColor;
		
		
		theta = 2*Math.PI/spokes;
		phase = 0;
		
		strokeWidth = (int) radius/30;
		spokeStroke = new BasicStroke(strokeWidth);
		tyreStroke = new BasicStroke(5*strokeWidth);
	}
	
	
	public void setSize(double radius) {
		this.radius = radius;
		strokeWidth = (int) radius/30;
		spokeStroke = new BasicStroke(strokeWidth);
		tyreStroke = new BasicStroke(5*strokeWidth);
	}
	
	// spokePositions zijn de punten waar elke spaak aan de velg komt
	public double[][] spokePositions() {
		double [][] spokePositions = new double[spokes][2];
		for (int i = 0; i < spokes; i++) {
			spokePositions[i][0] = x + radius*Math.cos(i*theta+phase);
			spokePositions[i][1] = y + radius*Math.sin(i*theta+phase);
		}
		return spokePositions;
	}
	
	public void draw(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		
		// We tekenen eerst de spaken, dan pas de de band zodat de band over de spaken getekend is (dat is veel mooier)
		
		// spokes
		double[][] spokePositions = spokePositions();
		g2D.setStroke(spokeStroke);
		g2D.setColor(spokeColor);
		for (double[] coordinate : spokePositions) {
			g.drawLine((int) Math.round(x), (int) Math.round(y), (int) Math.round(coordinate[0]), (int) Math.round(coordinate[1]));
		}
		
		// tyre
		g2D.setColor(tyreColor);
		g2D.setStroke(tyreStroke);
		g2D.drawOval((int) Math.round(x-radius), (int) Math.round(y-radius), (int) Math.round(2*radius), (int) Math.round(2*radius));
	}
	

	// Om de x ms moeten we de spaken draaien, afhankelijk van de rotatiesnelheid (angularVelocity)
	public void update(int period) {
		double deltaphi = angularVelocity*period/(1000); // de hoek waarmee we de spaken draaien
		phase += deltaphi;
	}
	
}
