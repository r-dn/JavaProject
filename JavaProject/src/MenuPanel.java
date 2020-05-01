import java.awt.Font;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MenuPanel extends JPanel implements ActionListener {
	JButton playButton;
	JButton buyButton;
	
	Main frame;
	
	public static final int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
	public static final int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
	
	public MenuPanel(Main frame) {
		this.frame = frame;
		
		this.playButton = new JButton("Play!");
		playButton.addActionListener(this);
		
		this.buyButton = new JButton("Buy...");
		buyButton.addActionListener(this);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setFont(new Font(Font.MONOSPACED,  Font.BOLD, 72));
		g.drawString("INSERT NAME HERE", 40, screenHeight/4);

		g.setFont(new Font(Font.MONOSPACED,  Font.BOLD, 32));
		g.drawString("You stop, you die...", 40, screenHeight/4+150);
		
	    playButton.setBounds(40, screenHeight/2, 72, 32);  
	    this.add(playButton);  
	    buyButton.setBounds(40, screenHeight/2+50, 72, 32);
	    this.add(buyButton);
	      
	    this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == playButton) {
			frame.startGame();
		}
	}

}
