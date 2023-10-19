package com.biblioTech.Security.controller;

import javax.xml.transform.OutputKeys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblioTech.Security.entity.Library;
import com.biblioTech.Security.repository.LibraryRepository;
import com.biblioTech.Security.service.LibraryService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/library")
public class LibraryController {

	@Autowired LibraryService libraryService;
	
	
	@PostMapping
	@PreAuthorize("hasRole('MODERATOR')")
	public ResponseEntity<?> saveLibrary(@RequestBody Library l){
		return ResponseEntity.ok(libraryService.saveLibrary(l));
	}
}
