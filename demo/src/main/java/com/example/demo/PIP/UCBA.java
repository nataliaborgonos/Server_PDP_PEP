package com.example.demo.PIP;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import com.example.demo.models.UCBARequest;
import com.google.gson.Gson;

public class UCBA {
	static String UCBA_address;
	Gson gson;
	
	//Returns true or false whether the user is authenticated
	public String auth(UCBARequest req) {
		        try {
		         
		            HttpClient client = HttpClient.newBuilder().build();


		            Gson gson = new Gson();
		            String input = gson.toJson(req);
		            // Crear la solicitud POST
		            HttpRequest request = HttpRequest.newBuilder()
		                    .uri(URI.create("https://api.quadible.io/external/auth"))
		                    .header("Content-Type", "application/json")
		                    .header("x-admin-api-key", "de6c2697c047b11c9440ff879f3fc034a2b16e2a")
		                    .POST(HttpRequest.BodyPublishers.ofString(input, StandardCharsets.UTF_8))
		                    .build();

	
		            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


		            if (response.statusCode() == 201) {
		                System.out.println("Authentication from UCBA successful.");
		                return response.body();
		            } else {
		                System.out.println("Failed to authenticate. Response code: " + response.statusCode());
		            }

		        } catch (Exception e) {
		            System.out.println("Something went wrong with the authentication.");
		            e.printStackTrace();
		        }

		        return null;
		    }
	}

