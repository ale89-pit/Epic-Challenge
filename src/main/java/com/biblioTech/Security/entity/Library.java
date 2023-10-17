package com.biblioTech.Security.entity;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name= "library")
@NoArgsConstructor
@AllArgsConstructor
@Data

public class Library {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
//	@PrimaryKeyJoinColumn
//	@OneToOne
//	private Address address;
	
	@Column (unique=true, nullable = false)
	private String email;
	
	@Column(nullable = false)
	@JsonIgnore
	private String password;
	
	@Column(nullable = false)
	private String phone;
//	
//	@OneToOne
//	@PrimaryKeyJoinColumn
//	private Map<Book, Integer> booklist;
//	
//	@ManyToMany
//	private List<Book> booking;
	
//	@OneToMany(mappedBy = "id")
//    private List<MembershipCard> membershipCards;
	
}


