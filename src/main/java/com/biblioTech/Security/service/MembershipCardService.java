package com.biblioTech.Security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.biblioTech.Enum.MembershipCardState;
import com.biblioTech.Security.entity.Library;
import com.biblioTech.Security.entity.MembershipCard;
import com.biblioTech.Security.entity.User;
import com.biblioTech.Security.exception.MyAPIException;
import com.biblioTech.Security.payload.MembershipCardDto;
import com.biblioTech.Security.repository.LibraryRepository;
import com.biblioTech.Security.repository.MembershipCardRepository;
import com.biblioTech.Security.repository.UserRepository;

import jakarta.persistence.EntityExistsException;

@Service
public class MembershipCardService {

	@Autowired
	MembershipCardRepository membershipCardRepository;
	@Autowired
	LibraryRepository libraryRepository;
	@Autowired
	UserRepository userRepository;

	public MembershipCard saveMembershipCard(MembershipCard c) {
		return membershipCardRepository.save(c);
	}

	public MembershipCard updateMembershipCard(String id, MembershipCardDto c) {
		if (!membershipCardRepository.existsById(id)) {
			throw new EntityExistsException("Card with id " + id + " does not exists");
		}
		MembershipCard card = membershipCardRepository.findById(id).get();

		if (c.getLibraryId() != null)
			if (libraryRepository.findById(c.getLibraryId()).isPresent())
				card.setLibrary(libraryRepository.findById(c.getLibraryId()).get());
			else
				throw new EntityExistsException("Library with id " + c.getLibraryId() + " does not exists");

		if (c.getState() != null)
			card.setState(c.getState());

		if (c.getUsername() != null)
			card.setUser(userRepository.findByUsername(c.getUsername()).get());

		if (c.getBlacklist() != null)
			card.setBlacklist(c.getBlacklist());

		return membershipCardRepository.save(card);
	}

	public MembershipCard getMembershipCard(String id) {
		return membershipCardRepository.findById(id).get();
	}

	public List<MembershipCard> getMembershipCardsByLibrary(Library l) {
		return membershipCardRepository.findByLibrary(l);
	}

	public List<MembershipCard> getMembershipCardsByLibraryAndState(Library l, MembershipCardState s) {
		return membershipCardRepository.findByLibraryAndState(l, s);
	}

	public List<MembershipCard> getAllMembershipCards() {
		return membershipCardRepository.findAll();
	}

	public Boolean existsByUserAndLibrary(User u, Library l) {
		return membershipCardRepository.existsByUserAndLibrary(u, l);
	}

	public String deleteMembershipCard(String id) {
		if (!membershipCardRepository.existsById(id)) {
			throw new MyAPIException(HttpStatus.NOT_FOUND, "This card does not exits");
		}
		membershipCardRepository.deleteById(id);

		return "This card has been deleted";
	}
}
