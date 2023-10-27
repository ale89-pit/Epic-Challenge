package com.biblioTech.Security.controller;

import java.util.Map;

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

import com.biblioTech.Enum.State;
import com.biblioTech.Security.entity.Book;
import com.biblioTech.Security.entity.Booking;
import com.biblioTech.Security.entity.Library;
import com.biblioTech.Security.service.BookingService;
import com.biblioTech.Security.service.LibraryService;
import com.biblioTech.message.ResponseMessage;

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

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("This library not exist"));
	}

	/*
	 * Crea nuova prenotazione solo se la MembershipCard è stata approvata dalla
	 * libreria
	 */
	@PostMapping("/newRequest")
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> saveBooking(@RequestBody Booking bookingToSave) {
		if (bookingService.saveBooking(bookingToSave) != null)
			return ResponseEntity.ok(bookingToSave);
		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
				.body(new ResponseMessage("Membership card not accepted"));
	}

	@PostMapping("/{library_id}/{booking_id}")
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> acceptBooking(@PathVariable Long library_id, @PathVariable Long booking_id) {
		try {
			Library library = libraryService.getLibraryById(library_id);
			Booking booking = bookingService.getBooking(booking_id);
			Map<Book, Integer> libraryBooklist = library.getBooklist();

			// controllo se la prenotazione è tra quelle della libreria
			if (bookingService.getAllBookingsByLibraryId(library_id).contains(booking)) {

				// se tutti i libri della prenotazione sono nella booklist della libreria
				if (booking.getBooks().stream().allMatch(book -> libraryBooklist.get(book) != null)) {

					bookingService.setState(booking_id, State.APPROVED);
					libraryService.decreaseBooksQuantity(library_id, booking.getBooks());
					return ResponseEntity.status(HttpStatus.ACCEPTED).body(bookingService.getBooking(booking_id));

				}
				return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
						.body(new ResponseMessage("One or more books are not from this library"));

			}
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseMessage("This Booking is not for this library"));

		} catch (Exception e) {
			// se non trova library o booking
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage(e.getMessage()));
		}

	}

	/*
	 * TODO: metodo per restituzione di uno o più libri che aggiorna la quantità
	 * nella booklist della libreria
	 */
//		libraryService.decreaseBooksQuantity(library_id, bookingToSave.getBooks());

}
