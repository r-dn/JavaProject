import java.awt.Color;
import java.awt.Graphics2D;

// 15/3/20


public class Landscape {
	private static final int LOAD = 6;
	private static final int LIMIT = 100;
	public LineSegment[] lines = new LineSegment[LOAD];
	public Bike bike;
	public int current, current2; 		// geeft aan welk(e) lijnsegment(en) van 'lines' momenteel onder de fiets zijn
	public int length; 					// de lengte van elk segment
	
	public Landscape(Bike bike, int frameWidth) {
		length = frameWidth/4;
		lines[0] = new LineSegment(0, bike.back.y+bike.back.radius, length, bike.back.y+bike.back.radius);
		for (int i = 1; i < LOAD; i++) {
			lines[i] = LineSegment.random(lines[i-1].x2, lines[i-1].y2, length, LIMIT);
		}
		current = 0;
		current2 = 0;
		this.bike = bike;
	}
	
	public void update(int period) {
		double speed = bike.back.angularVelocity*bike.back.radius;
		
		double deltax = period*speed/1000;
		double currentSlope = lines[current].slope();
		double deltay = -currentSlope*deltax;
		
		for (LineSegment line : lines) {
			line.x1 -= (deltax);
			line.x2 -= (deltax);
			line.y1 += (deltay);
			line.y2 += (deltay);
		}
		
		if (lines[current].x2 < bike.back.x) {
			current = Math.floorMod(current + 1, LOAD);
		}
		if (lines[current].x2 < bike.front.x) {
			current2 = Math.floorMod(current + 1,LOAD);
		}
		
		if (lines[Math.floorMod(current-1,LOAD)].x2 < -5) {
			double newx1 = lines[Math.floorMod(current-2,LOAD)].x2;
			double newy1 = lines[Math.floorMod(current-2,LOAD)].y2;
			lines[Math.floorMod(current-1,LOAD)] = LineSegment.random(newx1, newy1, length, LIMIT);
		}
		
		// fiets updaten
		double newbikefronty = lines[current2].heightAt(bike.front.x);
		double angle = Math.asin(((newbikefronty - bike.front.y-bike.front.radius)/bike.size()) % Math.PI/2);
		bike.rotateAroundBack(-angle);
		bike.update(period);
	}
	
	public void draw(Graphics2D g2D) {
		for (LineSegment line : lines) {
			line.draw(g2D);
		}
		
		bike.draw(g2D);
	}
	
}
