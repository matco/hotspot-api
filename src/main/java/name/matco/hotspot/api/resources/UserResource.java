package name.matco.hotspot.api.resources;

import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.SecurityContext;

import org.apache.commons.lang3.StringUtils;

import name.matco.hotspot.api.dto.UserDto;
import name.matco.hotspot.api.security.AuthenticationRequestFilter;
import name.matco.hotspot.api.security.tokens.JWTService;
import name.matco.hotspot.model.User;
import name.matco.hotspot.repositories.UserRepository;

@Path("users")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

	@Inject
	private JWTService jwtService;

	@Inject
	private UserRepository userRepository;

	@Context
	private SecurityContext sc;

	@GET
	@Path("{handle}")
	public UserDto getUser(@PathParam("handle") final String handle) {
		final User user = (User) sc.getUserPrincipal();
		if(user.getHandle().equals(handle)) {
			return new UserDto(user);
		}
		throw new ForbiddenException();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@PermitAll
	public UserDto createUser(final UserDto userDto) throws Exception {
		if(StringUtils.isAnyBlank(userDto.getFirstname(), userDto.getLastname(), userDto.getEmail(), userDto.getPassword())) {
			throw new BadRequestException("First name, last name, email and password are required");
		}
		final var user = new User();
		user.setFirstname(userDto.getFirstname());
		user.setLastname(userDto.getLastname());
		user.setEmail(userDto.getEmail());
		user.setPlainTextPassword(userDto.getPassword());
		user.setHandle(userRepository.generateHandle(user));
		userRepository.save(user);
		return new UserDto(user);
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{handle}")
	public UserDto updateUser(@PathParam("handle") final String handle, final UserDto userDto) throws Exception {
		final User user = (User) sc.getUserPrincipal();
		if(user.getHandle().equals(handle)) {
			user.setFirstname(userDto.getFirstname());
			user.setLastname(userDto.getLastname());
			user.setEmail(userDto.getEmail());
			userRepository.update(user);
			return new UserDto(user);
		}
		throw new ForbiddenException();
	}

	@DELETE
	@Path("{handle}")
	public Response deleteUser(
			@PathParam("handle") final String handle,
			@HeaderParam(HttpHeaders.AUTHORIZATION) final String authorization) throws Exception {
		final User user = (User) sc.getUserPrincipal();
		if(user.getHandle().equals(handle)) {
			//delete
			userRepository.delete(user);
			//revoke user
			jwtService.revoke(user, AuthenticationRequestFilter.retrieveToken(authorization));
			return Response.noContent().build();
		}
		return Response.status(Status.FORBIDDEN).build();
	}
}
