package com.example.demo.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nimbusds.jose.shaded.json.JSONArray;

public class TSMGetResponse {
	 public String entity_did;
	    public int id;
	    public String name;
	    public double minimum_value;
	    public String description;
	    public int buffer_size;
	    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
	    public Date created;
	    public JSONArray characteristics;
		public String getEntity_did() {
			return entity_did;
		}
		public void setEntity_did(String entity_did) {
			this.entity_did = entity_did;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public double getMinimum_value() {
			return minimum_value;
		}
		public void setMinimum_value(double minimum_value) {
			this.minimum_value = minimum_value;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public int getBuffer_size() {
			return buffer_size;
		}
		public void setBuffer_size(int buffer_size) {
			this.buffer_size = buffer_size;
		}
		public Date getCreated() {
			return created;
		}
		public void setCreated(Date created) {
			this.created = created;
		}
		public JSONArray getCharacteristics() {
			return characteristics;
		}
		public void setCharacteristics(JSONArray characteristics) {
			this.characteristics = characteristics;
		}
	    
	    
}
