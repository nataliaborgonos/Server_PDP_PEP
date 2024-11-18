package com.example.demo.models;

import java.util.ArrayList;

import com.nimbusds.jose.shaded.json.JSONObject;

public class TSMConfigRequest {
	String entity_did;
	JSONObject trustworthiness;
	ArrayList<JSONObject> characteristics;
	
	public TSMConfigRequest() {
	}
	
	public TSMConfigRequest(String entity_did, JSONObject trustworthiness, ArrayList<JSONObject> characteristics) {
		this.entity_did = entity_did;
		this.trustworthiness = trustworthiness;
		this.characteristics = characteristics;
	}

	public String getEntity_did() {
	    return entity_did;
	}

	public void setEntity_did(String entity_did) {
	    this.entity_did = entity_did;
	}

	public JSONObject getTrustworthiness() {
		return trustworthiness;
	}
	public void setTrustworthiness(JSONObject trustworthiness) {
		this.trustworthiness = trustworthiness;
	}
	public ArrayList<JSONObject> getCharacteristics() {
		return characteristics;
	}
	public void setCharacteristics(ArrayList<JSONObject> characteristics) {
		this.characteristics = characteristics;
	}
}
