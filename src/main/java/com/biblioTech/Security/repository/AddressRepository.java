package com.biblioTech.Security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.biblioTech.Security.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>{

	@Query(value = "Select * from address where lat = :lat and lon = :lon and street = :street", nativeQuery = true)
	public Address findByLatAndLonAndStreet(String lat,String lon,String street);
}
