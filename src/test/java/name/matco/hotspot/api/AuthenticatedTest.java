package name.matco.hotspot.api;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.glassfish.jersey.client.ClientConfig;

import name.matco.hotspot.api.dto.UserDto;
import name.matco.hotspot.api.security.AuthenticationRequestFilter;
import name.matco.hotspot.api.security.tokens.Credentials;

public class AuthenticatedTest extends APITest {

	public static final String USER_FIRSTNAME = "John";
	public static final String USER_LASTNAME = "Doe";
	public static final String USER_EMAIL = "john@doe.name";
	public static final String USER_PASSWORD = "password";

	public static final GenericType<Map<String, String>> TOKEN_TYPE = new GenericType<Map<String, String>>() {
		//don't care
	};

	protected String token;

	protected UserDto user;

	@Override
	public void configureClient(final ClientConfig config) {
		super.configureClient(config);
		config.register(new ClientRequestFilter() {
			@Override
			public void filter(final ClientRequestContext requestContext) throws IOException {
				if(StringUtils.isNoneBlank(token)) {
					final String header = AuthenticationRequestFilter.generateHeader(token);
					requestContext.getHeaders().put(javax.ws.rs.core.HttpHeaders.AUTHORIZATION, Collections.singletonList(header));
				}
			}
		});
	}

	public void create_user() {
		var newUser = new UserDto();
		newUser.setFirstname(USER_FIRSTNAME);
		newUser.setLastname(USER_LASTNAME);
		newUser.setEmail(USER_EMAIL);
		newUser.setPassword(USER_PASSWORD);
		user = target("users").request().post(Entity.entity(newUser, MediaType.APPLICATION_JSON)).readEntity(UserDto.class);
		user.setHandle(newUser.getHandle());
	}

	public void delete_user() {
		target(String.format("users/%", user.getHandle())).request().delete();
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
