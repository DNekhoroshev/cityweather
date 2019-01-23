package ru.sberbank.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

import java.lang.reflect.Field;

import javax.ws.rs.core.Response;

import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import ru.sberbank.example.services.CityWeatherService;
import ru.sberbank.example.services.OpenWeatherMapClient;

public class CityWeatherServiceTest {
	
	@Test
	public void testDefaultCall(){		
		String expectedResult = "Correct calling: <host:port>/<context-path>/api/temperature/bycityname?name=<name>";
		
		CityWeatherService service = new CityWeatherService();
		Response resp = service.defaultCall();		
		assertEquals(200,resp.getStatus());
		assertEquals(expectedResult,resp.getEntity());
	}
	
	@Test
	public void testGetTempByCityName() throws Exception{		
				
		String expectedResult = "{\"temperature\":\"22.75\"}";
		
		OpenWeatherMapClient owmcMock = mock(OpenWeatherMapClient.class);
		
		doAnswer(new Answer<String>() {
			@Override   
			public String answer(InvocationOnMock invocation) throws Exception{			       		       
			       return "22.75";
			   }})
		 .when(owmcMock).getTempByCity(anyString());
		
		CityWeatherService service = new CityWeatherService();
		injectField(service, "client", owmcMock);
				
		Response resp = service.getTempByCityName("Moscow");		
		assertEquals(200,resp.getStatus());		
		assertEquals(expectedResult,resp.getEntity());
	}
	
	@Test
	public void testEmptyCityName() throws Exception{		
				
		String expectedResult = "Empty city name is not allowed";
		
		OpenWeatherMapClient owmcMock = mock(OpenWeatherMapClient.class);
		
		doAnswer(new Answer<String>() {
			@Override   
			public String answer(InvocationOnMock invocation) throws Exception{			       		       
			       return "22.75";
			   }})
		 .when(owmcMock).getTempByCity(anyString());
		
		CityWeatherService service = new CityWeatherService();
		injectField(service, "client", owmcMock);
				
		Response resp = service.getTempByCityName("");		
		assertEquals(400,resp.getStatus());		
		assertEquals(expectedResult,resp.getEntity());
	}
	
	@Test
	public void testDoPost(){		
		String expectedResult = "Not allowed";
		
		CityWeatherService service = new CityWeatherService();
		Response resp = service.doPost();		
		assertEquals(405,resp.getStatus());
		assertEquals(expectedResult,resp.getEntity());
	}
	
	@Test
	public void testDoPut(){		
		String expectedResult = "Not allowed";
		
		CityWeatherService service = new CityWeatherService();
		Response resp = service.doPut();		
		assertEquals(405,resp.getStatus());
		assertEquals(expectedResult,resp.getEntity());
	}
	
	@Test
	public void testDoOptions(){		
		String expectedResult = "Not allowed";
		
		CityWeatherService service = new CityWeatherService();
		Response resp = service.doOptions();		
		assertEquals(405,resp.getStatus());
		assertEquals(expectedResult,resp.getEntity());
	}
	
	@Test
	public void testDoDelete(){		
		String expectedResult = "Not allowed";
		
		CityWeatherService service = new CityWeatherService();
		Response resp = service.doDelete();		
		assertEquals(405,resp.getStatus());
		assertEquals(expectedResult,resp.getEntity());
	}
	
	@Test
	public void testDoHead(){		
		String expectedResult = "Not allowed";
		
		CityWeatherService service = new CityWeatherService();
		Response resp = service.doHead();		
		assertEquals(405,resp.getStatus());
		assertEquals(expectedResult,resp.getEntity());
	}
	
	private void injectField(Object target,String fieldName,Object value) throws Exception{
		Field injectedField = target.getClass().getDeclaredField(fieldName);
		injectedField.setAccessible(true);
		injectedField.set(target,value);
	}
}
