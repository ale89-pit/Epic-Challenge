package com.biblioTech.Security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.biblioTech.Security.entity.Province;
import com.biblioTech.Security.repository.MunicipalityRepository;
import com.biblioTech.Security.repository.ProvinceRepository;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/app")
public class ProvinceMunicipalityController {
	@Autowired ProvinceRepository provinceRepository;
	@Autowired MunicipalityRepository comuniRepository;
	
	@GetMapping("/province")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getAll(){
		return ResponseEntity.ok(provinceRepository.findAll());
	}
	
	@GetMapping("/allComuni")
	public ResponseEntity<?> getAllComuni(){
		return ResponseEntity.ok(comuniRepository.findAll());
	}
	
	@GetMapping("/comuni")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getComuniByProvince(@RequestParam (value="sign") String provinceSign){
		Province p = provinceRepository.findById(provinceSign).get();
				return ResponseEntity.ok(comuniRepository.findByProvincename(p));
	}
}
