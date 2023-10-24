package com.biblioTech.Security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblioTech.Security.entity.Library;
import com.biblioTech.Security.payload.BookDto;
import com.biblioTech.Security.payload.LibraryDto;
import com.biblioTech.Security.service.LibraryService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/library")
public class LibraryController {
	@Autowired LibraryService libraryService;
	
	@GetMapping("/all")
	public ResponseEntity<?> allLibraries(){
		return ResponseEntity.ok(libraryService.getAllLibraries());
	}
	
	@GetMapping("/{library_id}")
	public ResponseEntity<?> libraryById(@PathVariable Long library_id){
		return ResponseEntity.ok(libraryService.getLibraryById(library_id));
	}
	
	//TODO Serve?!
	@PostMapping("/add")
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> saveLibrary(@RequestBody LibraryDto library){
		return ResponseEntity.ok(libraryService.saveLibrary(library));
	}
	
	@PutMapping("/{library_id}")
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> editLibrary(@PathVariable Long library_id, @RequestBody Library library){
		return ResponseEntity.ok(libraryService.updateLibrary(library_id, library));
	}
	
	//TODO correggere il metodo 
	@PostMapping("/addBooks")
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> addBooks(@PathVariable Long library_id, String path){
		return ResponseEntity.ok(libraryService.addLibraryBooks(library_id, path));
	}
	
	@PostMapping("/addBook")
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> addBook(@RequestBody Long id, BookDto book, Integer quantity){
		return ResponseEntity.ok(libraryService.addLibraryBook(id, book, quantity));
	}
	
	//TODO rimuovere moderator?
	@DeleteMapping("/{library_id}")
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> deleteLibrary(@PathVariable Long library_id){
		return ResponseEntity.ok(libraryService.deleteLibrary(library_id));
	}
	
}
