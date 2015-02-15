package GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class GraphicsWindow extends JPanel implements ActionListener {

	private JComboBox<String> shapeChoice;
	private JTextField xStart, yStart, xEnd, yEnd, color;
	private JCheckBox solid;
	private Graphics aShape;
	private Oval firstOval;
    private Rectangle firstRectangle;
    private Line firstLine;
    private Triangle firstTriangle;
	/**
	 * @param args
	 */

	public static void main(String[] args) {
		// TODO Auto-generated method stub
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

		// Instantiate a JTextFields for data entry
		xStart = new JTextField(3);
		add(xStart); // Add the JTextFields to the JPanel
		xStart.addActionListener(this); // Register the JTextFields with Java

		// Instantiate a JTextFields for data entry
		yStart = new JTextField(3);
		add(yStart); // Add the JTextFields to the JPanel
		yStart.addActionListener(this); // Register the JTextFields with Java

		// Instantiate a JTextFields for data entry
		xEnd = new JTextField(3);
		add(xEnd); // Add the JTextFields to the JPanel
		xEnd.addActionListener(this); // Register the JTextFields with Java

		// Instantiate a JTextFields for data entry
		yEnd = new JTextField(3);
		add(yEnd); // Add the JTextFields to the JPanel
		yEnd.addActionListener(this); // Register the JTextFields with Java

		// Instantiate a JTextFields for data entry
		yEnd = new JTextField(9);
		add(yEnd); // Add the JTextFields to the JPanel
		yEnd.addActionListener(this); // Register the JTextFields with Java

		// Create a new window using the Swing class JFrame and add this panel
		makeFrame();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == xStart) {
			aShape.moveXY(100, 100);
		}

		else if (e.getSource() == yStart) {
			aShape.reSize(20);
		}

		else if (e.getSource() == xEnd) {
			aShape.reSize(-20);
		}

		else if (e.getSource() == yEnd) {
			aShape.reSize(-20);
		}

		// checkbox assigns object to aShape
		else if (e.getSource() == shapeChoice) {
			if (shapeChoice.getSelectedItem().equals("Oval"))
				aShape = firstOval;
			else if (shapeChoice.getSelectedItem().equals("Rectangle"))
				aShape = firstRectangle;
			else if (shapeChoice.getSelectedItem().equals("Line"))
				aShape = firstLine;
			else if (shapeChoice.getSelectedItem().equals("Triangle"))
				aShape = firstTriangle;
		}
		repaint();
	}

	// Provide this method to instruct Java what to do when repainting the
	// window
	public void paintComponent(Graphics g) {
		g.setColor(Color.black);
		super.printComponent(g);
	}

	// Create a window frame

	public void makeFrame() {

		// Instantiate a window frame using Swing class JFrame
		JFrame frame = new JFrame("Ex2");

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
