package com.biblioTech.Security.entity;


import java.util.List;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
	
	@Column(nullable = false)
	private Library library;
	
	@Column(nullable = false)
	private User user;
	
	@Column(nullable = false)
	private Boolean blacklist;
	
	private List<Booking> booking;
}
