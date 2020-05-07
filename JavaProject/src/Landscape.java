
import java.awt.Graphics2D;

// 15/3/20

public class Landscape {
	private static final int load = 25; // hoeveel lijnsegmenten er geladen moeten worden
	private static final double limit = 0.1; // hoeveel elk lijnsegment max mag stijgen of dalen
	private static final double maxTilt = Math.PI / 4; // de maximale helling
	private static final int increment = 100;
	private static final int g = 2000; // valversnelling
	public static final int startEnergy = 5000;

	public LineSegment[] lines = new LineSegment[load];
	public Bike bike;
	public int current, current2; // pointers, geven aan welk(e) lijnsegment(en) van 'lines' momenteel onder de
									// fiets zijn
	public int length; // de lengte van elk segment
	public double speed; // de snelheid van de fiets, in pixels/s

	private double jumpHeight; // hoe hoog de fiets momenteel boven de weg is
	private double jumpSpeed; // de verticale snelheid tijdens een jump
	private boolean jumping; // is de fiets momenteel in een jump?

	public double energy;
	public double distance;
	public double time;
	public int coins;

	public Landscape(Bike bike, int frameWidth) {
		this.bike = bike;

		// Dit lijkt me een goede lengte
		length = frameWidth / 20;

		// De lijnsegmenten genereren
		// Het eerste segment is horizontaal
		lines[0] = new LineSegment(0, bike.back.y + bike.back.radius, length, bike.back.y + bike.back.radius);
		for (int i = 1; i < load; i++) {
			lines[i] = LineSegment.randomTilt(lines[i - 1], length, 0, maxTilt);
		}

		// current is het segment onder het achterwiel
		current = (int) Math.round(bike.back.x / length);
		current2 = current;

		// De snelheid van het landschap kan gehaald worden uit rotatiesnelheid van de
		// wielen
		speed = bike.back.angularVelocity * bike.back.radius;

		// de jumphoogte is 0
		jumpHeight = 0;
		jumpSpeed = 0;
		jumping = false;

		energy = startEnergy;
		distance = 0;
		time = 0;
		coins = 0;
	}

	public void setSpeed(double speed) {
		bike.back.angularVelocity = speed / bike.back.radius;
		bike.front.angularVelocity = speed / bike.front.radius;
		this.speed = speed;
	}

	// De hellingsgraad
	public double slope() {
		return -Math.tan(bike.tilt());
	}

	public void update(int period) {

		// versnellen bergaf, vertragen bergop
		// niet als we aan het springen zijn <- wel, want anders kun je cheaten
		// if (!jumping) {
		setSpeed(speed + g * period / 1000 * Math.sin(bike.tilt()));
		// }
		// voorlopig is de max snelheid 2000
		if (speed > bike.maxSpeed) {
			setSpeed(bike.maxSpeed);
		}

		// de hoogte in de lucht veranderen tijdens een sprong
		if (jumpHeight >= 0) {
			jumpSpeed -= g * period / 1000;
			jumpHeight += jumpSpeed * period / 1000;
		}
		if (jumpHeight <= 0) {
			jumping = false;
			jumpHeight = 0;
		}

		// deltax en deltay geven aan met welke hoeveelheid we de lijnsegmenten moeten
		// verplaatsen
		// deltax is afhankelijk van de snelheid (speed)
		double horizontalSpeed = speed * Math.cos(bike.tilt());
		double deltax = period * horizontalSpeed / 1000;

		// Met deltay corrigeren we de hoogte van de lijnsegmenten zodat het achterwiel
		// steeds de weg raakt
		double deltay = lines[current].heightAt(bike.back.x) - (bike.back.y + bike.back.radius) - jumpHeight;

		for (LineSegment line : lines) {
			line.shift(-deltax, -deltay);
		}

		// Als lines[current] zich niet meer onder het achterwiel bevindt, moeten we de
		// pointer verplaatsen
		// We gebruiken Math.floorMod() omdat de %-operator niet goed werkt met
		// negatieve getallen
		if (lines[current].x2 < bike.back.x) {
			current = Math.floorMod(current + 1, load);
		}
		// idem, maar met het voorwiel
		if (lines[current].x2 < bike.front.x) {
			current2 = Math.floorMod(current + 1, load);
		}

		// Als een lijnsegment zich niet meer op het scherm bevindt, kunnen we het
		// verwijderen en een nieuw segment voorbereiden
		if (lines[Math.floorMod(current - 5, load)].x2 < 0) {
			lines[Math.floorMod(current - 5, load)] = LineSegment.randomTilt(lines[Math.floorMod(current - 6, load)],
					length, limit, maxTilt);

			distance += (double) length / 100;
		}

		// fiets updaten
		// We roteren de fiets rond het voorwiel zodat het voorwiel de weg
		// (lines[current2]) raakt
		double newbikefronty = lines[current2].heightAt(bike.front.x) - jumpHeight;
		double angle = Math.asin(((newbikefronty - bike.front.y - bike.front.radius) / bike.size()) % 1); // die %1 fixt
																											// een bug
		// We delen de hoek door 4 zodat het minder schokkerig lijkt
		bike.rotateAroundBack(-angle / 4);
		bike.update(period);

		// energie updaten
		// hoeveel energie je verliest hangt af van de snelheid en hellingsgraad
		energy -= speed * (1 + slope()) / bike.efficiency;
		if (energy < 0) {
			energy = 0;
		}

		time += (double) period / 1000;
	}

	public void increaseSpeed() {
		if (speed <= bike.maxSpeed - increment && energy > 0) {
			setSpeed(speed + increment);
		}
	}

	public void decreaseSpeed() {
		if (speed >= increment && energy > 0) {
			setSpeed(speed - increment);
		}
	}

	public void jump() {
		if (!jumping && energy > 0) {
			jumping = true;
			jumpSpeed = bike.jumpPower;
			energy -= 100000 / bike.efficiency;
		}
	}

	public void draw(Graphics2D g2D) {
		g2D.setColor(LineSegment.skyColor);
		g2D.fillRect(0, 0, Main.screenWidth, Main.screenHeight);

		for (LineSegment line : lines) {
			line.drawWithBackground(g2D);

		}

		bike.draw(g2D);

	}

	public void drawText(Graphics2D g2D) {
		String text = "Speed: " + speed + "\n" + "Slope: " + Math.round(slope()) + " %\n" + "";

		g2D.drawString(text, 10, 20);
	}

}
