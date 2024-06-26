package name.matco.hotspot.repositories;

import org.junit.jupiter.api.Test;

import name.matco.hotspot.model.Spot;
import name.matco.hotspot.model.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SpotRepositoryTest extends RepositoryTest {

	@Test
	public void test() throws EmailAlreadyExistsException {
		final User user = new User();
		user.setName("John Doe");
		user.setEmail("john.doe@matco.name");
		final UserRepository userRepository = LOCATOR.getService(UserRepository.class);
		userRepository.save(user);

		final SpotRepository spotRepository = LOCATOR.getService(SpotRepository.class);

		final Spot spot = new Spot();
		spot.setUserFk(user.getPk());
		spot.setName("Annecy city hall");
		spot.setDescription("The city hall of Annecy");
		spotRepository.save(spot);
		assertEquals(1, spotRepository.search(user, null).size());
		assertEquals(1, spotRepository.search(user, "Ann").size());
		assertTrue(spotRepository.search(user, "aa").isEmpty());
	}
}
