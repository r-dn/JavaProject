import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class Coin {
	public double x, y;
	public int coinvalue;
	public double coinsize;
	public Color coincolor;
	
	private final BasicStroke stroke;

	public Coin(double x, double y, int val, double size, Color color) {
		this.x = x;
		this.y = y;
		this.coinvalue = val;
		this.coinsize = size;
		this.coincolor = color;
		
		this.stroke = new BasicStroke((float) coinsize/2);
	}

	public Coin(double x, double y) {
		this(x, y, 1, Main.screenHeight/20, new Color(200, 200, 0));
	}

	public void draw(Graphics2D g2D) {
		
		g2D.setColor(coincolor);
		g2D.setStroke(stroke);
		g2D.drawOval((int) (x - coinsize/2),(int) (y - coinsize/2), (int) coinsize, (int) coinsize);
	}
}
