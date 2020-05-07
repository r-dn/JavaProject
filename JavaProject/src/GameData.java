import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/*
 * sources:
 * https://www.w3schools.com/java/java_files_create.asp
 * https://www.w3schools.com/java/java_files_read.asp
 */
public class GameData {
	int coins;
	boolean[] unlocked;
	int current;
	double highscore;

	public static final Bike[] bikes = {
			new StandardBike(Main.screenWidth / 2, Main.screenHeight / 3, Main.screenHeight / 5, 0, 0),
			new MonsterBike(Main.screenWidth / 2, Main.screenHeight / 3, Main.screenHeight / 5, 0, 0),
			new SpecialBike(Main.screenWidth / 2, Main.screenHeight / 3, Main.screenHeight / 5, 0, 0),
			new EnduranceBike(Main.screenWidth / 2, Main.screenHeight / 3, Main.screenHeight / 5, 0, 0), };

	public static Bike bike(int number) {

		if (number == 0) {
			return new StandardBike(Main.screenWidth / 8, Main.screenHeight / 2, Main.screenWidth / 8, 0, 1000);
		} else if (number == 1) {
			return new MonsterBike(Main.screenWidth / 8, Main.screenHeight / 2, Main.screenWidth / 8, 0, 1000);
		} else if (number == 2) {
			return new SpecialBike(Main.screenWidth / 8, Main.screenHeight / 2, Main.screenWidth / 8, 0, 1000);
		} else {
			return new EnduranceBike(Main.screenWidth / 8, Main.screenHeight / 2, Main.screenWidth / 8, 0, 1000);
		}
	}

	public GameData() {
		try {
			File dataFile = new File("gamedata.txt");
			Scanner myReader = new Scanner(dataFile);

			coins = Integer.parseInt(myReader.nextLine());
			unlocked = strToBoolArray(myReader.nextLine(), bikes.length);
			System.out.println(boolArrayToStr(unlocked));
			current = Integer.parseInt(myReader.nextLine());
			if (current >= bikes.length) {
				System.out.println("In gamedata.txt: current must be < " + bikes.length);
				current = 0;
			}
			highscore = Double.parseDouble(myReader.nextLine());

			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("gamedata.txt does not exist yet; creating and initialising file");
			createGameDataFile();
			coins =0;
			unlocked = new boolean[] {true, false, false, false};
			current =0;
			highscore = 0.0;
			// e.printStackTrace();
		}
	}

	public boolean createGameDataFile() {
		// creating the file
		try {
			File myObj = new File("gamedata.txt");
			if (myObj.createNewFile()) {
				System.out.println("File created: " + myObj.getName());
			} else {
				System.out.println("File already exists.");
				return false;
			}
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
			return false;
		}

		// initialising the file
		try {
			FileWriter myWriter = new FileWriter("gamedata.txt");

			myWriter.write("0\n" + "true,false,false,false\n" + "0\n" + "0.0");

			myWriter.close();
			System.out.println("Successfully initialised");
			return true;
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
			return false;
		}
	}

	public void save() {
		try {
			FileWriter myWriter = new FileWriter("gamedata.txt");

			myWriter.write(coins + "\n" + boolArrayToStr(unlocked) + "\n" + current + "\n" + highscore);

			myWriter.close();
			System.out.println("Successfully saved");
		} catch (IOException e) {
			System.out.println("An error occurred during saving");
			e.printStackTrace();

		}
	}

	public void drawCoins(Graphics g) {
		// align right
		String s = coins + " coins";
		g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 24));
		g.setColor(Color.black);
		FontMetrics fontMetrics = g.getFontMetrics();
		g.drawString(s, Main.screenWidth - 40 - fontMetrics.stringWidth(s), 72);
	}

	private static boolean[] strToBoolArray(String str, int length) {
		boolean[] ret = new boolean[length];
		String[] strArray = str.split(",");
		for (int i = 0; i < length; i++) {
			if (strArray[i].equals("true")) {
				ret[i] = true;
			} else if (strArray[i].equals("false")) {
				ret[i] = false;
			}
		}
		if (ret.length != length) {
			System.out.println("Following string cannot entirely be converted to boolean[]: \"" + str + "\"");
		}
		return ret;
	}

	private static String boolArrayToStr(boolean[] boolArray) {
		String ret = "";
		for (boolean bool : boolArray) {
			if (bool == true) {
				ret += "true,";
			} else if (bool == false) {
				ret += "false,";
			}
		}
		// het laatste karakter van ret is een komma, die mag weg
		return ret.substring(0, ret.length() - 1);
	}
}
