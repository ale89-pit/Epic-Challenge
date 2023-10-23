package com.biblioTech.Security.security;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.biblioTech.Security.entity.Library;
import com.biblioTech.Security.entity.User;
import com.biblioTech.Security.repository.LibraryRepository;
import com.biblioTech.Security.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private UserRepository userRepository;
	@Autowired
	LibraryRepository libraryRepository;

	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
		// Cerca un utente in base al nome utente o all'email
		Optional<User> userOptional = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);

		if (userOptional.isPresent()) {
			User user = userOptional.get();

			Set<GrantedAuthority> authorities = user.getRoles().stream()
					.map(role -> new SimpleGrantedAuthority(role.getRoleName().toString())).collect(Collectors.toSet());

			return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
					authorities);
		}

		// Se l'utente non è stato trovato, cerca una libreria
		Optional<Library> libraryOptional = libraryRepository.findByEmail(usernameOrEmail);

		if (libraryOptional.isPresent()) {
			Library library = libraryOptional.get();

			Set<GrantedAuthority> authorities = library.getRoles().stream()
					.map(role -> new SimpleGrantedAuthority(role.getRoleName().toString())).collect(Collectors.toSet());

			return new org.springframework.security.core.userdetails.User(library.getEmail(), library.getPassword(),
					authorities);
		}

		throw new UsernameNotFoundException("User or library not found with username or email: " + usernameOrEmail);
	}

}
