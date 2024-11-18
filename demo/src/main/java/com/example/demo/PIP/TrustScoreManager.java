package com.example.demo.PIP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;

import com.example.demo.models.TSMConfigRequest;
import com.example.demo.models.TSMScoreRequest;
import com.google.gson.Gson;
import com.nimbusds.jose.shaded.json.JSONObject;

public class TrustScoreManager {
	//@Value("${app.TSM_ADDRESS:localhost}")
	static String tsm_address;
	Gson gson;
	
	public TrustScoreManager(String tsm_address) {
		this.tsm_address=tsm_address;
		gson = new Gson();
	}
	
    // Crear el JSON de entrada para el request
    public String createConfig(TSMConfigRequest inputRequest) {
        try {
            HttpClient client = HttpClient.newBuilder()
                    .build();
            String input = gson.toJson(inputRequest);
            System.out.println(input);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://tsm-development.k8s-cluster.tango.rid-intrasoft.eu/api/tsm/config"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(input, StandardCharsets.UTF_8))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
           if(response.statusCode()==200) {System.out.println("Trustworthiness Configuration successfully created.");}
            return response.body();

        } catch (Exception e) {
        	System.out.println("Something went wrong creating the Trustworthiness Configuration.");
            e.printStackTrace();
        }
    
		return null;
    }
    
    public String calculateTrustScore(TSMScoreRequest inputRequest) {
    	 try {
             HttpClient client = HttpClient.newBuilder()
                     .build();
             String input = gson.toJson(inputRequest);
             System.out.println(input);
             HttpRequest request = HttpRequest.newBuilder()
                     .uri(URI.create("https://tsm-development.k8s-cluster.tango.rid-intrasoft.eu/api/tsm/score"))
                     .header("Content-Type", "application/json")
                     .POST(HttpRequest.BodyPublishers.ofString(input, StandardCharsets.UTF_8))
                     .build();

             HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode()==200) {System.out.println("Trustworthiness Score for the user has been successfully calculated.");}
             return response.body();

         } catch (Exception e) {
         	System.out.println("Something went wrong calculating the Trustworthiness Score for the user.");
             e.printStackTrace();
         }
     
 		return null;
    }
}
	
	

