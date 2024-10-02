package com.example.demo.PDP;

import java.net.URL;

import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.jwk.source.RemoteJWKSet;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;

public class JWTVerifier {
	//Method for verifying JWT using the public key
	    public boolean verifyJwt(String jwtString, String jwksUrl) {
	        try {
	            // Obtain JWKS
	            JWKSource<SecurityContext> keySource = new RemoteJWKSet<>(new URL(jwksUrl));

	            
	            // Parse the JWT
	            SignedJWT jwt = SignedJWT.parse(jwtString);
	            
	            // Create a key selector based on the JWKS
	            JWSVerificationKeySelector<SecurityContext> selector = new JWSVerificationKeySelector<>(jwt.getHeader().getAlgorithm(), keySource);

	            // Create a JWT  processor with the key selector 
	            ConfigurableJWTProcessor<SecurityContext> jwtProcessor = new DefaultJWTProcessor<>();
	            jwtProcessor.setJWSKeySelector(selector);

	            // Verify the JWT
	            try {
	            	JWTClaimsSet jwtClaims = jwtProcessor.process(jwtString, null);
				} catch (Exception e) {
					// TODO: handle exception
					System.err.println("The process of verifying JWT failed.");
					return false;
				}
	            
	            

	            // If the verification is successful
	            return true;

	        } catch (Exception e) {
	            // If there is an error
	        	e.printStackTrace();
	            System.err.println("The access token is expired or couldn't be verified. Please check it and try again.");
	            return false;
	        }
	    }

}
