package com.example.demo.JWTManager;
	import com.nimbusds.jose.*;
	import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
	import com.nimbusds.jwt.SignedJWT;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;

	public class JWTGenerator {
		//PDP Private Key information
		@Value("${app.PDP_KS: \"/app/crypto/serverErat.ks\"}")
		static String keystore;

		@Value("${app.PDP_PW:hola123}")
		static String keystorepwd;

		@Value("${app.PDP_ALIAS:MiAliasPriv}")
		static String alias;
		
		
		public JWTGenerator() {

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

		public String generateLongLivedJWT() {
			   try {
		            KeyStore keystore1 = KeyStore.getInstance("JKS");
		            try (FileInputStream fis = new FileInputStream(keystore)) {
		                keystore1.load(fis, keystorepwd.toCharArray());
		            }
		            // Obtain private key
		            PrivateKey privateKey = (PrivateKey) keystore1.getKey(alias, keystorepwd.toCharArray());
		            if (privateKey == null) {
		                throw new RuntimeException("The private key couldn't be loaded, please try again.");
		            }
		            JWSSigner signer = new RSASSASigner(privateKey);

		            // Build JWT 
		            Date now = new Date();
		            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
		                    .subject("TANGO Requester")
		                    .issuer("TANGO PEP/PDP Component")
		                    .claim("role", "user")
		                    .issueTime(now)
		                    .expirationTime(new Date(now.getTime() + (2L * 365 * 24 * 60 * 60 * 1000))) //2 years
		                    .build();

		            SignedJWT signedJWT = new SignedJWT(
		                    new JWSHeader.Builder(JWSAlgorithm.RS256).type(JOSEObjectType.JWT).build(),
		                    claimsSet
		            );

		            // Sign JWT
		            signedJWT.sign(signer);
		            String jwtString = signedJWT.serialize();
		            return jwtString;
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
				return null;
		}
		
		public String generateShortLivedJWT(String sub, String scope) {
		        try {
		            KeyStore keystore1 = KeyStore.getInstance("JKS");
		            try (FileInputStream fis = new FileInputStream(keystore)) {
		                keystore1.load(fis, keystorepwd.toCharArray());
		            }
		            // Obtain private key
		            PrivateKey privateKey = (PrivateKey) keystore1.getKey(alias, keystorepwd.toCharArray());
		            if (privateKey == null) {
		                throw new RuntimeException("The private key couldn't be loaded, please try again.");
		            }
		            JWSSigner signer = new RSASSASigner(privateKey);

		            // Build JWT 
		            Date now = new Date();
		            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
		                    .subject(sub)
		                    .issuer("TANGO PEP/PDP Component")
		                    .expirationTime(new Date(now.getTime() + 60 * 60 * 1000)) //1 hour
		                    .issueTime(now)
		                    .claim("scope", scope) 
		                    .build();

		            SignedJWT signedJWT = new SignedJWT(
		                    new JWSHeader.Builder(JWSAlgorithm.RS256).type(JOSEObjectType.JWT).build(),
		                    claimsSet
		            );

		            // Sign JWT
		            signedJWT.sign(signer);
		            String jwtString = signedJWT.serialize();
		          //  System.out.println("Generated JWT: " + jwtString);
		            return jwtString;
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
				return null;
		    
		}

}
