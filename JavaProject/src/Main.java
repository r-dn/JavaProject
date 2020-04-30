import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main implements ActionListener {
	JFrame f = new JFrame();

	public static final int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
	public static final int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

	public static void main(String[] args) {

		Main m = new Main();
		m.f.setSize(screenWidth, screenHeight);
		m.f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		m.f.setTitle("Game");
		m.f.setLocation(0, 0); // standaard in de hoek van het scherm

		startGame(m.f);

		GameOverPanel gop = new GameOverPanel();
		m.f.add(gop);
		m.f.setVisible(true);
	}

	public static void startGame(JFrame f) {
		// beginsnelheid is 1000
		GameInterface wp = new GameInterface(new DefaultBike(screenWidth / 8, screenHeight / 2, screenWidth / 8, 0, 1000));
		JPanel hoofdpaneel = wp;
		f.add(hoofdpaneel);
		f.setVisible(true);
	}

	public static void endGame(JFrame f) {
		f.add(new GameOverPanel());
		f.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getActionCommand() == "game over") {
			endGame(f);
		}
	}

}
