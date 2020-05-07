import java.awt.Color;
import java.awt.Graphics2D;

public class Bush {
	public double x, y;
	public double deltax, deltay;
	public double size;
	public Color bushColor;

	private double width = Main.screenWidth / size;
	private double height = Main.screenHeight / size;

	
	public Bush(double x, double y, double deltax, double deltay, double size, Color bushColor) {
		this.x = x;
		this.y = y;
		this.deltax = deltax;
		this.deltay = deltay;
		this.size = size;
		this.bushColor = bushColor;
	}
	
	public void draw(Graphics2D g2D) {
			g2D.setColor(bushColor);
			g2D.fillOval((int) x, (int) y, (int) width, (int) height); //upper oval
			g2D.fillOval((int) (x + deltax), (int) (y + deltay),(int) width, (int) height); //bottom right
			g2D.fillOval((int) (x - deltax), (int) (y + deltay), (int) width, (int) height); //bottom left
			
			if (size != 10) {
			g2D.fillOval((int) x, (int) (y + deltay), (int) width, (int) height);	//bottom middle
		}
		
	}
}
