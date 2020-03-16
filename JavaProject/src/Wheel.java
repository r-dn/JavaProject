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

	
	
	
	
	public Wheel(double x , double y, double radius, int spokes, double angularVelocity, Color spokeColor) {
		this.x = x;
		this.y = y;
		this.radius = radius;
		this.spokes = spokes;		// <---- hier zat het f probleeem
		this.angularVelocity = angularVelocity;
		this.tyreColor = new Color(0);
		this.spokeColor = spokeColor;
		
		// De hoek tussen twee spaken is enkel afhankelijk van het aantal spaken, en dus constant
		theta = 2*Math.PI/spokes;
		
		// Op zich  maakt de beginwaarde van de fase van het wiel niet uit
		phase = 0;
	}
	
	
	/* spokePositions zijn de punten waar elke spaak aan de velg komt
	 * Deze zijn nodig om de spaken te kunnen tekenen
	 * Deze punten worden berekent uit theta en phase
	 * 
	 * Het returnt een array van coordinaten
	 */
	public double[][] spokePositions() {
		double [][] spokePositions = new double[spokes][2];
		for (int i = 0; i < spokes; i++) {
			spokePositions[i][0] = x + radius*Math.cos(i*theta+phase);
			spokePositions[i][1] = y + radius*Math.sin(i*theta+phase);
		}
		return spokePositions;
	}
	
	public void draw(Graphics g) {
		// We nemen een Graphics2D-object ipv Graphics omdat we de lijnbreedte moeten kunnen aanpassen
		Graphics2D g2D = (Graphics2D) g;
		int strokeWidth = (int) radius/30;
		
		// We tekenen eerst de spaken, dan pas de de band zodat de band over de spaken getekend is (dat is veel mooier)
		
		/* spaken tekenen
		 * Dit zijn gewoon lijnen van het centrum van het wiel naar de band (hier hebben we dus spokePositions voor nodig)
		 */
		double[][] spokePositions = spokePositions();
		g2D.setStroke(new BasicStroke(strokeWidth));
		g2D.setColor(spokeColor);
		for (double[] coordinate : spokePositions) {
			g.drawLine((int) Math.round(x), (int) Math.round(y), (int) Math.round(coordinate[0]), (int) Math.round(coordinate[1]));
		}
		
		// tyre
		g2D.setColor(tyreColor);
		g2D.setStroke(new BasicStroke(5*strokeWidth));
		g2D.drawOval((int) Math.round(x-radius), (int) Math.round(y-radius), (int) Math.round(2*radius), (int) Math.round(2*radius));
	}
	
	/*
	 * Om de x ms moeten we de spaken draaien, afhankelijk van de rotatiesnelheid (angularVelocity)
	 * Hiervoor moeten we gewoon phase aanpassen
	 * 
	 * period is het aantal milliseconden dat verstreken is
	 */
	
	public void update(int period) {
		double deltaphi = angularVelocity*period/1000; // de hoek waarmee we de spaken draaien
		phase += deltaphi;
	}
	
}
