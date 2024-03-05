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
	
      /*	CONSTRUCTOR		*/
	public PIPErat(GRPCClient client) {
		this.client=client;
	
			//Create a trust score for testing 
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
	        System.out.println("CreateTrustScore Response: " + createMessage.getScore());
	}
	
	/*		METHODS		*/
	
	//TODO: Make a method for creating trust scores 
	
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


