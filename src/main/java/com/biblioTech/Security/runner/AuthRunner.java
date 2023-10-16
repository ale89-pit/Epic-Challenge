package com.biblioTech.Security.runner;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.biblioTech.Security.entity.Role;
import com.biblioTech.Security.enumeration.ERole;
import com.biblioTech.Security.repository.RoleDAO;
import com.biblioTech.Security.repository.UtenteDAO;
import com.biblioTech.Security.service.AuthService;







@Component
public class AuthRunner implements ApplicationRunner {

	@Autowired
	RoleDAO roleRepository;
	@Autowired
	UtenteDAO userRepository;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	AuthService authService;


	private Set<Role> adminRole;
	private Set<Role> moderatorRole;
	private Set<Role> userRole;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.println("Run...");
		// Metodo da lanciare solo la prima volta
		// Serve per salvare i ruoli nel DB
//		Riempio le tabelle role e facilityService con Enum gia predisposti
//		per facilitare l'inserimento nella parte frontEnd
		
//		filaDataService.init();
		if(roleRepository.findAll().isEmpty()) {
			setRoleDefault();
		}
		



	}

	private void setRoleDefault() {
		Role admin = new Role();
		admin.setRoleName(ERole.ROLE_ADMIN);
		roleRepository.save(admin);

		Role user = new Role();
		user.setRoleName(ERole.ROLE_USER);
		roleRepository.save(user);

		// adminRole = new HashSet<Role>();
		// adminRole.add(admin);
		// adminRole.add(moderator);
		// adminRole.add(user);
		//
		// moderatorRole = new HashSet<Role>();
		// moderatorRole.add(moderator);
		// moderatorRole.add(user);
		//
		// userRole = new HashSet<Role>();
		// userRole.add(user);
	}
	
	

}
