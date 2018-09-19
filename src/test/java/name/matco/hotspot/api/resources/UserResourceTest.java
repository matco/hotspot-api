package name.matco.hotspot.api.resources;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.junit.Test;

import name.matco.hotspot.api.APITest;
import name.matco.hotspot.model.User;

public class UserResourceTest extends APITest {

	@Test
	public void test_user_crud() {
		final User newUser = new User();
		newUser.setFirstname("John");
		newUser.setLastname("Doe");
		newUser.setEmail("john@doe.name");
		newUser.setPassword("password");

		final User responseUser = target("users").request().post(Entity.entity(newUser, MediaType.APPLICATION_JSON)).readEntity(User.class);
		assertEquals("John", responseUser.getFirstname());
		assertEquals("Doe", responseUser.getLastname());
		assertEquals("john@doe.name", responseUser.getEmail());
		assertEquals("john.doe", responseUser.getHandle());

		final var response = target("users/john.doe").request().delete();
		assertEquals(Status.NO_CONTENT.getStatusCode(), response.getStatus());
	}
}
