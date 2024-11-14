package com.example.demo.JWTManager;

import java.io.FileInputStream;
import java.net.URL;
import java.security.KeyStore;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;

import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.jwk.source.RemoteJWKSet;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;

public class JWTVerifier {
	
	//PDP Private Key information
	@Value("${app.PDP_KS: \"/app/crypto/serverErat.ks\"}")
	static String keystore;

	@Value("${app.PDP_PW:hola123}")
	static String keystorepwd;

	@Value("${app.PDP_ALIAS:MiAliasPriv}")
	static String alias;
	
	public JWTVerifier() {
		
		//PDP Cryptographic Information
		
		keystore = System.getenv("PDP_KS");
		if( System.getenv("PDP_KS")==null) {
			keystore="crypto/serverErat.ks";
		}
		
		keystorepwd = System.getenv("PDP_PW");
		if(System.getenv("PDP_PW")==null) {
			keystorepwd="hola123";
		}
		
		alias = System.getenv("PDP_ALIAS");
		if(System.getenv("PDP_ALIAS")==null) {
			alias="MiAliasPriv";
		}
	}
	
	//Method for verifying JWT using the public key from verifier
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

		//Method for verifying JWT using the PDP public key 
	    public boolean verifyJwtLived(String jwtString) {
	    	try {
	            // Header, Payload, Signature
	            String[] parts = jwtString.split("\\.");
	            if (parts.length != 3) {
	                System.err.println("Wrong JWT format");
	                return false;
	            }

	            String headerAndPayload = parts[0] + "." + parts[1];
	            byte[] signatureBytes = Base64.getUrlDecoder().decode(parts[2]);

	            // Verify signature
	            Signature signature = Signature.getInstance("SHA256withRSA");  // Asegúrate de usar el algoritmo adecuado

	        //Load Public key from keystore
	            KeyStore keyStore = KeyStore.getInstance("JKS");
	            try (FileInputStream fis = new FileInputStream(keystore)) {
	                keyStore.load(fis, keystorepwd.toCharArray());
	            }
	            PublicKey publicKey = keyStore.getCertificate(alias).getPublicKey();
	            if (publicKey == null) {
	                throw new RuntimeException("Public Key couldn't be loaded.");
	            }

	            signature.initVerify(publicKey);

	            signature.update(headerAndPayload.getBytes());

	            boolean isValid = signature.verify(signatureBytes);
	            return isValid;

	        } catch (Exception e) {
	           // e.printStackTrace();
	        	System.err.println("There was an error verifying the JWT.");
	            return false;
	        }
	    
	    }
}
