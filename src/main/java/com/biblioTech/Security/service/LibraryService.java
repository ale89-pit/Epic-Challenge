package com.biblioTech.Security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.biblioTech.Security.entity.Library;
import com.biblioTech.Security.exception.MyAPIException;
import com.biblioTech.Security.repository.LibraryRepository;

import jakarta.persistence.EntityExistsException;


	@Service
	public class LibraryService {

		@Autowired LibraryRepository  libraryRepository;
		
		public Library saveLibrary(Library l) {
			return libraryRepository.save(l);
		}
		
	    public Library updateLibrary(long id,Library l) {
	        if(!libraryRepository.existsById(id)){
	          throw new EntityExistsException("This library does not exists");
	        }
	        Library library = libraryRepository.findById(id).get();
	        return libraryRepository.save(library);
	    }
	      public Library getLibrary(long id) {
	          return libraryRepository.findById(id).get();
	      }
	      
	      public List<Library> getAllLibrarys(long id) {
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


