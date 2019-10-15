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
	HashMap<String, String> defaultLocations = new HashMap<String, String>();
	
	public CensusResponse[] retrieveData(int year) throws JsonParseException, JsonMappingException, IOException {
		defaultLocations.put("us", "1");
		return retrieveData(year, defaultLocations);
	}
	
	public CensusResponse[] retrieveData(int year, HashMap<String, String> locations) throws JsonParseException, JsonMappingException, IOException {
		return retrieveData(year, locations, defaultVariables);
	}
	
	public CensusResponse[] retrieveData(int year, HashMap<String, String> locations, String variables) throws JsonParseException, JsonMappingException, IOException {
		return retrieveData(year, "", locations, variables);
	}
	
	public CensusResponse[] retrieveData(int year, String profile, HashMap<String, String> locations, String variables) throws JsonParseException, JsonMappingException, IOException {
		
		AtomicReference<String> schoolDistrictUnified = new AtomicReference<>("");
		AtomicReference<String> schoolDistrictSecondary = new AtomicReference<>("");
		AtomicReference<String> schoolDistrictElementary = new AtomicReference<>("");
		AtomicReference<String> publicUseMicrodataArea = new AtomicReference<>("");
		AtomicReference<String> congressionalDistrict = new AtomicReference<>("");
		AtomicReference<String> urbanArea = new AtomicReference<>("");
		AtomicReference<String> nectaDivision = new AtomicReference<>("");
		AtomicReference<String> necta = new AtomicReference<>("");
		AtomicReference<String> combinedNECTA = new AtomicReference<>("");
		AtomicReference<String> combinedStatisticalArea = new AtomicReference<>("");
		AtomicReference<String> metropolitanDivision = new AtomicReference<>("");
		AtomicReference<String> principalCityOrPart = new AtomicReference<>("");
		AtomicReference<String> metropolitanStatisticalAreaMicropolitanStatisticalArea = new AtomicReference<>("");
		AtomicReference<String> americanIndianAreaAlaskaNativeAreaHawaiianHomeLand = new AtomicReference<>("");
		AtomicReference<String> alaskaNativeRegionalCorporation = new AtomicReference<>("");
		AtomicReference<String> place = new AtomicReference<>("");
		AtomicReference<String> countySubdivision = new AtomicReference<>("");
		AtomicReference<String> county = new AtomicReference<>("");
		AtomicReference<String> state = new AtomicReference<>("");
		AtomicReference<String> division = new AtomicReference<>("");
		AtomicReference<String> region = new AtomicReference<>("");
		AtomicReference<String> us = new AtomicReference<>("");
		
		
		locations.forEach((k,v) -> {
			switch (k.toLowerCase()) {
			case "schooldistrictunified":
				schoolDistrictUnified.set(v);
				break;
			case "schooldistrictsecondary":
				schoolDistrictSecondary.set(v);
				break;
			case "schooldistrictelementary":
				schoolDistrictElementary.set(v);
				break;
			case "publicusemicrodataarea":
				publicUseMicrodataArea.set(v);
				break;
			case "congressionaldistrict":
				congressionalDistrict.set(v);
				break;
			case "urbanarea":
				urbanArea.set(v);
				break;
			case "nectadivision":
				nectaDivision.set(v);
				break;
			case "newenglandcityandtownarea":
				necta.set(v);
				break;
			case "combinednewenglandcityandtownarea":
				combinedNECTA.set(v);
				break;
			case "combinedstatisticalarea":
				combinedStatisticalArea.set(v);
				break;
			case "metropolitandivision":
				metropolitanDivision.set(v);
				break;
			case "principalcity":
				principalCityOrPart.set(v);
				break;
			case "metropolitanstatisticalareamicropolitanstatisticalarea":
				metropolitanStatisticalAreaMicropolitanStatisticalArea.set(v);
				break;
			case "americanindianareaalaskanativeareahawaiianhomeland":
				americanIndianAreaAlaskaNativeAreaHawaiianHomeLand.set(v);
				break;
			case "alaskanativeregionalcorporation":
				alaskaNativeRegionalCorporation.set(v);
				break;
			case "place":
				place.set(v);
				break;
			case "countysubdivision":
				countySubdivision.set(v);
				break;
			case "county":
				county.set(v);
				break;
			case "state":
				state.set(v);
				break;
			case "division":
				division.set(v);
				break;
			case "region":
				region.set(v);
				break;
			case "us":
				us.set(v);
				break;
			
			}
		});
		
		return retrieveData(year, profile, variables, schoolDistrictUnified.get(), schoolDistrictSecondary.get(), schoolDistrictElementary.get(), publicUseMicrodataArea.get(), congressionalDistrict.get(), urbanArea.get(), nectaDivision.get(), necta.get(), combinedNECTA.get(), combinedStatisticalArea.get(), metropolitanDivision.get(), principalCityOrPart.get(), metropolitanStatisticalAreaMicropolitanStatisticalArea.get(), americanIndianAreaAlaskaNativeAreaHawaiianHomeLand.get(), alaskaNativeRegionalCorporation.get(), place.get(), countySubdivision.get(), county.get(), state.get(), division.get(), region.get(), us.get());
	}
	
	public CensusResponse[] retrieveData(int year, String profileSetting, String variables, String schoolDistrictUnified, String schoolDistrictSecondary, String schoolDistrictElementary, String publicUseMicrodataArea, String congressionalDistrict, String urbanArea, String nectaDivision, String necta, String combinedNECTA, String combinedStatisticalArea, String metropolitanDivision, String principalCityOrPart, String metropolitanStatisticalAreaMicropolitanStatisticalArea, String americanIndianAreaAlaskaNativeAreaHawaiianHomeLand, String alaskaNativeRegionalCorporation, String place, String countySubdivision, String county, String state, String division, String region, String us) throws JsonParseException, JsonMappingException, IOException {
		
		String uri = buildUri(year, profileSetting, variables, schoolDistrictUnified, schoolDistrictSecondary, schoolDistrictElementary, publicUseMicrodataArea, congressionalDistrict, urbanArea, nectaDivision, necta, combinedNECTA, combinedStatisticalArea, metropolitanDivision, principalCityOrPart, metropolitanStatisticalAreaMicropolitanStatisticalArea, americanIndianAreaAlaskaNativeAreaHawaiianHomeLand, alaskaNativeRegionalCorporation, place, countySubdivision, county, state, division, region, us);
		
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
	
	private String buildUri(int year, String profileSetting, String variables, String schoolDistrictUnified, String schoolDistrictSecondary, String schoolDistrictElementary, String publicUseMicrodataArea, String congressionalDistrict, String urbanArea, String nectaDivision, String necta, String combinedNECTA, String combinedStatisticalArea, String metropolitanDivision, String principalCityOrPart, String metropolitanStatisticalAreaMicropolitanStatisticalArea, String americanIndianAreaAlaskaNativeAreaHawaiianHomeLand, String alaskaNativeRegionalCorporation, String place, String countySubdivision, String county, String state, String division, String region, String us) {
		String uri = baseUri
				+ year
				+ "/acs/acs1/"
				+ profileSetting
				+ "?get="
				+ variables
				+ "&for=";
		if(schoolDistrictUnified.length()>0) {
			uri += "school district (unified):" + schoolDistrictUnified;
			if(state.length() > 0) {
				uri += "&in=state:" + state;
			}
		} else if(schoolDistrictSecondary.length() > 0) {
			uri += "school district (secondary):" + schoolDistrictSecondary;
			if(state.length() > 0) {
				uri += "&in=state:" + state;
			}
		} else if(schoolDistrictElementary.length()>0) {
			uri += "school district (elementary):" + schoolDistrictElementary;
			if(state.length() > 0) {
				uri += "&in=state:" + state;
			}
		} else if(congressionalDistrict.length() > 0) {
			uri += "congressional district:" + congressionalDistrict;
			if(state.length() > 0) {
				uri += "&in=state:" + state;
			}
		} else if(urbanArea.length() > 0) {
			uri += "urban area:" + urbanArea;
		} else if(nectaDivision.length() > 0) {
			uri += "necta division:" + nectaDivision;
			if(necta.length() > 0) {
				uri += "&in=new england city and town area:" + necta;
			}
		} else if (principalCityOrPart.length() > 0) {
			uri += "principal city (or part):" + principalCityOrPart;
			if(state.length() > 0) {
				uri += "&in=state:" + state;
			} if(necta.length() > 0) {
				uri += "&in=new england city and town area:" + necta;
			} if(metropolitanStatisticalAreaMicropolitanStatisticalArea.length() > 0) {
				uri += "&in=metropolitan statistical area/micropolitan statistical area: " + metropolitanStatisticalAreaMicropolitanStatisticalArea;
			}
		} else if(necta.length() > 0) {
			uri += "new england city and town area:" + necta;
		} else if(combinedNECTA.length() > 0) {
			uri += "combined new england city and town area:" + combinedNECTA;
		} else if(combinedStatisticalArea.length() > 0) {
			uri += "combined statistical area:" + combinedStatisticalArea;
		} else if(metropolitanDivision.length() > 0) {
			uri += "metropolitan division:" + metropolitanDivision;
			if(metropolitanStatisticalAreaMicropolitanStatisticalArea.length() > 0) {
				uri += "&in=metropolitan statistical area/micropolitan statistical area: " + metropolitanStatisticalAreaMicropolitanStatisticalArea;
			}
		}	else if(metropolitanStatisticalAreaMicropolitanStatisticalArea.length() > 0) {
			uri += "metropolitan statistical area/micropolitan statistical area: " + metropolitanStatisticalAreaMicropolitanStatisticalArea;
		}	else if(americanIndianAreaAlaskaNativeAreaHawaiianHomeLand.length() > 0) {
			uri += "american indian area/alaska native area/hawaiian home land" + americanIndianAreaAlaskaNativeAreaHawaiianHomeLand;
		}	else if(alaskaNativeRegionalCorporation.length() > 0) {
			uri += "alaska native regional corporation:" + alaskaNativeRegionalCorporation;
			if(state.length() > 0) {
				uri += "&in=state:" + state;
			}
		}	else if(place.length() > 0) {
			uri += "place:" + place;
			if(state.length() > 0) {
				uri += "&in=state:" + state;
			}
		}	else if(countySubdivision.length()>0) {
			uri += "county subdivision:" + countySubdivision;
			if(county.length()>1) {
				uri += "&in=county:" + county;
			}
			if(state.length()>0) {
				uri += "&in=state:" + state;
			}
		}	else if (county.length() > 0) {
			uri += "county:" + county;
			if(state.length()>0) {
				uri += "&in=state:" + state;
			} else {
				uri += "&in=state:*";
			}
		} else if(state.length()>0) {
			uri += "state:" + state;
		} else if(division.length() > 0) {
			uri += "division:" + division;
		} else if(region.length() > 0) {
			uri += "region:" + region;
		} else if(us.length() > 0) {
			uri += "us:" + us;
		} else {
			uri += "state:*";
		}
		System.out.println("Query uri: " + uri);
		return uri;
	}
}
