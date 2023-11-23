package com.biblioTech.Security.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.biblioTech.Enum.ERole;
import com.biblioTech.Security.entity.ConfirmationToken;
import com.biblioTech.Security.entity.Library;
import com.biblioTech.Security.entity.Role;
import com.biblioTech.Security.entity.User;
import com.biblioTech.Security.exception.MyAPIException;
import com.biblioTech.Security.payload.LoginDto;
import com.biblioTech.Security.payload.RegisterDto;
import com.biblioTech.Security.repository.ConfirmationTokenRepository;
import com.biblioTech.Security.repository.LibraryRepository;
import com.biblioTech.Security.repository.RoleRepository;
import com.biblioTech.Security.repository.UserRepository;
import com.biblioTech.Security.security.JwtTokenProvider;

@Service
public class AuthServiceImpl implements AuthService {

	private AuthenticationManager authenticationManager;
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private PasswordEncoder passwordEncoder;
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private ConfirmationTokenRepository confirmationTokenRepository;
	@Autowired
	private EmailService emailService;
	@Autowired
	private LibraryRepository libraryRepository;

	public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository,
			RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
	public String login(LoginDto loginDto) {

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
		System.out.println(authentication);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtTokenProvider.generateToken(authentication);
		System.out.println(token);
		return token;
	}

	@Override
	public String register(RegisterDto registerDto) {

		// add check for username exists in database
		if (userRepository.existsByUsername(registerDto.getUsername())) {
			throw new MyAPIException(HttpStatus.BAD_REQUEST, "Username is already exists!.");
		}

		// add check for email exists in database
		if (userRepository.existsByEmail(registerDto.getEmail())) {
			throw new MyAPIException(HttpStatus.BAD_REQUEST, "Email is already exists!.");
		}
		if (libraryRepository.existsByEmail(registerDto.getEmail())) {
			throw new MyAPIException(HttpStatus.BAD_REQUEST, "Email is already exists!.");
		}

		User user = new User();
		user.setFullname(registerDto.getName());
		user.setUsername(registerDto.getUsername());
		user.setEmail(registerDto.getEmail());
		user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

		Set<Role> roles = new HashSet<>();

		if (registerDto.getRoles() != null) {
			registerDto.getRoles().forEach(role -> {
				Role userRole = roleRepository.findByRoleName(getRole(role)).get();
				roles.add(userRole);
			});
		} else {
			Role userRole = roleRepository.findByRoleName(ERole.ROLE_USER).get();
			roles.add(userRole);
		}

		user.setRoles(roles);
		System.out.println(user);
		userRepository.save(user);
		ConfirmationToken confirmationToken = new ConfirmationToken(user);

		confirmationTokenRepository.save(confirmationToken);
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(user.getEmail());
		mailMessage.setSubject("Complete Registration! This Email is valid to 24 Hours");
		mailMessage.setText("To confirm your account, please click here : "
				+ "http://localhost:8080/api/auth/confirm-account?token=" + confirmationToken.getConfirmationToken());
		emailService.sendEmail(mailMessage);

		System.out.println("Confirmation Token: " + confirmationToken.getConfirmationToken());

		return "Check your email to confirm.";
	}

