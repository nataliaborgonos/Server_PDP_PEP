	package com.example.demo;

	import org.junit.jupiter.api.Test;
	import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.beans.factory.annotation.Value;
	import org.springframework.boot.autoconfigure.SpringBootApplication;
	import org.springframework.boot.test.context.SpringBootTest;
	import org.springframework.boot.test.web.client.TestRestTemplate;
	import org.springframework.context.annotation.Configuration;
	import org.springframework.http.HttpEntity;
	import org.springframework.http.HttpHeaders;
	import org.springframework.http.HttpMethod;
	import org.springframework.http.MediaType;
	import org.springframework.http.ResponseEntity;
	import org.springframework.test.context.TestPropertySource;
	import org.springframework.test.context.event.annotation.BeforeTestExecution;
	import org.springframework.test.context.junit.jupiter.SpringExtension;
	import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.web.client.RestTemplate;

import com.example.demo.models.TSMConfigResponse;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import static org.junit.jupiter.api.Assertions.assertNotNull;
	import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.json.Json;

import org.junit.jupiter.api.BeforeAll;
	import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
	@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
	@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DemoApplicationConnectorToken {
	 Gson gson;
		 public DemoApplicationConnectorToken() {
			 // Arguments
		        System.setProperty("pdpConfig", "test");
		        gson=new Gson();
		}
		
		String token;
		String shortLivedToken;
		String entityDid;
	    int configId;
		@Autowired
		private TestRestTemplate restTemplate;
		

		 @Order(1)
		@Test
		void testAddTSMConfig() {
			System.out.println("Test 1 for TSM Component: Add TSM Configuration correctly.\n" );
			
			
			String requestBody = "{\n" +
				    "    \"entity_did\": \"df429e49-23e1-4013-9962-091602afdd83\",\n" +
				    "    \"trustworthiness\": {\n" +
				    "        \"name\": \"configuration\",\n" +
				    "        \"minimum_value\": 0.33,\n" +
				    "        \"description\": \"Configuration for testing\",\n" +
				    "        \"buffer_size\": 100\n" +
				    "    },\n" +
				    "    \"characteristics\": [\n" +
				    "        {\n" +
				    "            \"name\": \"security\",\n" +
				    "            \"weight\": 0.5,\n" +
				    "            \"minimum_value\": 0.33,\n" +
				    "            \"created\": \"2024-09-10T16:39:39.316Z\",\n" +
				    "            \"modified\": \"2024-09-10T16:39:39.316Z\",\n" +
				    "            \"attributes\": [\n" +
				    "                {\n" +
				    "                    \"name\": \"a1\",\n" +
				    "                    \"weight\": 0.5,\n" +
				    "                    \"minimum_value\": 0.33,\n" +
				    "                    \"created\": \"2024-09-10T16:39:39.316Z\"\n" +
				    "                },\n" +
				    "                {\n" +
				    "                    \"name\": \"a2\",\n" +
				    "                    \"weight\": 0.6,\n" +
				    "                    \"minimum_value\": 0.33,\n" +
				    "                    \"created\": \"2024-09-10T16:39:39.316Z\"\n" +
				    "                }\n" +
				    "            ]\n" +
				    "        },\n" +
				    "        {\n" +
				    "            \"name\": \"safety\",\n" +
				    "            \"weight\": 0.4,\n" +
				    "            \"minimum_value\": 0.33,\n" +
				    "            \"created\": \"2024-09-10T16:39:39.316Z\",\n" +
				    "            \"modified\": \"2024-09-10T16:39:39.316Z\",\n" +
				    "            \"attributes\": [\n" +
				    "                {\n" +
				    "                    \"name\": \"a1\",\n" +
				    "                    \"weight\": 0.5,\n" +
				    "                    \"minimum_value\": 0.33,\n" +
				    "                    \"created\": \"2024-09-10T16:39:39.316Z\"\n" +
				    "                }\n" +
				    "            ]\n" +
				    "        },\n" +
				    "        {\n" +
				    "            \"name\": \"reliability\",\n" +
				    "            \"weight\": 0.3,\n" +
				    "            \"minimum_value\": 0.33,\n" +
				    "            \"created\": \"2024-09-10T16:39:39.316Z\",\n" +
				    "            \"modified\": \"2024-09-10T16:39:39.316Z\",\n" +
				    "            \"attributes\": [\n" +
				    "                {\n" +
				    "                    \"name\": \"a1\",\n" +
				    "                    \"weight\": 0.5,\n" +
				    "                    \"minimum_value\": 0.33,\n" +
				    "                    \"created\": \"2024-09-10T16:39:39.316Z\"\n" +
				    "                },\n" +
				    "                {\n" +
				    "                    \"name\": \"a2\",\n" +
				    "                    \"weight\": 0.6,\n" +
				    "                    \"minimum_value\": 0.33,\n" +
				    "                    \"created\": \"2024-09-10T16:39:39.316Z\"\n" +
				    "                },\n" +
				    "                {\n" +
				    "                    \"name\": \"a3\",\n" +
				    "                    \"weight\": 0.3,\n" +
				    "                    \"minimum_value\": 0.33,\n" +
				    "                    \"created\": \"2024-09-10T16:39:39.316Z\"\n" +
				    "                }\n" +
				    "            ]\n" +
				    "        },\n" +
				    "        {\n" +
				    "            \"name\": \"resilience\",\n" +
				    "            \"weight\": 0.3,\n" +
				    "            \"minimum_value\": 0.33,\n" +
				    "            \"created\": \"2024-09-10T16:39:39.316Z\",\n" +
				    "            \"modified\": \"2024-09-10T16:39:39.316Z\",\n" +
				    "            \"attributes\": [\n" +
				    "                {\n" +
				    "                    \"name\": \"a1\",\n" +
				    "                    \"weight\": 0.8,\n" +
				    "                    \"minimum_value\": 0.33,\n" +
				    "                    \"created\": \"2024-09-10T16:39:39.316Z\"\n" +
				    "                },\n" +
				    "                {\n" +
				    "                    \"name\": \"a2\",\n" +
				    "                    \"weight\": 0.1,\n" +
				    "                    \"minimum_value\": 0.33,\n" +
				    "                    \"created\": \"2024-09-10T16:39:39.316Z\"\n" +
				    "                }\n" +
				    "            ]\n" +
				    "        },\n" +
				    "        {\n" +
				    "            \"name\": \"privacy\",\n" +
				    "            \"weight\": 0.2,\n" +
				    "            \"minimum_value\": 0.33,\n" +
				    "            \"created\": \"2024-09-10T16:39:39.316Z\",\n" +
				    "            \"modified\": \"2024-09-10T16:39:39.316Z\",\n" +
				    "            \"attributes\": [\n" +
				    "                {\n" +
				    "                    \"name\": \"a1\",\n" +
				    "                    \"weight\": 0.35,\n" +
				    "                    \"minimum_value\": 0.33,\n" +
				    "                    \"created\": \"2024-09-10T16:39:39.316Z\"\n" +
				    "                }\n" +
				    "            ]\n" +
				    "        }\n" +
				    "    ]\n" +
				"}";

		
			JsonObject jsonObject = JsonParser.parseString(requestBody).getAsJsonObject();
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
	
			ResponseEntity<String> responseEntity = restTemplate.exchange("/api/trust-score-config", HttpMethod.POST,
					requestEntity, String.class);
			
			// Verify 200 OK
			assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

			assertNotNull(responseEntity.getBody());
			TSMConfigResponse resp=gson.fromJson(responseEntity.getBody(), TSMConfigResponse.class);
			
		    // Extract fields entity_did and config_id
		    entityDid = resp.getEntity_did();
		    configId = resp.getConfig_id();
		}
		
		 @Order(2)
			@Test
			void testAddTSMPOSConfig() {
				System.out.println("Test 2 for TSM Component: Add Protective Objectives to the TSM Configuration correctly.\n" );
				
				String requestBody = "{\n" +
						"    \"entity_did\": \""+entityDid+"\",\n" +
						"    \"config_id\": "+configId+",\n" +
						"    \"protective_objectives\": [\n" +
						"        {\n" +
						"            \"name\": \"securitya1\",\n" +
						"            \"value\": 0.7,\n" +
						"            \"timestamp\": \"2024-09-10T16:58:17.445Z\",\n" +
						"            \"created\": \"2024-09-10T16:58:17.445Z\"\n" +
						"        },\n" +
						"        {\n" +
						"            \"name\": \"securitya2\",\n" +
						"            \"value\": 0.8,\n" +
						"            \"timestamp\": \"2024-09-10T16:58:17.445Z\",\n" +
						"            \"created\": \"2024-09-10T16:58:17.445Z\"\n" +
						"        },\n" +
						"        {\n" +
						"            \"name\": \"safetya1\",\n" +
						"            \"value\": 0.8,\n" +
						"            \"timestamp\": \"2024-09-10T16:58:17.445Z\",\n" +
						"            \"created\": \"2024-09-10T16:58:17.445Z\"\n" +
						"        },\n" +
						"        {\n" +
						"            \"name\": \"reliabilitya1\",\n" +
						"            \"value\": 0.9,\n" +
						"            \"timestamp\": \"2024-09-10T16:58:17.445Z\",\n" +
						"            \"created\": \"2024-09-10T16:58:17.445Z\"\n" +
						"        },\n" +
						"        {\n" +
						"            \"name\": \"reliabilitya2\",\n" +
						"            \"value\": 0.8,\n" +
						"            \"timestamp\": \"2024-09-10T16:58:17.445Z\",\n" +
						"            \"created\": \"2024-09-10T16:58:17.445Z\"\n" +
						"        },\n" +
						"        {\n" +
						"            \"name\": \"reliabilitya3\",\n" +
						"            \"value\": 0.9,\n" +
						"            \"timestamp\": \"2024-09-10T16:58:17.445Z\",\n" +
						"            \"created\": \"2024-09-10T16:58:17.445Z\"\n" +
						"        },\n" +
						"        {\n" +
						"            \"name\": \"resiliencea1\",\n" +
						"            \"value\": 0.8,\n" +
						"            \"timestamp\": \"2024-09-10T16:58:17.445Z\",\n" +
						"            \"created\": \"2024-09-10T16:58:17.445Z\"\n" +
						"        },\n" +
						"        {\n" +
						"            \"name\": \"resiliencea2\",\n" +
						"            \"value\": 0.9,\n" +
						"            \"timestamp\": \"2024-09-10T16:58:17.445Z\",\n" +
						"            \"created\": \"2024-09-10T16:58:17.445Z\"\n" +
						"        },\n" +
						"        {\n" +
						"            \"name\": \"privacya1\",\n" +
						"            \"value\": 0.9,\n" +
						"            \"timestamp\": \"2024-09-10T16:58:17.445Z\",\n" +
						"            \"created\": \"2024-09-10T16:58:17.445Z\"\n" +
						"        }\n" +
						"    ]\n" +
						"}";


				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);

				HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
		
				ResponseEntity<String> responseEntity = restTemplate.exchange("/api/add-protective-objectives", HttpMethod.POST,
						requestEntity, String.class);

			
				// Verify 200 OK
				assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

			
				assertNotNull(responseEntity.getBody());
				assertTrue(!responseEntity.getBody().isEmpty());
			
			}
		
		    @Order(3)
					@Test
					void testGetConfig() {
						System.out.println("Test 3 for TSM Component: Get Trustworthiness Configuration correctly.\n" );
						
						String requestBody = "{\n" +
								"    \"entity_did\": \""+entityDid+"\",\n" +
								"    \"config_id\": "+configId+"\n"+
								"}";


						HttpHeaders headers = new HttpHeaders();
						headers.setContentType(MediaType.APPLICATION_JSON);

						HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
				
						ResponseEntity<String> responseEntity = restTemplate.exchange("/api/get-config", HttpMethod.POST,
								requestEntity, String.class);

					
						// Verify 200 OK
						assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

						assertNotNull(responseEntity.getBody());
						assertTrue(!responseEntity.getBody().isEmpty());
					
					}
		 
		    @Order(4)
			@Test
			void testAddWrongTSMConfig() {
				System.out.println("Test 4 for TSM Component: Add wrong TSM Configuration.\n" );
				
				
				String requestBody = "{\n" +
					    "    \"trustworthiness\": {\n" +
					    "        \"name\": \"configuration\",\n" +
					    "        \"minimum_value\": 0.33,\n" +
					    "        \"description\": \"Configuration for testing\",\n" +
					    "        \"buffer_size\": 100\n" +
					    "    },\n" +
					    "    \"characteristics\": [\n" +
					    "        {\n" +
					    "            \"name\": \"security\",\n" +
					    "            \"weight\": 0.5,\n" +
					    "            \"minimum_value\": 0.33,\n" +
					    "            \"created\": \"2024-09-10T16:39:39.316Z\",\n" +
					    "            \"modified\": \"2024-09-10T16:39:39.316Z\",\n" +
					    "            \"attributes\": [\n" +
					    "                {\n" +
					    "                    \"name\": \"a1\",\n" +
					    "                    \"weight\": 0.5,\n" +
					    "                    \"minimum_value\": 0.33,\n" +
					    "                    \"created\": \"2024-09-10T16:39:39.316Z\"\n" +
					    "                },\n" +
					    "                {\n" +
					    "                    \"name\": \"a2\",\n" +
					    "                    \"weight\": 0.6,\n" +
					    "                    \"minimum_value\": 0.33,\n" +
					    "                    \"created\": \"2024-09-10T16:39:39.316Z\"\n" +
					    "                }\n" +
					    "            ]\n" +
					    "        },\n" +
					    "        {\n" +
					    "            \"name\": \"safety\",\n" +
					    "            \"weight\": 0.4,\n" +
					    "            \"minimum_value\": 0.33,\n" +
					    "            \"created\": \"2024-09-10T16:39:39.316Z\",\n" +
					    "            \"modified\": \"2024-09-10T16:39:39.316Z\",\n" +
					    "            \"attributes\": [\n" +
					    "                {\n" +
					    "                    \"name\": \"a1\",\n" +
					    "                    \"weight\": 0.5,\n" +
					    "                    \"minimum_value\": 0.33,\n" +
					    "                    \"created\": \"2024-09-10T16:39:39.316Z\"\n" +
					    "                }\n" +
					    "            ]\n" +
					    "        },\n" +
					    "        {\n" +
					    "            \"name\": \"reliability\",\n" +
					    "            \"weight\": 0.3,\n" +
					    "            \"minimum_value\": 0.33,\n" +
					    "            \"created\": \"2024-09-10T16:39:39.316Z\",\n" +
					    "            \"modified\": \"2024-09-10T16:39:39.316Z\",\n" +
					    "            \"attributes\": [\n" +
					    "                {\n" +
					    "                    \"name\": \"a1\",\n" +
					    "                    \"weight\": 0.5,\n" +
					    "                    \"minimum_value\": 0.33,\n" +
					    "                    \"created\": \"2024-09-10T16:39:39.316Z\"\n" +
					    "                },\n" +
					    "                {\n" +
					    "                    \"name\": \"a2\",\n" +
					    "                    \"weight\": 0.6,\n" +
					    "                    \"minimum_value\": 0.33,\n" +
					    "                    \"created\": \"2024-09-10T16:39:39.316Z\"\n" +
					    "                },\n" +
					    "                {\n" +
					    "                    \"name\": \"a3\",\n" +
					    "                    \"weight\": 0.3,\n" +
					    "                    \"minimum_value\": 0.33,\n" +
					    "                    \"created\": \"2024-09-10T16:39:39.316Z\"\n" +
					    "                }\n" +
					    "            ]\n" +
					    "        },\n" +
					    "        {\n" +
					    "            \"name\": \"resilience\",\n" +
					    "            \"weight\": 0.3,\n" +
					    "            \"minimum_value\": 0.33,\n" +
					    "            \"created\": \"2024-09-10T16:39:39.316Z\",\n" +
					    "            \"modified\": \"2024-09-10T16:39:39.316Z\",\n" +
					    "            \"attributes\": [\n" +
					    "                {\n" +
					    "                    \"name\": \"a1\",\n" +
					    "                    \"weight\": 0.8,\n" +
					    "                    \"minimum_value\": 0.33,\n" +
					    "                    \"created\": \"2024-09-10T16:39:39.316Z\"\n" +
					    "                },\n" +
					    "                {\n" +
					    "                    \"name\": \"a2\",\n" +
					    "                    \"weight\": 0.1,\n" +
					    "                    \"minimum_value\": 0.33,\n" +
					    "                    \"created\": \"2024-09-10T16:39:39.316Z\"\n" +
					    "                }\n" +
					    "            ]\n" +
					    "        },\n" +
					    "        {\n" +
					    "            \"name\": \"privacy\",\n" +
					    "            \"weight\": 0.2,\n" +
					    "            \"minimum_value\": 0.33,\n" +
					    "            \"created\": \"2024-09-10T16:39:39.316Z\",\n" +
					    "            \"modified\": \"2024-09-10T16:39:39.316Z\",\n" +
					    "            \"attributes\": [\n" +
					    "                {\n" +
					    "                    \"name\": \"a1\",\n" +
					    "                    \"weight\": 0.35,\n" +
					    "                    \"minimum_value\": 0.33,\n" +
					    "                    \"created\": \"2024-09-10T16:39:39.316Z\"\n" +
					    "                }\n" +
					    "            ]\n" +
					    "        }\n" +
					    "    ]\n" +
					"}";

				
				JsonObject jsonObject = JsonParser.parseString(requestBody).getAsJsonObject();
				
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);

				HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
		
				ResponseEntity<String> responseEntity = restTemplate.exchange("/api/trust-score-config", HttpMethod.POST,
						requestEntity, String.class);
				
				// Verify 200 OK
				assertEquals(responseEntity.getBody(), "error");

				
			}
		    
		    
		    @Order(5)
			@Test
			void testGetWongConfig() {
				System.out.println("Test 5 for TSM Component: Get wrong Trustworthiness Configuration \n" );
				
				String requestBody = "{\n" +
						"    \"entity_did\": \""+entityDid+"\",\n" +
						"    \"config_id\": -1\n"+
						"}";


				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);

				HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
		
				ResponseEntity<String> responseEntity = restTemplate.exchange("/api/get-config", HttpMethod.POST,
						requestEntity, String.class);

			
				// Verify 200 OK
				assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

				// Verify that there's a CapabilityToken and is not null
				assertNotNull(responseEntity.getBody());
				assertTrue(!responseEntity.getBody().isEmpty());
			
			}
		    
		    
		    @Order(6)
			@Test
			void testAddWrongTSMPOSConfig() {
				System.out.println("Test 6 for TSM Component: Add Wrong Protective Objectives to the TSM Configuration. \n" );
				
				String requestBody = "{\n" +
						"    \"entity_did\": \"not_valid\",\n" +
						"    \"config_id\": "+configId+",\n" +
						"    \"protective_objectives\": [\n" +
						"        {\n" +
						"            \"name\": \"securitya1\",\n" +
						"            \"value\": 0.7,\n" +
						"            \"timestamp\": \"2024-09-10T16:58:17.445Z\",\n" +
						"            \"created\": \"2024-09-10T16:58:17.445Z\"\n" +
						"        },\n" +
						"        {\n" +
						"            \"name\": \"securitya2\",\n" +
						"            \"value\": 0.8,\n" +
						"            \"timestamp\": \"2024-09-10T16:58:17.445Z\",\n" +
						"            \"created\": \"2024-09-10T16:58:17.445Z\"\n" +
						"        },\n" +
						"        {\n" +
						"            \"name\": \"safetya1\",\n" +
						"            \"value\": 0.8,\n" +
						"            \"timestamp\": \"2024-09-10T16:58:17.445Z\",\n" +
						"            \"created\": \"2024-09-10T16:58:17.445Z\"\n" +
						"        },\n" +
						"        {\n" +
						"            \"name\": \"reliabilitya1\",\n" +
						"            \"value\": 0.9,\n" +
						"            \"timestamp\": \"2024-09-10T16:58:17.445Z\",\n" +
						"            \"created\": \"2024-09-10T16:58:17.445Z\"\n" +
						"        },\n" +
						"        {\n" +
						"            \"name\": \"reliabilitya2\",\n" +
						"            \"value\": 0.8,\n" +
						"            \"timestamp\": \"2024-09-10T16:58:17.445Z\",\n" +
						"            \"created\": \"2024-09-10T16:58:17.445Z\"\n" +
						"        },\n" +
						"        {\n" +
						"            \"name\": \"reliabilitya3\",\n" +
						"            \"value\": 0.9,\n" +
						"            \"timestamp\": \"2024-09-10T16:58:17.445Z\",\n" +
						"            \"created\": \"2024-09-10T16:58:17.445Z\"\n" +
						"        },\n" +
						"        {\n" +
						"            \"name\": \"resiliencea1\",\n" +
						"            \"value\": 0.8,\n" +
						"            \"timestamp\": \"2024-09-10T16:58:17.445Z\",\n" +
						"            \"created\": \"2024-09-10T16:58:17.445Z\"\n" +
						"        },\n" +
						"        {\n" +
						"            \"name\": \"resiliencea2\",\n" +
						"            \"value\": 0.9,\n" +
						"            \"timestamp\": \"2024-09-10T16:58:17.445Z\",\n" +
						"            \"created\": \"2024-09-10T16:58:17.445Z\"\n" +
						"        },\n" +
						"        {\n" +
						"            \"name\": \"privacya1\",\n" +
						"            \"value\": 0.9,\n" +
						"            \"timestamp\": \"2024-09-10T16:58:17.445Z\",\n" +
						"            \"created\": \"2024-09-10T16:58:17.445Z\"\n" +
						"        }\n" +
						"    ]\n" +
						"}";


				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);

				HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
		
				ResponseEntity<String> responseEntity = restTemplate.exchange("/api/add-protective-objectives", HttpMethod.POST,
						requestEntity, String.class);

			
		
				assertNotNull(responseEntity.getBody());
				assertEquals(responseEntity.getBody(),"{\"detail\":\"Entity not found.\"}");
				assertTrue(!responseEntity.getBody().isEmpty());
			
			}
		    
			 @Order(7)
				@Test
				void testRequestAccessEndpointNoPolicies() {
					System.out.println("Test 1 for New Policies Endpoint: Request access for doing action GET in the resource /humidity but it has no polcies so the Capability Token is not issued properly. \n" );
					
					
					String requestBody =  "{\n" +
						    "    \"didSP\": \"Leo-Free-Home-Standing-1 Hand-Phone\",\n" +
						    "    \"sar\": {\n" +
						    "        \"action\":\"GET\",\n" +
						    "        \"resource\":\"https://api-server.testing1.k8s-cluster.tango.rid-intrasoft.eu/resource/humidity\"\n" +
						    "    },\n" +
						    "    \"didRequester\": \""+entityDid+"\",\n" +
						    "    \"accessToken\": \"eyJhbGciOiJFUzI1NiIsImtpZCI6IkN3YjlhMzNnVVE3Wmw0amlJZTI3alkwc1oweUd0ZXRVamxqdUFsb3RSWnciLCJ0eXAiOiJKV1QifQ.eyJhdWQiOlsicG9ydGFsLnRlc3RpbmcxLms4cy1jbHVzdGVyLnRhbmdvLnJpZC1pbnRyYXNvZnQuZXUiXSwiY2xpZW50X2lkIjoiZGlkOndlYjppcHMudGVzdGluZzEuazhzLWNsdXN0ZXIudGFuZ28ucmlkLWludHJhc29mdC5ldTpkaWQiLCJleHAiOjE3NDA1NjcxNDAsImlzcyI6ImRpZDp3ZWI6aXBzLnRlc3RpbmcxLms4cy1jbHVzdGVyLnRhbmdvLnJpZC1pbnRyYXNvZnQuZXU6ZGlkIiwia2lkIjoiQ3diOWEzM2dVUTdabDRqaUllMjdqWTBzWjB5R3RldFVqbGp1QWxvdFJadyIsInN1YiI6ImRpZDpteTp3YWxsZXQiLCJ2ZXJpZmlhYmxlQ3JlZGVudGlhbCI6eyJAY29udGV4dCI6WyJodHRwczovL3d3dy53My5vcmcvMjAxOC9jcmVkZW50aWFscy92MSIsImh0dHBzOi8vdzNpZC5vcmcvc2VjdXJpdHkvc3VpdGVzL2p3cy0yMDIwL3YxIl0sImNyZWRlbnRpYWxTY2hlbWEiOnsiaWQiOiJodHRwczovL3Jhdy5naXRodWJ1c2VyY29udGVudC5jb20vRklXQVJFLU9wcy90ZWNoLXgtY2hhbGxlbmdlL21haW4vc2NoZW1hLmpzb24iLCJ0eXBlIjoiRnVsbEpzb25TY2hlbWFWYWxpZGF0b3IyMDIxIn0sImNyZWRlbnRpYWxTdWJqZWN0Ijp7ImVtYWlsIjoic3RhbmRhcmQtZW1wbG95ZWVAaXBzLm9yZyIsImlkIjoiMGIxOWEzNDItN2I2NC00NjJmLWI5OTctYjkwZmZiYTlkMjgyIiwibGFzdE5hbWUiOiJJUFMiLCJyb2xlcyI6W3sibmFtZXMiOlsiR09MRF9DVVNUT01FUiJdLCJ0YXJnZXQiOiJkaWQ6d2ViOmlwcy5rOHMtY2x1c3Rlci50YW5nby5yaWQtaW50cmFzb2Z0LmV1OmRpZCJ9XSwidHlwZSI6Imd4Ok5hdHVyYWxQYXJ0aWNpcGFudCJ9LCJpZCI6InVybjp1dWlkOjhkMzdlNTA5LWNkNTctNGYwNS04YWIwLTY5YmM1NDNmMzE4MCIsImlzc3VhbmNlRGF0ZSI6IjIwMjQtMTEtMjBUMDk6Mzc6NDRaIiwiaXNzdWVkIjoiMjAyNC0xMS0yMFQwOTozNzo0NFoiLCJpc3N1ZXIiOiJkaWQ6d2ViOmlwcy5rOHMtY2x1c3Rlci50YW5nby5yaWQtaW50cmFzb2Z0LmV1OmRpZCIsInByb29mIjp7ImNyZWF0ZWQiOiIyMDI0LTExLTIwVDA5OjM3OjQ0WiIsImp3cyI6ImV5SmlOalFpT21aaGJITmxMQ0pqY21sMElqcGJJbUkyTkNKZExDSmhiR2NpT2lKUVV6STFOaUo5Li5qb1pyTmtMRDJtNVBWVWNTZkp3Tjlnb0YxUzZVcEhfR3lmNEJVTDA4NEtQeFA0NENrNzB0eEFoN0NfLWRsQllTZ0FYeEhod0xzRWVvaHpkb1BNV1M3Ql9hZ2pMZlpSUjY3SzFBR00zelI4VWtXeTRNc3cxMWlUa0FmRUlRNWtaSzZ6SHk4WlptaExJQTJPT1U2T3NTZU9neVR5bGNKU1ItWHlIWjQxZkNuX3haVl94RkZqNU1OWEhkRG95UENsV2MwdVk5MVlXT09DQU1rdGF6RkhVQTVrY29IejV5YThJSGtoc29FLUNRRFpWUFF6QmdjaGU0ZHdPTGhZdVdMQ3ZOMjkwVDNRRTlLSGY3Qno5OVRveS1qZVhpZEJYbER0NENlcWx4OEt1T0hYRzJITmxTdlRDTXVQXzdoYW5WWjRPSTVqbG5nXzRXWE1zREo1eEJpbHhUaEEiLCJ0eXBlIjoiSnNvbldlYlNpZ25hdHVyZTIwMjAiLCJ2ZXJpZmljYXRpb25NZXRob2QiOiJkaWQ6d2ViOmlwcy5rOHMtY2x1c3Rlci50YW5nby5yaWQtaW50cmFzb2Z0LmV1OmRpZCM4MTY0ZTdlYTE5NmE0NTdmODZiM2Y4ODdhYjY5MTYyYiJ9LCJ0eXBlIjpbIkVtcGxveWVlQ3JlZGVudGlhbCJdLCJ2YWxpZEZyb20iOiIyMDI0LTExLTIwVDA5OjM3OjQ0WiJ9fQ.2FQwqgwCrR1JF_23aYgP_CduY3TZD8TtIG7R0UkIbx2xdcpzybkZGMdzjaX1eyyPPer2dxRC-WeYq3n4Gvvh0g\"\n" +
						    "}";
					HttpHeaders headers = new HttpHeaders();
					headers.setContentType(MediaType.APPLICATION_JSON);

					HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
			
					ResponseEntity<String> responseEntity = restTemplate.exchange("/api/connector-access-token", HttpMethod.POST,
							requestEntity, String.class);

				
					token = responseEntity.getBody();

					assertNotNull(responseEntity.getBody());
					assertTrue(!responseEntity.getBody().isEmpty());
					assertEquals(responseEntity.getBody(), "Capability Token couldn't be issued, please revise the request and try again.\n");

				}
		    
			 @Order(8)
				@Test
				void testAuthEndpoint() {
					 System.out.println("Test 2 for New Policies Endpoint: Request access using the Long-Lived JWT. \n" );
						
						
						String requestBody =  "{\n" +
							    "    \"sub\": \"PAT\",\n" +
							    "    \"scope\": \"policies\"\n" +
							    "}";
						HttpHeaders headers = new HttpHeaders();
						headers.setContentType(MediaType.APPLICATION_JSON);
						headers.set("Authorization", "Bearer " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJUQU5HTyBQRVBcL1BEUCBDb21wb25lbnQiLCJzdWIiOiJUQU5HTyBSZXF1ZXN0ZXIiLCJyb2xlIjoidXNlciIsImV4cCI6MTc5NDY1MzA2MiwiaWF0IjoxNzMxNTgxMDYyfQ.T8i4DvJZnGJH9TxGuCdrOEvHlpmJaWVzSv91NMEywL1uqalaP2Zc3_sk4Y8grauGKs7VvxRrJZCcNcH1MLPd0mb4movU4gk3_N4satDeoE8iBAKy2u3mVw3rV-rKOHJsmqzxeQD8lOl9p5ibwfphobN-li7w1KQSbxyTst-PjJwl1Rq83aCrVpaRy_KdXFuqxsavfzpHKO9FMPNgit4qd3PV7QP32Fgek-u3ENY0aW7jhj99N0qoZj-8zkQ4pFqMWSqBWpCWj9grb0vT0mEIDjYnjPrBpVii9nLjsmmQfW2lZxoGdq-Goo9K0KZD9gT28gN8zrhEzKL1xqoJSlvNkA");
						HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
				
						ResponseEntity<String> responseEntity = restTemplate.exchange("/api/auth", HttpMethod.POST,
								requestEntity, String.class);

						// JWT Short-lived issued for requester
						shortLivedToken = responseEntity.getBody();

						// Verify 200 OK
						assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

						// Verify that there's a JWT Short Lived and is not null
						assertNotNull(responseEntity.getBody());
						assertNotEquals(responseEntity.getBody(), "The Authorization process couldn't be completed");
						assertTrue(!responseEntity.getBody().isEmpty());
				 }
				 
				 @Order(9)
					@Test
					void testNewPoliciesEndpoint() {
						 System.out.println("Test 3 for New Policies Endpoint: Request publishing new policy using the Short-Lived JWT issued. \n" );
							
							
						 String requestBody = "{\n" +
								    "    \"jwtAuth\": \"" + shortLivedToken + "\",\n" +
								    "    \"policy\": \"{\\\"name\\\":\\\"Tango User Information\\\",\\\"purpose\\\":\\\"Reveal role GOLD_COSTUMER of the user.\\\",\\\"serviceProvider\\\":\\\"did:serviceProvider:1\\\",\\\"accessRights\\\":[{\\\"action\\\":\\\"GET\\\",\\\"resource\\\":\\\"https://api-server.testing1.k8s-cluster.tango.rid-intrasoft.eu/resource/humidity\\\"}],\\\"constraints\\\":{\\\"fields\\\":[{\\\"path\\\":[\\\"$.roles.names\\\"],\\\"filter\\\":{\\\"type\\\":\\\"string\\\",\\\"pattern\\\":\\\"GOLD_COSTUMER\\\"}}]}}\",\n" +
								    "    \"resource\": \"https://api-server.testing1.k8s-cluster.tango.rid-intrasoft.eu/resource/humidity\"\n" +
								    "}";

							
							HttpHeaders headers = new HttpHeaders();
							headers.setContentType(MediaType.APPLICATION_JSON);
							HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
					
							ResponseEntity<String> responseEntity = restTemplate.exchange("/api/new-policy", HttpMethod.POST,
									requestEntity, String.class);

							

							// Verify 200 OK
							assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

							// Verify that there's a JWT Short Lived and is not null
							assertNotNull(responseEntity.getBody());
							assertTrue(!responseEntity.getBody().isEmpty());
					 }
				 
				 @Order(10)
					@Test
					void testRequestAccessEndpointPoliciesAdded() {
						System.out.println("Test 4 for New Policies Endpoint: Request access for doing action GET in the resource /humidity again but now the Capability Token is issued correctly. \n" );
						
						
						String requestBody =  "{\n" +
							    "    \"didSP\": \"Leo-Free-Home-Standing-1 Hand-Phone\",\n" +
							    "    \"sar\": {\n" +
							    "        \"action\":\"GET\",\n" +
							    "        \"resource\":\"https://api-server.testing1.k8s-cluster.tango.rid-intrasoft.eu/resource/humidity\"\n" +
							    "    },\n" +
							    "    \"didRequester\": \""+entityDid+"\",\n" +
							    "    \"accessToken\": \"eyJhbGciOiJFUzI1NiIsImtpZCI6IkN3YjlhMzNnVVE3Wmw0amlJZTI3alkwc1oweUd0ZXRVamxqdUFsb3RSWnciLCJ0eXAiOiJKV1QifQ.eyJhdWQiOlsicG9ydGFsLnRlc3RpbmcxLms4cy1jbHVzdGVyLnRhbmdvLnJpZC1pbnRyYXNvZnQuZXUiXSwiY2xpZW50X2lkIjoiZGlkOndlYjppcHMudGVzdGluZzEuazhzLWNsdXN0ZXIudGFuZ28ucmlkLWludHJhc29mdC5ldTpkaWQiLCJleHAiOjE3NDA1NjcxNDAsImlzcyI6ImRpZDp3ZWI6aXBzLnRlc3RpbmcxLms4cy1jbHVzdGVyLnRhbmdvLnJpZC1pbnRyYXNvZnQuZXU6ZGlkIiwia2lkIjoiQ3diOWEzM2dVUTdabDRqaUllMjdqWTBzWjB5R3RldFVqbGp1QWxvdFJadyIsInN1YiI6ImRpZDpteTp3YWxsZXQiLCJ2ZXJpZmlhYmxlQ3JlZGVudGlhbCI6eyJAY29udGV4dCI6WyJodHRwczovL3d3dy53My5vcmcvMjAxOC9jcmVkZW50aWFscy92MSIsImh0dHBzOi8vdzNpZC5vcmcvc2VjdXJpdHkvc3VpdGVzL2p3cy0yMDIwL3YxIl0sImNyZWRlbnRpYWxTY2hlbWEiOnsiaWQiOiJodHRwczovL3Jhdy5naXRodWJ1c2VyY29udGVudC5jb20vRklXQVJFLU9wcy90ZWNoLXgtY2hhbGxlbmdlL21haW4vc2NoZW1hLmpzb24iLCJ0eXBlIjoiRnVsbEpzb25TY2hlbWFWYWxpZGF0b3IyMDIxIn0sImNyZWRlbnRpYWxTdWJqZWN0Ijp7ImVtYWlsIjoic3RhbmRhcmQtZW1wbG95ZWVAaXBzLm9yZyIsImlkIjoiMGIxOWEzNDItN2I2NC00NjJmLWI5OTctYjkwZmZiYTlkMjgyIiwibGFzdE5hbWUiOiJJUFMiLCJyb2xlcyI6W3sibmFtZXMiOlsiR09MRF9DVVNUT01FUiJdLCJ0YXJnZXQiOiJkaWQ6d2ViOmlwcy5rOHMtY2x1c3Rlci50YW5nby5yaWQtaW50cmFzb2Z0LmV1OmRpZCJ9XSwidHlwZSI6Imd4Ok5hdHVyYWxQYXJ0aWNpcGFudCJ9LCJpZCI6InVybjp1dWlkOjhkMzdlNTA5LWNkNTctNGYwNS04YWIwLTY5YmM1NDNmMzE4MCIsImlzc3VhbmNlRGF0ZSI6IjIwMjQtMTEtMjBUMDk6Mzc6NDRaIiwiaXNzdWVkIjoiMjAyNC0xMS0yMFQwOTozNzo0NFoiLCJpc3N1ZXIiOiJkaWQ6d2ViOmlwcy5rOHMtY2x1c3Rlci50YW5nby5yaWQtaW50cmFzb2Z0LmV1OmRpZCIsInByb29mIjp7ImNyZWF0ZWQiOiIyMDI0LTExLTIwVDA5OjM3OjQ0WiIsImp3cyI6ImV5SmlOalFpT21aaGJITmxMQ0pqY21sMElqcGJJbUkyTkNKZExDSmhiR2NpT2lKUVV6STFOaUo5Li5qb1pyTmtMRDJtNVBWVWNTZkp3Tjlnb0YxUzZVcEhfR3lmNEJVTDA4NEtQeFA0NENrNzB0eEFoN0NfLWRsQllTZ0FYeEhod0xzRWVvaHpkb1BNV1M3Ql9hZ2pMZlpSUjY3SzFBR00zelI4VWtXeTRNc3cxMWlUa0FmRUlRNWtaSzZ6SHk4WlptaExJQTJPT1U2T3NTZU9neVR5bGNKU1ItWHlIWjQxZkNuX3haVl94RkZqNU1OWEhkRG95UENsV2MwdVk5MVlXT09DQU1rdGF6RkhVQTVrY29IejV5YThJSGtoc29FLUNRRFpWUFF6QmdjaGU0ZHdPTGhZdVdMQ3ZOMjkwVDNRRTlLSGY3Qno5OVRveS1qZVhpZEJYbER0NENlcWx4OEt1T0hYRzJITmxTdlRDTXVQXzdoYW5WWjRPSTVqbG5nXzRXWE1zREo1eEJpbHhUaEEiLCJ0eXBlIjoiSnNvbldlYlNpZ25hdHVyZTIwMjAiLCJ2ZXJpZmljYXRpb25NZXRob2QiOiJkaWQ6d2ViOmlwcy5rOHMtY2x1c3Rlci50YW5nby5yaWQtaW50cmFzb2Z0LmV1OmRpZCM4MTY0ZTdlYTE5NmE0NTdmODZiM2Y4ODdhYjY5MTYyYiJ9LCJ0eXBlIjpbIkVtcGxveWVlQ3JlZGVudGlhbCJdLCJ2YWxpZEZyb20iOiIyMDI0LTExLTIwVDA5OjM3OjQ0WiJ9fQ.2FQwqgwCrR1JF_23aYgP_CduY3TZD8TtIG7R0UkIbx2xdcpzybkZGMdzjaX1eyyPPer2dxRC-WeYq3n4Gvvh0g\"\n" +
							    "}";
						HttpHeaders headers = new HttpHeaders();
						headers.setContentType(MediaType.APPLICATION_JSON);

						HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
				
						ResponseEntity<String> responseEntity = restTemplate.exchange("/api/connector-access-token", HttpMethod.POST,
								requestEntity, String.class);

						// Token issued for requester
						token = responseEntity.getBody();

						// Verify 200 OK
						assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

						// Verify that there's a CapabilityToken and is not null
						assertNotNull(responseEntity.getBody());
						assertNotEquals(responseEntity.getBody(), "Capability Token couldn't be issued, please revise the request and try again.");

						assertTrue(!responseEntity.getBody().isEmpty());

					}
				 
				 @Order(11)
					@Test
					void testAccessWithTokenNewPolicies() {
						System.out.println("Test 5 for New Policies Endpoint: Doing action GET in the resource /humidity with the Capability Token received and policies added \n" );
						
						String requestBody = "{\n" +
						        "    \"ct\": " + token + ",\n" +
						        "     \"sar\":{\n" +
						        "        \"action\":\"GET\",\n" +
						        "        \"resource\":\"https://api-server.testing1.k8s-cluster.tango.rid-intrasoft.eu/resource/humidity\"\n" +
						        "    },\n" +
						        "     \"queryParameters\": {\n" +
						        "        \"id\": \"user123\",\n" +
						        "        \"role\": \"employee\"\n" +
						        "    }\n" +
						        "}";	
					
						HttpHeaders headers = new HttpHeaders();
						headers.setContentType(MediaType.APPLICATION_JSON);

						HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

						ResponseEntity<String> responseEntity = restTemplate.exchange("/api/access-with-token", HttpMethod.POST,
								requestEntity, String.class);

						// Verify 200 OK
						assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

						// Verify that it has been a successful validation
						assertNotEquals("ERROR: Validation failed. Capability Token is not valid. The requester couldn't access to the resource.\n", responseEntity.getBody());

					}
				 
				 @Order(12)
					@Test
					void testWrongPolicy() {
					 System.out.println("Test 6 for New Policies Endpoint: Request publishing new policy using a wrong policy \n" );
						
						
					 String requestBody = "{\n" +
							    "    \"jwtAuth\": \"" + shortLivedToken + "\",\n" +
							    "    \"policy\": \"{\\\"name\\\":\\\"Tango User Information\\\",\\\"purpose\\\":\\\"Reveal role GOLD_COSTUMER of the user.\\\",\\\"serviceProvider\\\":\\\"did:serviceProvider:1\\\",\\\"constraints\\\":{\\\"fields\\\":[{\\\"path\\\":[\\\"$.roles.names\\\"],\\\"filter\\\":{\\\"type\\\":\\\"string\\\",\\\"pattern\\\":\\\"GOLD_COSTUMER\\\"}}]}}\",\n" +
							    "    \"resource\": \"https://api-server.testing1.k8s-cluster.tango.rid-intrasoft.eu/resource/temperature\"\n" +
							    "}";

						HttpHeaders headers = new HttpHeaders();
						headers.setContentType(MediaType.APPLICATION_JSON);
						HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
				
						ResponseEntity<String> responseEntity = restTemplate.exchange("/api/new-policy", HttpMethod.POST,
								requestEntity, String.class);

						// Verify 400 Bad Request 
						assertTrue(responseEntity.getStatusCode().is4xxClientError());

					 }
				 
				 
				 @Order(13)
					@Test
					void testWrongAuthEndpoint() {
						 System.out.println("Test 7 for New Policies Endpoint: Request access using a wrong Long-Lived JWT \n" );
							
							
							String requestBody =  "{\n" +
								    "    \"sub\": \"PAT\",\n" +
								    "    \"scope\": \"/api/new-policy\"\n" +
								    "}";
							HttpHeaders headers = new HttpHeaders();
							headers.setContentType(MediaType.APPLICATION_JSON);
							headers.set("Authorization", "Bearer " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJUQU5HTyBQRVBcL1BEUCBDb21wb25lbnQiLCJzdWIiOiJUQU5HTyBSZXF1ZXN0ZXIiLCJyb2xlIjoidXNlciIsImV4cCI6MTc5NDY1MzA2MiwiaWF0IjoxNzMxNTgxMDYyfQ.T8i4DvJZnGJH9TxGuCdrOEvHlpmJaWVzSv91NMEywL1uqalaP2Zc3_sk4Y8grauGKs7VvxRrJZCcNcH1MLPd0mb4movU4gk3_N4satDeoE8iBAKy2u3mVw3rV-rKOHJsmqzxeQD8lOl9p5ibwfphobN-li7w1KQSbxyTst-PjJwl1Rq83aCrVpaRy_KdXFuqxsavfzpHKO9FMPNgit4qd3PV7QP32Fgek-u3ENY0aW7jhj99N");
							HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
					
							ResponseEntity<String> responseEntity = restTemplate.exchange("/api/auth", HttpMethod.POST,
									requestEntity, String.class);

					
							// Verify the authorization process wasn't completed
							assertEquals( responseEntity.getBody(),"The Authorization process couldn't be completed");
							
					 }
				 
				 @Order(14)
					@Test
					void testWrongShortLivedJWT() {
						 System.out.println("Test 8 for New Policies Endpoint: Request publishing new policy using a wrong Short-Lived JWT  \n" );
							
							
						 String requestBody = "{\n" +
								    "    \"jwtAuth\": \"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJUQU5HTyBQRVBcL1BEUCBDb21wb25lbnQiLCJzdWIiOiJQQVQiLCJleHAiOjE3MzE1ODg2NzMsImlhdCI6MTczMTU4NTA3Mywic2NvcGUiOiJwb2xpY2llcyJ9.qiQAQuCyaOeuoUj0DjwXimTIAWlOp-IolIGHhdAhglnhdRQz8SyviGoqevI0pKf8b_UCOJhx6evU9iZEC0wshivJiY6A3PERuWhgGcLWVuY3xsmSZs4gO4b9eHXG8E_COK18h8tkNpycXVzqnPqWZKf9pweQtgw3o5VjXoFvkC8BWL_Gni3ZejiWsYGVm3yx935coyE7vtAZgw_zhPEje3YF494yXAUsher7JbAaFmve75wTVNroEO5_TdW8sp3UU7F\",\n" +
								    "    \"policy\": \"{\\\"id\\\":\\\"123\\\",\\\"name\\\":\\\"User Information\\\",\\\"purpose\\\":\\\"Reveal email of the user.\\\",\\\"serviceProvider\\\":\\\"did:ServiceProvider:1\\\",\\\"accessRights\\\":[{\\\"action\\\":\\\"GET\\\",\\\"resource\\\":\\\"https://api-server.testing1.k8s-cluster.tango.rid-intrasoft.eu/resource/temperature\\\"}],\\\"authTime\\\":1642058400,\\\"minTrustScore\\\":0.5,\\\"constraints\\\":{\\\"fields\\\":[{\\\"path\\\":[\\\"$.email\\\"]}]}}\",\n" +
								    "    \"resource\": \"https://api-server.testing1.k8s-cluster.tango.rid-intrasoft.eu/resource/temperature\"\n" +
								    "}";

							
							HttpHeaders headers = new HttpHeaders();
							headers.setContentType(MediaType.APPLICATION_JSON);
							HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
					
							ResponseEntity<String> responseEntity = restTemplate.exchange("/api/new-policy", HttpMethod.POST,
									requestEntity, String.class);

						

							// Verify 403 Forbidden
							assertTrue(responseEntity.getStatusCode().is4xxClientError());

					 }
				 
		    
		    
		    
		 @Order(15)
		@Test
		void testRequestAccessEndpoint() {
			System.out.println("Test 1 for PEP/PDP resources: Request access for doing action GET in the resource /temperature and getting the Capability Token \n" );
			
			
			String requestBody =  "{\n" +
				    "    \"didSP\": \"Leo-Free-Home-Standing-1 Hand-Phone\",\n" +
				    "    \"sar\": {\n" +
				    "        \"action\":\"GET\",\n" +
				    "        \"resource\":\"https://api-server.testing1.k8s-cluster.tango.rid-intrasoft.eu/resource/temperature\"\n" +
				    "    },\n" +
				    "    \"didRequester\": \""+entityDid+"\",\n" +
				    "    \"accessToken\": \"eyJhbGciOiJFUzI1NiIsImtpZCI6IkN3YjlhMzNnVVE3Wmw0amlJZTI3alkwc1oweUd0ZXRVamxqdUFsb3RSWnciLCJ0eXAiOiJKV1QifQ.eyJhdWQiOlsicG9ydGFsLnRlc3RpbmcxLms4cy1jbHVzdGVyLnRhbmdvLnJpZC1pbnRyYXNvZnQuZXUiXSwiY2xpZW50X2lkIjoiZGlkOndlYjppcHMudGVzdGluZzEuazhzLWNsdXN0ZXIudGFuZ28ucmlkLWludHJhc29mdC5ldTpkaWQiLCJleHAiOjE3NDA1NjcxNDAsImlzcyI6ImRpZDp3ZWI6aXBzLnRlc3RpbmcxLms4cy1jbHVzdGVyLnRhbmdvLnJpZC1pbnRyYXNvZnQuZXU6ZGlkIiwia2lkIjoiQ3diOWEzM2dVUTdabDRqaUllMjdqWTBzWjB5R3RldFVqbGp1QWxvdFJadyIsInN1YiI6ImRpZDpteTp3YWxsZXQiLCJ2ZXJpZmlhYmxlQ3JlZGVudGlhbCI6eyJAY29udGV4dCI6WyJodHRwczovL3d3dy53My5vcmcvMjAxOC9jcmVkZW50aWFscy92MSIsImh0dHBzOi8vdzNpZC5vcmcvc2VjdXJpdHkvc3VpdGVzL2p3cy0yMDIwL3YxIl0sImNyZWRlbnRpYWxTY2hlbWEiOnsiaWQiOiJodHRwczovL3Jhdy5naXRodWJ1c2VyY29udGVudC5jb20vRklXQVJFLU9wcy90ZWNoLXgtY2hhbGxlbmdlL21haW4vc2NoZW1hLmpzb24iLCJ0eXBlIjoiRnVsbEpzb25TY2hlbWFWYWxpZGF0b3IyMDIxIn0sImNyZWRlbnRpYWxTdWJqZWN0Ijp7ImVtYWlsIjoic3RhbmRhcmQtZW1wbG95ZWVAaXBzLm9yZyIsImlkIjoiMGIxOWEzNDItN2I2NC00NjJmLWI5OTctYjkwZmZiYTlkMjgyIiwibGFzdE5hbWUiOiJJUFMiLCJyb2xlcyI6W3sibmFtZXMiOlsiR09MRF9DVVNUT01FUiJdLCJ0YXJnZXQiOiJkaWQ6d2ViOmlwcy5rOHMtY2x1c3Rlci50YW5nby5yaWQtaW50cmFzb2Z0LmV1OmRpZCJ9XSwidHlwZSI6Imd4Ok5hdHVyYWxQYXJ0aWNpcGFudCJ9LCJpZCI6InVybjp1dWlkOjhkMzdlNTA5LWNkNTctNGYwNS04YWIwLTY5YmM1NDNmMzE4MCIsImlzc3VhbmNlRGF0ZSI6IjIwMjQtMTEtMjBUMDk6Mzc6NDRaIiwiaXNzdWVkIjoiMjAyNC0xMS0yMFQwOTozNzo0NFoiLCJpc3N1ZXIiOiJkaWQ6d2ViOmlwcy5rOHMtY2x1c3Rlci50YW5nby5yaWQtaW50cmFzb2Z0LmV1OmRpZCIsInByb29mIjp7ImNyZWF0ZWQiOiIyMDI0LTExLTIwVDA5OjM3OjQ0WiIsImp3cyI6ImV5SmlOalFpT21aaGJITmxMQ0pqY21sMElqcGJJbUkyTkNKZExDSmhiR2NpT2lKUVV6STFOaUo5Li5qb1pyTmtMRDJtNVBWVWNTZkp3Tjlnb0YxUzZVcEhfR3lmNEJVTDA4NEtQeFA0NENrNzB0eEFoN0NfLWRsQllTZ0FYeEhod0xzRWVvaHpkb1BNV1M3Ql9hZ2pMZlpSUjY3SzFBR00zelI4VWtXeTRNc3cxMWlUa0FmRUlRNWtaSzZ6SHk4WlptaExJQTJPT1U2T3NTZU9neVR5bGNKU1ItWHlIWjQxZkNuX3haVl94RkZqNU1OWEhkRG95UENsV2MwdVk5MVlXT09DQU1rdGF6RkhVQTVrY29IejV5YThJSGtoc29FLUNRRFpWUFF6QmdjaGU0ZHdPTGhZdVdMQ3ZOMjkwVDNRRTlLSGY3Qno5OVRveS1qZVhpZEJYbER0NENlcWx4OEt1T0hYRzJITmxTdlRDTXVQXzdoYW5WWjRPSTVqbG5nXzRXWE1zREo1eEJpbHhUaEEiLCJ0eXBlIjoiSnNvbldlYlNpZ25hdHVyZTIwMjAiLCJ2ZXJpZmljYXRpb25NZXRob2QiOiJkaWQ6d2ViOmlwcy5rOHMtY2x1c3Rlci50YW5nby5yaWQtaW50cmFzb2Z0LmV1OmRpZCM4MTY0ZTdlYTE5NmE0NTdmODZiM2Y4ODdhYjY5MTYyYiJ9LCJ0eXBlIjpbIkVtcGxveWVlQ3JlZGVudGlhbCJdLCJ2YWxpZEZyb20iOiIyMDI0LTExLTIwVDA5OjM3OjQ0WiJ9fQ.2FQwqgwCrR1JF_23aYgP_CduY3TZD8TtIG7R0UkIbx2xdcpzybkZGMdzjaX1eyyPPer2dxRC-WeYq3n4Gvvh0g\"\n" +
				    "}";
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
	
			ResponseEntity<String> responseEntity = restTemplate.exchange("/api/connector-access-token", HttpMethod.POST,
					requestEntity, String.class);

			// Token issued for requester
			token = responseEntity.getBody();

			// Verify 200 OK
			assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

			// Verify that there's a CapabilityToken and is not null
			assertNotNull(responseEntity.getBody());
			assertTrue(!responseEntity.getBody().isEmpty());

		}
		 @Order(16)
		@Test
		void testAccessWithToken() {
			System.out.println("Test 2 for PEP/PDP resources: Doing action GET in the resource /temperature with the Capability Token received \n" );
			
			String requestBody = "{\n" +
			        "    \"ct\": " + token + ",\n" +
			        "     \"sar\":{\n" +
			        "        \"action\":\"GET\",\n" +
			        "        \"resource\":\"https://api-server.testing1.k8s-cluster.tango.rid-intrasoft.eu/resource/temperature\"\n" +
			        "    },\n" +
			        "     \"queryParameters\": {\n" +
			        "        \"id\": \"user123\",\n" +
			        "        \"role\": \"employee\"\n" +
			        "    }\n" +
			        "}";	
		
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

			ResponseEntity<String> responseEntity = restTemplate.exchange("/api/access-with-token", HttpMethod.POST,
					requestEntity, String.class);

			// Verify 200 OK
			assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

			// Verify that it has been a successful validation
			assertNotEquals("ERROR: Validation failed. Capability Token is not valid. The requester couldn't access to the resource.\n", responseEntity.getBody());

		}
		 @Order(17)
		@Test
		void testAccessWithWrongToken() {
			System.out.println("Test 3 for TANGO: Trying to do action POST in the resource /temperature with the Capability Token received \n" );
			

			String requestBody = "{\n" + "    \"ct\": " + token + ",\n" + "     \"sar\":{\n"
					+ "        \"action\":\"POST\",\n" + "        \"resource\":\"https://api-server.testing1.k8s-cluster.tango.rid-intrasoft.eu/resource/temperature\"\n" + "    },\n"+"     \"queryParameters\": {\n	\"id\": \"user123\",\n"	
			        + "	\"role\": \"employee\"\n"
			        + "	}\n "+ "}";

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

			ResponseEntity<String> responseEntity = restTemplate.exchange("/api/access-with-token", HttpMethod.POST,
					requestEntity, String.class);

		
			// Verify that it has been a successful validation
			assertEquals("ERROR: Validation failed. Capability Token is not valid. The requester couldn't access to the resource.\n", responseEntity.getBody());

		}
		 @Order(18)
			@Test
			void testNotMatchingPolicy() {
				System.out.println("Test 4 for TANGO: Request access for doing action GET in the resource /temperature and not getting the Capability Token because the token is expired and the policy matching failed \n" );
				
				
				String requestBody =  "{\n" +
					    "    \"didSP\": \"Leo-Free-Home-Standing-1 Hand-Phone\",\n" +
					    "    \"sar\": {\n" +
					    "        \"action\":\"GET\",\n" +
					    "        \"resource\":\"https://api-server.testing1.k8s-cluster.tango.rid-intrasoft.eu/resource/temperature\"\n" +
					    "    },\n" +
					    "    \"didRequester\": \""+entityDid+"\",\n" +
					    "    \"accessToken\": \"eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOlsiMTAuNjQuNDUuNTg6NTAwMCJdLCJjbGllbnRfaWQiOiJkaWQ6d2ViOmlwcy5kc2JhLmF3cy5uaXRsYWIuaW86ZGlkIiwiZXhwIjoxNzA3ODM2MDU1LCJpc3MiOiJkaWQ6d2ViOmlwcy5kc2JhLmF3cy5uaXRsYWIuaW86ZGlkIiwia2lkIjoiT1c4V0pwMlFES3JGV1Q1Wk9nYjNZZ3Z5dE1UZHpFYUw1T3dubkxaRVNVQSIsInN1YiI6ImRpZDpteTp3YWxsZXQiLCJ2ZXJpZmlhYmxlQ3JlZGVudGlhbCI6eyJAY29udGV4dCI6WyJodHRwczovL3d3dy53My5vcmcvMjAxOC9jcmVkZW50aWFscy92MSIsImh0dHBzOi8vdzNpZC5vcmcvc2VjdXJpdHkvc3VpdGVzL2p3cy0yMDIwL3YxIl0sImNyZWRlbnRpYWxTY2hlbWEiOnsiaWQiOiJodHRwczovL3Jhdy5naXRodWJ1c2VyY29udGVudC5jb20vRklXQVJFLU9wcy90ZWNoLXgtY2hhbGxlbmdlL21haW4vc2NoZW1hLmpzb24iLCJ0eXBlIjoiRnVsbEpzb25TY2hlbWFWYWxpZGF0b3IyMDIxIn0sImNyZWRlbnRpYWxTdWJqZWN0Ijp7ImVtYWlsIjoic3RhbmRhcmQtZW1wbG95ZWVAaXBzLm9yZyIsImlkIjoiODJmZDA0MDUtNGFmMC00ZjM5LWEyNzQtOGU4MjM3NGY0N2I1Iiwicm9sZXMiOlt7Im5hbWVzIjpbIkdPTERfQ1VTVE9NRVIiXSwidGFyZ2V0IjoiZGlkOndlYjppcHMuZHNiYS5hd3Mubml0bGFiLmlvOmRpZCJ9XSwidHlwZSI6Imd4Ok5hdHVyYWxQYXJ0aWNpcGFudCJ9LCJpZCI6InVybjp1dWlkOjk1ZjdkMGE4LWI5YjYtNGViMC04MDY2LWQ1MGI5NmU3ODY5MyIsImlzc3VhbmNlRGF0ZSI6IjIwMjQtMDItMTNUMTQ6MDY6NTJaIiwiaXNzdWVkIjoiMjAyNC0wMi0xM1QxNDowNjo1MloiLCJpc3N1ZXIiOiJkaWQ6d2ViOmlwcy5kc2JhLmF3cy5uaXRsYWIuaW86ZGlkIiwicHJvb2YiOnsiY3JlYXRlZCI6IjIwMjQtMDItMTNUMTQ6MDY6NTJaIiwiandzIjoiZXlKaU5qUWlPbVpoYkhObExDSmpjbWwwSWpwYkltSTJOQ0pkTENKaGJHY2lPaUpRVXpJMU5pSjkuLm56QXc5WTNNMDB1Q0RtbnprRFVLXzJHQ0lORjRBT2JiZmpDMFllQkhOME5oTmhEQ19sWTB2T2FVRm5DRGhmQmpINzhNa1pjb2g4Y2FmLWFxM3FkWGl0Q0tlQ25vTkEta3VyNGszWDZldDNSVkJCUkFKdi1yVkV2R1Zqa3BMY1VfWHVzdmY4Tmc1SzlPMURJREhzSThUdmQydVhUblhHN2VKSC04M2Z0Q2dFX2M2dW5RUC0zbkdYQXZJYkpkMXhDUG04MVJVNHJZM0QtVTItLUM5WG84S2kyemxzTUlRRzVSZUpuVzRtNnZjRUM4emttZ0JHVEZRNzJIak1mUHVLN0I1dUp3SHV4V01KVkVWTkN4T2Q3cUxWWDBBdjdTaWQ2Q2dVaXUxTVo5emJTeVJ1RXQ3a3QxWGVUTUZpZDBMeDQyMjJ4cnVsZ2NVUGxzMEhIY3IyTEE5OHFfZXptX2NmS1pkVE9qRUViYWc3Vl9QZTI1bG41UkcwWmZzRGJNNVlQRUp5czQwaTBnMndJQVhFVVVuQmNaUTJoeUp4T2pYWjFQbTRrZXFtMnRBTUF2bVJwN2ppM1Q4SXpSTUFPTU9kUUhSbUxNRDVuYnQ4X2NpTmJSa1VLQ25wcXI5UUF4UmtDRGtLOWdwdEczSFdwMnZFcDc1c2VoY21fQ1RPTWdUU0cyelJDQW9xOWJkbTQzeG5qQWpTcnJObVhyZkZFbkJsRmN0eE05aHpETTFMTDRwR3NWdU1SSFhSb1JWSDhPUW5uM0J1TFVkZjVFUHhRbGU3X2pwdHpIMUpwbFgxUTJpX0Jwb0FVUlgxSmZ1TGs1YlRfYW5LS05nLVF6azBqMjZTRE1Obk05dEJvNGV1WXJFSEFfVlVyWG5lcUpuYTE3VWpzdkdJVDMxV2lLVmZrIiwidHlwZSI6Ikpzb25XZWJTaWduYXR1cmUyMDIwIiwidmVyaWZpY2F0aW9uTWV0aG9kIjoiZGlkOndlYjppcHMuZHNiYS5hd3Mubml0bGFiLmlvOmRpZCMzODllMTkzZWMyOWU0NDg1YTI5YzlmMmEzM2NkNDQ2MCJ9LCJ0eXBlIjpbIkVtcGxveWVlQ3JlZGVudGlhbCJdLCJ2YWxpZEZyb20iOiIyMDI0LTAyLTEzVDE0OjA2OjUyWiJ9fQ.HYxxjSc15-16VbVT7reiL5TuJTo4ZzrWBRPmamkgLLU\"\n" +
					    "}";
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);

				HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
		
				ResponseEntity<String> responseEntity = restTemplate.exchange("/api/connector-access-token", HttpMethod.POST,
						requestEntity, String.class);

				// Token issued for requester
				token = responseEntity.getBody();

				// Verify that there's a CapabilityToken and is not null
				assertNotNull(responseEntity.getBody());
				assertTrue(!responseEntity.getBody().isEmpty());
				assertEquals("Capability Token couldn't be issued, please revise the request and try again.\n", responseEntity.getBody());
			}
		 
		 @Order(19)
			@Test
			void testNotVerifyingSignature() {
				System.out.println("Test 5 for TANGO: Request access for doing action GET in the resource /temperature and not getting the Capability Token because access token signature couldn't be verified \n" );
				
				
				String requestBody =  "{\n" +
					    "    \"didSP\": \"Leo-Free-Home-Standing-1 Hand-Phone\",\n" +
					    "    \"sar\": {\n" +
					    "        \"action\":\"GET\",\n" +
					    "        \"resource\":\"https://api-server.testing1.k8s-cluster.tango.rid-intrasoft.eu/resource/temperature\"\n" +
					    "    },\n" +
					    "    \"didRequester\": \""+entityDid+"\",\n" +
					    "    \"accessToken\": \"eyJhbGciOiJFUzI1NiIsImtpZCI6IkN3YjlhMzNnVVE3Wmw0amlJZTI3alkwc1oweUd0ZXRVamxqdUFsb3RSWnciLCJ0eXAiOiJKV1QifQ.eyJhdWQiOlsicG9ydGFsLnRlc3RpbmcxLms4cy1jbHVzdGVyLnRhbmdvLnJpZC1pbnRyYXNvZnQuZXUiXSwiY2xpZW50X2lkIjoiZGlkOndlYjppcHMudGVzdGluZzEuazhzLWNsdXN0ZXIudGFuZ28ucmlkLWludHJhc29mdC5ldTpkaWQiLCJleHAiOjE3MjY2NzE0NzIsImlzcyI6ImRpZDp3ZWI6aXBzLnRlc3RpbmcxLms4cy1jbHVzdGVyLnRhbmdvLnJpZC1pbnRyYXNvZnQuZXU6ZGlkIiwia2lkIjoiQ3diOWEzM2dVUTdabDRqaUllMjdqWTBzWjB5R3RldFVqbGp1QWxvdFJadyIsInN1YiI6ImRpZDpteTp3YWxsZXQiLCJ2ZXJpZmlhYmxlQ3JlZGVudGlhbCI6eyJAY29udGV4dCI6WyJodHRwczovL3d3dy53My5vcmcvMjAxOC9jcmVkZW50aWFscy92MSIsImh0dHBzOi8vdzNpZC5vcmcvc2VjdXJpdHkvc3VpdGVzL2p3cy0yMDIwL3YxIl0sImNyZWRlbnRpYWxTY2hlbWEiOnsiaWQiOiJodHRwczovL3Jhdy5naXRodWJ1c2VyY29udGVudC5jb20vRklXQVJFLU9wcy90ZWNoLXgtY2hhbGxlbmdlL21haW4vc2NoZW1hLmpzb24iLCJ0eXBlIjoiRnVsbEpzb25TY2hlbWFWYWxpZGF0b3IyMDIxIn0sImNyZWRlbnRpYWxTdWJqZWN0Ijp7ImVtYWlsIjoic3RhbmRhcmQtZW1wbG95ZWVAaXBzLm9yZyIsImlkIjoiMGU3ZGE3NGUtYTFmYi00YmY2LTkzZTYtOWM4ZWUzYzYzZDlhIiwibGFzdE5hbWUiOiJJUFMiLCJyb2xlcyI6W3sibmFtZXMiOlsiR09MRF9DVVNUT01FUiJdLCJ0YXJnZXQiOiJkaWQ6d2ViOmlwcy5rOHMtY2x1c3Rlci50YW5nby5yaWQtaW50cmFzb2Z0LmV1OmRpZCJ9XSwidHlwZSI6Imd4Ok5hdHVyYWxQYXJ0aWNpcGFudCJ9LCJpZCI6InVybjp1dWlkOjllZWY3ODYxLTJjNjUtNDE0My1hZDc0LTg5NjQ0YzdiNTkwMyIsImlzc3VhbmNlRGF0ZSI6IjIwMjQtMDktMThUMTQ6MjY6MjRaIiwiaXNzdWVkIjoiMjAyNC0wOS0xOFQxNDoyNjoyNFoiLCJpc3N1ZXIiOiJkaWQ6d2ViOmlwcy5rOHMtY2x1c3Rlci50YW5nby5yaWQtaW50cmFzb2Z0LmV1OmRpZCIsInByb29mIjp7ImNyZWF0ZWQiOiIyMDI0LTA5LTE4VDE0OjI2OjI0WiIsImp3cyI6ImV5SmlOalFpT21aaGJITmxMQ0pqY21sMElqcGJJbUkyTkNKZExDSmhiR2NpT2lKUVV6STFOaUo5Li5EclZUS1l3blRySnIxUGs1cUpqdW5BM0g5ZFNCWS1pSy1nNThnWXBGUDlzRF9OVWJ5QmNMekMtdXpaamNEaVFtR00zRW9UYWlpOTQ3V21TdWo5V3hQZTcyRXJxRnFqSHBaN3ZYd1RrN3pzX3duWXphZTdZZEZ6ekJLcnpmRTNOc09TclZZQ1lUSm5pdnVsSkI3ME1Ca1U2eEUtTGcwZzk4QzRqQ3RIT2dac1BOOHEyRDRBbnRyOVhXb0s3ME02Rnl4UmhLVDJhT2lRYVIyMG4zLVA4blNHZ0tCX2RNd0tNOGlsbE1PdTFaU292NlpkWlRoU1pHczRHbUdRazNQSTNlc2pfVzItYjNwWDAxYTdYT2doQXpIckpuRzZ4dTRpRDhrX21DREVxN1VjaWJtYUtJZkgzUDNBZ3c5YUF1QkxYbVJxOXI2WFc0bEhoR1JmTjBtUGotcWciLCJ0eXBlIjoiSnNvbldlYlNpZ25hdHVyZTIwMjAiLCJ2ZXJpZmljYXRpb25NZXRob2QiOiJkaWQ6d2ViOmlwcy5rOHMtY2x1c3Rlci50YW5nby5yaWQtaW50cmFzb2Z0LmV1OmRpZCM4MTY0ZTdlYTE5NmE0NTdmODZiM2Y4ODdhYjY5MTYyYiJ9LCJ0eXBlIjpbIkVtcGxveWVlQ3JlZGVudGlhbCJdLCJ2YWxpZEZyb20iOiIyMDI0LTA5LTE4VDE0OjI2OjI0WiJ9fQ.nosignature\"\n" +
					    "}";
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);

				HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
		
				ResponseEntity<String> responseEntity = restTemplate.exchange("/api/connector-access-token", HttpMethod.POST,
						requestEntity, String.class);

				// Token issued for requester
				token = responseEntity.getBody();

				// Verify that there's a CapabilityToken and is not null
				assertNotNull(responseEntity.getBody());
				assertTrue(!responseEntity.getBody().isEmpty());
				assertEquals("Capability Token couldn't be issued, please revise the request and try again.\n", responseEntity.getBody());
			}
		 
		 @Order(20)
			@Test
			void testRequestAccessEndpointPost() {
				System.out.println("Test 6 for TANGO: Request access for doing action POST in the resource /temperature and getting the Capability Token \n" );
				
				
				String requestBody =  "{\n" +
					    "    \"didSP\": \"Leo-Free-Home-Standing-1 Hand-Phone\",\n" +
					    "    \"sar\": {\n" +
					    "        \"action\":\"POST\",\n" +
					    "        \"resource\":\"https://api-server.testing1.k8s-cluster.tango.rid-intrasoft.eu/resource/temperature\"\n" +
					    "    },\n" +
					    "    \"didRequester\": \""+entityDid+"\",\n" +
					    "    \"accessToken\": \"eyJhbGciOiJFUzI1NiIsImtpZCI6IkN3YjlhMzNnVVE3Wmw0amlJZTI3alkwc1oweUd0ZXRVamxqdUFsb3RSWnciLCJ0eXAiOiJKV1QifQ.eyJhdWQiOlsicG9ydGFsLnRlc3RpbmcxLms4cy1jbHVzdGVyLnRhbmdvLnJpZC1pbnRyYXNvZnQuZXUiXSwiY2xpZW50X2lkIjoiZGlkOndlYjppcHMudGVzdGluZzEuazhzLWNsdXN0ZXIudGFuZ28ucmlkLWludHJhc29mdC5ldTpkaWQiLCJleHAiOjE3NDA1NjcxNDAsImlzcyI6ImRpZDp3ZWI6aXBzLnRlc3RpbmcxLms4cy1jbHVzdGVyLnRhbmdvLnJpZC1pbnRyYXNvZnQuZXU6ZGlkIiwia2lkIjoiQ3diOWEzM2dVUTdabDRqaUllMjdqWTBzWjB5R3RldFVqbGp1QWxvdFJadyIsInN1YiI6ImRpZDpteTp3YWxsZXQiLCJ2ZXJpZmlhYmxlQ3JlZGVudGlhbCI6eyJAY29udGV4dCI6WyJodHRwczovL3d3dy53My5vcmcvMjAxOC9jcmVkZW50aWFscy92MSIsImh0dHBzOi8vdzNpZC5vcmcvc2VjdXJpdHkvc3VpdGVzL2p3cy0yMDIwL3YxIl0sImNyZWRlbnRpYWxTY2hlbWEiOnsiaWQiOiJodHRwczovL3Jhdy5naXRodWJ1c2VyY29udGVudC5jb20vRklXQVJFLU9wcy90ZWNoLXgtY2hhbGxlbmdlL21haW4vc2NoZW1hLmpzb24iLCJ0eXBlIjoiRnVsbEpzb25TY2hlbWFWYWxpZGF0b3IyMDIxIn0sImNyZWRlbnRpYWxTdWJqZWN0Ijp7ImVtYWlsIjoic3RhbmRhcmQtZW1wbG95ZWVAaXBzLm9yZyIsImlkIjoiMGIxOWEzNDItN2I2NC00NjJmLWI5OTctYjkwZmZiYTlkMjgyIiwibGFzdE5hbWUiOiJJUFMiLCJyb2xlcyI6W3sibmFtZXMiOlsiR09MRF9DVVNUT01FUiJdLCJ0YXJnZXQiOiJkaWQ6d2ViOmlwcy5rOHMtY2x1c3Rlci50YW5nby5yaWQtaW50cmFzb2Z0LmV1OmRpZCJ9XSwidHlwZSI6Imd4Ok5hdHVyYWxQYXJ0aWNpcGFudCJ9LCJpZCI6InVybjp1dWlkOjhkMzdlNTA5LWNkNTctNGYwNS04YWIwLTY5YmM1NDNmMzE4MCIsImlzc3VhbmNlRGF0ZSI6IjIwMjQtMTEtMjBUMDk6Mzc6NDRaIiwiaXNzdWVkIjoiMjAyNC0xMS0yMFQwOTozNzo0NFoiLCJpc3N1ZXIiOiJkaWQ6d2ViOmlwcy5rOHMtY2x1c3Rlci50YW5nby5yaWQtaW50cmFzb2Z0LmV1OmRpZCIsInByb29mIjp7ImNyZWF0ZWQiOiIyMDI0LTExLTIwVDA5OjM3OjQ0WiIsImp3cyI6ImV5SmlOalFpT21aaGJITmxMQ0pqY21sMElqcGJJbUkyTkNKZExDSmhiR2NpT2lKUVV6STFOaUo5Li5qb1pyTmtMRDJtNVBWVWNTZkp3Tjlnb0YxUzZVcEhfR3lmNEJVTDA4NEtQeFA0NENrNzB0eEFoN0NfLWRsQllTZ0FYeEhod0xzRWVvaHpkb1BNV1M3Ql9hZ2pMZlpSUjY3SzFBR00zelI4VWtXeTRNc3cxMWlUa0FmRUlRNWtaSzZ6SHk4WlptaExJQTJPT1U2T3NTZU9neVR5bGNKU1ItWHlIWjQxZkNuX3haVl94RkZqNU1OWEhkRG95UENsV2MwdVk5MVlXT09DQU1rdGF6RkhVQTVrY29IejV5YThJSGtoc29FLUNRRFpWUFF6QmdjaGU0ZHdPTGhZdVdMQ3ZOMjkwVDNRRTlLSGY3Qno5OVRveS1qZVhpZEJYbER0NENlcWx4OEt1T0hYRzJITmxTdlRDTXVQXzdoYW5WWjRPSTVqbG5nXzRXWE1zREo1eEJpbHhUaEEiLCJ0eXBlIjoiSnNvbldlYlNpZ25hdHVyZTIwMjAiLCJ2ZXJpZmljYXRpb25NZXRob2QiOiJkaWQ6d2ViOmlwcy5rOHMtY2x1c3Rlci50YW5nby5yaWQtaW50cmFzb2Z0LmV1OmRpZCM4MTY0ZTdlYTE5NmE0NTdmODZiM2Y4ODdhYjY5MTYyYiJ9LCJ0eXBlIjpbIkVtcGxveWVlQ3JlZGVudGlhbCJdLCJ2YWxpZEZyb20iOiIyMDI0LTExLTIwVDA5OjM3OjQ0WiJ9fQ.2FQwqgwCrR1JF_23aYgP_CduY3TZD8TtIG7R0UkIbx2xdcpzybkZGMdzjaX1eyyPPer2dxRC-WeYq3n4Gvvh0g\"\n" +
					    "}";
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);

				HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
		
				ResponseEntity<String> responseEntity = restTemplate.exchange("/api/connector-access-token", HttpMethod.POST,
						requestEntity, String.class);

				// Token issued for requester
				token = responseEntity.getBody();

				// Verify 200 OK
				assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

				// Verify that there's a CapabilityToken and is not null
				assertNotNull(responseEntity.getBody());
				assertTrue(!responseEntity.getBody().isEmpty());

			}
		 
		 @Order(21)
			@Test
			void testAccessWithTokenPost() {
				System.out.println("Test 7 for TANGO: Doing action POST in the resource /temperature with the Capability Token received \n" );
				
				String requestBody = "{\n" +
				        "    \"ct\": " + token + ",\n" +
				        "    \"sar\": {\n" +
				        "        \"action\": \"POST\",\n" +
				        "        \"resource\": \"https://api-server.testing1.k8s-cluster.tango.rid-intrasoft.eu/resource/temperature\"\n" +
				        "    },\n" +
				        "    \"queryParameters\": {\n" +
				        "        \"id\": \"user456\",\n" +
				        "        \"role\": \"leader\"\n" +
				        "    },\n" +
				        "    \"jsonBody\": {\n" +
				        "        \"sensor\": \"sensorTests1\",\n" +
				        "        \"unit\": \"Celsius\",\n" +
				        "        \"measure\": \"temperature\",\n" +
				        "        \"values\": [\n" +
				        "            {\"value\": 21, \"timestamp\": \"2024-09-09T12:34:56Z\"},\n" +
				        "            {\"value\": 23, \"timestamp\": \"2024-09-09T12:35:56Z\"}\n" +
				        "        ]\n" +
				        "    }\n" +
				        "}";	
			
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);

				HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

				ResponseEntity<String> responseEntity = restTemplate.exchange("/api/access-with-token", HttpMethod.POST,
						requestEntity, String.class);

				// Verify 200 OK
				assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

				// Verify that it has been a successful validation
				assertNotEquals("ERROR: Validation failed. Capability Token is not valid. The requester couldn't access to the resource.\n", responseEntity.getBody());

			}
		 
		 @Order(22)
			@Test
			void testRequestAccessEndpointGetH() {
				System.out.println("Test 8 for TANGO: Request access for doing action GET in the resource /humidity and getting the Capability Token \n" );
				
				
				String requestBody =  "{\n" +
					    "    \"didSP\": \"Leo-Free-Home-Standing-1 Hand-Phone\",\n" +
					    "    \"sar\": {\n" +
					    "        \"action\":\"GET\",\n" +
					    "        \"resource\":\"https://api-server.testing1.k8s-cluster.tango.rid-intrasoft.eu/resource/humidity\"\n" +
					    "    },\n" +
					    "    \"didRequester\": \""+entityDid+"\",\n" +
					    "    \"accessToken\": \"eyJhbGciOiJFUzI1NiIsImtpZCI6IkN3YjlhMzNnVVE3Wmw0amlJZTI3alkwc1oweUd0ZXRVamxqdUFsb3RSWnciLCJ0eXAiOiJKV1QifQ.eyJhdWQiOlsicG9ydGFsLnRlc3RpbmcxLms4cy1jbHVzdGVyLnRhbmdvLnJpZC1pbnRyYXNvZnQuZXUiXSwiY2xpZW50X2lkIjoiZGlkOndlYjppcHMudGVzdGluZzEuazhzLWNsdXN0ZXIudGFuZ28ucmlkLWludHJhc29mdC5ldTpkaWQiLCJleHAiOjE3NDA1NjcxNDAsImlzcyI6ImRpZDp3ZWI6aXBzLnRlc3RpbmcxLms4cy1jbHVzdGVyLnRhbmdvLnJpZC1pbnRyYXNvZnQuZXU6ZGlkIiwia2lkIjoiQ3diOWEzM2dVUTdabDRqaUllMjdqWTBzWjB5R3RldFVqbGp1QWxvdFJadyIsInN1YiI6ImRpZDpteTp3YWxsZXQiLCJ2ZXJpZmlhYmxlQ3JlZGVudGlhbCI6eyJAY29udGV4dCI6WyJodHRwczovL3d3dy53My5vcmcvMjAxOC9jcmVkZW50aWFscy92MSIsImh0dHBzOi8vdzNpZC5vcmcvc2VjdXJpdHkvc3VpdGVzL2p3cy0yMDIwL3YxIl0sImNyZWRlbnRpYWxTY2hlbWEiOnsiaWQiOiJodHRwczovL3Jhdy5naXRodWJ1c2VyY29udGVudC5jb20vRklXQVJFLU9wcy90ZWNoLXgtY2hhbGxlbmdlL21haW4vc2NoZW1hLmpzb24iLCJ0eXBlIjoiRnVsbEpzb25TY2hlbWFWYWxpZGF0b3IyMDIxIn0sImNyZWRlbnRpYWxTdWJqZWN0Ijp7ImVtYWlsIjoic3RhbmRhcmQtZW1wbG95ZWVAaXBzLm9yZyIsImlkIjoiMGIxOWEzNDItN2I2NC00NjJmLWI5OTctYjkwZmZiYTlkMjgyIiwibGFzdE5hbWUiOiJJUFMiLCJyb2xlcyI6W3sibmFtZXMiOlsiR09MRF9DVVNUT01FUiJdLCJ0YXJnZXQiOiJkaWQ6d2ViOmlwcy5rOHMtY2x1c3Rlci50YW5nby5yaWQtaW50cmFzb2Z0LmV1OmRpZCJ9XSwidHlwZSI6Imd4Ok5hdHVyYWxQYXJ0aWNpcGFudCJ9LCJpZCI6InVybjp1dWlkOjhkMzdlNTA5LWNkNTctNGYwNS04YWIwLTY5YmM1NDNmMzE4MCIsImlzc3VhbmNlRGF0ZSI6IjIwMjQtMTEtMjBUMDk6Mzc6NDRaIiwiaXNzdWVkIjoiMjAyNC0xMS0yMFQwOTozNzo0NFoiLCJpc3N1ZXIiOiJkaWQ6d2ViOmlwcy5rOHMtY2x1c3Rlci50YW5nby5yaWQtaW50cmFzb2Z0LmV1OmRpZCIsInByb29mIjp7ImNyZWF0ZWQiOiIyMDI0LTExLTIwVDA5OjM3OjQ0WiIsImp3cyI6ImV5SmlOalFpT21aaGJITmxMQ0pqY21sMElqcGJJbUkyTkNKZExDSmhiR2NpT2lKUVV6STFOaUo5Li5qb1pyTmtMRDJtNVBWVWNTZkp3Tjlnb0YxUzZVcEhfR3lmNEJVTDA4NEtQeFA0NENrNzB0eEFoN0NfLWRsQllTZ0FYeEhod0xzRWVvaHpkb1BNV1M3Ql9hZ2pMZlpSUjY3SzFBR00zelI4VWtXeTRNc3cxMWlUa0FmRUlRNWtaSzZ6SHk4WlptaExJQTJPT1U2T3NTZU9neVR5bGNKU1ItWHlIWjQxZkNuX3haVl94RkZqNU1OWEhkRG95UENsV2MwdVk5MVlXT09DQU1rdGF6RkhVQTVrY29IejV5YThJSGtoc29FLUNRRFpWUFF6QmdjaGU0ZHdPTGhZdVdMQ3ZOMjkwVDNRRTlLSGY3Qno5OVRveS1qZVhpZEJYbER0NENlcWx4OEt1T0hYRzJITmxTdlRDTXVQXzdoYW5WWjRPSTVqbG5nXzRXWE1zREo1eEJpbHhUaEEiLCJ0eXBlIjoiSnNvbldlYlNpZ25hdHVyZTIwMjAiLCJ2ZXJpZmljYXRpb25NZXRob2QiOiJkaWQ6d2ViOmlwcy5rOHMtY2x1c3Rlci50YW5nby5yaWQtaW50cmFzb2Z0LmV1OmRpZCM4MTY0ZTdlYTE5NmE0NTdmODZiM2Y4ODdhYjY5MTYyYiJ9LCJ0eXBlIjpbIkVtcGxveWVlQ3JlZGVudGlhbCJdLCJ2YWxpZEZyb20iOiIyMDI0LTExLTIwVDA5OjM3OjQ0WiJ9fQ.2FQwqgwCrR1JF_23aYgP_CduY3TZD8TtIG7R0UkIbx2xdcpzybkZGMdzjaX1eyyPPer2dxRC-WeYq3n4Gvvh0g\"\n" +
					    "}";
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);

				HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
		
				ResponseEntity<String> responseEntity = restTemplate.exchange("/api/connector-access-token", HttpMethod.POST,
						requestEntity, String.class);

				// Token issued for requester
				token = responseEntity.getBody();

				// Verify 200 OK
				assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

				// Verify that there's a CapabilityToken and is not null
				assertNotNull(responseEntity.getBody());
				assertTrue(!responseEntity.getBody().isEmpty());

			}
		 
		 @Order(23)
		@Test
		void testAccessWithTokenH() {
			System.out.println("Test 9 for TANGO: Doing action GET in the resource /humidity with the Capability Token received \n" );
			
			
			String requestBody = "{\n" + "    \"ct\": " + token + ",\n" + "     \"sar\":{\n"
					+ "        \"action\":\"GET\",\n" + "        \"resource\":\"https://api-server.testing1.k8s-cluster.tango.rid-intrasoft.eu/resource/humidity\"\n" + "    }\n"+ "}";
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

			ResponseEntity<String> responseEntity = restTemplate.exchange("/api/access-with-token", HttpMethod.POST,
					requestEntity, String.class);

			// Verify 200 OK
			assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

			// Verify that it has been a successful validation
			assertNotEquals("ERROR: Validation failed. Capability Token is not valid. The requester couldn't access to the resource.\n", responseEntity.getBody());

		}

		 @Order(24)
			@Test
			void testRequestAccessEndpointGetP() {
				System.out.println("Test 10 for TANGO: Request access for doing action GET in the resource /pressure and getting the Capability Token \n" );
				
				
				String requestBody =  "{\n" +
					    "    \"didSP\": \"Leo-Free-Home-Standing-1 Hand-Phone\",\n" +
					    "    \"sar\": {\n" +
					    "        \"action\":\"GET\",\n" +
					    "        \"resource\":\"https://api-server.testing1.k8s-cluster.tango.rid-intrasoft.eu/resource/pressure\"\n" +
					    "    },\n" +
					    "    \"didRequester\": \""+entityDid+"\",\n" +
					    "    \"accessToken\": \"eyJhbGciOiJFUzI1NiIsImtpZCI6IkN3YjlhMzNnVVE3Wmw0amlJZTI3alkwc1oweUd0ZXRVamxqdUFsb3RSWnciLCJ0eXAiOiJKV1QifQ.eyJhdWQiOlsicG9ydGFsLnRlc3RpbmcxLms4cy1jbHVzdGVyLnRhbmdvLnJpZC1pbnRyYXNvZnQuZXUiXSwiY2xpZW50X2lkIjoiZGlkOndlYjppcHMudGVzdGluZzEuazhzLWNsdXN0ZXIudGFuZ28ucmlkLWludHJhc29mdC5ldTpkaWQiLCJleHAiOjE3NDA1NjcxNDAsImlzcyI6ImRpZDp3ZWI6aXBzLnRlc3RpbmcxLms4cy1jbHVzdGVyLnRhbmdvLnJpZC1pbnRyYXNvZnQuZXU6ZGlkIiwia2lkIjoiQ3diOWEzM2dVUTdabDRqaUllMjdqWTBzWjB5R3RldFVqbGp1QWxvdFJadyIsInN1YiI6ImRpZDpteTp3YWxsZXQiLCJ2ZXJpZmlhYmxlQ3JlZGVudGlhbCI6eyJAY29udGV4dCI6WyJodHRwczovL3d3dy53My5vcmcvMjAxOC9jcmVkZW50aWFscy92MSIsImh0dHBzOi8vdzNpZC5vcmcvc2VjdXJpdHkvc3VpdGVzL2p3cy0yMDIwL3YxIl0sImNyZWRlbnRpYWxTY2hlbWEiOnsiaWQiOiJodHRwczovL3Jhdy5naXRodWJ1c2VyY29udGVudC5jb20vRklXQVJFLU9wcy90ZWNoLXgtY2hhbGxlbmdlL21haW4vc2NoZW1hLmpzb24iLCJ0eXBlIjoiRnVsbEpzb25TY2hlbWFWYWxpZGF0b3IyMDIxIn0sImNyZWRlbnRpYWxTdWJqZWN0Ijp7ImVtYWlsIjoic3RhbmRhcmQtZW1wbG95ZWVAaXBzLm9yZyIsImlkIjoiMGIxOWEzNDItN2I2NC00NjJmLWI5OTctYjkwZmZiYTlkMjgyIiwibGFzdE5hbWUiOiJJUFMiLCJyb2xlcyI6W3sibmFtZXMiOlsiR09MRF9DVVNUT01FUiJdLCJ0YXJnZXQiOiJkaWQ6d2ViOmlwcy5rOHMtY2x1c3Rlci50YW5nby5yaWQtaW50cmFzb2Z0LmV1OmRpZCJ9XSwidHlwZSI6Imd4Ok5hdHVyYWxQYXJ0aWNpcGFudCJ9LCJpZCI6InVybjp1dWlkOjhkMzdlNTA5LWNkNTctNGYwNS04YWIwLTY5YmM1NDNmMzE4MCIsImlzc3VhbmNlRGF0ZSI6IjIwMjQtMTEtMjBUMDk6Mzc6NDRaIiwiaXNzdWVkIjoiMjAyNC0xMS0yMFQwOTozNzo0NFoiLCJpc3N1ZXIiOiJkaWQ6d2ViOmlwcy5rOHMtY2x1c3Rlci50YW5nby5yaWQtaW50cmFzb2Z0LmV1OmRpZCIsInByb29mIjp7ImNyZWF0ZWQiOiIyMDI0LTExLTIwVDA5OjM3OjQ0WiIsImp3cyI6ImV5SmlOalFpT21aaGJITmxMQ0pqY21sMElqcGJJbUkyTkNKZExDSmhiR2NpT2lKUVV6STFOaUo5Li5qb1pyTmtMRDJtNVBWVWNTZkp3Tjlnb0YxUzZVcEhfR3lmNEJVTDA4NEtQeFA0NENrNzB0eEFoN0NfLWRsQllTZ0FYeEhod0xzRWVvaHpkb1BNV1M3Ql9hZ2pMZlpSUjY3SzFBR00zelI4VWtXeTRNc3cxMWlUa0FmRUlRNWtaSzZ6SHk4WlptaExJQTJPT1U2T3NTZU9neVR5bGNKU1ItWHlIWjQxZkNuX3haVl94RkZqNU1OWEhkRG95UENsV2MwdVk5MVlXT09DQU1rdGF6RkhVQTVrY29IejV5YThJSGtoc29FLUNRRFpWUFF6QmdjaGU0ZHdPTGhZdVdMQ3ZOMjkwVDNRRTlLSGY3Qno5OVRveS1qZVhpZEJYbER0NENlcWx4OEt1T0hYRzJITmxTdlRDTXVQXzdoYW5WWjRPSTVqbG5nXzRXWE1zREo1eEJpbHhUaEEiLCJ0eXBlIjoiSnNvbldlYlNpZ25hdHVyZTIwMjAiLCJ2ZXJpZmljYXRpb25NZXRob2QiOiJkaWQ6d2ViOmlwcy5rOHMtY2x1c3Rlci50YW5nby5yaWQtaW50cmFzb2Z0LmV1OmRpZCM4MTY0ZTdlYTE5NmE0NTdmODZiM2Y4ODdhYjY5MTYyYiJ9LCJ0eXBlIjpbIkVtcGxveWVlQ3JlZGVudGlhbCJdLCJ2YWxpZEZyb20iOiIyMDI0LTExLTIwVDA5OjM3OjQ0WiJ9fQ.2FQwqgwCrR1JF_23aYgP_CduY3TZD8TtIG7R0UkIbx2xdcpzybkZGMdzjaX1eyyPPer2dxRC-WeYq3n4Gvvh0g\"\n" +
					    "}";
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);

				HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
		
				ResponseEntity<String> responseEntity = restTemplate.exchange("/api/connector-access-token", HttpMethod.POST,
						requestEntity, String.class);

				// Token issued for requester
				token = responseEntity.getBody();

				// Verify 200 OK
				assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

				// Verify that there's a CapabilityToken and is not null
				assertNotNull(responseEntity.getBody());
				assertTrue(!responseEntity.getBody().isEmpty());

			}
		 
		 @Order(25)
		@Test
		void testAccessWithTokenP() {
			System.out.println("Test 11 for TANGO: Doing action GET in the resource /pressure with the Capability Token received \n" );
			
			
			String requestBody = "{\n" + "    \"ct\": " + token + ",\n" + "     \"sar\":{\n"
					+ "        \"action\":\"GET\",\n" + "        \"resource\":\"https://api-server.testing1.k8s-cluster.tango.rid-intrasoft.eu/resource/pressure\"\n" + "    }\n"+ "}";
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

			ResponseEntity<String> responseEntity = restTemplate.exchange("/api/access-with-token", HttpMethod.POST,
					requestEntity, String.class);

			// Verify 200 OK
			assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

			// Verify that it has been a successful validation
			assertNotEquals("ERROR: Validation failed. Capability Token is not valid. The requester couldn't access to the resource.\n", responseEntity.getBody());

		}
		 


}
