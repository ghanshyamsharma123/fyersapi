package com.fyers.api.python;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FyersTokenProcessBuilder {
    public static void main(String[] args) throws IOException {
        //ProcessBuilder pb = new ProcessBuilder("C:\\Users\\eghshar\\AppData\\Local\\Microsoft\\WindowsApps\\python.exe", "get_fyers_access_token.py");
        ProcessBuilder pb = new ProcessBuilder("python3", "get_fyers_access_token.py");
        //  /opt/homebrew/bin/python3
        pb.redirectErrorStream(true);
        Process p = pb.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
    }
    
    public String getToken() throws IOException {
        ProcessBuilder pb = new ProcessBuilder("python3", "get_fyers_access_token.py");
        pb.redirectErrorStream(true);
        Process p = pb.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            //System.out.println(line);
            return line;
        }
		return line;
    }
}