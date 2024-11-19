package com.example.demo.PEP;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;

import java.util.Base64;

import com.example.demo.PDP.PDP;
import com.example.demo.models.AccessRequest;
import com.example.demo.models.AuthRequest;
import com.example.demo.models.CapabilityToken;
import com.example.demo.models.Resource;
import com.example.demo.models.SimpleAccessRight;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.nimbusds.jose.shaded.json.JSONObject;

public class PEP implements PEPInterface {

	Gson gson = new Gson();
	JsonNode schemaToken;
	JsonNode schemaRequest;
	JsonNode schemaAccess;
	JsonNode request;
	PublicKey pbk;

	String jwtString;

	public String getJwtString() {
		return jwtString;
	}

	public void setJwtString(String jwtString) {
		this.jwtString = jwtString;
	}

	PDP pdp;
	ArrayList<Resource> resources;

	@Value("${app.RESOURCES:/app/resources}")
	String serverResources;

	/* CONSTRUCTOR */

	public PEP(PDP pdp) {
		serverResources = System.getenv("RESOURCES");
		if (System.getenv("RESOURCES") == null) {
			serverResources = "resources";
		}
		this.pdp = pdp;
		gson = new Gson();
	}

	/* METHODS */

	// Sends request to PDP for verifying requester's ID, matching policies and
	// trust score
	public CapabilityToken sendRequest(String authRequestJson) {
		CapabilityToken ct = pdp.verifyIdErat(authRequestJson);
		pbk = pdp.getPbk();
		return ct;
	}

	// Sends request to PDP for verifying requester's ID, matching policies and
	// trust score
	public CapabilityToken sendConnectorToken(String authRequestJson) {
		System.out.println("\nPEP recieves Requester's query for accessing an asset.\n");
		pdp.setJwtString(jwtString);
		CapabilityToken ct = pdp.verifyConnectorToken(authRequestJson);
		pbk = pdp.getPbk();
		System.out.println("PEP send the Capability Token to the Requester.\n");
		return ct;
	}

	public static Map<String, String> jsonToMap(Map<String, Object> jsonObject) {
		Map<String, String> queryParams = new HashMap<>();

		for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue().toString();
			queryParams.put(key, value);
		}

