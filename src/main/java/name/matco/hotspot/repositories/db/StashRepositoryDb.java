package name.matco.hotspot.repositories.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import jakarta.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.jooq.Condition;
import org.jooq.DSLContext;

import name.matco.hotspot.model.Stash;
import name.matco.hotspot.model.User;
import name.matco.hotspot.model.jooq.tables.records.StashRecord;
import name.matco.hotspot.repositories.StashRepository;

import static name.matco.hotspot.model.jooq.Tables.STASH;

public class StashRepositoryDb implements StashRepository {

	@Inject
	private DSLContext dsl;

	@Override
	public Optional<Stash> getByUuid(final String uuid) {
		return dsl.selectFrom(STASH).where(STASH.UUID.eq(uuid)).fetchOptionalInto(Stash.class);
	}

	@Override
	public List<Stash> search(final User user, final String search) {
		final var conditions = new ArrayList<Condition>();
		conditions.add(STASH.USER_FK.eq(user.getPk()));
		if(StringUtils.isNotBlank(search)) {
			conditions.add(STASH.NAME.containsIgnoreCase(search).or(STASH.DESCRIPTION.containsIgnoreCase(search)));
		}
		return dsl.selectFrom(STASH).where(conditions).fetchInto(Stash.class);
	}

	@Override
	public void save(final Stash stash) {
		stash.setUuid(UUID.randomUUID().toString());
		final var stashRecord = new StashRecord();
		stashRecord.from(stash);
		final var pk = dsl.insertInto(STASH).set(stashRecord).returningResult(STASH.PK).fetchOne();
		stash.setPk(pk.value1());
	}

	@Override
	public void update(final Stash stash) {
		dsl.update(STASH)
			.set(STASH.NAME, stash.getName())
			.set(STASH.DESCRIPTION, stash.getDescription())
			.where(STASH.PK.eq(stash.getPk()))
			.execute();
	}

	@Override
	public void delete(final Stash stash) {
		dsl.deleteFrom(STASH).where(STASH.PK.eq(stash.getPk())).execute();
	}

}
