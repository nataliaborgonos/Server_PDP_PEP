package com.example.demo.PAP;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.SSLException;

import com.example.client.PolicyResponseHandler;
import com.example.client.PolicyServicesClient;
import com.example.demo.models.Policy;
import com.google.gson.Gson;

import grpcEratosthenesAPI.GrpcEratosthenesAPI.PolicyMessage;

public class PAPErat implements PAPInterface{

	PolicyServicesClient client = null;
	Gson gson;
	
	public PAPErat() {
		gson=new Gson();
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
	}
	
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
	
		
	@Override
	public ArrayList<Policy> getPolicies(String didSP, String recursoSolicitado,String action) {
		//Add the full access rights
		String accessRightsJSON="\"accessRights\":[{\"resource\":"+recursoSolicitado+",\"action\":\""+action+"\"}]";
		CompletableFuture<PolicyMessage> queryPolicyFuture = client.queryPolicy(didSP, accessRightsJSON);
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
		Policy policy = gson.fromJson(queryPolicyResponse.getPolicyJSON(), Policy.class);
		ArrayList<Policy> policies=new ArrayList<>();
		policies.add(policy);
		return policies;
	}

}
