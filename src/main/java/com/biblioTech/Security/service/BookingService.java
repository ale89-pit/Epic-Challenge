package com.biblioTech.Security.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioTech.Enum.MembershipCardState;
import com.biblioTech.Enum.State;
import com.biblioTech.Security.entity.Book;
import com.biblioTech.Security.entity.Booking;
import com.biblioTech.Security.entity.Library;
import com.biblioTech.Security.entity.MembershipCard;
import com.biblioTech.Security.exception.ResourceNotFoundException;
import com.biblioTech.Security.payload.BookingDto;
import com.biblioTech.Security.repository.BookingRepository;
import com.biblioTech.Security.repository.MembershipCardRepository;

@Service
public class BookingService {

	@Autowired
	BookingRepository bookingRepository;

	@Autowired
	MembershipCardRepository membershipCardRepository;
	@Autowired
	LibraryService libraryService;

	public Booking saveBooking(BookingDto bookingDto) {
		MembershipCard card = membershipCardRepository.findById(bookingDto.getCardId()).get();
		if (card.getState().equals(MembershipCardState.APPROVED)) {
			Booking b = new Booking();
			Library l = card.getLibrary();
			System.out.println(card);
			System.out.println(l);

			for (String isbn : bookingDto.getBooks()) {
				System.out.println(isbn);
				for (Map.Entry<Book, Integer> entry : l.getBooklist().entrySet()) {
					Book book = entry.getKey();
					Integer quantity = entry.getValue();

					if (book.getIsbn().equals(isbn)) {
						if (quantity > 0) {
							System.out.println(book);
							b.getBooks().add(book);

						} else {
							bookingDto.getBooksNotAvailable().add(book);
						}

					}

				}
			}
			b.getBooks().forEach(book -> System.out.println(book));

			b.setCard(card);
			b.setState(State.PENDING);
			b.setConfirmed(false);
			b.setStartDate(null);
			b.setRestitutionDate(null);
			b.setEndDate(null);
			libraryService.decreaseBooksQuantity(l.getId(), b.getBooks());
			return bookingRepository.save(b);
		}
		return null;
	}

	public Booking acceptBooking(Long id, LocalDate endDate) {
		Booking b = bookingRepository.findById(id).get();
		b.setConfirmed(true);
		b.setStartDate(LocalDate.now());
		b.setState(State.APPROVED);
		b.setEndDate(endDate);
		return bookingRepository.save(b);

	}

	public Booking rejectBooking(Long id) {
		Booking b = bookingRepository.findById(id).get();
		Library l = b.getCard().getLibrary();
		b.setStartDate(null);
		b.setState(State.REJECTED);
		b.setEndDate(null);
		libraryService.increaseBookQuantity(l.getId(), b.getBooks());
		return bookingRepository.save(b);

	}

	// TODO creare procedura automatica per settare lo stato in scaduto se endDate è
	// minore di oggi
	public Booking returnedBooking(Long id) {
		Booking b = bookingRepository.findById(id).get();
		Library l = b.getCard().getLibrary();
		b.setState(State.RETURNED);
		b.setEndDate(LocalDate.now());
		libraryService.increaseBookQuantity(l.getId(), b.getBooks());
		return bookingRepository.save(b);

	}

	public Booking updateBooking(Long id, BookingDto b) {

		if (!bookingRepository.existsById(id)) {
			throw new ResourceNotFoundException("Booking", "id", id);
		}
		MembershipCard m = membershipCardRepository.findById(b.getCardId()).get();
		Library l = m.getLibrary();
		Booking booking = bookingRepository.findById(id).get();

		if (b.getCardId() != null)
			booking.setCard(m);
		if (b.getStartDate() != null)
			booking.setStartDate(b.getStartDate());
		if (b.getEndDate() != null)
			booking.setEndDate(b.getEndDate());
		if (b.getRestitutionDate() != null)
			booking.setRestitutionDate(b.getRestitutionDate());
		if (b.getState() != null)
			booking.setState(b.getState());
		for (String isbn : b.getBooks()) {
			for (Map.Entry<Book, Integer> entry : l.getBooklist().entrySet()) {
				Book book = entry.getKey();
				Integer quantity = entry.getValue();

				if (book.getIsbn().equals(isbn)) {
					booking.getBooks().add(book);

				}

			}
		}
		return bookingRepository.save(booking);
	}

	public Booking getBooking(Long id) {
		if (!bookingRepository.existsById(id)) {
			throw new ResourceNotFoundException("Booking", "id", id);
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
			throw new ResourceNotFoundException("Booking", "id", id);
		}
		Booking booking = bookingRepository.findById(id).get();
		MembershipCard m = membershipCardRepository.findById(booking.getCard().getId()).get();
		Library l = m.getLibrary();
		libraryService.increaseBookQuantity(l.getId(), booking.getBooks());
		bookingRepository.deleteById(id);

		return "This booking has been deleted";
	}

	public Booking setState(Long id, State state) {
		Booking booking = bookingRepository.findById(id).get();
		booking.setState(state);

		return booking;
	}

}
