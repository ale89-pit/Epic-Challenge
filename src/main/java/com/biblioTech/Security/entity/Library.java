package com.biblioTech.Security.entity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.MapKeyJoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "library")
@NoArgsConstructor
@AllArgsConstructor
@Data

public class Library {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Address address;

	@Column(unique = true, nullable = false)
	private String email;

	@Column(nullable = false)
	@JsonIgnore
	private String password;

//	@Column(nullable = false)
	private String phone;

	private Boolean isActive = false;
//	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinTable(name = "library_roles", joinColumns = @JoinColumn(name = "library_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private Set<Role> roles = new HashSet<>();

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "library_books", joinColumns = @JoinColumn(name = "library_id"))
	@MapKeyJoinColumn(name = "isbn")
	@Column(name = "quantity")
	private Map<Book, Integer> booklist = new HashMap<Book, Integer>();

	// return the previous quantity null if not exist
	public Integer addBook(Book book, Integer quantity) {
		return this.booklist.put(book, quantity);
	}

	// return the previous quantity null if not exist
	public Integer removeBook(Book book) {
		return this.booklist.remove(book);
	}

	// return the actual quantity null if not exist
	public Integer getBookQuantity(Book book) {
		return this.booklist.get(book);
	}

	// return the previous quantity null if not exist
	public void setBookQuantity(Book book, Integer quantity) {
		this.booklist.replace(book, quantity);
	}

	// il metodo non cambia le quantità perchè non agisce sul Map della classe
//	public void increaseBooksQuantity(Set<Book> books) {
//		for (Book book : books) {
//			setBookQuantity(book, getBookQuantity(book) + 1);
//		}
//	}
	
	
	public void increaseBooksQuantity(Set<Book> books) {
		for (Book book : books) {
			for (Map.Entry<Book, Integer> entry : booklist.entrySet()) {
				Book singlebook = entry.getKey();
				Integer quantity = entry.getValue();
				if(book.getIsbn().equals(singlebook.getIsbn())) {
					
						entry.setValue(quantity + 1);
				}
			}
			
		}
	}

	public void decreaseBooksQuantity(Set<Book> books) {
		for (Book book : books) {
			for (Map.Entry<Book, Integer> entry : booklist.entrySet()) {
				Book singlebook = entry.getKey();
				Integer quantity = entry.getValue();
				if(book.getIsbn().equals(singlebook.getIsbn())) {
					if (quantity > 0)
						entry.setValue(quantity - 1);
				}
			}
			
		}
	}

}
