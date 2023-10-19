package com.biblioTech.Security.runner;

import java.time.LocalDate;
import java.util.Date;

import org.hibernate.type.descriptor.java.LocalDateJavaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.biblioTech.Enum.Category;
import com.biblioTech.Enum.Languages;
import com.biblioTech.Security.entity.Address;
import com.biblioTech.Security.entity.Book;
import com.biblioTech.Security.entity.Library;
import com.biblioTech.Security.entity.Municipality;
import com.biblioTech.Security.entity.Province;
import com.biblioTech.Security.repository.AddressRepository;
import com.biblioTech.Security.repository.LibraryRepository;
import com.biblioTech.Security.repository.MunicipalityRepository;
import com.biblioTech.Security.repository.ProvinceRepository;
import com.biblioTech.Security.service.BookService;
import com.biblioTech.Security.service.LibraryService;

@Component
public class provaInserimento implements ApplicationRunner{
	
	@Autowired AddressRepository addressRepository;
	@Autowired LibraryRepository libraryRepository;
	@Autowired ProvinceRepository provinceRepository;
	@Autowired MunicipalityRepository municipalityRepository;
	@Autowired LibraryService libService;
	@Autowired BookService bookService;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		// TODO Auto-generated method stub
		
		
		// Address
		
//		Province p = provinceRepository.findByName("ROMA");
//		Municipality m = municipalityRepository.findByProvincename(p).get(0);
//		Address a = new Address();
//		a.setStreet("Via le dita dal naso");
//		a.setNumber("12");
//		a.setMunicipality(m);
//		a.setLon("0000012n");
//		a.setLat("0000003e");
//		a.setKm("123.000");
//		
//		addressRepository.save(a);
//		
		
		//Books
		Book book1 = new Book();
		book1.setIsbn("0000000000011");
		book1.setTitle("Titolo del Libro 1");
		book1.setAuthor("Autore del Libro 1");
		book1.setPublisher("Editore");
		book1.setCategory(Category.ADVENTURE);
		book1.setLanguage(Languages.ITALIAN);
		book1.setPublishedYear(LocalDate.of(2001, 1, 1));
		book1.setCol("lll");
		book1.setShelf("32");


		Book book2 = new Book();
		book2.setIsbn("0000000000012");
		book2.setTitle("Titolo del Libro 2");
		book2.setAuthor("Autore del Libro 2");
		book2.setPublisher("Editore");
		book2.setCategory(Category.ADVENTURE);
		book2.setLanguage(Languages.ITALIAN);
		book2.setPublishedYear(LocalDate.of(2001, 1, 1));
		book2.setCol("lll");
		book2.setShelf("32");

		
		bookService.saveBook(book1);
		bookService.saveBook(book2);
		
		
		// Library
		
//		Library lib = new Library();
//		lib.setName("Nome biblioteca");
//		lib.setAddress(addressRepository.findById(1L).get());
//		
//		lib.setEmail("email@example.com");
//		lib.setPassword("password");
//		lib.setPhone("123-456-7890");
//		libraryRepository.save(lib);
//		
		Library l = libraryRepository.findById(1L).get();
		l.getBooklist().put(bookService.getBook(book1.getIsbn()), 0);
		l.getBooklist().put(bookService.getBook(book2.getIsbn()), 3);
		
		libraryRepository.save(l);

		
		
		

		
		}

	}
