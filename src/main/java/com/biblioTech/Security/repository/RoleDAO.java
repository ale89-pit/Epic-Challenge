package com.biblioTech.Security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioTech.Security.enumeration.ERole;
import com.biblioTech.Security.entity.Role;



public interface RoleDAO extends JpaRepository<Role, Long> {

	Optional<Role> findByRoleName(ERole roleName);
}
