package com.f9g.census.beans;

import java.util.Map;

import com.fasterxml.jackson.annotation.*;

public class CensusResponse {
	private Map<String, Object> data;
	private Map<String, Object> location;
	
	public CensusResponse() {};
	
	public CensusResponse(Map<String, Object> data, Map<String, Object> location) {
		super();
		this.data = data;
		this.location = location;
	}

	
	public Map<String, Object> getData() {
		return this.data;
	}
	
	public Map<String, Object> getLocation() {
		return this.location;
	}
	
	
	public void setData(String key, Object value) {
		data.put(key, value);
	}
	
	public void setLocation(String key, Object value) {
		this.location.put(key, value);
	}
	
	@Override
	public String toString() {
		return location.toString() + data.toString();
	}
}
