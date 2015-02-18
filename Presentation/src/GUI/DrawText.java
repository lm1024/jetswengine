/**
 * 
 */
package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.font.TextAttribute;
import java.text.AttributedString;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;

import javafx.*;


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
		 //GraphicsEnvironment e =
		 //GraphicsEnvironment.getLocalGraphicsEnvironment();
		 //Font[] fonts = e.getAllFonts(); // Get the fonts
		 //for (Font f : fonts) {
		 //System.out.println(f.getFontName());
		 //}
		
		/*String fontna = "Arial";
		drawSubstring(g, "normal", 200, 400, fontna, 20, new Color(0, 0, 0), false, false, false, false, false, false);
		drawSubstring(g, "bold", 200, 420, fontna, 20, new Color(0, 0, 0), true, false, false, false, false, false);
		drawSubstring(g, "italic", 200, 440, fontna, 20, new Color(0, 0, 0), false, true, false, false, false, false);
		drawSubstring(g, "underline", 200, 460, fontna, 20, new Color(0, 0, 0), false, false, true, false, false, false);
		drawSubstring(g, "strikethrough", 200, 480, fontna, 20, new Color(0, 0, 0), false, false, false, true, false,
				false);
		drawSubstring(g, "superscript", 200, 500, fontna, 20, new Color(0, 0, 0), false, false, false, false, true,
				false);
		drawSubstring(g, "subscript", 200, 520, fontna, 20, new Color(0, 0, 0), false, false, false, false, false, true);
		*/

		// g.drawString("Hello\nworld!", 200, 400);
		// String text = new String("Hello\nWorld!\nB");

		// int y = 400;
		// int x = 300;
		/*
		 * Splits string at every newline char and then prints it with newline
		 * added
		 */
		// for (String line : text.split("\n")) {
		// g.drawString(line, x, (y += g.getFontMetrics().getHeight()));
		// }

		// int[] test = { 3, 1 };
		// for (int tests : test)
		// System.out.println(tests);

		/*
		Graphics2D g2 = (Graphics2D) g;

		String s = "www.java2s.com is g\nreat.";

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Font plainFont = new Font("Arial", Font.PLAIN, 30);
		// System.out.println(g2.getFont());
		g2.setFont(plainFont);
		AttributedString as = new AttributedString(s);
		
		// as.addAttribute(TextAttribute.FONT, plainFont);
		
		as.addAttribute(TextAttribute.SUPERSCRIPT, TextAttribute.SUPERSCRIPT_SUB, 0, 3);
		as.addAttribute(TextAttribute.SUPERSCRIPT, TextAttribute.SUPERSCRIPT_SUPER, 4, 7);
		as.addAttribute(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON, 8, 11);
		as.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON, 12, 15);
		as.addAttribute(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD, 16, 18);
		as.addAttribute(TextAttribute.POSTURE, TextAttribute.POSTURE_OBLIQUE, 19, 25);
		as.addAttribute(TextAttribute.BACKGROUND, new Color(231, 0, 255), 19, 25);
		
		g2.drawString(as.getIterator(), 24, 70);
		*/
		

		Group root = new Group();
		Scene s = new Scene(root, 300, 300, Color.BLACK);
		
		final Canvas canvas = new Canvas(250,250);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		gc.setFill(Color.BLUE);
		gc.fillRect(75,75,100,100);
		
		root.getChildren().add(canvas);
 
		
	}

	public void drawSubstring(Graphics g, String s, int xPos, int yPos, String fontName, int fontSize, Color fontColor,
			boolean bold, boolean italic, boolean underline, boolean strikethrough, boolean superscript,
			boolean subscript) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(fontColor);

		Font stringFont = new Font(fontName, Font.BOLD, fontSize);
		System.out.println(g2.getFont().getFontName());
		AttributedString as = new AttributedString(s);
		
		as.addAttribute(TextAttribute.FONT, stringFont);

		if (bold) 
			as.addAttribute(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
		if (italic)
			as.addAttribute(TextAttribute.POSTURE, TextAttribute.POSTURE_OBLIQUE);
		if (underline)
			as.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		if (strikethrough)
			as.addAttribute(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
		if (superscript)
			as.addAttribute(TextAttribute.SUPERSCRIPT, TextAttribute.SUPERSCRIPT_SUPER);
		if (subscript) {
			//HashMap attrMap = new HashMap();
			//attrMap.put(TextAttribute.SUPERSCRIPT, TextAttribute.SUPERSCRIPT_SUB);
			
			//stringFont = stringFont.deriveFont(attrMap);
			as.addAttribute(TextAttribute.SUPERSCRIPT, TextAttribute.SUPERSCRIPT_SUB);
		}
			
		
		
		
		//as.addAttribute(TextAttribute.FONT, stringFont);

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
