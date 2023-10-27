package com.biblioTech.Security.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.biblioTech.Enum.Category;
import com.biblioTech.Enum.Languages;
import com.biblioTech.Security.entity.Address;
import com.biblioTech.Security.entity.Book;
import com.biblioTech.Security.entity.Library;
import com.biblioTech.Security.exception.MyAPIException;
import com.biblioTech.Security.payload.AddressDto;
import com.biblioTech.Security.payload.BookDto;
import com.biblioTech.Security.payload.LibraryDto;
import com.biblioTech.Security.repository.BookRepository;
import com.biblioTech.Security.repository.LibraryRepository;
import com.biblioTech.Security.repository.MunicipalityRepository;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class LibraryService {

	@Autowired
	LibraryRepository libraryRepository;
	@Autowired
	MunicipalityRepository municipalityRepository;

	@Autowired
	BookService bookService;
	@Autowired
	BookRepository bookRepository;
	@Autowired
	EntityManager entityManager;
	@Autowired
	AddressService addressService;

	public String addAddressToLibrary(long id, AddressDto a) {
		try {
			if (libraryRepository.existsById(id)) {
				Library lib = libraryRepository.findById(id).get();
				Address libraryAddress = addressService.saveAddress(a);
				lib.setAddress(libraryAddress);
				libraryRepository.save(lib);
				return "Address added succefully";
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw (e);
		}
		return "Library not present";

	}

	@Transactional
	public Library addLibraryBooks(long id, String filename) {

		BufferedReader brList;
		Library library = libraryRepository.findById(id).get();
		String folder = System.getProperty("user.dir");
		System.out.println(filename);
		try {
			brList = new BufferedReader(
					new FileReader(folder + "/src/main/resources/csvFiles/" + filename, StandardCharsets.UTF_8));
			String lineBook;
			boolean firstLine = true;
			while ((lineBook = brList.readLine()) != null) {
				if (firstLine) {
					// Skip the first line
					firstLine = false;
					continue;
				}
				String[] values = lineBook.split(";");

				Book b = new Book();
				b.setIsbn(values[0]);
				b.setTitle(values[1]);
				b.setAuthor(values[2]);
				b.setPublisher(values[3]);

				// Categoria libro

				if (values[4] != null) {
					try {
						// Se values[4] è una categoria valido fa il cast sennò entra nel catch
						Category c = Category.valueOf(values[4]);
						// System.out.println("Categoria: " + c);
						b.setCategory(c);
					} catch (IllegalArgumentException e) {
						// Se values[4] non è un enum valido setta "UNKNOWN"
						b.setCategory(Category.UNKNOWN);
						System.out.println("Categoria non valida: " + values[4].toString());
					}

				}

				// Data publicazione libro

				String date = values[5];
				Integer year = Integer.parseInt(date.split("/")[2]);
				Integer month = Integer.parseInt(date.split("/")[0]);
				Integer day = Integer.parseInt(date.split("/")[1]);
				// System.out.println("anno" + year);
				// System.out.println("mese" + month);
				// System.out.println("giorno" + day);
				b.setPublishedYear(LocalDate.of(year, month, day));

				if (values.length > 5) {
					try {
						// Se values[6] è una lingua valido fa il cast sennò entra nel catch
						Languages language = Languages.valueOf(values[6]);
						b.setLanguage(language);
					} catch (IllegalArgumentException e) {
						b.setLanguage(Languages.ITALIAN);
						System.out.println("Lingua non valida: " + values[6]);
					}

				}
				System.out.println("Libro aggiunto: " + b.getTitle() + " quantità: " + values[7]);
				bookService.saveBook(b);
				library.getBooklist().put(b, Integer.parseInt(values[7]));
				libraryRepository.save(library);
			}
			brList.close();

		} catch (IOException e) {
			e.printStackTrace();

		}
		System.out.println(library.getBooklist());
		return library;
	}

	public Library addLibraryBook(long id, BookDto book, Integer quantity) {

		if (!libraryRepository.existsById(id)) {
			throw new EntityNotFoundException("Library not found for ID: " + id);
		}

		Library library = libraryRepository.findById(id).get();

		Book b;

		if (bookRepository.existsById(book.getIsbn())) {
			b = bookRepository.findById(book.getIsbn()).get();

		} else {
			b = new Book();
			b.setIsbn(book.getIsbn());
			b.setTitle(book.getTitle());
			b.setAuthor(book.getAuthor());
			b.setPublisher(book.getPublisher());
			b.setPublishedYear(book.getPublishedYear());
			b.setCategory(book.getCategory());
			b.setLanguage(book.getLanguage());
			b = bookRepository.save(b);
		}

		library.getBooklist().put(b, quantity);

		return libraryRepository.save(library);
	}

	public Library addBook(Long idLib, Book book, Integer quantity) {
		if (!libraryRepository.existsById(idLib))
			throw new EntityExistsException("This library does not exists");
		Library library = libraryRepository.findById(idLib).get();
		library.addBook(book, quantity);
		return libraryRepository.save(library);
	}

	public Library setBookQuantity(Long idLib, Book book, Integer quantity) {
		if (!libraryRepository.existsById(idLib))
			throw new EntityExistsException("This library does not exists");
		Library library = libraryRepository.findById(idLib).get();
		library.setBookQuantity(book, quantity);
		return libraryRepository.save(library);
	}

	public Library removeBook(Long idLib, Book book) {
		if (!libraryRepository.existsById(idLib))
			throw new EntityExistsException("This library does not exists");
		Library library = libraryRepository.findById(idLib).get();
		library.removeBook(book);
		return libraryRepository.save(library);
	}

	public Integer getBookQuantity(Long idLib, Book book) {
		if (!libraryRepository.existsById(idLib))
			throw new EntityExistsException("This library does not exists");
		Library library = libraryRepository.findById(idLib).get();
		return library.getBookQuantity(book);
	}

	public Library increaseBookQuantity(Long idLib, Set<Book> books) {
		if (!libraryRepository.existsById(idLib))
			throw new EntityExistsException("This library does not exists");
		Library library = libraryRepository.findById(idLib).get();
		library.increaseBooksQuantity(books);
		return libraryRepository.save(library);
	}

	public Library decreaseBooksQuantity(Long idLib, Set<Book> books) {
		if (!libraryRepository.existsById(idLib))
			throw new EntityExistsException("This library does not exists");
		Library library = libraryRepository.findById(idLib).get();
		library.decreaseBooksQuantity(books);
		return libraryRepository.save(library);
	}

	public Library updateLibrary(long id, LibraryDto l) {
		if (!libraryRepository.existsById(id)) {
			throw new EntityExistsException("This library does not exists");
		}
		Library library = libraryRepository.findById(id).get();

		if (l.getName() != null)
			library.setName(l.getName());

		if (l.getEmail() != null)
			library.setEmail(l.getEmail());

		if (l.getAddress() != null)
			addressService.updateAddress(library.getAddress().getId(), l.getAddress());

		if (l.getPhone() != null)
			library.setPhone(l.getPhone());

		return libraryRepository.save(library);
	}

	public Library getLibraryById(long id) {
		return libraryRepository.findById(id).get();
	}

	public List<Library> getAllLibraries() {
		return libraryRepository.findAll();
	}

	public String deleteLibrary(Long id) {
		if (!libraryRepository.existsById(id)) {
			throw new MyAPIException(HttpStatus.NOT_FOUND, "This library does not exits");
		}
		libraryRepository.deleteById(id);

		return "This library has been deleted";
	}

}
