package com.biblioTech.Security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.biblioTech.Security.entity.Book;
import com.biblioTech.Security.exception.MyAPIException;
import com.biblioTech.Security.repository.BookRepository;

import jakarta.persistence.EntityExistsException;

	@Service
	public class BookService {

		@Autowired BookRepository  bookRepository;
		
		public Book saveBook(Book b) {
			return bookRepository.save(b);
		}
		
	    public Book updateBook(long id,Book b) {
	        if(!bookRepository.existsById(id)){
	          throw new EntityExistsException("This book does not exists");
	        }
	        Book book = bookRepository.findById(id).get();
	        return bookRepository.save(book);
	    }
	      public Book getBook(long id) {
	          return bookRepository.findById(id).get();
	      }
	      
	      public List<Book> getAllBooks(long id) {
	          return bookRepository.findAll();
	      }
	      
	      
	      public String deleteBook(Long id) {
	  		if (!bookRepository.existsById(id)) {
	  			throw new MyAPIException(HttpStatus.NOT_FOUND, "This book does not exits");
	  		}
	  		bookRepository.deleteById(id);
	  		
	  		return "This book has been deleted";
	  	}
	}

