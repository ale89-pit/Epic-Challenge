package com.biblioTech.Security.entity;

import java.time.LocalDate;

import com.biblioTech.Enum.MembershipCardState;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "membership_card")
@NoArgsConstructor
@Getter
public class MembershipCard {

	@Id
	private String id;

	@ManyToOne(fetch = FetchType.EAGER)
	private Library library;

	@ManyToOne(fetch = FetchType.EAGER)
	private User user;

	@Column(nullable = false)
	private Boolean blacklist;

	@Column(nullable = false)
	private MembershipCardState state;

	@Column(nullable = false)
	private LocalDate date;

	public MembershipCard(Library library, User user, Boolean blacklist, MembershipCardState state, LocalDate date) {
		this.library = library;
		this.user = user;
		this.blacklist = blacklist;
		this.state = state;
		this.date = date;
		setId();
	}

	public void setId() {
		this.id = library + "." + user.getFullname() + "." + date.toString();
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

	public void setDate(LocalDate date) {
		this.date = date;
	}

}
