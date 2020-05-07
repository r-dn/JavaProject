import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GameOverPanel extends JPanel implements ActionListener, KeyListener {
	private JButton retryButton;
	private JButton menuButton;

	public Main frame;

	public double totalDistance;
	public double totalTime;
	public int coins;
	public int totalCoins;
	public boolean highscore;

	public GameOverPanel(Main frame, double totalDistance, double totalTime, int coins, int totalCoins,
			boolean highscore) {
		this.frame = frame;

		this.totalDistance = totalDistance;
		this.totalTime = totalTime;
		this.coins = coins;
		this.totalCoins = totalCoins;
		this.highscore = highscore;

		retryButton = new JButton("Retry");
		retryButton.addActionListener(this);

		menuButton = new JButton("Menu");
		menuButton.addActionListener(this);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(LineSegment.skyColor);
		g.fillRect(0, 0, Main.screenWidth, Main.screenHeight);
		
		frame.game.main.draw((Graphics2D) g, false);

		g.setColor(Color.black);
		g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 72));
		g.drawString("Game Over!", 40, Main.screenHeight / 4);

		g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 32));
		g.drawString("Distance: " + (double) Math.round(totalDistance) / 1000 + " km"
				+ (highscore ? "\t \t New Highscore!" : ""), 40, Main.screenHeight / 4 + 100);
		g.drawString("Time: " + Math.round(totalTime) + " s", 40, Main.screenHeight / 4 + 150);
		g.drawString("Average speed: " + (double) Math.round(totalDistance * 36 / totalTime) / 10 + " km/h", 40,
				Main.screenHeight / 4 + 200);
		g.drawString("+" + coins + " coins", 40, Main.screenHeight / 4 + 250);

		frame.gamedata.drawCoins(g);

		retryButton.setBounds(40, Main.screenHeight / 4 + 300, 96, 32);
		add(retryButton);
		menuButton.setBounds(40, Main.screenHeight / 4 + 350, 96, 32);
		add(menuButton);

		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == retryButton) {
			frame.startGame();
		}
		if (e.getSource() == menuButton) {
			frame.menu();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_ESCAPE) {
			frame.menu();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

}
