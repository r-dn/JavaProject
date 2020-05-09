import java.awt.Color;
import java.awt.Graphics2D;

public class Bush {
	public double x, y;
	public double deltax, deltay;
	public double size;
	public Color bushcolor;

	public Bush(double x, double y, double deltax, double deltay, double size, Color bushcolor) {
		this.x = x;
		this.y = y;
		this.deltax = deltax;
		this.deltay = deltay;
		this.size = size;
		this.bushcolor = bushcolor;
	}

	public void draw(Graphics2D g2D) {
		double width = Main.screenWidth / size;		//Breedte en lengte van de ovalen willen we op basis van screenWidth en screenHeight
		double height = Main.screenHeight / size;

		g2D.setColor(bushcolor);
		g2D.fillOval((int) x, (int) y, (int) width, (int) height); // bottom left oval
		g2D.fillOval((int) (x + deltax), (int) (y - deltay), (int) width, (int) height); // upper oval
		g2D.fillOval((int) (x + 2*deltax), (int) y, (int) width, (int) height); // bottom right oval
		g2D.fillOval((int) (x + deltax), (int) y, (int) width, (int) height); // bottom middle oval
	}
}
