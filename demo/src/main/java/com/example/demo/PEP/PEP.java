package com.example.demo.PEP;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

public class PEP implements PEPInterface {

	Gson gson = new Gson();
	JsonNode schemaToken;
	JsonNode schemaRequest;
	JsonNode schemaAccess;
	JsonNode request;
	PublicKey pbk;

	PDP pdp;
	ArrayList<Resource> resources;

	String serverResources;
	/* CONSTRUCTOR */

	public PEP(PDP pdp) {
		serverResources=System.getenv("RESOURCES");
		if(System.getenv("RESOURCES")==null) {
			serverResources="resources";
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
		CapabilityToken ct = pdp.verifyConnectorToken(authRequestJson);
		pbk = pdp.getPbk();
		System.out.println("PEP send the Capability Token to the Requester.\n");
		return ct;
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
			  // Especifica la ruta del archivo JSON en el sistema de archivos
	        String rutaArchivo = serverResources+acc.getSar().getResource();
	        
	        // StringBuilder para almacenar el contenido del archivo
	        StringBuilder contenido = new StringBuilder();
	        
	        // Lee el contenido del archivo línea por línea
	        BufferedReader br = null;
			try {
				br = new BufferedReader(new FileReader(rutaArchivo));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				System.err.println("That resource doesn't exist.");
			}
	        String linea;
	        try {
				while ((linea = br.readLine()) != null) {
				    contenido.append(linea).append("\n");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(
					"SUCCESS: Capability Token has been successfully validated.The requester could access to the resource.\n"+ contenido.toString());
			return "SUCCESS: Capability Token has been successfully validated. The requester could access to the resource.\n"+ contenido.toString();
		} else {
			System.out.println(
					"ERROR: Validation failed. Capability Token is not valid. The requester couldn't access to the resource.\n");
		}
		return "ERROR: Validation failed. Capability Token is not valid. The requester couldn't access to the resource.\n";
	}

}
