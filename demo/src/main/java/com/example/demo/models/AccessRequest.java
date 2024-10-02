package com.example.demo.models;

import com.google.gson.JsonObject;
import com.nimbusds.jose.shaded.json.JSONObject;

public class AccessRequest {

	/* ATTRIBUTES */
	private CapabilityToken ct;
	private SimpleAccessRight sar;
	private JSONObject queryParameters;

	/* CONSTRUCTORS */
	public AccessRequest() {
	}

	public AccessRequest(CapabilityToken ct, SimpleAccessRight sar,JSONObject queryParameters) {
		this.ct = ct;
		this.sar = sar;
		this.queryParameters=queryParameters;
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

	public JSONObject getQueryParameters() {
		return queryParameters;
	}

	public void setQueryParameters(JSONObject queryParameters) {
		this.queryParameters = queryParameters;
	}
}
