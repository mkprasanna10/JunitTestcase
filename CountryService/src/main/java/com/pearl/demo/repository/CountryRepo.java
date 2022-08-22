package com.pearl.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pearl.demo.beans.Country;

@Repository
public interface CountryRepo extends JpaRepository<Country, Long>{

}
