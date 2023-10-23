package com.biblioTech.Security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.biblioTech.Security.entity.Book;
import com.biblioTech.Security.exception.MyAPIException;
import com.biblioTech.Security.payload.BookDto;
import com.biblioTech.Security.repository.BookRepository;

import jakarta.persistence.EntityExistsException;

@Service
public class BookService {

	@Autowired
	BookRepository bookRepository;

	public Book saveBook(Book b) {
		if (!bookRepository.existsById(b.getIsbn())) {
			return bookRepository.save(b);
		}

		return bookRepository.findById(b.getIsbn()).get();

	}

	public Book updateBook(String id, BookDto b) {
		if (!bookRepository.existsById(id)) {
			throw new EntityExistsException("Book with id " + id + " does not exists");
		}
		Book book = bookRepository.findById(id).get();

		if (b.getTitle() != null)
			book.setTitle(b.getTitle());

		if (b.getAuthor() != null)
			book.setAuthor(b.getAuthor());

		if (b.getPublisher() != null)
			book.setPublisher(b.getPublisher());

		if (b.getPublishedYear() != null)
			book.setPublishedYear(b.getPublishedYear());

		if (b.getCategory() != null)
			book.setCategory(b.getCategory());

		if (b.getLanguage() != null)
			book.setLanguage(b.getLanguage());

		return bookRepository.save(book);
	}

	public Book getBook(String id) {
		return bookRepository.findById(id).get();
	}

	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	public String deleteBook(String id) {
		if (!bookRepository.existsById(id)) {
			throw new MyAPIException(HttpStatus.NOT_FOUND, "Book with id " + id + " does not exits");
		}
		bookRepository.deleteById(id);

		return "This book has been deleted";
	}
}
