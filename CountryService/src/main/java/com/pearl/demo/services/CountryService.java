package com.pearl.demo.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pearl.demo.beans.Country;
import com.pearl.demo.repository.CountryRepo;

@Service
public class CountryService {
	
	@Autowired
	CountryRepo countryrepo;
	
	public List<Country> getAllCountries()
	{
		return countryrepo.findAll();
	}
	
	public Country getCountrybyID(int id)
	{
		Country countryfilter = null;
		
		List<Country> country = countryrepo.findAll();
		
		for(Country con : country)
		{
			if(con.getId()==id)
				countryfilter = con;
		}
		
		return countryfilter;
		//return country.stream().filter(e -> e.getId()==id).forEach(e -> con);
	}
	
	public Country getCountrybyName(String countryName)
	{
		List<Country> country = countryrepo.findAll();
		
		Country countryfilter = null;
		
		for(Country con : country)
		{
			if(con.getCountry().equals(countryName))
				countryfilter = con;
		}
		
		return countryfilter;
		//return (Country) country.stream().filter(e -> e.getCountry().equals(countryName)).collect(Collectors.toList());
	}
	
	public Country addCountry(Country country)
	{
		countryrepo.save(country);
		return country;
	}
	
	public Country updateCountry(Country country)
	{
		System.out.println("country from service---->"+country);
		countryrepo.save(country);
		return country;
	}
	
	public void deleteCountry(Country country)
	{
		countryrepo.delete(country);
	}
}
