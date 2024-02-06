package com.example.demo.models;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class VCredential {
		private String context;
	    private CredentialSubject credentialSubject;
	    private String expirationDate;
	    private String id;
	    private String issuanceDate;
	    private String issuer;
	    private Proof proof;
	    private String referenceNumber;
	    private ArrayList<String> type;


	    public VCredential(String id, String type, String issuer, String issuanceDate, CredentialSubject credentialSubject) {
	        this.id = id;
	        this.issuer = issuer;
	        this.issuanceDate = issuanceDate;
	        this.credentialSubject = credentialSubject;
	    }
	    
	    public VCredential() {}
	    
	    public VCredential(CredentialSubject cs) {
	    	this.id=UUID.randomUUID().toString().substring(0, 8);
	    	this.issuer="Erathostenes";
	    	LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
	    	String nowStr = now.format(DateTimeFormatter.ISO_DATE_TIME);
	    	this.issuanceDate=nowStr;
	    	this.credentialSubject=cs;
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


	    public String getContext() {
			return context;
		}

		public void setContext(String context) {
			this.context = context;
		}

		public String getExpirationDate() {
			return expirationDate;
		}

		public void setExpirationDate(String expirationDate) {
			this.expirationDate = expirationDate;
		}

		public String getReferenceNumber() {
			return referenceNumber;
		}

		public void setReferenceNumber(String referenceNumber) {
			this.referenceNumber = referenceNumber;
		}

		public ArrayList<String> getType() {
			return type;
		}

		public void setType(ArrayList<String> type) {
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


