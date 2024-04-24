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

import static org.junit.jupiter.api.Assertions.assertNotNull;
	import static org.junit.jupiter.api.Assertions.assertTrue;

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

		 public DemoApplicationConnectorToken() {
			 // Arguments
		        System.setProperty("pdpConfig", "test");
		}
		
		String token;
		
		@Autowired
		private TestRestTemplate restTemplate;
		
		 @Order(1)
		@Test
		void testRequestAccessEndpoint() {
			System.out.println("Test 1 for TANGO: Request access for doing action GET in the resource /temperatura and getting the Capability Token \n" );
			
			
			String requestBody =  "{\n" +
				    "    \"didSP\": \"tangoUser\",\n" +
				    "    \"sar\": {\n" +
				    "        \"action\":\"GET\",\n" +
				    "        \"resource\":\"/temperatura\"\n" +
				    "    },\n" +
				    "    \"didRequester\": \"did\",\n" +
				    "    \"accessToken\": \"eyJhbGciOiJFUzI1NiIsImtpZCI6Ik9XOFdKcDJRREtyRldUNVpPZ2IzWWd2eXRNVGR6RWFMNU93bm5MWkVTVUEiLCJ0eXAiOiJKV1QifQ.eyJhdWQiOlsiMTAuNjQuNDUuNTg6NTAwMCJdLCJjbGllbnRfaWQiOiJkaWQ6d2ViOmlwcy5kc2JhLmF3cy5uaXRsYWIuaW86ZGlkIiwiZXhwIjoxNzA3ODM2MDU1LCJpc3MiOiJkaWQ6d2ViOmlwcy5kc2JhLmF3cy5uaXRsYWIuaW86ZGlkIiwia2lkIjoiT1c4V0pwMlFES3JGV1Q1Wk9nYjNZZ3Z5dE1UZHpFYUw1T3dubkxaRVNVQSIsInN1YiI6ImRpZDpteTp3YWxsZXQiLCJ2ZXJpZmlhYmxlQ3JlZGVudGlhbCI6eyJAY29udGV4dCI6WyJodHRwczovL3d3dy53My5vcmcvMjAxOC9jcmVkZW50aWFscy92MSIsImh0dHBzOi8vdzNpZC5vcmcvc2VjdXJpdHkvc3VpdGVzL2p3cy0yMDIwL3YxIl0sImNyZWRlbnRpYWxTY2hlbWEiOnsiaWQiOiJodHRwczovL3Jhdy5naXRodWJ1c2VyY29udGVudC5jb20vRklXQVJFLU9wcy90ZWNoLXgtY2hhbGxlbmdlL21haW4vc2NoZW1hLmpzb24iLCJ0eXBlIjoiRnVsbEpzb25TY2hlbWFWYWxpZGF0b3IyMDIxIn0sImNyZWRlbnRpYWxTdWJqZWN0Ijp7ImVtYWlsIjoic3RhbmRhcmQtZW1wbG95ZWVAaXBzLm9yZyIsImlkIjoiODJmZDA0MDUtNGFmMC00ZjM5LWEyNzQtOGU4MjM3NGY0N2I1IiwibGFzdE5hbWUiOiJJUFMiLCJyb2xlcyI6W3sibmFtZXMiOlsiR09MRF9DVVNUT01FUiJdLCJ0YXJnZXQiOiJkaWQ6d2ViOmlwcy5kc2JhLmF3cy5uaXRsYWIuaW86ZGlkIn1dLCJ0eXBlIjoiZ3g6TmF0dXJhbFBhcnRpY2lwYW50In0sImlkIjoidXJuOnV1aWQ6OTVmN2QwYTgtYjliNi00ZWIwLTgwNjYtZDUwYjk2ZTc4NjkzIiwiaXNzdWFuY2VEYXRlIjoiMjAyNC0wMi0xM1QxNDowNjo1MloiLCJpc3N1ZWQiOiIyMDI0LTAyLTEzVDE0OjA2OjUyWiIsImlzc3VlciI6ImRpZDp3ZWI6aXBzLmRzYmEuYXdzLm5pdGxhYi5pbzpkaWQiLCJwcm9vZiI6eyJjcmVhdGVkIjoiMjAyNC0wMi0xM1QxNDowNjo1MloiLCJqd3MiOiJleUppTmpRaU9tWmhiSE5sTENKamNtbDBJanBiSW1JMk5DSmRMQ0poYkdjaU9pSlFVekkxTmlKOS4ubnpBdzlZM00wMHVDRG1uemtEVUtfMkdDSU5GNEFPYmJmakMwWWVCSE4wTmhOaERDX2xZMHZPYVVGbkNEaGZCakg3OE1rWmNvaDhjYWYtYXEzcWRYaXRDS2VDbm9OQS1rdXI0azNYNmV0M1JWQkJSQUp2LXJWRXZHVmprcExjVV9YdXN2ZjhOZzVLOU8xRElESHNJOFR2ZDJ1WFRuWEc3ZUpILTgzZnRDZ0VfYzZ1blFQLTNuR1hBdkliSmQxeENQbTgxUlU0clkzRC1VMi0tQzlYbzhLaTJ6bHNNSVFHNVJlSm5XNG02dmNFQzh6a21nQkdURlE3MkhqTWZQdUs3QjV1SndIdXhXTUpWRVZOQ3hPZDdxTFZYMEF2N1NpZDZDZ1VpdTFNWjl6YlN5UnVFdDdrdDFYZVRNRmlkMEx4NDIyMnhydWxnY1VQbHMwSEhjcjJMQTk4cV9lem1fY2ZLWmRUT2pFRWJhZzdWX1BlMjVsbjVSRzBaZnNEYk01WVBFSnlzNDBpMGcyd0lBWEVVVW5CY1pRMmh5SnhPalhaMVBtNGtlcW0ydEFNQXZtUnA3amkzVDhJelJNQU9NT2RRSFJtTE1ENW5idDhfY2lOYlJrVUtDbnBxcjlRQXhSa0NEa0s5Z3B0RzNIV3AydkVwNzVzZWhjbV9DVE9NZ1RTRzJ6UkNBb3E5YmRtNDN4bmpBalNyck5tWHJmRkVuQmxGY3R4TTloekRNMUxMNHBHc1Z1TVJIWFJvUlZIOE9Rbm4zQnVMVWRmNUVQeFFsZTdfanB0ekgxSnBsWDFRMmlfQnBvQVVSWDFKZnVMazViVF9hbktLTmctUXprMGoyNlNETU5uTTl0Qm80ZXVZckVIQV9WVXJYbmVxSm5hMTdVanN2R0lUMzFXaUtWZmsiLCJ0eXBlIjoiSnNvbldlYlNpZ25hdHVyZTIwMjAiLCJ2ZXJpZmljYXRpb25NZXRob2QiOiJkaWQ6d2ViOmlwcy5kc2JhLmF3cy5uaXRsYWIuaW86ZGlkIzM4OWUxOTNlYzI5ZTQ0ODVhMjljOWYyYTMzY2Q0NDYwIn0sInR5cGUiOlsiRW1wbG95ZWVDcmVkZW50aWFsIl0sInZhbGlkRnJvbSI6IjIwMjQtMDItMTNUMTQ6MDY6NTJaIn19.eKqz-eduU-SDkvuPYFu6UvVM7Ga5Xa6VsNDk4L_S113ugIBLEb_QXbxyWlxdP4XXSuBbQTiDz0FAx15wgmFXvQ\"\n" +
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
		 @Order(2)
		@Test
		void testAccessWithToken() {
			System.out.println("Test 2 for TANGO: Doing action GET in the resource /temperatura with the Capability Token received \n" );
			
			
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
			assertNotEquals("ERROR: Validation failed. Capability Token is not valid. The requester couldn't access to the resource.\n", responseEntity.getBody());

		}
		 @Order(3)
		@Test
		void testAccessWithWrongToken() {
			System.out.println("Test 3 for TANGO: Trying to do action POST in the resource /temperatura with the Capability Token received \n" );
			
			String requestBody = "{\n" + "    \"ct\": " + token + ",\n" + "     \"sar\":{\n"
					+ "        \"action\":\"POST\",\n" + "        \"resource\":\"/temperatura\"\n" + "    }\n" + "}";

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

			ResponseEntity<String> responseEntity = restTemplate.exchange("/api/access-with-token", HttpMethod.POST,
					requestEntity, String.class);

		
			// Verify that it has been a successful validation
			assertEquals("ERROR: Validation failed. Capability Token is not valid. The requester couldn't access to the resource.\n", responseEntity.getBody());

		}
		 @Order(4)
			@Test
			void testNotMatchingPolicy() {
				System.out.println("Test 4 for TANGO: Request access for doing action GET in the resource /temperatura and not getting the Capability Token because the policy matching failed \n" );
				
				
				String requestBody =  "{\n" +
					    "    \"didSP\": \"tangoUser\",\n" +
					    "    \"sar\": {\n" +
					    "        \"action\":\"GET\",\n" +
					    "        \"resource\":\"/temperatura\"\n" +
					    "    },\n" +
					    "    \"didRequester\": \"did\",\n" +
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
		 
		 //MISSING TEST WHERE THE SIGNATURE IS NOT VERIFIED -> WAITING FOR THE PUBLIC KEY
}
