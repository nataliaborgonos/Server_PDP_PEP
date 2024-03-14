package com.example.demo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class DemoApplication {

	//Default port used if there's no "--port" parameter
	static int port=8080;
	static String pdpConfig;
	
	public static void main(String[] args) {
			
		for (int i = 0; i < args.length; i++) {
		    if (args[i].equals("--port")) {
		        try {
		            port = Integer.parseInt(args[i + 1]);
		            System.setProperty("server.port", String.valueOf(port));
		        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
		            System.err.println("Not a valid port.");
		        }
		    } else if (args[i].equals("--pdp")) {
		        if (i < args.length - 1) {
		            pdpConfig = args[i + 1];
		            System.setProperty("pdpConfig", pdpConfig);
		        } else {
		            System.err.println("Missing value after '--pdp'. You need a PDP configuration.");
		        }
		    	
		    }
		    //TODO: Add the missing information here
		    else if (args[i].equals("--help")) {
		        System.out.println("Arguments information: \n"
		        		+ "--port [number] The port can be set manually adding this argument. If this is not set, the app will be running in port 8080.\n"
		        		+ "--pdp [test | erathostenes] The app needs the PDP configuration in order to apply policies and check trust scores. \n"
		        		);
		        System.exit(0);
		    }
		}

		
		System.out.println("PDP and PEP REST Server are listening in port " + port);
		
		SpringApplication.run(DemoApplication.class, args);
	}
	
}
