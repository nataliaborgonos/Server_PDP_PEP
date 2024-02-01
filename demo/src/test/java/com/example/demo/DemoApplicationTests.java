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
		String requestBody = "{\n" + "    \"didSP\": \"natalia\",\n" + "    \"sar\": {\n"
				+ "        \"action\":\"GET\",\n" + "        \"resource\":\"/temperatura\"\n" + "    },\n"
				+ "    \"didRequester\": \"did\",\n" + "    \"verifiablePresentation\":  {\"id\":\"9ac9ebb4\",\n"
				+ " \"type\":\"VerifiablePresentation\",\n" + " \"issuingDate\":\"2024-01-25T16:31:10.802145808\",\n"
				+ " \"verifiableCredential\":[\n"
				+ " {\"id\":\"5746b866\",\"type\":\"VerifiableCredential\",\"issuer\":\"Erathostenes\",\"issuanceDate\":\"2024-01-25T16:31:10.641923467\",\"credentialSubject\":{\"id\":\"natalia\",\"nombre\":\"natalia\",\"apellido\":\"apellido\",\"birthDate\":\"1999-01-19\",\"age\":25},\"proof\":{\"type\":\"PsmsBlsSignature2022\",\"created\":\"2024-01-25T16:31:10.647602027\",\"verificationMethod\":\"did:fabric:98147c6a2a0e5ab92a308124b5ad7b55986b7ebfba0e5e0d0c9f2d56a93407e8:2fd02be71ab8812cf681a500dfeb3e5524f5bda99aa56a07d59d03f716226946#didkey\",\n"
				+ " \"proofPurpose\":\"assertionMethod\",\"jws\":\"123\"}}],\"proof\":{\"type\":\"PsmsBlsSignature2022\",\"created\":\"2024-01-25T16:31:10.803576042\",\"verificationMethod\":\"123\",\"proofPurpose\":\"hola\",\"jws\":\"123\"}}\n"
				+ "}";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

		ResponseEntity<String> responseEntity = restTemplate.exchange("/api/request-access", HttpMethod.POST,
				requestEntity, String.class);

		// Token issued for requester
		token = responseEntity.getBody();

		// Verify 200 OK
		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

		// Verify that there's a CapabilityToken and is not null
		assertNotNull(responseEntity.getBody());
		assertTrue(!responseEntity.getBody().isEmpty());

	}

	@Test
	void testAccessWithToken() {
		String requestBody = "{\n" + "    \"ct\": " + token + ",\n" + "     \"sar\":{\n"
				+ "        \"action\":\"GET\",\n" + "        \"resource\":\"/temperatura\"\n" + "    }\n" + "}";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

		ResponseEntity<String> responseEntity = restTemplate.exchange("/api/access-with-token", HttpMethod.POST,
				requestEntity, String.class);

		// Verify 200 OK
		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

		// Verify that it has been a successful validation
		assertEquals("SUCCESS: Capability Token has been successfully validated.", responseEntity.getBody());

	}
}
