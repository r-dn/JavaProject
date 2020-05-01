import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class Main extends JFrame implements ActionListener {

	public static final int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
	public static final int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

	public int frameWidth;
	public int frameHeight;	
	
	GameInterface game;

	public Main(GameInterface game) {
		this.game = game;
		this.setSize(screenWidth, screenHeight);
		
		frameWidth = this.getWidth();
		frameHeight = this.getHeight();
	}
	public static void main(String[] args) {

		// beginsnelheid is 1000
		Main m = new Main(new GameInterface(new DefaultBike(screenWidth / 8, screenHeight / 2, screenWidth / 8, 0, 1000), null));
		m.game.frame = m;
		m.frameWidth = m.getWidth();
		m.frameHeight = m.getHeight();
		m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		m.setTitle("Game");
		m.setLocation(0, 0); // standaard in de hoek van het scherm

		m.menu();

		
	}
	
	public void menu() {
		getContentPane().removeAll();
		add(new MenuPanel(this));
		setVisible(true);
		revalidate();
		repaint();
	}


	public void endGame() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getContentPane().removeAll();
		add(new GameOverPanel(this));
		setVisible(true);
		revalidate();
		repaint();
	}
	
	public void startGame() {
		getContentPane().removeAll();
		game = game.restart();
		add(game);
		game.start();
		setVisible(true);
		revalidate();
		repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

	

}
