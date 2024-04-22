package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.example.demo.idAgent.IdentityAgent;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,properties = {"server.port=8088"})

public class DemoApplicationEratosthenesFailComponentTest {
	
			String token=null;
			IdentityAgent idAg=new IdentityAgent();
			
			public DemoApplicationEratosthenesFailComponentTest() {
				 // Arguments
			        System.setProperty("pdpConfig", "eratosthenes");
			}
			  @LocalServerPort
			    private int port=8088;
			 
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

