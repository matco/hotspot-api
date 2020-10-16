package name.matco.hotspot.api.resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response.Status;

import org.junit.Test;

import name.matco.hotspot.api.AuthenticatedTest;
import name.matco.hotspot.api.security.tokens.Credentials;

public class TokenResourceTest extends AuthenticatedTest {

	@Test
	public void test_token_crud() {
		create_user();

		final Credentials credentials = new Credentials();
		credentials.email = AuthenticatedTest.USER_EMAIL;
		credentials.password = AuthenticatedTest.USER_PASSWORD;

		var response = target("tokens").request().post(Entity.entity(credentials, MediaType.APPLICATION_JSON));
		assertEquals(response.getStatus(), Status.OK.getStatusCode());
		final var responseToken = response.readEntity(AuthenticatedTest.TOKEN_TYPE);
		final var customToken = responseToken.get("token");
		assertNotNull(customToken);

		response = target("tokens").request().header(HttpHeaders.AUTHORIZATION, customToken).delete();
		assertEquals(Status.NO_CONTENT.getStatusCode(), response.getStatus());
	}
}
