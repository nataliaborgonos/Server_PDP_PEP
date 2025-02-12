package com.example.demo.models;

import java.util.ArrayList;
import java.util.List;

public class Field {
     	
        private List<String> path;
        private Filter filter;
       

	public Field() {
		
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

