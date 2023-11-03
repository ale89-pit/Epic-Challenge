package com.biblioTech.Security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.biblioTech.Security.entity.Province;
import java.util.List;


@Repository
public interface ProvinceRepository  extends JpaRepository<Province, String>{

	public List<Province> findAll();
	
	public Province findByName(String name);
	
	public List<Province> findByRegion(String region);
}
