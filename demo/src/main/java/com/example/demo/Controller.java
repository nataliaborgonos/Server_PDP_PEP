package com.example.demo;

import static org.hamcrest.CoreMatchers.instanceOf;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.client.GRPCClient;
import com.example.client.PolicyResponseHandler;
import com.example.client.TrustScoreResponseHandler;
import com.example.demo.JWTManager.JWTGenerator;
import com.example.demo.JWTManager.JWTVerifier;
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
import com.example.demo.models.AuthPolicyRequest;
import com.example.demo.models.AuthRequest;
import com.example.demo.models.AuthRequestConnectorToken;
import com.example.demo.models.AuthRequestTango;
import com.example.demo.models.SimpleAccessRight;
import com.example.demo.models.CapabilityToken;
import com.example.demo.models.Policy;
import com.example.demo.models.PolicyRequest;
import com.example.demo.requester.Requester;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.nimbusds.jwt.*;

import ch.qos.logback.core.recovery.ResilientSyslogOutputStream;
import grpcEratosthenesAPI.GrpcEratosthenesAPI.DeviceMessage;
import grpcEratosthenesAPI.GrpcEratosthenesAPI.PolicyMessage;

@RestController
@RequestMapping("/api")
public class Controller {

	@Value("${app.DLT_IP:localhost}")
	static String dlt_ip;

	@Value("${app.DLT_PORT:8080}")
	static int dlt_port;

	private String pdpConfig;
	PIPInterface pip;
	PAPInterface pap;
	PDP pdp;
	PEP pep;
	Gson gson;

	JWTGenerator jwtGenerator;
	JWTVerifier jwtVerifier;
	
	/* CONSTRUCTOR */

	public Controller() {
		this.pdpConfig = System.getProperty("pdpConfig");
		jwtGenerator=new JWTGenerator();
		jwtVerifier=new JWTVerifier();
		dlt_ip = System.getenv("DLT_IP");
		if(dlt_ip==null) {
			dlt_ip="localhost";
		}
		
		String dltPortEnv = System.getenv("DLT_PORT");
		if (dltPortEnv != null && !dltPortEnv.isEmpty()) {
			dlt_port = Integer.parseInt(dltPortEnv);
		}else {dlt_port=8080;}

		// Create the PAP,PIP according to the args

		if (pdpConfig.equals("test")) {

			TrustScoreStore trustScores = new TrustScoreStore();
			pip = new PIPTest(trustScores);

			PolicyStore policies = new PolicyStore();
			pap = new PAPTest(policies);

			pdp = new PDP(pip, pap);

			pep = new PEP(pdp);

			gson = new Gson();
			String long_lived_jwt=jwtGenerator.generateLongLivedJWT();
			System.out.println("The long-lived jwt that has been generated is: "+long_lived_jwt);
		} else if (pdpConfig.equals("eratosthenes")) {

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
				public void handleCreateTrustScoreResponse(CompletableFuture<DeviceMessage> future,
						DeviceMessage message) {
					System.out.println("CreateTrustScore Response: " + message.getScore());
					future.complete(message);
				}

				@Override
				public void handleReadTrustScoreResponse(CompletableFuture<DeviceMessage> future,
						DeviceMessage message) {
					System.out.println("ReadTrustScore Response: " + message.getScore());
					future.complete(message);
				}

				@Override
				public void handleUpdateTrustScoreResponse(CompletableFuture<DeviceMessage> future,
						DeviceMessage message) {
					System.out.println("UpdateTrustScore Response: " + message.getScore());
					future.complete(message);
				}

				@Override
				public void handleDeleteTrustScoreResponse(CompletableFuture<DeviceMessage> future,
						DeviceMessage message) {
					System.out.println("DeleteTrustScore Response: " + message.getDeviceID());
					future.complete(message);
				}
			};

			GRPCClient client = null;

			try {
				client = new GRPCClient(dlt_ip, dlt_port, false, handler, trustScoreHandler);
			} catch (SSLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pap = new PAPErat(client);

			pip = new PIPErat(client);

			pdp = new PDP(pip, pap);

			pep = new PEP(pdp);

			gson = new Gson();
		} else {
			System.err.println("Not a valid configuration.");
			System.exit(0);
		}
	}

	/* METHODS */

	@PostMapping("/request-access")
	public String requestAccess(@RequestBody AuthRequest request) {

		// Create a requester with request data
		Requester requester = new Requester(request.getDidSP(), request.getDidRequester(),
				request.getVerifiablePresentation());

		// Create access request
		String req = requester.requestAccess(request.getSar().getResource(), request.getSar().getAction());

		// Process access request to obtain a Capability Token
		CapabilityToken ct = process(req);
		String token = gson.toJson(ct);
		if (ct == null) {
			System.out.println("Capability Token couldn't be issued, please revise the request and try again.\n");
			return "Capability Token couldn't be issued, please revise the request and try again.\n";
		}
		System.out.println("Capability Token successfully issued.\n");
		return token;
	}

	public CapabilityToken process(String req) {

		// Send request to PEP for issuing the Capability token
		String requestJson = gson.toJson(req);
		CapabilityToken ct = pep.sendRequest(requestJson);

		return ct;
	}

