package com.example.demo.models;

public class QueryParameters {

	String id;
	String role;
	String email;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public QueryParameters(String id, String role) {
		this.id = id;
		this.role = role;
	}
	
	
}
