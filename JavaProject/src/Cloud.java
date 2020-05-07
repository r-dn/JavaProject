import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

//TODO Herschrijven naar een echte klasse

public class Cloud {
	public double x, y;
	public double size;
	public double vx,vy;
	
	public Cloud(double x, double y, double size) {
		double randomx = Main.screenWidth - ThreadLocalRandom.current().nextDouble(0, Main.screenWidth + 1);
		double randomy = ThreadLocalRandom.current().nextDouble(40,60);
		double randomsize = ThreadLocalRandom.current().nextDouble(9,15);
		Random rng = new Random();
		
		this.x = randomx;
		this.y = randomy;
		this.size = randomsize;
		
		this.vx = rng.nextInt();					//Max snelheid = 100
		this.vy = rng.nextInt();
		
	}

	public void draw(Graphics2D g2D) {

		double width = Main.screenWidth / size;
		double height = Main.screenHeight / size;

		g2D.setColor(new Color(255, 255, 255));
		g2D.fillOval((int) x, (int) y, (int) width, (int) height); // Left oval
		g2D.fillOval((int) x + 50, (int) y - 40, (int) width, (int) height); // Upper oval
		g2D.fillOval((int) x + 50, (int) y + 40, (int) width, (int) height); // Down oval
		g2D.fillOval((int) x + 100, (int) y, (int) width, (int) height); // Right oval
	}
	
	public void update(int ms) {
		x += vx*ms/1000.0;
		y += vy*ms/1000.0;
	
	}
}