	@Override
	public String registerLibrary(RegisterDto registerDto) {

		// add check for email exists in database
		if (libraryRepository.existsByEmail(registerDto.getEmail())) {
			throw new MyAPIException(HttpStatus.BAD_REQUEST, "Email is already exists!.");
		}
		if (userRepository.existsByEmail(registerDto.getEmail())) {
			throw new MyAPIException(HttpStatus.BAD_REQUEST, "Email is already exists!.");
		}

		Library library = new Library();
		library.setName(registerDto.getName());
		library.setEmail(registerDto.getEmail());
		library.setPassword(passwordEncoder.encode(registerDto.getPassword()));

		Set<Role> roles = new HashSet<>();

		if (registerDto.getRoles() != null) {
			registerDto.getRoles().forEach(role -> {
				Role userRole = roleRepository.findByRoleName(getRole(role)).get();
				roles.add(userRole);
			});
		} else {
			Role userRole = roleRepository.findByRoleName(ERole.ROLE_MODERATOR).get();
			roles.add(userRole);
		}

		library.setRoles(roles);
		System.out.println(library);
		libraryRepository.save(library);
		ConfirmationToken confirmationToken = new ConfirmationToken(library);

		confirmationTokenRepository.save(confirmationToken);
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(library.getEmail());
		mailMessage.setSubject("Complete Registration! This Email is valid to 24 Hours");
		mailMessage.setText("To confirm your account, please click here : "
				+ "http://localhost:8080/api/auth/confirm-library-account?token="
				+ confirmationToken.getConfirmationToken());
		emailService.sendEmail(mailMessage);

		System.out.println("Confirmation Token: " + confirmationToken.getConfirmationToken());

		return "Check your email to confirm.";
	}

	@Override
	public ResponseEntity<?> confirmEmail(String confirmationToken) {
		ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
		if (token != null) {
			Date dataAttuale = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(token.getCreatedDate());
			cal.add(Calendar.HOUR, 24); // Aggiungi 1 minuto alla data di creazione
			System.out.println(cal);
			if (cal.getTime().after(dataAttuale)) {
				Optional<User> user = userRepository.findByEmail(token.getUser().getEmail());

				if (user.isPresent()) {
					User userPresent = user.get();
					userPresent.setIsActive(true);
					userRepository.save(userPresent);
//					confirmationTokenRepository.delete(token);
					return ResponseEntity.ok("Email verified successfully!");

				}
			}
		}

		return ResponseEntity.badRequest().body("Error: Couldn't verify email");
	}

	@Override
	public ResponseEntity<?> confirmEmailByLibrary(String confirmationToken) {
		ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
		if (token != null) {
			Date dataAttuale = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(token.getCreatedDate());
			cal.add(Calendar.HOUR, 24); // Aggiungi 1 minuto alla data di creazione
			System.out.println(cal);
			if (cal.getTime().after(dataAttuale)) {

				Optional<Library> library = libraryRepository.findByEmail(token.getLibrary().getEmail());
				if (library.isPresent()) {
					Library libraryPresent = library.get();
					libraryPresent.setIsActive(true);

					libraryRepository.save(libraryPresent);
					confirmationTokenRepository.delete(token);
					return ResponseEntity.ok("Email verified successfully!");

				}
			}
		}

		return ResponseEntity.badRequest().body("Error: Couldn't verify email");
	}

	public ERole getRole(String role) {
		if (role.equals("ADMIN"))
			return ERole.ROLE_ADMIN;
		else if (role.equals("MODERATOR"))
			return ERole.ROLE_MODERATOR;
		else
			return ERole.ROLE_USER;
	}

	@Override
	public Library registerLibraryByRunner(RegisterDto registerDto) {
		if (libraryRepository.existsByEmail(registerDto.getEmail())) {
			throw new MyAPIException(HttpStatus.BAD_REQUEST, "Email is already exists!.");
		}
		if (userRepository.existsByEmail(registerDto.getEmail())) {
			throw new MyAPIException(HttpStatus.BAD_REQUEST, "Email is already exists!.");
		}

		Library library = new Library();
		library.setName(registerDto.getName());
		library.setEmail(registerDto.getEmail());
		library.setPassword(passwordEncoder.encode(registerDto.getPassword()));

		Set<Role> roles = new HashSet<>();

		if (registerDto.getRoles() != null) {
			registerDto.getRoles().forEach(role -> {
				Role userRole = roleRepository.findByRoleName(getRole(role)).get();
				roles.add(userRole);
			});
		} else {
			Role userRole = roleRepository.findByRoleName(ERole.ROLE_MODERATOR).get();
			roles.add(userRole);
		}

		library.setRoles(roles);
		System.out.println(library);
		return libraryRepository.save(library);
	}

}
