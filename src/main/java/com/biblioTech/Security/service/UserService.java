package com.biblioTech.Security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioTech.Security.entity.User;
import com.biblioTech.Security.payload.UserDto;
import com.biblioTech.Security.repository.AddressRepository;
import com.biblioTech.Security.repository.MunicipalityRepository;
import com.biblioTech.Security.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	AddressService addressService;

	@Autowired
	AddressRepository addressRepository;
	@Autowired
	MunicipalityRepository municipalityRepository;

//	public String addAddressUser(long id, AddressDto a) {
//		if (userRepository.existsById(id)) {
//			User u = userRepository.findById(id).get();
//			u.getAddress().getId();
//			Address added = addressService.saveAddress(a);
//			userRepository.save(u);
//			return "Address added succefully";
//		}
//		return "user non found";
//
//	}

	public String updateUser(long id, UserDto userDto) {
		if (userRepository.existsById(id)) {
			User u = userRepository.findById(id).get();

			if (userDto.getFullname() != null) {
				u.setFullname(userDto.getFullname());
			}
			if (userDto.getPhone() != null) {
				u.setPhone(userDto.getPhone());
			}
			// se email nuova non è stata trovata nel db, può essere aggiornata
			if (userDto.getEmail() != null && userRepository.findByEmail(userDto.getEmail()).isEmpty()) {
				u.setEmail(userDto.getEmail());
			}
			// se username nuovo non è stato trovato nel db, può essere aggiornato
			if (userDto.getUsername() != null && userRepository.findByUsername(userDto.getUsername()).isEmpty()) {
				u.setUsername(userDto.getUsername());
			}
			if (userDto.getAddressDto() != null) {
				addressService.updateAddress(u.getAddress().getId(), userDto.getAddressDto());
			}

			userRepository.save(u);
			return "user updated succesfully";
		}
		return "user not found";

	}
//	
//	Address address = u.getAddress();
//	Optional<Municipality> municipality = municipalityRepository
//			.findById(userDto.getAddressDto().getMunicipalityId());
//
//	if (municipality.isEmpty()) {
//		return "updateUser: Municipality not found";
//	}
//
//	address.setMunicipality(municipality.get());
//	address.setStreet(userDto.getAddressDto().getStreet());
//	address.setNumber(userDto.getAddressDto().getStreetNumber());
//
//	u.setAddress(address);
//	addressRepository.save(address);

	
	public User findByUsernameOrEmail(String username,String email) {
		return userRepository.findByUsernameOrEmail(username, email).get();
	}
}
