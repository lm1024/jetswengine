package SwingGUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Class to make graphics window to test drawing shapes
 * 
 * @version 1.0 16/02/2015
 * @author Tom Davidson
 */
@SuppressWarnings("serial")
public class GraphicsWindow extends JPanel implements ActionListener {

	private JComboBox<String> shapeChoice;
	private JTextField xStart, yStart, xEnd, yEnd, rotation, shapeColor;
	private JCheckBox solid;
	private Oval firstOval;
	private Rectangle firstRectangle;
	private Line firstLine;
	private Triangle firstTriangle;
	private Star firstStar;
	private RegularConvexPolygon firstPolygon;
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
		shapeChoice.addItem("Star");
		shapeChoice.addItem("N Sided Regular Convex Polygon");
		
		add(shapeChoice);
		shapeChoice.addActionListener(this);

		/* Instantiate JTextFields for xStart, yStart, xEnd, yEnd and shapeColor */
		xStart = new JTextField(3);
		add(xStart);
		xStart.addActionListener(this);
		xStart.setText(Integer.toString((int) defaultStartingCoordinate));

		yStart = new JTextField(3);
		add(yStart);
		yStart.addActionListener(this);
		yStart.setText(Integer.toString((int) defaultStartingCoordinate));

		xEnd = new JTextField(3);
		add(xEnd);
		xEnd.addActionListener(this);
		xEnd.setText(Integer.toString((int) defaultEndingCoordinate));

		yEnd = new JTextField(3);
		add(yEnd);
		yEnd.addActionListener(this);
		yEnd.setText(Integer.toString((int) defaultEndingCoordinate));

		shapeColor = new JTextField(8);
		add(shapeColor);
		shapeColor.addActionListener(this);
		shapeColor.setText("00000000");
		
		rotation = new JTextField(3);
		add(rotation);
		rotation.addActionListener(this);
		rotation.setText("0");

		/* Instantiate a JCheckBox for if shape is solid */
		solid = new JCheckBox();
		add(solid);
		solid.addActionListener(this);
		solid.setSelected(true);

		/* Set initial shape variables */
		int xStart = 100;
		int yStart = 100;
		int xEnd = 200;
		int yEnd = 200;
		boolean solid = true;
		Color shapeColor = Color.black;
		float rotation = 0;
		float duration = 0;
		float startTime = 0;
		
		/* N FOR SIDES ON POLYGON */
		int numberOfSides = 5;
		
		/* Instantiates shape classes */
		firstOval = new Oval(xStart, yStart, xEnd, yEnd, solid, shapeColor, rotation, duration, startTime);
		firstRectangle = new Rectangle(xStart, yStart, xEnd, yEnd, solid, shapeColor, rotation, duration, startTime);
		firstLine = new Line(xStart, yStart, xEnd, yEnd, shapeColor, rotation, duration, startTime);
		firstTriangle = new Triangle(xStart, yStart, xEnd, yEnd, solid, shapeColor, rotation, duration, startTime);
		firstStar = new Star(xStart, yStart, xEnd, yEnd, solid, shapeColor, rotation, duration, startTime);
		firstPolygon = new RegularConvexPolygon(xStart, yStart, xEnd, yEnd, numberOfSides, solid, shapeColor, rotation, duration, startTime);
		
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
				break;
			case "Star":
				aShape = firstStar;
				break;
			case "N Sided Regular Convex Polygon":
				aShape = firstPolygon;
			}

		} else if (e.getSource() == xStart) {
			aShape.setXStart(Integer.parseInt(xStart.getText()));
		} else if (e.getSource() == yStart) {
			aShape.setYStart(Integer.parseInt(yStart.getText()));
		} else if (e.getSource() == xEnd) {
			aShape.setXEnd(Integer.parseInt(xEnd.getText()));
		} else if (e.getSource() == yEnd) {
			aShape.setYEnd(Integer.parseInt(yEnd.getText()));
		} else if (e.getSource() == shapeColor) {
			// checks for a 8 digit long hex number
			String colorText = new String(shapeColor.getText());
			if (colorText.matches("\\p{XDigit}+") && colorText.length() == 8)
				aShape.shapeColor = new Color((int) Long.parseLong(
						shapeColor.getText(), 16), aShape.hasalpha);
		} else if (e.getSource() == rotation) {
			aShape.setRotation(Float.parseFloat(rotation.getText()));			
		} else if (e.getSource() == solid) {
			aShape.setSolid(solid.isSelected());
		}
		repaint();
	}

	/** Method for what to do when updating graphics window */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		aShape.display(g);
		
		/* Uncomment these lines if you want to see the shading that Tom has been playing with!!!!! */
		Shading aShade = new Shading();
		aShade.display(g);
		
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
}
