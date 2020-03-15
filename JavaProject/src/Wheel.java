// 14/3/20


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Wheel {
	public double x,y,radius;			// het centrum en de straal van het wiel
	public int spokes;					// het aantal spaken
	Color tyreColor;					// de kleur van de band/velg
	Color spokeColor;					// de kleur van de spaken
	public double angularVelocity; 		// de rotatiesnelheid van het wiel, in rad/s, (+) voorwaarts, (-) achterwaarts
	
	private double theta;				// de hoek tussen twee spaken
	private double phase;				// de hoek waarmee het wiel gedraaid is

	
	
	public double[][] spokePositions() {
		double [][] spokePositions = new double[spokes][2];
		for (int i = 0; i < spokes; i++) {
			spokePositions[i][0] = x + radius*Math.cos(i*theta+phase);
			spokePositions[i][1] = y + radius*Math.sin(i*theta+phase);
		}
		return spokePositions;
	}
	
	public Wheel(double x , double y, double radius, int spokes, double angularVelocity, Color spokeColor) {
		this.x = x;
		this.y = y;
		this.radius = radius;
		this.spokes = spokes;		// <---- hier zat het f probleeem
		this.angularVelocity = angularVelocity;
		this.tyreColor = new Color(0);
		this.spokeColor = spokeColor;
		
		theta = 2*Math.PI/spokes;
		phase = 0;
	}
	
	public void draw(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		
		// spokes
		double[][] spokePositions = spokePositions();
		g2D.setStroke(new BasicStroke(2));
		g2D.setColor(spokeColor);
		for (double[] coordinate : spokePositions) {
			g.drawLine((int) Math.round(x), (int) Math.round(y), (int) Math.round(coordinate[0]), (int) Math.round(coordinate[1]));
		}
		
		// tyre
		g2D.setColor(tyreColor);
		g2D.setStroke(new BasicStroke(10));
		g2D.drawOval((int) Math.round(x-radius), (int) Math.round(y-radius), (int) Math.round(2*radius), (int) Math.round(2*radius));
	}
	
	public void update(int period) {
		double deltaphi = angularVelocity*period/1000; // de hoek waarmee we de spaken draaien
		phase += deltaphi;;
	}
	
}
