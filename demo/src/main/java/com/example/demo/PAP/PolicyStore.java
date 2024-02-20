package com.example.demo.PAP;

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

		// Create a policy for trying the Server
		Policy policy=new Policy(1);
		policy.setNombre("Student Information");
		policy.setPurpose("Reveal id and name of the BachelorDegree's student in MIT.");
		policy.setServiceProvider("did:serviceProvider:1");
		SimpleAccessRight sar=new SimpleAccessRight("GET", "/temperatura");
		List<SimpleAccessRight> accessRights=new ArrayList<>();
		accessRights.add(sar);
		policy.setAccessRights(accessRights);
		policy.setAuthTime(123123123);
		policy.setMinTrustScore(0.5);
		
		// Field for revealing id
		Field field1=new Field();
		List<String> path=new ArrayList<String>();
		path.add("$.id");
		field1.setPath(path);
		
		// Field for revealing name
		Field field2=new Field();
		List<String> path2=new ArrayList<String>();
		path2.add("$.name");
		field2.setPath(path2);
		
		// Field for matching degree type with "BachelorDegree"
		Field field3=new Field();
		List<String> path3=new ArrayList<String>();
		path3.add("$.degree.type");
		field3.setPath(path3);
		Filter degreeTypeFilter=new Filter("string");
		degreeTypeFilter.setPattern("BachelorDegree");
		field3.setFilter(degreeTypeFilter);
	
		// Field  for matching degree university with "MIT"
		Field field4=new Field();
		List<String> path4=new ArrayList<String>();
		path4.add("$.degree.university");
		field4.setPath(path4);
		Filter degreeUniversityFilter=new Filter("string");
		degreeUniversityFilter.setPattern("MIT");
		field4.setFilter(degreeUniversityFilter);
		
		Constraint constraints=new Constraint();
		
		List<Field> fields=new ArrayList<>();
		fields.add(field1);
		fields.add(field2);
		fields.add(field3);
		fields.add(field4);
		constraints.setFields(fields);
	
		policy.setConstraints(constraints);
		
		Resource r=new Resource(1, "/temperatura");
		
		ArrayList<Policy> pols=new ArrayList<>();
		pols.add(policy);
		HashMap<Resource,ArrayList<Policy>> rec=new HashMap<>();
		rec.put(r,pols);
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
