package com.example.demo.models;

public class DeletePolicyRequest {
	/* ATTRIBUTES */
	private String jwtAuth;
	private String policyID;
	
	public  DeletePolicyRequest() {
		
	}

	public  DeletePolicyRequest(String jwtAuth, String policyID) {
		this.jwtAuth = jwtAuth;
		this.policyID=policyID;
	}

	public String getPolicyID() {
		return policyID;
	}

	public void setPolicyID(String policyID) {
		this.policyID = policyID;
	}

	public String getJwtAuth() {
		return jwtAuth;
	}

	public void setJwtAuth(String jwtAuth) {
		this.jwtAuth = jwtAuth;
	} 

}
