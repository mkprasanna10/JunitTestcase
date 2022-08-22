package com.pearl.demo.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Country 
{
	@Id
	@SequenceGenerator(name = "Country_id",allocationSize=1,sequenceName="Country_seq")
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="Country_id")
	private int id;
	private String country;
	private String countrycapital;
	
	public Country()
	{
		
	}
	
	@Override
	public String toString() {
		return "Country [id=" + id + ", country=" + country + ", countrycapital=" + countrycapital + "]";
	}

	public Country(int id, String country, String countrycapital) {
		//super();
		this.id = id;
		this.country = country;
		this.countrycapital = countrycapital;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCountrycapital() {
		return countrycapital;
	}
	public void setCountrycapital(String countrycapital) {
		this.countrycapital = countrycapital;
	}
	
	
	
}
