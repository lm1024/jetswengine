package GUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.PrintStream;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import server.Interceptor;
import server.Server;

public class Console extends Server implements ActionListener {

	private JPanel panel;
	private JTextField textInput;
	private JTextArea textOutput;
	private JScrollPane areaScrollPane;
	private JCheckBox toggleSystemStream;
	private JLabel checkBoxLabel;
	private final PrintStream origOut = System.out;
	private final PrintStream interceptor = new Interceptor(origOut);
	private JPanel topPanel;
	//private CommandProcessor commandProcessor = new CommandProcessor();
	private String processorUsername = "Command Processor";
	private String username = "Username";

	public Console() {
		
		panel = new JPanel();
		// Use BorderLayout layout manager to arrange the label
		panel.setLayout(new BorderLayout());

		topPanel = new JPanel();

		toggleSystemStream = new JCheckBox();
		topPanel.add(toggleSystemStream, BorderLayout.WEST);
		toggleSystemStream.addActionListener(this);

		checkBoxLabel = new JLabel();
		checkBoxLabel.setText("Display System Messages");
		topPanel.add(checkBoxLabel, BorderLayout.EAST);

		panel.add(topPanel, BorderLayout.NORTH);

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
		panel.add(areaScrollPane, BorderLayout.CENTER);

		// Instantiate a JTextFields for data entry
		textInput = new JTextField(20);
		panel.add(textInput, BorderLayout.SOUTH); // Add the JTextFields to the JPanel
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
				textOutput.append( getUsername() + ": " + textInput.getText() + "\n");

				/*
				 *  Splits the command into an array of
				 *  strings for the console to process
				 */
				processCommand(textInput.getText().split("\\s+"));

				// clear the inputed text in the JTextField
				textInput.setText("");
			}

		} else if (e.getSource() == toggleSystemStream) {
			if (toggleSystemStream.isSelected()) {
				System.setOut(interceptor); // enable the interceptor
				System.out.println("Interceptor enabled");
			} else {
				System.out.println("Interceptor disabled");
				System.setOut(origOut);
			}

		}
	}

	public void printToConsole(String user, String message) {
		textOutput.append(user + ":\n" + message + "\n");
	}

	// Create a window frame
	private void makeFrame() {

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
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		frame.setVisible(true);

	}
	
	/**
	 * @return the processorUsername
	 */
	public String getProcessorUsername() {
		return processorUsername;
	}
	
	/**
	 * @return the Username
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * @Param Set Username
	 */
	public void setUsername(String user) {
		this.username = user;
	}
	
	private void close(String[] args) {
		System.out.println("Received exit command. Now exiting.");
		//do stuff before exiting now
		System.exit(0);
	}
	
	private void open(String[] args) {
		if(args.length == 2) {
			setCurrentSlideshow(args[1]);
		} else {
			printToConsole(getProcessorUsername(),"Invalid arguements for command \"Open\"");
		}
	}
	
	private void print(String[] args) {
		if(args.length > 1) {
			if(args[1].toLowerCase().equals("filename")) {
				printToConsole(getProcessorUsername(),args[1] + ": " + getCurrentSlideshow());
			} else if(args[1].toLowerCase().equals("directory")) {
				printToConsole(getProcessorUsername(),"Active Directory:\n" + System.getProperty("user.dir"));
			}
		} else {
			printToConsole(getProcessorUsername(),"Invalid arguements for command \"" + args[0] + "\"");
		}
	}
	
	private void set(String[] args) {
		if(args.length > 2) {
			if(args[1].toLowerCase().equals("username")) {
				setUsername(args[2]);
				printToConsole(processorUsername,"Username Updated");
			}
		} else {
			printToConsole(processorUsername,"Invalid arguements for command \"" + args[0] + "\"");
		}
	}
	
	
	
	public void processCommand(String[] args) {
		
//		try {
//			Method method = this.getClass().getMethod(args[0]);
//			try {
//				  method.invoke(this, args);
//				} catch (IllegalArgumentException e) {
//					
//				} catch (IllegalAccessException e) {
//					
//				} catch (InvocationTargetException e) {
//					
//				}
//			} catch (SecurityException e) {
//			  // ...
//			} catch (NoSuchMethodException e) {
//			  // ...
//			}
		
		
		switch(args[0].toLowerCase()) {
		
			case "close":
			case "exit":
				close(args);
				break;
				
			case "open":
				open(args);
				break;
			
			case "print":
				print(args);
				break;
				
			case "set":
				set(args);
				break;
				
			default:
				printToConsole(processorUsername,"Unknown command: \"" + args[0] + "\"");
				break;
			
	}
}
}
