package com.example.demo.models;

public class AccessRequest {

	/* ATTRIBUTES */
	private CapabilityToken ct;
	private SimpleAccessRight sar;

	/* CONSTRUCTORS */
	public AccessRequest() {
	}

	public AccessRequest(CapabilityToken ct, SimpleAccessRight sar) {
		this.ct = ct;
		this.sar = sar;
	}

	/* GETTER AND SETTER METHODS */
	public CapabilityToken getCt() {
		return ct;
	}

	public void setCt(CapabilityToken ct) {
		this.ct = ct;
	}

	public SimpleAccessRight getSar() {
		return sar;
	}

	public void setSar(SimpleAccessRight sar) {
		this.sar = sar;
	}

}
