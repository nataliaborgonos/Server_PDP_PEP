package com.example.demo.PIP;

import java.util.ArrayList;

import com.example.demo.models.Policy;

public interface PIPInterface {
	ArrayList<Policy> getPolicies(String didSP, String recursoSolicitado);
	double getTrustScore(String didSolicitante);
}
