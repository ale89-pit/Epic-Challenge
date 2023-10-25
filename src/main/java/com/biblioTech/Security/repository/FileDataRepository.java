package com.biblioTech.Security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioTech.Security.entity.FileData;

public interface FileDataRepository extends JpaRepository<FileData, Long> {

	
	public FileData findByNome(String nome);
}
