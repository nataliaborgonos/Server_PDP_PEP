package com.example.demo.models;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class VCredential {
	    private String id;
	    private String type;
	    private String issuer;
	    private String issuanceDate;
	    private CredentialSubject credentialSubject;
	    private Proof proof;

	    public VCredential(String id, String type, String issuer, String issuanceDate, CredentialSubject credentialSubject) {
	        this.id = id;
	        this.type = type;
	        this.issuer = issuer;
	        this.issuanceDate = issuanceDate;
	        this.credentialSubject = credentialSubject;
	    }
	    
	    public VCredential() {}
	    
	    public VCredential(CredentialSubject cs) {
	    	this.id=UUID.randomUUID().toString().substring(0, 8);
	    	this.type="VerifiableCredential";
	    	this.issuer="Erathostenes";
	    	LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
	    	String nowStr = now.format(DateTimeFormatter.ISO_DATE_TIME);
	    	this.issuanceDate=nowStr;
	    	this.credentialSubject=cs;
	    	//Map<String, String> proof =new HashMap<>();
	    	//proof.put("JWS", "123");
	    }

	    public Proof getProof() {
			return proof;
		}

		public void setProof(Proof proof) {
			this.proof = proof;
		}

		public String getId() {
	        return id;
	    }

	    public void setId(String id) {
	        this.id = id;
	    }

	    public String getType() {
	        return type;
	    }

	    public void setType(String type) {
	        this.type = type;
	    }

	    public String getIssuer() {
	        return issuer;
	    }

	    public void setIssuer(String issuer) {
	        this.issuer = issuer;
	    }

	    public String getIssuanceDate() {
	        return issuanceDate;
	    }

	    public void setIssuanceDate(String issuanceDate) {
	        this.issuanceDate = issuanceDate;
	    }

	    public CredentialSubject getCredentialSubject() {
	        return credentialSubject;
	    }

	    public void setCredentialSubject(CredentialSubject credentialSubject) {
	        this.credentialSubject = credentialSubject;
	    }

	    @Override
	    public String toString() {
	        return "VerifiableCredential{" +
	                "id='" + id + '\'' +
	                ", type='" + type + '\'' +
	                ", issuer='" + issuer + '\'' +
	                ", issuanceDate='" + issuanceDate + '\'' +
	                ", credentialSubject=" + credentialSubject +
	                '}';
	    }
	}


