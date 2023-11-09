package com.biblioTech.Security.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioTech.Enum.MembershipCardState;
import com.biblioTech.Security.entity.Library;
import com.biblioTech.Security.entity.MembershipCard;
import com.biblioTech.Security.entity.User;
import com.biblioTech.Security.exception.ResourceNotFoundException;
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

	public MembershipCard saveMembershipCard(MembershipCardDto c) {

		if (userRepository.existsByUsername(c.getUsername())) {
			User u = userRepository.findByUsername(c.getUsername()).get();
			if (!u.getIsActive())
				throw new EntityExistsException(u.getUsername() + " not active");
			if (libraryRepository.existsById(c.getLibraryId())) {
				Library l = libraryRepository.findById(c.getLibraryId()).get();
				if (!membershipCardRepository.existsByUserAndLibrary(u, l)) {
					MembershipCard card = new MembershipCard(l, u, false, MembershipCardState.WAITING_FOR_APPROVAL,
							null, null);

					return membershipCardRepository.save(card);

				}

				throw new EntityExistsException(u.getUsername() + " alredy has a card on library " + l.getName());
			}
			throw new ResourceNotFoundException("Library", "id", c.getLibraryId());
		}
		throw new ResourceNotFoundException("User", "username", c.getUsername());
	}

	public MembershipCard updateMembershipCard(String id, MembershipCardDto c) {
		if (!membershipCardRepository.existsById(id)) {
			throw new ResourceNotFoundException("Card", "id", Long.parseLong(id));
		}
		MembershipCard card = membershipCardRepository.findById(id).get();

		if (c.getLibraryId() != null)
			if (libraryRepository.findById(c.getLibraryId()).isPresent())
				card.setLibrary(libraryRepository.findById(c.getLibraryId()).get());
			else
				throw new ResourceNotFoundException("Library", "id", c.getLibraryId());

		if (c.getState() != null)
			card.setState(c.getState());

		if (c.getUsername() != null)
			card.setUser(userRepository.findByUsername(c.getUsername()).get());

		if (c.getBlacklist() != null)
			card.setBlacklist(c.getBlacklist());

		return membershipCardRepository.save(card);
	}

	public MembershipCard acceptCard(String card_id, LocalDate endDate) {
		if (!membershipCardRepository.existsById(card_id)) {
			throw new ResourceNotFoundException("Card", "id", Long.parseLong(card_id));
		} else {
			MembershipCard m = membershipCardRepository.findById(card_id).get();
			m.setState(MembershipCardState.APPROVED);
			m.setStartDate(LocalDate.now());
			m.setEndDate(endDate);
			return membershipCardRepository.save(m);
		}

	}

	// rifiuta la carta perchè dobbiamo far decidere alla libreria se accettare o
	// meno la tessera
	public MembershipCard rejectCard(String card_id) {
		if (!membershipCardRepository.existsById(card_id)) {
			throw new ResourceNotFoundException("Card", "id", Long.parseLong(card_id));
		} else {
			MembershipCard m = membershipCardRepository.findById(card_id).get();
			m.setState(MembershipCardState.REJECTED);
			m.setEndDate(LocalDate.now());
			return membershipCardRepository.save(m);
		}

	}

	public MembershipCard blockCard(String card_id) {
		if (!membershipCardRepository.existsById(card_id)) {
			throw new ResourceNotFoundException("Card", "id", Long.parseLong(card_id));
		} else {

			MembershipCard m = membershipCardRepository.findById(card_id).get();
			if (m.getState().equals(MembershipCardState.APPROVED)) {
				m.setBlacklist(true);
				return membershipCardRepository.save(m);

			}
			// TODO cambiato da entityExistException a RuntimeException
			throw new RuntimeException("This card is not approved, you can't block card");
		}

	}

	public MembershipCard restoreCard(String card_id) {
		if (!membershipCardRepository.existsById(card_id)) {
			throw new ResourceNotFoundException("Card", "id", Long.parseLong(card_id));
		} else {
			MembershipCard m = membershipCardRepository.findById(card_id).get();
			m.setBlacklist(false);
			return membershipCardRepository.save(m);
		}

	}

	public MembershipCard renewalCard(String card_id, LocalDate endDate) {
		if (!membershipCardRepository.existsById(card_id)) {
			throw new ResourceNotFoundException("Card", "id", Long.parseLong(card_id));
		} else {
			MembershipCard m = membershipCardRepository.findById(card_id).get();
			m.setStartDate(LocalDate.now());
			m.setEndDate(endDate);
			return membershipCardRepository.save(m);
		}

	}

	public MembershipCard getMembershipCard(String id) {
		return membershipCardRepository.findById(id).get();
	}

	public List<MembershipCard> getMembershipCardsByLibrary(Long libId) {
		if (!libraryRepository.existsById(libId))
			throw new ResourceNotFoundException("Library", "id", libId);

		Library library = libraryRepository.findById(libId).get();
		return membershipCardRepository.findByLibrary(library);

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

	public List<MembershipCard> getMembershipCardsByUserId(Long userId) {
		if (!userRepository.existsById(userId))
			throw new ResourceNotFoundException("User", "id", userId);

		return membershipCardRepository.findByUser(userRepository.findById(userId).get());
	}

	public String deleteMembershipCard(String id) {
		if (!membershipCardRepository.existsById(id)) {
			throw new ResourceNotFoundException("Card", "id", Long.parseLong(id));
		}
		membershipCardRepository.deleteById(id);

		return "This card has been deleted";
	}
}
