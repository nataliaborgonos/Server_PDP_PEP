package com.example.demo.PIP;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.example.client.GRPCClient;
import com.example.client.PolicyResponseHandler;
import com.example.client.TrustScoreResponseHandler;

import grpcEratosthenesAPI.GrpcEratosthenesAPI.DeviceMessage;
import grpcEratosthenesAPI.GrpcEratosthenesAPI.PolicyMessage;

public class PIPErat implements PIPInterface{

	GRPCClient client = null;

	
      String deviceID = "123";
      float initialScore = 0.9f;
	
	public PIPErat(GRPCClient client) {
		this.client=client;
	    String jsonExample = "{\"id\":\"123\",\"nombre\":\"Sample Name\",\"purpose\":\"Demonstration\",\"serviceProvider\":\"UMU\",\"accessRights\":[{\"resource\":\"File1\",\"action\":\"Read\"},{\"resource\":\"File2\",\"action\":\"Write\"}],\"authTime\":1642058400,\"minTrustScore\":0.8,\"constraints\":{\"fields\":[{\"purpose\":\"Validation\",\"name\":\"Field1\",\"path\":[\"Path\",\"to\",\"Field1\"],\"filter\":{\"type\":\"string\",\"pattern\":\"^[a-zA-Z0-9]+$\"}},{\"purpose\":\"Analysis\",\"name\":\"Field2\",\"path\":[\"Path\",\"to\",\"Field2\"],\"filter\":{\"type\":\"number\",\"min\":10,\"max\":200}}]}}";
        String id = "123";
        String accessRightsJSON = "[{\"resource\":\"File1\",\"action\":\"Read\"},{\"resource\":\"File2\",\"action\":\"Write\"}]";
        CompletableFuture<PolicyMessage> addPolicyFuture = client.addPolicy(
                jsonExample);
        PolicyMessage addPolicyResponse = null;
		try {
			addPolicyResponse = addPolicyFuture.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        System.out.println("AddPolicy Response received: " + addPolicyResponse.getPolicyJSON());

       /*
	        CompletableFuture<DeviceMessage> createFuture = client.createTrustScore(deviceID, initialScore);
	        DeviceMessage createMessage = null;
			try {
				createMessage = createFuture.get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        System.out.println("CreateTrustScore Response: " + createMessage.getScore());*/
	}
	
	static  PolicyResponseHandler handler = new PolicyResponseHandler() {
         @Override
         public void handleAddPolicyResponse(CompletableFuture<PolicyMessage> future, PolicyMessage message) {

             System.out.println("AddPolicy Response: " + message.getPolicyJSON());
             future.complete(message);
         }

         @Override
         public void handleQueryPolicyResponse(CompletableFuture<PolicyMessage> future, PolicyMessage message) {
             System.out.println("QueryPolicy Response: " + message.getPolicyJSON());
             future.complete(message);
         }
     };
 	TrustScoreResponseHandler trustScoreHandler = new TrustScoreResponseHandler() {
  @Override
  public void handleCreateTrustScoreResponse(CompletableFuture<DeviceMessage> future, DeviceMessage message) {
      System.out.println("CreateTrustScore Response: " + message.getScore());
      future.complete(message);
  }

  @Override
  public void handleReadTrustScoreResponse(CompletableFuture<DeviceMessage> future, DeviceMessage message) {
      System.out.println("ReadTrustScore Response: " + message.getScore());
      future.complete(message);
  }

  @Override
  public void handleUpdateTrustScoreResponse(CompletableFuture<DeviceMessage> future, DeviceMessage message) {
      System.out.println("UpdateTrustScore Response: " + message.getScore());
      future.complete(message);
  }

  @Override
  public void handleDeleteTrustScoreResponse(CompletableFuture<DeviceMessage> future, DeviceMessage message) {
      System.out.println("DeleteTrustScore Response: " + message.getDeviceID());
      future.complete(message);
  }
};
	
	@Override
	public double getTrustScore(String didSolicitante) {
	
	        // Leer TrustScore
	        CompletableFuture<DeviceMessage> readFuture = client.readTrustScore(deviceID);
	        DeviceMessage readMessage = null;
			try {
				readMessage = readFuture.get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        System.out.println("ReadTrustScore Response: " + readMessage.getScore());

	        // Actualizar TrustScore
	        // float updatedScore = 0.95f;
	        // CompletableFuture<DeviceMessage> updateFuture =
	        // client.updateTrustScore(deviceID, updatedScore);
	        // DeviceMessage updateMessage = updateFuture.get();
	        // System.out.println("UpdateTrustScore Response: " + updateMessage.getScore());

/*
	        CompletableFuture<DeviceMessage> readAfterUpdateFuture = client.readTrustScore(deviceID);
	        DeviceMessage readAfterUpdateMessage = null;
			try {
				readAfterUpdateMessage = readAfterUpdateFuture.get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        System.out.println("ReadTrustScore After Update Response: " + readAfterUpdateMessage.getScore());
*/
	        
	        // Eliminar TrustScore
	        // CompletableFuture<DeviceMessage> deleteFuture =
	        // client.deleteTrustScore(deviceID);
	        // DeviceMessage deleteMessage = deleteFuture.get();
	        // System.out.println("DeleteTrustScore Response: " +
	        // deleteMessage.getDeviceID());

	        // Leer TrustScore después de eliminar
	      /*
	        CompletableFuture<DeviceMessage> readAfterDeleteFuture = client.readTrustScore(deviceID);
	        DeviceMessage readAfterDeleteMessage = null;
	        try {
				readAfterDeleteMessage = readAfterDeleteFuture.get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        System.out.println("ReadTrustScore After Delete Response: " + readAfterDeleteMessage.getScore());*/
	        return readMessage.getScore();
	    }
}


