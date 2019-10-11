package com.f9g.census.services;

import java.util.Map;
import java.io.IOException;
import java.util.HashMap;

import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ACSVariablesService {

	private String baseUri = "https://api.census.gov/data/";
	private String uriVariables = "variables/";
	
	
	private TypeReference<Map<String,String>> strMapTypRef = new TypeReference<Map<String,String>>(){};
	
	public Map<String, Map<String,String>> retrieveVariableInfo(String variableCodeCSV, int year) throws JsonParseException, JsonMappingException, IOException {
		return retrieveVariableInfo(variableCodeCSV, year, "");
	}
	
	public Map<String, Map<String,String>> retrieveVariableInfo(String variableCodeCSV, int year, String profile) throws JsonParseException, JsonMappingException, IOException {
		
		String variableLibUri = baseUri
				+ year
				+ "/acs/acs1/"
				+ profile
				+ uriVariables;
		
		String[] variablesArr = variableCodeCSV.split(",");

		RestTemplate restTemplate = new RestTemplate();
		
		Map<String, Map<String, String>> variablesInfo = new HashMap<String, Map<String, String>>();
		
		for(String variableString: variablesArr) {
			String targetVariableUri = variableLibUri + variableString;
			System.out.println("VARIABLE URI: " +  targetVariableUri);
			String httpRetVal = restTemplate.getForObject(targetVariableUri, String.class);
			
			ObjectMapper mapper = new ObjectMapper();
			
			Map<String, String> variableMap = mapper.readValue(httpRetVal, strMapTypRef);
			variablesInfo.put(variableMap.get("name"), variableMap);
		}
		
		return variablesInfo;
	}
	
}
