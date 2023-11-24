package com.biblioTech.Security.runner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.biblioTech.Enum.Category;
import com.biblioTech.Enum.ERole;
import com.biblioTech.Enum.Languages;
import com.biblioTech.Security.entity.Address;
import com.biblioTech.Security.entity.Book;
import com.biblioTech.Security.entity.Library;
import com.biblioTech.Security.entity.Municipality;
import com.biblioTech.Security.entity.Province;
import com.biblioTech.Security.entity.Role;
import com.biblioTech.Security.payload.RegisterDto;
import com.biblioTech.Security.repository.AddressRepository;
import com.biblioTech.Security.repository.BookRepository;
import com.biblioTech.Security.repository.BookingRepository;
import com.biblioTech.Security.repository.LibraryRepository;
import com.biblioTech.Security.repository.MembershipCardRepository;
import com.biblioTech.Security.repository.MunicipalityRepository;
import com.biblioTech.Security.repository.ProvinceRepository;
import com.biblioTech.Security.repository.RoleRepository;
import com.biblioTech.Security.repository.UserRepository;
import com.biblioTech.Security.service.AuthService;
import com.biblioTech.Security.service.BookService;
import com.biblioTech.Security.service.DatabaseUpdateService;
import com.biblioTech.Security.service.FileDataService;
import com.biblioTech.Security.service.LibraryService;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

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
	@Autowired RoleRepository roleRepository;
	@Autowired
	DatabaseUpdateService databaseUpdateService;

	@Autowired AuthService authService;
	
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
//		addLibraryFromFile("territorio.csv");
//		addLibraryFromFile("territorioFiltrato3.csv");
	}
	
	
	public String addLibraryFromFile(String filename) {
		
		BufferedReader brList;
		String folder = System.getProperty("user.dir");
		System.out.println(filename);
		try {
			brList = new BufferedReader(
					new FileReader(folder + "/src/main/resources/csvFiles/" + filename, StandardCharsets.UTF_8));
			String lineBook;
			System.out.println(brList);
			int line = 0;
			boolean firstLine = true;
			while ((lineBook = brList.readLine()) != null) {
				if (firstLine) {
					// Skip the first line
					firstLine = false;
					line++;
					continue;
				}
				line++;
				if(line>1) {
					
				
				String[] values = lineBook.split(";");
				System.out.println(values.length);
				System.out.println(values[0]);
				System.out.println(values[1]);
				System.out.println(values[2]);
				System.out.println(values[3]);
				System.out.println(values[4]);
				System.out.println(values[5]);
				System.out.println(values[6]);
				System.out.println(values[7]);
				System.out.println(values[8]);
				System.out.println(values[9]);
				System.out.println(values[10]);
				if(libraryRepository.existsByEmail(values[10].replaceAll("\"",""))) {
					line++;
					continue;
				}
				System.out.println(values.length);
				for(String s :values) {
					System.out.println(s + "**********");
				}
				
				
				
				
				RegisterDto register = new RegisterDto();
				
				
				if(!values[10].isBlank() ) {
					
						register.setEmail(values[10].replaceAll("\"",""));
						
				}else {
					register.setEmail("email@prova" + line +".it");
				}
				
				register.setName(values[0].replaceAll("\"",""));
				register.setPassword("qwert");
				
				Library l = authService.registerLibraryByRunner(register);
				
				if(l!= null) {
					l.setIsActive(true);
					l.setPhone(values[9].replaceAll("\"",""));
					
					Address a = new Address();
					String[] parti = values[1].split("(?<=\\D)(?=\\d)");
					System.out.println(parti+ "************");
//				for(String s :parti) {
//					System.out.println(s + "**********");
//				}
					if(parti.length > 1) {
						
						a.setStreet(parti[0].replaceAll("\"",""));
						a.setNumber(parti[1].replaceAll("\"",""));
					}else {
						a.setStreet(values[1].replaceAll("\"",""));
						a.setNumber("snc");
						
					}
					a.setLat(values[7].replaceAll("\"",""));
					a.setLon(values[8].replaceAll("\"",""));
					Province p = provinceRepository.findByName(values[4].replaceAll("\"",""));
					Municipality m = municipalityRepository.findByProvince(p).stream().filter( m1 -> m1.getName().toUpperCase().equals(values[2]. replaceAll("\"","").toUpperCase())).findFirst().orElse(null);
					a.setMunicipality(m);
					Address save = addressRepository.saveAndFlush(a);
					l.setAddress(save);
					libraryRepository.save(l);
				}
			
				// Data publicazione libro
//				Address salvato = addressRepository.findByLatAndLonAndStreet(values[11].replaceAll("\"",""),values[12].replaceAll("\"",""),a.getStreet());
			
				

				line++;
				
			}
			}
			brList.close();

		} catch (IOException e) {
			e.printStackTrace();

		}
		
		return "file letto";
	}

}
