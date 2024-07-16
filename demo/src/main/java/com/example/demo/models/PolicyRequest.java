package com.example.demo.models;

public class PolicyRequest {
	/* ATTRIBUTES */
	private String didSP;
	private String policy;
	private String resource;
	
	public PolicyRequest() {}
	public PolicyRequest(String didSP,String policy, String resource) {
		this.didSP=didSP;
		this.policy=policy;
		this.resource=resource;
	}
	public String getDidSP() {
		return didSP;
	}
	public void setDidSP(String didSP) {
		this.didSP = didSP;
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
	

}
