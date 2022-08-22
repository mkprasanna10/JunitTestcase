package com.pearl.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.pearl.demo.beans.Country;
import com.pearl.demo.controller.CountryController;
import com.pearl.demo.services.CountryService;

@SpringBootTest(classes={ControllerMackitoTests.class})
@TestMethodOrder(OrderAnnotation.class)
public class ControllerMackitoTests 
{
	@InjectMocks
	CountryController countrycontroller;
	
	@Mock
	CountryService countryservice;
	
	List<Country> myCountryList;
	
	Country country;
	
	@Test
	@Order(1)
	public void getCountries_Test()
	{
		myCountryList = new ArrayList<Country>();
		myCountryList.add(new Country(1, "IND", "Delhi"));
		myCountryList.add(new Country(2, "USA", "Washington"));
		myCountryList.add(new Country(3, "PAK", "Karachi"));
		
		when(countryservice.getAllCountries()).thenReturn(myCountryList);
		
		ResponseEntity<List<Country>> res = countrycontroller.getCountries();
		
		assertEquals(res.getStatusCode(), HttpStatus.FOUND);
	}
	
	@Test
	@Order(2)
	public void getCountryById_Test()
	{
		country = new Country(1,"IND","Delhi");
		
		int CountryId = 1;
		
		when(countryservice.getCountrybyID(CountryId)).thenReturn(country);
		
		ResponseEntity<Country> res = countrycontroller.getCountryById(CountryId);
	
		assertEquals(res.getStatusCode(),HttpStatus.FOUND);
		assertEquals(res.getBody().getId(),CountryId);

	}
	
	@Test
	@Order(3)
	public void getCountryByName_Test()
	{
		String CountryName = "USA";
		country = new Country(2,"USA","Washington");
		
		when(countryservice.getCountrybyName(CountryName)).thenReturn(country);
		
		ResponseEntity<Country> res = countrycontroller.getCountryByName(CountryName);

		assertEquals(res.getStatusCode(),HttpStatus.FOUND);
		assertEquals(res.getBody().getCountry(),CountryName);
	}
	
	@Test
	@Order(4)
	public void addCountry_Test()
	{
		country = new Country(4,"JPY","Tokyo");
		
		when(countryservice.addCountry(country)).thenReturn(country);
		
		ResponseEntity<Country> res = countrycontroller.addCountry(country);
		
		assertEquals(res.getStatusCode(), HttpStatus.CREATED);
		assertEquals(res.getBody().getId(),country.getId());
		assertEquals(res.getBody().getCountry(),country.getCountry());
		assertEquals(res.getBody().getCountrycapital(),country.getCountrycapital());
		
	}
	
	@Test
	@Order(5)
	public void updateCountry_Test()
	{
		country = new Country(3,"JPY","Tokyo");
		
		int CountryId = 3;
	
		when(countryservice.getCountrybyID(CountryId)).thenReturn(country);		
	
		assertEquals(country.getId(), CountryId);
		
		when(countryservice.updateCountry(country)).thenReturn(country);
		
		ResponseEntity<Country> res = countrycontroller.updateCountry(CountryId, country);
	
		assertEquals(res.getBody(),HttpStatus.OK);
		assertEquals(res.getBody().getCountry(),country.getCountry());
		assertEquals(res.getBody().getCountrycapital(),country.getCountrycapital());
		
	}
	
	@Test
	@Order(6)
	public void deleteCountry_Test()
	{
		country = new Country(3,"JPY","Tokyo");
		
		int CountryId = 3;
		
		when(countryservice.getCountrybyID(CountryId)).thenReturn(country);			
		assertEquals(country.getId(), CountryId);
		
		ResponseEntity<Country> res = countrycontroller.deleteCountry(CountryId);
		
		assertEquals(res.getStatusCode(), HttpStatus.OK);
		
	
	}
}
