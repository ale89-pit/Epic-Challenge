package com.biblioTech.Security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.biblioTech.Security.entity.Province;
import com.biblioTech.Security.service.ProvinceService;

@Controller
public class ProvinceController {
	
	@Autowired ProvinceService provinceService;
	
	public Province save(Province p ) {
		return provinceService.save(p);
	}
	
}
