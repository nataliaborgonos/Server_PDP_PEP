package com.example.demo.PIP;

import java.util.ArrayList;
import java.util.HashMap;

public class TrustScoreStore {

	/* ASOCIA didSolicitante CON trustScore */
	HashMap<String,Double> trustScores;
	
	public TrustScoreStore() {
		this.trustScores=new HashMap<>();
		trustScores.put("did", 0.7);
	}
	
	public double getTrustScore(String didRequester) {
		return trustScores.get(didRequester);
	}
	
	
}
