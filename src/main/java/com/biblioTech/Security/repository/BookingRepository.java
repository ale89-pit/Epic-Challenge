package com.biblioTech.Security.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.biblioTech.Security.entity.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long>{

	@Query("SELECT b FROM Booking b WHERE b.card.library.id = :libraryId")
    List<Booking> findBookingsByLibraryId(@Param("libraryId") Long libraryId);
	
	@Query("SELECT b FROM Booking b WHERE b.endDate > NOW()")
	List<Booking> findBookingExpired();
}
