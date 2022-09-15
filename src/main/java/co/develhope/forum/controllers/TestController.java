package co.develhope.forum.controllers;



import it.pasqualecavallo.studentsmaterial.authorization_framework.filter.AuthenticationContext;
import it.pasqualecavallo.studentsmaterial.authorization_framework.security.PublicEndpoint;
import it.pasqualecavallo.studentsmaterial.authorization_framework.security.RoleSecurity;
import it.pasqualecavallo.studentsmaterial.authorization_framework.security.ZeroSecurity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {


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

}
