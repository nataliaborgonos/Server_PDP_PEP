package com.example.demo.models;

public class GetPoliciesRequest {
	/* ATTRIBUTES */
	private String jwtAuth;

	public GetPoliciesRequest() {
		
	}

	public GetPoliciesRequest(String jwtAuth) {
		this.jwtAuth = jwtAuth;
	}

	public String getJwtAuth() {
		return jwtAuth;
	}

	public void setJwtAuth(String jwtAuth) {
		this.jwtAuth = jwtAuth;
	} 

	
}
