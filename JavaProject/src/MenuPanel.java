import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MenuPanel extends JPanel implements ActionListener {
	private JButton playButton;
	private JButton changeButton;

	public Main frame;

	public MenuPanel(Main frame) {
		this.frame = frame;

		playButton = new JButton("Play!");
		playButton.addActionListener(this);

		changeButton = new JButton("Change...");
		changeButton.addActionListener(this);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 72));
		g.drawString("GHOSTBIKE", 40, Main.screenHeight / 4);

		g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 32));
		g.drawString("You stop, you die...", 40, Main.screenHeight / 4 + 50);

		g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 24));
		g.drawString("Highscore: " + (double) Math.round(frame.gamedata.highscore) / 1000 + " km", 40,
				Main.screenHeight / 4 + 120);

		frame.gamedata.drawCoins(g);

		playButton.setBounds(40, Main.screenHeight / 4 + 300, 96, 32);
		add(playButton);
		changeButton.setBounds(40, Main.screenHeight / 4 + 350, 96, 32);
		add(changeButton);

		setLayout(null);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == playButton) {
			frame.startGame();
		}
		if (e.getSource() == changeButton) {
			frame.changeMenu();
		}
	}

}
