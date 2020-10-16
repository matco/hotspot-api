package name.matco.hotspot.repositories.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.inject.Inject;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import name.matco.hotspot.model.User;
import name.matco.hotspot.repositories.UserRepository;
import name.matco.hotspot.services.datasource.ConnectionProvider;

public class UserRepositoryDb implements UserRepository {

	private static final Logger LOGGER = LogManager.getLogger(UserRepositoryDb.class.getName());

	@Inject
	private ConnectionProvider connectionProvider;

	public static User generateUser(final ResultSet result) throws SQLException {
		final User user = new User();
		user.setPk(result.getLong("pk"));
		user.setHandle(result.getString("handle"));
		user.setEmail(result.getString("email"));
		user.setPassword(result.getString("password"));
		user.setFirstname(result.getString("firstname"));
		user.setLastname(result.getString("lastname"));
		return user;
	}

	@Override
	public List<User> getAll() {
		final List<User> users = new ArrayList<>();
		try(
				final Connection connection = connectionProvider.getConnection();
				final Statement statement = connection.createStatement();
				final ResultSet result = statement.executeQuery("select * from user");) {
			while(result.next()) {
				users.add(generateUser(result));
			}
		}
		catch(final SQLException e) {
			LOGGER.catching(Level.ERROR, e);
		}
		return users;
	}

	@Override
	public Optional<User> getByEmail(final String email) {
		try(
				final Connection connection = connectionProvider.getConnection();
				final PreparedStatement statement = connection.prepareStatement("select * from user where email = ? limit 1");) {
			statement.setString(1, email);
			final ResultSet result = statement.executeQuery();
			while(result.next()) {
				return Optional.<User> of(generateUser(result));
			}
		}
		catch(final SQLException e) {
			LOGGER.catching(Level.ERROR, e);
		}
		return Optional.<User> empty();
	}

	@Override
	public Optional<User> getByHandle(final String handle) {
		try(
				final Connection connection = connectionProvider.getConnection();
				final PreparedStatement statement = connection.prepareStatement("select * from user where handle = ? limit 1");) {
			statement.setString(1, handle);
			final ResultSet result = statement.executeQuery();
			while(result.next()) {
				return Optional.<User> of(generateUser(result));
			}
		}
		catch(final SQLException e) {
			LOGGER.catching(Level.ERROR, e);
		}
		return Optional.<User> empty();
	}

	@Override
	public boolean save(final User user) {
		user.setHandle(generateHandle(user));
		try(
				final Connection connection = connectionProvider.getConnection();
				final PreparedStatement statement = connection.prepareStatement("insert into user (handle, email, password, firstname, lastname) values (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);) {
			statement.setString(1, user.getHandle());
			statement.setString(2, user.getEmail());
			statement.setString(3, user.getPassword());
			statement.setString(4, user.getFirstname());
			statement.setString(5, user.getLastname());
			statement.executeUpdate();
			final ResultSet rs = statement.getGeneratedKeys();
			if(rs.next()) {
				user.setPk(rs.getInt(1));
			}
			return true;
		}
		catch(final SQLException e) {
			LOGGER.catching(Level.ERROR, e);
			return false;
		}
	}

	@Override
	public boolean update(final User user) {
		try(
				final Connection connection = connectionProvider.getConnection();
				final PreparedStatement statement = connection.prepareStatement("update user set email = ?, password = ?, firstname = ?, lastname = ? where handle = ?");) {
			statement.setString(1, user.getEmail());
			statement.setString(2, user.getPassword());
			statement.setString(3, user.getFirstname());
			statement.setString(4, user.getLastname());
			statement.setString(5, user.getHandle());
			statement.executeUpdate();
			return true;
		}
		catch(final SQLException e) {
			LOGGER.catching(Level.ERROR, e);
			return false;
		}
	}

	@Override
	public boolean delete(final User user) {
		try(
				final Connection connection = connectionProvider.getConnection();
				final PreparedStatement statement = connection.prepareStatement("delete from user where handle = ? limit 1");) {
			statement.setString(1, user.getHandle());
			statement.executeUpdate();
			return true;
		}
		catch(final SQLException e) {
			LOGGER.catching(Level.ERROR, e);
			return false;
		}
	}

}
