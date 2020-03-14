import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class TestInterface extends JPanel implements ActionListener {
	Wheel wheel = new Wheel(300, 300, 100, 2, 0, new Color(0, 0, 0));
	Timer updateTimer = new Timer(20, this);
	
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
        
        wheel.draw(g);
    }
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == updateTimer) {
			wheel.update(20);
			//repaint();
		}
		
	}
}
