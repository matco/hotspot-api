package name.matco.hotspot.api.resources;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

import name.matco.hotspot.api.security.AuthenticationRequestFilter;
import name.matco.hotspot.api.security.tokens.RevokedTokenRepository;
import name.matco.hotspot.model.User;
import name.matco.hotspot.repositories.UserRepository;

@Path("users")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

	@Inject
	private RevokedTokenRepository revokedTokenRepository;

	@Inject
	private UserRepository userRepository;

	@Context
	private SecurityContext sc;

	@GET
	@Path("{handle}")
	public User getUser(@PathParam("handle") final String handle) {
		final User user = (User) sc.getUserPrincipal();
		final User getUser = userRepository.getByHandle(handle).get();
		if(getUser.getPk() == user.getPk()) {
			return user;
		}
		throw new NotAuthorizedException("Bearer");
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@PermitAll
	public User createUser(final User user) throws Exception {
		user.setPlainTextPassword(user.getPassword());
		user.setHandle(userRepository.generateHandle(user));
		userRepository.save(user);
		return user;
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{handle}")
	public User updateUser(@PathParam("handle") final String handle, final User user) throws Exception {
		user.setPlainTextPassword(user.getPassword());
		user.setHandle(handle);
		userRepository.update(user);
		return user;
	}

	@DELETE
	@Path("{handle}")
	public Response deleteUser(
			@PathParam("handle") final String handle,
			@HeaderParam(javax.ws.rs.core.HttpHeaders.AUTHORIZATION) final String authorization) throws Exception {
		final User user = (User) sc.getUserPrincipal();
		if(user.getHandle().equals(handle)) {
			//delete
			userRepository.delete(user);
			//logout user
			revokedTokenRepository.delete(AuthenticationRequestFilter.retrieveToken(authorization));
			return Response.ok().build();
		}
		return Response.status(Status.FORBIDDEN).build();
	}
}
