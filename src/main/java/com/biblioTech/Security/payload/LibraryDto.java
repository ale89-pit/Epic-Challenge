package com.biblioTech.Security.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

	private String name;

	private AddressDto address;

	private String email;

	private String phone;

	@JsonIgnore
	private String password;

}
