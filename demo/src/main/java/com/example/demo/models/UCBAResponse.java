package com.example.demo.models;

public class UCBAResponse {
	
	boolean isAuthenticated;
	String status;
	double similarity;
	public UCBAResponse(boolean isAuthenticated, String status, double similarity) {
		this.isAuthenticated = isAuthenticated;
		this.status = status;
		this.similarity = similarity;
	}
	public boolean getIsAuthenticated() {
		return isAuthenticated;
	}
	public void setIsAuthenticated(boolean isAuthenticated) {
		this.isAuthenticated = isAuthenticated;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public double getSimilarity() {
		return similarity;
	}
	public void setSimilarity(double similarity) {
		this.similarity = similarity;
	}
}
