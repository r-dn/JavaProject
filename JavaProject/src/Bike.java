// 15/3/20


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Bike {
	public Wheel front, back; // het voor- en achterwiel
	public Color frameColor; 
	
	
	private int strokeWidth;
	private BasicStroke frameStroke;
	
	
	// De grootte van de fiets (eig het frame) is de afstand tussen de wielen
	public double size() {
		return Math.sqrt((front.x - back.x)*(front.x - back.x) + (front.y - back.y)*(front.y - back.y));
	}
	
	// tilt() geeft aan hoe schuin de fiets staat
	public double tilt() {
		// We gebruiken de functie atan2 omdat atan (boogtangens) niet deftig werkt
		return Math.atan2((front.y-back.y),(front.x-back.x));
	}
	
	public Bike(Wheel front, Wheel back, Color color) {
		this.front = front;
		this.back = back;
		this.frameColor = color;
		
		strokeWidth = (int) front.radius/30;
		frameStroke = new BasicStroke(4*strokeWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
	}
	
	
	public void rotateAroundFront(double angle) {
		double rho = size();
		double tilt = tilt();
		
		// Het voorwiel blijft staan, dus het achterwiel moet draaien
		// zie formules van simpson
		back.x -= -rho*2*Math.sin(tilt-angle/2)*Math.sin(angle/2);
		back.y -= rho*2*Math.cos(tilt-angle/2)*Math.sin(angle/2);
		
	}
	
	public void rotateAroundBack(double angle) {
		double rho = size();
		double tilt = tilt();
		
		// idem
		front.x -= -rho*2*Math.sin(tilt-angle/2)*Math.sin(angle/2);
		front.y -= rho*2*Math.cos(tilt-angle/2)*Math.sin(angle/2);
		
	}
	
	public void draw(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		
		// wielen
		front.draw(g);
		back.draw(g);
		
		// frame
		double d0 = size();
		double tilt = tilt();
		
		double d1 = (1.25*back.radius);
		double pedalx = back.x + d1*Math.cos(tilt);
		double pedaly = back.y + d1*Math.sin(tilt);
		
		double d2 = d0/2;
		double saddlex = pedalx + d2*Math.sin(tilt-0.3);
		double saddley = pedaly - d2*Math.cos(tilt-0.3);
		
		double d3 = (d0-d1);
		double steeringx = saddlex + d3*Math.cos(tilt);
		double steeringy = saddley + d3*Math.sin(tilt);
		
		g2D.setColor(frameColor);
		g2D.setStroke(frameStroke);
		g2D.drawLine((int) back.x, (int) back.y, (int) pedalx, (int) pedaly);
		g2D.drawLine((int) pedalx, (int) pedaly, (int) saddlex, (int) saddley);
		g2D.drawLine((int) saddlex, (int) saddley, (int) back.x, (int) back.y);
		g2D.drawLine((int) saddlex, (int) saddley, (int) steeringx, (int) steeringy);
		g2D.drawLine((int) pedalx, (int) pedaly, (int) steeringx, (int) steeringy);
		g2D.drawLine((int) front.x, (int) front.y, (int) steeringx, (int) steeringy);
	}
	
	public void update(int period) {
		front.update(period);
		back.update(period);
	}
	
}
