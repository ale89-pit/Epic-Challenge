package com.biblioTech.Security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblioTech.Security.entity.Booking;
import com.biblioTech.Security.service.BookingService;
import com.biblioTech.Security.service.LibraryService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/booking")
public class BookingController {

	@Autowired
	BookingService bookingService;

	@Autowired
	LibraryService libraryService;

	// get all Bookings from a library
	@GetMapping("/{library_id}/all")
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> getAllBookingsByLibrary(@PathVariable Long library_id) {
		if (libraryService.getLibraryById(library_id) != null)
			return ResponseEntity.ok(bookingService.getAllBookingsByLibraryId(library_id));

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This library not exist");
	}

	@GetMapping("/{library_id}/{booking_id}")
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> getBookingByLibrary(@PathVariable Long library_id, @PathVariable Long booking_id) {
		if (libraryService.getLibraryById(library_id) != null)
			return ResponseEntity.ok(bookingService.getBooking(booking_id));

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This library not exist");
	}

	/*
	 * Crea nuova prenotazione solo se la MembershipCard è stata approvata dalla
	 * libreria
	 */
	@PostMapping("/newRequest/")
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> saveBooking(@RequestBody Booking bookingToSave) {
		if (bookingService.saveBooking(bookingToSave) != null)
			return ResponseEntity.ok(bookingToSave);
		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Membership card not accepted");
	}

	@PostMapping("/{library_id}/{booking_id}")
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> acceptBooking(@PathVariable Long library_id, @PathVariable Long booking_id) {
		Booking booking = bookingService.getBooking(booking_id);
		// TODO:

		return (ResponseEntity<?>) ResponseEntity.ok();
	}

	/*
	 * TODO: metodo per restituzione di uno o più libri che aggiorna la quantità
	 * nella booklist della libreria
	 */
//		libraryService.decreaseBooksQuantity(library_id, bookingToSave.getBooks());

}
