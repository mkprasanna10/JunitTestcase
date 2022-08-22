package com.pearl.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.pearl.demo.beans.Country;
import com.pearl.demo.repository.CountryRepo;
import com.pearl.demo.services.CountryService;

@SpringBootTest(classes= {ServiceMackitoTests.class})
@TestMethodOrder(OrderAnnotation.class)
public class ServiceMackitoTests 
{
	@Mock
	CountryRepo countryrepo;
	
	@InjectMocks
	CountryService countryservice;
	
	public List<Country> mycountries;
	
	//public List<Country> getAllCountries()
	@Test
	@Order(1)
	public void getAllCountries_Test()
	{
		mycountries = new ArrayList<Country>();
		mycountries.add(new Country(1, "IND", "Delhi"));
		mycountries.add(new Country(2, "USA", "Washington"));
		
		when(countryrepo.findAll()).thenReturn(mycountries);
		
		assertEquals(countryservice.getAllCountries(), mycountries);
	}
	
	
	//public Country getCountrybyID(int id)
	@Test
	@Order(2)
	public void getCountrybyID_Test()
	{
		mycountries = new ArrayList<Country>();
		mycountries.add(new Country(1, "IND", "Delhi"));
		mycountries.add(new Country(2, "USA", "Washington"));
		
		int myCountriesId = 2;
		
		when(countryrepo.findAll()).thenReturn(mycountries);
		
		System.out.println(countryservice.getCountrybyID(myCountriesId).getId());
		
		assertEquals(countryservice.getCountrybyID(myCountriesId).getId(),myCountriesId);
		
	}
	
	
	//public Country getCountrybyName(String countryName)
	@Test
	@Order(3)
	public void getCountrybyName_Test()
	{
		mycountries = new ArrayList<Country>();
		mycountries.add(new Country(1, "IND", "Delhi"));
		mycountries.add(new Country(2, "USA", "Washington"));
		
		String CountryName = "IND";
		
		when(countryrepo.findAll()).thenReturn(mycountries);
		
		assertEquals(countryservice.getCountrybyName(CountryName).getCountry(),CountryName);
	}
	
	
	//public Country addCountry(Country country)
	@Test
	@Order(4)
	public void addCountry_Test()
	{
		mycountries = new ArrayList<Country>();
		mycountries.add(new Country(1, "IND", "Delhi"));
		mycountries.add(new Country(2, "USA", "Washington"));
		
		Country country = new Country(3,"GER","Berlin");
	
		when(countryrepo.save(country)).thenReturn(country);
		
		assertEquals(countryservice.addCountry(country),country);
		
	}
	
	//public Country updateCountry(Country country)
	@Test
	@Order(5)
	public void updateCountry_Test()
	{
		Country country = new Country(3,"JPY","Tokyo");
			
		when(countryrepo.save(country)).thenReturn(country);
		
		assertEquals(countryservice.updateCountry(country),country);
	}
	
	//public void deleteCountry(Country country)
	@Test
	@Order(6)
	public void deleteCountry_Test()
	{
		Country country = new Country(3,"JPY","Tokyo");
		
		countryservice.deleteCountry(country);
		
		verify(countryrepo,times(1)).delete(country);
	}
}
