package com.example.demo.models;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;


public class Policy {
	
	String id;
	String nombre;
	String purpose;
	private String serviceProvider;
    private List<SimpleAccessRight> accessRights;
    private long authTime;
    private double minTrustScore;
    private Constraint constraints;
	
	public Policy(int id) {
		this.id="did:politica:"+id;
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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


/*
	public boolean isCumplida(String request) {
		JsonSchemaFactory factory=JsonSchemaFactory.byDefault();
		try {
			JsonSchema schema1=factory.getJsonSchema(schema);
			try {
				ok=JsonLoader.fromString(request);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			schema1.validate(ok);
			System.out.println("La solicitud cumple la politica");
			return true;
		} catch (ProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	*/
}

