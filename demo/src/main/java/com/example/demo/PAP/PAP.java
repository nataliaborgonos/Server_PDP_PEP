package com.example.demo.PAP;

import java.util.ArrayList;

import com.example.demo.PIP.PolicyStore;
import com.example.demo.models.Policy;

public class PAP implements PAPInterface{

	PolicyStore policyStore;
	
	public PAP(PolicyStore policies ) {
		this.policyStore = policies;
	}

	@Override
	public ArrayList<Policy> getPolicies(String didSP, String recursoSolicitado) {
		return policyStore.getPolicy(didSP, recursoSolicitado);
	}

}
