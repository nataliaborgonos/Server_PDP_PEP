package com.example.demo.PIP;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.demo.models.Policy;
import com.example.demo.models.Resource;

public class PIPTest implements PIPInterface{

	TrustScoreStore trustScoreStore;

	public PIPTest(TrustScoreStore trustScores) {
		this.trustScoreStore=trustScores;
	}
	
	@Override
	public double getTrustScore(String didSolicitante) {
		return trustScoreStore.getTrustScore(didSolicitante);
	}

}
