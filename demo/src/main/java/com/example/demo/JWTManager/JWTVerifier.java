package com.example.demo.JWTManager;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyStore;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.springframework.beans.factory.annotation.Value;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.jwk.source.RemoteJWKSet;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jose.shaded.json.JSONArray;
import com.nimbusds.jose.shaded.json.JSONObject;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;

public class JWTVerifier {

	// PDP Private Key information
	@Value("${app.PDP_KS: \"/app/crypto/serverErat.ks\"}")
	static String keystore;

	@Value("${app.PDP_PW:hola123}")
	static String keystorepwd;

	@Value("${app.PDP_ALIAS:MiAliasPriv}")
	static String alias;

	public JWTVerifier() {

		// PDP Cryptographic Information

		keystore = System.getenv("PDP_KS");
		if (System.getenv("PDP_KS") == null) {
			keystore = "crypto/serverErat.ks";
		}

		keystorepwd = System.getenv("PDP_PW");
		if (System.getenv("PDP_PW") == null) {
			keystorepwd = "hola123";
		}

		alias = System.getenv("PDP_ALIAS");
		if (System.getenv("PDP_ALIAS") == null) {
			alias = "MiAliasPriv";
		}
	}

	// Method for verifying JWT using the public key from verifier
	public boolean verifyJwt(String jwtString, String jwksUrl) {
		try {
			// Obtain JWKS
			JWKSource<SecurityContext> keySource = new RemoteJWKSet<>(new URL(jwksUrl));

			JWKSet jwkSet = JWKSet.load(new URL(jwksUrl).openStream());

			// Parse the JWT
			SignedJWT jwt = SignedJWT.parse(jwtString);

			for (JWK key : jwkSet.getKeys()) {
				if (key.getKeyID().equals(jwt.getHeader().getKeyID())) {
					System.out.println("Matching JWK Found: " + key.toJSONString());
				}
			}

			// Create a key selector based on the JWKS
			JWSVerificationKeySelector<SecurityContext> selector = new JWSVerificationKeySelector<>(JWSAlgorithm.RS256,
					keySource);

			// Create a JWT processor with the key selector
			ConfigurableJWTProcessor<SecurityContext> jwtProcessor = new DefaultJWTProcessor<>();
			jwtProcessor.setJWSKeySelector(selector);

			// Verify the JWT
			try {
				JWTClaimsSet jwtClaims = jwtProcessor.process(jwtString, null);
			} catch (Exception e) {
				// TODO: handle exception
				System.err.println("The process of verifying JWT failed.");
				e.printStackTrace();
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

	// Method for verifying JWT using the PDP public key
	public boolean verifyJwtLived(String jwtString, String scope) {
		boolean scopeOk = false;
		try {
			// Header, Payload, Signature
			String[] parts = jwtString.split("\\.");
			if (parts.length != 3) {
				System.err.println("Wrong JWT format");
				return false;
			}

			String headerAndPayload = parts[0] + "." + parts[1];
			byte[] signatureBytes = Base64.getUrlDecoder().decode(parts[2]);

			// Check Scope
			if ((!scope.equals("long-lived"))) {
				 String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]));

			        com.google.gson.JsonObject payload = JsonParser.parseString(payloadJson).getAsJsonObject();

			        String scope1 = payload.has("scope") ? payload.get("scope").getAsString() : "No scope found";

				System.out.println("scope in the JWT " + scope1);
				System.out.println("current scope: "+scope);
				if (scope.equals(scope1)) {
					scopeOk = true;
					System.out.println("Scope is successfully verified: " + scope + "\n");
				}
				
			} else if (scope.equals("long-lived")) {
				scopeOk = true;
			} else {
				return false;
			}
			// Check signature
			Signature signature = Signature.getInstance("SHA256withRSA");

			// Load Public key from keystore
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

			//Check expiration
			 String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]));

		        com.google.gson.JsonObject payload = JsonParser.parseString(payloadJson).getAsJsonObject();

		        long exp = payload.has("exp") ? payload.get("exp").getAsLong() : 0;
		        long now = System.currentTimeMillis() / 1000L; // tiempo actual en segundos
		        if (now > exp) {
		            System.out.println("Expired JWT");
		            return false;
		        }
			
			if(scopeOk) {
			return isValid;
			}else {return false;}
		} catch (Exception e) {
			// e.printStackTrace();
			System.err.println("There was an error verifying the JWT.");
			return false;
		}

	}
}
