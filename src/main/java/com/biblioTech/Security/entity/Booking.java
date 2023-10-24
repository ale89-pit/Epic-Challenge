package com.biblioTech.Security.entity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.biblioTech.Enum.State;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "booking")
@NoArgsConstructor
@AllArgsConstructor
@Data

public class Booking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JoinColumn(nullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	private MembershipCard card;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "booking_book", joinColumns = @JoinColumn(name = "booking_id"), inverseJoinColumns = @JoinColumn(name = "book_isbn"))
	private Set<Book> books = new HashSet<Book>();

	@Column(name = "start_date")
	private LocalDate startDate;

	@Column(name = "end_date")
	private LocalDate endDate;

	@Column(name = "restitution_date")
	private LocalDate restitutionDate;

	@Column(nullable = false)
	private boolean confirmed;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private State state;

}
