package com.biblioTech.Security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.biblioTech.Security.entity.MembershipCard;

@Repository
public interface MembershipCardRepository extends JpaRepository<MembershipCard, String>{

}
