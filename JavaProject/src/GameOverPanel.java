import java.awt.Font;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GameOverPanel extends JPanel implements ActionListener {
	JButton retryButton;
	JButton menuButton;
	
	Main frame;
	

	public static final int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
	public static final int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
	
	public GameOverPanel(Main frame) {
		this.frame = frame;
		
		this.retryButton = new JButton("Retry");
		retryButton.addActionListener(this);
		
		this.menuButton = new JButton("Menu");
		menuButton.addActionListener(this);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setFont(new Font(Font.MONOSPACED,  Font.BOLD, 72));
		g.drawString("Game Over!", 40, screenHeight/4);
	    retryButton.setBounds(40, screenHeight/2, 72, 32);  
	    this.add(retryButton);  
	    menuButton.setBounds(40, screenHeight/2+50, 72, 32);
	    this.add(menuButton);
	    
	    this.setLayout(null);  
	    this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == retryButton) {
			frame.startGame();
		}
		if (e.getSource() == menuButton) {
			frame.menu();
		}
	}
	
	


}
