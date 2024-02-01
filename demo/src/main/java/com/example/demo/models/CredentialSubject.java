package com.example.demo.models;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class CredentialSubject {
	 private String id;
	 private String nombre;
	 private String apellido;
	 private String birthDate;
	 private int age;

	 public CredentialSubject() {}
	    public CredentialSubject(String id, String nombre, String apellido, String birthDate) {
	        this.id = id;
	        this.nombre=nombre;
	        this.apellido=apellido;
	        this.birthDate = birthDate;
	        LocalDate fechaNacim = LocalDate.parse(birthDate);
	        LocalDate fechaActual = LocalDate.now();
	        this.age= Period.between(fechaNacim, fechaActual).getYears();
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

		public String getApellido() {
			return apellido;
		}

		public void setApellido(String apellido) {
			this.apellido = apellido;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}

		public String getBirthDate() {
	        return birthDate;
	    }

	    public void setBirthDate(String birthDate) {
	        this.birthDate = birthDate;
	    }
	    
	    public List<String> getMatches(){
	    	List<String> matches=new ArrayList<String>();
	    	//split('.')
	    	if(!nombre.equals(null)) {
	    		String match="$.credentialSubject.nombre";
	    		matches.add(match);
	    	}
	    	if(!apellido.equals(null)) {
	    		String match="$.credentialSubject.apellido";
	    		matches.add(match);
	    	}
			return matches;
	    	
	    }
	    public List<String> getMatchesString(){
	    	List<String> matches=new ArrayList<String>();
	    	if(!nombre.equals(null)) {
	    		String match="$.credentialSubject.nombre";
	    		matches.add(match);
	    	}
	    	if(!apellido.equals(null)) {
	    		String match="$.credentialSubject.apellido";
	    		matches.add(match);
	    	}
			return matches;
	    	
	    }
	    
	    public List<String> getMatchesFilterNumber(){
	    	List<String> matches=new ArrayList<String>();
	    	if(!nombre.equals(null)) {
	    		String match="$.credentialSubject.nombre";
	    		matches.add(match);
	    	}
	    	if(!apellido.equals(null)) {
	    		String match="$.credentialSubject.apellido";
	    		matches.add(match);
	    	}
			return matches;
	    	
	    }
}
