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
			System.out.println("Test 1 for TANGO: Request access for doing action GET in the resource /temperature and getting the Capability Token \n" );
			
			
			String requestBody =  "{\n" +
				    "    \"didSP\": \"tangoUser\",\n" +
				    "    \"sar\": {\n" +
				    "        \"action\":\"GET\",\n" +
				    "        \"resource\":\"/resource/temperature\"\n" +
				    "    },\n" +
				    "    \"didRequester\": \"did\",\n" +
				    "    \"accessToken\": \"eyJhbGciOiJFUzI1NiIsImtpZCI6IkN3YjlhMzNnVVE3Wmw0amlJZTI3alkwc1oweUd0ZXRVamxqdUFsb3RSWnciLCJ0eXAiOiJKV1QifQ.eyJhdWQiOlsicG9ydGFsLnRlc3RpbmcxLms4cy1jbHVzdGVyLnRhbmdvLnJpZC1pbnRyYXNvZnQuZXUiXSwiY2xpZW50X2lkIjoiZGlkOndlYjppcHMudGVzdGluZzEuazhzLWNsdXN0ZXIudGFuZ28ucmlkLWludHJhc29mdC5ldTpkaWQiLCJleHAiOjE3Mjg1NTUwNDEsImlzcyI6ImRpZDp3ZWI6aXBzLnRlc3RpbmcxLms4cy1jbHVzdGVyLnRhbmdvLnJpZC1pbnRyYXNvZnQuZXU6ZGlkIiwia2lkIjoiQ3diOWEzM2dVUTdabDRqaUllMjdqWTBzWjB5R3RldFVqbGp1QWxvdFJadyIsInN1YiI6ImRpZDpteTp3YWxsZXQiLCJ2ZXJpZmlhYmxlQ3JlZGVudGlhbCI6eyJAY29udGV4dCI6WyJodHRwczovL3d3dy53My5vcmcvMjAxOC9jcmVkZW50aWFscy92MSIsImh0dHBzOi8vdzNpZC5vcmcvc2VjdXJpdHkvc3VpdGVzL2p3cy0yMDIwL3YxIl0sImNyZWRlbnRpYWxTY2hlbWEiOnsiaWQiOiJodHRwczovL3Jhdy5naXRodWJ1c2VyY29udGVudC5jb20vRklXQVJFLU9wcy90ZWNoLXgtY2hhbGxlbmdlL21haW4vc2NoZW1hLmpzb24iLCJ0eXBlIjoiRnVsbEpzb25TY2hlbWFWYWxpZGF0b3IyMDIxIn0sImNyZWRlbnRpYWxTdWJqZWN0Ijp7ImVtYWlsIjoic3RhbmRhcmQtZW1wbG95ZWVAaXBzLm9yZyIsImlkIjoiMDJhYjdiYjYtYjMyZS00MjEyLTljYWQtNWZhYTM5M2VmMjcwIiwibGFzdE5hbWUiOiJJUFMiLCJyb2xlcyI6W3sibmFtZXMiOlsiR09MRF9DVVNUT01FUiJdLCJ0YXJnZXQiOiJkaWQ6d2ViOmlwcy5rOHMtY2x1c3Rlci50YW5nby5yaWQtaW50cmFzb2Z0LmV1OmRpZCJ9XSwidHlwZSI6Imd4Ok5hdHVyYWxQYXJ0aWNpcGFudCJ9LCJpZCI6InVybjp1dWlkOjI5NWE0YzdlLTQzNjEtNDYwNS1iOTY3LTMyZDkzNzRlN2E2YSIsImlzc3VhbmNlRGF0ZSI6IjIwMjQtMTAtMDlUMTA6MTg6MTBaIiwiaXNzdWVkIjoiMjAyNC0xMC0wOVQxMDoxODoxMFoiLCJpc3N1ZXIiOiJkaWQ6d2ViOmlwcy5rOHMtY2x1c3Rlci50YW5nby5yaWQtaW50cmFzb2Z0LmV1OmRpZCIsInByb29mIjp7ImNyZWF0ZWQiOiIyMDI0LTEwLTA5VDEwOjE4OjEwWiIsImp3cyI6ImV5SmlOalFpT21aaGJITmxMQ0pqY21sMElqcGJJbUkyTkNKZExDSmhiR2NpT2lKUVV6STFOaUo5Li5NSmc1VWhEbUszaS0tSEx5eXNxU3RpVlVla1BsVUFKeHprdzJfMXFhcHhnTVdxZGZGTVN3NHYyUFdERVpBVmZVcXVaUDVXRnhEMmNQOU5MbTVOb3hQNDJkZ0x1U0t1Tm4zam1MaWxNcE9QeHE5ejhuY1lqdi1SQ2wyRTZlTEx3cXZ2NWQ0WUFtRFBHZ25Jd3hiZEF2X0tPS0g4TEF6MTdRNWh6RmtEa1lqcXdseDdQc1JJdERvOUd4Zkx0RlVoRHlOWEV1MFZaSE9oSjVxMnpJTTAyQkhxSE9SQ1BPSXdNaDhZMDhURWEwcUdsT1NFbmplVUFsQWRQbmMtRGZ4cnItOEpzQnpMMTh1TUVaVGx3ZFN1bUZwU0ZGN0RwTk1nd0dqVy1MZlBia1E5c3hoWHcwelBENXdNUUhqV3pKQktHVW5ERjRvR2dWeXZpbHd1bDQ4a3hCQ2ciLCJ0eXBlIjoiSnNvbldlYlNpZ25hdHVyZTIwMjAiLCJ2ZXJpZmljYXRpb25NZXRob2QiOiJkaWQ6d2ViOmlwcy5rOHMtY2x1c3Rlci50YW5nby5yaWQtaW50cmFzb2Z0LmV1OmRpZCM4MTY0ZTdlYTE5NmE0NTdmODZiM2Y4ODdhYjY5MTYyYiJ9LCJ0eXBlIjpbIkVtcGxveWVlQ3JlZGVudGlhbCJdLCJ2YWxpZEZyb20iOiIyMDI0LTEwLTA5VDEwOjE4OjEwWiJ9fQ.2tp2L-a4AFEJ_dZCT1t3en9-OM9sN6-HIkKLmgyLJirO_xgyDnAksVMBzU3KRmaAQ_kYtzJVPmr0iL_i3K8LiA\"\n" +
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
		 @Order(3)
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
		 @Order(4)
			@Test
			void testNotMatchingPolicy() {
				System.out.println("Test 4 for TANGO: Request access for doing action GET in the resource /temperature and not getting the Capability Token because the token is expired and the policy matching failed \n" );
				
				
				String requestBody =  "{\n" +
					    "    \"didSP\": \"tangoUser\",\n" +
					    "    \"sar\": {\n" +
					    "        \"action\":\"GET\",\n" +
					    "        \"resource\":\"/resource/temperature\"\n" +
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
		 
		 @Order(5)
			@Test
			void testNotVerifyingSignature() {
				System.out.println("Test 5 for TANGO: Request access for doing action GET in the resource /temperature and not getting the Capability Token because access token signature couldn't be verified \n" );
				
				
				String requestBody =  "{\n" +
					    "    \"didSP\": \"tangoUser\",\n" +
					    "    \"sar\": {\n" +
					    "        \"action\":\"GET\",\n" +
					    "        \"resource\":\"/resource/temperature\"\n" +
					    "    },\n" +
					    "    \"didRequester\": \"did\",\n" +
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
		 
		 @Order(6)
			@Test
			void testRequestAccessEndpointPost() {
				System.out.println("Test 6 for TANGO: Request access for doing action POST in the resource /temperature and getting the Capability Token \n" );
				
				
				String requestBody =  "{\n" +
					    "    \"didSP\": \"tangoUser\",\n" +
					    "    \"sar\": {\n" +
					    "        \"action\":\"POST\",\n" +
					    "        \"resource\":\"/resource/temperature\"\n" +
					    "    },\n" +
					    "    \"didRequester\": \"did\",\n" +
					    "    \"accessToken\": \"eyJhbGciOiJFUzI1NiIsImtpZCI6IkN3YjlhMzNnVVE3Wmw0amlJZTI3alkwc1oweUd0ZXRVamxqdUFsb3RSWnciLCJ0eXAiOiJKV1QifQ.eyJhdWQiOlsicG9ydGFsLnRlc3RpbmcxLms4cy1jbHVzdGVyLnRhbmdvLnJpZC1pbnRyYXNvZnQuZXUiXSwiY2xpZW50X2lkIjoiZGlkOndlYjppcHMudGVzdGluZzEuazhzLWNsdXN0ZXIudGFuZ28ucmlkLWludHJhc29mdC5ldTpkaWQiLCJleHAiOjE3Mjg1NTUwNDEsImlzcyI6ImRpZDp3ZWI6aXBzLnRlc3RpbmcxLms4cy1jbHVzdGVyLnRhbmdvLnJpZC1pbnRyYXNvZnQuZXU6ZGlkIiwia2lkIjoiQ3diOWEzM2dVUTdabDRqaUllMjdqWTBzWjB5R3RldFVqbGp1QWxvdFJadyIsInN1YiI6ImRpZDpteTp3YWxsZXQiLCJ2ZXJpZmlhYmxlQ3JlZGVudGlhbCI6eyJAY29udGV4dCI6WyJodHRwczovL3d3dy53My5vcmcvMjAxOC9jcmVkZW50aWFscy92MSIsImh0dHBzOi8vdzNpZC5vcmcvc2VjdXJpdHkvc3VpdGVzL2p3cy0yMDIwL3YxIl0sImNyZWRlbnRpYWxTY2hlbWEiOnsiaWQiOiJodHRwczovL3Jhdy5naXRodWJ1c2VyY29udGVudC5jb20vRklXQVJFLU9wcy90ZWNoLXgtY2hhbGxlbmdlL21haW4vc2NoZW1hLmpzb24iLCJ0eXBlIjoiRnVsbEpzb25TY2hlbWFWYWxpZGF0b3IyMDIxIn0sImNyZWRlbnRpYWxTdWJqZWN0Ijp7ImVtYWlsIjoic3RhbmRhcmQtZW1wbG95ZWVAaXBzLm9yZyIsImlkIjoiMDJhYjdiYjYtYjMyZS00MjEyLTljYWQtNWZhYTM5M2VmMjcwIiwibGFzdE5hbWUiOiJJUFMiLCJyb2xlcyI6W3sibmFtZXMiOlsiR09MRF9DVVNUT01FUiJdLCJ0YXJnZXQiOiJkaWQ6d2ViOmlwcy5rOHMtY2x1c3Rlci50YW5nby5yaWQtaW50cmFzb2Z0LmV1OmRpZCJ9XSwidHlwZSI6Imd4Ok5hdHVyYWxQYXJ0aWNpcGFudCJ9LCJpZCI6InVybjp1dWlkOjI5NWE0YzdlLTQzNjEtNDYwNS1iOTY3LTMyZDkzNzRlN2E2YSIsImlzc3VhbmNlRGF0ZSI6IjIwMjQtMTAtMDlUMTA6MTg6MTBaIiwiaXNzdWVkIjoiMjAyNC0xMC0wOVQxMDoxODoxMFoiLCJpc3N1ZXIiOiJkaWQ6d2ViOmlwcy5rOHMtY2x1c3Rlci50YW5nby5yaWQtaW50cmFzb2Z0LmV1OmRpZCIsInByb29mIjp7ImNyZWF0ZWQiOiIyMDI0LTEwLTA5VDEwOjE4OjEwWiIsImp3cyI6ImV5SmlOalFpT21aaGJITmxMQ0pqY21sMElqcGJJbUkyTkNKZExDSmhiR2NpT2lKUVV6STFOaUo5Li5NSmc1VWhEbUszaS0tSEx5eXNxU3RpVlVla1BsVUFKeHprdzJfMXFhcHhnTVdxZGZGTVN3NHYyUFdERVpBVmZVcXVaUDVXRnhEMmNQOU5MbTVOb3hQNDJkZ0x1U0t1Tm4zam1MaWxNcE9QeHE5ejhuY1lqdi1SQ2wyRTZlTEx3cXZ2NWQ0WUFtRFBHZ25Jd3hiZEF2X0tPS0g4TEF6MTdRNWh6RmtEa1lqcXdseDdQc1JJdERvOUd4Zkx0RlVoRHlOWEV1MFZaSE9oSjVxMnpJTTAyQkhxSE9SQ1BPSXdNaDhZMDhURWEwcUdsT1NFbmplVUFsQWRQbmMtRGZ4cnItOEpzQnpMMTh1TUVaVGx3ZFN1bUZwU0ZGN0RwTk1nd0dqVy1MZlBia1E5c3hoWHcwelBENXdNUUhqV3pKQktHVW5ERjRvR2dWeXZpbHd1bDQ4a3hCQ2ciLCJ0eXBlIjoiSnNvbldlYlNpZ25hdHVyZTIwMjAiLCJ2ZXJpZmljYXRpb25NZXRob2QiOiJkaWQ6d2ViOmlwcy5rOHMtY2x1c3Rlci50YW5nby5yaWQtaW50cmFzb2Z0LmV1OmRpZCM4MTY0ZTdlYTE5NmE0NTdmODZiM2Y4ODdhYjY5MTYyYiJ9LCJ0eXBlIjpbIkVtcGxveWVlQ3JlZGVudGlhbCJdLCJ2YWxpZEZyb20iOiIyMDI0LTEwLTA5VDEwOjE4OjEwWiJ9fQ.2tp2L-a4AFEJ_dZCT1t3en9-OM9sN6-HIkKLmgyLJirO_xgyDnAksVMBzU3KRmaAQ_kYtzJVPmr0iL_i3K8LiA\"\n" +
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
		 
		 @Order(7)
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
		 
		 @Order(8)
			@Test
			void testRequestAccessEndpointGetH() {
				System.out.println("Test 8 for TANGO: Request access for doing action GET in the resource /humidity and getting the Capability Token \n" );
				
				
				String requestBody =  "{\n" +
					    "    \"didSP\": \"tangoUser\",\n" +
					    "    \"sar\": {\n" +
					    "        \"action\":\"GET\",\n" +
					    "        \"resource\":\"/resource/humidity\"\n" +
					    "    },\n" +
					    "    \"didRequester\": \"did\",\n" +
					    "    \"accessToken\": \"eyJhbGciOiJFUzI1NiIsImtpZCI6IkN3YjlhMzNnVVE3Wmw0amlJZTI3alkwc1oweUd0ZXRVamxqdUFsb3RSWnciLCJ0eXAiOiJKV1QifQ.eyJhdWQiOlsicG9ydGFsLnRlc3RpbmcxLms4cy1jbHVzdGVyLnRhbmdvLnJpZC1pbnRyYXNvZnQuZXUiXSwiY2xpZW50X2lkIjoiZGlkOndlYjppcHMudGVzdGluZzEuazhzLWNsdXN0ZXIudGFuZ28ucmlkLWludHJhc29mdC5ldTpkaWQiLCJleHAiOjE3Mjg1NTUwNDEsImlzcyI6ImRpZDp3ZWI6aXBzLnRlc3RpbmcxLms4cy1jbHVzdGVyLnRhbmdvLnJpZC1pbnRyYXNvZnQuZXU6ZGlkIiwia2lkIjoiQ3diOWEzM2dVUTdabDRqaUllMjdqWTBzWjB5R3RldFVqbGp1QWxvdFJadyIsInN1YiI6ImRpZDpteTp3YWxsZXQiLCJ2ZXJpZmlhYmxlQ3JlZGVudGlhbCI6eyJAY29udGV4dCI6WyJodHRwczovL3d3dy53My5vcmcvMjAxOC9jcmVkZW50aWFscy92MSIsImh0dHBzOi8vdzNpZC5vcmcvc2VjdXJpdHkvc3VpdGVzL2p3cy0yMDIwL3YxIl0sImNyZWRlbnRpYWxTY2hlbWEiOnsiaWQiOiJodHRwczovL3Jhdy5naXRodWJ1c2VyY29udGVudC5jb20vRklXQVJFLU9wcy90ZWNoLXgtY2hhbGxlbmdlL21haW4vc2NoZW1hLmpzb24iLCJ0eXBlIjoiRnVsbEpzb25TY2hlbWFWYWxpZGF0b3IyMDIxIn0sImNyZWRlbnRpYWxTdWJqZWN0Ijp7ImVtYWlsIjoic3RhbmRhcmQtZW1wbG95ZWVAaXBzLm9yZyIsImlkIjoiMDJhYjdiYjYtYjMyZS00MjEyLTljYWQtNWZhYTM5M2VmMjcwIiwibGFzdE5hbWUiOiJJUFMiLCJyb2xlcyI6W3sibmFtZXMiOlsiR09MRF9DVVNUT01FUiJdLCJ0YXJnZXQiOiJkaWQ6d2ViOmlwcy5rOHMtY2x1c3Rlci50YW5nby5yaWQtaW50cmFzb2Z0LmV1OmRpZCJ9XSwidHlwZSI6Imd4Ok5hdHVyYWxQYXJ0aWNpcGFudCJ9LCJpZCI6InVybjp1dWlkOjI5NWE0YzdlLTQzNjEtNDYwNS1iOTY3LTMyZDkzNzRlN2E2YSIsImlzc3VhbmNlRGF0ZSI6IjIwMjQtMTAtMDlUMTA6MTg6MTBaIiwiaXNzdWVkIjoiMjAyNC0xMC0wOVQxMDoxODoxMFoiLCJpc3N1ZXIiOiJkaWQ6d2ViOmlwcy5rOHMtY2x1c3Rlci50YW5nby5yaWQtaW50cmFzb2Z0LmV1OmRpZCIsInByb29mIjp7ImNyZWF0ZWQiOiIyMDI0LTEwLTA5VDEwOjE4OjEwWiIsImp3cyI6ImV5SmlOalFpT21aaGJITmxMQ0pqY21sMElqcGJJbUkyTkNKZExDSmhiR2NpT2lKUVV6STFOaUo5Li5NSmc1VWhEbUszaS0tSEx5eXNxU3RpVlVla1BsVUFKeHprdzJfMXFhcHhnTVdxZGZGTVN3NHYyUFdERVpBVmZVcXVaUDVXRnhEMmNQOU5MbTVOb3hQNDJkZ0x1U0t1Tm4zam1MaWxNcE9QeHE5ejhuY1lqdi1SQ2wyRTZlTEx3cXZ2NWQ0WUFtRFBHZ25Jd3hiZEF2X0tPS0g4TEF6MTdRNWh6RmtEa1lqcXdseDdQc1JJdERvOUd4Zkx0RlVoRHlOWEV1MFZaSE9oSjVxMnpJTTAyQkhxSE9SQ1BPSXdNaDhZMDhURWEwcUdsT1NFbmplVUFsQWRQbmMtRGZ4cnItOEpzQnpMMTh1TUVaVGx3ZFN1bUZwU0ZGN0RwTk1nd0dqVy1MZlBia1E5c3hoWHcwelBENXdNUUhqV3pKQktHVW5ERjRvR2dWeXZpbHd1bDQ4a3hCQ2ciLCJ0eXBlIjoiSnNvbldlYlNpZ25hdHVyZTIwMjAiLCJ2ZXJpZmljYXRpb25NZXRob2QiOiJkaWQ6d2ViOmlwcy5rOHMtY2x1c3Rlci50YW5nby5yaWQtaW50cmFzb2Z0LmV1OmRpZCM4MTY0ZTdlYTE5NmE0NTdmODZiM2Y4ODdhYjY5MTYyYiJ9LCJ0eXBlIjpbIkVtcGxveWVlQ3JlZGVudGlhbCJdLCJ2YWxpZEZyb20iOiIyMDI0LTEwLTA5VDEwOjE4OjEwWiJ9fQ.2tp2L-a4AFEJ_dZCT1t3en9-OM9sN6-HIkKLmgyLJirO_xgyDnAksVMBzU3KRmaAQ_kYtzJVPmr0iL_i3K8LiA\"\n" +
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

		 @Order(10)
			@Test
			void testRequestAccessEndpointGetP() {
				System.out.println("Test 10 for TANGO: Request access for doing action GET in the resource /pressure and getting the Capability Token \n" );
				
				
				String requestBody =  "{\n" +
					    "    \"didSP\": \"tangoUser\",\n" +
					    "    \"sar\": {\n" +
					    "        \"action\":\"GET\",\n" +
					    "        \"resource\":\"/resource/pressure\"\n" +
					    "    },\n" +
					    "    \"didRequester\": \"did\",\n" +
					    "    \"accessToken\": \"eyJhbGciOiJFUzI1NiIsImtpZCI6IkN3YjlhMzNnVVE3Wmw0amlJZTI3alkwc1oweUd0ZXRVamxqdUFsb3RSWnciLCJ0eXAiOiJKV1QifQ.eyJhdWQiOlsicG9ydGFsLnRlc3RpbmcxLms4cy1jbHVzdGVyLnRhbmdvLnJpZC1pbnRyYXNvZnQuZXUiXSwiY2xpZW50X2lkIjoiZGlkOndlYjppcHMudGVzdGluZzEuazhzLWNsdXN0ZXIudGFuZ28ucmlkLWludHJhc29mdC5ldTpkaWQiLCJleHAiOjE3Mjg1NTUwNDEsImlzcyI6ImRpZDp3ZWI6aXBzLnRlc3RpbmcxLms4cy1jbHVzdGVyLnRhbmdvLnJpZC1pbnRyYXNvZnQuZXU6ZGlkIiwia2lkIjoiQ3diOWEzM2dVUTdabDRqaUllMjdqWTBzWjB5R3RldFVqbGp1QWxvdFJadyIsInN1YiI6ImRpZDpteTp3YWxsZXQiLCJ2ZXJpZmlhYmxlQ3JlZGVudGlhbCI6eyJAY29udGV4dCI6WyJodHRwczovL3d3dy53My5vcmcvMjAxOC9jcmVkZW50aWFscy92MSIsImh0dHBzOi8vdzNpZC5vcmcvc2VjdXJpdHkvc3VpdGVzL2p3cy0yMDIwL3YxIl0sImNyZWRlbnRpYWxTY2hlbWEiOnsiaWQiOiJodHRwczovL3Jhdy5naXRodWJ1c2VyY29udGVudC5jb20vRklXQVJFLU9wcy90ZWNoLXgtY2hhbGxlbmdlL21haW4vc2NoZW1hLmpzb24iLCJ0eXBlIjoiRnVsbEpzb25TY2hlbWFWYWxpZGF0b3IyMDIxIn0sImNyZWRlbnRpYWxTdWJqZWN0Ijp7ImVtYWlsIjoic3RhbmRhcmQtZW1wbG95ZWVAaXBzLm9yZyIsImlkIjoiMDJhYjdiYjYtYjMyZS00MjEyLTljYWQtNWZhYTM5M2VmMjcwIiwibGFzdE5hbWUiOiJJUFMiLCJyb2xlcyI6W3sibmFtZXMiOlsiR09MRF9DVVNUT01FUiJdLCJ0YXJnZXQiOiJkaWQ6d2ViOmlwcy5rOHMtY2x1c3Rlci50YW5nby5yaWQtaW50cmFzb2Z0LmV1OmRpZCJ9XSwidHlwZSI6Imd4Ok5hdHVyYWxQYXJ0aWNpcGFudCJ9LCJpZCI6InVybjp1dWlkOjI5NWE0YzdlLTQzNjEtNDYwNS1iOTY3LTMyZDkzNzRlN2E2YSIsImlzc3VhbmNlRGF0ZSI6IjIwMjQtMTAtMDlUMTA6MTg6MTBaIiwiaXNzdWVkIjoiMjAyNC0xMC0wOVQxMDoxODoxMFoiLCJpc3N1ZXIiOiJkaWQ6d2ViOmlwcy5rOHMtY2x1c3Rlci50YW5nby5yaWQtaW50cmFzb2Z0LmV1OmRpZCIsInByb29mIjp7ImNyZWF0ZWQiOiIyMDI0LTEwLTA5VDEwOjE4OjEwWiIsImp3cyI6ImV5SmlOalFpT21aaGJITmxMQ0pqY21sMElqcGJJbUkyTkNKZExDSmhiR2NpT2lKUVV6STFOaUo5Li5NSmc1VWhEbUszaS0tSEx5eXNxU3RpVlVla1BsVUFKeHprdzJfMXFhcHhnTVdxZGZGTVN3NHYyUFdERVpBVmZVcXVaUDVXRnhEMmNQOU5MbTVOb3hQNDJkZ0x1U0t1Tm4zam1MaWxNcE9QeHE5ejhuY1lqdi1SQ2wyRTZlTEx3cXZ2NWQ0WUFtRFBHZ25Jd3hiZEF2X0tPS0g4TEF6MTdRNWh6RmtEa1lqcXdseDdQc1JJdERvOUd4Zkx0RlVoRHlOWEV1MFZaSE9oSjVxMnpJTTAyQkhxSE9SQ1BPSXdNaDhZMDhURWEwcUdsT1NFbmplVUFsQWRQbmMtRGZ4cnItOEpzQnpMMTh1TUVaVGx3ZFN1bUZwU0ZGN0RwTk1nd0dqVy1MZlBia1E5c3hoWHcwelBENXdNUUhqV3pKQktHVW5ERjRvR2dWeXZpbHd1bDQ4a3hCQ2ciLCJ0eXBlIjoiSnNvbldlYlNpZ25hdHVyZTIwMjAiLCJ2ZXJpZmljYXRpb25NZXRob2QiOiJkaWQ6d2ViOmlwcy5rOHMtY2x1c3Rlci50YW5nby5yaWQtaW50cmFzb2Z0LmV1OmRpZCM4MTY0ZTdlYTE5NmE0NTdmODZiM2Y4ODdhYjY5MTYyYiJ9LCJ0eXBlIjpbIkVtcGxveWVlQ3JlZGVudGlhbCJdLCJ2YWxpZEZyb20iOiIyMDI0LTEwLTA5VDEwOjE4OjEwWiJ9fQ.2tp2L-a4AFEJ_dZCT1t3en9-OM9sN6-HIkKLmgyLJirO_xgyDnAksVMBzU3KRmaAQ_kYtzJVPmr0iL_i3K8LiA\"\n" +
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

}
