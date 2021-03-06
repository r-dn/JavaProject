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
	public Coin coin;
	public Spike spike;

	private static final BasicStroke stroke = new BasicStroke(100.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
	private static final Color groundColor = new Color(120, 100, 60);
	public static final Color skyColor = new Color(44, 52, 210);
	private static final Color roadColor = new Color(34, 177, 76);
	public static final double spikeHeight = 50;
	private static final Color DARKGREEN = new Color(0, 153, 0);

	public Bush bush;
	

	public LineSegment(double x1, double y1, double x2, double y2, boolean hasCoin, boolean hasSpike) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		
		Random rng = new Random();
		if (rng.nextDouble() < 0.1 && hasCoin) {
			this.coin = new Coin(x1, y1 - Main.screenHeight / 8);
		}







		if (rng.nextDouble() < 0.1 && hasSpike) {




			this.spike = new Spike(x1, y1, (x1+x2)/2, (y1+y2)/2, spikeHeight);
		}
		
		if (rng.nextDouble() < 0.1 && Math.abs(slope()) <= 0.2) {
			this.bush = new Bush(x1, y1 - Main.screenHeight / 11, 40, 40, 15, DARKGREEN);
		}
	}

	// Lijnsegment met een willk hoek met het vorige segment
	public LineSegment(LineSegment previous, double length, double maxDeltaTilt, double maxTilt, boolean hasCoin, boolean hasSpike) {
		/*
		this(previous.x2, previous.y2, previous.x2 + length,
				0, hasCoin, hasSpike); 
				^^ dit gaat niet, dat gaf problemen met het aanmaken van spikes en bushes, dus moeten we toch de linesegment apart constructen
		*/
		Random rng = new Random();
		double deltaTilt = rng.nextGaussian() * maxDeltaTilt / 3;
		double totalTilt = previous.tilt() + deltaTilt;

		if (totalTilt >= maxTilt) {
			totalTilt = previous.tilt() - deltaTilt;
		} else if (totalTilt <= -maxTilt) {
			totalTilt = previous.tilt() - deltaTilt;
		}

		x1 = previous.x2;
		y1 = previous.y2;
		x2 = x1 + length;
		y2 = previous.y2 + length * Math.sin(totalTilt);
		
		if (rng.nextDouble() < 0.1 && hasCoin) {
			this.coin = new Coin(x1, y1 - Main.screenHeight / 8);
		}

		if (rng.nextDouble() < 0.1 && hasSpike) {
			this.spike = new Spike(x1, y1, (x1+x2)/2, (y1+y2)/2, spikeHeight);
		}
		
		if (rng.nextDouble() < 0.1 && Math.abs(slope()) <= 0.2) {
			this.bush = new Bush(x1, y1 - Main.screenHeight / 11, 40, 40, 15, DARKGREEN);
		}
		 
	}

	// de helling van het segment
	public double slope() {
		return (y2 - y1) / (x2 - x1);
	}
	
	public double length() {
		return Math.sqrt((x1 - x2)*(x1 - x2) + (y1 - y2)*(y1 - y2));
	}

	public double tilt() {
		return Math.atan2(y2 - y1, x2 - x1);
	}

	// Om de hoogte op een zeker x-coordinaat te bepalen
	public double heightAt(double x) {
		return slope() * (x - x1) + y1;
	}

	public void draw(Graphics2D g2D) {
		g2D.setColor(Color.darkGray);
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
			this.spike = new Spike(x1, y1, (x1+x2)/2, (y1+y2)/2, spikeHeight);
		}
		if (bush != null ) {
			this.bush = new Bush(x1, y1 - Main.screenHeight / 11, 40, 40, 15, DARKGREEN);
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
		


		// de coin, spike en bush worden apart getekend

	}
}
