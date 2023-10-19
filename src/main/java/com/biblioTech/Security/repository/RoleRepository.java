package com.biblioTech.Security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioTech.Enum.ERole;
import com.biblioTech.Security.entity.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    
	Optional<Role> findByRoleName(ERole roleName);

}