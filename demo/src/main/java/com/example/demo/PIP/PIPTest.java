package com.example.demo.PIP;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.demo.models.Policy;
import com.example.demo.models.Resource;
import com.example.demo.models.TSMConfigRequest;
import com.example.demo.models.TSMPOSRequest;
import com.example.demo.models.TSMScoreRequest;
import com.example.demo.models.UCBARequest;
import com.nimbusds.jose.shaded.json.JSONObject;

public class PIPTest implements PIPInterface{

	TrustScoreStore trustScoreStore;
	HashMap<String, Integer> trustScores;
	TrustScoreManager tsm;
	UCBA ucba;

	public PIPTest(TrustScoreStore trustScores) {
		this.trustScoreStore=trustScores;
	}
	public PIPTest(TrustScoreManager tsm) {
		this.tsm=tsm;
		trustScores=new HashMap<>();
	}
	
	public String createConfig(TSMConfigRequest tsmreq) {
		return tsm.createConfig(tsmreq);
	}
	 
  	public String getConfig(TSMScoreRequest tsmreq) {
		return tsm.getConfig(tsmreq);
	}
	
	public void addConfig(String did,int config_id) {
		trustScores.put(did, config_id);
	}
	
	public String calculateTrustScore(String did){
		int config_id=trustScores.get(did);
		TSMScoreRequest tsmScoreReq= new TSMScoreRequest(did, config_id);
		return tsm.calculateTrustScore(tsmScoreReq);
	}
	

	public String addProtectiveObjectives(TSMPOSRequest req){
		return tsm.addProtectiveObjectives(req);
	}
	
	public String getBehaviouralScore(UCBARequest ucbaReq){
		return ucba.auth(ucbaReq);
	}	
	@Override
	public double getTrustScore(String didSolicitante) {
		return trustScoreStore.getTrustScore(didSolicitante);
	}

}
