// 15/3/20

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.util.Random;

/* Een Lijnsegment
 * Dit is nodig in de klasse 'Landscape'
 * Dit object bevat het begin- en eindpunt van de lijn, en enkele handige methoden
 */
public class LineSegment {
	public double x1, y1, x2, y2;
	
	private int GroundLayer = Toolkit.getDefaultToolkit().getScreenSize().height;

	public LineSegment(double x1, double y1, double x2, double y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}

	// Dit is een andere constructor, die een willekeurig lijnsegment aanmaakt met
	// vast beginpunt en vaste lengte

	public static LineSegment random(double x1, double y1, double length, double limit) {
		Random rng = new Random();
		// limit geeft aan hoever het lijnstuk mag varieren in hoogte
		LineSegment ret = new LineSegment(x1, y1, x1 + length, y1 + rng.nextDouble() * 2 * limit - limit);
		return ret;

	}

	// Lijnsegment met een willk hoek met het vorige segment
	public static LineSegment randomTilt(LineSegment previous, double length, double maxDeltaTilt, double maxTilt) {
		Random rng = new Random();
		double deltaTilt = rng.nextGaussian() * maxDeltaTilt / 3;
		double totalTilt = previous.tilt() + deltaTilt;

		if (totalTilt >= maxTilt) {
			totalTilt = previous.tilt() - deltaTilt;
		} else if (totalTilt <= -maxTilt) {
			totalTilt = previous.tilt() - deltaTilt;
		}

		LineSegment ret = new LineSegment(previous.x2, previous.y2, previous.x2 + length,
				previous.y2 + length * Math.sin(totalTilt));
		return ret;

	}

	// de helling van het segment
	public double slope() {
		return (y2 - y1) / (x2 - x1);
	}

	public double tilt() {
		return Math.atan2(y2 - y1, x2 - x1);
	}

	// Om de hoogte op een zeker x-coordinaat te bepalen
	public double heightAt(double x) {
		return slope() * (x - x1) + y1;
	}

	public void draw(Graphics2D g2D) {
		g2D.setColor(Color.darkGray);    				// vroeger: new Color(100, 100, 100)
		g2D.setStroke(new BasicStroke(100.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		g2D.drawLine((int) Math.round(x1), (int) Math.round(y1), (int) Math.round(x2), (int) Math.round(y2));
	}
	
	public void drawWithBackground(Graphics2D g2D) {
		// De kleur en breedte van het lijnsegment kan natuurlijk aangepast worden	
		// Achtergrond blauw kleuren + grond bruin kleuren
		// TODO: De hoeken van de lucht & grond (wanneer je stilstaat) die zichtbaar zijn op de baan wegkrijgen
		int SegmentWidth = (int) (x2 - x1+1);
		g2D.setColor(new Color(51,153,255));
		g2D.fillRect((int) x1,(int) 0,(int) SegmentWidth,(int) y1);
		
		// TODO: Andere manier vinden voor de grond in te kleuren + laten fluctueren (nu is er soms teveel ingekleurd = onnodig gebruik van geheugen)
		g2D.setColor(new Color(102,51,0));
		g2D.fillRect((int) x1,(int) y1,(int) SegmentWidth,(int) GroundLayer);
		
		
		g2D.setColor(Color.GREEN);    				// vroeger: new Color(100, 100, 100)
		g2D.setStroke(new BasicStroke(100.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		g2D.drawLine((int) Math.round(x1), (int) Math.round(y1), (int) Math.round(x2), (int) Math.round(y2));
	}
	

}
