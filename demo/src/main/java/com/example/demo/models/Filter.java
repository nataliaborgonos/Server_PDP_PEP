package com.example.demo.models;

public class Filter {
	 private String type;
     private Number min;
     private Number max;
     private String pattern;
     
     public Filter(String type) {
    	 this.type=type;
     }

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Number getMin() {
		return min;
	}

	public void setMin(Number min) {
		this.min = min;
	}

	public Number getMax() {
		return max;
	}

	public void setMax(Number max) {
		this.max = max;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
}
