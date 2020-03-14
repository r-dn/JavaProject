// 14/3/20


import java.awt.Color;
import java.awt.Graphics;

public class Wheel {
	public double x,y,radius;			// het centrum en de straal van het wiel
	public int spokes;					// het aantal spaken
	Color color;						// de kleur van het wiel
	public double angularVelocity; 		// de rotatiesnelheid van het wiel, in rad/s
	public double theta;				// de hoek tussen twee spaken
	public double phase;

	
	
	public double[][] spokePositions() {
		double [][] spokePositions = new double[spokes][2];
		for (int i = 0; i < spokes; i++) {
			spokePositions[i][0] = x + radius*Math.cos(i*theta+phase);
			spokePositions[i][1] = y + radius*Math.sin(i*theta+phase);
		}
		return spokePositions;
	}
	
	public Wheel(double x , double y, double radius, int spokes, double angularVelocity, Color color) {
		this.x = x;
		this.y = y;
		this.radius = radius;
		this.angularVelocity = angularVelocity;
		this.color = color;
		
		theta = 2*Math.PI/spokes;
		phase = 0;
	}
	
	public void draw(Graphics g) {
		g.setColor(color);
		g.drawOval((int) Math.round(x-radius), (int) Math.round(y-radius), (int) Math.round(2*radius), (int) Math.round(2*radius));
		System.out.println(phase);
		double[][] spokePositions = spokePositions();
		for (double[] coordinate : spokePositions) {
			int x2 = (int) Math.round(coordinate[0]);
			int y2 = (int) Math.round(coordinate[1]);
			g.drawLine((int) Math.round(x), (int) Math.round(y), x2, y2);
		}
	}
	
	public void update(int period) {
		double deltaphi = angularVelocity*period; // de hoek waarmee we de spaken draaien
		phase += deltaphi;;
	}
	
}
