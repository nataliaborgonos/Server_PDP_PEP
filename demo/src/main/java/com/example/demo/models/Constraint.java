package com.example.demo.models;

import java.util.ArrayList;
import java.util.List;

public class Constraint {
	
	/* ATRIBUTTES */
	private List<Field> fields;
	
	/* CONSTRUCTOR */
	public Constraint() {
		this.fields=new ArrayList<Field>();
	}

	/* GETTER AND SETTER METHODS*/
	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}
	
}
