package com.example.demo.PAP;

import java.util.ArrayList;

import com.example.demo.models.Policy;

public class PAPTest implements PAPInterface{

	PolicyStore policyStore;
	
	public PAPTest(PolicyStore policies ) {
		this.policyStore = policies;
	}

	@Override
	public ArrayList<Policy> getPolicies(String didSP, String recursoSolicitado) {
		return policyStore.getPolicy(didSP, recursoSolicitado);
	}

}
