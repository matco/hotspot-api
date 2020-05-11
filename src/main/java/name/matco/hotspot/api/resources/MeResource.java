package name.matco.hotspot.api.resources;

import java.security.Principal;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.Response.Status;

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
