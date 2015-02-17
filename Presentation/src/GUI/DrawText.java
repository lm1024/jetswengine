/**
 * 
 */
package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.font.TextAttribute;
import java.text.AttributedString;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author tjd511
 *
 */
@SuppressWarnings("serial")
public class DrawText extends JPanel {

	public static void main(String[] args) {
		new DrawText();
	}

	/**
	 * 
	 */
	public DrawText() {
		makeFrame();
	}

	public void display(Graphics g) {
		/* Create a new window using the Swing class JFrame and add this panel */
		drawSubstring(g,"normal", 200, 400, "Times New Roman", 20, new Color(0, 0, 0), false, false, false, false, false, false);
		drawSubstring(g,"bold", 200, 420, "Times New Roman", 20, new Color(0, 0, 0), true, false, false, false, false, false);
		drawSubstring(g,"italic", 200, 440, "Times New Roman", 20, new Color(0, 0, 0), false, true, false, false, false, false);
		drawSubstring(g,"underline", 200, 460, "Times New Roman", 20, new Color(0, 0, 0), false, false, true, false, false, false);
		drawSubstring(g,"strikethrough", 200, 480, "Times New Roman", 20, new Color(0, 0, 0), false, false, false, true, false, false);
		drawSubstring(g,"superscript", 200, 500, "Times New Roman", 20, new Color(0, 0, 0), false, false, false, false, true, false);
		drawSubstring(g,"subscript", 200, 520, "Times New Roman", 20, new Color(0, 0, 0), false, false, false, false, false, true);
		/*g.drawString("Hello\nworld!", 200, 400);
		String text = new String("Hello\nWorld!\nB");

		int y = 400;
		int x = 300;
		/*
		 * Splits string at every newline char and then prints it with newline
		 * added
		 *
		for (String line : text.split("\n")) {
			g.drawString(line, x, (y += g.getFontMetrics().getHeight()));
		}

		// int[] test = { 3, 1 };
		// for (int tests : test)
		// System.out.println(tests);

		Graphics2D g2 = (Graphics2D) g;

		String s = "www.java2s.com is g\nreat.";

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Font plainFont = new Font("Times New Roman", Font.PLAIN, 24);
		// System.out.println(g2.getFont());
		g2.setFont(null);
		AttributedString as = new AttributedString(s);
		// as.addAttribute(TextAttribute.FONT, plainFont, 18, 25);
		as.addAttribute(TextAttribute.SUPERSCRIPT, TextAttribute.SUPERSCRIPT_SUB, 0, 3);
		as.addAttribute(TextAttribute.SUPERSCRIPT, TextAttribute.SUPERSCRIPT_SUPER, 4, 7);
		as.addAttribute(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON, 8, 11);
		as.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON, 12, 15);
		as.addAttribute(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD, 16, 18);
		as.addAttribute(TextAttribute.POSTURE, TextAttribute.POSTURE_OBLIQUE, 19, 25);

		as.addAttribute(TextAttribute.BACKGROUND, new Color(231, 0, 255), 19, 25);

		System.out.println(s.length());
		g2.drawString(as.getIterator(), 24, 70);*/
	}

	public void drawSubstring(Graphics g, String s, int xPos, int yPos, String fontName, int fontSize, Color fontColor,
			boolean bold, boolean italic, boolean underline, boolean strikethrough, boolean superscript,
			boolean subscript) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(fontColor);
		Font stringFont = new Font(fontName, Font.PLAIN, fontSize);

		AttributedString as = new AttributedString(s);
		//as.addAttribute(TextAttribute.FONT, stringFont);
		
		if (bold){
			System.out.println("Bold!");
			as.addAttribute(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
		}
			
		if (italic)
			as.addAttribute(TextAttribute.POSTURE, TextAttribute.POSTURE_OBLIQUE);
		if (underline)
			as.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		if (strikethrough)
			as.addAttribute(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
		if (superscript)
			as.addAttribute(TextAttribute.SUPERSCRIPT, TextAttribute.SUPERSCRIPT_SUPER);
		if (subscript)
			as.addAttribute(TextAttribute.SUPERSCRIPT, TextAttribute.SUPERSCRIPT_SUB);

		g2.drawString(as.getIterator(), xPos, yPos);
	}

	/**
	 * Creates the window frame and adds the listener for exiting the program on
	 * window close
	 */
	public void makeFrame() {

		// Instantiate a window frame using Swing class JFrame
		JFrame frame = new JFrame("SmartSlidesTestEnvironment");

		// When the window is closed terminate the application
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		// set initial size of window
		frame.setSize(800, 600);

		// add the current object to the centre of the frame and make visible
		frame.getContentPane().add(this, BorderLayout.CENTER);
		frame.setVisible(true);
	}

	/** Method for what to do when updating graphics window */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		display(g);
	}

}
