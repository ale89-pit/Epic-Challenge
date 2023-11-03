package com.biblioTech.Security.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblioTech.Security.entity.Municipality;
import com.biblioTech.Security.entity.Province;
import com.biblioTech.Security.service.MunicipalityService;

@RestController
@CrossOrigin(value="*")
@RequestMapping("/municipality")
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
	@GetMapping("/{name}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR')")
	public List<Municipality> findByProvinceName(@PathVariable String name) throws Exception{
		
		String provinceNameToSearch = name.trim();//remove spaces
		provinceNameToSearch = provinceNameToSearch.substring(0, 1).toUpperCase() + provinceNameToSearch.substring(1);//capitalize first letter
		
		return municipalityService.findByProvinceName(provinceNameToSearch);
	}
}
