package com.example.demo.PAP;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.example.demo.models.Constraint;
import com.example.demo.models.Field;
import com.example.demo.models.Filter;
import com.example.demo.models.Policy;
import com.example.demo.models.Resource;
import com.example.demo.models.SimpleAccessRight;


public class PolicyStore {

	/* ASOCIA didSP CON RECURSO Y POL�TICAS */
	public  HashMap<Resource,ArrayList<Policy>> policies;
	public ArrayList<Resource> resources;
	
	int resourceCounter=0;
	int policyCounter=0;
	
	public PolicyStore() {
		this.policies=new HashMap<Resource,ArrayList<Policy>>();
		this.resources=new ArrayList<Resource>();
		
		// Policy for doing POST /resource/temperature
				policyCounter++;
				Policy policy2=new Policy(policyCounter);
				policy2.setName("Tango User Information");
				policy2.setPurpose("Reveal id and last name of the user.");
				policy2.setServiceProvider("did:serviceProvider:1");
				SimpleAccessRight sar1=new SimpleAccessRight("POST", "https://api-server.testing1.k8s-cluster.tango.rid-intrasoft.eu/resource/temperature");
				List<SimpleAccessRight> accessRights1=new ArrayList<>();
				accessRights1.add(sar1);
				policy2.setAccessRights(accessRights1);
				policy2.setAuthTime(123123123);
				policy2.setMinTrustScore(0.5);
				
				// Field for revealing id
				Field field5=new Field();
				List<String> path5=new ArrayList<String>();
				path5.add("$.id");
				field5.setPath(path5);
				
				// Field for matching last name with "IPS"
				Field field6=new Field();
				List<String> path6=new ArrayList<String>();
				path6.add("$.lastName");
				field6.setPath(path6);
				Filter lastNameFilter=new Filter("string");
				lastNameFilter.setPattern("IPS");
				field6.setFilter(lastNameFilter);

				Constraint constraints1=new Constraint();
				
				List<Field> fields1=new ArrayList<>();
				fields1.add(field5);
				fields1.add(field6);
				
				constraints1.setFields(fields1);
			
				policy2.setConstraints(constraints1);
				
				
				ArrayList<Policy> pols1=new ArrayList<>();
				pols1.add(policy2);
				resourceCounter++;
				Resource r=new Resource(resourceCounter, "https://api-server.testing1.k8s-cluster.tango.rid-intrasoft.eu/resource/temperature");
				resources.add(r);
				policies.put(r,pols1);
				

				// Policy for doing GET /resource/temperature
						policyCounter++;
						Policy policy3=new Policy(policyCounter);
						policy3.setName("Tango User Information");
						policy3.setPurpose("Reveal id of the user.");
						policy3.setServiceProvider("did:serviceProvider:1");
						SimpleAccessRight sar2=new SimpleAccessRight("GET", "https://api-server.testing1.k8s-cluster.tango.rid-intrasoft.eu/resource/temperature");
						List<SimpleAccessRight> accessRights2=new ArrayList<>();
						accessRights2.add(sar2);
						policy3.setAccessRights(accessRights2);
						policy3.setAuthTime(123123123);
						policy3.setMinTrustScore(0.5);
						policy3.setMinBehaviouralScore(0.5);
						// Field for revealing id
						Field field7=new Field();
						List<String> path7=new ArrayList<String>();
						path7.add("$.id");
						field7.setPath(path7);

						Constraint constraints2=new Constraint();
						
						List<Field> fields2=new ArrayList<>();
						fields2.add(field7);
						
						constraints2.setFields(fields2);
					
						policy3.setConstraints(constraints2);
						//Resource rt=new Resource(resourceCounter, "https://api-server.testing1.k8s-cluster.tango.rid-intrasoft.eu/resource/temperature");
				//resources.add(rt);
				//ArrayList<Policy> polst=new ArrayList<>();
						pols1.add(policy3);
						policies.put(r,pols1);
						
/*
						// Policy for doing GET /resource/humidity
								policyCounter++;
								Policy policy4=new Policy(policyCounter);
								policy4.setName("Tango User Information");
								policy4.setPurpose("Reveal role GOLD_COSTUMER of the user.");
								policy4.setServiceProvider("did:serviceProvider:1");
								SimpleAccessRight sar3=new SimpleAccessRight("GET", "https://api-server.testing1.k8s-cluster.tango.rid-intrasoft.eu/resource/humidity");
								List<SimpleAccessRight> accessRights3=new ArrayList<>();
								accessRights3.add(sar3);
								policy4.setAccessRights(accessRights3);
								policy4.setAuthTime(123123123);
								policy4.setMinTrustScore(0.5);
								
								// Field for revealing id
								Field field8=new Field();
								List<String> path8=new ArrayList<String>();
								path8.add("$.roles.names");
								field8.setPath(path8);
								Filter roleFilter=new Filter("string");
								roleFilter.setPattern("GOLD_COSTUMER");
								field8.setFilter(roleFilter);

								Constraint constraints3=new Constraint();
								
								List<Field> fields3=new ArrayList<>();
								fields3.add(field8);
								
								constraints3.setFields(fields3);
							
								policy4.setConstraints(constraints3);
								
								//Policies for humidity
								resourceCounter++;
								Resource r1=new Resource(resourceCounter, "https://api-server.testing1.k8s-cluster.tango.rid-intrasoft.eu/resource/humidity");
								resources.add(r1);
								ArrayList<Policy> pols2=new ArrayList<>();
								pols2.add(policy4);
								policies.put(r1,pols2);*/
							
								// Policy for doing GET /resource/pressure
								policyCounter++;
								Policy policy5=new Policy(policyCounter);
								policy5.setName("Tango User Information");
								policy5.setPurpose("Reveal id of the user.");
								policy5.setServiceProvider("did:serviceProvider:1");
								SimpleAccessRight sar4=new SimpleAccessRight("GET", "https://api-server.testing1.k8s-cluster.tango.rid-intrasoft.eu/resource/pressure");
								List<SimpleAccessRight> accessRights4=new ArrayList<>();
								accessRights4.add(sar4);
								policy5.setAccessRights(accessRights4);
								policy5.setAuthTime(123123123);
								//policy5.setMinBehaviouralScore(0.5);
								
								// Field for revealing id
								Field field9=new Field();
								List<String> path9=new ArrayList<String>();
								path9.add("$.id");
								field9.setPath(path9);

								Constraint constraints4=new Constraint();
								
								List<Field> fields4=new ArrayList<>();
								fields4.add(field9);
								
								constraints4.setFields(fields4);
							
								policy5.setConstraints(constraints4);
								
								//Policies for pressure
								resourceCounter++;
								Resource r2=new Resource(resourceCounter, "https://api-server.testing1.k8s-cluster.tango.rid-intrasoft.eu/resource/pressure");
								resources.add(r2);
								ArrayList<Policy> pols3=new ArrayList<>();
								pols3.add(policy5);
								policies.put(r2,pols3);
								
						System.out.println("DEFAULT TESTING POLICIES: ");
						// Recorremos el Map<String, ArrayList<Policy>>
						for (Map.Entry<Resource, ArrayList<Policy>> outerEntry : policies.entrySet()) {
						    Resource resource = outerEntry.getKey(); // Muestra el recurso (la clave del mapa)
						    ArrayList<Policy> policyList = outerEntry.getValue(); // Obtiene la lista de políticas asociada al recurso

						    System.out.println("Resource: " + resource.getNombre()); // Muestra el nombre del recurso

						    // Recorremos la lista de políticas
						    for (Policy policy : policyList) {
						        System.out.println("  Policy: " + policy); // Muestra la descripción de la política
						    }
						}

	}
	
