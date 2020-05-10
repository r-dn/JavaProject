
import java.awt.Color;

public class StandardBike extends Bike {

	public StandardBike(double x, double y, double size, double tilt, double beginSpeed) {
		super(x, y, size, tilt, beginSpeed, 2000.0, 1000.0, 700.0, new Color(60,60,80), 0, "Standard");
	}

}
