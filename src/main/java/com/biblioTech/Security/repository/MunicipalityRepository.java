package com.biblioTech.Security.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.biblioTech.Security.entity.Municipality;
import com.biblioTech.Security.entity.Province;

@Repository
public interface MunicipalityRepository extends JpaRepository<Municipality, Long>{

	List<Municipality> findByProvincename(Province provincename);
}
