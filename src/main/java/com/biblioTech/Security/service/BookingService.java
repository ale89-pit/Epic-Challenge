package com.biblioTech.Security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.biblioTech.Enum.MembershipCardState;
import com.biblioTech.Enum.State;
import com.biblioTech.Security.entity.Booking;
import com.biblioTech.Security.exception.MyAPIException;
import com.biblioTech.Security.payload.BookingDto;
import com.biblioTech.Security.repository.BookingRepository;

import jakarta.persistence.EntityExistsException;

@Service
public class BookingService {

	@Autowired
	BookingRepository bookingRepository;

	public Booking saveBooking(Booking b) {
		if (b.getCard().getState().equals(MembershipCardState.APPROVED))
			return bookingRepository.save(b);
		return null;
	}

	public Booking updateBooking(Long id, BookingDto b) {

		if (!bookingRepository.existsById(id)) {
			throw new EntityExistsException("This booking does not exists");
		}
		Booking booking = bookingRepository.findById(id).get();

		if (b.getCard() != null)
			booking.setCard(b.getCard());
		if (b.getStartDate() != null)
			booking.setStartDate(b.getStartDate());
		if (b.getEndDate() != null)
			booking.setEndDate(b.getEndDate());
		if (b.getRestitutionDate() != null)
			booking.setRestitutionDate(b.getRestitutionDate());
		if (b.getState() != null)
			booking.setState(b.getState());
		if (b.getBooks() != null)
			booking.setBooks(b.getBooks());

		return bookingRepository.save(booking);
	}

	public Booking getBooking(Long id) {
		if (!bookingRepository.existsById(id)) {
			throw new EntityExistsException("This booking does not exists");
		}
		return bookingRepository.findById(id).get();
	}

	public List<Booking> getAllBookings() {
		return bookingRepository.findAll();
	}

	public List<Booking> getAllBookingsByLibraryId(Long id) {
		return bookingRepository.findBookingsByLibraryId(id);
	}

	public String deleteBooking(Long id) {
		if (!bookingRepository.existsById(id)) {
			throw new MyAPIException(HttpStatus.NOT_FOUND, "This booking does not exits");
		}
		bookingRepository.deleteById(id);

		return "This booking has been deleted";
	}

	public Booking setState(Long id, State state) {
		Booking booking = bookingRepository.findById(id).get();
		booking.setState(state);

		return booking;
	}

}
