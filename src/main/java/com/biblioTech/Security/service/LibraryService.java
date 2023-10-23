package com.biblioTech.Security.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.biblioTech.Enum.Category;
import com.biblioTech.Enum.Languages;
import com.biblioTech.Security.entity.Address;
import com.biblioTech.Security.entity.Book;
import com.biblioTech.Security.entity.Library;
import com.biblioTech.Security.entity.Municipality;
import com.biblioTech.Security.entity.Province;
import com.biblioTech.Security.exception.MyAPIException;
import com.biblioTech.Security.payload.LibraryDto;
import com.biblioTech.Security.repository.BookRepository;
import com.biblioTech.Security.repository.LibraryRepository;
import com.biblioTech.Security.repository.MunicipalityRepository;

import jakarta.persistence.EntityExistsException;


	@Service
	public class LibraryService {

		@Autowired LibraryRepository  libraryRepository;
		@Autowired MunicipalityRepository municipalityRepository;
		@Autowired BookRepository  bookRepository;
		
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
	
		public Library addLibraryBooks(long id,Library l, String filepath) {
			 String currentWorkingDir = System.getProperty("user.dir");
				     BufferedReader brList;
				     Library library = libraryRepository.findById(id).get();
					try {
						brList = new BufferedReader(
						 new
						 FileReader(currentWorkingDir + filepath,StandardCharsets.UTF_8));
						 String lineProvince;
						 boolean firstLine = true;
					     while ((lineProvince = brList.readLine()) != null) {
					    	  if (firstLine) {
					    	        // Skip the first line
					    	        firstLine = false;
					    	        continue;
					    	    }
					     String[] values = lineProvince.split(";");
				    
				     Book b = new Book();
					     b.setIsbn(values[0]);
					     b.setTitle(values[1]);
					     b.setAuthor(values[2]);
					     b.setPublisher(values[3]);
					     
					     //Categoria libro
					     
					     if (values[4]!= null) {
					    	 try {
					    		// Se values[4] è una categoria valido fa il cast sennò entra nel catch
					    		    Category c = Category.valueOf(values[4]);
					    		    //System.out.println("Categoria: " + c);
					    		    b.setCategory(c);
					    		} catch (IllegalArgumentException e) {
					    			// Se values[4] non è un enum valido setta "UNKNOWN"
					    			b.setCategory(Category.UNKNOWN);
					    		    System.out.println("Categoria non valida: " + values[4].toString());
					    		}
					    
					    	
					     }
					     
					     //Data publicazione libro
					     
					     String date = values[5];
					     Integer year = Integer.parseInt(date.split("/")[2]);
					     Integer month = Integer.parseInt(date.split("/")[0]);
					     Integer day = Integer.parseInt(date.split("/")[1]);
//					     System.out.println("anno" + year);
//					     System.out.println("mese" + month);
//					     System.out.println("giorno" + day);
					     b.setPublishedYear(LocalDate.of(year, month, day));
					     
					     if (values.length > 5){
					    	 try {
						    	// Se values[6] è una lingua valido fa il cast sennò entra nel catch
					    		 Languages language = Languages.valueOf(values[6]); 
					    		 b.setLanguage(language);
						    	} catch (IllegalArgumentException e) {
						    		 b.setLanguage(Languages.ITALIAN);
						    		System.out.println("Lingua non valida: " + values[6]);
						   		}
					    	 
					     }

					     System.out.println("Libro aggiunto: " + b.getTitle() + " quantità: " + values[7] );
					     bookRepository.save(b);
//			1 rappresenta la quantità, inserire la colonna nel foglio e riprovare con la giusta quantità
					   library.getBooklist().put(b, Integer.parseInt(values[7]));
					     
//					     return libraryRepository.save(library);
						}
					     brList.close();
					     
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					
					}
				    
					return libraryRepository.save(library);
		}
		
	    public Library updateLibrary(long id,Library l) {
	        if(!libraryRepository.existsById(id)){
	          throw new EntityExistsException("This library does not exists");
	        }
	        Library library = libraryRepository.findById(id).get();
	        return libraryRepository.save(library);
	    }

		public Library getLibraryById(long id) {
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


