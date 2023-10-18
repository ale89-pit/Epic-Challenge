package com.biblioTech.Security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.biblioTech.Security.entity.MembershipCard;
import com.biblioTech.Security.exception.MyAPIException;
import com.biblioTech.Security.repository.MembershipCardRepository;

import jakarta.persistence.EntityExistsException;


	@Service
	public class MembershipCardService {

		@Autowired MembershipCardRepository  membershipCardRepository;
		
		public MembershipCard saveMembershipCard(MembershipCard c) {
			return membershipCardRepository.save(c);
		}
		
	    public MembershipCard updateMembershipCard(String id,MembershipCard c) {
	        if(!membershipCardRepository.existsById(id)){
	          throw new EntityExistsException("This card does not exists");
	        }
	        MembershipCard book = membershipCardRepository.findById(id).get();
	        return membershipCardRepository.save(book);
	    }
	      public MembershipCard getMembershipCard(String id) {
	          return membershipCardRepository.findById(id).get();
	      }
	      
	      public List<MembershipCard> getAllMembershipCards(long id) {
	          return membershipCardRepository.findAll();
	      }
	      
	      
	      public String deleteMembershipCard(String id) {
	  		if (!membershipCardRepository.existsById(id)) {
	  			throw new MyAPIException(HttpStatus.NOT_FOUND, "This card does not exits");
	  		}
	  		membershipCardRepository.deleteById(id);
	  		
	  		return "This card has been deleted";
	  	}
	}


