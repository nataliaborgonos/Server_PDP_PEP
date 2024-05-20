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

import org.springframework.beans.factory.annotation.Value;

import com.example.demo.models.VPresentation;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class IdentityAgent {

	@Value("${app.IDAGENT_KS:\"/app/crypto/ec-cakey.jks\"}")
	static String keystore;

	@Value("${app.IDAGENT_PW:hola123}")
	static String keystorepwd;

	@Value("${app.IDAGENT_ALIAS:myserver}")
	static String alias;

	@Value("${app.IDAGENT_CERT:\"/app/crypto/ec-cacert.pem\"}")
	static String certificate;

	@Value("${app.IDAGENT_IP:127.21.0.4}")
	static String ipIdAgent;
	
	@Value("${app.IDAGENT_PORT:8082}")
	static String portIdAgent; 
	
	Gson gson = new Gson();

	// Initialize token for the wallet's operations
	String authToken = "token";

	/* CONSTRUCTOR */

	public IdentityAgent() {
		keystore = System.getenv("IDAGENT_KS");
		if(System.getenv("IDAGENT_KS")==null) {
			keystore="crypto/ec-cakey.jks";
		}
		
		keystorepwd = System.getenv("IDAGENT_PW");
		if(System.getenv("IDAGENT_PW")==null) {
			keystorepwd="hola123";
		}
		
		alias = System.getenv("IDAGENT_ALIAS");
		if(System.getenv("IDAGENT_ALIAS")==null) {
			alias="myserver";
		}
		
		certificate = System.getenv("IDAGENT_CERT");
		if(System.getenv("IDAGENT_CERT")==null) {
			certificate="crypto/ec-cacert.pem";
		}
		
		ipIdAgent=System.getenv("IDAGENT_IP");
		if(System.getenv("IDAGENT_IP")==null) {
			ipIdAgent="127.21.0.4";
		}
		
		portIdAgent=System.getenv("IDAGENT_PORT");
		if(System.getenv("IDAGENT_PORT")==null) {
			portIdAgent="8082";
		}
	}

	/* METHODS */

	
	public void createWallet(String user) {
		System.setProperty("javax.net.ssl.trustStore", keystore);
		System.setProperty("javax.net.ssl.trustStorePassword", keystorepwd);

		// Create the requester's wallet
		CertificateFactory certificateFactory = null;

		Path certificatePath = Paths.get(certificate);
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
			keyStore.load(new FileInputStream(keystore), keystorepwd.toCharArray());
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
			String url = "https://"+ipIdAgent+":"+portIdAgent+"/vcwallet/create-profile";
			String requestBody = "{\r\n" + "    \"userID\":\"" + user + "\",\r\n"
					+ "    \"localKMSPassphrase\":\"pass\"\r\n" + "}";

			HttpClient client = HttpClient.newBuilder().sslContext(sslContext).build();
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
					.header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(requestBody))
					.build();
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			int responseCode = response.statusCode();

			String responseBody = response.body();
			System.out.println("\nCreated wallet profile for the requester " + user + ".");

		} catch (Exception e) {
			System.out.println("Identity Agent component is not avaliable. Please restart it and try again.\n");
		}

		// Unlock that wallet and get a token

		try {
			String url = "https://"+ipIdAgent+":"+portIdAgent+"/vcwallet/open";
			String requestBody = "{\r\n" + "    \"userID\":\"" + user + "\",\r\n"
					+ "    \"localKMSPassphrase\":\"pass\"\r\n" + "}";

			HttpClient client = HttpClient.newBuilder().sslContext(sslContext).build();
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
					.header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(requestBody))
					.build();
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			int responseCode = response.statusCode();

			String responseBody = response.body();

			JsonParser jsonParser = new JsonParser();
			JsonObject jsonObject = jsonParser.parse(responseBody).getAsJsonObject();

			authToken = jsonObject.get("token").getAsString();
			System.out.println("Wallet has been successfully unlocked, getting auth token...");

		} catch (Exception e) {
			System.err.println("Wallet already unlocked. The auth token hasn't expired yet.");
		}

	}

	public boolean verifyPresentation(String VPjson, String user) {

		// Verify the requester's Verifiable Presentation
		System.setProperty("javax.net.ssl.trustStore", keystore);
		System.setProperty("javax.net.ssl.trustStorePassword", keystorepwd);

		CertificateFactory certificateFactory = null;

		Path certificatePath = Paths.get(certificate);
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
			keyStore.load(new FileInputStream(keystore), keystorepwd.toCharArray());
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
			String url = "https://"+ipIdAgent+":"+portIdAgent+"/vcwallet/verify";
			String requestBody = "{\n" + "    \"auth\":\"" + authToken + "\",\n" + "    \"presentation\":" + VPjson
					+ ",\n" + "    \"userid\":\"" + user + "\"\n" + "  }";
			HttpClient client = HttpClient.newBuilder().sslContext(sslContext).build();
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
					.header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(requestBody))
					.build();

				HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			
			
			int responseCode = response.statusCode();

			String responseBody = response.body();

			if (responseCode == 200) {
				if (responseBody.contains("error")) {
					System.out.println("Error: Verifiable Presentation can't be verified.\n");
					return false;
				}

				else if (responseBody.contains("verified")) {
					System.out.println("\nVerifiable Presentation has been successfully verified.\n");
					return true;
				}
			} else {
				System.out.println(responseBody);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Identity Agent component is not avaliable. Please restart it and try again.\n");
		}
		return false;
	}

	public static String getPortIdAgent() {
		return portIdAgent;
	}

	public static void setPortIdAgent(String portIdAgent) {
		IdentityAgent.portIdAgent = portIdAgent;
	}

}
