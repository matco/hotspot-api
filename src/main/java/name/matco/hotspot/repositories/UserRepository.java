package name.matco.hotspot.repositories;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import name.matco.hotspot.model.User;

public interface UserRepository {

	List<User> getAll();

	Optional<User> getByEmail(String email);

	Optional<User> getByHandle(String handle);

	boolean save(User user);

	boolean delete(User user);

	boolean update(User user);

	default String generateHandle(final User user) {
		//generate base handle
		String baseHandle = String.format("%s.%s", user.getFirstname(), user.getLastname());
		baseHandle = StringUtils.replace(baseHandle, " ", "_");
		baseHandle = StringUtils.lowerCase(baseHandle);
		//look in database if handle does not already exist
		String handle = baseHandle;
		int handleOffset = 0;
		while(getByHandle(handle).isPresent()) {
			final StringBuilder builder = new StringBuilder();
			builder.append(baseHandle);
			builder.append(++handleOffset);
			handle = builder.toString();
		}
		return handle;
	}
}
