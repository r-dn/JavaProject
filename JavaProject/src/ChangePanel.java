
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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ChangePanel extends JPanel implements ActionListener, KeyListener {
	JButton returnButton;
	JButton selectButton;
	
	/*
	 * Er zijn n soorten fietsen in totaal (standard, monster,...)
	 * Elke fiets heeft een int (tussen 0 en n-1) als getal
	 */
	public int selected;
	public boolean[] unlocked;
	public Color[] colors;

	Main frame;
	
	public static final Bike[] bikes = GameData.bikes;
	
	public ChangePanel(Main frame, GameData gamedata, Color[] colors) {
		this.frame = frame;
		
		
		this.returnButton = new JButton("Return");
		returnButton.addActionListener(this);
		
		
		this.selected = gamedata.current;
		this.unlocked = gamedata.unlocked;
		this.colors = colors;
		
		this.selectButton = new JButton();
		selectButton.addActionListener(this);
		
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		requestFocusInWindow();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		double defaultSize = Main.screenHeight/5;
		
		for (int i = 0; i < bikes.length; i++) {
			if (selected == i) {
				bikes[i].setSize(defaultSize*1.3);
				
				g.setColor(unlocked[i] ? Color.black : Color.GRAY);
				drawCenteredString(g, bikes[i].name, new Rectangle(Main.screenWidth/2-100, Main.screenHeight/3+120, 200, 40), new Font(Font.MONOSPACED,  Font.BOLD, 32));
				
				g.setFont(new Font(Font.MONOSPACED,  Font.BOLD, 20));
				g.drawString("Speed: "+(int) bikes[i].maxSpeed, Main.screenWidth/2-100, Main.screenHeight/3+200);
				g.drawString("Efficiency: "+(int) bikes[i].efficiency, Main.screenWidth/2-100, Main.screenHeight/3+230);
				g.drawString("Jump: "+(int) bikes[i].jumpPower, Main.screenWidth/2-100, Main.screenHeight/3+260);
				if (!unlocked[i]) {
					drawLockIcon(g, Main.screenWidth/2-84, Main.screenHeight/3+325, 32);
					
					drawCenteredString(g, " "+bikes[i].price+ " coins", new Rectangle(Main.screenWidth/2-100, Main.screenHeight/3+300, 200, 40), new Font(Font.MONOSPACED,  Font.BOLD, 24));
				}
			} else {
				bikes[i].setSize(defaultSize);
			}
			
			double size = bikes[i].size();
			bikes[i].back.x = Main.screenWidth/2 + (i-selected)*defaultSize*2.1 - size/2;
			bikes[i].front.x = Main.screenWidth/2 + (i-selected)*defaultSize*2.1 + size/2;
			
			bikes[i].draw(g);
		}
		
		
		returnButton.setBounds(40, 40, 72, 32);
	    this.add(returnButton);
	      
	    
	}
	
	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
	int key = e.getKeyCode();
	
	if (key == KeyEvent.VK_LEFT && selected > 0) {
		selected--;
		repaint();
	} else if (key == KeyEvent.VK_RIGHT && selected < GameData.bikes.length - 1) {
		selected++;
		repaint();
	}

	}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == returnButton) {
			frame.menu();
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
		g.fillRoundRect((int) Math.round(x-size/2), (int) Math.round(y-size*1/3), (int) Math.round(size), (int) Math.round(size*2/3), (int) Math.round(size/6), (int) Math.round(size/6));
		Graphics2D g2D = (Graphics2D) g;
		g2D.setStroke(new BasicStroke((int) Math.round(size/6)));
		g.drawLine((int) Math.round(x-size/4), (int) Math.round(y-size*1/3), (int) Math.round(x-size/4), (int) Math.round(y-size/3));
		g.drawLine((int) Math.round(x+size/4), (int) Math.round(y-size*1/3), (int) Math.round(x+size/4), (int) Math.round(y-size/3));
		g2D.drawArc((int) Math.round(x-size/4), (int) Math.round(y-size*3/4), (int) Math.round(size/2), (int) Math.round(size/2), 180, -180);
	}
	
}
