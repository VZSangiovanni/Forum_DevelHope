package co.develhope.forum.controllers;



import co.develhope.forum.dto.response.UserDTO;
import co.develhope.forum.model.User;
import co.develhope.forum.repositories.UserRepository;
import co.develhope.forum.services.CustomUserService;
import it.pasqualecavallo.studentsmaterial.authorization_framework.dao.UserDao;
import it.pasqualecavallo.studentsmaterial.authorization_framework.filter.AuthenticationContext;
import it.pasqualecavallo.studentsmaterial.authorization_framework.security.PublicEndpoint;
import it.pasqualecavallo.studentsmaterial.authorization_framework.security.RoleSecurity;
import it.pasqualecavallo.studentsmaterial.authorization_framework.security.ZeroSecurity;
import it.pasqualecavallo.studentsmaterial.authorization_framework.service.UserDetails;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

	@Autowired
	private CustomUserService customUserService;

	@GetMapping("/default-deny")
	public void defaultDenyEndpoint() {
		System.out.println("Default deny. This endpoint cannot be reached");
	}

	@PublicEndpoint
	@GetMapping("/public-endpoint")
	public void publicEndpoint() {
		System.out.println("This endpoint can be reached without authentication header");
	}

	@ZeroSecurity
	@GetMapping("/no-role-endpoint")
	public void noRoleEndpoint() {
		System.out.println("This endpoint can be reached with any role, but require authentication. Authenticated user is " + AuthenticationContext.get().getUsername());
	}

	@RoleSecurity(value = {"ROLE_USER", "ROLE_ADMIN"})
	@GetMapping("/role-evaluated-endpoint")
	public void roleCheckEndpoint() {
		System.out.println("This endpoint can be reached only by authenticated users with ROLE_USER or ROLE_ADMIN. Authenticated user is " + AuthenticationContext.get().getUsername());
	}

	@ZeroSecurity
	@GetMapping("/read-self")
	public User readSelf() {
		AuthenticationContext.Principal principal = AuthenticationContext.get();
		return customUserService.readUser(principal.getUsername());

	}

	@RoleSecurity(value = {"ROLE_FOUNDER, ROLE_ADMIN"})
	@GetMapping("/read-users")
		public User readUsers() {

		List<User> users = customUserService.findAll(UserDetails.builder().build());

		System.out.println(users.toString());

		return (User) users;
	}

}
