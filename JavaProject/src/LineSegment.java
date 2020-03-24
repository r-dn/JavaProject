// 15/3/20


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;


/* Een Lijnsegment
 * Dit is nodig in de klasse 'Landscape'
 * Dit object bevat het begin- en eindpunt van de lijn, en enkele handige methoden
 */
public class LineSegment {
	public double x1, y1, x2, y2;
	
	public LineSegment(double x1, double y1, double x2, double y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}
	
	// Dit is een andere constructor, die een willekeurig lijnsegment aanmaakt met vast beginpunt en vaste lengte
	// Jammer genoeg kan je in Java geen constructors met hetzelfde type parameters hebben
	// Dit is dus een static functie, maar eigenlijk moet het gezien worden als een constructor
	public static LineSegment random(double x1, double y1, double length, double limit) {
		Random rng = new Random();
		// limit geeft aan hoever het lijnstuk mag varieren in hoogte
		LineSegment ret = new LineSegment(x1, y1, x1 + length, y1 + rng.nextDouble()*2*limit-limit);
		return ret;
		
	}
	
	public static LineSegment randomTilt(LineSegment previous, double length, double maxDeltaTilt, double maxTilt) {
		Random rng = new Random();
		double deltaTilt = rng.nextDouble()*maxDeltaTilt*2-maxDeltaTilt;
		double totalTilt = previous.tilt() + deltaTilt;
		
		if (totalTilt >= maxTilt) {
			totalTilt = previous.tilt() - deltaTilt;
		} else if (totalTilt <= -maxTilt) {
			totalTilt = previous.tilt() - deltaTilt;
		} 
		
		LineSegment ret = new LineSegment(previous.x2, previous.y2, previous.x2 + length, previous.y2 + length*Math.sin(totalTilt));
		return ret;
		
	}
	
	// de helling van het segment
	public double slope() {
		return (y2-y1)/(x2-x1);
	}
	
	public double tilt() {
		return Math.atan2(y2-y1, x2-x1);
	}
	
	// Om de hoogte op een zeker x-coordinaat te bepalen
	// Er wordt van uitgegaan dat x tussen x1 en x2 ligt, maar de methode werkt ook als x erbuiten ligt
	public double heightAt(double x) {
		return slope()*(x-x1) + y1;
	}
	
	public void draw(Graphics2D g2D) {
		// De kleur en breedte van het lijnsegment kan natuurlijk aangepast worden
		g2D.setColor(new Color(100,100,100));
		g2D.setStroke(new BasicStroke(100.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		g2D.drawLine((int) Math.round(x1), (int) Math.round(y1), (int) Math.round(x2), (int) Math.round(y2));
	}
	
	
}
