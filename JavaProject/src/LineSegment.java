// 15/3/20


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

public class LineSegment {
	public double x1, y1, x2, y2;
	
	public LineSegment(double x1, double y1, double x2, double y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}
	
	
	// je kan in Java geen constructors met dezelfde parameters hebben dus is dit een static functie, maar eigenlijk moet het gezien worden als een constructor
	public static LineSegment random(double x1, double y1, double length, double limit) {
		Random rng = new Random();
		LineSegment ret = new LineSegment(x1, y1, x1 + length, y1 + rng.nextDouble()*2*limit-limit);
		return ret;
		
	}
	
	public double slope() {
		return (y2-y1)/(x2-x1);
	}
	
	public double heightAt(double x) {
		return slope()*(x-x1) + y1;
	}
	
	public void draw(Graphics2D g2D) {
		g2D.setColor(new Color(100,100,100));
		g2D.setStroke(new BasicStroke(100.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		g2D.drawLine((int) Math.round(x1), (int) Math.round(y1), (int) Math.round(x2), (int) Math.round(y2));
	}
	
	
}
