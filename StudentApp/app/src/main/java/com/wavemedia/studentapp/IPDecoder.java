/**
 *
 * Harrison Holt-McHale
 *
 * Copyright (c) 2015 WaveMedia. All rights reserved
 *
 */

package com.wavemedia.studentapp;

/**
 * @author Harrison Holt-McHale
 * @version 2.0 29/05/2015
 *
 */

/* Class that Decodes user input and provides serverIP to connect to */
public class IPDecoder {

    /* Inputs */
    int siteID;             // Institution Site Selected
    String hexCode;         // HexCode Input
    int[] sites;            // Array of Site IDs
    String[] ips;           // Array of Site IPs

    /* Outputs */
    String serverIP;        // Output Server IP

    IPDecoder(int sentSite, String sentHexCode, int[] sentSites, String[] sentIPS) {

        /* Parameters passed into Class in Constructor */
        siteID = sentSite;
        hexCode = sentHexCode;
        sites = sentSites;
        ips = sentIPS;

        /* Set the IP for the selected site */
        switch (siteID) {
            case 0:
                serverIP = ips[0];
                break;
            default:
                serverIP = "0.0";
                System.err.println("No site selected");
                break;
        }

        /* Append the Institution IP with IP numbers obtained from the Hex Code */
        serverIP += "." + getHexCodeIP(hexCode);
    }

    /* This method converts the Hex Code to the IP Numbers */
    public String getHexCodeIP(String hexCode) {

        String[] hex;       // Input Hex split into individual digits
        String hexCodeIP;   // IP Numbers to output
        /* Split Hex Code into individual digits */
        hex = hexCode.split("(?!^)");
        /* Create the 2 hex numbers */
        String hexString1 = hex[0] + hex[1];
        String hexString2 = hex[2] + hex[3];
        /* Parse Hex Strings to Integers */
        int hexCodeIPString1 = Integer.parseInt(hexString1, 16);
        int hexCodeIPString2 = Integer.parseInt(hexString2, 16);
        /* Convert Integers to Strings and convert them to IP Number format */
        hexCodeIP =  Integer.toString(hexCodeIPString1);
        hexCodeIP += "." + Integer.toString(hexCodeIPString2);
        /* Return the Converted IP Numbers */
        return hexCodeIP;
    }

    /* Getter to return serverIP */
    public String getServerIP(){
        return serverIP;
    }
}
