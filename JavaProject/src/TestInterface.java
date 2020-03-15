// 14/3/20


import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class TestInterface extends JPanel implements ActionListener {
	Wheel wheel1, wheel2;
	Bike bike;
	Timer updateTimer = new Timer(20, this);
	
	public TestInterface() {
		wheel1 = new Wheel(200, 500, 80, 12, 2, new Color(20, 150, 0));
		wheel2 = new Wheel(450, 500, 80, 12, 2, new Color(240, 150, 0));
		bike = new Bike(wheel2, wheel1, new Color(64, 64, 64));
		
		
		updateTimer.start();
	}
	
	public static void main(String[] args) {
		JFrame f = new JFrame();
        f.setSize(750,750);
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
        
        bike.draw(g);
        //bike.rotateAroundFront(1);
        //bike.draw(g);
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == updateTimer) {
			//bike.rotateAroundFront(0.01);
			bike.update(20);
			repaint();
			
		}
		
	}
}
