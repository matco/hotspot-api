package name.matco.hotspot.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class UserTest {

	@Test
	public void test_password_hash() {
		var password = "password";
		final User user = new User();
		user.setPlainTextPassword(password);

		var encryptedPassword = user.getPassword();
		assertNotNull(encryptedPassword);
		assertNotEquals(password, encryptedPassword);

		//because of random salt, new encrypted password should not be equal to previous hash
		user.setPlainTextPassword(password);
		assertNotEquals(encryptedPassword, user.getPassword());

		//check password
		assertFalse(user.checkPassword("toto"));
		assertTrue(user.checkPassword(password));
	}
}
