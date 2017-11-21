package name.matco.hotspot.model;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class UserTest {

	@Test
	public void test_password_hash() {
		final User user = new User();
		user.setPlainTextPassword("password");
		assertNotEquals("password", user.getPassword());
		assertTrue(user.getPassword().matches("^[a-z0-9]{40}$"));
	}
}
