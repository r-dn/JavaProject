// 15/3/20


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Bike {
	public Wheel front, back;
	public Color frameColor;
	
	public double size() {	// de grootte van de fiets (eig het frame) is de afstand tussen de wielen
		return Math.sqrt((front.x - back.x)*(front.x - back.x) + (front.y - back.y)*(front.y - back.y));
	}
	
	public double tilt() { // in rad, tilt > 0 als het voorwiel hoger staat dan het achterwiel
		return Math.atan2((front.y-back.y),(front.x-back.x));
	}
	
	public Bike(Wheel front, Wheel back, Color color) {
		this.front = front;
		this.back = back;
		this.frameColor = color;
	}
	
	public void rotateAroundFront(double angle) {
		double rho = size();
		double phi = tilt();
		
		back.x -= -rho*2*Math.sin(phi+angle/2)*Math.sin(angle/2);
		back.y -= rho*2*Math.cos(phi+angle/2)*Math.sin(angle/2);
		
	}
	
	public void rotateAroundBack(double angle) {
		double rho = size();
		double phi = tilt();
		
		front.x -= -rho*2*Math.sin(phi+angle/2)*Math.sin(angle/2);
		front.y -= rho*2*Math.cos(phi+angle/2)*Math.sin(angle/2);
		
	}
	
	public void draw(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		front.draw(g);
		back.draw(g);
		
		double d0 = size();
		double phi = tilt();
		
		double d1 = (1.25* back.radius);
		double pedalx = back.x + d1*Math.cos(phi);
		double pedaly = back.y + d1*Math.sin(phi);
		
		double d2 = d0/2;
		double saddlex = pedalx + d2*Math.sin(phi-0.3);
		double saddley = pedaly - d2*Math.cos(phi-0.3);
		
		double d3 = (d0-d1);
		double steeringx = saddlex + d3*Math.cos(phi);
		double steeringy = saddley + d3*Math.sin(phi);
		
		g2D.setColor(frameColor);
		g2D.setStroke(new BasicStroke(8.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
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
