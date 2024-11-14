package com.example.demo.models;

public class PolicyRequest {
	/* ATTRIBUTES */
	private String jwtAuth; 
	private String policy;
	private String resource;
	
	public PolicyRequest() {}
	public PolicyRequest(String jwtAuth, String policy, String resource) {
		this.jwtAuth=jwtAuth;
		this.policy=policy;
		this.resource=resource;
	}
	public String getPolicy() {
		return policy;
	}
	public void setPolicy(String policy) {
		this.policy = policy;
	}
	public String getResource() {
		return resource;
	}
	public void setResource(String resource) {
		this.resource = resource;
	}
	
	public String getJwtAuth() {
		return jwtAuth;
	}
	public void setJwtAuth(String jwtAuth) {
		this.jwtAuth = jwtAuth;
	}
}
