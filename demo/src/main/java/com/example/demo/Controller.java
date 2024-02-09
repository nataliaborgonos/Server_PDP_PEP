package com.example.demo;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.PAP.PAP;
import com.example.demo.PAP.PolicyStore;
import com.example.demo.PDP.PDP;
import com.example.demo.PEP.PEP;
import com.example.demo.PIP.PIP;
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
	PIP pip=new PIP(trustScores);
	
	PolicyStore policies=new PolicyStore();
	PAP pap=new PAP(policies);

	PDP pdp=new PDP(pip,pap);
	
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
    
    @PostMapping("/access-with-token")
    public String accessWithToken(@RequestBody AccessRequest request) {
    	
    	//Verify capability token
    	String requestJson=gson.toJson(request);
    	String response=pep.validateCapabilityToken(requestJson);
    	
    	return response;
    }
}

