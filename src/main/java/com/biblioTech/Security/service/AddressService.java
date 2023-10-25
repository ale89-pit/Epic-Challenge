package com.biblioTech.Security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioTech.Security.entity.Address;
import com.biblioTech.Security.entity.Municipality;
import com.biblioTech.Security.payload.AddressDto;
import com.biblioTech.Security.repository.AddressRepository;
import com.biblioTech.Security.repository.MunicipalityRepository;

import jakarta.persistence.EntityExistsException;

@Service
public class AddressService {

	@Autowired
	AddressRepository addressRepository;
	@Autowired
	MunicipalityRepository municipalityRepository;

	public Address saveAddress(AddressDto addressDto) {
		Address a = new Address();
		a.setStreet(addressDto.getStreet());
		a.setNumber(addressDto.getStreetNumber());
		if (municipalityRepository.existsById(addressDto.getMunicipality())) {
			Municipality m = municipalityRepository.findById(addressDto.getMunicipality()).get();
			a.setMunicipality(m);
		}

		return addressRepository.save(a);
	}

	public Address updateAddress(long id, Address a) {
		if (!addressRepository.existsById(id)) {
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
