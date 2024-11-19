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
			System.out.println("Test 1 for TANGO: Add TSM Configuration \n" );
			
			
			String requestBody = "{\n" +
				    "    \"entity_did\": \"did:example:123456789abcdefghi\",\n" +
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

			// Usa Gson para parsear la cadena en un JsonObject
			JsonObject jsonObject = JsonParser.parseString(requestBody).getAsJsonObject();
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
	
			ResponseEntity<String> responseEntity = restTemplate.exchange("/api/trust-score-config", HttpMethod.POST,
					requestEntity, String.class);
			
			// Verify 200 OK
			assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

			// Verify that there's a CapabilityToken and is not null
			assertNotNull(responseEntity.getBody());
			//assertTrue(!responseEntity.getBody());
			TSMConfigResponse resp=gson.fromJson(responseEntity.getBody(), TSMConfigResponse.class);
			
		    // Extraer los valores de los campos entity_did y config_id
		    entityDid = resp.getEntity_did();
		    configId = resp.getConfig_id();
		}
		
		 @Order(2)
			@Test
			void testAddTSMPOSConfig() {
				System.out.println("Test 2 for TANGO: Add Protective Objectives to the TSM Configuration \n" );
				
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

				// Verify that there's a CapabilityToken and is not null
				assertNotNull(responseEntity.getBody());
				assertTrue(!responseEntity.getBody().isEmpty());
			
			}
		
		 @Order(3)
		@Test
		void testRequestAccessEndpoint() {
			System.out.println("Test 1 for TANGO: Request access for doing action GET in the resource /temperature and getting the Capability Token \n" );
			
			
			String requestBody =  "{\n" +
				    "    \"didSP\": \"tangoUser\",\n" +
				    "    \"sar\": {\n" +
				    "        \"action\":\"GET\",\n" +
				    "        \"resource\":\"/resource/temperature\"\n" +
				    "    },\n" +
				    "    \"didRequester\": \""+entityDid+"\",\n" +
				    "    \"accessToken\": \"eyJhbGciOiJFUzI1NiIsImtpZCI6IkN3YjlhMzNnVVE3Wmw0amlJZTI3alkwc1oweUd0ZXRVamxqdUFsb3RSWnciLCJ0eXAiOiJKV1QifQ.eyJhdWQiOlsicG9ydGFsLnRlc3RpbmcxLms4cy1jbHVzdGVyLnRhbmdvLnJpZC1pbnRyYXNvZnQuZXUiXSwiY2xpZW50X2lkIjoiZGlkOndlYjppcHMudGVzdGluZzEuazhzLWNsdXN0ZXIudGFuZ28ucmlkLWludHJhc29mdC5ldTpkaWQiLCJleHAiOjE3MzIwMjkxNjIsImlzcyI6ImRpZDp3ZWI6aXBzLnRlc3RpbmcxLms4cy1jbHVzdGVyLnRhbmdvLnJpZC1pbnRyYXNvZnQuZXU6ZGlkIiwia2lkIjoiQ3diOWEzM2dVUTdabDRqaUllMjdqWTBzWjB5R3RldFVqbGp1QWxvdFJadyIsInN1YiI6ImRpZDpteTp3YWxsZXQiLCJ2ZXJpZmlhYmxlUHJlc2VudGF0aW9uIjpbeyJAY29udGV4dCI6WyJodHRwczovL3d3dy53My5vcmcvMjAxOC9jcmVkZW50aWFscy92MSIsImh0dHBzOi8vdzNpZC5vcmcvc2VjdXJpdHkvc3VpdGVzL2p3cy0yMDIwL3YxIl0sImNyZWRlbnRpYWxTY2hlbWEiOnsiaWQiOiJodHRwczovL3Jhdy5naXRodWJ1c2VyY29udGVudC5jb20vRklXQVJFLU9wcy90ZWNoLXgtY2hhbGxlbmdlL21haW4vc2NoZW1hLmpzb24iLCJ0eXBlIjoiRnVsbEpzb25TY2hlbWFWYWxpZGF0b3IyMDIxIn0sImNyZWRlbnRpYWxTdWJqZWN0Ijp7ImVtYWlsIjoic3RhbmRhcmQtZW1wbG95ZWVAaXBzLm9yZyIsImlkIjoiZGEyZjkyYTQtNDRlNS00ZGUxLTg3ODQtZDBjZWQwN2IzMTUwIiwibGFzdE5hbWUiOiJJUFMiLCJyb2xlcyI6W3sibmFtZXMiOlsiR09MRF9DVVNUT01FUiJdLCJ0YXJnZXQiOiJkaWQ6d2ViOmlwcy50ZXN0aW5nMS5rOHMtY2x1c3Rlci50YW5nby5yaWQtaW50cmFzb2Z0LmV1OmRpZCJ9XSwidHlwZSI6Imd4Ok5hdHVyYWxQYXJ0aWNpcGFudCJ9LCJpZCI6InVybjp1dWlkOjNiMGMyOTQwLWM1YjQtNDlhYy04NDc4LTU0YjUzZjkxMzM5NSIsImlzc3VhbmNlRGF0ZSI6IjIwMjQtMTAtMjRUMDg6Mzc6NDhaIiwiaXNzdWVkIjoiMjAyNC0xMC0yNFQwODozNzo0OFoiLCJpc3N1ZXIiOiJkaWQ6d2ViOmlwcy50ZXN0aW5nMS5rOHMtY2x1c3Rlci50YW5nby5yaWQtaW50cmFzb2Z0LmV1OmRpZCIsInByb29mIjp7ImNyZWF0ZWQiOiIyMDI0LTEwLTI0VDA4OjM3OjQ4WiIsImp3cyI6ImV5SmlOalFpT21aaGJITmxMQ0pqY21sMElqcGJJbUkyTkNKZExDSmhiR2NpT2lKUVV6STFOaUo5Li5OTWpXU216enlVdXJFbXNMSGtpUjUwX2V2UnNJdjFDZVd6OGhpS1N4anVtUVp6azBjc09QaDBFaVA1YWtDSlhTX0VISFpLUENCUFk1YXV1ZVU4N21la2F2YmNTaDdCZktnZXNzejFKdGJSWG9vN2ZVbFlVU2VpaHFVRzBERDlOMERWSGFrX2RlR2FMM2U4bXoybmNuemd3cmNtSDdLM1hVbU9EdW15dGRJTk5UTjdpOHVydUNvSzB0UjMwdzNnQmZYQ2lCUHV1eHpQSG1wVlJQWFFQMzZqV3BsdG00U05qZ3J2T3ltWHgzaUJMQmlDTEYwaWNOVDFTa1VtLWZ6Y0RrMFFyWUR3Z3RVV3lYa2ZxRnJNMGt4RGl4QTFLUENQb3BUbHhxSU5TNHRleWlFTTZhRmEtcUVySlRoRm5mTlNTRDN4UW1BdUltZGR0WkdBeDhVNkppM3ciLCJ0eXBlIjoiSnNvbldlYlNpZ25hdHVyZTIwMjAiLCJ2ZXJpZmljYXRpb25NZXRob2QiOiJkaWQ6d2ViOmlwcy50ZXN0aW5nMS5rOHMtY2x1c3Rlci50YW5nby5yaWQtaW50cmFzb2Z0LmV1OmRpZCMxZDljYzVlMWQzMDU0YzE3YmE5NjhlYmQ5OGM5NDNkMCJ9LCJ0eXBlIjpbIkVtcGxveWVlQ3JlZGVudGlhbCJdLCJ2YWxpZEZyb20iOiIyMDI0LTEwLTI0VDA4OjM3OjQ4WiJ9LHsiQGNvbnRleHQiOlsiaHR0cHM6Ly93d3cudzMub3JnLzIwMTgvY3JlZGVudGlhbHMvdjEiLCJodHRwczovL3czaWQub3JnL3NlY3VyaXR5L3N1aXRlcy9qd3MtMjAyMC92MSJdLCJjcmVkZW50aWFsU2NoZW1hIjp7ImlkIjoiaHR0cHM6Ly9yYXcuZ2l0aHVidXNlcmNvbnRlbnQuY29tL0ZJV0FSRS1PcHMvdGVjaC14LWNoYWxsZW5nZS9tYWluL3NjaGVtYS5qc29uIiwidHlwZSI6IkZ1bGxKc29uU2NoZW1hVmFsaWRhdG9yMjAyMSJ9LCJjcmVkZW50aWFsU3ViamVjdCI6eyJlbWFpbCI6InN0YW5kYXJkLWVtcGxveWVlQGlwcy5vcmciLCJpZCI6ImU2ZWE3MmIzLTkwMmUtNGMxMC1hMGNjLTk2ZmQ4MWY5N2YxZiIsImxhc3ROYW1lIjoiSVBTIiwicm9sZXMiOlt7Im5hbWVzIjpbIkdPTERfQ1VTVE9NRVIiXSwidGFyZ2V0IjoiZGlkOndlYjppcHMuazhzLWNsdXN0ZXIudGFuZ28ucmlkLWludHJhc29mdC5ldTpkaWQifV0sInR5cGUiOiJneDpOYXR1cmFsUGFydGljaXBhbnQifSwiaWQiOiJ1cm46dXVpZDozNzMxM2NmNy0wZmNjLTRmZjktYmE4Mi0zYWU0YmU0NzRmYjMiLCJpc3N1YW5jZURhdGUiOiIyMDI0LTExLTE5VDA5OjIwOjA3WiIsImlzc3VlZCI6IjIwMjQtMTEtMTlUMDk6MjA6MDdaIiwiaXNzdWVyIjoiZGlkOndlYjppcHMuazhzLWNsdXN0ZXIudGFuZ28ucmlkLWludHJhc29mdC5ldTpkaWQiLCJwcm9vZiI6eyJjcmVhdGVkIjoiMjAyNC0xMS0xOVQwOToyMDowN1oiLCJqd3MiOiJleUppTmpRaU9tWmhiSE5sTENKamNtbDBJanBiSW1JMk5DSmRMQ0poYkdjaU9pSlFVekkxTmlKOS4uTkYzYWNUR3JDYm1ITUhwaDVYazRuSmVTRFRnRzFLME1lc182Tnl2RFRTUUFISWRLVFRSN1dZT3JsdkhPX0MzN241V3daSkxCd0N1SERUUkJFa2FKRGNkLTlOQWdwcmJUbmZSLVpUVlBiZ1Y0MzRYcThnbldDRXBkQ1dkV3U4akFPNnpLdU9WWWlBODBZT2hkVVdHY21kMnh1cWhlMWZHd1dHVk1xdlZJSWU3anZwMkR5T0ZoUW5pVDBnQ2psRHdUcFVmZW51aUNGVnZLQTFiODV3aHF1TkRIT0lnSDZsMmxCVW5aRGtlOUVLdENXY0owQzlMTHVZRHRhdWs0RHZKNEh0anQyN2VleldpRjlpWUZWYmc5S1h1UmhhWGdTcUpSX1NQVVVMVHROdUJXX3VjMXZVSWU3SW5PNlpKNzhWd1NlS0hkNE1WMWg1WlNVVjlQS3k4NnlRIiwidHlwZSI6Ikpzb25XZWJTaWduYXR1cmUyMDIwIiwidmVyaWZpY2F0aW9uTWV0aG9kIjoiZGlkOndlYjppcHMuazhzLWNsdXN0ZXIudGFuZ28ucmlkLWludHJhc29mdC5ldTpkaWQjODE2NGU3ZWExOTZhNDU3Zjg2YjNmODg3YWI2OTE2MmIifSwidHlwZSI6WyJFbXBsb3llZUNyZWRlbnRpYWwiXSwidmFsaWRGcm9tIjoiMjAyNC0xMS0xOVQwOToyMDowN1oifV19.oluM_w-iSQOvDvrJc9-cGE7ElFR0uBmBS91ifyEyjv60qcoxZqU7ZAzxtRJJWJmF9gTVFqrLPGzrcTbKKbsmVw\"\n" +
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
		 @Order(4)
		@Test
		void testAccessWithToken() {
			System.out.println("Test 2 for TANGO: Doing action GET in the resource /temperature with the Capability Token received \n" );
			
			String requestBody = "{\n" +
			        "    \"ct\": " + token + ",\n" +
			        "     \"sar\":{\n" +
			        "        \"action\":\"GET\",\n" +
			        "        \"resource\":\"/resource/temperature\"\n" +
			        "    },\n" +
			        "     \"queryParameters\": {\n" +
			        "        \"min_value\": 20,\n" +
			        "        \"max_value\": 23,\n" +
			        "        \"sensor\": \"sensor2\",\n" +
			        "        \"unit\": \"Celsius\",\n" +
			        "        \"min_time\": \"2024-09-09T12:00:00Z\",\n" +
			        "        \"max_time\": \"2024-09-09T12:01:00Z\"\n" +
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
		 @Order(5)
		@Test
		void testAccessWithWrongToken() {
			System.out.println("Test 3 for TANGO: Trying to do action POST in the resource /temperature with the Capability Token received \n" );
			

			String requestBody = "{\n" + "    \"ct\": " + token + ",\n" + "     \"sar\":{\n"
					+ "        \"action\":\"POST\",\n" + "        \"resource\":\"/resource/temperature\"\n" + "    },\n"+"     \"queryParameters\": {\n	\"temperature\": 23.5,\n	\"unit\": \"Celsius\",\n	\"sensor\": \"sensor123\",\n"
			        + "	\"timestamp\": \"2024-09-09T12:34:56Z\"\n"
			        + "	}\n "+ "}";

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

			ResponseEntity<String> responseEntity = restTemplate.exchange("/api/access-with-token", HttpMethod.POST,
					requestEntity, String.class);

		
			// Verify that it has been a successful validation
			assertEquals("ERROR: Validation failed. Capability Token is not valid. The requester couldn't access to the resource.\n", responseEntity.getBody());

		}
		 @Order(6)
			@Test
			void testNotMatchingPolicy() {
				System.out.println("Test 4 for TANGO: Request access for doing action GET in the resource /temperature and not getting the Capability Token because the token is expired and the policy matching failed \n" );
				
				
				String requestBody =  "{\n" +
					    "    \"didSP\": \"tangoUser\",\n" +
					    "    \"sar\": {\n" +
					    "        \"action\":\"GET\",\n" +
					    "        \"resource\":\"/resource/temperature\"\n" +
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
		 
		 @Order(7)
			@Test
			void testNotVerifyingSignature() {
				System.out.println("Test 5 for TANGO: Request access for doing action GET in the resource /temperature and not getting the Capability Token because access token signature couldn't be verified \n" );
				
				
				String requestBody =  "{\n" +
					    "    \"didSP\": \"tangoUser\",\n" +
					    "    \"sar\": {\n" +
					    "        \"action\":\"GET\",\n" +
					    "        \"resource\":\"/resource/temperature\"\n" +
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
		 
		 @Order(8)
			@Test
			void testRequestAccessEndpointPost() {
				System.out.println("Test 6 for TANGO: Request access for doing action POST in the resource /temperature and getting the Capability Token \n" );
				
				
				String requestBody =  "{\n" +
					    "    \"didSP\": \"tangoUser\",\n" +
					    "    \"sar\": {\n" +
					    "        \"action\":\"POST\",\n" +
					    "        \"resource\":\"/resource/temperature\"\n" +
					    "    },\n" +
					    "    \"didRequester\": \""+entityDid+"\",\n" +
					    "    \"accessToken\": \"eyJhbGciOiJFUzI1NiIsImtpZCI6IkN3YjlhMzNnVVE3Wmw0amlJZTI3alkwc1oweUd0ZXRVamxqdUFsb3RSWnciLCJ0eXAiOiJKV1QifQ.eyJhdWQiOlsicG9ydGFsLnRlc3RpbmcxLms4cy1jbHVzdGVyLnRhbmdvLnJpZC1pbnRyYXNvZnQuZXUiXSwiY2xpZW50X2lkIjoiZGlkOndlYjppcHMudGVzdGluZzEuazhzLWNsdXN0ZXIudGFuZ28ucmlkLWludHJhc29mdC5ldTpkaWQiLCJleHAiOjE3MzIwMjkxNjIsImlzcyI6ImRpZDp3ZWI6aXBzLnRlc3RpbmcxLms4cy1jbHVzdGVyLnRhbmdvLnJpZC1pbnRyYXNvZnQuZXU6ZGlkIiwia2lkIjoiQ3diOWEzM2dVUTdabDRqaUllMjdqWTBzWjB5R3RldFVqbGp1QWxvdFJadyIsInN1YiI6ImRpZDpteTp3YWxsZXQiLCJ2ZXJpZmlhYmxlUHJlc2VudGF0aW9uIjpbeyJAY29udGV4dCI6WyJodHRwczovL3d3dy53My5vcmcvMjAxOC9jcmVkZW50aWFscy92MSIsImh0dHBzOi8vdzNpZC5vcmcvc2VjdXJpdHkvc3VpdGVzL2p3cy0yMDIwL3YxIl0sImNyZWRlbnRpYWxTY2hlbWEiOnsiaWQiOiJodHRwczovL3Jhdy5naXRodWJ1c2VyY29udGVudC5jb20vRklXQVJFLU9wcy90ZWNoLXgtY2hhbGxlbmdlL21haW4vc2NoZW1hLmpzb24iLCJ0eXBlIjoiRnVsbEpzb25TY2hlbWFWYWxpZGF0b3IyMDIxIn0sImNyZWRlbnRpYWxTdWJqZWN0Ijp7ImVtYWlsIjoic3RhbmRhcmQtZW1wbG95ZWVAaXBzLm9yZyIsImlkIjoiZGEyZjkyYTQtNDRlNS00ZGUxLTg3ODQtZDBjZWQwN2IzMTUwIiwibGFzdE5hbWUiOiJJUFMiLCJyb2xlcyI6W3sibmFtZXMiOlsiR09MRF9DVVNUT01FUiJdLCJ0YXJnZXQiOiJkaWQ6d2ViOmlwcy50ZXN0aW5nMS5rOHMtY2x1c3Rlci50YW5nby5yaWQtaW50cmFzb2Z0LmV1OmRpZCJ9XSwidHlwZSI6Imd4Ok5hdHVyYWxQYXJ0aWNpcGFudCJ9LCJpZCI6InVybjp1dWlkOjNiMGMyOTQwLWM1YjQtNDlhYy04NDc4LTU0YjUzZjkxMzM5NSIsImlzc3VhbmNlRGF0ZSI6IjIwMjQtMTAtMjRUMDg6Mzc6NDhaIiwiaXNzdWVkIjoiMjAyNC0xMC0yNFQwODozNzo0OFoiLCJpc3N1ZXIiOiJkaWQ6d2ViOmlwcy50ZXN0aW5nMS5rOHMtY2x1c3Rlci50YW5nby5yaWQtaW50cmFzb2Z0LmV1OmRpZCIsInByb29mIjp7ImNyZWF0ZWQiOiIyMDI0LTEwLTI0VDA4OjM3OjQ4WiIsImp3cyI6ImV5SmlOalFpT21aaGJITmxMQ0pqY21sMElqcGJJbUkyTkNKZExDSmhiR2NpT2lKUVV6STFOaUo5Li5OTWpXU216enlVdXJFbXNMSGtpUjUwX2V2UnNJdjFDZVd6OGhpS1N4anVtUVp6azBjc09QaDBFaVA1YWtDSlhTX0VISFpLUENCUFk1YXV1ZVU4N21la2F2YmNTaDdCZktnZXNzejFKdGJSWG9vN2ZVbFlVU2VpaHFVRzBERDlOMERWSGFrX2RlR2FMM2U4bXoybmNuemd3cmNtSDdLM1hVbU9EdW15dGRJTk5UTjdpOHVydUNvSzB0UjMwdzNnQmZYQ2lCUHV1eHpQSG1wVlJQWFFQMzZqV3BsdG00U05qZ3J2T3ltWHgzaUJMQmlDTEYwaWNOVDFTa1VtLWZ6Y0RrMFFyWUR3Z3RVV3lYa2ZxRnJNMGt4RGl4QTFLUENQb3BUbHhxSU5TNHRleWlFTTZhRmEtcUVySlRoRm5mTlNTRDN4UW1BdUltZGR0WkdBeDhVNkppM3ciLCJ0eXBlIjoiSnNvbldlYlNpZ25hdHVyZTIwMjAiLCJ2ZXJpZmljYXRpb25NZXRob2QiOiJkaWQ6d2ViOmlwcy50ZXN0aW5nMS5rOHMtY2x1c3Rlci50YW5nby5yaWQtaW50cmFzb2Z0LmV1OmRpZCMxZDljYzVlMWQzMDU0YzE3YmE5NjhlYmQ5OGM5NDNkMCJ9LCJ0eXBlIjpbIkVtcGxveWVlQ3JlZGVudGlhbCJdLCJ2YWxpZEZyb20iOiIyMDI0LTEwLTI0VDA4OjM3OjQ4WiJ9LHsiQGNvbnRleHQiOlsiaHR0cHM6Ly93d3cudzMub3JnLzIwMTgvY3JlZGVudGlhbHMvdjEiLCJodHRwczovL3czaWQub3JnL3NlY3VyaXR5L3N1aXRlcy9qd3MtMjAyMC92MSJdLCJjcmVkZW50aWFsU2NoZW1hIjp7ImlkIjoiaHR0cHM6Ly9yYXcuZ2l0aHVidXNlcmNvbnRlbnQuY29tL0ZJV0FSRS1PcHMvdGVjaC14LWNoYWxsZW5nZS9tYWluL3NjaGVtYS5qc29uIiwidHlwZSI6IkZ1bGxKc29uU2NoZW1hVmFsaWRhdG9yMjAyMSJ9LCJjcmVkZW50aWFsU3ViamVjdCI6eyJlbWFpbCI6InN0YW5kYXJkLWVtcGxveWVlQGlwcy5vcmciLCJpZCI6ImU2ZWE3MmIzLTkwMmUtNGMxMC1hMGNjLTk2ZmQ4MWY5N2YxZiIsImxhc3ROYW1lIjoiSVBTIiwicm9sZXMiOlt7Im5hbWVzIjpbIkdPTERfQ1VTVE9NRVIiXSwidGFyZ2V0IjoiZGlkOndlYjppcHMuazhzLWNsdXN0ZXIudGFuZ28ucmlkLWludHJhc29mdC5ldTpkaWQifV0sInR5cGUiOiJneDpOYXR1cmFsUGFydGljaXBhbnQifSwiaWQiOiJ1cm46dXVpZDozNzMxM2NmNy0wZmNjLTRmZjktYmE4Mi0zYWU0YmU0NzRmYjMiLCJpc3N1YW5jZURhdGUiOiIyMDI0LTExLTE5VDA5OjIwOjA3WiIsImlzc3VlZCI6IjIwMjQtMTEtMTlUMDk6MjA6MDdaIiwiaXNzdWVyIjoiZGlkOndlYjppcHMuazhzLWNsdXN0ZXIudGFuZ28ucmlkLWludHJhc29mdC5ldTpkaWQiLCJwcm9vZiI6eyJjcmVhdGVkIjoiMjAyNC0xMS0xOVQwOToyMDowN1oiLCJqd3MiOiJleUppTmpRaU9tWmhiSE5sTENKamNtbDBJanBiSW1JMk5DSmRMQ0poYkdjaU9pSlFVekkxTmlKOS4uTkYzYWNUR3JDYm1ITUhwaDVYazRuSmVTRFRnRzFLME1lc182Tnl2RFRTUUFISWRLVFRSN1dZT3JsdkhPX0MzN241V3daSkxCd0N1SERUUkJFa2FKRGNkLTlOQWdwcmJUbmZSLVpUVlBiZ1Y0MzRYcThnbldDRXBkQ1dkV3U4akFPNnpLdU9WWWlBODBZT2hkVVdHY21kMnh1cWhlMWZHd1dHVk1xdlZJSWU3anZwMkR5T0ZoUW5pVDBnQ2psRHdUcFVmZW51aUNGVnZLQTFiODV3aHF1TkRIT0lnSDZsMmxCVW5aRGtlOUVLdENXY0owQzlMTHVZRHRhdWs0RHZKNEh0anQyN2VleldpRjlpWUZWYmc5S1h1UmhhWGdTcUpSX1NQVVVMVHROdUJXX3VjMXZVSWU3SW5PNlpKNzhWd1NlS0hkNE1WMWg1WlNVVjlQS3k4NnlRIiwidHlwZSI6Ikpzb25XZWJTaWduYXR1cmUyMDIwIiwidmVyaWZpY2F0aW9uTWV0aG9kIjoiZGlkOndlYjppcHMuazhzLWNsdXN0ZXIudGFuZ28ucmlkLWludHJhc29mdC5ldTpkaWQjODE2NGU3ZWExOTZhNDU3Zjg2YjNmODg3YWI2OTE2MmIifSwidHlwZSI6WyJFbXBsb3llZUNyZWRlbnRpYWwiXSwidmFsaWRGcm9tIjoiMjAyNC0xMS0xOVQwOToyMDowN1oifV19.oluM_w-iSQOvDvrJc9-cGE7ElFR0uBmBS91ifyEyjv60qcoxZqU7ZAzxtRJJWJmF9gTVFqrLPGzrcTbKKbsmVw\"\n" +
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
		 
		 @Order(9)
			@Test
			void testAccessWithTokenPost() {
				System.out.println("Test 7 for TANGO: Doing action POST in the resource /temperature with the Capability Token received \n" );
				
				String requestBody = "{\n" +
				        "    \"ct\": " + token + ",\n" +
				        "    \"sar\": {\n" +
				        "        \"action\": \"POST\",\n" +
				        "        \"resource\": \"/resource/temperature\"\n" +
				        "    },\n" +
				        "    \"queryParameters\": {\n" +
				        "        \"sensor\": \"sensor8\",\n" +
				        "        \"measure\": \"temperature\",\n" +
				        "        \"unit\": \"Celsius\",\n" +
				        "        \"values\": [\n" +
				        "            {\"value\": 21, \"timestamp\": \"2024-09-09T12:34:56Z\"},\n" +
				        "            {\"value\": 23, \"timestamp\": \"2024-09-09T12:35:56Z\"},\n" +
				        "            {\"value\": 25}\n" +
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
		 
		 @Order(10)
			@Test
			void testRequestAccessEndpointGetH() {
				System.out.println("Test 8 for TANGO: Request access for doing action GET in the resource /humidity and getting the Capability Token \n" );
				
				
				String requestBody =  "{\n" +
					    "    \"didSP\": \"tangoUser\",\n" +
					    "    \"sar\": {\n" +
					    "        \"action\":\"GET\",\n" +
					    "        \"resource\":\"/resource/humidity\"\n" +
					    "    },\n" +
					    "    \"didRequester\": \""+entityDid+"\",\n" +
					    "    \"accessToken\": \"eyJhbGciOiJFUzI1NiIsImtpZCI6IkN3YjlhMzNnVVE3Wmw0amlJZTI3alkwc1oweUd0ZXRVamxqdUFsb3RSWnciLCJ0eXAiOiJKV1QifQ.eyJhdWQiOlsicG9ydGFsLnRlc3RpbmcxLms4cy1jbHVzdGVyLnRhbmdvLnJpZC1pbnRyYXNvZnQuZXUiXSwiY2xpZW50X2lkIjoiZGlkOndlYjppcHMudGVzdGluZzEuazhzLWNsdXN0ZXIudGFuZ28ucmlkLWludHJhc29mdC5ldTpkaWQiLCJleHAiOjE3MzIwMjkxNjIsImlzcyI6ImRpZDp3ZWI6aXBzLnRlc3RpbmcxLms4cy1jbHVzdGVyLnRhbmdvLnJpZC1pbnRyYXNvZnQuZXU6ZGlkIiwia2lkIjoiQ3diOWEzM2dVUTdabDRqaUllMjdqWTBzWjB5R3RldFVqbGp1QWxvdFJadyIsInN1YiI6ImRpZDpteTp3YWxsZXQiLCJ2ZXJpZmlhYmxlUHJlc2VudGF0aW9uIjpbeyJAY29udGV4dCI6WyJodHRwczovL3d3dy53My5vcmcvMjAxOC9jcmVkZW50aWFscy92MSIsImh0dHBzOi8vdzNpZC5vcmcvc2VjdXJpdHkvc3VpdGVzL2p3cy0yMDIwL3YxIl0sImNyZWRlbnRpYWxTY2hlbWEiOnsiaWQiOiJodHRwczovL3Jhdy5naXRodWJ1c2VyY29udGVudC5jb20vRklXQVJFLU9wcy90ZWNoLXgtY2hhbGxlbmdlL21haW4vc2NoZW1hLmpzb24iLCJ0eXBlIjoiRnVsbEpzb25TY2hlbWFWYWxpZGF0b3IyMDIxIn0sImNyZWRlbnRpYWxTdWJqZWN0Ijp7ImVtYWlsIjoic3RhbmRhcmQtZW1wbG95ZWVAaXBzLm9yZyIsImlkIjoiZGEyZjkyYTQtNDRlNS00ZGUxLTg3ODQtZDBjZWQwN2IzMTUwIiwibGFzdE5hbWUiOiJJUFMiLCJyb2xlcyI6W3sibmFtZXMiOlsiR09MRF9DVVNUT01FUiJdLCJ0YXJnZXQiOiJkaWQ6d2ViOmlwcy50ZXN0aW5nMS5rOHMtY2x1c3Rlci50YW5nby5yaWQtaW50cmFzb2Z0LmV1OmRpZCJ9XSwidHlwZSI6Imd4Ok5hdHVyYWxQYXJ0aWNpcGFudCJ9LCJpZCI6InVybjp1dWlkOjNiMGMyOTQwLWM1YjQtNDlhYy04NDc4LTU0YjUzZjkxMzM5NSIsImlzc3VhbmNlRGF0ZSI6IjIwMjQtMTAtMjRUMDg6Mzc6NDhaIiwiaXNzdWVkIjoiMjAyNC0xMC0yNFQwODozNzo0OFoiLCJpc3N1ZXIiOiJkaWQ6d2ViOmlwcy50ZXN0aW5nMS5rOHMtY2x1c3Rlci50YW5nby5yaWQtaW50cmFzb2Z0LmV1OmRpZCIsInByb29mIjp7ImNyZWF0ZWQiOiIyMDI0LTEwLTI0VDA4OjM3OjQ4WiIsImp3cyI6ImV5SmlOalFpT21aaGJITmxMQ0pqY21sMElqcGJJbUkyTkNKZExDSmhiR2NpT2lKUVV6STFOaUo5Li5OTWpXU216enlVdXJFbXNMSGtpUjUwX2V2UnNJdjFDZVd6OGhpS1N4anVtUVp6azBjc09QaDBFaVA1YWtDSlhTX0VISFpLUENCUFk1YXV1ZVU4N21la2F2YmNTaDdCZktnZXNzejFKdGJSWG9vN2ZVbFlVU2VpaHFVRzBERDlOMERWSGFrX2RlR2FMM2U4bXoybmNuemd3cmNtSDdLM1hVbU9EdW15dGRJTk5UTjdpOHVydUNvSzB0UjMwdzNnQmZYQ2lCUHV1eHpQSG1wVlJQWFFQMzZqV3BsdG00U05qZ3J2T3ltWHgzaUJMQmlDTEYwaWNOVDFTa1VtLWZ6Y0RrMFFyWUR3Z3RVV3lYa2ZxRnJNMGt4RGl4QTFLUENQb3BUbHhxSU5TNHRleWlFTTZhRmEtcUVySlRoRm5mTlNTRDN4UW1BdUltZGR0WkdBeDhVNkppM3ciLCJ0eXBlIjoiSnNvbldlYlNpZ25hdHVyZTIwMjAiLCJ2ZXJpZmljYXRpb25NZXRob2QiOiJkaWQ6d2ViOmlwcy50ZXN0aW5nMS5rOHMtY2x1c3Rlci50YW5nby5yaWQtaW50cmFzb2Z0LmV1OmRpZCMxZDljYzVlMWQzMDU0YzE3YmE5NjhlYmQ5OGM5NDNkMCJ9LCJ0eXBlIjpbIkVtcGxveWVlQ3JlZGVudGlhbCJdLCJ2YWxpZEZyb20iOiIyMDI0LTEwLTI0VDA4OjM3OjQ4WiJ9LHsiQGNvbnRleHQiOlsiaHR0cHM6Ly93d3cudzMub3JnLzIwMTgvY3JlZGVudGlhbHMvdjEiLCJodHRwczovL3czaWQub3JnL3NlY3VyaXR5L3N1aXRlcy9qd3MtMjAyMC92MSJdLCJjcmVkZW50aWFsU2NoZW1hIjp7ImlkIjoiaHR0cHM6Ly9yYXcuZ2l0aHVidXNlcmNvbnRlbnQuY29tL0ZJV0FSRS1PcHMvdGVjaC14LWNoYWxsZW5nZS9tYWluL3NjaGVtYS5qc29uIiwidHlwZSI6IkZ1bGxKc29uU2NoZW1hVmFsaWRhdG9yMjAyMSJ9LCJjcmVkZW50aWFsU3ViamVjdCI6eyJlbWFpbCI6InN0YW5kYXJkLWVtcGxveWVlQGlwcy5vcmciLCJpZCI6ImU2ZWE3MmIzLTkwMmUtNGMxMC1hMGNjLTk2ZmQ4MWY5N2YxZiIsImxhc3ROYW1lIjoiSVBTIiwicm9sZXMiOlt7Im5hbWVzIjpbIkdPTERfQ1VTVE9NRVIiXSwidGFyZ2V0IjoiZGlkOndlYjppcHMuazhzLWNsdXN0ZXIudGFuZ28ucmlkLWludHJhc29mdC5ldTpkaWQifV0sInR5cGUiOiJneDpOYXR1cmFsUGFydGljaXBhbnQifSwiaWQiOiJ1cm46dXVpZDozNzMxM2NmNy0wZmNjLTRmZjktYmE4Mi0zYWU0YmU0NzRmYjMiLCJpc3N1YW5jZURhdGUiOiIyMDI0LTExLTE5VDA5OjIwOjA3WiIsImlzc3VlZCI6IjIwMjQtMTEtMTlUMDk6MjA6MDdaIiwiaXNzdWVyIjoiZGlkOndlYjppcHMuazhzLWNsdXN0ZXIudGFuZ28ucmlkLWludHJhc29mdC5ldTpkaWQiLCJwcm9vZiI6eyJjcmVhdGVkIjoiMjAyNC0xMS0xOVQwOToyMDowN1oiLCJqd3MiOiJleUppTmpRaU9tWmhiSE5sTENKamNtbDBJanBiSW1JMk5DSmRMQ0poYkdjaU9pSlFVekkxTmlKOS4uTkYzYWNUR3JDYm1ITUhwaDVYazRuSmVTRFRnRzFLME1lc182Tnl2RFRTUUFISWRLVFRSN1dZT3JsdkhPX0MzN241V3daSkxCd0N1SERUUkJFa2FKRGNkLTlOQWdwcmJUbmZSLVpUVlBiZ1Y0MzRYcThnbldDRXBkQ1dkV3U4akFPNnpLdU9WWWlBODBZT2hkVVdHY21kMnh1cWhlMWZHd1dHVk1xdlZJSWU3anZwMkR5T0ZoUW5pVDBnQ2psRHdUcFVmZW51aUNGVnZLQTFiODV3aHF1TkRIT0lnSDZsMmxCVW5aRGtlOUVLdENXY0owQzlMTHVZRHRhdWs0RHZKNEh0anQyN2VleldpRjlpWUZWYmc5S1h1UmhhWGdTcUpSX1NQVVVMVHROdUJXX3VjMXZVSWU3SW5PNlpKNzhWd1NlS0hkNE1WMWg1WlNVVjlQS3k4NnlRIiwidHlwZSI6Ikpzb25XZWJTaWduYXR1cmUyMDIwIiwidmVyaWZpY2F0aW9uTWV0aG9kIjoiZGlkOndlYjppcHMuazhzLWNsdXN0ZXIudGFuZ28ucmlkLWludHJhc29mdC5ldTpkaWQjODE2NGU3ZWExOTZhNDU3Zjg2YjNmODg3YWI2OTE2MmIifSwidHlwZSI6WyJFbXBsb3llZUNyZWRlbnRpYWwiXSwidmFsaWRGcm9tIjoiMjAyNC0xMS0xOVQwOToyMDowN1oifV19.oluM_w-iSQOvDvrJc9-cGE7ElFR0uBmBS91ifyEyjv60qcoxZqU7ZAzxtRJJWJmF9gTVFqrLPGzrcTbKKbsmVw\"\n" +
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
		 
		 @Order(11)
		@Test
		void testAccessWithTokenH() {
			System.out.println("Test 9 for TANGO: Doing action GET in the resource /humidity with the Capability Token received \n" );
			
			
			String requestBody = "{\n" + "    \"ct\": " + token + ",\n" + "     \"sar\":{\n"
					+ "        \"action\":\"GET\",\n" + "        \"resource\":\"/resource/humidity\"\n" + "    }\n"+ "}";
			
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
			void testRequestAccessEndpointGetP() {
				System.out.println("Test 10 for TANGO: Request access for doing action GET in the resource /pressure and getting the Capability Token \n" );
				
				
				String requestBody =  "{\n" +
					    "    \"didSP\": \"tangoUser\",\n" +
					    "    \"sar\": {\n" +
					    "        \"action\":\"GET\",\n" +
					    "        \"resource\":\"/resource/pressure\"\n" +
					    "    },\n" +
					    "    \"didRequester\": \""+entityDid+"\",\n" +
					    "    \"accessToken\": \"eyJhbGciOiJFUzI1NiIsImtpZCI6IkN3YjlhMzNnVVE3Wmw0amlJZTI3alkwc1oweUd0ZXRVamxqdUFsb3RSWnciLCJ0eXAiOiJKV1QifQ.eyJhdWQiOlsicG9ydGFsLnRlc3RpbmcxLms4cy1jbHVzdGVyLnRhbmdvLnJpZC1pbnRyYXNvZnQuZXUiXSwiY2xpZW50X2lkIjoiZGlkOndlYjppcHMudGVzdGluZzEuazhzLWNsdXN0ZXIudGFuZ28ucmlkLWludHJhc29mdC5ldTpkaWQiLCJleHAiOjE3MzIwMjkxNjIsImlzcyI6ImRpZDp3ZWI6aXBzLnRlc3RpbmcxLms4cy1jbHVzdGVyLnRhbmdvLnJpZC1pbnRyYXNvZnQuZXU6ZGlkIiwia2lkIjoiQ3diOWEzM2dVUTdabDRqaUllMjdqWTBzWjB5R3RldFVqbGp1QWxvdFJadyIsInN1YiI6ImRpZDpteTp3YWxsZXQiLCJ2ZXJpZmlhYmxlUHJlc2VudGF0aW9uIjpbeyJAY29udGV4dCI6WyJodHRwczovL3d3dy53My5vcmcvMjAxOC9jcmVkZW50aWFscy92MSIsImh0dHBzOi8vdzNpZC5vcmcvc2VjdXJpdHkvc3VpdGVzL2p3cy0yMDIwL3YxIl0sImNyZWRlbnRpYWxTY2hlbWEiOnsiaWQiOiJodHRwczovL3Jhdy5naXRodWJ1c2VyY29udGVudC5jb20vRklXQVJFLU9wcy90ZWNoLXgtY2hhbGxlbmdlL21haW4vc2NoZW1hLmpzb24iLCJ0eXBlIjoiRnVsbEpzb25TY2hlbWFWYWxpZGF0b3IyMDIxIn0sImNyZWRlbnRpYWxTdWJqZWN0Ijp7ImVtYWlsIjoic3RhbmRhcmQtZW1wbG95ZWVAaXBzLm9yZyIsImlkIjoiZGEyZjkyYTQtNDRlNS00ZGUxLTg3ODQtZDBjZWQwN2IzMTUwIiwibGFzdE5hbWUiOiJJUFMiLCJyb2xlcyI6W3sibmFtZXMiOlsiR09MRF9DVVNUT01FUiJdLCJ0YXJnZXQiOiJkaWQ6d2ViOmlwcy50ZXN0aW5nMS5rOHMtY2x1c3Rlci50YW5nby5yaWQtaW50cmFzb2Z0LmV1OmRpZCJ9XSwidHlwZSI6Imd4Ok5hdHVyYWxQYXJ0aWNpcGFudCJ9LCJpZCI6InVybjp1dWlkOjNiMGMyOTQwLWM1YjQtNDlhYy04NDc4LTU0YjUzZjkxMzM5NSIsImlzc3VhbmNlRGF0ZSI6IjIwMjQtMTAtMjRUMDg6Mzc6NDhaIiwiaXNzdWVkIjoiMjAyNC0xMC0yNFQwODozNzo0OFoiLCJpc3N1ZXIiOiJkaWQ6d2ViOmlwcy50ZXN0aW5nMS5rOHMtY2x1c3Rlci50YW5nby5yaWQtaW50cmFzb2Z0LmV1OmRpZCIsInByb29mIjp7ImNyZWF0ZWQiOiIyMDI0LTEwLTI0VDA4OjM3OjQ4WiIsImp3cyI6ImV5SmlOalFpT21aaGJITmxMQ0pqY21sMElqcGJJbUkyTkNKZExDSmhiR2NpT2lKUVV6STFOaUo5Li5OTWpXU216enlVdXJFbXNMSGtpUjUwX2V2UnNJdjFDZVd6OGhpS1N4anVtUVp6azBjc09QaDBFaVA1YWtDSlhTX0VISFpLUENCUFk1YXV1ZVU4N21la2F2YmNTaDdCZktnZXNzejFKdGJSWG9vN2ZVbFlVU2VpaHFVRzBERDlOMERWSGFrX2RlR2FMM2U4bXoybmNuemd3cmNtSDdLM1hVbU9EdW15dGRJTk5UTjdpOHVydUNvSzB0UjMwdzNnQmZYQ2lCUHV1eHpQSG1wVlJQWFFQMzZqV3BsdG00U05qZ3J2T3ltWHgzaUJMQmlDTEYwaWNOVDFTa1VtLWZ6Y0RrMFFyWUR3Z3RVV3lYa2ZxRnJNMGt4RGl4QTFLUENQb3BUbHhxSU5TNHRleWlFTTZhRmEtcUVySlRoRm5mTlNTRDN4UW1BdUltZGR0WkdBeDhVNkppM3ciLCJ0eXBlIjoiSnNvbldlYlNpZ25hdHVyZTIwMjAiLCJ2ZXJpZmljYXRpb25NZXRob2QiOiJkaWQ6d2ViOmlwcy50ZXN0aW5nMS5rOHMtY2x1c3Rlci50YW5nby5yaWQtaW50cmFzb2Z0LmV1OmRpZCMxZDljYzVlMWQzMDU0YzE3YmE5NjhlYmQ5OGM5NDNkMCJ9LCJ0eXBlIjpbIkVtcGxveWVlQ3JlZGVudGlhbCJdLCJ2YWxpZEZyb20iOiIyMDI0LTEwLTI0VDA4OjM3OjQ4WiJ9LHsiQGNvbnRleHQiOlsiaHR0cHM6Ly93d3cudzMub3JnLzIwMTgvY3JlZGVudGlhbHMvdjEiLCJodHRwczovL3czaWQub3JnL3NlY3VyaXR5L3N1aXRlcy9qd3MtMjAyMC92MSJdLCJjcmVkZW50aWFsU2NoZW1hIjp7ImlkIjoiaHR0cHM6Ly9yYXcuZ2l0aHVidXNlcmNvbnRlbnQuY29tL0ZJV0FSRS1PcHMvdGVjaC14LWNoYWxsZW5nZS9tYWluL3NjaGVtYS5qc29uIiwidHlwZSI6IkZ1bGxKc29uU2NoZW1hVmFsaWRhdG9yMjAyMSJ9LCJjcmVkZW50aWFsU3ViamVjdCI6eyJlbWFpbCI6InN0YW5kYXJkLWVtcGxveWVlQGlwcy5vcmciLCJpZCI6ImU2ZWE3MmIzLTkwMmUtNGMxMC1hMGNjLTk2ZmQ4MWY5N2YxZiIsImxhc3ROYW1lIjoiSVBTIiwicm9sZXMiOlt7Im5hbWVzIjpbIkdPTERfQ1VTVE9NRVIiXSwidGFyZ2V0IjoiZGlkOndlYjppcHMuazhzLWNsdXN0ZXIudGFuZ28ucmlkLWludHJhc29mdC5ldTpkaWQifV0sInR5cGUiOiJneDpOYXR1cmFsUGFydGljaXBhbnQifSwiaWQiOiJ1cm46dXVpZDozNzMxM2NmNy0wZmNjLTRmZjktYmE4Mi0zYWU0YmU0NzRmYjMiLCJpc3N1YW5jZURhdGUiOiIyMDI0LTExLTE5VDA5OjIwOjA3WiIsImlzc3VlZCI6IjIwMjQtMTEtMTlUMDk6MjA6MDdaIiwiaXNzdWVyIjoiZGlkOndlYjppcHMuazhzLWNsdXN0ZXIudGFuZ28ucmlkLWludHJhc29mdC5ldTpkaWQiLCJwcm9vZiI6eyJjcmVhdGVkIjoiMjAyNC0xMS0xOVQwOToyMDowN1oiLCJqd3MiOiJleUppTmpRaU9tWmhiSE5sTENKamNtbDBJanBiSW1JMk5DSmRMQ0poYkdjaU9pSlFVekkxTmlKOS4uTkYzYWNUR3JDYm1ITUhwaDVYazRuSmVTRFRnRzFLME1lc182Tnl2RFRTUUFISWRLVFRSN1dZT3JsdkhPX0MzN241V3daSkxCd0N1SERUUkJFa2FKRGNkLTlOQWdwcmJUbmZSLVpUVlBiZ1Y0MzRYcThnbldDRXBkQ1dkV3U4akFPNnpLdU9WWWlBODBZT2hkVVdHY21kMnh1cWhlMWZHd1dHVk1xdlZJSWU3anZwMkR5T0ZoUW5pVDBnQ2psRHdUcFVmZW51aUNGVnZLQTFiODV3aHF1TkRIT0lnSDZsMmxCVW5aRGtlOUVLdENXY0owQzlMTHVZRHRhdWs0RHZKNEh0anQyN2VleldpRjlpWUZWYmc5S1h1UmhhWGdTcUpSX1NQVVVMVHROdUJXX3VjMXZVSWU3SW5PNlpKNzhWd1NlS0hkNE1WMWg1WlNVVjlQS3k4NnlRIiwidHlwZSI6Ikpzb25XZWJTaWduYXR1cmUyMDIwIiwidmVyaWZpY2F0aW9uTWV0aG9kIjoiZGlkOndlYjppcHMuazhzLWNsdXN0ZXIudGFuZ28ucmlkLWludHJhc29mdC5ldTpkaWQjODE2NGU3ZWExOTZhNDU3Zjg2YjNmODg3YWI2OTE2MmIifSwidHlwZSI6WyJFbXBsb3llZUNyZWRlbnRpYWwiXSwidmFsaWRGcm9tIjoiMjAyNC0xMS0xOVQwOToyMDowN1oifV19.oluM_w-iSQOvDvrJc9-cGE7ElFR0uBmBS91ifyEyjv60qcoxZqU7ZAzxtRJJWJmF9gTVFqrLPGzrcTbKKbsmVw\"\n" +
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
		 
		 @Order(13)
		@Test
		void testAccessWithTokenP() {
			System.out.println("Test 11 for TANGO: Doing action GET in the resource /pressure with the Capability Token received \n" );
			
			
			String requestBody = "{\n" + "    \"ct\": " + token + ",\n" + "     \"sar\":{\n"
					+ "        \"action\":\"GET\",\n" + "        \"resource\":\"/resource/pressure\"\n" + "    }\n"+ "}";
			
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
		 
		 @Order(14)
			@Test
			void testAuthEndpoint() {
				 System.out.println("Test 1 for New Policies for TANGO: Request access using the Long-Lived JWT \n" );
					
					
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
					assertTrue(!responseEntity.getBody().isEmpty());
			 }
			 
			 @Order(15)
				@Test
				void testNewPoliciesEndpoint() {
					 System.out.println("Test 2 for New Policies for TANGO: Request publishing new policy using the Short-Lived JWT issued  \n" );
						
						
					 String requestBody = "{\n" +
							    "    \"jwtAuth\": \"" + shortLivedToken + "\",\n" +
							    "    \"policy\": \"{\\\"id\\\":\\\"123\\\",\\\"nombre\\\":\\\"Student Information\\\",\\\"purpose\\\":\\\"Reveal id and name of the BachelorDegree's student in MIT.\\\",\\\"serviceProvider\\\":\\\"did:ServiceProvider:1\\\",\\\"accessRights\\\":[{\\\"resource\\\":\\\"/temperatura\\\",\\\"action\\\":\\\"GET\\\"}],\\\"authTime\\\":1642058400,\\\"minTrustScore\\\":0.5,\\\"constraints\\\":{\\\"fields\\\":[{\\\"purpose\\\":\\\"Reveal name\\\",\\\"name\\\":\\\"Student name\\\",\\\"path\\\":[\\\"$.id\\\"],\\\"filter\\\":{\\\"type\\\":\\\"string\\\",\\\"pattern\\\":\\\"^[a-zA-Z0-9]+$\\\"}}]}}\",\n" +
							    "    \"resource\": \"/temperatura\"\n" +
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
			 
			 @Order(16)
				@Test
				void testWrongPolicy() {
				 System.out.println("Test 3 for New Policies for TANGO: Request publishing new policy using a wrong policy \n" );
					
					
				 String requestBody = "{\n" +
						    "    \"jwtAuth\": \"" + shortLivedToken + "\",\n" +
						    "    \"policy\": \"\",\n" +
						    "    \"resource\": \"/temperatura\"\n" +
						    "}";

					HttpHeaders headers = new HttpHeaders();
					headers.setContentType(MediaType.APPLICATION_JSON);
					HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
			
					ResponseEntity<String> responseEntity = restTemplate.exchange("/api/new-policy", HttpMethod.POST,
							requestEntity, String.class);

					// Verify 400 Bad Request 
					assertTrue(responseEntity.getStatusCode().is4xxClientError());

				 }
			 
			 @Order(17)
				@Test
				void testWrongAuthEndpoint() {
					 System.out.println("Test 4 for New Policies for TANGO: Request access using a wrong Long-Lived JWT \n" );
						
						
						String requestBody =  "{\n" +
							    "    \"sub\": \"PAT\",\n" +
							    "    \"scope\": \"policies\"\n" +
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
			 
			 @Order(18)
				@Test
				void testWrongShortLivedJWT() {
					 System.out.println("Test 5 for New Policies for TANGO: Request publishing new policy using a wrong Short-Lived JWT  \n" );
						
						
					 String requestBody = "{\n" +
							    "    \"jwtAuth\": \"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJUQU5HTyBQRVBcL1BEUCBDb21wb25lbnQiLCJzdWIiOiJQQVQiLCJleHAiOjE3MzE1ODg2NzMsImlhdCI6MTczMTU4NTA3Mywic2NvcGUiOiJwb2xpY2llcyJ9.qiQAQuCyaOeuoUj0DjwXimTIAWlOp-IolIGHhdAhglnhdRQz8SyviGoqevI0pKf8b_UCOJhx6evU9iZEC0wshivJiY6A3PERuWhgGcLWVuY3xsmSZs4gO4b9eHXG8E_COK18h8tkNpycXVzqnPqWZKf9pweQtgw3o5VjXoFvkC8BWL_Gni3ZejiWsYGVm3yx935coyE7vtAZgw_zhPEje3YF494yXAUsher7JbAaFmve75wTVNroEO5_TdW8sp3UU7F\",\n" +
							    "    \"policy\": \"{\\\"id\\\":\\\"123\\\",\\\"nombre\\\":\\\"Student Information\\\",\\\"purpose\\\":\\\"Reveal id and name of the BachelorDegree's student in MIT.\\\",\\\"serviceProvider\\\":\\\"did:ServiceProvider:1\\\",\\\"accessRights\\\":[{\\\"resource\\\":\\\"/temperatura\\\",\\\"action\\\":\\\"GET\\\"}],\\\"authTime\\\":1642058400,\\\"minTrustScore\\\":0.5,\\\"constraints\\\":{\\\"fields\\\":[{\\\"purpose\\\":\\\"Reveal name\\\",\\\"name\\\":\\\"Student name\\\",\\\"path\\\":[\\\"$.id\\\"],\\\"filter\\\":{\\\"type\\\":\\\"string\\\",\\\"pattern\\\":\\\"^[a-zA-Z0-9]+$\\\"}}]}}\",\n" +
							    "    \"resource\": \"/temperatura\"\n" +
							    "}";

						
						HttpHeaders headers = new HttpHeaders();
						headers.setContentType(MediaType.APPLICATION_JSON);
						HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
				
						ResponseEntity<String> responseEntity = restTemplate.exchange("/api/new-policy", HttpMethod.POST,
								requestEntity, String.class);

					

						// Verify 403 Forbidden
						assertTrue(responseEntity.getStatusCode().is4xxClientError());

				 }
			 

}
