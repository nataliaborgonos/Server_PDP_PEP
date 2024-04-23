package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apache.commons.lang.StringEscapeUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import com.example.demo.PEP.PEP;
import com.example.demo.idAgent.IdentityAgent;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,properties = {"server.port=8088"})

public class DemoApplicationErathostenes {
		String token=null;
		IdentityAgent idAg=new IdentityAgent();
		
		public DemoApplicationErathostenes() {
			 // Arguments
		        System.setProperty("pdpConfig", "eratosthenes");
		}
		  @LocalServerPort
		    private int port=8088;
	
		 @Order(1)
		 @Tag("erat") 
		@Test
		void testRequestAccessEndpoint() {
			
			System.out.println("Test 1 for Eratosthenes: Request access for doing action GET in the resource /temperatura and getting the Capability Token \n" );
			
			 RestTemplate restTemplate = new RestTemplate();
			 
			   // Construye la URL con el puerto específico asignado
		        String url = "http://localhost:" + port + "/api/request-access";

		        String requestBody = "{\n" +
		        	    "    \"didSP\": \"natalia\",\n" +
		        	    "    \"sar\": {\n" +
		        	    "        \"action\": \"GET\",\n" +
		        	    "        \"resource\": \"/temperatura\"\n" +
		        	    "    },\n" +
		        	    "    \"didRequester\": \"did\",\n" +
		        	    "    \"verifiablePresentation\": \"{\\\"@context\\\": [\\\"https://www.w3.org/2018/credentials/v1\\\"], \\\"holder\\\": \\\"did:key:z6MknC1wwS6DEYwtGbZZo2QvjQjkh2qSBjb4GYmbye8dv4S5\\\", \\\"proof\\\": { \\\"created\\\": \\\"2021-03-26T14:08:21.15597-04:00\\\", \\\"jws\\\": \\\"eyJhbGciOiJFZERTQSIsImI2NCI6ZmFsc2UsImNyaXQiOlsiYjY0Il19..GUbI3psCXXhCjDJ2yBTwteuKSUHJuEK840yJzxWuPPxYyAuza1uwK1v75Az2jO63ILHEsLmxwcEhBlKcTw7ODA\\\", \\\"proofPurpose\\\": \\\"authentication\\\", \\\"type\\\": \\\"Ed25519Signature2018\\\", \\\"verificationMethod\\\": \\\"did:key:z6MknC1wwS6DEYwtGbZZo2QvjQjkh2qSBjb4GYmbye8dv4S5#z6MknC1wwS6DEYwtGbZZo2QvjQjkh2qSBjb4GYmbye8dv4S5\\\" }, \\\"type\\\": \\\"VerifiablePresentation\\\", \\\"verifiableCredential\\\": [{ \\\"@context\\\": [\\\"https://www.w3.org/2018/credentials/v1\\\", \\\"https://www.w3.org/2018/credentials/examples/v1\\\", \\\"https://w3id.org/security/bbs/v1\\\"], \\\"credentialSubject\\\": { \\\"degree\\\": {\\\"type\\\": \\\"BachelorDegree\\\", \\\"university\\\": \\\"MIT\\\"}, \\\"id\\\": \\\"did:example:ebfeb1f712ebc6f1c276e12ec21\\\", \\\"name\\\": \\\"Jayden Doe\\\", \\\"spouse\\\": \\\"did:example:c276e12ec21ebfeb1f712ebc6f1\\\" }, \\\"expirationDate\\\": \\\"2020-01-01T19:23:24Z\\\", \\\"id\\\": \\\"http://example.edu/credentials/1872\\\", \\\"issuanceDate\\\": \\\"2010-01-01T19:23:24Z\\\", \\\"issuer\\\": { \\\"id\\\": \\\"did:example:76e12ec712ebc6f1c221ebfeb1f\\\", \\\"name\\\": \\\"Example University\\\" }, \\\"proof\\\": { \\\"created\\\": \\\"2021-03-26T14:08:20.898673-04:00\\\", \\\"jws\\\": \\\"eyJhbGciOiJFZERTQSIsImI2NCI6ZmFsc2UsImNyaXQiOlsiYjY0Il19..PeIllfXnUh7zD4mH24NCnfFFeKf0Fys8XWt8nVE2Z-fgSvE6-3Rbc-LgSIpyKPF20CtFzEdownwOiMavy2_tAQ\\\", \\\"proofPurpose\\\": \\\"assertionMethod\\\", \\\"type\\\": \\\"Ed25519Signature2018\\\", \\\"verificationMethod\\\": \\\"did:key:z6MknC1wwS6DEYwtGbZZo2QvjQjkh2qSBjb4GYmbye8dv4S5#z6MknC1wwS6DEYwtGbZZo2QvjQjkh2qSBjb4GYmbye8dv4S5\\\" }, \\\"referenceNumber\\\": 83294847, \\\"type\\\": [\\\"VerifiableCredential\\\", \\\"UniversityDegreeCredential\\\"] }] }\"\n" +
		        	    "}";
			
	        // Define las cabeceras de la solicitud
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);

