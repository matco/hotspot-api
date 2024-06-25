package name.matco.hotspot.repositories.mocks;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import name.matco.hotspot.model.Spot;
import name.matco.hotspot.model.Stash;
import name.matco.hotspot.model.User;
import name.matco.hotspot.repositories.SpotRepository;

public class SpotRepositoryMock implements SpotRepository {

	private Set<Spot> spots = new HashSet<>();
	private Set<Pair<Stash, Spot>> stashSpots = new HashSet<>();
	private int pk = 1;

	@Override
	public Optional<Spot> getByUuid(final String uuid) {
		return spots.stream().filter(s -> s.getUuid().equals(uuid)).findFirst();
	}

	@Override
	public List<Spot> getByUser(final User user) {
		return spots.stream().filter(s -> s.getUserFk() == user.getPk()).collect(Collectors.toList());
	}

	@Override
	public List<Spot> search(final User user, final String search) {
		final Predicate<Spot> predicate = (Spot s) -> {
			return s.getUserFk() == user.getPk() && MockHelper.testSearch(s, search);
		};
		return spots.stream().filter(predicate).collect(Collectors.toList());
	}

	@Override
	public void save(final Spot spot) {
		spots.add(spot);
		spot.setPk(pk++);
	}

	@Override
	public void update(final Spot spot) {
		final Spot oldSpot = getByUuid(spot.getUuid()).get();
		oldSpot.setName(spot.getName());
	}

	@Override
	public void delete(final Spot spot) {
		spots.remove(spot);
	}

	@Override
	public List<Spot> getByStash(final Stash stash) {
		return stashSpots.stream().filter(p -> p.getLeft().equals(stash)).map(Pair::getRight).collect(Collectors.toList());
	}

	@Override
	public void addToStash(final Stash stash, final Spot spot) {
		stashSpots.add(Pair.of(stash, spot));
	}

	@Override
	public void removeFromStash(final Stash stash, final Spot spot) {
		stashSpots.remove(Pair.of(stash, spot));
	}
}
