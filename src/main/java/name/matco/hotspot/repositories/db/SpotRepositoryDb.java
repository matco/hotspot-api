package name.matco.hotspot.repositories.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import jakarta.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.jooq.Condition;
import org.jooq.DSLContext;

import name.matco.hotspot.model.Spot;
import name.matco.hotspot.model.Stash;
import name.matco.hotspot.model.User;
import name.matco.hotspot.model.jooq.tables.records.SpotRecord;
import name.matco.hotspot.model.jooq.tables.records.StashSpotRecord;
import name.matco.hotspot.repositories.SpotRepository;

import static name.matco.hotspot.model.jooq.Tables.SPOT;
import static name.matco.hotspot.model.jooq.Tables.STASH_SPOT;

public class SpotRepositoryDb implements SpotRepository {

	@Inject
	private DSLContext dsl;

	@Override
	public Optional<Spot> getByUuid(final String uuid) {
		return dsl.selectFrom(SPOT).where(SPOT.UUID.eq(uuid)).fetchOptionalInto(Spot.class);
	}

	@Override
	public List<Spot> getByUser(final User user) {
		return dsl.selectFrom(SPOT).where(SPOT.USER_FK.eq(user.getPk())).fetchInto(Spot.class);
	}

	@Override
	public List<Spot> search(final User user, final String search) {
		final var conditions = new ArrayList<Condition>();
		conditions.add(SPOT.USER_FK.eq(user.getPk()));
		if(StringUtils.isNotBlank(search)) {
			conditions.add(SPOT.NAME.containsIgnoreCase(search).or(SPOT.DESCRIPTION.containsIgnoreCase(search)));
		}
		return dsl.selectFrom(SPOT).where(conditions).fetchInto(Spot.class);
	}

	@Override
	public void save(final Spot spot) {
		spot.setUuid(UUID.randomUUID().toString());
		final var spotRecord = new SpotRecord();
		spotRecord.from(spot);
		final var pk = dsl.insertInto(SPOT).set(spotRecord).returningResult(SPOT.PK).fetchOne();
		spot.setPk(pk.value1());
	}

	@Override
	public void update(final Spot spot) {
		dsl.update(SPOT)
			.set(SPOT.NAME, spot.getName())
			.set(SPOT.LATITUDE, spot.getLatitude())
			.set(SPOT.LONGITUDE, spot.getLongitude())
			.set(SPOT.DESCRIPTION, spot.getDescription())
			.where(SPOT.PK.eq(spot.getPk()))
			.execute();
	}

	@Override
	public void delete(final Spot spot) {
		dsl.deleteFrom(SPOT).where(SPOT.PK.eq(spot.getPk())).execute();
	}

	@Override
	public List<Spot> getByStash(final Stash stash) {
		return dsl.selectFrom(SPOT).where(SPOT.stashSpot().STASH_FK.eq(stash.getPk())).fetchInto(Spot.class);
	}

	@Override
	public void addToStash(final Stash stash, final Spot spot) {
		final var stashSpotRecord = new StashSpotRecord(stash.getPk(), spot.getPk());
		dsl.insertInto(STASH_SPOT).set(stashSpotRecord).execute();
	}

	@Override
	public void removeFromStash(final Stash stash, final Spot spot) {
		dsl.deleteFrom(STASH_SPOT).where(STASH_SPOT.STASH_FK.eq(stash.getPk()).and(STASH_SPOT.SPOT_FK.eq(spot.getPk()))).execute();
	}
}
