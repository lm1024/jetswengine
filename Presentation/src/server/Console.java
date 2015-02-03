package server;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.PrintStream;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Console extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JTextField textInput;
	private JTextArea textOutput;
	private JScrollPane areaScrollPane;
	private JCheckBox toggleSystemStream;
	private JLabel checkBoxLabel;
	private final PrintStream origOut = System.out;
	private final PrintStream interceptor = new Interceptor(origOut);
	private JPanel topPanel;

	public Console() {
		// Use BorderLayout layout manager to arrange the label
		setLayout(new BorderLayout());
		
		topPanel = new JPanel();
		
		toggleSystemStream = new JCheckBox();
		topPanel.add(toggleSystemStream,BorderLayout.WEST);
		toggleSystemStream.addActionListener(this);
		
		checkBoxLabel = new JLabel();
		checkBoxLabel.setText("Display System Messages");
		topPanel.add(checkBoxLabel,BorderLayout.EAST);
		
		add(topPanel,BorderLayout.NORTH);

		// Instantiate a JTextArea to output the data
		textOutput = new JTextArea();

		// Prevent the user from editing it
		textOutput.setEditable(false);

		// Make text wrap by words
		textOutput.setLineWrap(true);
		textOutput.setWrapStyleWord(true);

		// make it visible
		textOutput.setVisible(true);

		areaScrollPane = new JScrollPane(textOutput);
		areaScrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		// Add the areaScrollPane to the JPanel
		add(areaScrollPane, BorderLayout.CENTER);

		// Instantiate a JTextFields for data entry
		textInput = new JTextField(20);
		add(textInput, BorderLayout.SOUTH); // Add the JTextFields to the JPanel
		textInput.addActionListener(this); // Register the JTextFields with Java

		// Create a new window using the Swing class JFrame and add this panel
		makeFrame();
	}

	/*
	 * When text is entered or the button is pressed the following method will
	 * be called
	 */
	public void actionPerformed(ActionEvent e) {

		// If the "Read value" button is pressed
		if (e.getSource() == textInput) {
			if (!textInput.getText().isEmpty()) {
				// Add the text to the console
				textOutput.append("Username: " + textInput.getText() + "\n");

				// clear the inputed text in the JTextField
				textInput.setText("");
			}

		} else if (e.getSource() == toggleSystemStream) {
			if(toggleSystemStream.isSelected()) {
				System.setOut(interceptor); // enable the interceptor
				System.out.println("Interceptor enabled");
			} else {
				System.out.println("Interceptor disabled");
				System.setOut(origOut);
			}
			
			
		}
	}

	public void printToConsole(String user, String message) {
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

			public void windowOpened(WindowEvent e) {
				textInput.requestFocus();
			}
		});

		// set initial size of window
		frame.setSize(600, 800);

		// add the current object to the centre of the frame and make visible
		frame.getContentPane().add(this, BorderLayout.CENTER);
		frame.setVisible(true);

	}
}
