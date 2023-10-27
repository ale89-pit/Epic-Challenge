package com.biblioTech.Security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.biblioTech.Security.entity.Library;

@Repository
public interface LibraryRepository extends JpaRepository<Library, Long> {

	Optional<Library> findByEmail(String email);

	Boolean existsByEmail(String email);

}
