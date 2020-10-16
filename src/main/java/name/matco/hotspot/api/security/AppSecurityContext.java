package name.matco.hotspot.api.security;

import java.security.Principal;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.SecurityContext;

import name.matco.hotspot.model.User;
import name.matco.hotspot.repositories.UserRepository;

public class AppSecurityContext implements SecurityContext {

	@Inject
	UserRepository userRepository;

	private User user;

	public AppSecurityContext(final User user) {
		this.user = user;
	}

	@Override
	public Principal getUserPrincipal() {
		return getUser();
	}

	public User getUser() {
		return user;
	}

	@Override
	public boolean isUserInRole(final String s) {
		//no role for now
		return true;
	}

	@Override
	public boolean isSecure() {
		return true;
	}

	@Override
	public String getAuthenticationScheme() {
		return SecurityContext.FORM_AUTH;
	}
}
