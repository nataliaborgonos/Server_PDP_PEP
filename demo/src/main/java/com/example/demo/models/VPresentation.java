package com.example.demo.models;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class VPresentation {
	//Metadata
	private String id;
	private String type;
	private String issuingDate;
	//VerifiableCredential
    private List<VCredential> verifiableCredential;
    //private Map<String, String> proof; //Tengo que hacer una clase especifica de proof
    private Proof proof;

   /* public VPresentation(List<VCredential> verifiableCredential, Map<String, String> proof) {
    	this.id=UUID.randomUUID().toString().substring(0, 8);
    	this.type="VerifiablePresentation";
    	LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
    	String nowStr = now.format(DateTimeFormatter.ISO_DATE_TIME);
    	this.issuingDate=nowStr;
    	this.verifiableCredential = verifiableCredential;
        this.proof = proof;
    }*/
    public VPresentation() {}
    public VPresentation(List<VCredential> verifiableCredential) {
    	this.id=UUID.randomUUID().toString().substring(0, 8);
    	this.type="VerifiablePresentation";
    	LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
    	String nowStr = now.format(DateTimeFormatter.ISO_DATE_TIME);
    	this.issuingDate=nowStr;
    	this.verifiableCredential=verifiableCredential;
    }

    public List<VCredential> getVerifiableCredential() {
        return verifiableCredential;
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

	public String getIssuingDate() {
		return issuingDate;
	}

	public void setIssuingDate(String issuingDate) {
		this.issuingDate = issuingDate;
	}

	public void setVerifiableCredential(List<VCredential> verifiableCredential) {
        this.verifiableCredential = verifiableCredential;
    }

	public Proof getProof() {
		return proof;
	}

	public void setProof(Proof proof) {
		this.proof = proof;
	}

    //public Map<String, String> getProof() {
      //  return proof;
    //}

    //public void setProof(Map<String, String> proof) {
      //  this.proof = proof;
    //}

   
}

