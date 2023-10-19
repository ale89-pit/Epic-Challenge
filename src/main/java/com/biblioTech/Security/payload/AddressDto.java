package com.biblioTech.Security.payload;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
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
public class AddressDto {
	
	  	@Column(nullable = false)
	    String street;
	    @Column(nullable = false)
	    String streetNumber;
	    @ManyToOne(fetch = FetchType.EAGER)
	    Long municipality;
	    

}
