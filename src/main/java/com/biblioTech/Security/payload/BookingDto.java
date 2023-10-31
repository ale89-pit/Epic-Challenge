package com.biblioTech.Security.payload;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.biblioTech.Enum.State;
import com.biblioTech.Security.entity.Book;
import com.biblioTech.Security.entity.MembershipCard;

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
public class BookingDto {

	private String cardId;
	private Set<String> books = new HashSet<String>();
	private Set<Book> booksNotAvailable = new HashSet<Book>();
	private LocalDate startDate;
	private LocalDate endDate;
	private LocalDate restitutionDate;
	private State state;
}
