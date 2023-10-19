package com.biblioTech.Security.payload;

import com.biblioTech.Security.entity.Address;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LibraryDto {

	
	
	
	@Column(nullable = false)
	private String name;
	

	private AddressDto address;
	
	@Column (unique=true, nullable = false)
	private String email;
	
	@Column(nullable = false)
	@JsonIgnore
	private String password;
	
	@Column(nullable = false)
	private String phone;
}
