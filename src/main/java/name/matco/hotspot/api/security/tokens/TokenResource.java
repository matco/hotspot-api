package name.matco.hotspot.api.security.tokens;

import java.util.Collections;
import java.util.Optional;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

import name.matco.hotspot.model.User;
import name.matco.hotspot.repositories.UserRepository;

@Path("/tokens")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TokenResource {

	@Context
	private SecurityContext sc;

	@Inject
	private JWTService jwtService;

	@Inject
	private UserRepository userRepository;

	@POST
	@PermitAll
	public Response getToken(final Credentials credentials) throws Exception {
		final Optional<User> user = userRepository.getByEmail(credentials.email);
		if(user.isPresent() && user.get().checkPassword(credentials.password)) {
			final String token = jwtService.create(user.get());
			return Response.ok(Collections.singletonMap("token", token)).build();
		}
		return Response.status(Status.UNAUTHORIZED).build();
	}

	@DELETE
	public Response revokeToken(
			@HeaderParam(javax.ws.rs.core.HttpHeaders.AUTHORIZATION) final String authorization) throws Exception {
		final User user = (User) sc.getUserPrincipal();
		jwtService.revoke(user, authorization);
		return Response.noContent().build();
	}

}
