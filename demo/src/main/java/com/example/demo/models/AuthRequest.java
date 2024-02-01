package com.example.demo.models;

import com.example.demo.requester.Requester;

public class AuthRequest {

	/* ATTRIBUTES */
	String didSP;
	SimpleAccessRight sar;
	String didRequester;
	VPresentation verifiablePresentation;

	/* CONSTRUCTORS */
	public AuthRequest(String resource, String action, Requester requester) {
		this.didSP = requester.getDidSP();
		this.sar = new SimpleAccessRight(action, resource);
		this.didRequester = requester.getDidRequester();
		this.verifiablePresentation = requester.getVP_base64();
	}

	public AuthRequest() {
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

	public VPresentation getVerifiablePresentation() {
		return verifiablePresentation;
	}

	public void setVerifiablePresentation(VPresentation verifiablePresentation) {
		this.verifiablePresentation = verifiablePresentation;
	}

}
