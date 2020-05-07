import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MenuPanel extends JPanel implements ActionListener {
	JButton playButton;
	JButton changeButton;
	
	Main frame;
	
	public static final int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
	public static final int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
	
	public MenuPanel(Main frame) {
		this.frame = frame;
		
		this.playButton = new JButton("Play!");
		playButton.addActionListener(this);
		
		this.changeButton = new JButton("Change...");
		changeButton.addActionListener(this);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setFont(new Font(Font.MONOSPACED,  Font.BOLD, 72));
		g.drawString("GHOSTBIKE", 40, screenHeight/4);

		g.setFont(new Font(Font.MONOSPACED,  Font.BOLD, 32));
		g.drawString("You stop, you die...", 40, screenHeight/4+50);
		
		g.setFont(new Font(Font.MONOSPACED,  Font.BOLD, 24));
		g.drawString("Highscore: "+(double) Math.round(frame.gamedata.highscore)/1000+" km", 40, screenHeight/4+120);
		
		
		
		// align right
		String s =  frame.gamedata.coins+" coins";
		g.setFont(new Font(Font.MONOSPACED,  Font.BOLD, 24));
		g.setColor(Color.black);
		FontMetrics fontMetrics = g.getFontMetrics();
		g.drawString(s, Main.screenWidth-40-fontMetrics.stringWidth(s), 72);
		
	    playButton.setBounds(40, screenHeight/4+300, 96, 32);  
	    this.add(playButton);  
	    changeButton.setBounds(40, screenHeight/4+350, 96, 32);
	    this.add(changeButton);
	     
	    this.setLayout(null);
	    this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == playButton) {
			frame.startGame();
		}
		if (e.getSource() == changeButton) {
			frame.changeMenu();
		}
	}

}
