package com.example.demo.models;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CapabilityToken {

	/* ATTRIBUTES */

	/*
	 * id ID
	 * ii Issuing timestamp 
	 * is Issuer 
	 * su Subject 
	 * de Device 
	 * si Signature 
	 * ar Access resources 
	 * nb Not before 
	 * na Not after
	 */
	String id;
	String ii;
	String is;
	String su;
	String de;
	String si;
	ArrayList<SimpleAccessRight> ar;
	long nb;
	long na;

	static PublicKey pbk;

	/* CONSTRUCTORS */
	public CapabilityToken(String keyStore, char[] keyStorePWD, String alias, String didRequester, String didSP,
			SimpleAccessRight sar) {
		this.id = UUID.randomUUID().toString().substring(0, 8);
		long time = System.currentTimeMillis();
		this.ii = String.valueOf(time);
		this.is = "erat";
		this.su = didRequester;
		this.de = didSP;
		ArrayList<SimpleAccessRight> ar = new ArrayList<>();
		SimpleAccessRight sarAdd = new SimpleAccessRight(sar.getAction(), sar.getResource());
		ar.add(sarAdd);
		this.ar = ar;
		this.nb = time;
		// 60 min TTL
		this.na = time + 60 * 60 * 1000;
		this.si = getPrivKey(keyStore, keyStorePWD, alias);
	}

	public CapabilityToken() {
	}

	/* METHODS */

	// Method for generating private key
	public String getPrivKey(String keyStore, char[] keyStorePWD, String alias) {
		KeyStore ks = null;
		String signature = null;
		try {
			ks = KeyStore.getInstance("JKS");
		} catch (KeyStoreException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Key key = null;
		try {
			ks.load(new FileInputStream(keyStore), keyStorePWD);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			key = ks.getKey(alias, keyStorePWD);
		} catch (UnrecoverableKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (key instanceof PrivateKey) {

			java.security.cert.Certificate cert = null;
			try {
				cert = ks.getCertificate(alias);
			} catch (KeyStoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pbk = cert.getPublicKey();
			PrivateKey privateKey = (PrivateKey) key;

			signature = generateSignature(this, privateKey);

			return signature;
			// Aqu� puedes utilizar la clave privada para firmar tus archivos
			// ...
		} else {
			System.out.println("That key is not private.");
		}
		return null;
	}

	// Concatenate every token's field (except signature)
	public String getSignatureString(CapabilityToken token) {
		String signature = null;
		signature = token.getId() + token.getIi() + token.getIs() + token.getSu() + token.getDe()
				+ token.getAr().toString() + token.getNb() + token.getNa();
		return signature;
	}

	// Generate the signature for the CapabilityToken with the private key
	private String generateSignature(CapabilityToken token, PrivateKey serverPrivateKey) {
		String dataToCheck = this.getSignatureString(token);

		try {
			Signature signature = Signature.getInstance("SHA1withRSA");
			signature.initSign(serverPrivateKey);
			signature.update(dataToCheck.getBytes());
			byte[] sigBytes;
			sigBytes = signature.sign();

			// Encode the signature using Base64
			String signature_bytes = Base64.getEncoder().encodeToString(sigBytes);
			return signature_bytes;
		} catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e1) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e1);
			return null;
		}
	}

	/* GETTER AND SETTER METHODS */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIi() {
		return ii;
	}

	public void setIi(String ii) {
		this.ii = ii;
	}

	public String getIs() {
		return is;
	}

	public void setIs(String is) {
		this.is = is;
	}

	public String getSu() {
		return su;
	}

	public void setSu(String su) {
		this.su = su;
	}

	public String getDe() {
		return de;
	}

	public void setDe(String de) {
		this.de = de;
	}

	public String getSi() {
		return si;
	}

	public void setSi(String si) {
		this.si = si;
	}

	public ArrayList<SimpleAccessRight> getAr() {
		return ar;
	}

	public void setAr(ArrayList<SimpleAccessRight> ar) {
		this.ar = ar;
	}

	public long getNb() {
		return nb;
	}

	public void setNb(long nb) {
		this.nb = nb;
	}

	public long getNa() {
		return na;
	}

	public void setNa(long na) {
		this.na = na;
	}

	public PublicKey getPublicKey() {
		return pbk;
	}
}
