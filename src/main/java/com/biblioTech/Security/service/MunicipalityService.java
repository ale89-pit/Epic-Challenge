package com.biblioTech.Security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioTech.Security.entity.Municipality;
import com.biblioTech.Security.entity.Province;
import com.biblioTech.Security.repository.MunicipalityRepository;


@Service
public class MunicipalityService {

	@Autowired MunicipalityRepository  municipalityRepository;
	
	public Municipality save(Municipality m ) {
		return municipalityRepository.save(m);
	}
	
	public List<Municipality> findByProvince(Province p ) {
		return municipalityRepository.findByProvince(p);
	}
	
	//return a list of municipalities by province name
	public List<Municipality> findByProvinceName(String name){
		return municipalityRepository.findByProvinceName(name);
	}
	    
}

