package name.matco.hotspot.repositories.db;

import java.util.List;
import java.util.Optional;

import jakarta.inject.Inject;

import org.jooq.DSLContext;

import name.matco.hotspot.model.User;
import name.matco.hotspot.model.jooq.tables.records.UsersRecord;
import name.matco.hotspot.repositories.EmailAlreadyExistsException;
import name.matco.hotspot.repositories.UserRepository;

import static name.matco.hotspot.model.jooq.Tables.USERS;

public class UserRepositoryDb implements UserRepository {

	@Inject
	private DSLContext dsl;

	@Override
	public List<User> getAll() {
		return dsl.selectFrom(USERS).fetchInto(User.class);
	}

	@Override
	public Optional<User> getByEmail(final String email) {
		return dsl.selectFrom(USERS).where(USERS.EMAIL.eq(email)).fetchOptionalInto(User.class);
	}

	@Override
	public Optional<User> getByHandle(final String handle) {
		return dsl.selectFrom(USERS).where(USERS.HANDLE.eq(handle)).fetchOptionalInto(User.class);
	}

	@Override
	public void save(final User user) throws EmailAlreadyExistsException {
		//check that no user with the same e-mail already exists
		if(getByEmail(user.getEmail()).isPresent()) {
			throw new EmailAlreadyExistsException(user.getEmail());
		}
		user.setHandle(generateHandle(user));
		final var userRecord = new UsersRecord();
		userRecord.from(user);
		final var pk = dsl.insertInto(USERS).set(userRecord).returningResult(USERS.PK).fetchOne();
		user.setPk(pk.value1());
	}

	@Override
	public void update(final User user) {
		dsl.update(USERS)
				.set(USERS.NAME, user.getName())
				.set(USERS.EMAIL, user.getEmail())
				.set(USERS.PASSWORD, user.getPassword())
				.where(USERS.PK.eq(user.getPk()))
				.execute();
	}

	@Override
	public void delete(final User user) {
		dsl.deleteFrom(USERS).where(USERS.PK.eq(user.getPk())).execute();
	}

}
