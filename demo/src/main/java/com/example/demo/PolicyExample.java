package com.example.demo;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.SSLException;

import com.example.client.PolicyResponseHandler;
import com.example.client.PolicyServicesClient;

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
				System.out.println("creo el cliente");
			} catch (SSLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String jsonExample = "{\"id\":\"policy_id\",\"nombre\":\"Nombre de la política\",\"purpose\":\"Propósito de la política\",\"serviceProvider\":\"Proveedor de servicios\",\"accessRights\":[{\"resource\":\"recurso\",\"action\":\"GET\"}],\"authTime\":123456789,\"constraints\":{\"fields\":[{\"purpose\":\"Revelar nombre\",\"name\":\"name\",\"path\":[\"usuario\"],\"filter\":{\"type\":\"string\"}}]}}";
			String id = "123";
			String accessRightsJSON = "[{\"resource\":\"/recurso\",\"action\":\"GET\"}]";

			CompletableFuture<PolicyMessage> addPolicyFuture = client.addPolicy(jsonExample);
			PolicyMessage addPolicyResponse = null;
			try {
				addPolicyResponse = addPolicyFuture.get();
				System.out.println("hago el get");
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(addPolicyResponse !=null) {
			System.out.println("AddPolicy Response received: " + addPolicyResponse.getPolicyJSON());
			}
			
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

	}

}
