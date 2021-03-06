
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ChangePanel extends JPanel implements ActionListener, KeyListener {
	private JButton returnButton;
	private JButton selectButton;

	/*
	 * Er zijn n soorten fietsen in totaal (standard, monster,...) Elke fiets heeft
	 * een int (tussen 0 en n-1) als getal
	 */
	private int selected;
	public boolean[] unlocked;

	public Main frame;
	private boolean displayMessage;

	private static final Bike[] bikes = GameData.bikes;

	public ChangePanel(Main frame) {
		this.frame = frame;

		returnButton = new JButton("Return");
		returnButton.addActionListener(this);

		this.selected = frame.gamedata.current;
		this.unlocked = frame.gamedata.unlocked;

		selectButton = new JButton();
		selectButton.addActionListener(this);
		
		displayMessage = false;

		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		requestFocusInWindow();
		setLayout(null);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		frame.game.main.draw((Graphics2D) g, false);
		
		g.setColor(Color.black);

		for (int i = 0; i < bikes.length; i++) {
			if (selected == i) {
				g.setColor(unlocked[i] ? Color.black : Color.darkGray);
				drawCenteredString(g, bikes[i].name,
						new Rectangle(Main.screenWidth / 2 - 100, Main.screenHeight / 3 + 120, 200, 40),
						new Font(Font.MONOSPACED, Font.BOLD, 32));

				g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
				g.drawString("Speed: " + (int) bikes[i].maxSpeed, Main.screenWidth / 2 - 100,
						Main.screenHeight / 3 + 200);
				g.drawString("Efficiency: " + (int) bikes[i].efficiency, Main.screenWidth / 2 - 100,
						Main.screenHeight / 3 + 230);
				g.drawString("Jump: " + (int) bikes[i].jumpPower, Main.screenWidth / 2 - 100,
						Main.screenHeight / 3 + 260);

				selectButton.setText("Select");

				if (!unlocked[i]) {
					drawLockIcon(g, Main.screenWidth / 2 - 84, Main.screenHeight / 3 + 325, 32);

					drawCenteredString(g, " " + bikes[i].price + " coins",
							new Rectangle(Main.screenWidth / 2 - 100, Main.screenHeight / 3 + 300, 200, 40),
							new Font(Font.MONOSPACED, Font.BOLD, 24));

					selectButton.setText("Buy");
				}
				if (selected == frame.gamedata.current) {
					selectButton.setText("Selected");
				}
			}

			double size = bikes[i].size();
			bikes[i].back.x = Main.screenWidth / 2 + (i - selected)*Main.screenHeight*0.42 - size / 2;
			bikes[i].front.x = Main.screenWidth / 2 + (i - selected)*Main.screenHeight*0.42 + size / 2;

			bikes[i].draw(g);
			
		}

		returnButton.setBounds(40, 40, 96, 32);
		add(returnButton);

		selectButton.setBounds(Main.screenWidth / 2 - 48, Main.screenHeight / 3 + 360, 96, 32);
		add(selectButton);
		
		if (displayMessage) {
			g.setColor(Color.black);
			drawCenteredString(g, "You don't have enough coins", new Rectangle(Main.screenWidth / 2 - 100, Main.screenHeight / 3 + 400, 200, 20),
					new Font(Font.MONOSPACED, Font.BOLD, 18));
		}

		frame.gamedata.drawCoins(g);
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT && selected > 0) {
			selected--;
			displayMessage = false;
			repaint();
		} else if (key == KeyEvent.VK_RIGHT && selected < GameData.bikes.length - 1) {
			selected++;
			displayMessage = false;
			repaint();
		} else if (key == KeyEvent.VK_ESCAPE) {
			frame.menu();
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == returnButton) {
			frame.menu();
		} else if (e.getSource() == selectButton) {
			if (unlocked[selected]) {
				frame.gamedata.current = selected;
			} else if (frame.gamedata.coins >= bikes[selected].price) {
				frame.gamedata.coins -= bikes[selected].price;
				unlocked[selected] = true;
				frame.gamedata.unlocked = unlocked;
				frame.gamedata.current = selected;
				repaint();
			} else {
				displayMessage = true;
				repaint();
			}
		}

	}

	// stackoverflow
	public static void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
		FontMetrics metrics = g.getFontMetrics(font);

		int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
		int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
		g.setFont(font);
		g.drawString(text, x, y);
	}

	private void drawLockIcon(Graphics g, double x, double y, double size) {
		g.fillRoundRect((int) Math.round(x - size / 2), (int) Math.round(y - size * 1 / 3), (int) Math.round(size),
				(int) Math.round(size * 2 / 3), (int) Math.round(size / 6), (int) Math.round(size / 6));
		Graphics2D g2D = (Graphics2D) g;
		g2D.setStroke(new BasicStroke((int) Math.round(size / 6)));
		g.drawLine((int) Math.round(x - size / 4), (int) Math.round(y - size * 1 / 3), (int) Math.round(x - size / 4),
				(int) Math.round(y - size / 3));
		g.drawLine((int) Math.round(x + size / 4), (int) Math.round(y - size * 1 / 3), (int) Math.round(x + size / 4),
				(int) Math.round(y - size / 3));
		g2D.drawArc((int) Math.round(x - size / 4), (int) Math.round(y - size * 3 / 4), (int) Math.round(size / 2),
				(int) Math.round(size / 2), 180, -180);
	}

}
