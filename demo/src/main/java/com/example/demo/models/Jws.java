package com.example.demo.models;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.nimbusds.jwt.util.DateUtils;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.EdECKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.util.Date;

import org.bouncycastle.math.ec.rfc8032.Ed25519;

public class Jws {
	  public RSAKey generateRSAJWK() {
	        try {
				return new RSAKeyGenerator(2048)
				        .keyUse(KeyUse.SIGNATURE)
				        .generate();
			} catch (JOSEException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return null;
	    }

	    public  String createJWS(String verifiablePresentation, RSAKey rsaJWK) throws JOSEException {
	        // Construir el encabezado JWS con el ID de la clave
	    	JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256)
	    	        .keyID(rsaJWK.getKeyID())
	    	        .build();
	        // Construir la carga útil (claims)
	        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
	                .subject("subject")
	                .claim("payload", verifiablePresentation)
	                .build();

	        // Construir el objeto JWS firmado
	        SignedJWT signedJWT = new SignedJWT(header, claimsSet);

	        // Firma el JWS con la clave privada RSA
	        RSASSASigner signer = new RSASSASigner(rsaJWK.toRSAPrivateKey());
	        signedJWT.sign(signer);

	        // Serializa el JWS como una cadena
	        return signedJWT.serialize();
	    }

	    public  void verifySignature(String generatedJWS, RSAKey rsaPublicKey) throws JOSEException {
	        // Parsear el JWS
	        SignedJWT signedJWT=null;
			try {
				signedJWT = SignedJWT.parse(generatedJWS);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	        // Crear un verificador de firma con la clave pública RSA
	        RSASSAVerifier verifier = new RSASSAVerifier(rsaPublicKey.toRSAPublicKey());

	        // Verificar la firma del JWS
	        if (signedJWT.verify(verifier)) {
	            System.out.println("La firma del JWS es válida");
	            try {
					System.out.println("Contenido: " + signedJWT.getJWTClaimsSet().getClaim("payload"));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        } else {
	            System.out.println("La firma del JWS no es válida");
	        }
	    }
	}