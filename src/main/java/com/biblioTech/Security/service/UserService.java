package com.biblioTech.Security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioTech.Security.entity.Address;
import com.biblioTech.Security.entity.User;
import com.biblioTech.Security.payload.AddressDto;
import com.biblioTech.Security.payload.UserDto;
import com.biblioTech.Security.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	@Autowired
	AddressService addressService;

	public String addAddressUser(long id, AddressDto a) {
		if (userRepository.existsById(id)) {
			User u = userRepository.findById(id).get();
			Address added = addressService.saveAddress(a);
			u.setAddress(added);
			userRepository.save(u);
			return "Address added succefully";
		}
		return "user non found";

	}

	public String updateUser(long id, UserDto userDto) {
		if (userRepository.existsById(id)) {
			User u = userRepository.findById(id).get();
			if (userDto.getFullname() != null) {
				u.setFullname(userDto.getFullname());
			} else {
				u.setFullname(u.getFullname());
			}
			if (userDto.getEmail() != null) {
				u.setEmail(userDto.getEmail());
			} else {
				u.setEmail(u.getEmail());
			}
			if (userDto.getUsername() != null) {
				// TODO: controlla se username già esistente
				u.setUsername(userDto.getUsername());

			} else {
				u.setUsername(u.getUsername());
			}
			userRepository.save(u);
			return "Address added succefully";
		}
		return "user non found";

	}

}
