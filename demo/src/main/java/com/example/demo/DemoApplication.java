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
		    }//else if(args[i].equals("--requester")) {
		    	//if (i < args.length - 1) {
		          //  requester = args[i + 1];
		            //System.setProperty("requester", requester);
		        //} else {
		         //   System.err.println("Missing value after '--requester'. You need a Policies Store.");
		        //}
		    //}
		}

		/*
		 if (args.length > 1) {
			
			 if(args[1].equals("--port")) {
	            try {
	                port = Integer.parseInt(args[2]);
	                System.setProperty("server.port", String.valueOf(port));
	            } catch (NumberFormatException e) {
	                System.err.println("Not a valid port.");
	            }
			 }else if (args[1].equals("--pip")) {
		            customString = args[2];
		            System.setProperty("customString", customString);
		            // Podrías usar esta cadena personalizada según sea necesario
		        }
		 }*/
		System.out.println("PDP and PEP REST Server are listening in port " + port);
		System.out.println("configuracion para pip: "+pipConfig);
		System.out.println("configuracion para pap: "+papConfig);
		
		SpringApplication.run(DemoApplication.class, args);
	}
	
}
