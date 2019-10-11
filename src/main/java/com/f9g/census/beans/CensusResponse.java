package com.f9g.census.beans;

import java.util.Map;

import com.fasterxml.jackson.annotation.*;

public class CensusResponse {
	private Map<String, Object> data;
	
	public CensusResponse() {};
	
	public CensusResponse(Map<String, Object> data) {
		super();
		this.data = data;
	}



	@JsonAnyGetter
	public Map<String, Object> getData() {
		return this.data;
	}
	
	@JsonAnySetter
	public void setData(String key, Object value) {
		data.put(key, value);
	}
	
	@Override
	public String toString() {
		return data.toString();
	}
}
