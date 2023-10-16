package com.biblioTech.Security.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.biblioTech.Security.entity.User;



@Repository
public interface UtenteDAO extends JpaRepository<User, Long> {

	public boolean existsById(Long id);

	Optional<User> findByEmail(String email);

	Optional<User> findByUserNameOrEmail(String username, String email);

	Optional<User> findByUserName(String username);

	Boolean existsByUserName(String username);

	Boolean existsByEmail(String email);

	// SELECT * FROM postazioni p WHERE p.tipo = 'OPENSPACE' AND p.edificio_id in
	// (SELECT id from edifici where citta = 'Quasimodo lido')
	// @Query("SELECT p FROM Postazione p WHERE p.tipo = :tipo AND edificio
	// in(SELECT id FROM Edificio e WHERE e.citta = :citta)")
	// public List<Postazione> findPostazioneCitta(TipoPostazione tipo,String
	// citta);
}
