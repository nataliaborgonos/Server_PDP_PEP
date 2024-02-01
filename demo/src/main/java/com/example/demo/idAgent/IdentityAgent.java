package com.example.demo.idAgent;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import com.example.demo.models.VPresentation;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class IdentityAgent {
	
	
	private static final String KEYSTORE = "/home/natalia/eclipse-workspace/TFG_/ec-cakey.jks";
	private static final char[] KEYSTOREPWD = "hola123".toCharArray();
    private static final String ALIAS="myserver";
    
	Gson gson = new Gson();
	//Token para hacer operaciones sobre la wallet
	String authToken="token"; 
	
	public IdentityAgent() {
		
	}
	
	
	//PRUEBAS DE LA API
	
	
	//Crear una wallet para el solicitante 
	//Habilitar esa wallet para hacer operaciones? -> POST /vcwallet/open -> La respuesta devuelve un token para hacer operaciones sobre ella
	public void createWallet(String user) {  
		
		System.setProperty("javax.net.ssl.trustStore", "/home/natalia/eclipse-workspace/TFG_/ec-cakey.jks");
		System.setProperty("javax.net.ssl.trustStorePassword", "hola123");

		CertificateFactory certificateFactory=null;
		  //Crear wallet para el solicitante 
	    
        Path certificatePath = Paths.get("/home/natalia/eclipse-workspace/TFG_/ec-cacert.pem");
        try {
		 certificateFactory = CertificateFactory.getInstance("X.509");
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        X509Certificate certificate = null;
        try (InputStream inputStream = Files.newInputStream(certificatePath)) {
            certificate = (X509Certificate) certificateFactory.generateCertificate(inputStream);
        } catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

        // Configurar el truststore con el certificado del servidor
        KeyStore keyStore = null;
        
		try {
			keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			keyStore.load(new FileInputStream(KEYSTORE), KEYSTOREPWD);
		} catch (NoSuchAlgorithmException | CertificateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			keyStore.setCertificateEntry("serverCert", certificate);
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        // Configurar el SSLContext con el truststore
        TrustManagerFactory trustManagerFactory = null;
		try {
			trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			trustManagerFactory.init(keyStore);
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        SSLContext sslContext = null;
		try {
			sslContext = SSLContext.getInstance("TLS");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			sslContext.init(null, trustManagerFactory.getTrustManagers(), null);
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		    try {
	            String url = "https://localhost:8082/vcwallet/create-profile";
	            String requestBody = "{\r\n" +
	                    "    \"userID\":\"natalia\",\r\n" +
	                    "    \"localKMSPassphrase\":\"pass\"\r\n" +
	                    "}";

	            HttpClient client = HttpClient.newBuilder()
	                    .sslContext(sslContext)
	                    .build();
	            HttpRequest request = HttpRequest.newBuilder()
	                    .uri(URI.create(url))
	                    .header("Content-Type", "application/json")
	                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
	                    .build();
	            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
	            int responseCode = response.statusCode();
	            System.out.println("Código de respuesta: " + responseCode);

	            String responseBody = response.body();
	            System.out.println("Respuesta del servidor: " + responseBody);

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		    
		    //Habilitar la wallet para hacer operaciones -> devuelve el token
		    try {
	            String url = "https://localhost:8082/vcwallet/open";
	            String requestBody = "{\r\n" +
	                    "    \"userID\":\"natalia\",\r\n" +
	                    "    \"localKMSPassphrase\":\"pass\"\r\n" +
	                  //  "    \"expiry\":\"9000000000000\"\r\n"+
	                    "}";

	            HttpClient client = HttpClient.newBuilder()
	                    .sslContext(sslContext)
	                    .build();
	            HttpRequest request = HttpRequest.newBuilder()
	                    .uri(URI.create(url))
	                    .header("Content-Type", "application/json")
	                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
	                    .build();
	            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
	            int responseCode = response.statusCode();
	            System.out.println("Código de respuesta: " + responseCode);

	            String responseBody = response.body();
	            System.out.println("Respuesta del servidor: " + responseBody);

	            // Convertir la cadena JSON a un objeto JsonObject
	            JsonParser jsonParser = new JsonParser();
	            JsonObject jsonObject = jsonParser.parse(responseBody).getAsJsonObject();

	            //Coger el token de respuesta -> meterlo en la variable authToken
	            authToken = jsonObject.get("token").getAsString();
	           

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		    
		    //METER CREDENCIALES EN LA WALLET
		    
	    }
	
	//Método para verificar la VPresentation del solicitante
	public boolean verifyPresentation(String VPjson) {
		// Recibe la VPresentation del solicitante
		//VPresentation vp = gson.fromJson(VPjson, VPresentation.class);

		// Realiza la petición HTTP a la API con los parámetros necesarios en JSON

		System.setProperty("javax.net.ssl.trustStore", "/home/natalia/eclipse-workspace/TFG_/ec-cakey.jks");
		System.setProperty("javax.net.ssl.trustStorePassword", "hola123");

		CertificateFactory certificateFactory=null;
		  //Crear wallet para el solicitante 
	    
        Path certificatePath = Paths.get("/home/natalia/eclipse-workspace/TFG_/ec-cacert.pem");
        try {
		 certificateFactory = CertificateFactory.getInstance("X.509");
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        X509Certificate certificate = null;
        try (InputStream inputStream = Files.newInputStream(certificatePath)) {
            certificate = (X509Certificate) certificateFactory.generateCertificate(inputStream);
        } catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

        // Configurar el truststore con el certificado del servidor
        KeyStore keyStore = null;
        
		try {
			keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			keyStore.load(new FileInputStream(KEYSTORE), KEYSTOREPWD);
		} catch (NoSuchAlgorithmException | CertificateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			keyStore.setCertificateEntry("serverCert", certificate);
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        // Configurar el SSLContext con el truststore
        TrustManagerFactory trustManagerFactory = null;
		try {
			trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			trustManagerFactory.init(keyStore);
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        SSLContext sslContext = null;
		try {
			sslContext = SSLContext.getInstance("TLS");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			sslContext.init(null, trustManagerFactory.getTrustManagers(), null);
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
       
		try {
			String url = "https://localhost:8082/vcwallet/verify";
			String requestBody="{\n"
					+ "    \"auth\":\""+authToken+"\",\n"
					+ "    \"presentation\":{\n"
					+ "    \"@context\": [\"https://www.w3.org/2018/credentials/v1\"],\n"
					+ "    \"holder\": \"did:key:z6MknC1wwS6DEYwtGbZZo2QvjQjkh2qSBjb4GYmbye8dv4S5\",\n"
					+ "    \"proof\": {\n"
					+ "      \"created\": \"2021-03-26T14:08:21.15597-04:00\",\n"
					+ "      \"jws\": \"eyJhbGciOiJFZERTQSIsImI2NCI6ZmFsc2UsImNyaXQiOlsiYjY0Il19..GUbI3psCXXhCjDJ2yBTwteuKSUHJuEK840yJzxWuPPxYyAuza1uwK1v75Az2jO63ILHEsLmxwcEhBlKcTw7ODA\",\n"
					+ "      \"proofPurpose\": \"authentication\",\n"
					+ "      \"type\": \"Ed25519Signature2018\",\n"
					+ "      \"verificationMethod\": \"did:key:z6MknC1wwS6DEYwtGbZZo2QvjQjkh2qSBjb4GYmbye8dv4S5#z6MknC1wwS6DEYwtGbZZo2QvjQjkh2qSBjb4GYmbye8dv4S5\"\n"
					+ "    },\n"
					+ "    \"type\": \"VerifiablePresentation\",\n"
					+ "    \"verifiableCredential\": [{\n"
					+ "      \"@context\": [\"https://www.w3.org/2018/credentials/v1\", \"https://www.w3.org/2018/credentials/examples/v1\", \"https://w3id.org/security/bbs/v1\"],\n"
					+ "      \"credentialSubject\": {\n"
					+ "        \"degree\": {\"type\": \"BachelorDegree\", \"university\": \"MIT\"},\n"
					+ "        \"id\": \"did:example:ebfeb1f712ebc6f1c276e12ec21\",\n"
					+ "        \"name\": \"Jayden Doe\",\n"
					+ "        \"spouse\": \"did:example:c276e12ec21ebfeb1f712ebc6f1\"\n"
					+ "      },\n"
					+ "      \"expirationDate\": \"2020-01-01T19:23:24Z\",\n"
					+ "      \"id\": \"http://example.edu/credentials/1872\",\n"
					+ "      \"issuanceDate\": \"2010-01-01T19:23:24Z\",\n"
					+ "      \"issuer\": {\"id\": \"did:example:76e12ec712ebc6f1c221ebfeb1f\", \"name\": \"Example University\"},\n"
					+ "      \"proof\": {\n"
					+ "        \"created\": \"2021-03-26T14:08:20.898673-04:00\",\n"
					+ "        \"jws\": \"eyJhbGciOiJFZERTQSIsImI2NCI6ZmFsc2UsImNyaXQiOlsiYjY0Il19..PeIllfXnUh7zD4mH24NCnfFFeKf0Fys8XWt8nVE2Z-fgSvE6-3Rbc-LgSIpyKPF20CtFzEdownwOiMavy2_tAQ\",\n"
					+ "        \"proofPurpose\": \"assertionMethod\",\n"
					+ "        \"type\": \"Ed25519Signature2018\",\n"
					+ "        \"verificationMethod\": \"did:key:z6MknC1wwS6DEYwtGbZZo2QvjQjkh2qSBjb4GYmbye8dv4S5#z6MknC1wwS6DEYwtGbZZo2QvjQjkh2qSBjb4GYmbye8dv4S5\"\n"
					+ "      },\n"
					+ "      \"referenceNumber\": 83294847,\n"
					+ "      \"type\": [\"VerifiableCredential\", \"UniversityDegreeCredential\"]\n"
					+ "    }]},\n"
					+ "    \"userid\":\"natalia\"\n"
					+ "  }";
			HttpClient client = HttpClient.newBuilder()
                    .sslContext(sslContext)
                    .build();
	        HttpRequest request = HttpRequest.newBuilder()
	            		 .uri(URI.create(url))
		                    .header("Content-Type", "application/json")
		                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
		                    .build();
		
	        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            int responseCode = response.statusCode();
            System.out.println("Código de respuesta: " + responseCode);

            String responseBody = response.body();
            System.out.println("Respuesta del servidor: " + responseBody);


            if(responseCode==200) {
	            // Obtener propiedades de la respuesta
	            if (responseBody.contains("error")) {
	                System.out.println("Error " );
	                return false;
	            }
	
	            else if (responseBody.contains("verified")) {
	                System.out.println("Verified ");
	                return true;
	            }
            } else {
            	System.out.println(responseBody);
            }
        
            
			// Obtener la respuesta
			// Recibe las respuestas en JSON
			// Verificado con éxito -> boolean
			// Error -> string

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
