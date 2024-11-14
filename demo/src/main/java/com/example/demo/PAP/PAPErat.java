package com.example.demo.PAP;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.json.Json;
import javax.json.JsonReaderFactory;
import javax.net.ssl.SSLException;

import com.example.client.GRPCClient;
import com.example.client.PolicyResponseHandler;
import com.example.client.PolicyServicesClient;
import com.example.client.TrustScoreResponseHandler;
import com.example.demo.models.Policy;
import com.google.gson.Gson;

import grpcEratosthenesAPI.GrpcEratosthenesAPI.DeviceMessage;
import grpcEratosthenesAPI.GrpcEratosthenesAPI.PolicyMessage;

public class PAPErat implements PAPInterface{

	GRPCClient client = null;
	Gson gson;
	
	/*		CONSTRUCTOR		*/
	public PAPErat(GRPCClient client) {
		gson=new Gson();
		this.client=client;
		String jsonExample = "{\"id\":\"123\",\"nombre\":\"Student Information\",\"purpose\":\"Reveal id and name of the BachelorDegree's student in MIT.\",\"serviceProvider\":\"did:ServiceProvider:1\",\"accessRights\":[{\"resource\":\"/temperatura\",\"action\":\"GET\"}],\"authTime\":1642058400,\"minTrustScore\":0.5,\"constraints\":{\"fields\":[{\"purpose\":\"Reveal name\",\"name\":\"Student name\",\"path\":[\"$.id\"],\"filter\":{\"type\":\"string\",\"pattern\":\"^[a-zA-Z0-9]+$\"}}]}}";
		String id = "123";
		String accessRightsJSON = "[{\"resource\":\"/temperatura\",\"action\":\"GET\"}]";
		CompletableFuture<PolicyMessage> addPolicyFuture = client.addPolicy(jsonExample);
	
		PolicyMessage addPolicyResponse = null;
		try {
			addPolicyResponse = addPolicyFuture.get();
			System.out.println(addPolicyFuture.get());
			if(addPolicyResponse.getPolicyJSON()!=null) {
				System.out.println("AddPolicy Response received: " + addPolicyResponse.getPolicyJSON());
				}
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		System.out.println("AddPolicy Response received: " + addPolicyResponse.getPolicyJSON());
	}
	
	/*		METHODS		*/
		
	@Override
	public ArrayList<Policy> getPolicies(String didSP, String recursoSolicitado,String action) {
		//Add the full access rights
		String accessRightsJSON="[{\"resource\":\""+recursoSolicitado+"\",\"action\":\""+action+"\"}]";
		String id = "123";
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
		Policy policy = gson.fromJson(queryPolicyResponse.getPolicyJSON(), Policy.class);
		ArrayList<Policy> policies=new ArrayList<>();
		policies.add(policy);
		return policies;
	}

	@Override
	public void addPolicy(Policy policy, String resource) {
		// TODO Auto-generated method stub
		String json=gson.toJson(policy);
		CompletableFuture<PolicyMessage> addPolicyFuture = client.addPolicy(json);
	
		PolicyMessage addPolicyResponse = null;
		try {
			addPolicyResponse = addPolicyFuture.get();
			System.out.println(addPolicyFuture.get());
			if(addPolicyResponse.getPolicyJSON()!=null) {
				System.out.println("AddPolicy Response received: " + addPolicyResponse.getPolicyJSON());
				}
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		System.out.println("AddPolicy Response received: " + addPolicyResponse.getPolicyJSON());
	}

}
