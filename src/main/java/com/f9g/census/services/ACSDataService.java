package com.f9g.census.services;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.web.client.RestTemplate;

import com.f9g.census.beans.CensusResponse;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ACSDataService {

	String baseUri = "https://api.census.gov/data/";
	int defaultYear = 2019;
	String defaultVariables = "NAME";
	
	public CensusResponse[] retrieveData(int year) throws JsonParseException, JsonMappingException, IOException {
		
		
		return retrieveData(year, "cprofile", defaultVariables, "*", "*", "55");
	}
	
	public CensusResponse[] retrieveData(int year, HashMap<String, String> locations) throws JsonParseException, JsonMappingException, IOException {
		return retrieveData(year, locations, defaultVariables);
	}
	
	public CensusResponse[] retrieveData(int year, HashMap<String, String> locations, String variables) throws JsonParseException, JsonMappingException, IOException {
		return retrieveData(year, "", locations, variables);
	}
	
	public CensusResponse[] retrieveData(int year, String profile, HashMap<String, String> locations, String variables) throws JsonParseException, JsonMappingException, IOException {
		AtomicReference<String> countySubdivision = new AtomicReference<>("");
		AtomicReference<String> county = new AtomicReference<>("");
		AtomicReference<String> state = new AtomicReference<>("");
		locations.forEach((k,v) -> {
			switch (k.toLowerCase()) {
			case "countysubdivision":
				countySubdivision.set(v);
				System.out.println("in");
				break;
			case "county":
				county.set(v);
				break;
			case "state":
				state.set(v);
				break;
			}
		});
		
		return retrieveData(year, profile, variables, countySubdivision.get(), county.get(), state.get());
	}
	
	public CensusResponse[] retrieveData(int year, String profileSetting, String variables, String countySubdivision, String county, String state) throws JsonParseException, JsonMappingException, IOException {
		
		String uri = buildUri(year, profileSetting, variables, countySubdivision, county, state);
		
		RestTemplate restTemplate = new RestTemplate();
		String httpRetVal = restTemplate.getForObject(uri, String.class);
		
		Map<String, Map<String, String>> variablesInfo = new ACSVariablesService().retrieveVariableInfo(variables, year, profileSetting);
		
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			Object[][] arrayMapped = mapper.readValue(httpRetVal, Object[][].class);
			String[] arrayKeys = new String[arrayMapped[0].length];
			Object[][] arrayVals = new String[arrayMapped.length-1][arrayMapped[0].length];
			CensusResponse[] cr = new CensusResponse[arrayMapped.length-1];
			
			for(int i = 0; i < arrayMapped.length; i++) {
				if(i != 0) {
					cr[i-1] = new CensusResponse(new HashMap<String, Object>());
				}
				for(int j = 0; j < arrayMapped[i].length; j++) {
					if(i == 0) {
						arrayKeys[j] = arrayMapped[i][j].toString();
						if(variablesInfo.containsKey(arrayKeys[j])) {
							Map<String, String> var = variablesInfo.get(arrayKeys[j]);
							if(var.containsKey("label")) {
								System.out.println("Found variable: " + var.get("label") + " for code: " + var.get("name"));
								arrayKeys[j] = var.get("label");
							}
						}
					} else {
						arrayVals[i-1][j] = arrayMapped[i][j];
						cr[i-1].setData(arrayKeys[j], arrayMapped[i][j]);
					}
				}
			}
			return cr;
		} catch (JsonParseException e) {
			e.printStackTrace();
			return null;
		} catch (JsonMappingException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private String buildUri(int year, String profileSetting, String variables, String countySubdivision, String county, String state) {
		String uri = baseUri
				+ year
				+ "/acs/acs1/"
				+ profileSetting
				+ "?get="
				+ variables
				+ "&for=";
		
		if(countySubdivision.length()>0) {
			uri += "county subdivision:" + countySubdivision;
			System.out.println("in2");
			if(county.length()>1) {
				uri += "&in=county:" + county;
			}
			if(state.length()>0) {
				uri += "&in=state:" + state;
			}
		} else if (county.length() > 0) {
			uri += "county:" + county;
			if(state.length()>0) {
				uri += "&in=state:" + state;
			} else {
				uri += "&in=state:*";
			}
		} else if (state.length()>0) {
			uri += "state:" + state;
		} else {
			uri += "state:*";
		}
		System.out.println("Query uri: " + uri);
		return uri;
	}
}
