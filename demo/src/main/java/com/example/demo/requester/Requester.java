package com.example.demo.requester;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.google.gson.Gson;

import java.util.Base64;

import com.example.demo.PEP.PEP;
import com.example.demo.models.AccessRequest;
import com.example.demo.models.AuthRequest;
import com.example.demo.models.AuthRequestTango;
import com.example.demo.models.CapabilityToken;
import com.example.demo.models.CredentialSubject;
import com.example.demo.models.Proof;
import com.example.demo.models.SimpleAccessRight;
import com.example.demo.models.VCredential;
import com.example.demo.models.VPresentation;

public class Requester {
	
	/* ATTIBUTES */
	String didSP;
	String didRequester;
	String user;
	String birthdate;
	int age;
	String VPJSON;
	private Gson gson =new Gson();
	PEP pep;
	CapabilityToken token;
//	idAgent agente;
	
	/* CONSTRUCTOR */
	
	public Requester(String didSP, String didRequester, String verifiablePresentation) {
		this.didSP=didSP;
		//this.sar=sar;
		this.didRequester=didRequester;
		this.user="natalia";
		birthdate="1999-01-19";
		LocalDate birthDate = LocalDate.parse(birthdate);
	    LocalDate currentDate = LocalDate.now();
	    this.age= Period.between(birthDate, currentDate).getYears();
	    this.VPJSON=verifiablePresentation;
	}
	
	
	/* GETTER AND SETTER METHODS */
	
	public String getVPJSON() {
		return VPJSON;
	}

	public void setVPJSON(String vPJSON) {
		VPJSON = vPJSON;
	}

	public String getDidSP() {
		return didSP;
	}
	public void setDidSP(String didSP) {
		this.didSP = didSP;
	}
	public String getDidRequester() {
		return didRequester;
	}
	public void setDidRequester(String didRequester) {
		this.didRequester = didRequester;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public CapabilityToken getToken() {
		return token;
	}
	public void setToken(CapabilityToken token) {
		this.token = token;
	}
	
	
	/* METHODS */
	

	public String requestAccess(String recursoSolicitado, String accion) {
		AuthRequest ar=new AuthRequest(recursoSolicitado,accion,this);
		String json = gson.toJson(ar);
		return json;
	}
	
	public String requestAccessToken(String recursoSolicitado, String accion,String token) {
		AuthRequestTango ar=new AuthRequestTango(recursoSolicitado,accion,this, token);
		String json = gson.toJson(ar);
		return json;
	}

	
	
	
}

