package com.biblioTech.Security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblioTech.Security.entity.Province;
import com.biblioTech.Security.service.ProvinceService;

@RestController
@CrossOrigin(value = "*")
@RequestMapping("/province")
public class ProvinceController {
	
	@Autowired ProvinceService provinceService;
	
	public Province save(Province p ) {
		return provinceService.save(p);
	}
	
	@GetMapping("/all")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR')")
	public ResponseEntity<?> gettAllProvince(){
		return  ResponseEntity.ok(provinceService.getAllProvince());
	}
	
}
