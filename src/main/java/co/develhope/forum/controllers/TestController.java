package co.develhope.forum.controllers;



import co.develhope.forum.dao.SignUpDAO;
import co.develhope.forum.dto.response.BaseResponse;
import co.develhope.forum.dto.response.EmailDTO;
import co.develhope.forum.dto.response.UserDTO;
import co.develhope.forum.model.User;
import co.develhope.forum.repositories.UserRepository;
import co.develhope.forum.services.CustomUserService;
import it.pasqualecavallo.studentsmaterial.authorization_framework.filter.AuthenticationContext;
import it.pasqualecavallo.studentsmaterial.authorization_framework.security.PublicEndpoint;
import it.pasqualecavallo.studentsmaterial.authorization_framework.security.RoleSecurity;
import it.pasqualecavallo.studentsmaterial.authorization_framework.security.ZeroSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

	@Autowired
	SignUpDAO signUpDAO;

	@Autowired
	UserRepository userRepository;

	@Autowired
	CustomUserService customUserService;

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

	@RoleSecurity(value = { "ROLE_USER", "ROLE_ADMIN"})
	@GetMapping("/role-evaluated-endpoint")
	public void roleCheckEndpoint() {
		System.out.println("This endpoint can be reached only by authenticated users with ROLE_USER or ROLE_ADMIN. Authenticated user is " + AuthenticationContext.get().getUsername());
	}

	@ZeroSecurity
	@PutMapping("/update-email")
	public BaseResponse updateEmail(@RequestBody EmailDTO emailDTO){

		AuthenticationContext.Principal principal = AuthenticationContext.get();

		return customUserService.updateEmail(principal.getUsername(), emailDTO.getNewEmail());
	}
}
