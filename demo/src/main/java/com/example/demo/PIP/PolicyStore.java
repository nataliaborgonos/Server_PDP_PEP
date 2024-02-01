package com.example.demo.PIP;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.demo.models.Constraint;
import com.example.demo.models.Field;
import com.example.demo.models.Filter;
import com.example.demo.models.Policy;
import com.example.demo.models.Resource;
import com.example.demo.models.SimpleAccessRight;


public class PolicyStore {

	/* ASOCIA didSP CON RECURSO Y POL�TICAS */
	public  HashMap<String,HashMap<Resource,ArrayList<Policy>>> policies;
	
	public PolicyStore() {
		this.policies=new HashMap<String,HashMap<Resource,ArrayList<Policy>>>();

		//PROVISIONAL PARA HACER PRUEBAS
		Policy politica=new Policy(1);
		politica.setNombre("Politica ejemplo");
		politica.setPurpose("Ejemplificar");
		politica.setServiceProvider("did:serviceProvider:1");
		SimpleAccessRight sar=new SimpleAccessRight("GET", "/temperatura");
		List<SimpleAccessRight> accessRights=new ArrayList<>();
		accessRights.add(sar);
		politica.setAccessRights(accessRights);
		politica.setAuthTime(123123123);
		politica.setMinTrustScore(0.5);
		Field field1=new Field("Debe revelar nombre y appellido","Restricción de revelar");
		List<String> path=new ArrayList<String>();
		//path.add("$.credentialSubject.nombre");
		//path.add("$.credentialSubject.apellido");
		path.add("$.credentialSubject.age");
		field1.setPath(path);
		//Filter filtro=new Filter("string");
		//filtro.setPattern("Jefe");
		//field1.setFilter(filtro);
		Filter filtro=new Filter("number");
		filtro.setMin(18);
		filtro.setMax(100);
		field1.setFilter(filtro);
		Constraint constraints=new Constraint();
		List<Field> fields=new ArrayList<>();
		fields.add(field1);
		constraints.setFields(fields);
		politica.setConstraints(constraints);
		
		Resource r=new Resource(1, "/temperatura");
		//pap.nuevaPolitica("did:serviceProvider:1", politica, r);
		ArrayList<Policy> politicas=new ArrayList<>();
		politicas.add(politica);
		HashMap<Resource,ArrayList<Policy>> rec=new HashMap<>();
		rec.put(r,politicas);
		policies.put("natalia", rec);
	}
	
	public ArrayList<Policy> getPolicy(String didSP, String recursoSolicitado) {
		//Politicas devueltas
		ArrayList<Policy> politicas=new ArrayList<>();
		HashMap<Resource,ArrayList<Policy>> map=new HashMap<Resource,ArrayList<Policy>>();
		map=policies.get(didSP);
		
		for(Resource r : map.keySet()) {
			if(r.getNombre().equals(recursoSolicitado)) {
				politicas=map.get(r);
			}
		}
		return politicas;
	}
	

	public void newPolicy(String didSP, Policy politica, Resource recurso) {
		if(policies.isEmpty()) {
			ArrayList<Policy> politicas=new ArrayList<>();
			politicas.add(politica);
			HashMap<Resource,ArrayList<Policy>> rec=new HashMap<>();
			rec.put(recurso,politicas);
			policies.put(didSP, rec);
		}else {
			if(policies.containsKey(didSP)) {
				if(policies.get(didSP).containsKey(recurso)) {
					policies.get(didSP).get(recurso).add(politica);
				}else {
					ArrayList<Policy> politicas=new ArrayList<>();
					politicas.add(politica);
					policies.get(didSP).put(recurso, politicas);
				}
			}else {
				ArrayList<Policy> politicas=new ArrayList<>();
				politicas.add(politica);
				HashMap<Resource,ArrayList<Policy>> recursoPolitica=new HashMap<>();
				recursoPolitica.put(recurso,politicas);
				policies.put(didSP, recursoPolitica);
			}
		}
	}
}
