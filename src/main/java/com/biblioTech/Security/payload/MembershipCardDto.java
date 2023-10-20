package com.biblioTech.Security.payload;

import com.biblioTech.Enum.MembershipCardState;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MembershipCardDto {

	private Long libraryId;

	private String username;

	private Boolean blacklist;

	private MembershipCardState state;

//	@OneToMany(mappedBy = "card",fetch = FetchType.EAGER)
//	private List<Booking> bookings = new ArrayList<Booking>();

}
