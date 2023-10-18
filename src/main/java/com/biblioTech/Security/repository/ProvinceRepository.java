package com.biblioTech.Security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.biblioTech.Security.entity.Province;

@Repository
public interface ProvinceRepository  extends JpaRepository<Province, String>{

	public Province findByName(String name);
}
