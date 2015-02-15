package GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

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

	/**
	 * @param args
	 */

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
		firstOval = new Oval(100, 100, 200, 200, true, Color.black, 0, 0);
		firstRectangle = new Rectangle(100, 100, 200, 200, true, Color.black, 0, 0);
		firstLine = new Line(100, 100, 200, 200, Color.black, 0, 0);
		firstTriangle = new Triangle(100, 100, 200, 200, true, Color.black, 0, 0);

		aShape = firstOval;

		/* Create a new window using the Swing class JFrame and add this panel */
		makeFrame();
	}

	public void actionPerformed(ActionEvent e) {
		// Updates various variables depending on the object that updates
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
			if (shapeColor.getText().matches("\\p{XDigit}+") && shapeColor.getText().length() == 8)
				aShape.shapeColor = new Color((int) Long.parseLong(shapeColor.getText(), 16), aShape.hasalpha);
		} else if (e.getSource() == solid) {
			aShape.setSolid(solid.isSelected());
		}
		repaint();
	}

	// Provide this method to instruct Java what to do when repainting the
	// window
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		aShape.display(g);
	}

	// Create a window frame

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