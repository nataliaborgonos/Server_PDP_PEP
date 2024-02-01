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
import com.example.demo.models.CapabilityToken;
import com.example.demo.models.CredentialSubject;
import com.example.demo.models.Proof;
import com.example.demo.models.SimpleAccessRight;
import com.example.demo.models.VCredential;
import com.example.demo.models.VPresentation;

public class Requester {
	
	/* ATRIBUTOS */
	String didSP;
	String didRequester;
	String user;
	String birthdate;
	int age;
	String VPJSON;
	VPresentation VP_base64;
	
	/* ADICIONAL */
	private Gson gson =new Gson();
	PEP pep;
	CapabilityToken token;
//	idAgent agente;
	
	/* CONSTRUCTORES */
	
	public Requester(String didSP, String didRequester, VPresentation verifiablePresentation) {
		this.didSP=didSP;
		//this.sar=sar;
		this.didRequester=didRequester;
		this.user="natalia";
		birthdate="1999-01-19";
		LocalDate birthDate = LocalDate.parse(birthdate);
	    LocalDate currentDate = LocalDate.now();
	    this.age= Period.between(birthDate, currentDate).getYears();
	    this.VP_base64=verifiablePresentation;
	}
	
	public String getVPJSON() {
		return VPJSON;
	}


	public void setVPJSON(String vPJSON) {
		VPJSON = vPJSON;
	}

	
	/* M�TODOS DE CONSULTA Y MODIFICACI�N */
	
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
	
	
	/* FUNCIONALIDAD */
	
	
	
	public VPresentation getVP_base64() {
		return VP_base64;
	}

	public void setVP_base64(VPresentation vP_base64) {
		VP_base64 = vP_base64;
	}

	public String requestAccess(String recursoSolicitado, String accion) {
		AuthRequest ar=new AuthRequest(recursoSolicitado,accion,this);
		String json = gson.toJson(ar);
		return json;
	}

	public String accesoRecurso(CapabilityToken ct, SimpleAccessRight sar) {
		AccessRequest acc=new AccessRequest(ct, sar);
		String json=gson.toJson(acc);
		return json;
	}

	//Esto hay que cambiarlo y delegar en el idAgent para que genere la VPresentation -> solicitud a API 
	
	public VPresentation generaVP() {
		
		//agente.generateVP();
		
		CredentialSubject cs=new CredentialSubject(didRequester, user,"apellido", birthdate);
		VCredential vc=new VCredential(cs);
		String payloadVC=gson.toJson(vc);
		Proof pVC = new Proof(payloadVC);
		String pVCString=gson.toJson(pVC);
		vc.setProof(pVC);
				
		List<VCredential> lista=new ArrayList<>();
		lista.add(vc);
		VPresentation contentToSign=new VPresentation(lista);
		String payload=gson.toJson(contentToSign);
		System.out.println("Contenido a firmar: "+payload);
		
		Proof p = new Proof(payload);
		String pJson=gson.toJson(p);
		System.out.println("proof en json "+pJson);
		contentToSign.setProof(p);
		//Añadirle a la VPresentation el campo proof 
		
	//	Map<String,String> proof=new HashMap<>();
		//proof.put("JWS", "123");
	//	VPresentation vp=new VPresentation(lista, proof);
//		return vp;
		return  contentToSign;
	}
}

