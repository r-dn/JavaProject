import java.awt.Toolkit;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Main extends JFrame {

	public static final int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
	public static final int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

	public GameInterface game;
	public GameData gamedata;

	public Main() {
		gamedata = new GameData();

		game = new GameInterface(gamedata.current, this);
		setSize(screenWidth, screenHeight);
	}

	public static void main(String[] args) {

		Main m = new Main();
		m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		m.setTitle("Ghostbike");
		m.setLocation(0, 0); // standaard in de hoek van het scherm

		m.menu();

	}

	public void menu() {
		gamedata.save();
		getContentPane().removeAll();
		add(new MenuPanel(this));
		setVisible(true);
		revalidate();
		repaint();
	}

	public void changeMenu() {
		getContentPane().removeAll();
		ChangePanel ch = new ChangePanel(this);
		add(ch);
		ch.requestFocusInWindow();
		setVisible(true);
		revalidate();
		repaint();
	}

	public void endGame() {

		double totalDistance = game.main.distance;
		double totalTime = game.main.time;
		int coins = game.main.coins;
		boolean highscore = (totalDistance > gamedata.highscore);

		gamedata.coins = gamedata.coins + coins;
		if (highscore) {
			gamedata.highscore = totalDistance;
		}
		gamedata.save();
		game.transferFocus();
		getContentPane().removeAll();
		add(new GameOverPanel(this, totalDistance, totalTime, coins, highscore));
		setVisible(true);
		revalidate();
		repaint();
	}

	public void startGame() {
		getContentPane().removeAll();
		game = new GameInterface(gamedata.current, this);
		add(game);
		game.start();
		setVisible(true);
		revalidate();
		repaint();
	}
}
