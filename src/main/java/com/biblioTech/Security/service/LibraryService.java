package com.biblioTech.Security.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.biblioTech.Enum.Category;
import com.biblioTech.Enum.Languages;
import com.biblioTech.Security.entity.Address;
import com.biblioTech.Security.entity.Book;
import com.biblioTech.Security.entity.Library;
import com.biblioTech.Security.entity.Municipality;
import com.biblioTech.Security.exception.MyAPIException;
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
	@Autowired BookRepository bookRepository;
	@Autowired EntityManager entityManager;
//	@Autowired LibraryBookRepositoryImpl libraryBookRepository;

	public Library saveLibrary(LibraryDto l) {
		Library lib = new Library();
		Address a = new Address();
		Municipality m = municipalityRepository.findById(l.getAddress().getMunicipality()).get();

		a.setMunicipality(m);
		a.setStreet(l.getAddress().getStreet());
		a.setNumber(l.getAddress().getStreetNumber());

		lib.setName(l.getName());
		lib.setEmail(l.getEmail());
		lib.setPassword(l.getPassword());
		lib.setPhone(l.getPhone());
		lib.setAddress(a);

		return libraryRepository.save(lib);
	}

	@Transactional
	public Library addLibraryBooks(long id, String filename) {

		BufferedReader brList;
		Library library = libraryRepository.findById(id).get();
		String folder = System.getProperty("user.dir");
		System.out.println(filename);
		try {
			brList = new BufferedReader(new FileReader(folder + "/src/main/resources/csvFiles/" + filename, StandardCharsets.UTF_8));
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
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
System.out.println(library.getBooklist());
		return updateLibrary(library.getId(), library);
	}
	
	
	 public Library addLibraryBook(long id, BookDto book, Integer quantity) {
	        Library library = libraryRepository.findById(id).orElse(null);
System.out.println(quantity);
System.out.println(quantity instanceof Integer);
	        if (library != null) {
	        	Book b;
	        	if (bookRepository.existsById(book.getIsbn())) {
	            b = bookRepository.findById(book.getIsbn()).get();
	        	}
	        	else {
	        		b = new Book();
	            b.setIsbn(book.getIsbn());
	            b.setTitle(book.getTitle());
                b.setAuthor(book.getAuthor());
                b.setPublisher(book.getPublisher());
                b.setPublishedYear(book.getPublishedYear());
                b.setCategory(book.getCategory());
                b.setLanguage(book.getLanguage());
                b= bookRepository.save(b);
	        	}
	           
	                library.getBooklist().put(b, quantity);
	            

	            return libraryRepository.save(library);
	        } else {
	            // Gestisci il caso in cui la libreria con l'ID specificato non esiste
	            throw new EntityNotFoundException("Library not found for ID: " + id);
	        }
	    }
	 
	public Library updateLibrary(long id, Library l) {
		if (!libraryRepository.existsById(id)) {
			throw new EntityExistsException("This library does not exists");
		}
		Library library = libraryRepository.findById(id).get();
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
