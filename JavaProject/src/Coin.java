import java.awt.Color;
import java.awt.Graphics2D;

public class Coin {
	public double x, y;
	public int coinvalue;
	public int coinsize;
	public Color coincolor;

	public Coin(double x, double y, int val, int size, Color color) {
		this.x = x;
		this.y = y;
		this.coinvalue = val;
		this.coinsize = size;
		this.coincolor = color;
	}

	public Coin(double x, double y) {
		this(x, y, 1, 40, Color.yellow);
		// TODO Auto-generated constructor stub
	}

	public void draw(Graphics2D g2D) {
		// ik wil sterven
		// iedereen wil sterven
		
		g2D.setColor(coincolor);
		g2D.drawOval((int) x - coinsize/2,(int) y - coinsize/2, coinsize, coinsize);
	}
}
