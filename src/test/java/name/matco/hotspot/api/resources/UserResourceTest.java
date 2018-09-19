package name.matco.hotspot.api.resources;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import name.matco.hotspot.api.APITest;
import name.matco.hotspot.api.AuthenticatedTest;
import name.matco.hotspot.api.security.tokens.Credentials;
import name.matco.hotspot.model.User;

public class UserResourceTest extends APITest {

	@Test
	public void test_user_crud() {
		final String lastname = RandomStringUtils.randomAlphabetic(5);
		final String email = String.format("john@%s.com", lastname);
		final String password = "password";

		final User newUser = new User();
		newUser.setFirstname("John");
		newUser.setLastname(lastname);
		newUser.setEmail(email);
		newUser.setPassword(password);

		final User responseUser = target("users").request().post(Entity.entity(newUser, MediaType.APPLICATION_JSON)).readEntity(User.class);
		assertEquals("John", responseUser.getFirstname());
		assertEquals(lastname, responseUser.getLastname());
		assertEquals(email, responseUser.getEmail());
		final var handle = String.format("john.%s", lastname.toLowerCase());
		assertEquals(handle, responseUser.getHandle());

		final Credentials credentials = new Credentials();
		credentials.email = email;
		credentials.password = password;
		final Map<String, String> responseToken = target("tokens").request().post(Entity.entity(credentials, MediaType.APPLICATION_JSON)).readEntity(AuthenticatedTest.TOKEN_TYPE);
		final String token = responseToken.get("token");

		final var response = target("users").path(handle).request().header(javax.ws.rs.core.HttpHeaders.AUTHORIZATION, token).delete();
		assertEquals(Status.NO_CONTENT.getStatusCode(), response.getStatus());
	}
}
