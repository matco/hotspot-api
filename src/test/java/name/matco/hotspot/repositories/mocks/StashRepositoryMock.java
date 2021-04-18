package name.matco.hotspot.repositories.mocks;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import name.matco.hotspot.model.Stash;
import name.matco.hotspot.model.User;
import name.matco.hotspot.repositories.StashRepository;

public class StashRepositoryMock implements StashRepository {

	private Set<Stash> stashes = new HashSet<>();
	private long pk = 1;

	@Override
	public Optional<Stash> getByUuid(final String uuid) {
		return stashes.stream().filter(s -> s.getUuid().equals(uuid)).findFirst();
	}

	@Override
	public List<Stash> search(final User user, final String search) {
		final Predicate<Stash> predicate = (Stash s) -> {
			return s.getUserFk() == user.getPk() && MockHelper.testSearch(s, search);
		};
		return stashes.stream().filter(predicate).collect(Collectors.toList());
	}

	@Override
	public boolean save(final Stash stash) {
		stashes.add(stash);
		stash.setPk(pk++);
		return true;
	}

	@Override
	public boolean update(final Stash stash) {
		final Stash oldStash = getByUuid(stash.getUuid()).get();
		oldStash.setName(stash.getName());
		return true;
	}

	@Override
	public boolean delete(final Stash stash) {
		stashes.remove(stash);
		return true;
	}

}
