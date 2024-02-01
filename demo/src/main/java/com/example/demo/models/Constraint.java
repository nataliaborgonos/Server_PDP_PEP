package com.example.demo.models;

import java.util.ArrayList;
import java.util.List;

public class Constraint {
	private List<Field> fields;
	
	public Constraint() {
		this.fields=new ArrayList<Field>();
	}

	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}
	
}
