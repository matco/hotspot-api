package name.matco.hotspot.api.resources;

import static org.junit.Assert.assertNotNull;

import java.util.Map;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import org.junit.Test;

import name.matco.hotspot.api.AuthenticatedTest;
import name.matco.hotspot.api.security.tokens.Credentials;

public class TokenResourceTest extends AuthenticatedTest {

	@Test
	public void test_get_token() {
		create_user();

		final Credentials credentials = new Credentials();
		credentials.email = AuthenticatedTest.USER_EMAIL;
		credentials.password = AuthenticatedTest.USER_PASSWORD;

		final Map<String, String> response = target("tokens").request().post(Entity.entity(credentials, MediaType.APPLICATION_JSON)).readEntity(AuthenticatedTest.TOKEN_TYPE);
		assertNotNull(response.get("token"));
	}
}
