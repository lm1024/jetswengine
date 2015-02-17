/**
 * 
 */
package GUI;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.TextAttribute;
import java.text.AttributedString;

/**
 * @author tjd511
 *
 */
public class DrawText {

	/**
	 * 
	 */
	public DrawText() {
		// TODO Auto-generated constructor stub
	}
	
	public void display(Graphics g) {

		g.drawString("Hello\nworld!", 200, 400);
		String text = new String("Hello\nWorld!\nB");

		int y = 400;
		int x = 300;
		/*
		 * Splits string at every newline char and then prints it with newline
		 * added
		 */
		for (String line : text.split("\n")) {
			g.drawString(line, x, (y += g.getFontMetrics().getHeight()));
		}

		// int[] test = { 3, 1 };
		// for (int tests : test)
		// System.out.println(tests);

		Graphics2D g2 = (Graphics2D) g;

		String s = "\"www.java2s.com\" is g\nreat.";

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		Font plainFont = new Font("Times New Roman", Font.PLAIN, 24);
		// System.out.println(g2.getFont());
		g2.setFont(null);
		AttributedString as = new AttributedString(s);
		as.addAttribute(TextAttribute.FONT, plainFont, 18, 25);
		as.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON, 1,
				15);
		as.addAttribute(TextAttribute.STRIKETHROUGH,
				TextAttribute.STRIKETHROUGH_ON, 18, 25);
		
		g2.drawString(as.getIterator(), 24, 70);
	}

}
