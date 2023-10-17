package com.biblioTech.Security.entity;


import java.util.List;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name= "membership_card")
@NoArgsConstructor
@AllArgsConstructor
@Data

public class MembershipCard {
	
	@Id
	private String id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Library library;
	
	@ManyToOne(fetch = FetchType.EAGER)	
	private User user;
	
	@Column(nullable = false)
	private Boolean blacklist;
	
//	private List<Booking> booking;
}