	        // Crea la entidad de solicitud HTTP con el cuerpo y las cabeceras definidas
	        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

	        // Realiza la solicitud POST
	        ResponseEntity<String> responseEntity = restTemplate.exchange(
	                url,
	                HttpMethod.POST,
	                requestEntity,
	                String.class
	        );
// Token issued for requester
			token = responseEntity.getBody();

			// Verify 200 OK
			assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

			// Verify that there's a CapabilityToken and is not null
			assertNotNull(responseEntity.getBody());
			assertTrue(!responseEntity.getBody().isEmpty());

		}

		 @Order(2)
		 @Tag("erat") 
		@Test
		void testAccessWithToken() {
			
			System.out.println("Test 2 for Eratosthenes: Doing action GET in the resource /temperatura with the Capability Token received \n" );
			
			RestTemplate restTemplate = new RestTemplate();
			 
			 
			  // Construye la URL con el puerto específico asignado
	        String url = "http://localhost:" + port + "/api/access-with-token";

			String requestBody = "{\n" + "    \"ct\": " + token + ",\n" + "     \"sar\":{\n"
					+ "        \"action\":\"GET\",\n" + "        \"resource\":\"/temperatura\"\n" + "    }\n" + "}";

	        // Define las cabeceras de la solicitud
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);

	        // Crea la entidad de solicitud HTTP con el cuerpo y las cabeceras definidas
	        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

	        // Realiza la solicitud POST
	        ResponseEntity<String> responseEntity = restTemplate.exchange(
	                url,
	                HttpMethod.POST,
	                requestEntity,
	                String.class
	        );

			// Verify that there's a CapabilityToken and is not null
			assertNotNull(responseEntity.getBody());
			assertTrue(!responseEntity.getBody().isEmpty());
			// Verify 200 OK
			assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

			// Verify that it has been a successful validation
			assertEquals("SUCCESS: Capability Token has been successfully validated. The requester could access to the resource.\n", responseEntity.getBody());

		}
		 
		 @Order(3)
		 @Tag("erat") 
		@Test
		void testAccessWithWrongToken() {
			System.out.println("Test 3 for Eratosthenes: Trying to do action POST in the resource /temperatura with the Capability Token received \n" );
			String requestBody = "{\n" + "    \"ct\": " + token + ",\n" + "     \"sar\":{\n"
					+ "        \"action\":\"POST\",\n" + "        \"resource\":\"/temperatura\"\n" + "    }\n" + "}";

			RestTemplate restTemplate = new RestTemplate();
			 
			 
			  // Construye la URL con el puerto específico asignado
	        String url = "http://localhost:" + port + "/api/access-with-token";

			
	        // Define las cabeceras de la solicitud
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);

	        // Crea la entidad de solicitud HTTP con el cuerpo y las cabeceras definidas
	        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

	        // Realiza la solicitud POST
	        ResponseEntity<String> responseEntity = restTemplate.exchange(
	                url,
	                HttpMethod.POST,
	                requestEntity,
	                String.class
	        );
			// Verify that there's a CapabilityToken and is not null
			assertNotNull(responseEntity.getBody());
			assertTrue(!responseEntity.getBody().isEmpty());
		
			// Verify that it has been a successful validation
			assertEquals("ERROR: Validation failed. Capability Token is not valid. The requester couldn't access to the resource.\n", responseEntity.getBody());

		}
		
		 @Order(4)
		 @Tag("erat") 
		@Test
		void testNotMatchingPolicy() {
			System.out.println("Test 4 for Eratosthenes: Request access for doing action GET in the resource /temperatura and not getting the Capability Token because the policy matching failed \n" );
			
			 RestTemplate restTemplate = new RestTemplate();
			 
			   // Construye la URL con el puerto específico asignado
		        String url = "http://localhost:" + port + "/api/request-access";

		        String requestBody = "{\n" +
		        	    "    \"didSP\": \"natalia\",\n" +
		        	    "    \"sar\": {\n" +
		        	    "        \"action\": \"GET\",\n" +
		        	    "        \"resource\": \"/temperatura\"\n" +
		        	    "    },\n" +
		        	    "    \"didRequester\": \"did\",\n" +
		        	    "    \"verifiablePresentation\": \"{\\\"@context\\\": [\\\"https://www.w3.org/2018/credentials/v1\\\"], \\\"holder\\\": \\\"did:key:z6MknC1wwS6DEYwtGbZZo2QvjQjkh2qSBjb4GYmbye8dv4S5\\\", \\\"proof\\\": { \\\"created\\\": \\\"2021-03-26T14:08:21.15597-04:00\\\", \\\"jws\\\": \\\"eyJhbGciOiJFZERTQSIsImI2NCI6ZmFsc2UsImNyaXQiOlsiYjY0Il19..GUbI3psCXXhCjDJ2yBTwteuKSUHJuEK840yJzxWuPPxYyAuza1uwK1v75Az2jO63ILHEsLmxwcEhBlKcTw7ODA\\\", \\\"proofPurpose\\\": \\\"authentication\\\", \\\"type\\\": \\\"Ed25519Signature2018\\\", \\\"verificationMethod\\\": \\\"did:key:z6MknC1wwS6DEYwtGbZZo2QvjQjkh2qSBjb4GYmbye8dv4S5#z6MknC1wwS6DEYwtGbZZo2QvjQjkh2qSBjb4GYmbye8dv4S5\\\" }, \\\"type\\\": \\\"VerifiablePresentation\\\", \\\"verifiableCredential\\\": [{ \\\"@context\\\": [\\\"https://www.w3.org/2018/credentials/v1\\\", \\\"https://www.w3.org/2018/credentials/examples/v1\\\", \\\"https://w3id.org/security/bbs/v1\\\"], \\\"credentialSubject\\\": { \\\"degree\\\": {\\\"type\\\": \\\"BachelorDegree\\\", \\\"university\\\": \\\"MIT\\\"}, \\\"id\\\": \\\"did:example:ebfeb1f712ebc6f1c276e12ec21\\\", \\\"spouse\\\": \\\"did:example:c276e12ec21ebfeb1f712ebc6f1\\\" }, \\\"expirationDate\\\": \\\"2020-01-01T19:23:24Z\\\", \\\"id\\\": \\\"http://example.edu/credentials/1872\\\", \\\"issuanceDate\\\": \\\"2010-01-01T19:23:24Z\\\", \\\"issuer\\\": { \\\"id\\\": \\\"did:example:76e12ec712ebc6f1c221ebfeb1f\\\", \\\"name\\\": \\\"Example University\\\" }, \\\"proof\\\": { \\\"created\\\": \\\"2021-03-26T14:08:20.898673-04:00\\\", \\\"jws\\\": \\\"eyJhbGciOiJFZERTQSIsImI2NCI6ZmFsc2UsImNyaXQiOlsiYjY0Il19..PeIllfXnUh7zD4mH24NCnfFFeKf0Fys8XWt8nVE2Z-fgSvE6-3Rbc-LgSIpyKPF20CtFzEdownwOiMavy2_tAQ\\\", \\\"proofPurpose\\\": \\\"assertionMethod\\\", \\\"type\\\": \\\"Ed25519Signature2018\\\", \\\"verificationMethod\\\": \\\"did:key:z6MknC1wwS6DEYwtGbZZo2QvjQjkh2qSBjb4GYmbye8dv4S5#z6MknC1wwS6DEYwtGbZZo2QvjQjkh2qSBjb4GYmbye8dv4S5\\\" }, \\\"referenceNumber\\\": 83294847, \\\"type\\\": [\\\"VerifiableCredential\\\", \\\"UniversityDegreeCredential\\\"] }] }\"\n" +
		        	    "}";
			
	        // Define las cabeceras de la solicitud
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);

	        // Crea la entidad de solicitud HTTP con el cuerpo y las cabeceras definidas
	        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

	        // Realiza la solicitud POST
	        ResponseEntity<String> responseEntity = restTemplate.exchange(
	                url,
	                HttpMethod.POST,
	                requestEntity,
	                String.class
	        );
// Token issued for requester
			token = responseEntity.getBody();
		
			// Verify that there's a CapabilityToken and is not null
			assertNotNull(responseEntity.getBody());
			assertTrue(!responseEntity.getBody().isEmpty());
			assertEquals("Capability Token couldn't be issued, please revise the request and try again.\n", responseEntity.getBody());

		}
		 
		 @Order(5)
		 @Tag("erat") 
		 @Test
			void testIdAg() {
				System.out.println("Test 5 for Eratosthenes: Identity Agent fails. \n" );
				IdentityAgent.setPortIdAgent("9999");
		        // Ejecutar el método createWallet
		        try {
		            idAg.createWallet("user");
		            // Si no se lanza ninguna excepción, el test pasa
		        } catch (Exception e) {
		        	   // Verificar el mensaje de la excepción
		            assertEquals("Identity Agent component is not avaliable. Please restart it and try again.\n", e.getMessage());
		    
		        }
			}
		
	
	}


