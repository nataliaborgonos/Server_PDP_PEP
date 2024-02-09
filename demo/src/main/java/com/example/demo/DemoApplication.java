package com.example.demo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	//Default port used if there's no "--port" parameter
	static int port=8080;
	
	public static void main(String[] args) {
		
		//TODO: Add args for creating an specific type of PDP
		
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
	
}
