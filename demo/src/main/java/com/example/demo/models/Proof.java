package com.example.demo.models;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.SignedJWT;
public class Proof {
	
		String type;
		String created;
		String verificationMethod;
		String proofPurpose;
	    String jws; 
	   // String proofValue; -> SI NO SE USA JsonWebSignature
	   
	    public Proof() {}
	    
	    public Proof(String contentToSign) {
	    	this.type="PsmsBlsSignature2022";
	    	LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
	    	String nowStr = now.format(DateTimeFormatter.ISO_DATE_TIME);
	       System.out.println("Proof creado: "+nowStr);
	       	this.created=nowStr;
	       	this.verificationMethod="did:fabric:98147c6a2a0e5ab92a308124b5ad7b55986b7ebfba0e5e0d0c9f2d56a93407e8:2fd02be71ab8812cf681a500dfeb3e5524f5bda99aa56a07d59d03f716226946#didkey";
	       	this.proofPurpose="assertionMethod";
	   
	    	Jws jws1 = new Jws();
	    	String jwk=null;
	    	   // Generar un par de claves RSA en formato JWK
	    	RSAKey rsaJWK = jws1.generateRSAJWK();
	    
	     // Crear un objeto JWS firmado con la clave privada RSA
	        try {
				jwk = jws1.createJWS(contentToSign, rsaJWK);
			} catch (JOSEException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			jws=jwk;
			
	    }
	    
	    public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getCreated() {
			return created;
		}
		public void setCreated(String created) {
			this.created = created;
		}
		public String getVerificationMethod() {
			return verificationMethod;
		}
		public void setVerificationMethod(String verificationMethod) {
			this.verificationMethod = verificationMethod;
		}
		public String getProofPurpose() {
			return proofPurpose;
		}
		public void setProofPurpose(String proofPurpose) {
			this.proofPurpose = proofPurpose;
		}
		public String getJws() {
			return jws;
		}
		public void setJws(String jws) {
			this.jws = jws;
		}
		
}
