// 14/3/20


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class TestInterface extends JPanel implements ActionListener {
	Wheel wheel1, wheel2;
	Bike bike;
	Landscape landsc;
	DefaultBike defbike = new DefaultBike(200, 400, 200, 5);
	Timer updateTimer = new Timer(20, this);
	
	public TestInterface() {
		wheel1 = new Wheel(200, 500, 80, 12, 2, new Color(20, 150, 0));
		wheel2 = new Wheel(450, 500, 80, 12, 2, new Color(240, 150, 0));
		bike = new Bike(wheel2, wheel1, new Color(64, 64, 64));
		landsc = new Landscape(defbike, 1440);
		landsc.setSpeed(500);
		updateTimer.start();
	}
	
	public static void main(String[] args) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        System.out.println(screenSize);
		
		JFrame f = new JFrame();
        f.setSize(screenSize.width,screenSize.height);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setTitle("Wheel");
        f.setLocation(100, 100); //standaard in de hoek van het scherm
        TestInterface wp = new TestInterface();
        JPanel hoofdpaneel = wp;
        f.add(hoofdpaneel);
        f.setVisible(true);
        
	}

	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2D = (Graphics2D) g;
        
        g2D.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        System.out.println(landsc.speed);
        
        landsc.draw(g2D);
        //LineSegment.random(0, 400, 300, 200).draw(g2D);
        //defbike.draw(g2D);
        //bike.draw(g);
        //bike.rotateAroundFront(1);
        //bike.draw(g);
        
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == updateTimer) {
			bike.rotateAroundBack(0.1);
			bike.update(20);
			//defbike.update(20);
			landsc.update(20);
			repaint();
			
		}
		
	}
}
