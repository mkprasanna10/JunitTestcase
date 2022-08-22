package com.pearl.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pearl.demo.beans.Country;
import com.pearl.demo.controller.CountryController;
import com.pearl.demo.services.CountryService;

@TestMethodOrder(OrderAnnotation.class)
@ContextConfiguration
@AutoConfigureMockMvc
@ComponentScan(basePackages="com.pearl.demo")
@SpringBootTest(classes= {ControllerMockMvcTests.class})
public class ControllerMockMvcTests 
{
	@Autowired
	MockMvc mockmvc;
	
	@Mock	
	CountryService countryservice;
	
	@InjectMocks
	CountryController countrycontroller;
	
	List<Country> myCountryList;
	
	Country country;
	
	@BeforeEach
	public void setup()
	{
		mockmvc = MockMvcBuilders.standaloneSetup(countrycontroller).build();
	}
	
	@Test
	@Order(1)
	public void getCountries_Test() throws Exception
	{
		myCountryList = new ArrayList<Country>();
		myCountryList.add(new Country(1, "IND", "Delhi"));
		myCountryList.add(new Country(2, "USA", "Washington"));
		myCountryList.add(new Country(3, "PAK", "Karachi"));
		
		when(countryservice.getAllCountries()).thenReturn(myCountryList);
		
		this.mockmvc.perform(get("/getCountries"))
		.andExpect(status().isFound())
		.andDo(print());
	}
	
	@Test
	@Order(2)
	public void getCountryById_Test() throws Exception
	{
		country = new Country(1,"IND","Delhi");
		
		int CountryId = 1;
		
		when(countryservice.getCountrybyID(CountryId)).thenReturn(country);
		
		this.mockmvc.perform(get("/getCountry/{id}",CountryId))
		.andExpect(status().isFound())
		.andExpect(MockMvcResultMatchers.jsonPath(".id").value(1))
		.andExpect(MockMvcResultMatchers.jsonPath(".country").value("IND"))
		.andExpect(MockMvcResultMatchers.jsonPath(".countrycapital").value("Delhi"))
		.andDo(print());
	}
	
	@Test
	@Order(3)
	public void getCountryByName_Test() throws Exception
	{
		String CountryName = "USA";
		country = new Country(2,"USA","Washington");
		
		when(countryservice.getCountrybyName(CountryName)).thenReturn(country);
		
		this.mockmvc.perform(get("/getCountry/countryname").param("name", "USA"))
		.andExpect(status().isFound())
		.andExpect(MockMvcResultMatchers.jsonPath(".id").value(2))
		.andExpect(MockMvcResultMatchers.jsonPath(".country").value("USA"))
		.andExpect(MockMvcResultMatchers.jsonPath(".countrycapital").value("Washington"))
		.andDo(print());
		
	}
	
	@Test
	@Disabled
	@Order(4)
	public void addCountry_Test() throws Exception
	{
		country = new Country(4,"JPY","Tokyo");
		
		when(countryservice.addCountry(country)).thenReturn(country);
		
		ObjectMapper mapper = new ObjectMapper();
		
		String jsonbody = mapper.writeValueAsString(country);
		
		this.mockmvc.perform(post("/addCountry")
							.content(jsonbody)
							.contentType(MediaType.APPLICATION_JSON)
				)
		.andExpect(status().isCreated())
		.andDo(print());
	}
	
	@Test
	@Disabled
	@Order(5)
	public void updateCountry_Test() throws Exception
	{
		country = new Country(3,"JPY","Tokyo");
		
		int CountryId = 3;
	
		when(countryservice.getCountrybyID(CountryId)).thenReturn(country);		
		when(countryservice.updateCountry(country)).thenReturn(country);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonbody = mapper.writeValueAsString(country);
			
		this.mockmvc.perform(put("/updateCountry/{id}",CountryId)
							.content(jsonbody)
							.contentType(MediaType.APPLICATION_JSON)
							)
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath(".id").value("3"))
		.andExpect(MockMvcResultMatchers.jsonPath(".country").value("JPY"))
		.andDo(print());
							
	}
	
	@Test
	@Order(6)
	@Disabled
	public void deleteCountry_Test() throws Exception
	{
		country = new Country(3,"JPY","Tokyo");
		
		int CountryId = 3;
		
		when(countryservice.getCountrybyID(CountryId)).thenReturn(country);			
		
		this.mockmvc.perform(delete("/deleteCountry/{id}",CountryId))
				.andExpect(status().isOk());
		
	}
}
