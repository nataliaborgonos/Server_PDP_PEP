package com.example.demo.PDP;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Base64;

import com.example.demo.PAP.PAPErat;
import com.example.demo.PAP.PAPTest;
import com.example.demo.PAP.PolicyStore;
import com.example.demo.PEP.PEP;
import com.example.demo.PIP.PIPErat;
import com.example.demo.PIP.PIPTest;
import com.example.demo.PIP.TrustScoreStore;
import com.example.demo.idAgent.IdentityAgent;
import com.example.demo.models.AuthRequest;
import com.example.demo.models.CapabilityToken;
import com.example.demo.models.Constraint;
import com.example.demo.models.CredentialSubject;
import com.example.demo.models.Degree;
import com.example.demo.models.Field;
import com.example.demo.models.Issuer;
import com.example.demo.models.Policy;
import com.example.demo.models.Proof;
import com.example.demo.models.VCredential;
import com.example.demo.models.VPresentation;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.core.JsonParseException;
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
import com.google.gson.reflect.TypeToken;

import javax.json.*;

import com.github.jsonldjava.core.JsonLdError;
import com.github.jsonldjava.core.JsonLdProcessor;
import com.github.jsonldjava.utils.JsonUtils;

import org.apache.commons.*;
import org.apache.commons.lang.StringEscapeUtils;
import org.mozilla.javascript.tools.shell.JSConsole;

public class PDP implements PDPInterface {

	/* KEYS */
	private static final String KEYSTORE = "/home/natalia/Descargas/serverErat.ks";
	private static final char[] KEYSTOREPWD = "hola123".toCharArray();
	private static final String ALIAS = "MiAliasPriv";

	String wallet; // temporarily this is String
	PIPTest pip;
	PAPTest pap;
	PEP pep;
	IdentityAgent idAgent = new IdentityAgent();

	Gson gson = new Gson();
	JsonNode schemaRequest;
	JsonNode schemaPolitica;
	JsonNode request;
	PublicKey pbk;

	/* CONSTRUCTOR */