	public ArrayList<Policy> getPolicy(String didSP, String recursoSolicitado, String action) {
		// Policies for the resource
		ArrayList<Policy> politicas=new ArrayList<>();
		//Returned  policies for performing the action
		ArrayList<Policy> politicasOK=new ArrayList<>();
		
		for(Resource r : resources) {
			if(r.getNombre().equals(recursoSolicitado)) { politicas=policies.get(r); }
		}
		for(Policy p : politicas) {
			for(SimpleAccessRight sar :	p.getAccessRights()) {
				if(sar.getAction().equals(action)) {
					System.out.println("Retrieving policy for performing an action "+ action + " in the resource: "+ recursoSolicitado + "\n");
					politicasOK.add(p);
				}
			}
	}
		return politicasOK;
	}
	
	
	public void addPolicy(Policy policy, String resource) {
		boolean resourceFound=false;
		//There are no policies in the system, add the first one
		if(policies.isEmpty()) {
			resourceCounter++;
			Resource r=new Resource(resourceCounter, resource);
			resources.add(r);
			ArrayList<Policy> politicas=new ArrayList<>();
			politicas.add(policy);
			policies.put(r,politicas);
		}else {
		//Searchs if the resource already has policies associated
		     for(Resource r : policies.keySet()){
		     //The resource have policies, so the new policy is added in its list
		     	if(r.getNombre()==resource){
		     		resourceFound=true;
		     		ArrayList<Policy> pols=new ArrayList<>();
		     		pols=policies.get(r);
		     		pols.add(policy);
		     		policies.put(r,pols);
		     	}
		     }
		     //The resource doesn't have policies associated in the system
		     if(resourceFound==false){
		     	resourceCounter++;
			Resource r=new Resource(resourceCounter, resource);
			resources.add(r);
			ArrayList<Policy> politicas=new ArrayList<>();
			politicas.add(policy);
			policies.put(r,politicas);
		     }
		}
		System.out.println("LIST OF POLICIES HAS BEEN UPDATED: ");
		// Recorremos el Map<String, ArrayList<Policy>>
		for (Map.Entry<Resource, ArrayList<Policy>> outerEntry : policies.entrySet()) {
		    Resource resource1 = outerEntry.getKey(); 
		    ArrayList<Policy> policyList = outerEntry.getValue(); 

		    System.out.println("Resource: " + resource1.getNombre()); 

		    // Recorremos la lista de políticas
		    for (Policy policy1 : policyList) {
		        System.out.println("  Policy: " + policy1); 
		    }
		}

	}	

