package com.example.demo.models;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;



public class Policy {
	
	String id;
	String name;
	String purpose;
	private String serviceProvider;
    private List<SimpleAccessRight> accessRights;
    private long authTime;
    private double minTrustScore;
    private Constraint constraints;
	
	@Override
	public String toString() {
		return "Policy [id=" + id + ", name=" +name+ ", purpose=" + purpose + ", serviceProvider=" + serviceProvider
				+ ", accessRights=" + accessRights + ", authTime=" + authTime + ", minTrustScore=" + minTrustScore
				+ ", constraints=" + constraints + "]";
	}

	public Policy(int id) {
		this.id="did:politica:"+id;
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getServiceProvider() {
		return serviceProvider;
	}

	public void setServiceProvider(String serviceProvider) {
		this.serviceProvider = serviceProvider;
	}

	public List<SimpleAccessRight> getAccessRights() {
		return accessRights;
	}

	public void setAccessRights(List<SimpleAccessRight> accessRights) {
		this.accessRights = accessRights;
	}

	public long getAuthTime() {
		return authTime;
	}

	public void setAuthTime(long authTime) {
		this.authTime = authTime;
	}

	public double getMinTrustScore() {
		return minTrustScore;
	}

	public void setMinTrustScore(double minTrustScore) {
		this.minTrustScore = minTrustScore;
	}

	public Constraint getConstraints() {
		return constraints;
	}

	public void setConstraints(Constraint constraints) {
		this.constraints = constraints;
	}

}

