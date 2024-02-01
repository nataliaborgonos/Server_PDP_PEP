package com.example.demo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	static int port=8080;
	
	public static void main(String[] args) {
		 if (args.length > 1) {
			 if(args[1].equals("--port")) {
	            try {
	                port = Integer.parseInt(args[2]);
	                System.setProperty("server.port", String.valueOf(port));
	            } catch (NumberFormatException e) {
	                System.err.println("Not a valid port.");
	            }
			 }
		 }
		System.out.println("PDP and PEP REST Server are listening in port " + port);
		SpringApplication.run(DemoApplication.class, args);
	}
	

    private static int setPort(String[] args) {
        for (int i = 0; i < args.length - 1; i++) {
            if ("--port".equals(args[i])) {
            	port=Integer.parseInt(args[i+1]);
                return Integer.parseInt(args[i + 1]);
            }
        }
        // Default port
        return 8080;
    }
}
