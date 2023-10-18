package com.biblioTech.Security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.biblioTech.Security.entity.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long>{

}
