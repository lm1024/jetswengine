package GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


/** Class to make graphics window to test drawing shapes
 * 
 * @version 1.0 16/02/2015
 * @author Tom Davidson */
@SuppressWarnings("serial")
public class GraphicsWindow extends JPanel implements ActionListener {

	private JComboBox<String> shapeChoice;
	private JTextField xStart, yStart, xEnd, yEnd, shapeColor;
	private JCheckBox solid;
	private Oval firstOval;
	private Rectangle firstRectangle;
	private Line firstLine;
	private Triangle firstTriangle;
	private Shapes aShape;
	
	final float defaultStartingCoordinate = 100;
	final float defaultEndingCoordinate = 200;

	public static void main(String[] args) {
		new GraphicsWindow();
	}
	
	
	
	public GraphicsWindow() {
		shapeChoice = new JComboBox<String>();
		shapeChoice.addItem("Oval");
		shapeChoice.addItem("Rectangle");
		shapeChoice.addItem("Line");
		shapeChoice.addItem("Triangle");
		add(shapeChoice);
		shapeChoice.addActionListener(this);

		/* Instantiate JTextFields for xStart, yStart, xEnd, yEnd and shapeColor*/
		xStart = new JTextField(3);
		add(xStart); 
		xStart.addActionListener(this); 
		xStart.setText(Integer.toString((int)defaultStartingCoordinate));

		yStart = new JTextField(3);
		add(yStart); 
		yStart.addActionListener(this); 
		yStart.setText(Integer.toString((int)defaultStartingCoordinate));
		
		xEnd = new JTextField(3);
		add(xEnd); 
		xEnd.addActionListener(this); 
		xEnd.setText(Integer.toString((int)defaultEndingCoordinate));
		
		yEnd = new JTextField(3);
		add(yEnd); 
		yEnd.addActionListener(this); 
		yEnd.setText(Integer.toString((int)defaultEndingCoordinate));

		shapeColor = new JTextField(8);
		add(shapeColor); 
		shapeColor.addActionListener(this);
		shapeColor.setText("00000000");
		
		/* Instantiate a JCheckBox for if shape is solid */
		solid = new JCheckBox();
		add(solid);
		solid.addActionListener(this);
		solid.setSelected(true);

		/* Instantiates shape classes */
		firstOval = new Oval(56, 58, 144, 70, true, Color.black, 0, 0);
		firstRectangle = new Rectangle(100, 100, 200, 200, true, Color.black, 0, 0);
		firstLine = new Line(100, 100, 200, 200, Color.black, 0, 0);
		firstTriangle = new Triangle(100, 100, 200, 200, true, Color.black, 0, 0);

		aShape = firstOval;

		/* Create a new window using the Swing class JFrame and add this panel */
		makeFrame();
	}
	
	/** Method called whenever any item is updated */
	public void actionPerformed(ActionEvent e) {
		// Calls methods/updates variables depending on the object that updates
		if (e.getSource() == shapeChoice) {
			// Set the selected shape to the one chosen in the shapeChoice menu
			switch ((String) shapeChoice.getSelectedItem()) {
			case "Oval":
				aShape = firstOval;
				break;
			case "Rectangle":
				aShape = firstRectangle;
				break;
			case "Line":
				aShape = firstLine;
				break;
			case "Triangle":
				aShape = firstTriangle;
			}

		} else if (e.getSource() == xStart) {
			aShape.setXStart(Integer.parseInt(xStart.getText()));
		} else if (e.getSource() == yStart) {
			aShape.setYStart(Integer.parseInt(yStart.getText()));
		} else if (e.getSource() == xEnd) {
			aShape.setXEnd(Integer.parseInt(xEnd.getText()));
		} else if (e.getSource() == yEnd) {
			System.out.println(yEnd.getText());
			aShape.setYEnd(Integer.parseInt(yEnd.getText()));
		} else if (e.getSource() == shapeColor) {
			// checks for a 8 digit long hex number
			String colorText = new String(shapeColor.getText());
			if (colorText.matches("\\p{XDigit}+") && colorText.length() == 8)
				aShape.shapeColor = new Color((int) Long.parseLong(shapeColor.getText(), 16), aShape.hasalpha);
		} else if (e.getSource() == solid) {
			aShape.setSolid(solid.isSelected());
		}
		repaint();
	}

	/** Method for what to do when updating graphics window */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		aShape.display(g);

		Font f = new Font("Arial", Font.PLAIN, 12);
		g.setFont(f);
		g.drawString("Arial - PLAIN - 12", 300, 130);

		f = new Font("Arial", Font.ITALIC, 10);
		g.setFont(f);
		g.drawString("Arial - ITALIC - 10", 300, 160);

		f = new Font("Arial", Font.BOLD | Font.ITALIC, 14);
		g.setFont(f);
		g.drawString("Arial - BOLD and ITALIC - 14", 300, 190);

		f = new Font("Arial", Font.PLAIN, 12);
		g.setFont(f);
		g.drawString("Arial - PLAIN - 12", 300, 220);

		f = new Font("Arial", Font.BOLD + Font.ITALIC, 10);
		g.setFont(f);
		g.drawString("Arial - BOLD and ITALIC - 10", 300, 250);
		
		// Graphics2D g2 = (Graphics2D) g;
		FontRenderContext frc = ((Graphics2D) g).getFontRenderContext();		
		String s = "www.Éava2s.com";
		Rectangle2D bounds = ((Graphics2D) g).getFont().getStringBounds(s, frc);
		float width = (float) bounds.getWidth();
		float height = (float) bounds.getHeight();
		
		int centerX = 100;
		int baselineY = 70;
		
		System.out.println(centerX-width/2);
		System.out.println(centerX+width/2);
		
		System.out.println(baselineY-height);
		System.out.println(baselineY);
		
		g.setColor(Color.white);
		g.drawString(s, (int) (centerX - width / 2), baselineY);
		

	}

	/** Creates the window frame and adds the listener for exiting the program on window close */
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
}
