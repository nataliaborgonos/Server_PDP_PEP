package com.example.demo;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.SSLException;

import com.example.client.PolicyResponseHandler;
import com.example.client.PolicyServicesClient;
import com.example.demo.models.Policy;
import com.google.gson.Gson;

import grpcEratosthenesAPI.GrpcEratosthenesAPI.PolicyMessage;

public class PolicyExample {

	static PolicyResponseHandler handler = new PolicyResponseHandler() {
		@Override
		public void handleAddPolicyResponse(CompletableFuture<PolicyMessage> future, PolicyMessage message) {
		System.out.println("AddPolicy Response: " + message.getPolicyJSON());
		future.complete(message);
		}
			@Override
			public void handleQueryPolicyResponse(CompletableFuture<PolicyMessage> arg0, PolicyMessage arg1) {
				// TODO Auto-generated method stub
				 System.out.println("QueryPolicy Response: " + arg1.getPolicyJSON());
			       arg0.complete(arg1);

			}
		};
	
	public static void main(String[] args) {
		

			PolicyServicesClient client = null;
			try {
				client = new PolicyServicesClient("localhost", 8080, false, handler);
			} catch (SSLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String jsonExample = "{\"id\":\"123\",\"nombre\":\"Student Information\",\"purpose\":\"Reveal id and name of the BachelorDegree's student in MIT.\",\"serviceProvider\":\"did:ServiceProvider:1\",\"accessRights\":[{\"resource\":\"/temperatura\",\"action\":\"GET\"}],\"authTime\":1642058400,\"minTrustScore\":0.5,\"constraints\":{\"fields\":[{\"purpose\":\"Reveal name\",\"name\":\"Student name\",\"path\":[\"$.id\"],\"filter\":{\"type\":\"string\",\"pattern\":\"^[a-zA-Z0-9]+$\"}}]}}";
			String id = "123";
			String accessRightsJSON = "[{\"resource\":\"/temperatura\",\"action\":\"GET\"}]";

			CompletableFuture<PolicyMessage> addPolicyFuture = client.addPolicy(jsonExample);
			PolicyMessage addPolicyResponse = null;
			try {
				addPolicyResponse = addPolicyFuture.get();
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println("AddPolicy Response received: " + addPolicyResponse.getPolicyJSON());
			
			CompletableFuture<PolicyMessage> queryPolicyFuture = client.queryPolicy(id, accessRightsJSON);
			PolicyMessage queryPolicyResponse = null;
			try {
				queryPolicyResponse = queryPolicyFuture.get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("QueryPolicy Response received: " + queryPolicyResponse.getPolicyJSON());
			   Gson gson = new Gson();
		        Policy policies = gson.fromJson(queryPolicyResponse.getPolicyJSON(), Policy.class);
		        System.out.println(policies.toString());
	}

}
