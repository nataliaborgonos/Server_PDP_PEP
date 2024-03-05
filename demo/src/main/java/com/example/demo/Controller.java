package com.example.demo;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.net.ssl.SSLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.client.GRPCClient;
import com.example.client.PolicyResponseHandler;
import com.example.client.TrustScoreResponseHandler;
import com.example.demo.PAP.PAPErat;
import com.example.demo.PAP.PAPInterface;
import com.example.demo.PAP.PAPTest;
import com.example.demo.PAP.PolicyStore;
import com.example.demo.PDP.PDP;
import com.example.demo.PEP.PEP;
import com.example.demo.PIP.PIPErat;
import com.example.demo.PIP.PIPInterface;
import com.example.demo.PIP.PIPTest;
import com.example.demo.PIP.TrustScoreStore;
import com.example.demo.models.AccessRequest;
import com.example.demo.models.AuthRequest;
import com.example.demo.models.AuthRequestTango;
import com.example.demo.models.SimpleAccessRight;
import com.example.demo.models.CapabilityToken;
import com.example.demo.requester.Requester;
import com.google.gson.Gson;

import grpcEratosthenesAPI.GrpcEratosthenesAPI.DeviceMessage;
import grpcEratosthenesAPI.GrpcEratosthenesAPI.PolicyMessage;

@RestController
@RequestMapping("/api")
public class Controller {

    private final String pipConfig;
    private final String papConfig;
    private final String wallet;
    
    PIPInterface pip;
    PAPInterface pap;
    PDP pdp;
    PEP pep;
    Gson gson;

    /*	CONSTRUCTOR	*/
    @Autowired
    public Controller(Environment env) {
        this.pipConfig = System.getProperty("pipConfig");
        this.papConfig = System.getProperty("papConfig");
        this.wallet=System.getProperty("wallet");
        		
      //Create the PAP,PIP according to the args
        
        if(pipConfig.equals("test") && papConfig.equals("test") && wallet.equals("test")) {
    	
    	TrustScoreStore trustScores=new TrustScoreStore();
    	pip=new PIPTest(trustScores);
    	
    	PolicyStore policies=new PolicyStore();
    	pap=new PAPTest(policies);

    	pdp=new PDP(pip,pap,wallet);
    	
    	pep= new PEP(pdp);

    	gson=new Gson();
        } 
        else if(pipConfig.equals("erathostenes") && papConfig.equals("erathostenes") && wallet.equals("erathostenes")){
        	
        	  PolicyResponseHandler handler = new PolicyResponseHandler() {
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
       
       GRPCClient client=null;
       
      try {
    	   client = new GRPCClient("localhost", 8080, false, handler, trustScoreHandler);
	} catch (SSLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      		pap=new PAPErat(client);
      
        	pip=new PIPErat(client);
        	
        	pdp=new PDP(pip,pap,wallet);
        	
        	pep=new PEP(pdp);
        	
        	gson=new Gson();
        } 
        else {
        	System.err.println("Not a valid configuration."); System.exit(0);
        }
    }
	
    /*	METHODS	*/
    @PostMapping("/request-access")
    public String requestAccess(@RequestBody AuthRequest request) {
    	
    	//Create a requester with request data
    	Requester requester =new Requester(request.getDidSP(),request.getDidRequester(),request.getVerifiablePresentation());
    	
    	//Create access request
		String req=requester.requestAccess(request.getSar().getResource(), request.getSar().getAction());
		pep.parseRequest(req);
		
		//Process access request to obtain a Capability Token 
    	CapabilityToken ct=process(req);
    	String token = gson.toJson(ct);
    	if(ct==null) {
    		System.out.println("Capability Token couldn't be issued, please revise the request and try again.\n");
    		return "Capability Token couldn't be issued, please revise the request and try again.\n";
    	}
    	System.out.println("Capability Token successfully issued.\n");
    	return token ;
    }
    
    public CapabilityToken process(String req) {
    	
    	//Send request to PEP for issuing the Capability token
    	String requestJson=gson.toJson(req);
    	CapabilityToken ct=pep.sendRequest(requestJson);
    	
    	return ct;
    }
    
    @PostMapping("/access-token-tango")
    public String accessTokenForTango(@RequestBody AuthRequestTango request) {
    	
    	//Create a requester with request data -> change the token tango instead VP
    	Requester requester =new Requester(request.getDidSP(),request.getDidRequester(),request.getToken());
    	
    	//Create access request
		String req=requester.requestAccessToken(request.getSar().getResource(), request.getSar().getAction(),request.getToken());
		
		//Process access request to obtain a Capability Token 
    	CapabilityToken ct=processTokenTango(req);
    	String token = gson.toJson(ct);
    	if(ct==null) {
    		System.out.println("Capability Token couldn't be issued, please revise the request and try again.\n");
    		return "Capability Token couldn't be issued, please revise the request and try again.\n";
    	}
    	System.out.println("Capability Token successfully issued.\n");
    	return token ;
    }
    
    public CapabilityToken processTokenTango(String req) {
    	
    	//Send request to PEP for issuing the Capability token -> change the PEP treatment 
    	String requestJson=gson.toJson(req);
    	CapabilityToken ct=pep.sendToken(requestJson);
    	return ct;
    }
    
    
    @PostMapping("/access-with-token")
    public String accessWithToken(@RequestBody AccessRequest request) {
    	
    	//Verify capability token
    	String requestJson=gson.toJson(request);
    	String response=pep.validateCapabilityToken(requestJson);
    	
    	return response;
    }
}

