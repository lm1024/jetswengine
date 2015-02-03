package server;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Console extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JTextField textInput;
	private JTextArea textOutput;
	private JScrollPane areaScrollPane;

	public Console() {

		// Use BorderLayout layout manager to arrange the label
        setLayout(new BorderLayout());
		
		// Instantiate a JTextArea to output the data
		textOutput = new JTextArea();
		
		// Prevent the user from editing it
		textOutput.setEditable(false); 
		
		// Make text wrap by words
		textOutput.setLineWrap(true);
		textOutput.setWrapStyleWord(true);
		
		//make it visible
		textOutput.setVisible(true);

		
		areaScrollPane = new JScrollPane(textOutput);
		areaScrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		// Add the areaScrollPane to the JPanel
		add(areaScrollPane, BorderLayout.CENTER); 

		// Instantiate a JTextFields for data entry
		textInput = new JTextField(20);
		add(textInput,BorderLayout.SOUTH); // Add the JTextFields to the JPanel
		textInput.addActionListener(this); // Register the JTextFields with Java

		// Create a new window using the Swing class JFrame and add this panel
		makeFrame();
	}

	/* 
	 * When text is entered or the button is pressed
	 * the following method will be called
	 */
	public void actionPerformed(ActionEvent e) {

		// If the "Read value" button is pressed
		if (e.getSource() == textInput) {

			// Add the text to the console
			textOutput.append("Username: " + textInput.getText() + "\n");

			// clear the inputed text in the JTextField
			textInput.setText("");

		}
	}
	
	public void printToConsole(String user, String message){
		textOutput.append(user + ": " + message + "\n");
	}
	
	// Create a window frame
	public void makeFrame() {

		// Instantiate a window frame using Swing class JFrame
		JFrame frame = new JFrame("Console");

		// When the window is closed terminate the application
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		// set initial size of window
		frame.setSize(300, 500);

		// add the current object to the centre of the frame and make visible
		frame.getContentPane().add(this,BorderLayout.CENTER);
		frame.setVisible(true);

	}
}
