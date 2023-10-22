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
//	
//	@OneToMany(fetch = FetchType.EAGER)
//	@JoinTable(name = "library_booking",
//  joinColumns = @JoinColumn(name = "library_id", referencedColumnName = "id"),
//  inverseJoinColumns = @JoinColumn(name = "booking_id", referencedColumnName = "id")
//)
//	private List<Booking> booking = new ArrayList<Booking>();

//	@OneToMany(fetch = FetchType.EAGER)
//    private List<MembershipCard> membershipCards = new ArrayList<MembershipCard>();

}
