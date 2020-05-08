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
	public Coin coin;
	public Spike spike;

	private static final BasicStroke stroke = new BasicStroke(100.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
	private static final Color groundColor = new Color(120, 100, 60);
	public static final Color skyColor = new Color(44, 52, 210);
	private static final Color roadColor = new Color(34, 177, 76);
	private static final Color veryDarkGreen = new Color(0, 102, 0);
	private static final Color darkGreen = new Color(0, 153, 0);
	private static final Color lightGreen = new Color(0, 255, 51);


	
	

	private Bush bush1 = new Bush(500,500,40,40,15,veryDarkGreen);

	private Bush bush2;
	private Bush bush3;

	public LineSegment(double x1, double y1, double x2, double y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		Random rng = new Random();
		if (rng.nextDouble() < 0.1) {
			this.coin = new Coin(x1, y1 - Main.screenHeight / 8);
		}

		if (rng.nextDouble() < 0.1) {
			this.spike = new Spike(x1, y1, x1+50/2*Math.cos(this.tilt()*Math.PI/3), y1-50/2*Math.cos(this.tilt()*Math.PI/3), this.heightAt(x1+50/2), 50, 50);
		}
		if (rng.nextDouble() < 0.1) {

		/*if (rng.nextDouble() < 0.1) {
>>>>>>> branch 'master' of https://github.com/r-dn/JavaProject.git
			this.bush1 = new Bush(x1, y1 - Main.screenHeight / 6, 40, 40, 15, veryDarkGreen);
		}*/
		if (rng.nextDouble() < 0.2) {
			this.bush2 = new Bush(x1, y1 - Main.screenHeight / 6, 40, 20, 20, darkGreen);
		}
		if (rng.nextDouble() < 0.3) {
			this.bush3 = new Bush(x1, y1 - Main.screenHeight / 6, 50, 50, 10, lightGreen);
		}
		}
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
		g2D.setColor(Color.darkGray); // vroeger: new Color(100, 100, 100)
		g2D.setStroke(stroke);
		g2D.drawLine((int) Math.round(x1), (int) Math.round(y1), (int) Math.round(x2), (int) Math.round(y2));
	}

	public void shift(double deltax, double deltay) {
		x1 += (deltax);
		x2 += (deltax);
		y1 += (deltay);
		y2 += (deltay);

		if (coin != null) {
			coin = new Coin(x1, y1 - Main.screenHeight / 8);
		}
		if (spike != null) {
			this.spike = new Spike(x1, y1, x1+50/2*Math.cos(this.tilt()+Math.PI/3), y1-50/2*Math.cos(this.tilt()+Math.PI/3), this.heightAt(x1+50/2), 50, 50);
		}
		if (bush1 != null) {
			bush1 = new Bush(x1, y1 - Main.screenHeight / 6, 40, 40, 15, veryDarkGreen);
		}
		if (bush2 != null) {
			bush2 = new Bush(x1, y1 - Main.screenHeight / 6, 40, 20, 20, darkGreen);
		}
		if (bush3 != null) {
			bush3 = new Bush(x1, y1 - Main.screenHeight / 6, 50, 50, 10, lightGreen);
		}

	}

	public void drawWithBackground(Graphics2D g2D) {
		// De kleur en breedte van het lijnsegment kan natuurlijk aangepast worden
		int segmentWidth = (int) (x2 - x1 + 1);

		// Grond inkleuren
		g2D.setColor(groundColor);
		g2D.fillRect((int) x1, (int) y1, (int) segmentWidth, (int) Main.screenHeight - (int) y1);
		// Weg inkleuren
		g2D.setColor(roadColor);
		g2D.setStroke(stroke);
		g2D.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
		
		bush1.draw(g2D);
		
		if (coin != null) {
			coin.draw(g2D);
		}
		if (spike != null) {
			spike.draw(g2D);
		}
		if (bush1 != null) {
			bush1.draw(g2D);

		}
		if (bush2 != null) {
			bush2.draw(g2D);

		}
		if (bush3 != null) {
			bush3.draw(g2D);
		}

	}
}
