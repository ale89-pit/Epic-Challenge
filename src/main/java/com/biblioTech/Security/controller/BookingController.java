package com.biblioTech.Security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.biblioTech.Security.entity.Booking;
import com.biblioTech.Security.service.BookingService;

@Controller
public class BookingController {

	@Autowired
	BookingService bookingService;

	public Booking save(Booking bookingToSave) {
		// TODO: decrease the available books in the library

		return bookingService.saveBooking(bookingToSave);
	}
	/*
	 * TODO: metodo per restituzione di uno o più libri che aggiorna la quantità
	 * nella booklist della libreria
	 */

}
