package com.biblioTech.Security.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.biblioTech.Security.entity.MembershipCard;
import com.biblioTech.Security.payload.MembershipCardDto;
import com.biblioTech.Security.service.MembershipCardService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/card")
public class MembershipCardController {

	@Autowired
	MembershipCardService membershipCardService;

	@PostMapping("/new")
	@PreAuthorize("hasRole('USER')")
	public MembershipCard save(@RequestBody MembershipCardDto c) {
		return membershipCardService.saveMembershipCard(c);
	}

	@GetMapping("/allByUser/{user_id}")
	@PreAuthorize("hasRole('USER')")
	public List<MembershipCard> getAllMembershipCardsByUser(@PathVariable Long user_id) {
		return membershipCardService.getMembershipCardsByUserId(user_id);
	}

	@GetMapping("/allByLibrary/{library_id}")
	@PreAuthorize("hasRole('MODERATOR')")
	public List<MembershipCard> getAllMembershipCardsByLibrary(@PathVariable Long library_id) {
		return membershipCardService.getMembershipCardsByLibrary(library_id);
	}

	@PutMapping("/accept/{card_id}")
	@PreAuthorize("hasRole('MODERATOR')")
	public MembershipCard acceptCard(@PathVariable String card_id, @RequestParam("endDate") LocalDate endDate) {
		System.out.println(card_id + "data" + endDate);
		return membershipCardService.acceptCard(card_id, endDate);

	}

	@PutMapping("/reject/{card_id}")
	@PreAuthorize("hasRole('MODERATOR')")
	public MembershipCard rejectCard(@PathVariable String card_id) {

		return membershipCardService.rejectCard(card_id);

	}

	@PutMapping("/block/{card_id}")
	@PreAuthorize("hasRole('MODERATOR')")
	public MembershipCard blockCard(@PathVariable String card_id) {

		return membershipCardService.blockCard(card_id);

	}

	// quando la carta è scaduta o bloccata
	@PutMapping("/restore/{card_id}")
	@PreAuthorize("hasRole('MODERATOR')")
	public MembershipCard restoreCard(@PathVariable String card_id, @RequestParam("endDate") LocalDate endDate) {

		return membershipCardService.acceptCard(card_id, endDate);

	}

}
