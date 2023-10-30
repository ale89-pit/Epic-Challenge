package com.biblioTech.Security.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.biblioTech.Security.repository.AddressRepository;
import com.biblioTech.Security.repository.BookRepository;
import com.biblioTech.Security.repository.BookingRepository;
import com.biblioTech.Security.repository.LibraryRepository;
import com.biblioTech.Security.repository.MembershipCardRepository;
import com.biblioTech.Security.repository.MunicipalityRepository;
import com.biblioTech.Security.repository.ProvinceRepository;
import com.biblioTech.Security.repository.UserRepository;
import com.biblioTech.Security.service.BookService;
import com.biblioTech.Security.service.DatabaseUpdateService;
import com.biblioTech.Security.service.FileDataService;
import com.biblioTech.Security.service.LibraryService;

@Component
@Order(3)
public class provaInserimento implements ApplicationRunner {

	@Autowired
	AddressRepository addressRepository;
	@Autowired
	LibraryRepository libraryRepository;
	@Autowired
	ProvinceRepository provinceRepository;
	@Autowired
	MunicipalityRepository municipalityRepository;
	@Autowired
	LibraryService libService;
	@Autowired
	BookService bookService;
	@Autowired
	UserRepository userRepository;
	@Autowired
	MembershipCardRepository membershipCardRepository;
	@Autowired
	BookingRepository bookingRepository;
	@Autowired
	BookRepository bookRepository;
	@Autowired
	FileDataService fileDataService;

	@Autowired
	DatabaseUpdateService databaseUpdateService;

	@Override
	public void run(ApplicationArguments args) throws Exception {

		// // Address

		// Province p = provinceRepository.findByName("Roma");
		// Municipality m = municipalityRepository.findByProvince(p).get(2);
		// System.out.println(m);
		// Address a = new Address();
		// a.setStreet("Via le dita dal naso");
		// a.setNumber("12");
		// a.setMunicipality(m);
		// a.setLon("0000012n");
		// a.setLat("0000003e");
		// a.setKm("123.000");
		//
		// addressRepository.save(a);
		//
		//
		// //Books
		// Book book1 = new Book();
		// book1.setIsbn("0000000000011");
		// book1.setTitle("Titolo del Libro 1");
		// book1.setAuthor("Autore del Libro 1");
		// book1.setPublisher("Editore");
		// book1.setCategory(Category.ADVENTURE);
		// book1.setLanguage(Languages.ITALIAN);
		// book1.setPublishedYear(LocalDate.of(2001, 1, 1));
		// book1.setCol("lll");
		// book1.setShelf("32");
		//
		//
		// Book book2 = new Book();
		// book2.setIsbn("0000000000012");
		// book2.setTitle("Titolo del Libro 2");
		// book2.setAuthor("Autore del Libro 2");
		// book2.setPublisher("Editore");
		// book2.setCategory(Category.ADVENTURE);
		// book2.setLanguage(Languages.ITALIAN);
		// book2.setPublishedYear(LocalDate.of(2001, 1, 1));
		// book2.setCol("lll");
		// book2.setShelf("32");
		//
		//
		// bookService.saveBook(book1);
		// bookService.saveBook(book2);
		//
		//
		// // Library

//		LibraryDto lDto = new LibraryDto("Prova2", new AddressDto("via delle due", "123", 2345L), "email", "pass",
//				"telefono");
//		libService.saveLibrary(lDto);
//
//		LibraryDto lDto2 = new LibraryDto("Lib2", new AddressDto("via delle due2", "1232", 245L), "email2", "pass2",
//				"telefono");
//		libService.saveLibrary(lDto2);
		// NON FUNZIONA PERCHE' IL SERVICE SI ASPETTA LIBRARY DTO E NON LIBRARY
		// Address a = addressRepository.findById(1L).get();
		// System.out.println(a);
		// Library lib = new Library();
		// lib.setName("Nome biblioteca");
		// lib.setAddress(a);
		//
		// lib.setEmail("email@example.com");
		// lib.setPassword("password");
		// lib.setPhone("123-456-7890");
		// libraryRepository.save(lib);
		//
		// Library l = libraryRepository.findById(1L).get();
		// l.getBooklist().put(bookService.getBook(book1.getIsbn()), 0);
		// l.getBooklist().put(bookService.getBook(book2.getIsbn()), 3);
		//
		// libraryRepository.save(l);
		//
		//
		// User u = new User();
		// u.setAddress(addressRepository.findAll().get(0));
		// u.setEmail("user@email.com");
		// u.setFullname("Utente Prova");
		// u.setPassword("password");
		// u.setUsername("username");

		// userRepository.save(u);
		//
		// MembershipCard c = new MembershipCard();
		// c.setUser(userRepository.findAll().get(0));
		// c.setBlacklist(false);
		// c.setId("Carta1");
		// c.setLibrary(libraryRepository.findById(l.getId()).get());
		//
		// membershipCardRepository.save(c);

		//
		// Booking b = new Booking();
		// b.getBooks().add(bookRepository.findById("0000000000012").get());
		// b.setCard(membershipCardRepository.findAll().get(0));
		// b.setState(State.PENDING);
		//
		// bookingRepository.save(b);
		// System.out.println(bookingRepository.findAll().get(0));
		// l.getBooking().add(bookingRepository.findAll().get(0));
		// System.out.println(l);
		// System.out.println(bookingRepository.findById(1l).get());
		// System.out.println(bookingRepository.findBookingsByLibraryId(1l));
		// libraryRepository.save(l);
		//
//		Library l = libraryRepository.findById(1L).get();
		// libService.addLibraryBooks(1l, "/src/main/resources/Top_100_Libri.csv");
		fileDataService.init();

//		BookDto b = new BookDto("9788800500876", "La vera storia di Emanuele Syrbe", "Emanuele Syrbe", "Publisher", LocalDate.of(2023,10,22),Category.AUTOBIOGRAPHY, Languages.ITALIAN);
//		libService.addLibraryBook(1l, b, 30);

		// aggiornamento data scadenza
		databaseUpdateService.updateBookingState();

	}

}
