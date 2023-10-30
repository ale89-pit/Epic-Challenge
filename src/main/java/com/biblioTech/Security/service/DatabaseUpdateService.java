package com.biblioTech.Security.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.biblioTech.Enum.State;
import com.biblioTech.Security.repository.BookingRepository;

@Service
public class DatabaseUpdateService {

	@Autowired
	BookingRepository bookingRepository;

	// metodo per aggiornare lo stato di tutte le prenotazioni 'Booking' se la data
	// di scadenza è stata superata
	@Scheduled(cron = "0 0 0 * * *") // Esegue ogni giorno a mezzanotte
	public void updateBookingState() {
		// filtra solo quelli con StartDate presente, EndDate < oggi , state != EXPIRED
		// per questi cambia lo state in EXPIRED e salva la booking
		bookingRepository
				.findAll().stream().filter(b -> b.getStartDate() != null
						&& b.getEndDate().compareTo(LocalDate.now()) < 0 && !b.getState().equals(State.EXPIRED))
				.forEach(b -> {
					b.setState(State.EXPIRED);
					bookingRepository.save(b);
					System.out.println("Booking with id: " + b.getId() + " expired");
				});

	}

}
