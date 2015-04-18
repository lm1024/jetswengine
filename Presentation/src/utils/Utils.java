/**
 * 
 */
package utils;

import java.util.HashMap;
import org.xml.sax.Attributes;
import javafx.scene.text.Font;

/**
 * @author David
 *
 */
// final, because it's not supposed to be subclassed
public final class Utils {

	// private constructor to avoid unnecessary instantiation of the class
	private Utils() {

	}
	
	public static boolean parse(HashMap<String, String> hashMap,
			Attributes attributes, String... strings) {
		boolean errors = false;
		String temp;
		for (String string : strings) {
			temp = attributes.getValue(string);
			if (temp != null) {
				hashMap.put(string, temp);
			} else {
				errors = true;
			}
		}
		return errors;
	}

	public static String capitaliseEachFirstLetter(String s) {
		try {
			String[] words = s.split(" ");
			String finalString = "";
			for (String word : words) {
				finalString += word.substring(0, 1).toUpperCase()
						+ word.substring(1).toLowerCase() + " ";
			}
			return finalString.substring(0, finalString.length() - 1);
		} catch (NullPointerException npe) {
			return null;
		}
	}

	public static boolean withinRangeInclusive(float min, float max, float value) {
		return (value >= min) && (value <= max);
	}

	public static boolean withinRangeExclusive(float min, float max, float value) {
		return (value > min) && (value < max);
	}
	
	public static boolean withinRangeInclusive(int min, int max, int value) {
		return (value >= min) && (value <= max);
	}

	public static boolean withinRangeExclusive(int min, int max, int value) {
		return (value > min) && (value < max);
	}

	public static boolean withinRangeInclusive(double min, double max,
			double value) {
		return (value >= min) && (value <= max);
	}

	public static boolean withinRangeExclusive(double min, double max,
			double value) {
		return (value > min) && (value < max);
	}

	public static boolean validARGB(String string) {
		try {
			return string.matches("^([#]([0-9a-fA-F]{8}))$");
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean validAlignment(String string) {
		try {
			return string.matches("left|right|centre|none");
		} catch (Exception e) {
			return false;
		}
	}
	
	public static boolean validTextCase(String string) {
		try {
			return string.matches("upper|lower|camel|none");
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean validFont(String string) {
		try {
			return Font.getFontNames().contains(string);
		} catch (Exception e) {
			return false;
		}
	}
}
