import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Main extends JFrame implements ActionListener {

	public static final int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
	public static final int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
	
	GameInterface game;
	GameOverPanel gameOver;

	public Main(GameInterface game) {
		this.game = game;
		this.gameOver = new GameOverPanel(this);
	}
	public static void main(String[] args) {

		// beginsnelheid is 1000
		Main m = new Main(new GameInterface(new DefaultBike(screenWidth / 8, screenHeight / 2, screenWidth / 8, 0, 1000), null));
		m.game.frame = m;
		m.setSize(screenWidth, screenHeight);
		m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		m.setTitle("Game");
		m.setLocation(0, 0); // standaard in de hoek van het scherm

		m.startGame();

		
	}

	public void startGame() {
		add(game);
		setVisible(true);
	}

	public void endGame() {
		remove(game);
		add(gameOver);
		setVisible(true);
		revalidate();
	}
	
	public void restartGame() {
		remove(gameOver);
		game = game.restart();
		gameOver = new GameOverPanel(this);
		add(game);
		setVisible(true);
		revalidate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	

}
