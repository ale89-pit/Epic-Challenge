package com.biblioTech.Security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioTech.Security.entity.Address;
import com.biblioTech.Security.entity.Municipality;
import com.biblioTech.Security.exception.ResourceNotFoundException;
import com.biblioTech.Security.payload.AddressDto;
import com.biblioTech.Security.repository.AddressRepository;
import com.biblioTech.Security.repository.MunicipalityRepository;

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
		if (municipalityRepository.existsById(addressDto.getMunicipalityId())) {
			Municipality m = municipalityRepository.findById(addressDto.getMunicipalityId()).get();
			a.setMunicipality(m);
		}

		return addressRepository.save(a);
	}

	public Address updateAddress(long id, AddressDto a) {
		if (!addressRepository.existsById(id)) {
			throw new ResourceNotFoundException("Address", "id", id);
		}
		Address address = addressRepository.findById(id).get();
		if (!municipalityRepository.existsById(a.getMunicipalityId())) {
			throw new ResourceNotFoundException("Municipality", "id", a.getMunicipalityId());
		}
		Municipality municipality = municipalityRepository.findById(a.getMunicipalityId()).get();

		if (a.getMunicipalityId() != null)
			address.setMunicipality(municipality);

		if (a.getStreet() != null)
			address.setStreet(a.getStreet());

		if (a.getStreetNumber() != null)
			address.setNumber(a.getStreetNumber());

		return addressRepository.save(address);
	}

	public Address getAddress(long id) {
		return addressRepository.findById(id).get();
	}

	// public String deleteAddress(Long id) {
	// if (!addressRepository.existsById(id)) {
	// throw new MyAPIException(HttpStatus.NOT_FOUND, "This address does not
	// exits");
	// }
	// addressRepository.deleteById(id);
	//
	// return "This address has been deleted";
	// }
}
