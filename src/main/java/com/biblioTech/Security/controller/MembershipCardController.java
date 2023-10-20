package com.biblioTech.Security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.biblioTech.Security.entity.MembershipCard;
import com.biblioTech.Security.service.MembershipCardService;

@Controller
public class MembershipCardController {

	@Autowired
	MembershipCardService membershipCardService;

	public MembershipCard save(MembershipCard c) {
		return membershipCardService.saveMembershipCard(c);
	}

}
