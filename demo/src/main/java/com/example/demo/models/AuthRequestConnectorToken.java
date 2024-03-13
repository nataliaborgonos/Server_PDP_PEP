package com.example.demo.models;

import com.example.demo.requester.Requester;

public class AuthRequestConnectorToken {
	/* ATTRIBUTES */
	String didSP;
	SimpleAccessRight sar;
	String didRequester;
	String accessToken;
	
	/* CONSTRUCTORS */
	
	public AuthRequestConnectorToken(String resource, String action, Requester requester,String token) {
		this.didSP = requester.getDidSP();
		this.sar = new SimpleAccessRight(action, resource);
		this.didRequester = requester.getDidRequester();
		this.accessToken = token;
	}



	public AuthRequestConnectorToken() {
	}



	/* GETTER AND SETTER METHODS */
	public String getDidSP() {
		return didSP;
	}

	public void setDidSP(String didSP) {
		this.didSP = didSP;
	}

	public SimpleAccessRight getSar() {
		return sar;
	}

	public void setSar(SimpleAccessRight sar) {
		this.sar = sar;
	}

	public String getDidRequester() {
		return didRequester;
	}

	public void setDidRequester(String didRequester) {
		this.didRequester = didRequester;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	
}
