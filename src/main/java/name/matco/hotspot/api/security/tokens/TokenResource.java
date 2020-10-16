package name.matco.hotspot.api.security.tokens;

import java.util.Collections;
import java.util.Optional;

import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.SecurityContext;

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
			@HeaderParam(HttpHeaders.AUTHORIZATION) final String authorization) throws Exception {
		final User user = (User) sc.getUserPrincipal();
		jwtService.revoke(user, authorization);
		return Response.noContent().build();
	}

}
