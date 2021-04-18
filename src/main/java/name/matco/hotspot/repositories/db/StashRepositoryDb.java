package name.matco.hotspot.repositories.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import jakarta.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import name.matco.hotspot.model.Stash;
import name.matco.hotspot.model.User;
import name.matco.hotspot.repositories.StashRepository;
import name.matco.hotspot.services.datasource.ConnectionProvider;

public class StashRepositoryDb implements StashRepository {

	private static final Logger LOGGER = LogManager.getLogger(StashRepositoryDb.class.getName());

	@Inject
	private ConnectionProvider connectionProvider;

	public static Stash generateStash(final ResultSet result) throws SQLException {
		final Stash stash = new Stash();
		stash.setPk(result.getLong("pk"));
		stash.setUserFk(result.getLong("user_fk"));
		stash.setUuid(result.getString("uuid"));
		stash.setName(result.getString("name"));
		stash.setDescription(result.getString("description"));
		return stash;
	}

	@Override
	public Optional<Stash> getByUuid(final String uuid) {
		try(
				final Connection connection = connectionProvider.getConnection();
				final PreparedStatement statement = connection.prepareStatement("select * from stash where uuid = ? limit 1");) {
			statement.setString(1, uuid);
			final ResultSet result = statement.executeQuery();
			while(result.next()) {
				return Optional.<Stash> of(generateStash(result));
			}
		}
		catch(final SQLException e) {
			LOGGER.catching(Level.ERROR, e);
		}
		return Optional.<Stash> empty();
	}

	@Override
	public List<Stash> search(final User user, final String search) {
		final List<Stash> stashes = new ArrayList<>();
		StringBuilder sql = new StringBuilder("select * from stash where user_fk = ?");
		if(StringUtils.isNotBlank(search)) {
			sql.append(" and (name like ? or description like ?)");
		}
		try(
				final Connection connection = connectionProvider.getConnection();
				final PreparedStatement statement = connection.prepareStatement(sql.toString());) {
			statement.setLong(1, user.getPk());
			if(StringUtils.isNotBlank(search)) {
				final StringBuilder matchBuilder = new StringBuilder();
				matchBuilder.append("%");
				matchBuilder.append(search);
				matchBuilder.append("%");
				final String match = matchBuilder.toString();
				statement.setString(2, match);
				statement.setString(3, match);
			}
			final ResultSet result = statement.executeQuery();
			while(result.next()) {
				stashes.add(generateStash(result));
			}
		}
		catch(final SQLException e) {
			LOGGER.catching(Level.ERROR, e);
		}
		return stashes;
	}

	@Override
	public boolean save(final Stash stash) {
		stash.setUuid(UUID.randomUUID().toString());
		try(
				final Connection connection = connectionProvider.getConnection();
				final PreparedStatement statement = connection.prepareStatement("insert into stash (user_fk, uuid, name, description) values (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);) {
			statement.setLong(1, stash.getUserFk());
			statement.setString(2, stash.getUuid());
			statement.setString(3, stash.getName());
			statement.setString(4, stash.getDescription());
			statement.executeUpdate();
			final ResultSet rs = statement.getGeneratedKeys();
			if(rs.next()) {
				stash.setPk(rs.getInt(1));
			}
			return true;
		}
		catch(final SQLException e) {
			LOGGER.catching(Level.ERROR, e);
			return false;
		}
	}

	@Override
	public boolean update(final Stash stash) {
		try(
				final Connection connection = connectionProvider.getConnection();
				final PreparedStatement statement = connection.prepareStatement("update stash set name = ?, description = ? where uuid = ?");) {
			statement.setString(1, stash.getName());
			statement.setString(2, stash.getDescription());
			statement.setString(3, stash.getUuid());
			statement.executeUpdate();
			return true;
		}
		catch(final SQLException e) {
			LOGGER.catching(Level.ERROR, e);
			return false;
		}
	}

	@Override
	public boolean delete(final Stash stash) {
		try(
				final Connection connection = connectionProvider.getConnection();
				final PreparedStatement statement = connection.prepareStatement("delete from stash where uuid = ? limit 1");) {
			statement.setString(1, stash.getUuid());
			statement.executeUpdate();
			return true;
		}
		catch(final SQLException e) {
			LOGGER.catching(Level.ERROR, e);
			return false;
		}
	}

}
