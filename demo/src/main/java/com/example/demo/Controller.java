package com.example.demo;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.PDP.PDP;
import com.example.demo.PEP.PEP;
import com.example.demo.PIP.PIP;
import com.example.demo.PIP.PolicyStore;
import com.example.demo.PIP.TrustScoreStore;
import com.example.demo.models.AccessRequest;
import com.example.demo.models.AuthRequest;
import com.example.demo.models.SimpleAccessRight;
import com.example.demo.models.CapabilityToken;
import com.example.demo.requester.Requester;
import com.google.gson.Gson;

@RestController
@RequestMapping("/api")
public class Controller {
	
	TrustScoreStore trustScores=new TrustScoreStore();
	PolicyStore policies=new PolicyStore();
	PIP pip=new PIP(trustScores,policies);

	PDP pdp=new PDP(pip);
	
	PEP pep= new PEP(pdp);

	
	Gson gson=new Gson();

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
    	System.out.println("Capability Token successfully issued.");
    	return "Capability Token: " +  token + " issued for requester: \n ServiceProvider: " + request.getDidSP()
    	+ " Simple Access Right: " + request.getSar().getAction() 
    	+ " Requester: " + request.getDidRequester()
    	+ " VP: " + request.getVerifiablePresentation();
    }
    
    public CapabilityToken process(String req) {
    	
    	//Send request to PEP for issuing the Capability token
    	String requestJson=gson.toJson(req);
    	CapabilityToken ct=pep.sendRequest(requestJson);
    	
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

