package name.matco.hotspot.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import name.matco.hotspot.model.Spot;
import name.matco.hotspot.model.User;

public class SpotRepositoryTest extends RepositoryTest {

	@Test
	public void test() {
		User user = new User();
		user.setFirstname("John");
		user.setLastname("Doe");
		user.setEmail("john@doe.com");
		UserRepository userRepository = locator.getService(UserRepository.class);
		userRepository.save(user);

		SpotRepository spotRepository = locator.getService(SpotRepository.class);

		Spot spot = new Spot();
		spot.setUserFk(user.getPk());
		spot.setName("Annecy city hall");
		spot.setDescription("The city hall of Annecy");
		spotRepository.save(spot);
		assertEquals(1, spotRepository.search(user, null).size());
		assertEquals(1, spotRepository.search(user, "Ann").size());
		assertTrue(spotRepository.search(user, "aa").isEmpty());
	}
}
