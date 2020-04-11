import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Toolkit;

public class Clouds {

	private int ScreenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
	private int ScreenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
	private Color cloudColor = new Color(255, 255, 255);

	public void draw(Graphics2D g2D) {

		g2D.setColor(cloudColor); // Left cloud
		g2D.fillOval((int) 50, (int) 50, (int) ScreenWidth / 9, (int) ScreenHeight / 9); // Left oval
		g2D.fillOval((int) 100, (int) 10, (int) ScreenWidth / 9, (int) ScreenHeight / 9); // Upper oval
		g2D.fillOval((int) 100, (int) 100, (int) ScreenWidth / 9, (int) ScreenHeight / 9); // Down oval
		g2D.fillOval((int) 200, (int) 50, (int) ScreenWidth / 9, (int) ScreenHeight / 9); // Right oval

		g2D.setColor(cloudColor); // Middle cloud
		g2D.fillOval((int) 600, (int) 100, (int) ScreenWidth / 12, (int) ScreenHeight / 12);
		g2D.fillOval((int) 650, (int) 60, (int) ScreenWidth / 12, (int) ScreenHeight / 12);
		g2D.fillOval((int) 650, (int) 125, (int) ScreenWidth / 12, (int) ScreenHeight / 12);
		g2D.fillOval((int) 750, (int) 100, (int) ScreenWidth / 12, (int) ScreenHeight / 12);

		g2D.setColor(cloudColor); // Right cloud
		g2D.fillOval((int) 1250, (int) 50, ScreenWidth / 10, ScreenHeight / 10);
		g2D.fillOval((int) 1300, (int) 10, (int) ScreenWidth / 10, ScreenHeight / 10);
		g2D.fillOval((int) 1300, (int) 75, ScreenWidth / 10, ScreenHeight / 10);
		g2D.fillOval((int) 1350, (int) 50, (int) ScreenWidth / 10, ScreenHeight / 10);

		g2D.setColor(Color.YELLOW); // Sun
		g2D.fillOval((int) 800, (int) 20, (int) ScreenWidth / 8, (int) ScreenWidth / 8);

	}

}
