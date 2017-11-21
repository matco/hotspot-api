package name.matco.hotspot.api.resources;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import org.junit.Test;

import name.matco.hotspot.api.APITest;
import name.matco.hotspot.model.User;

public class UserResourceTest extends APITest {

	@Test
	public void test_create_user() {
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
	}
}
