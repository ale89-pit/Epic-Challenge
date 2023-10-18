package com.biblioTech.Security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.biblioTech.Security.entity.Address;
import com.biblioTech.Security.exception.MyAPIException;
import com.biblioTech.Security.repository.AddressRepository;

import jakarta.persistence.EntityExistsException;

@Service
public class AddressService {

	@Autowired AddressRepository addressRepository;
	
	public Address saveAddress(Address a) {
		return addressRepository.save(a);
	}
	
    public Address updateAddress(long id,Address a) {
        if(!addressRepository.existsById(id)){
          throw new EntityExistsException("This address does not exists");
        }
        Address address = addressRepository.findById(id).get();
        return addressRepository.save(address);
    }
      public Address getAddress(long id) {
          return addressRepository.findById(id).get();
      }
      
//      public String deleteAddress(Long id) {
//  		if (!addressRepository.existsById(id)) {
//  			throw new MyAPIException(HttpStatus.NOT_FOUND, "This address does not exits");
//  		}
//  		addressRepository.deleteById(id);
//  		
//  		return "This address has been deleted";
//  	}
}
