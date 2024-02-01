package com.example.demo.PIP;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.demo.models.Policy;
import com.example.demo.models.Resource;

public class PIP implements PIPInterface{

	TrustScoreStore trustScoreStore;
	PolicyStore policyStore;

	public PIP(TrustScoreStore trustScores, PolicyStore policies ) {
		this.trustScoreStore=trustScores;
		this.policyStore=policies;
	}
	
	@Override
	public ArrayList<Policy> getPolicies(String didSP, String recursoSolicitado) {
		return policyStore.getPolicy(didSP, recursoSolicitado);
	}


	@Override
	public double getTrustScore(String didSolicitante) {
		return trustScoreStore.getTrustScore(didSolicitante);
	}

}
