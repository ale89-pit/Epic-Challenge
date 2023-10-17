package com.biblioTech.Security.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Entity
@Table(name = "address")
@NoArgsConstructor
@AllArgsConstructor
@Data

public class Address {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JoinColumn(name = "municipality")
	@ManyToOne(fetch =FetchType.EAGER)
	private Municipality municipality;
	
	@Column(nullable = false)
	public String street;
	
	@Column(nullable = false)
	public String number;
	
	public String km;
	
	public String description;
	
	@Column(nullable = false)
	public String lat;
	
	@Column(nullable = false)
	public String lon;
	
}
