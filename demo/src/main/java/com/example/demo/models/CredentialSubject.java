package com.example.demo.models;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class CredentialSubject {
	 private String id;
	 private String nombre;
	 private Degree degree;

	 public CredentialSubject() {}
	 

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


		public Degree getDegree() {
			return degree;
		}


		public void setDegree(Degree degree) {
			this.degree = degree;
		}

	    }
