package com.biblioTech.Security.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.biblioTech.Security.entity.Municipality;
import com.biblioTech.Security.entity.Province;
import com.biblioTech.Security.service.MunicipalityService;

@Controller
public class MunicipalityController {
	
	@Autowired MunicipalityService municipalityService;
	
	public Municipality save(Municipality m ) {
		return municipalityService.save(m);
	}
	public List<Municipality> findByProvince(Province p ) {
		return municipalityService.findByProvince(p);
	}
	
	//return a list of municipalities by province name
	//provinceNameToSearch: 
	public List<Municipality> findByProvinceName(String name) throws Exception{
		
		String provinceNameToSearch = name.trim();//remove spaces
		provinceNameToSearch = provinceNameToSearch.substring(0, 1).toUpperCase() + provinceNameToSearch.substring(1);//capitalize first letter
		
		return municipalityService.findByProvinceName(provinceNameToSearch);
	}
}
