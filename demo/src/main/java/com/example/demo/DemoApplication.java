package com.example.demo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class DemoApplication {
	
	//Read the environment variables or uses default values
	
	@Value("${app.PDP_PORT:8080}")
	static int port;

	@Value("${app.PDP_CONFIG:test}")
	static String pdpConfig;

	public static void main(String[] args) {

		String serverPortEnv = System.getenv("PDP_PORT");
		if (serverPortEnv != null && !serverPortEnv.isEmpty()) {
			port = Integer.parseInt(serverPortEnv);
		}
		System.setProperty("server.port", String.valueOf(port));

		String pdpconf = System.getenv("PDP_CONFIG");
		if (pdpconf != null && !pdpconf.isEmpty()) {
			pdpConfig = pdpconf;
		}
		System.setProperty("pdpConfig", pdpConfig);

		System.out.println("PEP/PDP REST Server is listening in port " + port + " with the " + pdpConfig + " configuration.");
		SpringApplication.run(DemoApplication.class, args);
	}

}