		return queryParams;
	}

	public static String buildUrlWithQueryParameters(String baseUrl, Map<String, String> queryParams) {
		StringBuilder urlWithParams = new StringBuilder(baseUrl);
		if (!queryParams.isEmpty()) {
			urlWithParams.append("?");
			for (Map.Entry<String, String> param : queryParams.entrySet()) {
				urlWithParams.append(param.getKey()).append("=").append(param.getValue()).append("&");
			}
			urlWithParams.setLength(urlWithParams.length() - 1);
		}
		return urlWithParams.toString();
	}

	// Validates the Capability Token according to the request's data, its
	// expiration and signature
	@Override
	public String validateCapabilityToken(String requestWithToken) {
		System.out.println("PEP validating the Capability Token...\n");
		boolean notExpired = false;
		boolean signatureVerified = false;
		boolean simpleAccessRightsOk = false;

		// Verifies the access rights that the Capability Token grants
		AccessRequest acc = gson.fromJson(requestWithToken, AccessRequest.class);
		List<SimpleAccessRight> simp = acc.getCt().getAr();

		for (SimpleAccessRight s : simp) {
			if (s.getAction().equals(acc.getSar().getAction()) && s.getResource().equals(acc.getSar().getResource())) {
				simpleAccessRightsOk = true;
				System.out.println("This Capability Token allows the Requester to do the desired action.\n");
			} else {
				simpleAccessRightsOk = false;
				System.out.println("This Capability Token doesn't allow the Requester to do the desired action.\n");
			}
		}
		// Verifies that the Capability Token is not expired
		Date nb = new Date(acc.getCt().getNb());
		Date na = new Date(acc.getCt().getNa());
		Date today = new Date();
		if (today.before(na) && today.after(nb)) {
			notExpired = true;
		} else {
			notExpired = false;
		}
		System.out.println("This Capability Token is not expired.\n");
		// Verify the token's signature
		// Decode from Base64
		byte[] signatureBytes = Base64.getDecoder().decode(acc.getCt().getSi());

		// Obtain a Signature's instance and configure for verifying with the Public Key
		Signature signature = null;
		try {
			signature = Signature.getInstance("SHA256withRSA");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Make a request to the Verifier to get the JWKS
		pbk = pdp.getPbk();
		try {
			signature.initVerify(pbk);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// The signed message is every token's field except signature
		String signature1 = null;

		// Concatenate fields
		signature1 = acc.getCt().getId() + acc.getCt().getIi() + acc.getCt().getIs() + acc.getCt().getSu()
				+ acc.getCt().getDe() + acc.getCt().getAr().get(0).getAction()
				+ acc.getCt().getAr().get(0).getResource() + acc.getCt().getNb() + acc.getCt().getNa();

		byte[] messageBytes = signature1.getBytes(StandardCharsets.UTF_8);

		// Update signature
		try {
			signature.update(messageBytes);
		} catch (SignatureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Verify signature
		boolean isVerified = false;
		try {
			isVerified = signature.verify(signatureBytes);
		} catch (SignatureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (isVerified) {
			signatureVerified = true;
		} else {
			signatureVerified = false;
		}
		System.out.println("The signature of this Capability Token is correct.\n");

		// If everything matches, access to the resource is granted
		if (notExpired && signatureVerified && simpleAccessRightsOk) {
			// Make an API call to bring the requested resource

			// TODO: change it and set as an env variable
			String url_string;
			StringBuilder response = new StringBuilder();
			boolean found = true;

			try {

				// Request does not contain query parameters
				if (acc.getQueryParameters() == null) {
					url_string = "http://api-server.testing1.k8s-cluster.tango.rid-intrasoft.eu/" + acc.getCt().getAr().get(0).getResource();
					URL url = new URL(url_string);
					HttpURLConnection connection = (HttpURLConnection) url.openConnection();
					connection.setRequestMethod(acc.getCt().getAr().get(0).getAction());
					int responseCode = connection.getResponseCode();
					if (responseCode == HttpURLConnection.HTTP_OK) {
						BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
						String inputLine;
						// Read requested resource from API response 
						while ((inputLine = in.readLine()) != null) {
							response.append(inputLine);
						}
						in.close();
					} else {
						found = false;
						System.out.println("Request not worked. Response code: " + responseCode);
					}
					
				} else { //Request contains query parameters
					
					//POST request 
					if (acc.getCt().getAr().get(0).getAction().equals("POST")) {
						//url_string = "http://localhost:5000/resource";
						url_string = "http://api-server.testing1.k8s-cluster.tango.rid-intrasoft.eu/resource";
						//url_string = "http://api-server.testing1.k8s-cluster.tango.rid-intrasoft.eu/" + acc.getCt().getAr().get(0).getResource();
						HttpURLConnection connection = (HttpURLConnection) new URL(url_string).openConnection();
						connection.setRequestMethod("POST");
						
						//Attach query parameters as a JSON body
						connection.setRequestProperty("Content-Type", "application/json");
						connection.setDoOutput(true); 

						JSONObject queryParameters = acc.getQueryParameters();
						String jsonRequestBody = queryParameters.toJSONString();

						try (OutputStream os = connection.getOutputStream()) {
							byte[] input = jsonRequestBody.getBytes("utf-8");
							os.write(input, 0, input.length);
						}
						
						//201 CREATED Response
						int responseCode = connection.getResponseCode();
						if (responseCode == HttpURLConnection.HTTP_CREATED) {
							BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
							String inputLine;
							while ((inputLine = in.readLine()) != null) {
								response.append(inputLine);
							}
							in.close();
						} else {
							found = false;
							System.out.println("Request not worked. Response code: " + responseCode);
						}

					} else { //GET request 
						Map<String, String> queryParams = jsonToMap(acc.getQueryParameters());
						url_string = "http://api-server.testing1.k8s-cluster.tango.rid-intrasoft.eu/" + acc.getCt().getAr().get(0).getResource();
						//Generate URL including the query parameters
						url_string = buildUrlWithQueryParameters(url_string, queryParams);
						
						HttpURLConnection connection = (HttpURLConnection) new URL(url_string).openConnection();
						connection.setRequestMethod(acc.getCt().getAr().get(0).getAction());

						int responseCode = connection.getResponseCode();
						if (responseCode == HttpURLConnection.HTTP_OK) {
							BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
							String inputLine;
							while ((inputLine = in.readLine()) != null) {
								response.append(inputLine);
							}
							in.close();
						} else {
							found = false;
							System.out.println(responseCode);
							System.out.println("Request not worked. Response code: " + responseCode);
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if (found) {
				System.out.println(
						"SUCCESS: Capability Token has been successfully validated.The requester could access to the resource.\n"
								+ response.toString() + "\n");
				return "SUCCESS: Capability Token has been successfully validated. The requester could access to the resource.\n"
						+ response.toString() + "\n";
			}
		} else {
			System.out.println(
					"ERROR: Validation failed. Capability Token is not valid. The requester couldn't access to the resource.\n");
		}
		return "ERROR: Validation failed. Capability Token is not valid. The requester couldn't access to the resource.\n";
	}

}
