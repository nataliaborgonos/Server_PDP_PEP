package com.example.demo.models;

public class TSMScoreRequest {
	 private String entity_did;
	    private int config_id;
		public TSMScoreRequest(String entity_did, int config_id) {
			this.entity_did = entity_did;
			this.config_id = config_id;
		}
		public TSMScoreRequest() {}
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
		
}
