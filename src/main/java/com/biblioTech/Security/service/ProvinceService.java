package com.biblioTech.Security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioTech.Security.entity.Province;
import com.biblioTech.Security.repository.ProvinceRepository;


@Service
public class ProvinceService {

	@Autowired ProvinceRepository provinceRepository;
	
	public Province save(Province p) {
		return provinceRepository.save(p);
	}
		
	    
}

