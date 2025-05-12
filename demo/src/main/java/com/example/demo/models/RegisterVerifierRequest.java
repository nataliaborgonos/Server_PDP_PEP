package com.example.demo.models;

public class RegisterVerifierRequest {
	/* ATTRIBUTES */
	private String jwtAuth;
	private String domain;
	private String verifier;

	public RegisterVerifierRequest(){
	}

	public RegisterVerifierRequest(String jwtAuth, String domain, String verifier) {
		this.jwtAuth = jwtAuth;
		this.domain = domain;
		this.verifier=verifier;
	}

	public String getJwtAuth() {
		return jwtAuth;
	}

	public void setJwtAuth(String jwtAuth) {
		this.jwtAuth = jwtAuth;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getVerifier() {
		return verifier;
	}

	public void setVerifier(String verifier) {
		this.verifier = verifier;
	} 
	
	

}
