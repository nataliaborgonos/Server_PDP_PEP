package com.example.demo.models;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class VPresentation {
	//Metadata
	private ArrayList<String> context;
	private String holder;
	private Proof proof;
	private String type;
    private List<VCredential> verifiableCredential;
 

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
    	this.type="VerifiablePresentation";
    	LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
    	String nowStr = now.format(DateTimeFormatter.ISO_DATE_TIME);
    	this.verifiableCredential=verifiableCredential;
    }

    public List<VCredential> getVerifiableCredential() {
        return verifiableCredential;
    }

  

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public ArrayList<String> getContext() {
		return context;
	}
	public void setContext(ArrayList<String> context) {
		this.context = context;
	}
	public String getHolder() {
		return holder;
	}
	public void setHolder(String holder) {
		this.holder = holder;
	}

   
}

