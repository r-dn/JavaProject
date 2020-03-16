import java.awt.Color;
import java.awt.Graphics2D;

// 15/3/20

/* Het landschap
 * Op dit moment bestaat het landschap uit niet maar dan en fiets en de weg (enkele random lijnsegmenten aan elkaar)
 */
public class Landscape {
	private static final int LOAD = 6;						// hoeveel lijnsegmenten er geladen moeten worden
	private static final int LIMIT = 200;					// hoeveel elk lijnsegment max mag stijgen of dalen
	public LineSegment[] lines = new LineSegment[LOAD];		// de lijnsegmenten
	public Bike bike;										// de fiets
	public int current, current2; 							// pointers, geven aan welk(e) lijnsegment(en) van 'lines' momenteel onder de fiets zijn
	public int length; 										// de lengte van elk segment
	public double speed;									// de (horizontale) snelheid van de fiets, in pixels/s
	
	public Landscape(Bike bike, int frameWidth) {
		this.bike = bike;
		
		// Dit lijkt me een goede lengte
		length = frameWidth/4;
		
		// De lijnsegmenten genereren
		// Het eerste segment is horizontaal
		lines[0] = new LineSegment(0, bike.back.y+bike.back.radius, length, bike.back.y+bike.back.radius);
		for (int i = 1; i < LOAD; i++) {
			lines[i] = LineSegment.random(lines[i-1].x2, lines[i-1].y2, length, LIMIT);
		}
		
		// In het begin is sowieso het eerste segment onder de fiets
		current = 0;
		current2 = 0;
		
		// De snelheid van het landschap kan gehaald worden uit rotatiesnelheid van de wielen
		speed = bike.back.angularVelocity*bike.back.radius*Math.PI;
	}
	
	public void update(int period) {
		
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
			current = Math.floorMod(current + 1, LOAD);
		}
		// idem, maar met het voorwiel
		if (lines[current].x2 < bike.front.x) {
			current2 = Math.floorMod(current + 1,LOAD);
		}
		
		// Als een lijnsegment zich niet meer op het scherm bevindt, kunnen we het verwijderen en een nieuw segment voorbereiden
		if (lines[Math.floorMod(current-1,LOAD)].x2 < -5) {
			double newx1 = lines[Math.floorMod(current-2,LOAD)].x2;
			double newy1 = lines[Math.floorMod(current-2,LOAD)].y2;
			lines[Math.floorMod(current-1,LOAD)] = LineSegment.random(newx1, newy1, length, LIMIT);
		}
		
		// fiets updaten
		// We roteren de fiets rond het voorwiel zodat het voorwiel de weg (lines[current2]) raakt
		double newbikefronty = lines[current2].heightAt(bike.front.x);
		double angle = Math.asin(((newbikefronty - bike.front.y-bike.front.radius)/bike.size()));
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
