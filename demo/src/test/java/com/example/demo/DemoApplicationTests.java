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
		String requestBody =  "{\n" +
			    "    \"didSP\": \"natalia\",\n" +
			    "    \"sar\": {\n" +
			    "        \"action\":\"GET\",\n" +
			    "        \"resource\":\"/temperatura\"\n" +
			    "    },\n" +
			    "    \"didRequester\": \"did\",\n" +
			    "    \"verifiablePresentation\": \"{\\\"@context\\\": [\\\"https://www.w3.org/2018/credentials/v1\\\"], \\\"holder\\\": \\\"did:key:z6MknC1wwS6DEYwtGbZZo2QvjQjkh2qSBjb4GYmbye8dv4S5\\\", \\\"proof\\\": { \\\"created\\\": \\\"2021-03-26T14:08:21.15597-04:00\\\", \\\"jws\\\": \\\"eyJhbGciOiJFZERTQSIsImI2NCI6ZmFsc2UsImNyaXQiOlsiYjY0Il19..GUbI3psCXXhCjDJ2yBTwteuKSUHJuEK840yJzxWuPPxYyAuza1uwK1v75Az2jO63ILHEsLmxwcEhBlKcTw7ODA\\\", \\\"proofPurpose\\\": \\\"authentication\\\", \\\"type\\\": \\\"Ed25519Signature2018\\\", \\\"verificationMethod\\\": \\\"did:key:z6MknC1wwS6DEYwtGbZZo2QvjQjkh2qSBjb4GYmbye8dv4S5#z6MknC1wwS6DEYwtGbZZo2QvjQjkh2qSBjb4GYmbye8dv4S5\\\" }, \\\"type\\\": \\\"VerifiablePresentation\\\", \\\"verifiableCredential\\\": [{ \\\"@context\\\": [\\\"https://www.w3.org/2018/credentials/v1\\\", \\\"https://www.w3.org/2018/credentials/examples/v1\\\", \\\"https://w3id.org/security/bbs/v1\\\"], \\\"credentialSubject\\\": { \\\"degree\\\": {\\\"type\\\": \\\"BachelorDegree\\\", \\\"university\\\": \\\"MIT\\\"}, \\\"id\\\": \\\"did:example:ebfeb1f712ebc6f1c276e12ec21\\\", \\\"name\\\": \\\"Jayden Doe\\\", \\\"spouse\\\": \\\"did:example:c276e12ec21ebfeb1f712ebc6f1\\\" }, \\\"expirationDate\\\": \\\"2020-01-01T19:23:24Z\\\", \\\"id\\\": \\\"http://example.edu/credentials/1872\\\", \\\"issuanceDate\\\": \\\"2010-01-01T19:23:24Z\\\", \\\"issuer\\\": { \\\"id\\\": \\\"did:example:76e12ec712ebc6f1c221ebfeb1f\\\", \\\"name\\\": \\\"Example University\\\" }, \\\"proof\\\": { \\\"created\\\": \\\"2021-03-26T14:08:20.898673-04:00\\\", \\\"jws\\\": \\\"eyJhbGciOiJFZERTQSIsImI2NCI6ZmFsc2UsImNyaXQiOlsiYjY0Il19..PeIllfXnUh7zD4mH24NCnfFFeKf0Fys8XWt8nVE2Z-fgSvE6-3Rbc-LgSIpyKPF20CtFzEdownwOiMavy2_tAQ\\\", \\\"proofPurpose\\\": \\\"assertionMethod\\\", \\\"type\\\": \\\"Ed25519Signature2018\\\", \\\"verificationMethod\\\": \\\"did:key:z6MknC1wwS6DEYwtGbZZo2QvjQjkh2qSBjb4GYmbye8dv4S5#z6MknC1wwS6DEYwtGbZZo2QvjQjkh2qSBjb4GYmbye8dv4S5\\\" }, \\\"referenceNumber\\\": 83294847, \\\"type\\\": [\\\"VerifiableCredential\\\", \\\"UniversityDegreeCredential\\\"] }] }\"\n" +
			    "}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
System.out.println(requestBody);
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
		assertEquals("SUCCESS: Capability Token has been successfully validated. The requester could access to the resource.\n", responseEntity.getBody());

	}
}
