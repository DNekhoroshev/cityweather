package ru.sberbank.example.services;

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

@Path("/temperature")
public class CityWeatherService {	
	
	private final String APP_ID = "8f56ccda96cd0d0eebfc792dbf952290"; 
			
	private OpenWeatherMapClient client;

	public CityWeatherService() {
		client = new OpenWeatherMapClient(APP_ID);
	}

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
			jsonObject.put("temperature", client.getTempByCity(city));		
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
	
}
