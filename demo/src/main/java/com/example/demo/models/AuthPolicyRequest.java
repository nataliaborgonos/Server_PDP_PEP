package com.example.demo.models;

public class AuthPolicyRequest {
	/* ATTRIBUTES */
	private String sub;
	private String scope;
	
	public AuthPolicyRequest() {}
	public AuthPolicyRequest(String sub, String scope) {
		this.sub=sub;
		this.scope=scope;
	}
	public String getSub() {
		return sub;
	}
	public void setSub(String sub) {
		this.sub = sub;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
}

