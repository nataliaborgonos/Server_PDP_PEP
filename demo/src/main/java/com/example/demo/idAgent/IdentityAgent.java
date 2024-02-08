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
	
	//Initialize token for the wallet's operations 
	String authToken="token"; 
	
	public IdentityAgent() {
		
	}
	
	public void createWallet(String user) {  
		
		//Create a wallet for the requester
		
		System.setProperty("javax.net.ssl.trustStore", "/home/natalia/eclipse-workspace/TFG_/ec-cakey.jks");
		System.setProperty("javax.net.ssl.trustStorePassword", "hola123");

		CertificateFactory certificateFactory=null;
	    
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
		    
			//Unlock that wallet and get a token
		    
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
	            System.out.println("Response Code: " + responseCode);

	            String responseBody = response.body();
	            System.out.println("Response Message: " + responseBody);

	            JsonParser jsonParser = new JsonParser();
	            JsonObject jsonObject = jsonParser.parse(responseBody).getAsJsonObject();

	            authToken = jsonObject.get("token").getAsString();
	         

	        } catch (Exception e) {
	            System.err.println("Wallet already unlocked. The auth token hasn't expired yet.");
	        }
		    
	    }
	
	public boolean verifyPresentation(String VPjson) {
	
		// Verify the requester's Verifiable Presentation 
		System.setProperty("javax.net.ssl.trustStore", "/home/natalia/eclipse-workspace/TFG_/ec-cakey.jks");
		System.setProperty("javax.net.ssl.trustStorePassword", "hola123");

		CertificateFactory certificateFactory=null;
	    
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
					+ "    \"presentation\":"+VPjson+",\n"
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
            System.out.println("Response Code: " + responseCode);

            String responseBody = response.body();
            System.out.println("Response Message: " + responseBody);


            if(responseCode==200) {
	            if (responseBody.contains("error")) {
	                System.out.println("Error: Verifiable Presentation can't be verified." );
	                return false;
	            }
	
	            else if (responseBody.contains("verified")) {
	                System.out.println("Verifiable Presentation has been successfully verified.");
	                return true;
	            }
            } else {
            	System.out.println(responseBody);
            }
        

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
