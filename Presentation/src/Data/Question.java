package Data;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author tjd511
 * @version 1.0 14/04/2015
 */
public class Question {
	private String id = null;
	private String logfile = null; // TODO add a folder extension to this in the
									// parser!!!
	private ArrayList<Answer> answers;

	private static final String FILE_FOLDER = "logfiles";
	private static final String FILE_EXTENSION = ".csv";

	private static final String COMMA_DELIMITER = ",";
	private static final String NEWLINE_DELIMITER = "\n";

	/**
	 * 
	 */
	public Question(String id, String logfile) {
		/* Set the variables based upon the constructor arguments */
		this.id = id;

		if (logfile != null) {
			this.logfile = logfile;
		} else {
			System.err.println("The logfile specified is invalid. Quitting!");
			System.exit(-2);
		}

		/* Instantiate the answer list */
		answers = new ArrayList<Answer>();
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the logfile
	 */
	public String getLogfile() {
		return logfile;
	}

	/**
	 * @return the answer
	 */
	public Answer getAnswer(int index) {
		return answers.get(index);
	}

	public ArrayList<Answer> getAnswers() {
		return answers;
	}

	/**
	 * @param answers
	 *            the answers to set
	 */
	public void addAnswer(String answerId, boolean correct) {
		/* Make a new answer and add it to the list of answers */
		answers.add(new Answer(answerId, correct));
	}

	/**
	 * Validates that the question has answers stored inside of it.
	 * 
	 * @return a boolean containing if the question has at least one answer that
	 *         is non zero.
	 */
	public boolean hasAnswerData() {
		for (Answer answer : answers) {
			if (answer.getAnswerCount() > 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @return
	 */
	public int getNumberOfAnswers() {
		return answers.size();
	}

	/**
	 * @param index
	 */
	public void increaseAnswerCount(int index) {
		if (index < answers.size() && index >= 0) {
			answers.get(index).increaseAnswerCount();
		}
	}

	public void writeLogfile() {
		/* Create a new reference to the folder for the files. */
		File folder = new File(FILE_FOLDER);

		/* Try to create the folder if it doesn't already exist. */
		if (!folder.exists() || !folder.isDirectory()) {
			System.out.println("folder no exist");
			folder.mkdirs();
		}

		/* Save the data if there is data to be saved, and the folder exists. */
		if (this.hasAnswerData() && folder.isDirectory()) {
			try {
				/* The format for saving the date and time in the filename. */
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
				Date date = new Date();

				/* Create the filepath for the logfile */
				String filepath = FILE_FOLDER + "/" + dateFormat.format(date) + " " + this.getLogfile()
						+ FILE_EXTENSION;

				/* Create a new filewriter to create the csv file. */
				FileWriter writer = new FileWriter(filepath);

				/* Write the question ID as the first line in the file. */
				writer.append(this.getId());
				writer.append(NEWLINE_DELIMITER);

				/*
				 * Loop through all the answers, writing the relevent info to a
				 * new line.
				 */
				for (Answer answer : this.getAnswers()) {
					writer.append(answer.getId());
					writer.append(COMMA_DELIMITER);
					writer.append(Integer.toString(answer.getAnswerCount()));
					writer.append(NEWLINE_DELIMITER);
				}

				/* Flush the stream and close the filewriter */
				writer.flush();
				writer.close();

			} catch (IOException e) {
				e.printStackTrace(); //TODO
			}
		}
	}
}
