package com.pearl.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.pearl.demo.beans.Country;

public class TestApplication 
{
	public static void main(String args[])
	{
		List<Country> mycountries = new ArrayList<Country>();
		mycountries.add(new Country(1, "IND", "Delhi"));
		mycountries.add(new Country(2, "USA", "Washington"));
		
		
	}
}

