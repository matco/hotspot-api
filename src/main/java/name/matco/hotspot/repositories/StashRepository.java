package name.matco.hotspot.repositories;

import java.util.List;
import java.util.Optional;

import name.matco.hotspot.model.Stash;
import name.matco.hotspot.model.User;

public interface StashRepository {

	Optional<Stash> getByUuid(String uuid);

	List<Stash> search(User user, String search);

	void save(Stash stash);

	void delete(Stash stash);

	void update(Stash stash);
}
