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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
	@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
	@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DemoApplicationNewPolicies {

		 public DemoApplicationNewPolicies() {
			 // Arguments
		        System.setProperty("pdpConfig", "test");
		}
		
		String token;
		
		@Autowired
		private TestRestTemplate restTemplate;
		
		 @Order(1)
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
				token = responseEntity.getBody();

				// Verify 200 OK
				assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

				// Verify that there's a JWT Short Lived and is not null
				assertNotNull(responseEntity.getBody());
				assertTrue(!responseEntity.getBody().isEmpty());
		 }
		 
		 @Order(2)
			@Test
			void testNewPoliciesEndpoint() {
				 System.out.println("Test 2 for New Policies for TANGO: Request publishing new policy using the Short-Lived JWT issued  \n" );
					
					
				 String requestBody = "{\n" +
						    "    \"jwtAuth\": \"" + token + "\",\n" +
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
		 
		 @Order(3)
			@Test
			void testWrongPolicy() {
			 System.out.println("Test 3 for New Policies for TANGO: Request publishing new policy using a wrong policy \n" );
				
				
			 String requestBody = "{\n" +
					    "    \"jwtAuth\": \"" + token + "\",\n" +
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
		 
		 @Order(4)
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
		 
		 @Order(5)
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
