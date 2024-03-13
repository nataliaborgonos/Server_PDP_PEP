package com.example.demo;
import java.text.ParseException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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
import com.example.demo.models.AuthRequestConnectorToken;
import com.example.demo.models.AuthRequestTango;
import com.example.demo.models.SimpleAccessRight;
import com.example.demo.models.CapabilityToken;
import com.example.demo.requester.Requester;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.nimbusds.jwt.*;


import grpcEratosthenesAPI.GrpcEratosthenesAPI.DeviceMessage;
import grpcEratosthenesAPI.GrpcEratosthenesAPI.PolicyMessage;

@RestController
@RequestMapping("/api")
public class Controller {

    private String pdpConfig;
    
    PIPInterface pip;
    PAPInterface pap;
    PDP pdp;
    PEP pep;
    Gson gson;

    /*	CONSTRUCTOR	*/
  
    public Controller(Environment env) {
        this.pdpConfig = System.getProperty("pdpConfig");

      //Create the PAP,PIP according to the args
       
        if(pdpConfig.equals("test")) {
    	
    	TrustScoreStore trustScores=new TrustScoreStore();
    	pip=new PIPTest(trustScores);
    	
    	PolicyStore policies=new PolicyStore();
    	pap=new PAPTest(policies);

    	pdp=new PDP(pip,pap);
    	
    	pep= new PEP(pdp);

    	gson=new Gson();
        } 
        else if(pdpConfig.equals("erathostenes") ){
        	
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
        	
        	pdp=new PDP(pip,pap);
        	
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
    
    
    @PostMapping("/connector-access-token")
    public String trying(@RequestBody AuthRequestConnectorToken request) {
    	
    	        String jwtString = request.getAccessToken();
    	        System.out.println(jwtString);
    	     
    	        // Parsear el JWT
    	        JWT jwt=null;
				try {
					jwt = JWTParser.parse(jwtString);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

    	        // Decodificar los claims del JWT
    	        JWTClaimsSet claimsSet=null;
				try {
					claimsSet = jwt.getJWTClaimsSet();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    	        System.out.println("Claims del JWT:");
    	        System.out.println(claimsSet.toJSONObject());  
    	        
    	        // Construir el string JSON
    	        String jsonString=null;
    	        
    	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz uuuu", Locale.ENGLISH);

    	        Object expClaim = claimsSet.getClaim("exp");
    	        String expString;
    	        if (expClaim instanceof Date) {
    	            // Si es una Date, la convertimos a String con el formato adecuado
    	            expString = ((Date) expClaim).toString();
    	        } else {
    	            // Si no, asumimos que es un String
    	            expString = (String) expClaim;
    	        }

    	        ZonedDateTime zonedDateTime = ZonedDateTime.parse(expString, formatter);

    	        // Convertimos la fecha a UNIX Epoch (segundos desde el 1 de enero de 1970)
    	        long epochSeconds = zonedDateTime.toEpochSecond();
    	        
    	        Map<String, Object> jo = null;
				try {
					jo = claimsSet.getJSONObjectClaim("verifiableCredential");
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 String jsonString1 = gson.toJson(jo);
				 System.out.println("cambio "+jsonString1);
				 
				 
				 JsonObject jsonObject = new JsonObject();
				 jsonObject.addProperty("aud", claimsSet.getAudience().iterator().next());
				 jsonObject.addProperty("sub", claimsSet.getSubject());
				 try {
					jsonObject.addProperty("kid", claimsSet.getStringClaim("kid"));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 jsonObject.addProperty("iss", claimsSet.getIssuer());
				 jsonObject.addProperty("exp", epochSeconds);
				 try {
					jsonObject.addProperty("client_id", claimsSet.getStringClaim("client_id"));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 jsonObject.addProperty("verifiableCredential", jsonString1); // Agregar directamente el objeto JSON como un campo


String jsonString2 = gson.toJson(jsonObject); // Convertir el objeto JSON resultante a string
System.out.println("string "+jsonString2);
				 
    	        // Imprimir el string JSON
    	        System.out.println("Claims del JWT en formato JSON:");
    	        System.out.println(jsonString);
    	        //Create a requester with request data -> change the token tango instead VP
    	    	Requester requester =new Requester(request.getDidSP(),request.getDidRequester(),jsonString2);
    	    	
    	        System.out.println(requester.getVPJSON());
    	        
    	      //Create access request
    		String req=requester.requestAccessToken(request.getSar().getResource(), request.getSar().getAction(),jsonString2);
    		//Process access request to obtain a Capability Token 
        	CapabilityToken ct=processConnectorToken(req);
        	String token = gson.toJson(ct);
        	if(ct==null) {
        		System.out.println("Capability Token couldn't be issued, please revise the request and try again.\n");
        		return "Capability Token couldn't be issued, please revise the request and try again.\n";
        	}
        	System.out.println("Capability Token successfully issued.\n");
        	return token ;
    	    }

    
    public CapabilityToken processConnectorToken(String req) {
    	
    	//Send request to PEP for issuing the Capability token -> change the PEP treatment 
    	String requestJson=gson.toJson(req);
    	CapabilityToken ct=pep.sendConnectorToken(requestJson);
    	return ct;
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

