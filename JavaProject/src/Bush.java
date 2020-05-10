import java.awt.Color;
import java.awt.Graphics2D;

public class Bush {
	public double x, y;
	public double deltax, deltay;	// Hoeveel de x en y verschuift moeten worden ten opzichte van de bottom left oval
	public double size;				// screenWidth en screenHeight worden beide gedeeld door dit getal
	public Color bushcolor;

	public Bush(double x, double y, double deltax, double deltay, double size, Color bushcolor) {
		this.x = x;
		this.y = y;
		this.deltax = deltax;	
		this.deltay = deltay;
		this.size = size;
		this.bushcolor = bushcolor;
	}
	
	// Er worden 4 ovalen getekend die ten op zichte van elkaar zijn verschoven
	public void draw(Graphics2D g2D) {
		double width = Main.screenWidth / size;		// Breedte en lengte van de ovalen willen we op basis van screenWidth en screenHeight
		double height = Main.screenHeight / size;

		g2D.setColor(bushcolor);
		g2D.fillOval((int) x, (int) y, (int) width, (int) height); // bottom left oval
		g2D.fillOval((int) (x + deltax), (int) (y - deltay), (int) width, (int) height); // upper oval
		g2D.fillOval((int) (x + 2*deltax), (int) y, (int) width, (int) height); // bottom right oval
		g2D.fillOval((int) (x + deltax), (int) y, (int) width, (int) height); // bottom middle oval
	}
}
