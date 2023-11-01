package com.biblioTech.Security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.biblioTech.Enum.Category;
import com.biblioTech.Security.entity.Book;
import com.biblioTech.Security.exception.ResourceNotFoundException;
import com.biblioTech.Security.payload.BookDto;
import com.biblioTech.Security.repository.BookRepository;

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
			throw new ResourceNotFoundException("Bookk","id", Long.parseLong(id) );
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

	public Page<Book> getAllBooks(Pageable pageable) {
		Page<Book> result = bookRepository.findAll(pageable);
		return result;
	}

	public Page<Book>getBookFromCategory(Category category,Pageable pageable){
		Page<Book> result = bookRepository.findByCategory(category,pageable);
		return result;
		
	}
	public String deleteBook(String id) {
		if (!bookRepository.existsById(id)) {
			throw new ResourceNotFoundException("Book","id", Long.parseLong(id) );
		}
		bookRepository.deleteById(id);

		return "This book has been deleted";
	}
}
