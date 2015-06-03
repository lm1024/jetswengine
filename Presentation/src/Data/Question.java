/** (c) Copyright by WaveMedia. */
package Data;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Class to store information about a question in a slideshow.
 * 
 * @author tjd511
 * @version 2.0 01/06/2015
 */
public class Question {
	/* Variables to store information about the question. */
	private String id;
	private String logfile;
	private ArrayList<Answer> answers;

	/*
	 * Hash map to keep track of if a IP has sent a response previously, and if
	 * so, what answer they selected.
	 */
	private HashMap<String, Integer> IPs;

	/* Final variables containing information for the CSV saving method. */
	private final String FILE_FOLDER = "logfiles";
	private final String FILE_EXTENSION = ".csv";
	private final String COMMA_DELIMITER = ",";
	private final String NEWLINE_DELIMITER = "\n";

	/*
	 * Hash map to keep track of unique IDs for each IP for when the answers are
	 * saved to CSV.
	 */
	private HashMap<String, Integer> IPMap;

	/*
	 * List to store all recieved responses, their unique ID and the time of the
	 * responce.
	 */
	private ArrayList<String> answerTimeData;

	/**
	 * Constructor for the question class.
	 * 
	 * @param id
	 *            the ID for the question.
	 * @param logfile
	 *            the name to be used in saving the answers to CSV.
	 */
	public Question(String id, String logfile) {
		/* Ensure that both ID and logfile have a value. */
		if (id == null) {
			System.out.println("Unspecified ID found in Question");
			id = "UnspecifiedID";
		}
		this.id = id;

		if (logfile == null) {
			System.out.println("Unspecified Filename found in Question");
			logfile = "UnspecifiedFilename";
		}
		this.logfile = logfile;

		/* Instantiate the other variables. */
		answers = new ArrayList<Answer>();
		IPs = new HashMap<String, Integer>();
		IPMap = new HashMap<String, Integer>();
		answerTimeData = new ArrayList<String>();
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

	/**
	 * @return an array list of all of the recieved answers.
	 */
	public ArrayList<Answer> getAnswers() {
		return answers;
	}

	/**
	 * Method adds a answer to the answer list for this question.
	 * 
	 * @param answers
	 *            the answers to set
	 */
	public void addAnswer(String answerId, boolean correct) {
		/* Ensure that answerId contains a valid string. */
		if (answerId == null) {
			System.out.println("Answer found with unspecified ID");
			answerId = Integer.toString(answers.size() + 1);
		}
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
	 * @return the number of answers in the list.
	 */
	public int getNumberOfAnswers() {
		return answers.size();
	}

	/**
	 * Increases the answer at the index specified by one of the parameters.
	 * Also checks that the IP specified has not answered previously. If it has,
	 * it decrements the last answer received before incrementing the new
	 * choice.
	 * 
	 * @param index
	 *            the index of the answer to increase.
	 * @param ip
	 *            the IP of the source of the answer.
	 */
	public void increaseAnswerCount(int index, String ip) {
		System.out.println(index + " " + ip );
		/* Ensure that the index exists in the list. */
		if (index < answers.size() && index >= 0) {

			/* Check if there has been a earlier response to this answer. */
			if (IPs.get(ip) == null) {
				/*
				 * If there has not, add this answer to the hashmap and
				 * increment the received answer.
				 */
				IPs.put(ip, index);
				answers.get(index).increaseAnswerCount();
			} else {
				/*
				 * If there has, decrement the previous answer, before updating
				 * the hashmap and incrementing the received answer.
				 */
				answers.get(IPs.get(ip)).decreaseAnswerCount();
				IPs.put(ip, index);
				answers.get(index).increaseAnswerCount();
			}

			/* Store the answer so it can be saved later. */
			storeAnswerTime(ip, index);

		}
	}

	/**
	 * Method saves data about the response in a list so that it can be saved to
	 * file later.
	 * 
	 * @param ip
	 *            the IP of the response
	 * @param answer
	 *            the answer received
	 */
	private void storeAnswerTime(String ip, int answer) {
		if (IPMap.get(ip) == null) {
			/* IP not been stored before. */
			int currentIndex = IPMap.size();
			IPMap.put(ip, currentIndex);
			answerTimeData.add(System.nanoTime() + "," + currentIndex + "," + answer);
		} else {
			/* IP has been stored before */
			int currentIndex = IPMap.get(ip);
			answerTimeData.add(System.nanoTime() + "," + currentIndex + "," + answer);
		}
	}

	/**
	 * Method saves all of the answer data to a CSV file.
	 */
	public void writeLogfile() {
		/* Create a new reference to the folder for the files. */
		File folder = new File(FILE_FOLDER);

		/* Try to create the folder if it doesn't already exist. */
		if (!folder.exists() || !folder.isDirectory()) {
			folder.mkdirs();
		}

		/* Save the data if there is data to be saved, and the folder exists. */
		if (this.hasAnswerData() && folder.isDirectory()) {
			try {
				/* The format for saving the date and time in the filename. */
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
				Date date = new Date();

				/* Create the filepath for the logfile */
				String filepath = FILE_FOLDER + "/"
						+ dateFormat.format(date)
						+ " "
						+ this.getLogfile()
						+ FILE_EXTENSION;

				/* Create a new filewriter to create the csv file. */
				FileWriter writer = new FileWriter(filepath);

				/* Write the question ID as the first line in the file. */
				writer.append(this.getId());
				writer.append(COMMA_DELIMITER);
				writer.append(Integer.toString(getNumberOfAnswers()));
				writer.append(NEWLINE_DELIMITER);

				/* Write the column headings. */
				writer.append("Answer ID");
				writer.append(COMMA_DELIMITER);
				writer.append("Answer Count");
				writer.append(NEWLINE_DELIMITER);

				/*
				 * Loop through all the answers, writing the relevant info to a
				 * new line.
				 */
				for (Answer answer : this.getAnswers()) {
					writer.append(answer.getId());
					writer.append(COMMA_DELIMITER);
					writer.append(Integer.toString(answer.getAnswerCount()));
					writer.append(NEWLINE_DELIMITER);
				}

				writer.append(NEWLINE_DELIMITER);

				/*
				 * Loop through all of the answer time data and save this in a
				 * column below the received answer columns.
				 */
				for (String s : answerTimeData) {
					writer.append(s);
					writer.append(NEWLINE_DELIMITER);
				}

				/* Flush the stream and close the filewriter. */
				writer.flush();
				writer.close();

			} catch (IOException e) {
				System.err.println("Error whilst trying to save data to CSV.");
			}
		}
	}
}
