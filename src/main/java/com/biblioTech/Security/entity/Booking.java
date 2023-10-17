package com.biblioTech.Security.entity;

import java.time.LocalDate;
import java.util.List;
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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
	
//	@Column(nullable = false)
//	private Set<Book> booklist;
	
	@Column(name = "start_date", nullable = false)
	private LocalDate startDate;
	
	@Column(name = "end_date", nullable = false)
	private LocalDate endDate;

	@Column(name = "restitution_date", nullable = false)
	private LocalDate restitutionDate;
	
	@Column(nullable = false)
	private boolean confirmed;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private State state;
}

