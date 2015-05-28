/** (c) Copyright by WaveMedia. */
package utils;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Class encapsulates the methods for creating a hex string as a function of the
 * last two numbers present in the local IP address.
 * 
 * @author tjd511
 * @version 1.0 21/05/2015
 */
public class IPEncoder {

	private String IPAddress;
	private String IPCode;

	/**
	 * Constructor for the IPEncoder class.
	 */
	public IPEncoder() {
		try {
			this.IPAddress = Inet4Address.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			System.err.println("The local host name could not be resolved into an address!");
			System.exit(-1);
		}

		this.encodeIPAddress();
	}

	/**
	 * Returns the IP address of the machine.
	 * 
	 * @return a string containing the IP address
	 */
	public String getIPAddress() {
		return IPAddress;
	}

	/**
	 * Returns a hex string which is a concatenation of hex conversions of the
	 * last two numbers in the IP address.
	 * 
	 * @return a 4 digit hex string related to the last 2 numbers in the IP
	 *         address
	 */
	public String getIPCode() {
		return IPCode;
	}

	/**
	 * Method populates the IPCode field of this class as a function of the last
	 * two sections of the stored IP address.
	 */
	private void encodeIPAddress() {
		/* Create an arrayList containing the 4 digits in the local IP address. */
		ArrayList<Integer> ipIntList = getIntListFromStringArray(IPAddress.split("\\.", 4));

		/* Convert the last two IP numbers to hex strings. */
		String hexString1 = Integer.toHexString(ipIntList.get(2));
		String hexString2 = Integer.toHexString(ipIntList.get(3));

		/* Ensures that there is always a leading zero for single digit IPs. */
		if (hexString1.length() < 2) {
			hexString1 = "0" + hexString1;
		}

		if (hexString2.length() < 2) {
			hexString2 = "0" + hexString2;
		}

		/* Append the hex digits together. */
		IPCode = hexString1 + hexString2;
	}

	/**
	 * Method parses a array of strings, adding all the ints present to an
	 * arrayList.
	 * 
	 * @param intStrings
	 *            a list of all of the strings containing ints to be added to
	 *            the array list
	 * @return an array list containing all the ints present in the string array
	 */
	private ArrayList<Integer> getIntListFromStringArray(String[] intStrings) {
		/* Define the intList to be returned. */
		ArrayList<Integer> intList = new ArrayList<Integer>();

		/* Loop through all the strings, adding them to the list. */
		for (int i = 0; i < intStrings.length; i++) {
			try {
				intList.add(Integer.valueOf(intStrings[i]));
			} catch (NumberFormatException nfe) {
				/*
				 * If there is not a number in the string, do nothing and don't
				 * add it to the list.
				 */
			}
		}

		return intList;
	}
}