	public String printPolicies() {
		String response = "Policies stored in the Policy Administration Point: \n";
		for (Map.Entry<Resource, ArrayList<Policy>> outerEntry : policies.entrySet()) {
		    Resource resource1 = outerEntry.getKey(); 
		    ArrayList<Policy> policyList = outerEntry.getValue(); 

		    response=response+"Resource: " + resource1.getNombre() + "\n" + "Associated policies: \n"; 

		    // Recorremos la lista de políticas
		    for (Policy policy1 : policyList) {
		    	  response=response + "\n Policy: " + policy1 + "\n"; 
		    	  response=response+" for performing actions: ";
		        for (SimpleAccessRight sar : policy1.getAccessRights()) {
		        	  response=response+sar.getAction();
		        }
		        response=response+"\n";
		    }
		}
		return response;
	}
	
	public void deletePolicy(String policyID) {
	    for (Resource r : policies.keySet()) {
	        ArrayList<Policy> pols = policies.get(r);
	        Iterator<Policy> it = pols.iterator();

	        while (it.hasNext()) {
	            Policy p = it.next();
	            if (p.getId().equals(policyID)) {
	                it.remove(); 
	            }
	        }

	        policies.replace(r, pols);
	    }

	    System.out.println("LIST OF POLICIES HAS BEEN UPDATED:");
	    for (Map.Entry<Resource, ArrayList<Policy>> outerEntry : policies.entrySet()) {
	        Resource resource1 = outerEntry.getKey(); 
	        ArrayList<Policy> policyList = outerEntry.getValue(); 

	        System.out.println("Resource: " + resource1.getNombre()); 
	        for (Policy policy1 : policyList) {
	            System.out.println("  Policy: " + policy1); 
	        }
	    }
	}

	
	public int getPolicyCounter() {
		return policyCounter;
	}
	public void setPolicyCounter(int policyCounter) {
		this.policyCounter=policyCounter;
	}

	/*
	public Resource createResource(String resource) {
		for(HashMap<Resource,ArrayList<Policy>> map : policies.values()) {
			for(Resource r : map.keySet()) {
				if(r.getNombre().equals(resource)) {
					Resource r1=new Resource(r.getId(), resource);
					return r1;
				}else {
					resourceCounter++;
					Resource r1=new Resource(resourceCounter, resource);
					return r1;
				}
			}
		}
		return null;
	}*/
	
	/*
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
	}*/
}
