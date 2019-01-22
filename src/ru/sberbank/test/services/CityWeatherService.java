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

@Path("/temperature")
public class CityWeatherService {
	
	@GET
	@Produces("text/plain")
	public Response convertFtoC() throws JSONException { 
		String result = "Correct calling: <host:port>/<context-path>/api/temperature/bycityname?name=<name>";
		return Response.status(200).entity(result).build();
	}
	
	@Path("/bycity")
	@GET
	@Produces("application/json")
	public Response getTempByCityName(@QueryParam("cityName") String city) throws JSONException { 
		
		if(city==null||city.isEmpty()){
			return Response.status(400).entity("Empty city name is not allowed").build();
		}
		
		JSONObject jsonObject = new JSONObject();	
		jsonObject.put("temperature", 666);		
		String result = jsonObject.toString();
		return Response.status(200).entity(result).build();
	}	
	
	@POST
	@Produces("text/plain")
	public Response doPost() throws JSONException {			
		return Response.status(405).entity("Not allowed").build();
	}
	
	@PUT
	@Produces("text/plain")
	public Response doPut() throws JSONException {			
		return Response.status(405).entity("Not allowed").build();
	}
	
	@DELETE
	@Produces("text/plain")
	public Response doDelete() throws JSONException {			
		return Response.status(405).entity("Not allowed").build();
	}
	
	@HEAD
	@Produces("text/plain")
	public Response doHead() throws JSONException {			
		return Response.status(405).entity("Not allowed").build();
	}
	
	@OPTIONS
	@Produces("text/plain")
	public Response doOptions() throws JSONException {			
		return Response.status(405).entity("Not allowed").build();
	}
	
}
