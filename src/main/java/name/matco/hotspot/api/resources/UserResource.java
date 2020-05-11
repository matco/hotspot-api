package name.matco.hotspot.api.resources;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
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
		var user = new User();
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
			@HeaderParam(javax.ws.rs.core.HttpHeaders.AUTHORIZATION) final String authorization) throws Exception {
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
