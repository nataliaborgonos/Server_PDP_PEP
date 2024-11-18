package com.example.demo.models;

import com.nimbusds.jose.shaded.json.JSONArray;

public class TSMConfigResponse {
    private String entity_did;
    private int config_id;
    private JSONArray characteristics; // Usamos JSONArray para manejar un array de JSONObject

    // Getters y Setters
    public String getEntity_did() {
        return entity_did;
    }

    public void setEntity_did(String entity_did) {
        this.entity_did = entity_did;
    }

    public int getConfig_id() {
        return config_id;
    }

    public void setConfig_id(int config_id) {
        this.config_id = config_id;
    }

    public JSONArray getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(JSONArray characteristics) {
        this.characteristics = characteristics;
    }
}
