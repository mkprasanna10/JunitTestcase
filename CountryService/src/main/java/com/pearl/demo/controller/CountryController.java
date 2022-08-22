package com.pearl.demo.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pearl.demo.beans.Country;
import com.pearl.demo.services.CountryService;

@RestController
public class CountryController 
{
	@Autowired
	CountryService countryservice;
	
	@GetMapping("/getCountries")
	public ResponseEntity<List<Country>> getCountries()
	{
		try
		{
			List<Country> countries = countryservice.getAllCountries();
			return new ResponseEntity<List<Country>>(countries,HttpStatus.FOUND);
		}
		catch(Exception e)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);		
		}
	}
	
	@GetMapping("/getCountry/{id}")
	public ResponseEntity<Country> getCountryById(@PathVariable(value="id") int id)
	{
		try
		{
			Country country = countryservice.getCountrybyID(id);
			return new ResponseEntity<Country>(country,HttpStatus.FOUND);
		}
		catch(Exception e)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
		
	@GetMapping("/getCountry/countryname")
	public ResponseEntity<Country> getCountryByName(@RequestParam(value = "name") String countryName)
	{
		try
		{
			System.out.println("countryName--->"+countryName);
			Country country = countryservice.getCountrybyName(countryName);
			return new ResponseEntity<Country>(country,HttpStatus.FOUND);
		}
		catch(Exception e)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/addCountry")
	public ResponseEntity<Country> addCountry(@RequestBody Country country)
	{
		try
		{
			country = countryservice.addCountry(country);
			return new ResponseEntity<Country>(country, HttpStatus.CREATED);
		}
		catch(Exception e)
		{
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}
	
	@PutMapping("/updateCountry/{id}")
	public ResponseEntity<Country> updateCountry(@PathVariable(value="id") int id, @RequestBody Country country)
	{
		try
		{
			System.out.println("Country ID ----> "+id);
			System.out.println("Country --->"+country);
			Country oldcountry = countryservice.getCountrybyID(id);
			System.out.println("Before oldcountry --->"+oldcountry);
			oldcountry.setCountry(country.getCountry());
			oldcountry.setCountrycapital(country.getCountrycapital());
			System.out.println("After oldcountry --->"+oldcountry);
			country = countryservice.updateCountry(oldcountry);
			
			return new ResponseEntity<Country>(country,HttpStatus.OK);
		}
		catch(Exception e)
		{
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}
	
	@DeleteMapping("/deleteCountry/{id}")
	public ResponseEntity<Country> deleteCountry(@PathVariable(value="id") int id)
	{
		Country country = null;
		try
		{
			country = countryservice.getCountrybyID(id);
			countryservice.deleteCountry(country);		
		}
		catch(Exception e)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Country>(country,HttpStatus.OK);
	}
	
}
