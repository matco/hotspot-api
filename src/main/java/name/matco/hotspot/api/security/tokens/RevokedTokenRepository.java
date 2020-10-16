package name.matco.hotspot.api.security.tokens;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;

import jakarta.inject.Inject;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import name.matco.hotspot.services.datasource.ConnectionProvider;

public class RevokedTokenRepository {

	private static final Logger LOGGER = LogManager.getLogger(RevokedTokenRepository.class.getName());

	@Inject
	private ConnectionProvider connectionProvider;

	public static RevokedToken generateRevokedToken(final ResultSet result) throws SQLException {
		return new RevokedToken(result.getString("token"), result.getTimestamp("expiration_date").toInstant(), result.getLong("user_fk"));
	}

	public Optional<RevokedToken> getByToken(final String token) {
		try(
				final Connection connection = connectionProvider.getConnection();
				final PreparedStatement statement = connection.prepareStatement("select * from revoked_token where token = ? limit 1");) {
			statement.setString(1, token);
			final ResultSet result = statement.executeQuery();
			while(result.next()) {
				return Optional.<RevokedToken> of(generateRevokedToken(result));
			}
		}
		catch(final SQLException e) {
			LOGGER.catching(Level.ERROR, e);
		}
		return Optional.<RevokedToken> empty();
	}

	public boolean save(final RevokedToken revokedToken) {
		try(
				final Connection connection = connectionProvider.getConnection();
				final PreparedStatement statement = connection.prepareStatement("insert into revoked_token (token, expiration_date, user_fk) values (?, ?, ?)");) {
			statement.setString(1, revokedToken.getToken());
			statement.setTimestamp(2, Timestamp.from(revokedToken.getExpirationDate()));
			statement.setLong(3, revokedToken.getUserFk());
			statement.executeUpdate();
			return true;
		}
		catch(final SQLException e) {
			LOGGER.catching(Level.ERROR, e);
			return false;
		}
	}
}
