import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class GameOverPanel extends JPanel implements KeyListener {

	public GameOverPanel() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		JButton b=new JButton("Click Here");  
	    b.setBounds(50,100,95,30);  
	    this.add(b);  
	    this.setLayout(null);  
	    this.setVisible(true);
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}



}
