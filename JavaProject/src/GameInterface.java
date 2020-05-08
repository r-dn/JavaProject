import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class GameInterface extends JPanel implements ActionListener, KeyListener {
	public Landscape main;
	public EnergySlider energy;
	public SpeedSlider speed;
	public Timer updateTimer = new Timer(refresh, this);
	public int current;
	public boolean paused;

	public Main frame;

	private int counter = 0;
	private int total = 0;
	private int fps = 0;

	private JButton returnButton;
	private JButton resumeButton;
	private JButton retryButton;

	public static final int refresh = 20;

	public GameInterface(int current, Main frame) {
		this.current = current;
		Bike bike = GameData.bike(current);
		main = new Landscape(bike, Main.screenWidth);
		energy = new EnergySlider(Main.screenWidth*2/3-20, 20, Main.screenWidth / 3, 40, Landscape.startEnergy);
		speed = new SpeedSlider(20, 20, Main.screenWidth/6, 40, main.bike.maxSpeed);

		this.frame = frame;

		paused = false;

		returnButton = new JButton("Menu");
		returnButton.addActionListener(this);

		resumeButton = new JButton("Resume");
		resumeButton.addActionListener(this);

		retryButton = new JButton("Retry");
		retryButton.addActionListener(this);

	}

	// nu is de main() in de klasse Main

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2D = (Graphics2D) g;

		int begintijd = (int) System.currentTimeMillis();
		main.draw(g2D, true);

		energy.draw(g2D);
		speed.draw(g2D);
		// main.drawText(g2D);
		g2D.drawString("Distance: " + (int) main.distance + " m", 20, 80);
		g2D.drawString("Time: " + Math.floor(main.time * 10) / 10 + " s", 20, 100);
		int eindtijd = (int) System.currentTimeMillis();

		// de fps om de 50 frames tekenen
		counter++;
		total += eindtijd - begintijd;
		if (counter == 50) {
			double invfps = (double) total / 50;
			fps = (int) Math.round(1000 / invfps);

			counter = 0;
			total = 0;
		}

		g2D.drawString("FPS: " + fps, Main.screenWidth - 100, 50);

		if (paused) {
			drawPausePanel(g2D);
		} else {
			remove(returnButton);
			remove(retryButton);
			remove(resumeButton);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (!paused) {
			if (key == KeyEvent.VK_LEFT) {
				main.decreaseSpeed();
			}
			if (key == KeyEvent.VK_RIGHT) {
				main.increaseSpeed();
			}
			if (key == KeyEvent.VK_SPACE) {
				main.jump();
			}
		}
		if (key == KeyEvent.VK_ESCAPE) {
			paused = !paused;
			repaint();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == updateTimer) {
			if (!paused) {
				if (main.speed <= 2) {
					gameOver();
				}

				main.update(20);

				energy.setCurrentEnergy(main.energy);
				speed.setCurrentSpeed(main.speed);

				repaint();
			} else {

			}
		}
		if (e.getSource() == returnButton) {
			frame.menu();
		} else if (e.getSource() == retryButton) {
			frame.startGame();
		} else if (e.getSource() == resumeButton) {
			updateTimer.restart();
			paused = false;
		}
	}

	public void gameOver() {
		updateTimer.stop();
		frame.endGame();
	}

	public void start() {
		updateTimer.start();
		addKeyListener(this);
		super.setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		requestFocusInWindow();
	}

	public GameInterface restart() {
		GameInterface newGame = new GameInterface(current, frame);
		return newGame;
	}

	public void drawPausePanel(Graphics g) {
		g.setColor(new Color(238, 238, 238));
		g.fillRoundRect(Main.screenWidth / 2 - 192, Main.screenHeight / 2 - 72, 384, 144, 20, 20);

		g.setColor(Color.black);
		ChangePanel.drawCenteredString(g, "Paused",
				new Rectangle(Main.screenWidth / 2 - 192, Main.screenHeight / 2 - 72, 384, 96),
				new Font(Font.MONOSPACED, Font.BOLD, 48));

		returnButton.setBounds(Main.screenWidth / 2 - 168, Main.screenHeight / 2 + 24, 96, 32);
		add(returnButton);
		retryButton.setBounds(Main.screenWidth / 2 - 48, Main.screenHeight / 2 + 24, 96, 32);
		add(retryButton);
		resumeButton.setBounds(Main.screenWidth / 2 + 72, Main.screenHeight / 2 + 24, 96, 32);
		add(resumeButton);
	}
}
