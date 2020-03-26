import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyListener;

// 15/3/20

/* Het landschap
 * Op dit moment bestaat het landschap uit niet maar dan en fiets en de weg (enkele random lijnsegmenten aan elkaar)
 */
public class Landscape {
	private static final int load = 25;						// hoeveel lijnsegmenten er geladen moeten worden
	private static final double limit = 0.05;					// hoeveel elk lijnsegment max mag stijgen of dalen
	private static final double maxTilt = Math.PI/4;
	private static final int increment = 200;
	private static final int maxSpeed = 1600;
	
	public LineSegment[] lines = new LineSegment[load];		// de lijnsegmenten
	public Bike bike;										// de fiets
	public int current, current2; 							// pointers, geven aan welk(e) lijnsegment(en) van 'lines' momenteel onder de fiets zijn
	public int length; 										// de lengte van elk segment
	public double speed;									// de (horizontale) snelheid van de fiets, in pixels/s
	
	public Landscape(Bike bike, int frameWidth) {
		this.bike = bike;
		
		// Dit lijkt me een goede lengte
		length = frameWidth/20;
		
		// De lijnsegmenten genereren
		// Het eerste segment is horizontaal
		lines[0] = new LineSegment(0, bike.back.y+bike.back.radius, length, bike.back.y+bike.back.radius);
		for (int i = 1; i < load; i++) {
			lines[i] = LineSegment.randomTilt(lines[i-1], length, limit, maxTilt);
		}
		
		// In het begin is sowieso het eerste segment onder de fiets <--- niet waar
		current = (int) Math.round(bike.back.x/length);
		current2 = current;
		
		// De snelheid van het landschap kan gehaald worden uit rotatiesnelheid van de wielen
		speed = bike.back.angularVelocity*bike.back.radius*Math.PI;
	}
	
	public void setSpeed(double speed) {
		bike.back.angularVelocity = speed/bike.back.radius/Math.PI;
		bike.front.angularVelocity = speed/bike.front.radius/Math.PI;
		this.speed = speed;
	}
	
	public void update(int period) {
	//	speed = bike.back.angularVelocity*bike.back.radius*Math.PI;
		
		// deltax en deltay geven aan met welke hoeveelheid we de lijnsegmenten moeten verplaatsen
		// deltax is afhankelijk van de snelheid (speed)
		double deltax = period*speed/1000;
		// Met deltay corrigeren we de hoogte van de lijnsegmenten zodat het achterwiel steeds de weg raakt
		double deltay = lines[current].heightAt(bike.back.x) - (bike.back.y + bike.back.radius);
		
		for (LineSegment line : lines) {
			line.x1 -= (deltax);
			line.x2 -= (deltax);
			line.y1 -= (deltay);
			line.y2 -= (deltay);
		}
		
		// Als lines[current] zich niet meer onder het achterwiel bevindt, moeten we de pointer verplaatsen
		// We gebruiken Math.floorMod() omdat de %-operator niet goed werkt met negatieve getallen
		if (lines[current].x2 < bike.back.x) {
			current = Math.floorMod(current + 1, load);
		}
		// idem, maar met het voorwiel
		if (lines[current].x2 < bike.front.x) {
			current2 = Math.floorMod(current + 1,load);
		}
		
		// Als een lijnsegment zich niet meer op het scherm bevindt, kunnen we het verwijderen en een nieuw segment voorbereiden
		
		if (lines[Math.floorMod(current-5,load)].x2 < 0) {
			lines[Math.floorMod(current-5,load)] = LineSegment.randomTilt(lines[Math.floorMod(current-6,load)], length, limit, maxTilt);
		}
		
		// fiets updaten
		// We roteren de fiets rond het voorwiel zodat het voorwiel de weg (lines[current2]) raakt
		double newbikefronty = lines[current2].heightAt(bike.front.x);
		double angle = Math.asin(((newbikefronty - bike.front.y-bike.front.radius)/bike.size()));
		bike.rotateAroundBack(-angle);
		bike.update(period);
	}
	
	public void increaseSpeed() {
		if (speed <= maxSpeed-increment) {
			setSpeed(speed + increment);
		}
	}
	
	public void decreaseSpeed() {
		if (speed >= increment) {
			setSpeed(speed - increment);
		}
	}
	
	public void draw(Graphics2D g2D) {
		for (LineSegment line : lines) {
			line.draw(g2D);
		}
		
		bike.draw(g2D);
	}
	
	
	
}
