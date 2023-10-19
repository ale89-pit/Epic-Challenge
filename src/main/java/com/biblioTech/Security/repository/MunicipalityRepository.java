package com.biblioTech.Security.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.biblioTech.Security.entity.Municipality;
import com.biblioTech.Security.entity.Province;

@Repository
public interface MunicipalityRepository extends JpaRepository<Municipality, Long>{

	List<Municipality> findByProvince(Province province);
	
	@Query("SELECT m FROM Municipality m JOIN m.province p WHERE p.name=:name")
	List<Municipality> findByProvinceName(@Param(value = "name") String provincename);
	
}
