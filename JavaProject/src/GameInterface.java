import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
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
	public SpeedSlider speed;
	public Timer updateTimer = new Timer(refresh, this);
	
	private int counter = 0;
	private int total = 0;
	private int fps = 0;
	
	public static final int refresh = 20;
	public static final int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
	public static final int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
	
	public GameInterface(Bike bike) {
		main = new Landscape(bike, screenWidth);
		speed = new SpeedSlider(20, 20, screenWidth/6, screenHeight/20, main.maxSpeed);
		
		updateTimer.start();
		addKeyListener(this);
		super.setFocusable(true);
		setFocusTraversalKeysEnabled(false);
	}
	
	public static void main(String[] args) {
		
		JFrame f = new JFrame();
        f.setSize(screenWidth,screenHeight);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setTitle("Game");
        f.setLocation(0, 0); //standaard in de hoek van het scherm
        GameInterface wp = new GameInterface(new DefaultBike(screenWidth/8, screenHeight/2, screenWidth/8, 0));
        JPanel hoofdpaneel = wp;
        f.add(hoofdpaneel);
        f.setVisible(true);
       
	}
	
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2D = (Graphics2D) g;
        
        // anti-aliasing
       // g2D.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int begintijd = (int) System.currentTimeMillis();
        main.draw(g2D);
        
        speed.draw(g2D);
        main.drawText(g2D);
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
        
        g2D.drawString("Theoretical fps: "+fps, 10, 120);
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
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == updateTimer) {
			main.update(20);
			
			speed.setCurrentSpeed(main.speed);
			
			repaint();
			
		}
	}

}
