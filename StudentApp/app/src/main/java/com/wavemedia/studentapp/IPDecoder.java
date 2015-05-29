package com.wavemedia.studentapp;

/**
 * Created by Harrison on 27/05/2015.
 */
public class IPDecoder {
    int siteID;
    String hexCode;
    int[] sites;
    String[] ips;

    String serverIP;

    IPDecoder(int sentSite, String sentHexCode, int[] sentSites, String[] sentIPS) {
        siteID = sentSite;
        hexCode = sentHexCode;
        sites = sentSites;
        ips = sentIPS;

        // Set IP for selected Site
        switch (siteID) {
            case 0:
                serverIP = ips[0];
                break;
            default:
                serverIP = "0.0";
                System.err.println("No site selected");
                break;
        }

        serverIP += "." + getHexCodeIP(hexCode);
        System.err.println(serverIP);
    }

    public String getHexCodeIP(String hexCode) {
        String[] hex;
        String hexCodeIP;
        hex = hexCode.split("(?!^)");
        String hexString1 = hex[0] + hex[1];
        String hexString2 = hex[2] + hex[3];
        int hexCodeIPString1 = hex2decimal(hexString1);
        System.out.println("WM IP: " + hexCode + " " + hex);
        System.out.println("WM IP: " + hexString1 + " -> " + hexCodeIPString1);
        int hexCodeIPString2 = hex2decimal(hexString2);
        System.out.println("WM IP: " + hexString2 + " -> " + hexCodeIPString2);
        hexCodeIP =  Integer.toString(hexCodeIPString1);
        hexCodeIP += "." + Integer.toString(hexCodeIPString2);
        System.out.println("WM IP: " + hexCodeIP);
        return hexCodeIP;
    }

    public static int hex2decimal(String s) {
        String digits = "0123456789ABCDEF";
        s = s.toUpperCase();
        int val = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int d = digits.indexOf(c);
            val = 16*val + d;
        }
        return val;
    }

    public String getServerIP(){
        return serverIP;
    }
}
