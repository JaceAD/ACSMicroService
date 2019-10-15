package com.f9g.census.controllers;

import java.io.IOException;
import java.util.HashMap;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;

import com.f9g.census.services.ACSDataService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * @author F9G_DEV_3
 *
 */
/**
 * @author F9G_DEV_3
 *
 */
@RestController
public class BasicController {

	ACSDataService dataService = new ACSDataService();
	ObjectMapper mapper = new ObjectMapper().configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
	
	@CrossOrigin
	@RequestMapping(value = "year/{year}")
	public ResponseEntity<String> yearOnlyMapping(@PathVariable int year) throws IOException {
		HttpHeaders head = new HttpHeaders();
	
		String responseString = mapper.writeValueAsString(dataService.retrieveData(year));
		return new ResponseEntity<String>(responseString, head, HttpStatus.OK);	
	}
	
	//locations specifiers separate value by : and entries by ,
	@CrossOrigin
	@RequestMapping(value = {"year/{year}/location/{locationSpecifiers}", "location/{locationSpecifiers}/year/{year}"},
					method = RequestMethod.GET)
	public ResponseEntity<String> anyOrderYearAndGeoMapping(@PathVariable int year, @PathVariable String locationSpecifiers) throws JsonParseException, JsonMappingException, JsonProcessingException, IOException {
		HttpHeaders head = new HttpHeaders();
		System.out.println("Location specifers: " + locationSpecifiers);
		String[] locationsArr = locationSpecifiers.split(",");
		HashMap<String, String> locationMap = new HashMap<>();
		for(int i = 0; i < locationsArr.length; i++) {
			locationMap.put(locationsArr[i].split(":")[0], locationsArr[i].split(":")[1]);
		}
		String responseString = mapper.writeValueAsString(dataService.retrieveData(year, locationMap));
		return new ResponseEntity<String>(responseString, head, HttpStatus.OK);	
	}
	
	//locations specifiers separate value by : and entries by ,
	@CrossOrigin	
	@RequestMapping(value = {
									"year/{year}/location/{locationSpecifiers}/variables/{variables}",
									"year/{year}/variables/{variables}/location/{locationSpecifiers}",
									"location/{locationSpecifiers}/year/{year}/variables/{variables}",
									"location/{locationSpecifiers}/variables/{variables}/year/{year}",
									"variables/{variables}/location/{locationSpecifiers}/year/{year}",
									"variables/{variables}/year/{year}/location/{locationSpecifiers}",
								},
						method = RequestMethod.GET)
		public ResponseEntity<String> anyOrderYearLocationAndVariables(
																@PathVariable int year,
																@PathVariable String locationSpecifiers, 
																@PathVariable String variables
																) throws JsonParseException, JsonMappingException, JsonProcessingException, IOException {
			
			HttpHeaders head = new HttpHeaders();
			HashMap<String, String> locationMap = resolveLocations(locationSpecifiers);
			String responseString = mapper.writeValueAsString(dataService.retrieveData(year, locationMap, variables));
			return new ResponseEntity<String>(responseString, head, HttpStatus.OK);	
		}
	
	//locations specifiers separate value by : and entries by ,
	@CrossOrigin
	@RequestMapping(value = {
								"profile/{profile}/year/{year}/location/{locationSpecifiers}/variables/{variables}",
								"profile/{profile}/year/{year}/variables/{variables}/location/{locationSpecifiers}",
								"profile/{profile}/location/{locationSpecifiers}/year/{year}/variables/{variables}",
								"profile/{profile}/location/{locationSpecifiers}/variables/{variables}/year/{year}",
								"profile/{profile}/variables/{variables}/location/{locationSpecifiers}/year/{year}",
								"profile/{profile}/variables/{variables}/year/{year}/location/{locationSpecifiers}",
							},
					method = RequestMethod.GET)
	public ResponseEntity<String> profileAndAnyOrderYearLocationAndVariables(
															@PathVariable String profile,
															@PathVariable int year,
															@PathVariable String locationSpecifiers, 
															@PathVariable String variables
															) throws JsonParseException, JsonMappingException, JsonProcessingException, IOException {
		
		HttpHeaders head = new HttpHeaders();
		String profileSetting = resolveProfile(profile);
		HashMap<String, String> locationMap = resolveLocations(locationSpecifiers);
		String responseString = mapper.writeValueAsString(dataService.retrieveData(year, profileSetting, locationMap, variables));
		return new ResponseEntity<String>(responseString, head, HttpStatus.OK);	
	}
	
	
	
	private String resolveProfile(String profile) {
		String profileSetting = "";
		if(profile.equalsIgnoreCase("c") || profile.equalsIgnoreCase("cprofile")) {
			profileSetting = "cprofile/";
		} else if(profile.equalsIgnoreCase("p") || profile.equalsIgnoreCase("profile")) {
			profileSetting = "profile/";
		}
		return profileSetting;
	}
	
	private HashMap<String, String> resolveLocations(String locations) {
		String[] locationsArr = locations.split(",");
		HashMap<String, String> locationMap = new HashMap<>();
		for(int i = 0; i < locationsArr.length; i++) {
			locationMap.put(locationsArr[i].split(":")[0], locationsArr[i].split(":")[1]);
		}
		return locationMap;
	}
	
	/*
	@RequestMapping("year/{year}/{geoName}/{geoVal}")
	public ResponseEntity<String> yearAndGeoMapping1(@PathVariable int year, @PathVariable String geoName, @PathVariable String geoVal) throws IOException {
		HttpHeaders head = new HttpHeaders();
		
		HashMap<String, String> locationSpecifiers = new HashMap<>();
		locationSpecifiers.put(geoName, geoVal);
		
		String responseString = mapper.writeValueAsString(dataService.retrieveData(year, locationSpecifiers));
		return new ResponseEntity<String>(responseString, head, HttpStatus.OK);
	}
	
	@RequestMapping("year/{year}/{geoName}/{geoVal}/{geoName2}/{geoVal2}")
	public ResponseEntity<String> yearAndGeoMapping2(@PathVariable int year, @PathVariable String geoName, @PathVariable String geoVal, @PathVariable String geoName2, @PathVariable String geoVal2) throws IOException {
		HttpHeaders head = new HttpHeaders();
		
		HashMap<String, String> locationSpecifiers = new HashMap<>();
		locationSpecifiers.put(geoName, geoVal);locationSpecifiers.put(geoName2, geoVal2);
		
		String responseString = mapper.writeValueAsString(dataService.retrieveData(year, locationSpecifiers));
		return new ResponseEntity<String>(responseString, head, HttpStatus.OK);
	}
	
	@RequestMapping("year/{year}/{geoName}/{geoVal}/{geoName2}/{geoVal2}/{geoName3}/{geoVal3}")
	public ResponseEntity<String> yearAndGeoMapping3(@PathVariable int year, @PathVariable String geoName, @PathVariable String geoVal, @PathVariable String geoName2, @PathVariable String geoVal2, @PathVariable String geoName3, @PathVariable String geoVal3) throws IOException {
		HttpHeaders head = new HttpHeaders();
		
		HashMap<String, String> locationSpecifiers = new HashMap<>();
		locationSpecifiers.put(geoName, geoVal);locationSpecifiers.put(geoName2, geoVal2);locationSpecifiers.put(geoName3, geoVal3);
		
		String responseString = mapper.writeValueAsString(dataService.retrieveData(year, locationSpecifiers));
		return new ResponseEntity<String>(responseString, head, HttpStatus.OK);
	}
	*/
}
