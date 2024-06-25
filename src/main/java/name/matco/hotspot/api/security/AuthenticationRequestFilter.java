package name.matco.hotspot.api.security;

import java.lang.reflect.Method;
import java.util.Optional;

import jakarta.annotation.Priority;
import jakarta.annotation.security.DenyAll;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import name.matco.hotspot.api.ErrorResponse;
import name.matco.hotspot.api.security.tokens.InvalidToken;
import name.matco.hotspot.api.security.tokens.JWTService;
import name.matco.hotspot.model.User;
import name.matco.hotspot.repositories.UserRepository;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationRequestFilter implements ContainerRequestFilter {

	private static final Logger LOGGER = LogManager.getLogger(AuthenticationRequestFilter.class.getName());

	public static String retrieveToken(final String authorizationHeader) {
		return authorizationHeader.replaceFirst("Bearer ", "");
	}

	public static String generateHeader(final String token) {
		final StringBuilder builder = new StringBuilder();
		builder.append("Bearer ");
		builder.append(token);
		return builder.toString();
	}

	@Context
	private ResourceInfo resourceInfo;

	@Inject
	private JWTService jwtService;

	@Inject
	private UserRepository userRepository;

	@Override
	public void filter(final ContainerRequestContext context) {
		final Method method = resourceInfo.getResourceMethod();
		//do not apply filter for OPTIONS requests
		if("OPTIONS".equals(context.getMethod())) {
			return;
		}
		//access allowed for all
		if(!method.isAnnotationPresent(PermitAll.class)) {
			//access denied for all
			if(method.isAnnotationPresent(DenyAll.class)) {
				context.abortWith(Response.status(Response.Status.FORBIDDEN).entity(new ErrorResponse("Access denied.")).build());
				return;
			}

			//get authorization header
			final MultivaluedMap<String, String> headers = context.getHeaders();
			final String authorization = headers.getFirst(HttpHeaders.AUTHORIZATION);

			//block access if there is no authorization
			if(StringUtils.isEmpty(authorization)) {
				context.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity(new ErrorResponse("Please login to access this resource.")).build());
				return;
			}

			final String token = retrieveToken(authorization);

			try {
				//check if token is valid
				final DecodedJWT jwt = jwtService.verify(token);
				final Optional<User> user = userRepository.getByEmail(jwt.getClaim("user").asString());
				if(user.isPresent()) {
					LOGGER.info("Create security context for user {}", user.get().getEmail());
					context.setSecurityContext(new AppSecurityContext(user.get()));
				}
				else {
					context.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity(new ErrorResponse("User is no longer valid. Please login again.")).build());
				}
			}
			catch(final JWTVerificationException | InvalidToken e) {
				LOGGER.catching(Level.INFO, e);
				context.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity(new ErrorResponse("Invalid token. Please login again.")).build());
			}

			//verify user access
			/*if(method.isAnnotationPresent(RolesAllowed.class)) {
				final RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
				final Set<String> rolesSet = new HashSet<String>(Arrays.asList(rolesAnnotation.value()));

				//Is user valid?
				if(!isUserAllowed(username, password, rolesSet)) {
					context.abortWith(ACCESS_UNAUTHORIZED);
					return;
				}
			}*/
		}
	}

}
