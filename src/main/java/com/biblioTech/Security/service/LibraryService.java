package com.biblioTech.Security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.biblioTech.Security.entity.Address;
import com.biblioTech.Security.entity.Library;
import com.biblioTech.Security.entity.Municipality;
import com.biblioTech.Security.exception.MyAPIException;
import com.biblioTech.Security.payload.LibraryDto;
import com.biblioTech.Security.repository.LibraryRepository;
import com.biblioTech.Security.repository.MunicipalityRepository;

import jakarta.persistence.EntityExistsException;


	@Service
	public class LibraryService {

		@Autowired LibraryRepository  libraryRepository;
		@Autowired MunicipalityRepository municipalityRepository;
		
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


