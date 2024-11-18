package com.example.demo.models;

import com.nimbusds.jose.shaded.json.JSONArray;

public class TSMScoreResponse {
	 private String entity_did;
	 private int config_id;
	 private JSONArray characteristic_score; 
	 private double minimum_value;
	 int score;
	 
	 public TSMScoreResponse() {}
		public TSMScoreResponse(String entity_did, int config_id, JSONArray characteristic_score, double minimum_value,
				int score) {
			this.entity_did = entity_did;
			this.config_id = config_id;
			this.characteristic_score = characteristic_score;
			this.minimum_value = minimum_value;
			this.score = score;
		}
		 
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
	public JSONArray getCharacteristic_score() {
		return characteristic_score;
	}
	public void setCharacteristic_score(JSONArray characteristic_score) {
		this.characteristic_score = characteristic_score;
	}
	public double getMinimum_value() {
		return minimum_value;
	}
	public void setMinimum_value(double minimum_value) {
		this.minimum_value = minimum_value;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}

	 

}
