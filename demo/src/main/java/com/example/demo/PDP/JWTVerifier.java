package com.example.demo.PDP;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.jwk.source.RemoteJWKSet;
import com.nimbusds.jose.proc.*;
import com.nimbusds.jwt.*;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;

import java.net.HttpURLConnection;
import java.net.URL;
import java.security.interfaces.RSAPublicKey;

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
	            JWTClaimsSet jwtClaims = jwtProcessor.process(jwtString, null);

	            // If the verification is successful
	            return true;

	        } catch (Exception e) {
	            // If there is an error
	            System.err.println("The access token is expired or couldn't be verified. Please check it and try again.");
	            return false;
	        }
	    }

}
