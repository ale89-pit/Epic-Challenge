package com.biblioTech.Security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.biblioTech.Security.service.BookService;
import com.biblioTech.Security.service.LibraryService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/book")
public class BookController {
	
	@Autowired
	BookService bookService;
	@Autowired
	LibraryService libraryService;
	
	@GetMapping("/all")
	public ResponseEntity<?> getAllBook(){
		return ResponseEntity.ok(bookService.getAllBooks());
	}
	
	@GetMapping("/bookInLibrary/{isbn}")
	public ResponseEntity<?>getLibraryWithBook(@PathVariable String isbn){
		return ResponseEntity.ok(libraryService.getLibraryWithBookAvaible(isbn));
	}

}
