import java.awt.Color;
import java.awt.Graphics2D;

public class Moon {
	public double x,y;						
	public double size; //Size is the radius of the two circles
	
	private static final Color MOONCOLOR = new Color(255,204,51) ;
			
	public Moon(double x, double y, double size) {
		this.x = x;
		this.y = y;
		this.size = size;
	}
	
	//2 cirkels worden over elkaar getekend
	public void draw(Graphics2D g2D) {	
		double r = Main.screenWidth / size;		//Straal van de cirkels willen we op basis van screenWidth of screenHeight 
		
		g2D.setColor(MOONCOLOR);
		g2D.fillOval((int) x, (int) y, (int) r, (int) r);
		g2D.setColor(LineSegment.skyColor);
		g2D.fillOval((int) (x + r/2) , (int) y , (int) r , (int) r);
	
	}

}
