package name.matco.hotspot.repositories.mocks;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import name.matco.hotspot.model.User;
import name.matco.hotspot.repositories.UserRepository;

public class UserRepositoryMock implements UserRepository {

	private final Set<User> users = new HashSet<>();
	private int pk = 1;

	@Override
	public List<User> getAll() {
		return new ArrayList<>(users);
	}

	@Override
	public Optional<User> getByEmail(final String email) {
		return users.stream().filter(u -> u.getEmail().equals(email)).findFirst();
	}

	@Override
	public Optional<User> getByHandle(final String handle) {
		return users.stream().filter(u -> u.getHandle().equals(handle)).findFirst();
	}

	@Override
	public void save(final User user) {
		users.add(user);
		user.setPk(pk++);
	}

	@Override
	public void update(final User user) {
		final User oldUser = getByHandle(user.getHandle()).get();
		oldUser.setName(user.getName());
	}

	@Override
	public void delete(final User user) {
		users.remove(user);
	}

}
