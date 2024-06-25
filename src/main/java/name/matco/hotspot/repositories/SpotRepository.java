package name.matco.hotspot.repositories;

import java.util.List;
import java.util.Optional;

import name.matco.hotspot.model.Spot;
import name.matco.hotspot.model.Stash;
import name.matco.hotspot.model.User;

public interface SpotRepository {

	Optional<Spot> getByUuid(String uuid);

	List<Spot> getByUser(User user);

	List<Spot> search(User user, String search);

	void save(Spot spot);

	void delete(Spot spot);

	void update(Spot spot);

	List<Spot> getByStash(Stash stash);

	void addToStash(Stash stash, Spot spot);

	void removeFromStash(Stash stash, Spot spot);
}
