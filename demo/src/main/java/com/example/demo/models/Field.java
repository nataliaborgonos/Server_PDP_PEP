package com.example.demo.models;

import java.util.ArrayList;
import java.util.List;

public class Field {
     	private String purpose;
        private String name;
        private List<String> path;
        private Filter filter;
       
	public Field(String purpose,String name){
		this.purpose=purpose;
		this.name=name;
		this.path=new ArrayList<String>();
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getPath() {
		return path;
	}

	public void setPath(List<String> path) {
		this.path = path;
	}

	public Filter getFilter() {
		return filter;
	}

	public void setFilter(Filter filter) {
		this.filter = filter;
	}
	
}

