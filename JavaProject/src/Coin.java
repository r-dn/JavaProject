import java.awt.Color;
import java.awt.Graphics2D;

public class Coin {
	
	
public static int coinvalue;
public static int coinsize;
public static Color coincolor;

public Coin(int val, int size, Color color) {
	this.coinvalue=val;
	this.coinsize=size;
	this.coincolor=color;
}

public static Coin defaultCoin = new Coin(1, 40, Color.yellow);
public static void draw(Graphics2D g2D) {
	//ik wil sterven
}
}
