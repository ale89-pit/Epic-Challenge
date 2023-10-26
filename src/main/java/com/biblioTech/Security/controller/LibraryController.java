package com.biblioTech.Security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.biblioTech.Security.payload.BookDto;
import com.biblioTech.Security.payload.LibraryDto;
import com.biblioTech.Security.repository.FileDataRepository;
import com.biblioTech.Security.service.FileDataService;
import com.biblioTech.Security.service.LibraryService;
import com.biblioTech.message.ResponseMessage;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/library")
public class LibraryController {
	@Autowired
	LibraryService libraryService;
	@Autowired
	FileDataService fileDataService;
	@Autowired
	FileDataRepository fileDataRepository;

	@GetMapping("/all")
	public ResponseEntity<?> allLibraries() {
		return ResponseEntity.ok(libraryService.getAllLibraries());
	}

	@GetMapping("/{library_id}")
	public ResponseEntity<?> libraryById(@PathVariable Long library_id) {
		return ResponseEntity.ok(libraryService.getLibraryById(library_id));
	}

	// TODO Serve?!
	@PostMapping("/add")
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> saveLibrary(@RequestBody LibraryDto library) {
		return ResponseEntity.ok(libraryService.saveLibrary(library));
	}

	@PutMapping("/{library_id}")
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> editLibrary(@PathVariable Long library_id, @RequestBody LibraryDto library) {
		return ResponseEntity.ok(libraryService.updateLibrary(library_id, library));
	}

	// TODO correggere il metodo
	@PostMapping("/addBooks/{library_id}")
//	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> addBooks(@PathVariable Long library_id, @RequestParam("file") MultipartFile file) {
		String message = "";
		try {
//	    	salvo il csv in resources
			fileDataService.save(file);
//			FileData f = new FileData();
//
//			f.setNome(file.getOriginalFilename());
////	      recupero il file in locale con il metodo load() del fileDataLocal 
//			Resource fileCsv = fileDataService.load(file.getOriginalFilename());
//
////	      recuperanto il file eseguo questa funzione per recuperare il path giusto che si userà poi nel frontEnd
//			String url = MvcUriComponentsBuilder
//					.fromMethodName(LibraryController.class, "getFile", fileCsv.getFilename().toString()).build()
//					.toString();
//
//			f.setFilePath(url);
//			FileData saved = fileDataRepository.save(f);

			libraryService.addLibraryBooks(library_id, file.getOriginalFilename());
			message = "Uploaded the file successfully: " + file.getOriginalFilename();
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message + file.getOriginalFilename()));
		} catch (Exception e) {
			message = "Could not upload the file: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
			e.printStackTrace();

			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
		}
	}

	@GetMapping("/csv/{filename}")
	@ResponseBody
	public ResponseEntity<Resource> getFile(@PathVariable String filename) {
		Resource fileCsv = fileDataService.load(filename);
		return ResponseEntity.ok(fileDataService.load(filename));
	}

	@PostMapping("/addBook/{library_id}/{quantity}")
//	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> addBook(@PathVariable Long library_id, @PathVariable Integer quantity,
			@RequestBody BookDto book) {
		return ResponseEntity.ok(libraryService.addLibraryBook(library_id, book, quantity));
	}

	// TODO rimuovere moderator?
	@DeleteMapping("/{library_id}")
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> deleteLibrary(@PathVariable Long library_id) {
		return ResponseEntity.ok(libraryService.deleteLibrary(library_id));
	}

}