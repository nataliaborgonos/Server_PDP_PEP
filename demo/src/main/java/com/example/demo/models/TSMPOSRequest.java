package com.example.demo.models;

import java.util.ArrayList;

import com.nimbusds.jose.shaded.json.JSONObject;

public class TSMPOSRequest {
	String entity_did;
	int config_id;
	ArrayList<JSONObject> protective_objectives;
	
	public TSMPOSRequest(String entity_did, int config_id, ArrayList<JSONObject> protective_objectives) {
		this.entity_did = entity_did;
		this.config_id =  config_id;
		this.protective_objectives = protective_objectives;
	}
	
	public TSMPOSRequest() {}
	
	public String getEntity_did() {
		return entity_did;
	}
	public void setEntity_did(String entity_did) {
		this.entity_did = entity_did;
	}
	
	public int getConfig_id() {
		return config_id;
	}

	public void setConfig_id(int config_id) {
		this.config_id = config_id;
	}

	public ArrayList<JSONObject> getProtective_objectives() {
		return protective_objectives;
	}
	public void setProtective_objectives(ArrayList<JSONObject> protective_objectives) {
		this.protective_objectives = protective_objectives;
	}

	
	
}
