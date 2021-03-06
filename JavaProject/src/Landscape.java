
import java.awt.Graphics2D;

// 15/3/20

public class Landscape {
	private static final int load = 25; // hoeveel lijnsegmenten er geladen worden
	private static final double limit = 0.1; // hoeveel elk lijnsegment max mag stijgen of dalen
	private static final double maxTilt = Math.PI / 4; // de maximale helling
	private static final int increment = 100;
	private static final int g = 100 * GameInterface.refresh; // valversnelling
	public static final int startEnergy = 5000;

	public LineSegment[] lines = new LineSegment[load];
	public Bike bike;
	private int current, current2; // pointers, geven aan welk(e) lijnsegment(en) van 'lines' momenteel onder de
									// fiets zijn
	private final int diff;
	public int length; // de lengte van elk segment
	public double speed; // de snelheid van de fiets, in pixels/s

	private double jumpHeight; // hoe hoog de fiets momenteel boven de weg is
	private double jumpSpeed; // de verticale snelheid tijdens een jump
	private boolean jumping; // is de fiets momenteel in een jump?

	public double energy;
	public double distance;
	public double time;
	public int coins;

	private static final Cloud LEFTCLOUD = new Cloud(Main.screenWidth / 30, Main.screenHeight / 15, 9);
	private static final Cloud MIDDLECLOUD = new Cloud(Main.screenWidth / 3, Main.screenHeight / 20 + 25, 10);
	private static final Cloud RIGHTCLOUD = new Cloud(Main.screenWidth - 300, Main.screenHeight / 20 + 10, 13);

	private static final Moon MOON = new Moon(Main.screenWidth / 3 + 200, Main.screenHeight / 27, 10);

	public Landscape(Bike bike, int frameWidth) {
		this.bike = bike;

	
		length = frameWidth / 20;

		// De lijnsegmenten genereren
		// Het eerste segment is horizontaal
		lines[0] = new LineSegment(0, bike.back.y + bike.back.radius, length, bike.back.y + bike.back.radius, false,
				false);
		for (int i = 1; i < load; i++) {
			lines[i] = new LineSegment(lines[i - 1], length, 0, maxTilt, false, false);
		}
		

		// current is het segment onder het achterwiel
		current = (int) Math.round(bike.back.x / length);
		// current2 onder het voorwiel
		current2 = (int) Math.round(bike.front.x / length);
		diff = current;

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

		if (lines[Math.floorMod(current2 + 2, load)].coin != null && jumpHeight <= Main.screenHeight / 8) {
			coins++;
			lines[Math.floorMod(current2 + 2, load)].coin = null;
		}

		// versnellen bergaf, vertragen bergop
		setSpeed(speed + g * period / 1000 * Math.sin(bike.tilt()));

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
		// deltax is afhankelijk van speed
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
		if (lines[current2].x2 < bike.front.x) {
			current2 = Math.floorMod(current2 + 1, load);
		}

		// Als een lijnsegment zich niet meer op het scherm bevindt, kunnen we het
		// verwijderen en een nieuw segment voorbereiden
		if (lines[Math.floorMod(current - diff - 2, load)].x2 < 0) {
			// we willen geen spikes vlak naast elkaar, dus als er een spike geweest is,
			// wachten we min. 20 lijnsegmenten
			boolean drawSpikes = true;
			for (int i = -20; i < 0; i++) {
				if (lines[Math.floorMod(current - diff + i, load)].spike != null) {
					drawSpikes = false;
				}
			}
			lines[Math.floorMod(current - diff - 2, load)] = new LineSegment(
					lines[Math.floorMod(current - diff - 3, load)], length, limit, maxTilt, true, drawSpikes);

			distance += (double) length / 200;
		}

		// fiets updaten
		// We roteren de fiets rond het achterwiel zodat het voorwiel de weg in
		// lines[current2] raakt
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

		// als één van de wielen een spike raakt, vertraag je enorm
		if ((lines[Math.floorMod(current, load)].spike != null || lines[Math.floorMod(current2, load)].spike != null)
				&& jumpHeight < LineSegment.spikeHeight - 20) {
			setSpeed(speed - 1400);
			lines[Math.floorMod(current, load)].spike = null;
			lines[Math.floorMod(current2, load)].spike = null;
		}

	}

	public void increaseSpeed() {
		if (speed <= bike.maxSpeed - increment && energy > 0) {
			setSpeed(speed + increment);
		}
	}

	public void decreaseSpeed() {
		if (speed >= increment / 2 && energy > 0) {
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

	public void draw(Graphics2D g2D, boolean drawBike) {
		g2D.setColor(LineSegment.skyColor);
		g2D.fillRect(0, 0, Main.screenWidth, Main.screenHeight);

		MOON.draw(g2D);
		LEFTCLOUD.draw(g2D);
		MIDDLECLOUD.draw(g2D);
		RIGHTCLOUD.draw(g2D);

		for (LineSegment line : lines) {
			line.drawWithBackground(g2D);

		}

		for (LineSegment line : lines) {
			if (line.bush != null) {
				line.bush.draw(g2D);
			}
		}
		for (LineSegment line : lines) {
			if (line.coin != null) {
				line.coin.draw(g2D);
			}
		}
		for (LineSegment line : lines) {
			if (line.spike != null) {
				line.spike.draw(g2D);

			}
		}

		if (drawBike)

		{
			bike.draw(g2D);
		}
	}
}
