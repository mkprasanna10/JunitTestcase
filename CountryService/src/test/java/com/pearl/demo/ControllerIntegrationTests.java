package com.pearl.demo;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.pearl.demo.beans.Country;


@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(classes={ControllerIntegrationTests.class})
public class ControllerIntegrationTests 
{
	@Test
	@Order(1)
	@Disabled
	public void getAllCountries_Test() throws Exception
	{
		String ExpectedValue = "[\r\n" + 
				"    {\r\n" + 
				"        \"id\": 1,\r\n" + 
				"        \"country\": \"IND\",\r\n" + 
				"        \"countrycapital\": \"Delhi\"\r\n" + 
				"    },\r\n" + 
				"    {\r\n" + 
				"        \"id\": 2,\r\n" + 
				"        \"country\": \"USA\",\r\n" + 
				"        \"countrycapital\": \"Washington\"\r\n" + 
				"    },\r\n" + 
				"    {\r\n" + 
				"        \"id\": 3,\r\n" + 
				"        \"country\": \"JPY\",\r\n" + 
				"        \"countrycapital\": \"Tokya\"\r\n" + 
				"    },\r\n" + 
				"    {\r\n" + 
				"        \"id\": 4,\r\n" + 
				"        \"country\": \"GER\",\r\n" + 
				"        \"countrycapital\": \"Berlin\"\r\n" + 
				"    }\r\n" + 
				"]";
		
		String url = "http://localhost:8081/getCountries";
		
		RestTemplate restTemplate = new RestTemplate();
	    ResponseEntity<String> res = restTemplate.getForEntity(url, String.class);

	    System.out.println(res.getStatusCode());
	    System.out.println(res.getBody());
	    
	    JSONAssert.assertEquals(ExpectedValue, res.getBody(), false);
	
	}
	
	
	//public Country getCountrybyID(int id)
	@Test
	@Order(2)
	@Disabled
	public void getCountrybyID_Test() throws Exception
	{
		String ExpectedValue = "{\r\n" + 
				"    \"id\": 1,\r\n" + 
				"    \"country\": \"IND\",\r\n" + 
				"    \"countrycapital\": \"Delhi\"\r\n" + 
				"}";
		
		String url = "http://localhost:8081/getCountry/1";
		
		RestTemplate restTemplate = new RestTemplate();
		
		ResponseEntity<String> res = restTemplate.getForEntity(url, String.class);
		
		System.out.println(res.getStatusCode());
		System.out.println(res.getBody());
		
		JSONAssert.assertEquals(ExpectedValue, res.getBody(), false);
	}
	
	
	//public Country getCountrybyName(String countryName)
	@Test
	@Order(3)
	@Disabled
	public void getCountrybyName_Test() throws Exception
	{
		String url = "http://localhost:8081/getCountry/countryname?name=GER";
		
		String ExpectedValue = "{\r\n" + 
				"    \"id\": 4,\r\n" + 
				"    \"country\": \"GER\",\r\n" + 
				"    \"countrycapital\": \"Berlin\"\r\n" + 
				"}";
		
		RestTemplate restTemplate = new RestTemplate();
	
		ResponseEntity<String> res = restTemplate.getForEntity(url, String.class);
		
		System.out.println(res.getBody());
		System.out.println(res.getStatusCode());
		
		JSONAssert.assertEquals(ExpectedValue, res.getBody(), false);
	}
	
	
	//public Country addCountry(Country country)
	@Test
	@Order(4)
	@Disabled
	public void addCountry_Test() throws Exception
	{
		Country country = new Country(5,"PAK","Karachi");
		
		String url = "http://localhost:8081/addCountry";
		
		String ExpectedValue = "{\r\n" + 
				"    \"id\": 5,\r\n" + 
				"    \"country\": \"PAK\",\r\n" + 
				"    \"countrycapital\": \"Karachi\"\r\n" + 
				"}";
		
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders httprequest = new HttpHeaders();
		httprequest.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<Country> request = new HttpEntity<Country>(country,httprequest);
		
		ResponseEntity<String> res = restTemplate.postForEntity(url, request, String.class);
		
		JSONAssert.assertEquals(ExpectedValue, res.getBody(), false);
		assertEquals(HttpStatus.CREATED,res.getStatusCode());
	}
	
	//public Country updateCountry(Country country)
	@Test
	@Order(5)
	public void updateCountry_Test() throws Exception
	{
		Country country = new Country(4,"SRI","Colombo");
		
		String url = "http://localhost:8081/updateCountry/4";
		
		String ExpectedValue = "{\r\n" + 
				"    \"id\": 4,\r\n" + 
				"    \"country\": \"SRI\",\r\n" + 
				"    \"countrycapital\": \"Colombo\"\r\n" + 
				"}";
		
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders httprequest = new HttpHeaders();
		httprequest.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<Country> request = new HttpEntity<Country>(country,httprequest);
		
		ResponseEntity<String> res = restTemplate.exchange(url,HttpMethod.PUT,request, String.class);
		
		JSONAssert.assertEquals(ExpectedValue, res.getBody(), false);
		assertEquals(HttpStatus.OK,res.getStatusCode());
	}
	
	//public void deleteCountry(Country country)
	@Test
	@Order(6)
	public void deleteCountry_Test() throws Exception
	{
		Country country = new Country(4,"SRI","Colombo");
		
		String url = "http://localhost:8081/deleteCountry/4";
		
		String ExpectedValue = "{\r\n" + 
				"    \"id\": 4,\r\n" + 
				"    \"country\": \"SRI\",\r\n" + 
				"    \"countrycapital\": \"Colombo\"\r\n" + 
				"}";
		
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders httprequest = new HttpHeaders();
		httprequest.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<Country> request = new HttpEntity<Country>(country,httprequest);
		
		ResponseEntity<String> res = restTemplate.exchange(url,HttpMethod.DELETE,request, String.class);
		
		JSONAssert.assertEquals(ExpectedValue, res.getBody(), false);
		assertEquals(HttpStatus.OK,res.getStatusCode());
	}
}
