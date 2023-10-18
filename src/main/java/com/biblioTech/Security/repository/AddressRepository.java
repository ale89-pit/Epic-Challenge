package com.biblioTech.Security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.biblioTech.Security.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>{

}
