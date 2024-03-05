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
	static String pipConfig;
	static String papConfig;
	static String requester;
	static String wallet;
	
	public static void main(String[] args) {
		
		//TODO: Add args for creating an specific type of PDP
		
		for (int i = 0; i < args.length; i++) {
		    if (args[i].equals("--port")) {
		        try {
		            port = Integer.parseInt(args[i + 1]);
		            System.setProperty("server.port", String.valueOf(port));
		        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
		            System.err.println("Not a valid port.");
		        }
		    } else if (args[i].equals("--pip")) {
		        if (i < args.length - 1) {
		            pipConfig = args[i + 1];
		            System.setProperty("pipConfig", pipConfig);
		        } else {
		            System.err.println("Missing value after '--pip'. You need a Trust Scores Store.");
		        }
		    }else if(args[i].equals("--pap")) {
		    	if (i < args.length - 1) {
		            papConfig = args[i + 1];
		            System.setProperty("papConfig", papConfig);
		        } else {
		            System.err.println("Missing value after '--pap'. You need a Policies Store.");
		        }
		    }else if(args[i].equals("--wallet")) {
		    	if (i < args.length - 1) {
		            wallet = args[i + 1];
		            System.setProperty("wallet", wallet);
		        } else {
		            System.err.println("Missing value after '--wallet'. You need the requester's wallet.");
		        }
		    	
		    }
		    //TODO: Add the missing information here
		    else if (args[i].equals("--help")) {
		        System.out.println("Arguments information: \n"
		        		+ "--port [number] The port can be set manually adding this argument. If this is not set, the app will be running in port 8080.\n"
		        		+ "--pip [test | ] The app needs the trust score store. \n"
		        		+ "--pap [test | ] The app needs the policy store.\n"
		        		+ "--wallet [test | ] The app needs the requester's wallet.\n");
		        System.exit(0);
		    }
		}

		
		System.out.println("PDP and PEP REST Server are listening in port " + port);
		
		SpringApplication.run(DemoApplication.class, args);
	}
	
}