	// Check this to add a wallet parameter
	public PDP(PIPTest pip,PAPTest pap, String wallet) {
		try {
			schemaRequest = JsonLoader.fromPath(
					"/home/natalia/git/local_repo/demo/src/main/java/com/example/demo/models/JSONSchemaRequest.json");
			schemaPolitica = JsonLoader.fromPath(
					"/home/natalia/git/local_repo/demo/src/main/java/com/example/demo/models/JSONSchemaPol.json");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		gson = new Gson();
		this.pip = pip;
		this.pap = pap;
		this.wallet=wallet;
	}
	
	public PDP(PIPErat pip,PAPErat pap, String wallet) {
		//TODO
	}
	

	/* METHODS */
	
	//TODO Method for erathostenes configuration

	// Method for formatting JSON
	private String removeQuotesAndUnescape(String uncleanJson) {
		String noQuotes = uncleanJson.replaceAll("^\"|\"$", "");

		return StringEscapeUtils.unescapeJava(noQuotes);
	}

	// Method for verifying the authorization request and issuing the Capability
	// Token
	public CapabilityToken verifyId(String authRequestJson) {
		AuthRequest ar = null;
		String VP_string = null;
		VPresentation vp = null;
		CapabilityToken ct = null;
		JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
		try {
			JsonSchema schemaReq = factory.getJsonSchema(schemaRequest);
			try {
				request = JsonLoader.fromString(authRequestJson);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			schemaReq.validate(request);

			String goodJson = removeQuotesAndUnescape(authRequestJson);

			ar = gson.fromJson(goodJson, AuthRequest.class);

		} catch (ProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// Get policies needed to do the requested action in that resource
		ArrayList<Policy> politicas = pap.getPolicies(ar.getDidSP(), ar.getSar().getResource());

		// Get trust score associated with the requester
		double trustScore = pip.getTrustScore(ar.getDidRequester());
		boolean trustScoreOK;
		for (Policy p : politicas) {
			if (trustScore > p.getMinTrustScore()) {
				trustScoreOK = true;
			}
		}

		//Get the requester's VP
		String VP=ar.getVerifiablePresentation();
	
		//Validate if the VP is well formed -> Put the not null fields (context,holder,proof,VCredential)
		try {
			Map<String, Object> presentation = (Map<String, Object>) JsonUtils.fromString(VP);
			   Object type = presentation.get("type");
	            if (type == null || !"VerifiablePresentation".equals(type.toString())) {
	                System.out.println("Not a valid presentation.");;
	            }
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		boolean allMatches = true;

		
		//TODO: This will be changing in order to the requester's wallet. This is just for testing.
		// Call API for verify the VPresentation
		idAgent.createWallet("natalia");
		boolean response = idAgent.verifyPresentation(VP);
		if (!response) {
			allMatches = false;
		}

		// Prove matching policies with requester's VP
		
		//Deserialize JSON
		  javax.json.JsonObject jsonObject;                                                       
		  try (JsonReader reader = Json.createReader(new StringReader(VP))) {
		            jsonObject = reader.readObject();  
		  }                                                                      
		                                                                               
      VPresentation presentationData = new VPresentation(); 
      List<JsonValue> contextList = jsonObject.getJsonArray("@context");
      ArrayList<String> stringList = new ArrayList<>();
      for (JsonValue jsonValue : contextList) {
          stringList.add(jsonValue.toString());
      }
      presentationData.setContext(stringList);
      
      presentationData.setHolder(jsonObject.getString("holder"));
   
      javax.json.JsonObject proofJsonObject = jsonObject.getJsonObject("proof");
      String jsonString = proofJsonObject.toString();
      Proof proof=gson.fromJson(jsonString, Proof.class);
      presentationData.setProof(proof);
	
      presentationData.setType(jsonObject.getString("type"));
      
      List<JsonValue> vcredential=jsonObject.getJsonArray("verifiableCredential");
     List<VCredential> stringCred = new ArrayList<>();
     
		List<javax.json.JsonObject> listajsn = new ArrayList<>();
      for (JsonValue jsonValue : vcredential) {
    	 
    	  
    	  javax.json.JsonObject jsonObject1 ;
               // Verifica si el JsonValue es un objeto JSON
               if (jsonValue.getValueType() == JsonValue.ValueType.OBJECT) {
                   // Convierte el JsonValue a JsonObject
                   jsonObject1 = jsonValue.asJsonObject();
                   
                   listajsn.add(jsonObject1);
               }
    	 
      }                                                                                                           
      //presentationData.setVerifiableCredential(stringCred);         
      
      //for(VCredential v : presentationData.getVerifiableCredential()) {
    	//  System.out.println(v.toString());
      //}
      
    // System.out.println(presentationData.getVerifiableCredential());
      
	

		
		// Verify if every path's element match the object CredentialSubject's fields 
		boolean allFieldsMatch = true;

		// True default, if there is a mismatch, finish the loop 
		for (Policy p : politicas) {
			// Find out if the policy is correctly formed 
			String politicaJSON = gson.toJson(p);

			JsonSchemaFactory factory1 = JsonSchemaFactory.byDefault();
			try {
				JsonSchema schemaReq = factory1.getJsonSchema(schemaRequest);
				try {
					request = JsonLoader.fromString(authRequestJson);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				schemaReq.validate(request);

			} catch (ProcessingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			// Matching

			Constraint constraints = p.getConstraints();
			List<Field> fields = constraints.getFields();
			
			System.out.println("Starting matching policies process...");
			for (Field f : fields) {
				
				List<String> path = f.getPath();
				// Look for hierarchy ( $.credentialSubject. )

				javax.json.JsonObject objGlobal = null;

				if (f.getFilter() == null) {
					for (String i : path) {
						String[] partes = i.split("\\.");

						// Hierarchy list 
						for (String parte : partes) {
							String parte2 = new String(parte);
							
							//Comprobar que ese path de la politica esta indicado en la VP
							
							
							for (javax.json.JsonObject obj1 : listajsn) {
								   javax.json.JsonObject credentialSubject = obj1.getJsonObject("credentialSubject");		  
								javax.json.JsonObject currentObj = obj1;
								// If the field is present, we take its value 
								if (!parte.equals("$")) {
									
									if (credentialSubject.containsKey(parte)) {
										String parte1 = new String(parte);
										JsonValue e = credentialSubject.get(parte1);
										
										if (e.getValueType() == JsonValue.ValueType.OBJECT) {
										if (e.asJsonObject() != null) {
											objGlobal = e.asJsonObject();
										}}
									} else if (objGlobal!=null && credentialSubject.containsKey(parte2) ) {

										JsonValue valor = credentialSubject.get(parte2);
									} else {
										allMatches = false;
									}
								}

							}
						}
					}

				}
				if (f.getFilter() != null) {
					for (String i : path) {
						if (f.getFilter().getType().equals("string")) {
							String patron = f.getFilter().getPattern();
							String patron1 = "\"" + patron + "\"";
							for (String j : path) {
								
								String[] partes1 = i.split("\\.");

								// Hierarchy list
								for (String parte1 : partes1) {
									String parte2 = new String(parte1);
									for (javax.json.JsonObject obj1 : listajsn) {
										 javax.json.JsonObject credentialSubject = obj1.getJsonObject("credentialSubject");
										javax.json.JsonObject currentObj = obj1;
										// If the field is present, we take its value
										if (!parte1.equals("$")) {
											if (credentialSubject.containsKey(parte1)) {
												String parte11 = new String(parte1);
												JsonValue e = credentialSubject.get(parte11);
												if (e.getValueType() == JsonValue.ValueType.OBJECT) {
							
												if (e.asJsonObject() != null) {
													objGlobal = e.asJsonObject();
												}}
												
											} else if (objGlobal!=null && objGlobal.containsKey(parte2)) {
												
												JsonValue valor = objGlobal.get(parte2);
												String valorS = valor.toString();
												
											
												if (patron1.equals(valorS)) {
													
													
												} else {
													allMatches = false;
												}
											} else {
												allMatches = false;
											}
										}
									}
								}
							}
						} else if (f.getFilter().getType().equals("number")) {
							Number minimo = f.getFilter().getMin();
							Number maximo = f.getFilter().getMax();
							for (String j : path) {

								String[] partes1 = i.split("\\.");

								// Hierarchy list
								for (String parte1 : partes1) {
									String parte2 = new String(parte1);
									for (javax.json.JsonObject obj1 : listajsn) {
										 javax.json.JsonObject credentialSubject = obj1.getJsonObject("credentialSubject");
										javax.json.JsonObject currentObj = obj1;
										// If the field is present, we take its value
										if (!parte1.equals("$")) {

											if (credentialSubject.containsKey(parte1)) {

												String parte11 = new String(parte1);
												JsonValue e = obj1.get(parte11);

												if (objGlobal!=null && e.asJsonObject() != null) {
													objGlobal = e.asJsonObject();

												}
											} else if (objGlobal.containsKey(parte2)) {
												JsonValue valor = objGlobal.get(parte2);
												Number valorS = null;
												if (valor instanceof Number) {
													 valorS = (Number) valor;
												}
												
												if ((valorS.intValue() < minimo.intValue())
														|| (valorS.intValue() > maximo.intValue())) {
													allMatches = false;
												}
											} else {
												allMatches = false;
											}
										}
									}
								}
							}
						}
					}
				}

			}
		}

	
		// If everything is OK, the Capability Token is issued

		if (allMatches == true) {
			System.out.println("The matching process has been successfully finished. Issuing Capability Token for requester...\n");
			ct = new CapabilityToken(KEYSTORE, KEYSTOREPWD, ALIAS, ar.getDidRequester(), ar.getDidSP(), ar.getSar());
			pbk = ct.getPublicKey();
		}else {
			System.out.println("The matching process failed...\n");
		}
		return ct;
	}

	public PublicKey getPbk() {
		return pbk;
	}

}
