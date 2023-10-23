package com.biblioTech.Security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.biblioTech.Security.entity.Book;
import com.biblioTech.Security.entity.Booking;
import com.biblioTech.Security.exception.MyAPIException;
import com.biblioTech.Security.repository.BookRepository;
import com.biblioTech.Security.repository.BookingRepository;

import jakarta.persistence.EntityExistsException;



	@Service
	public class BookingService {

		@Autowired BookingRepository bookingRepository;
		
		public Booking saveBooking(Booking b) {
			return bookingRepository.save(b);
		}
<<<<<<< Updated upstream
		
	    public Booking updateBooking(long id,Booking b) {
	        if(!bookingRepository.existsById(id)){
	          throw new EntityExistsException("This booking does not exists");
	        }
	        Booking booking = bookingRepository.findById(id).get();
	        return bookingRepository.save(booking);
	    }
	      public Booking getBooking(long id) {
	          return bookingRepository.findById(id).get();
	      }
	      
	      public List<Booking> getAllBookings(long id) {
	          return bookingRepository.findAll();
	      }
	      
	      
	      public String deleteBooking(Long id) {
	  		if (!bookingRepository.existsById(id)) {
	  			throw new MyAPIException(HttpStatus.NOT_FOUND, "This booking does not exits");
	  		}
	  		bookingRepository.deleteById(id);
	  		
	  		return "This booking has been deleted";
	  	}
=======
		// TODO: update book in base a campi del Bookdto
		Booking booking = bookingRepository.findById(id).get();
		return bookingRepository.save(booking);
>>>>>>> Stashed changes
	}