	@PostMapping("/connector-access-token")
	public String trying(@RequestBody AuthRequestConnectorToken request) {

		String jwtString = request.getAccessToken();
		
		pep.setJwtString(jwtString);
		
		// Parse JWT
		JWT jwt = null;
		try {
			jwt = JWTParser.parse(jwtString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Decode JWT claims
		JWTClaimsSet claimsSet = null;
		try {
			claimsSet = jwt.getJWTClaimsSet();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Construct string JSON
		String jsonString = null;

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz uuuu", Locale.ENGLISH);

		Object expClaim = claimsSet.getClaim("exp");
		String expString;
		if (expClaim instanceof Date) {
			expString = ((Date) expClaim).toString();
		} else {
			expString = (String) expClaim;
		}

		ZonedDateTime zonedDateTime = ZonedDateTime.parse(expString, formatter);

		long epochSeconds = zonedDateTime.toEpochSecond();

		Map<String, Object> jo = null;
		try {
			jo = claimsSet.getJSONObjectClaim("verifiableCredential");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String jsonString1 = gson.toJson(jo);

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
		jsonObject.addProperty("verifiableCredential", jsonString1);

		String jsonString2 = gson.toJson(jsonObject);

		// Create a requester with request data -> change the token tango instead VP
		Requester requester = new Requester(request.getDidSP(), request.getDidRequester(), jsonString2);

		// Create access request
		String req = requester.requestAccessToken(request.getSar().getResource(), request.getSar().getAction(),
				jsonString2);
		// Process access request to obtain a Capability Token
		CapabilityToken ct = processConnectorToken(req);
		String token = gson.toJson(ct);
		if (ct == null) {
			System.out.println("Capability Token couldn't be issued, please revise the request and try again.\n");
			return "Capability Token couldn't be issued, please revise the request and try again.\n";
		}
		System.out.println("Capability Token successfully issued.\n");
		return token;
	}

	public CapabilityToken processConnectorToken(String req) {

		// Send request to PEP for issuing the Capability token -> change the PEP
		// treatment
		String requestJson = gson.toJson(req);
		CapabilityToken ct = pep.sendConnectorToken(requestJson);
		return ct;
	}

	@PostMapping("/access-with-token")
	public String accessWithToken(@RequestBody AccessRequest request) {

		// Verify capability token
		String requestJson = gson.toJson(request);
		//System.out.println(requestJson);
		String response = pep.validateCapabilityToken(requestJson);

		return response;
	}
	
	/*
	@PostMapping("/new-policy")
	public void newPolicy(@RequestBody PolicyRequest request) {
	if(pdpConfig.equals("test")) {
			Policy p=gson.fromJson(request.getPolicy(), Policy.class);
			pap.addPolicy(request.getDidSP(), p, request.getResource());
			System.out.println("Policy: " + request.getPolicy()+ " added to the Policy Administration Point for the "+request.getDidSP()+ " and the resource "+request.getResource());
		}else if(pdpConfig.equals("eratosthenes")) {
			/*
			 * Policy p=gson.fromJson(request.getPolicy(), Policy.class);
			 * pap.addPolicy(request.getDidSP(), p, request.getResource());
			 * */
			
	//	}

	//}*/
	

@PostMapping("/auth")
public String handleAuthPolicyRequest(@RequestHeader("Authorization") String authorizationHeader, @RequestBody AuthPolicyRequest request) {  
       // header token Bearer
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            // Process token -> verify signature
            boolean isValid=jwtVerifier.verifyJwtLived(token);
            if(isValid==true) {
            	System.out.println("Verified signature of Bearer Token");
            	// Generate short-lived JWT
                String short_lived_jwt=jwtGenerator.generateShortLivedJWT(request.getSub(), request.getScope());
                System.out.println("Generated short-lived JWT for requester: "+short_lived_jwt);
                return  short_lived_jwt;
            }
            else {System.out.println("Signature of Bearer Token couldn't be verified.");}
      
        } else {
          return "Authorization header not found.";
        }
        return "The Authorization process couldn't be completed";
    }

@PostMapping("/new-policy")
public ResponseEntity<String> newPolicy(@RequestBody PolicyRequest request) {
	if(pdpConfig.equals("test")) {
		int policyCounter=((PAPTest) pap).getPolicyCounter();
		policyCounter++;
		String policyID= "did:politica:"+String.valueOf(policyCounter);
		System.out.println("Policy will be added with internal ID: "+policyID);
		 // Process short lived token -> verify signature
        boolean isValid=jwtVerifier.verifyJwtLived(request.getJwtAuth());
		if(isValid) {
			try {
			Policy p=gson.fromJson(request.getPolicy(), Policy.class);
			p.setId(policyID);
			pap.addPolicy(p, request.getResource());
			System.out.println("Policy: " + request.getPolicy() + " added to the Policy Administration Point for the resource "+request.getResource());
			return ResponseEntity.status(HttpStatus.OK).body("\"Policy: \"" + request.getPolicy() +" \" added to the Policy Administration Point for the resource \""+request.getResource() +" \" ");
			
			} catch (Exception e) {
				System.out.println("Error: 400 Bad Request");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: 400 Bad Request");
			}
		}else {System.out.println("Error: 403 Forbidden"); return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error: 403 Forbidden");}
	}else if(pdpConfig.equals("eratosthenes")) {
			/*
			 * Policy p=gson.fromJson(request.getPolicy(), Policy.class);
			 * pap.addPolicy(request.getDidSP(), p, request.getResource());
			 * */
		}
	return null;
	}
	

	public PDP getPdp() {
		return pdp;
	}

	public void setPdp(PDP pdp) {
		this.pdp = pdp;
	}
	
	
}
