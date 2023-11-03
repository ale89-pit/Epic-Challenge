package com.biblioTech.Security.payload;

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
public class UserDto {

	private String fullname;
	private String username;
	private String email;
	private String phone;
	private AddressDto addressDto;
}
