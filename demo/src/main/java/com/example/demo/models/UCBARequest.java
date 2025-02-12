package com.example.demo.models;

public class UCBARequest {
	 private String externalUserId;
	    private String externalClientId;

	    public UCBARequest(String externalUserId, String externalClientId) {
	        this.externalUserId = externalUserId;
	        this.externalClientId = externalClientId;
	    }

	  
		// Getters y setters
	    public String getExternalUserId() {
	        return externalUserId;
	    }

	    public void setExternalUserId(String externalUserId) {
	        this.externalUserId = externalUserId;
	    }

	    public String getExternalClientId() {
	        return externalClientId;
	    }

	    public void setExternalClientId(String externalClientId) {
	        this.externalClientId = externalClientId;
	    }
}
