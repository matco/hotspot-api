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

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import name.matco.hotspot.model.Spot;
import name.matco.hotspot.model.Stash;
import name.matco.hotspot.model.User;
import name.matco.hotspot.repositories.SpotRepository;
import name.matco.hotspot.services.datasource.ConnectionProvider;

public class SpotRepositoryDb implements SpotRepository {

	private static final Logger LOGGER = LogManager.getLogger(SpotRepositoryDb.class.getName());

	@Inject
	private ConnectionProvider connectionProvider;

	public static Spot generateSpot(final ResultSet result) throws SQLException {
		final Spot spot = new Spot();
		spot.setPk(result.getLong("pk"));
		spot.setUserFk(result.getLong("user_fk"));
		spot.setUuid(result.getString("uuid"));
		spot.setName(result.getString("name"));
		spot.setDescription(result.getString("description"));
		spot.setLatitude(result.getDouble("latitude"));
		spot.setLongitude(result.getDouble("longitude"));
		return spot;
	}

	@Override
	public Optional<Spot> getByUuid(final String uuid) {
		try(
				final Connection connection = connectionProvider.getConnection();
				final PreparedStatement statement = connection.prepareStatement("select * from spot where uuid = ? limit 1");) {
			statement.setString(1, uuid);
			final ResultSet result = statement.executeQuery();
			while(result.next()) {
				return Optional.<Spot> of(generateSpot(result));
			}
		}
		catch(final SQLException e) {
			LOGGER.catching(Level.ERROR, e);
		}
		return Optional.<Spot> empty();
	}

	@Override
	public List<Spot> getByUser(final User user) {
		final List<Spot> spots = new ArrayList<>();
		try(
				final Connection connection = connectionProvider.getConnection();
				final PreparedStatement statement = connection.prepareStatement("select * from spot where user_fk = ?");) {
			statement.setLong(1, user.getPk());
			final ResultSet result = statement.executeQuery();
			while(result.next()) {
				spots.add(generateSpot(result));
			}
		}
		catch(final SQLException e) {
			LOGGER.catching(Level.ERROR, e);
		}
		return spots;
	}

	@Override
	public List<Spot> search(final User user, final String search) {
		final List<Spot> spots = new ArrayList<>();
		StringBuilder sql = new StringBuilder("select * from spot where user_fk = ?");
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
				spots.add(generateSpot(result));
			}
		}
		catch(final SQLException e) {
			LOGGER.catching(Level.ERROR, e);
		}
		return spots;
	}

	@Override
	public boolean save(final Spot spot) {
		spot.setUuid(UUID.randomUUID().toString());
		try(
				final Connection connection = connectionProvider.getConnection();
				final PreparedStatement statement = connection.prepareStatement("insert into spot (user_fk, uuid, name, description, latitude, longitude) values (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);) {
			statement.setLong(1, spot.getUserFk());
			statement.setString(2, spot.getUuid());
			statement.setString(3, spot.getName());
			statement.setString(4, spot.getDescription());
			statement.setDouble(5, spot.getLatitude());
			statement.setDouble(6, spot.getLongitude());
			statement.executeUpdate();
			final ResultSet rs = statement.getGeneratedKeys();
			if(rs.next()) {
				spot.setPk(rs.getInt(1));
			}
			return true;
		}
		catch(final SQLException e) {
			LOGGER.catching(Level.ERROR, e);
			return false;
		}
	}

	@Override
	public boolean update(final Spot spot) {
		try(
				final Connection connection = connectionProvider.getConnection();
				final PreparedStatement statement = connection.prepareStatement("update spot set name = ?, description = ?, latitude = ?, longitude = ? where uuid = ?");) {
			statement.setString(1, spot.getName());
			statement.setString(2, spot.getDescription());
			statement.setDouble(3, spot.getLatitude());
			statement.setDouble(4, spot.getLongitude());
			statement.setString(5, spot.getUuid());
			statement.executeUpdate();
			return true;
		}
		catch(final SQLException e) {
			LOGGER.catching(Level.ERROR, e);
			return false;
		}
	}

	@Override
	public boolean delete(final Spot spot) {
		try(
				final Connection connection = connectionProvider.getConnection();
				final PreparedStatement statement = connection.prepareStatement("delete from spot where uuid = ? limit 1");) {
			statement.setString(1, spot.getUuid());
			statement.executeUpdate();
			return true;
		}
		catch(final SQLException e) {
			LOGGER.catching(Level.ERROR, e);
			return false;
		}
	}

	@Override
	public List<Spot> getByStash(final Stash stash) {
		final List<Spot> spots = new ArrayList<>();
		try(
				final Connection connection = connectionProvider.getConnection();
				final PreparedStatement statement = connection.prepareStatement("select * from spot s inner join stash_spot ss on s.pk = ss.spot_fk where stash_fk = ?");) {
			statement.setLong(1, stash.getPk());
			final ResultSet result = statement.executeQuery();
			while(result.next()) {
				spots.add(generateSpot(result));
			}
		}
		catch(final SQLException e) {
			LOGGER.catching(Level.ERROR, e);
		}
		return spots;
	}

	@Override
	public void addToStash(final Stash stash, final Spot spot) {
		try(
				final Connection connection = connectionProvider.getConnection();
				final PreparedStatement statement = connection.prepareStatement("insert into stash_spot (stash_fk, spot_fk) values (?, ?)");) {
			statement.setLong(1, stash.getPk());
			statement.setLong(2, spot.getPk());
			statement.executeUpdate();
		}
		catch(final SQLException e) {
			LOGGER.catching(Level.ERROR, e);
		}
	}

	@Override
	public void removeFromStash(final Stash stash, final Spot spot) {
		try(
				final Connection connection = connectionProvider.getConnection();
				final PreparedStatement statement = connection.prepareStatement("delete from stash_spot where stash_fk = ? and spot_fk = ?");) {
			statement.setLong(1, stash.getPk());
			statement.setLong(2, spot.getPk());
			statement.executeUpdate();
		}
		catch(final SQLException e) {
			LOGGER.catching(Level.ERROR, e);
		}
	}
}
