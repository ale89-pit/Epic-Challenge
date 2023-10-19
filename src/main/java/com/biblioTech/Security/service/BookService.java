package com.biblioTech.Security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.biblioTech.Security.entity.Book;
import com.biblioTech.Security.entity.Booking;
import com.biblioTech.Security.exception.MyAPIException;
import com.biblioTech.Security.repository.BookRepository;

import jakarta.persistence.EntityExistsException;

	@Service
	public class BookService {

		@Autowired BookRepository  bookRepository;
		
		public Book saveBook(Book b) {
			if(bookRepository.existsById(b.getIsbn())){
		          throw new EntityExistsException("This book alredy exists");
		        }
			else {
				return bookRepository.save(b);
			}
		   
			
		}
		
	    public Book updateBook(String id,Book b) {
	        if(!bookRepository.existsById(id)){
	          throw new EntityExistsException("This book does not exists");
	        }
	        Book book = bookRepository.findById(id).get();
	        return bookRepository.save(book);
	    }
	      public Book getBook(String string) {
	          return bookRepository.findById(string).get();
	      }
	      
	      public List<Book> getAllBooks() {
	          return bookRepository.findAll();
	      }
	      
	      
	      public String deleteBook(String id) {
	  		if (!bookRepository.existsById(id)) {
	  			throw new MyAPIException(HttpStatus.NOT_FOUND, "This book does not exits");
	  		}
	  		bookRepository.deleteById(id);
	  		
	  		return "This book has been deleted";
	  	}
	}

