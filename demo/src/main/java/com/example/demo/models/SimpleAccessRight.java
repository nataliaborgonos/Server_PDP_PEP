package com.example.demo.models;

public class SimpleAccessRight {
	String action;
	String resource;
	
	public SimpleAccessRight() {}
	public SimpleAccessRight(String action, String resource) {
		this.action=action;
		this.resource=resource;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getResource() {
		return resource;
	}
	public void setResource(String resource) {
		this.resource = resource;
	}
	
}
