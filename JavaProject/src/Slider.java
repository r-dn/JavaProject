import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

// 10/4/2020

public class Slider {
	public double x, y;
	public double width, height;
	public double current; // tussen 0 en 1

	private final BasicStroke stroke = new BasicStroke(3.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL);

	public Slider(double x, double y, double width, double height, double current) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.current = current;
	}

	public void draw(Graphics2D g2D, Color color) {
		g2D.setColor(color);
		g2D.fillRect((int) Math.round(x), (int) Math.round(y), (int) Math.round(current * width),
				(int) Math.round(height));

		g2D.setColor(Color.BLACK);
		g2D.setStroke(stroke);
		g2D.drawRect((int) Math.round(x), (int) Math.round(y), (int) Math.round(width), (int) Math.round(height));
	}

}
