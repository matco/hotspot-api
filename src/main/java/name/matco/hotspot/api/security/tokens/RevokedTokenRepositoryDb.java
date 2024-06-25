package name.matco.hotspot.api.security.tokens;

import java.util.Optional;

import jakarta.inject.Inject;

import org.jooq.DSLContext;

import name.matco.hotspot.model.jooq.tables.records.RevokedTokenRecord;

import static name.matco.hotspot.model.jooq.Tables.REVOKED_TOKEN;

public class RevokedTokenRepositoryDb implements RevokedTokenRepository {

	@Inject
	private DSLContext dsl;

	@Override
	public Optional<RevokedToken> getByToken(final String token) {
		return dsl.selectFrom(REVOKED_TOKEN).where(REVOKED_TOKEN.TOKEN.eq(token)).fetchOptionalInto(RevokedToken.class);
	}

	@Override
	public void save(final RevokedToken revokedToken) {
		final var revokedTokenRecord = new RevokedTokenRecord();
		revokedTokenRecord.from(revokedToken);
		dsl.insertInto(REVOKED_TOKEN).set(revokedTokenRecord).execute();
	}
}
