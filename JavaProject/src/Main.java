import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class Main extends JFrame {

	public static final int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
	public static final int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

	public int frameWidth;
	public int frameHeight;	
	
	GameInterface game;
	GameData gamedata;

	public Main() {
		gamedata = new GameData();
		
		game = new GameInterface(gamedata.current, null);
		game.frame = this;
		this.setSize(screenWidth, screenHeight);
		
		
		
		frameWidth = this.getWidth();
		frameHeight = this.getHeight();
	}
	public static void main(String[] args) {
		
		
		
		Main m = new Main();
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

	public void changeMenu() {
		getContentPane().removeAll();
		ChangePanel ch = new ChangePanel(this, gamedata, new Color[] {});
		add(ch);
		ch.requestFocusInWindow();
		setVisible(true);
		revalidate();
		repaint();
	}
	
	public void endGame() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		double totalDistance = game.main.distance;
		double totalTime = game.main.time;
		game.transferFocus();
		getContentPane().removeAll();
		add(new GameOverPanel(this, totalDistance, totalTime));
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

}
