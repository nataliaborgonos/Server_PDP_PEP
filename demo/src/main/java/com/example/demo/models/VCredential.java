package com.example.demo.models;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class VCredential {
		private String context;
	    private String credentialSubject;
	    private String expirationDate;
	    private String id;
	    private String issuanceDate;
	    private Issuer issuer;
	    private Proof proof;
	    private String referenceNumber;
	    private ArrayList<String> type;


	    public VCredential(String id, String type, Issuer issuer, String issuanceDate, String credentialSubject) {
	        this.id = id;
	        this.issuer = issuer;
	        this.issuanceDate = issuanceDate;
	        this.credentialSubject = credentialSubject;
	    }
	    
	    public VCredential() {}
	    
	   

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

		public Issuer getIssuer() {
	        return issuer;
	    }

	    public void setIssuer(Issuer issuer) {
	        this.issuer = issuer;
	    }

	    public String getIssuanceDate() {
	        return issuanceDate;
	    }

	    public void setIssuanceDate(String issuanceDate) {
	        this.issuanceDate = issuanceDate;
	    }

	    public String getCredentialSubject() {
	        return credentialSubject;
	    }

	    public void setCredentialSubject(String credentialSubject) {
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


