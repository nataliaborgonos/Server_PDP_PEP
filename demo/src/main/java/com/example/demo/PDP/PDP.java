package com.example.demo.PDP;


import java.io.IOException;
import java.lang.reflect.Array;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Base64;

import com.example.demo.PEP.PEP;
import com.example.demo.PIP.PIP;
import com.example.demo.PIP.PolicyStore;
import com.example.demo.PIP.TrustScoreStore;
import com.example.demo.idAgent.IdentityAgent;
import com.example.demo.models.AuthRequest;
import com.example.demo.models.CapabilityToken;
import com.example.demo.models.Constraint;
import com.example.demo.models.Field;
import com.example.demo.models.Policy;
import com.example.demo.models.Proof;
import com.example.demo.models.VCredential;
import com.example.demo.models.VPresentation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


import org.apache.commons.*;
import org.apache.commons.lang.StringEscapeUtils;

	public class PDP implements PDPInterface{
		
		/* KEYS */
		private static final String KEYSTORE = "/home/natalia/Descargas/serverErat.ks";
		private static final char[] KEYSTOREPWD = "hola123".toCharArray();
	    private static final String ALIAS="MiAliasPriv";
	    
	    TrustScoreStore trustScores=new TrustScoreStore();
	    PolicyStore policies=new PolicyStore();
		PIP pip;
		PEP pep;
		IdentityAgent idAgent=new IdentityAgent();
		
		Gson gson=new Gson();
		JsonNode schemaRequest;
		JsonNode schemaPolitica;
		JsonNode request;
		PublicKey pbk;
		
		/* CONSTRUCTOR */
		
		//Check this to add a wallet parameter
		public PDP(PIP pip) {
			try {
				schemaRequest=JsonLoader.fromPath("/home/natalia/git/repository/demo/src/main/java/com/example/demo/models/JSONSchemaRequest.json");
				schemaPolitica=JsonLoader.fromPath("/home/natalia/git/repository/demo/src/main/java/com/example/demo/models/JSONSchemaPol.json");
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			gson=new Gson();
			this.pip=pip;
		}
		
		/* METHODS */
		
		// Method for formatting JSON
		private String removeQuotesAndUnescape(String uncleanJson) { 
		    String noQuotes = uncleanJson.replaceAll("^\"|\"$", "");

		    return StringEscapeUtils.unescapeJava(noQuotes);
		}
		
		// Method for verifying the authorization request and issuing the Capability Token
		public CapabilityToken verifyId(String authRequestJson) {
			AuthRequest ar = null;
			String VP_string=null;
			VPresentation vp=null;
			CapabilityToken ct=null;
			JsonSchemaFactory factory=JsonSchemaFactory.byDefault();
			try {
				JsonSchema schemaReq=factory.getJsonSchema(schemaRequest);
				try {
					request=JsonLoader.fromString(authRequestJson);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				schemaReq.validate(request);
				
				String goodJson=removeQuotesAndUnescape(authRequestJson);
			
				ar=gson.fromJson(goodJson, AuthRequest.class);
				
		} catch (ProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
			//Get policies needed to do the requested action in that resource
			ArrayList<Policy> politicas=pip.getPolicies(ar.getDidSP(),ar.getSar().getResource());
			
			//Get trust score associated with the requester
			double trustScore=pip.getTrustScore(ar.getDidRequester());
			boolean trustScoreOK;
			for(Policy p : politicas) {
				if(trustScore>p.getMinTrustScore()) {
					trustScoreOK=true;
				}
			}
		    
			//TODO: CAMBIAR EL FORMATO DE PRESENTACIÓN -> preguntar lo de los access rights
			//VPresentation VP=ar.getVerifiablePresentation();
			   //Call API for verifying Verifiable Presentation
		      boolean allMatches = true;
				
		    //Call API for verify the VPresentation
			idAgent.createWallet("natalia");
			boolean response=idAgent.verifyPresentation("param");
			if(!response) {
				allMatches=false;
			}
			
		    //Prove matching policies with requester's VP
			String vp_json=gson.toJson(ar.getVerifiablePresentation());
		   JsonObject vpresentation = gson.fromJson(vp_json, JsonObject.class);

			JsonObject obj=vpresentation;
			
			List<VCredential> listavc=ar.getVerifiablePresentation().getVerifiableCredential();
			List<JsonObject> listajsn=new ArrayList<>();
			for(VCredential v : listavc) {
				String vjson=gson.toJson(v);
				JsonObject j=gson.fromJson(vjson, JsonObject.class);
				
				listajsn.add(j);
			}
				
			
			 // Verificar si todos los elementos del camino (path) coinciden con los campos del objeto CredentialSubject
	        boolean allFieldsMatch = true;
	      
	    	long inicioTiempoPoliticas=System.currentTimeMillis();
	      //true x defecto-> si alguna no se cumple se acaba
	      		//ir recorriendo los bucles
	  	for(Policy p : politicas) {
				//Comprobar si la politica es bien formada
				String politicaJSON= gson.toJson(p);	
			
				JsonSchemaFactory factory1=JsonSchemaFactory.byDefault();
				try {
					JsonSchema schemaReq=factory1.getJsonSchema(schemaRequest);
					try {
						request=JsonLoader.fromString(authRequestJson);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					schemaReq.validate(request);
					
				} catch (ProcessingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				//Matching
				
				
				Constraint constraints=p.getConstraints();
				List<Field> fields=constraints.getFields();
					for(Field f : fields) {
						List<String> path=f.getPath();
						//buscar jerarquia $.credentialSubject.
						
						JsonObject objGlobal=new JsonObject();
						
						if(f.getFilter()==null) {
							for(String i : path){
							
								String[] partes = i.split("\\.");

								// Recorremos la lista de la jerarqu�a
								for (String parte : partes) {
									String parte2=new String(parte);
									for(JsonObject obj1 : listajsn) {
										JsonObject currentObj=obj1;
									// Se comprueba que ese campo est�, y si est� se coge su valor
									if(!parte.equals("$")) {
										
									if(obj1.has(parte)) {
										
										String parte1=new String(parte);
										JsonElement e=obj1.get(parte1);
								
										if (e.isJsonObject()) {
							                   objGlobal = e.getAsJsonObject();
							                   
							            }
									}else if(objGlobal.has(parte2)) {
								
										JsonElement valor = objGlobal.get(parte2);
										
									}else {allMatches=false;}
									}
								
									}
								}
								}
								
								
							
							}
						if(f.getFilter()!=null) {
							for(String i : path){
							if(f.getFilter().getType().equals("string")) {
								String patron=f.getFilter().getPattern();
								String patron1="\""+patron+"\"";
								for(String j : path){
								
									String[] partes1 = i.split("\\.");

									// Recorremos la lista de la jerarqu�a
									for (String parte1 : partes1) {
										String parte2=new String(parte1);
										for(JsonObject obj1 : listajsn) {
											JsonObject currentObj=obj1;
										// Se comprueba que ese campo est�, y si est� se coge su valor
										if(!parte1.equals("$")) {
										
										if(obj1.has(parte1)) {
											
											String parte11=new String(parte1);
											JsonElement e=obj1.get(parte11);
											
											if (e.isJsonObject()) {
								                   objGlobal = e.getAsJsonObject();
								                   
								            }
										}else if(objGlobal.has(parte2)) {
											 JsonElement valor = objGlobal.get(parte2);
											String valorS= valor.toString();
											
											if(patron1.equals(valorS)) {
												
											}else {allMatches=false;}
										}else {allMatches=false;}
										}
										}
									}
								}
							}else if(f.getFilter().getType().equals("number")) {
								Number minimo=f.getFilter().getMin();
								Number maximo=f.getFilter().getMax();
								for(String j : path){
									
									String[] partes1 = i.split("\\.");

									// Recorremos la lista de la jerarqu�a
									for (String parte1 : partes1) {
										String parte2=new String(parte1);
										for(JsonObject obj1 : listajsn) {
											JsonObject currentObj=obj1;
										// Se comprueba que ese campo est�, y si est� se coge su valor
										if(!parte1.equals("$")) {
											
										if(obj1.has(parte1)) {
											
											String parte11=new String(parte1);
											JsonElement e=obj1.get(parte11);
											
											if (e.isJsonObject()) {
								                   objGlobal = e.getAsJsonObject();
								                   
								            }
										}else if(objGlobal.has(parte2)) {
											JsonElement valor = objGlobal.get(parte2);
											Number valorS= valor.getAsNumber();
											
											if((valorS.intValue()<minimo.intValue()) || (valorS.intValue()>maximo.intValue())) {
												allMatches=false;
											}
										}else {allMatches=false;}
										}
										}
									}
								}
							}
							}
						}
						
					}
				}
			
			long finTiempoPoliticas=System.currentTimeMillis();
			long difTiempoPoliticas=finTiempoPoliticas-inicioTiempoPoliticas;
			double tiempoPoliticasSeg=difTiempoPoliticas/1000.0;
			//Si todo ok, se genera el token
		
			
			if(allMatches==true) {
					//ct=new CapabilityToken(KEYSTORE, KEYSTOREPWD, ALIAS);
					 ct=new CapabilityToken(KEYSTORE,KEYSTOREPWD,ALIAS,ar.getDidRequester(),ar.getDidSP(),ar.getSar());
					
					
				//	pbk=ct.getPublicKey();
					pbk=ct.getPublicKey();
			}
			return ct;
		}
		
		public PublicKey getPbk() {
			return pbk;
		}

	}


