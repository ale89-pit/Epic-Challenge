package com.biblioTech.Security.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.biblioTech.Enum.MembershipCardState;
import com.biblioTech.Security.entity.Library;
import com.biblioTech.Security.entity.MembershipCard;
import com.biblioTech.Security.entity.User;

@Repository
public interface MembershipCardRepository extends JpaRepository<MembershipCard, String> {

//	List<Municipality> findByProvince(Province province);
	List<MembershipCard> findByUser(User u);

	List<MembershipCard> findByLibrary(Library l);

	Boolean existsByUserAndLibrary(User u, Library l);

	List<MembershipCard> findByLibraryAndMembershipCardState(Library l, MembershipCardState s);

}
