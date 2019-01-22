package ru.sberbank.test.services;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Path("/temperature")
public class CityWeatherService {
	
	private final String WEATHER_URL_TEMPLATE = "https://samples.openweathermap.org/data/2.5/weather?q=%s&appid=%s";
	
	@GET
	@Produces("text/plain")
	public Response defaultCall() throws JSONException { 
		String result = "Correct calling: <host:port>/<context-path>/api/temperature/bycityname?name=<name>";
		return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(result).build();
	}
	
	@Path("/bycity")
	@GET
	@Produces("application/json")
	public Response getTempByCityName(@QueryParam("cityName") String city) throws JSONException { 
		
		if(city==null||city.isEmpty()){
			return Response.status(400).header("Access-Control-Allow-Origin", "*").entity("Empty city name is not allowed").build();
		}
		
		try {
			JSONObject jsonObject = new JSONObject();	
			jsonObject.put("temperature", getTempByCity(city));		
			String result = jsonObject.toString();
			return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(result).build();
		}catch(Exception e) {
			return Response.status(500).header("Access-Control-Allow-Origin", "*").entity(e.getMessage()).build();
		}
	}	
	
	@POST
	@Produces("text/plain")
	public Response doPost() throws JSONException {			
		return Response.status(405).header("Access-Control-Allow-Origin", "*").entity("Not allowed").build();
	}
	
	@PUT
	@Produces("text/plain")
	public Response doPut() throws JSONException {			
		return Response.status(405).header("Access-Control-Allow-Origin", "*").entity("Not allowed").build();
	}
	
	@DELETE
	@Produces("text/plain")
	public Response doDelete() throws JSONException {			
		return Response.status(405).header("Access-Control-Allow-Origin", "*").entity("Not allowed").build();
	}
	
	@HEAD
	@Produces("text/plain")
	public Response doHead() throws JSONException {			
		return Response.status(405).header("Access-Control-Allow-Origin", "*").entity("Not allowed").build();
	}
	
	@OPTIONS
	@Produces("text/plain")
	public Response doOptions() throws JSONException {			
		return Response.status(405).header("Access-Control-Allow-Origin", "*").entity("Not allowed").build();
	}
	
	private String getTempByCity(String city) {
		
		double result = Double.MIN_VALUE;
		
		Client client = Client.create();

		WebResource webResource = client
		   .resource(String.format(WEATHER_URL_TEMPLATE, city,"123"));

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
