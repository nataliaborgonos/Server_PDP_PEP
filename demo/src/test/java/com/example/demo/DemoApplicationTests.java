package com.example.demo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DemoApplicationTests {
	
	String token;

	   @Autowired
	    private TestRestTemplate restTemplate;

	   @Test
	    void testRequestAccessEndpoint() {
	        // Define el cuerpo del JSON que deseas enviar
	        String requestBody = "{\n"
	        		+ "    \"didSP\": \"natalia\",\n"
	        		+ "    \"sar\": {\n"
	        		+ "        \"action\":\"GET\",\n"
	        		+ "        \"resource\":\"/temperatura\"\n"
	        		+ "    },\n"
	        		+ "    \"didRequester\": \"did\",\n"
	        		+ "    \"verifiablePresentation\":  {\"id\":\"9ac9ebb4\",\n"
	        		+ " \"type\":\"VerifiablePresentation\",\n"
	        		+ " \"issuingDate\":\"2024-01-25T16:31:10.802145808\",\n"
	        		+ " \"verifiableCredential\":[\n"
	        		+ " {\"id\":\"5746b866\",\"type\":\"VerifiableCredential\",\"issuer\":\"Erathostenes\",\"issuanceDate\":\"2024-01-25T16:31:10.641923467\",\"credentialSubject\":{\"id\":\"natalia\",\"nombre\":\"natalia\",\"apellido\":\"apellido\",\"birthDate\":\"1999-01-19\",\"age\":25},\"proof\":{\"type\":\"PsmsBlsSignature2022\",\"created\":\"2024-01-25T16:31:10.647602027\",\"verificationMethod\":\"did:fabric:98147c6a2a0e5ab92a308124b5ad7b55986b7ebfba0e5e0d0c9f2d56a93407e8:2fd02be71ab8812cf681a500dfeb3e5524f5bda99aa56a07d59d03f716226946#didkey\",\n"
	        		+ " \"proofPurpose\":\"assertionMethod\",\"jws\":\"123\"}}],\"proof\":{\"type\":\"PsmsBlsSignature2022\",\"created\":\"2024-01-25T16:31:10.803576042\",\"verificationMethod\":\"123\",\"proofPurpose\":\"hola\",\"jws\":\"123\"}}\n"
	        		+ "}";

	        // Configura las cabeceras de la solicitud
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);

	        // Crea la entidad HTTP con el cuerpo y las cabeceras
	        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

	        // Realiza la solicitud POST al endpoint request-access
	        ResponseEntity<String> responseEntity = restTemplate.exchange(
	                "/api/request-access", HttpMethod.POST, requestEntity, String.class);
	        

	        token = responseEntity.getBody();
	        
	        // Verifica el código de estado
	        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

	        // Verifica que el token recibido no sea nulo ni vacío
	        assertNotNull(responseEntity.getBody());
	        assertTrue(!responseEntity.getBody().isEmpty());
	        
	    }

	    @Test
	    void testAnotherEndpoint() {
	    	   // Define el cuerpo del JSON que deseas enviar
	        String requestBody = "{\n"
	        		+ "    \"ct\": "+token+",\n"
	        		+ "     \"sar\":{\n"
	        		+ "        \"action\":\"GET\",\n"
	        		+ "        \"resource\":\"/temperatura\"\n"
	        		+ "    }\n"
	        		+ "}";

	        // Configura las cabeceras de la solicitud
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);

	        // Crea la entidad HTTP con el cuerpo y las cabeceras
	        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

	        // Realiza la solicitud POST al endpoint request-access
	        ResponseEntity<String> responseEntity = restTemplate.exchange(
	                "/api/access-with-token", HttpMethod.POST, requestEntity, String.class);

	        // Verifica el código de estado
	        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

	        // Verifica el cuerpo de la respuesta si es necesario
	        assertEquals("SUCCESS: Capability Token has been successfully validated.",responseEntity.getBody());
	       
	    }
}
