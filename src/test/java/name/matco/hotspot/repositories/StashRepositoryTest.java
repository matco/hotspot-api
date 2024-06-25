package name.matco.hotspot.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import name.matco.hotspot.model.Stash;
import name.matco.hotspot.model.User;
import name.matco.hotspot.repositories.db.StashRepositoryDb;

public class StashRepositoryTest extends RepositoryTest {

	@Test
	public void test() {
		final User user = new User();
		user.setFirstname("John");
		user.setLastname("Doe");
		user.setEmail("john@doe.com");
		final UserRepository userRepository = LOCATOR.getService(UserRepository.class);
		userRepository.save(user);

		final StashRepository stashRepository = LOCATOR.getService(StashRepositoryDb.class);

		final Stash stash = new Stash();
		stash.setUserFk(user.getPk());
		stash.setName("City halls");
		stash.setDescription("Nearby city halls");
		stashRepository.save(stash);
		assertEquals(1, stashRepository.search(user, null).size());
		assertEquals(1, stashRepository.search(user, "hal").size());
		assertTrue(stashRepository.search(user, "aa").isEmpty());
	}
}
