import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class GameInterface extends JPanel implements ActionListener, KeyListener {
	public Landscape main;
	public EnergySlider energy;
	public Timer updateTimer = new Timer(refresh, this);
	public int current;
	
	public Main frame;
	
	private int counter = 0;
	private int total = 0;
	private int fps = 0;
	
	public static final int refresh = 20;
	public static final int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
	public static final int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
	
	public GameInterface(int current, Main frame) {
		this.current = current;
		Bike bike = GameData.bike(current);
		main = new Landscape(bike, screenWidth);
		energy = new EnergySlider(20, 20, screenWidth/3, 40, Landscape.startEnergy);
		
		this.frame = frame;
		
		
		
	}
	
	// nu is de main() in de klasse Main
	
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2D = (Graphics2D) g;
        
        // anti-aliasing
       //g2D.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int begintijd = (int) System.currentTimeMillis();
        main.draw(g2D);
        
        energy.draw(g2D);
        //main.drawText(g2D);
        g2D.drawString("Distance: "+(int) main.distance+" m", 20, 80);
        g2D.drawString("Time: "+Math.floor(main.time * 10) / 10+" s", 20, 100);
        int eindtijd = (int) System.currentTimeMillis();
        
        // de fps om de 50 frames tekenen
        counter++;
        total += eindtijd-begintijd;
        if (counter == 50) {
        	double invfps = (double) total/50;
            fps = (int) Math.round(1000/invfps);
            
            counter = 0;
            total = 0;
        }
        
        g2D.drawString("FPS: "+fps, screenWidth - 100, 50);
    }
	
	@Override
	public void keyTyped(KeyEvent e) {
		
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_LEFT) {
			main.decreaseSpeed();
		} else if (key == KeyEvent.VK_RIGHT) {
			main.increaseSpeed();
		} else if (key == KeyEvent.VK_SPACE) {
			main.jump();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == updateTimer) {
			if (main.speed <= 2 ) {
				gameOver();
			}
			
			main.update(20);
			
			energy.setCurrentEnergy(main.energy);
			
			repaint();
			
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

}
