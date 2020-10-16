package name.matco.hotspot.api.resources;

import java.security.Principal;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.SecurityContext;

import name.matco.hotspot.model.User;
import name.matco.hotspot.repositories.UserRepository;

@Path("me")
@Produces(MediaType.APPLICATION_JSON)
public class MeResource {

	private static class PasswordUpdate {
		public String currentPassword;
		public String newPassword;
	}

	@Context
	private SecurityContext sc;

	@Inject
	private UserRepository userRepository;

	@GET
	public Principal getUser() throws Exception {
		return sc.getUserPrincipal();
	}

	@Path("/password")
	@POST
	public Response updatePassord(PasswordUpdate passwordUpdate) throws Exception {
		final User user = (User) sc.getUserPrincipal();
		if(user.checkPassword(passwordUpdate.currentPassword)) {
			user.setPlainTextPassword(passwordUpdate.newPassword);
			userRepository.update(user);
			return Response.noContent().build();
		}
		return Response.status(Status.FORBIDDEN).build();
	}
}
