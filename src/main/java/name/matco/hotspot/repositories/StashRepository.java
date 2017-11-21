package name.matco.hotspot.repositories;

import java.util.List;
import java.util.Optional;

import name.matco.hotspot.model.Stash;
import name.matco.hotspot.model.User;

public interface StashRepository {

	Optional<Stash> getByUuid(String uuid);

	List<Stash> search(User user, String search);

	boolean save(Stash stash);

	boolean delete(Stash stash);

	boolean update(Stash stash);
}
