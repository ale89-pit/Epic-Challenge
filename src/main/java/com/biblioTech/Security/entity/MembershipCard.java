package com.biblioTech.Security.entity;

import java.time.LocalDate;

import com.biblioTech.Enum.MembershipCardState;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "membership_card")
@NoArgsConstructor
@Getter
@ToString
public class MembershipCard {

	@Id
	private String id;

    @JsonIgnoreProperties({"address", "email", "password", "phone", "roles", "booklist"})
	@ManyToOne(fetch = FetchType.EAGER)
	private Library library;

	@ManyToOne(fetch = FetchType.EAGER)
	private User user;

	@Column(nullable = false)
	private Boolean blacklist ;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private MembershipCardState state;
	
	@Column(name = "insert_date")
	private LocalDate insertDate;

	@Column(name = "start_date")
	private LocalDate startDate;

	@Column(name = "end_date")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate endDate;
	
	public MembershipCard(Library library, User user, Boolean blacklist, MembershipCardState state, LocalDate startDate,LocalDate endDate) {
		this.library = library;
		this.user = user;
		this.blacklist = false;
		this.state = state;
		this.insertDate=  LocalDate.now();
		this.startDate =startDate;
		this.endDate= endDate;
		setId();
	}

	//TODO modificare i vaolori di creazione della tessera per renderla leggibile,ricercabile e veritiera(idLibreria-Idutente-dataInserimento)
	public void setId() {
		this.id = library.getName() + "." + user.getFullname() + "." + insertDate.toString();
	}

	public void setLibrary(Library library) {
		this.library = library;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setBlacklist(Boolean blacklist) {
		this.blacklist = blacklist;
	}

	public void setState(MembershipCardState state) {
		this.state = state;
	}

	public void setStartDate(LocalDate date) {
		this.startDate = date;
	}
	public void setEndDate(LocalDate date) {
		this.endDate = date;
	}
	
	

}
