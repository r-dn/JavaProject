import java.awt.Color;
import java.awt.Graphics2D;

public class Cloud {
	public double x, y;
	public double size;		// screenHeight en screenWidth worden beide gedeeld door dit getal
	
	private static final Color CLOUDCOLOR = new Color(215,215,215);
	
	public Cloud(double x, double y, double size) {
		this.x = x;
		this.y = y;
		this.size = size;
	}
	
	// Er worden 4 ovalen getekend die ten opzichte van elkaar zijn verschoven 
	public void draw(Graphics2D g2D) {

		double width = Main.screenWidth / size;		// Breedte en lengte van de ovalen willen we op basis van screenWidth en screenHeight 
		double height = Main.screenHeight / size;

		g2D.setColor(CLOUDCOLOR);
		g2D.fillOval((int) x, (int) y, (int) width, (int) height); // Left oval
		g2D.fillOval((int) x + 45, (int) y - 40, (int) width, (int) height); // Upper oval
		g2D.fillOval((int) x + 45, (int) y + 40, (int) width, (int) height); // Down oval
		g2D.fillOval((int) x + 100, (int) y, (int) width, (int) height); // Right oval
	}
	
}
