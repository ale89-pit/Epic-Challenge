package com.biblioTech.Security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblioTech.Enum.Category;
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

//	http://localhost:8080/book/all?page=1&size=5
	// per fare la chiamata con la paginazione bisogna aggiungere alla chiamata
	// page che indica il numero della pagina
	// size che indica il numero di elementi da visualizzare, se non si specifica di
	// default mostra 20 risultati
	@GetMapping("/all")
	public ResponseEntity<?> getAllBook(Pageable pageable) {
		return ResponseEntity.ok(bookService.getAllBooks(pageable));
	}

	@GetMapping("/{isbn}")
	public ResponseEntity<?> getBookById(@PathVariable String isbn) {
		return ResponseEntity.ok(bookService.getBook(isbn));
	}

	@GetMapping("/allT")
	public ResponseEntity<?> getAllBook() {
		return ResponseEntity.ok(bookService.gettAllBooks());
	}

	@GetMapping("/bookInLibrary/{isbn}")
	public ResponseEntity<?> getLibraryWithBook(@PathVariable String isbn) {
		return ResponseEntity.ok(libraryService.getLibraryWithBookAvaible(isbn));
	}

	@GetMapping("/category/{category}")
	public ResponseEntity<?> getBookFromCategory(@PathVariable Category category, Pageable pageable) {
		System.out.println(category);
		return ResponseEntity.ok(bookService.getBookFromCategory(category, pageable));
	}

}
