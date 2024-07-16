package com.example.demo.PAP;

import java.util.ArrayList;

import com.example.demo.models.Policy;
import com.example.demo.models.Resource;

public class PAPTest implements PAPInterface{

	PolicyStore policyStore;
	
	public PAPTest(PolicyStore policies ) {
		this.policyStore = policies;
	}

	@Override
	public ArrayList<Policy> getPolicies(String didSP, String recursoSolicitado, String action) {
		return policyStore.getPolicy(didSP, recursoSolicitado);
	}
	
	@Override
	public void addPolicy(String didSP, Policy policy, String resource) {
		Resource r=policyStore.createResource(resource);
		policyStore.newPolicy(didSP, policy, r);
	}

}
