package ru.sberbank.example.services;

import org.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class OpenWeatherMapClient {
	
	private String appId;	
	private final String WEATHER_URL_TEMPLATE = "https://samples.openweathermap.org/data/2.5/weather?q=%s&appid=%s";
	
	public OpenWeatherMapClient(String appId) {	
		this.appId = appId;
	}

	public String getTempByCity(String city) {
		
		double result = Double.MIN_VALUE;
		
		Client client = Client.create();

		WebResource webResource = client
		   .resource(String.format(WEATHER_URL_TEMPLATE, city,appId));

		ClientResponse response = webResource.accept("application/json")
                   .get(ClientResponse.class);

		if (response.getStatus() != 200) {
		   throw new RuntimeException("Failed : HTTP error code : "
			+ response.getStatus());
		}

		String output = response.getEntity(String.class);
		JSONObject json_resp = new JSONObject(output);
		JSONObject main = null;
		if(json_resp.has("main")) {
			main = json_resp.getJSONObject("main");
			if(main.has("temp")) {
				result = kelvinToCelsium(main.getDouble("temp"));
			}
		}
		
		if(result==Double.MIN_VALUE) {
			throw new RuntimeException("Could not define temperature because of incorrect server response");
		}
		
		return String.valueOf(result);
	}
	
	private double kelvinToCelsium(double kelvin) {
		double result = kelvin - 273.15d;
		result = result * 100;
		return Math.round(result)/100.0;
	}
	
}
