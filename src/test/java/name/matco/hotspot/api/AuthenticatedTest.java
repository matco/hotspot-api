package name.matco.hotspot.api;

import java.util.Collections;
import java.util.Map;

import jakarta.ws.rs.client.ClientRequestFilter;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.glassfish.jersey.client.ClientConfig;

import name.matco.hotspot.api.dto.UserDto;
import name.matco.hotspot.api.security.AuthenticationRequestFilter;
import name.matco.hotspot.api.security.tokens.Credentials;

public class AuthenticatedTest extends APITest {

	public static final String USER_NAME = "John Doe";
	public static final String USER_EMAIL = "john.doe@matco.name";
	public static final String USER_PASSWORD = "password";

	public static final GenericType<Map<String, String>> TOKEN_TYPE = new GenericType<>() {
		//don't care
	};

	protected String token;

	protected UserDto user;

	@Override
	public void configureClient(final ClientConfig config) {
		super.configureClient(config);
		config.register((ClientRequestFilter) requestContext -> {
			if(StringUtils.isNoneBlank(token)) {
				final String header = AuthenticationRequestFilter.generateHeader(token);
				requestContext.getHeaders().put(HttpHeaders.AUTHORIZATION, Collections.singletonList(header));
			}
		});
	}

	public void create_user() {
		final var newUser = new UserDto(USER_EMAIL, USER_PASSWORD, USER_NAME);
		user = target("users").request().post(Entity.entity(newUser, MediaType.APPLICATION_JSON)).readEntity(UserDto.class);
	}

	public void delete_user() {
		target(String.format("users/%", user.handle())).request().delete();
	}

	public void get_token(final String email, final String password) {
		final Credentials credentials = new Credentials();
		credentials.email = email;
		credentials.password = password;
		final Map<String, String> response = target("tokens").request().post(Entity.entity(credentials, MediaType.APPLICATION_JSON)).readEntity(TOKEN_TYPE);
		token = response.get("token");
	}

	public void get_token() {
		get_token(USER_EMAIL, USER_PASSWORD);
	}

	public void revoke_token() {
		target("tokens").request().delete();
	}

}
