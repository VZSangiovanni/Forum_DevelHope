package co.develhope.forum.controllers;


import co.develhope.forum.configuration.filter.AuthenticationContext;
import co.develhope.forum.configuration.security.PublicEndpoint;
import co.develhope.forum.configuration.security.RoleSecurity;
import co.develhope.forum.configuration.security.ZeroSecurity;
import co.develhope.forum.model.User;
import co.develhope.forum.services.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

	@Autowired
	private TestService testService;

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

	@RoleSecurity(value = { "ROLE_USER", "ROLE_PUBLISHER" })
	@GetMapping("/role-evaluated-endpoint")
	public void roleCheckEndpoint() {
		System.out.println("This endpoint can be reached only by authenticated users with ROLE_ADMIN or ROLE_PUBLISHER. Authenticated user is " + AuthenticationContext.get().getUsername());
	}

}
