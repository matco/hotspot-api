package name.matco.hotspot.api.security.tokens;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.inject.Inject;

import org.apache.commons.lang3.RandomStringUtils;

import name.matco.hotspot.model.User;
import name.matco.hotspot.services.datasource.ConnectionProvider;

public class RevokedTokenRepository {

	private static final String CHECK_EXISTING_TOKEN_QUERY = "SELECT token FROM session WHERE token = ? LIMIT 1";
	private static final String CREATE_SESSION_QUERY = "INSERT INTO session(token, user_fk, last_use_date) VALUES(?, ?, NOW())";
	private static final String GET_SESSION_QUERY = "SELECT token, user_fk FROM session WHERE token = ? LIMIT 1";
	private static final String UPDATE_LAST_USE_QUERY = "UPDATE session SET last_use_date = NOW() WHERE token = ?";
	private static final String DELETE_SESSION_QUERY = "DELETE FROM session WHERE token = ?";

	@Inject
	private ConnectionProvider connectionProvider;

	private static String generateToken() {
		return RandomStringUtils.randomNumeric(32);
	}

	public boolean check(final String token) {
		try(
				Connection connection = connectionProvider.getConnection();
				PreparedStatement ps = connection.prepareStatement(CHECK_EXISTING_TOKEN_QUERY);) {
			ps.setString(1, token);
			return ps.execute();
		}
		catch(final SQLException ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public String create(final User user) throws SQLException {
		//generate token that doesn't already exist
		String token = null;
		do {
			token = generateToken();
		}
		while(!check(token));

		try(
				Connection connection = connectionProvider.getConnection();
				PreparedStatement ps = connection.prepareStatement(CREATE_SESSION_QUERY)) {
			ps.setString(1, token);
			ps.setLong(2, user.getPk());
			ps.executeUpdate();
			return token;
		}
	}

	public Long get(final String token) throws InvalidToken {
		try(
				Connection connection = connectionProvider.getConnection();
				PreparedStatement ps = connection.prepareStatement(GET_SESSION_QUERY);) {
			ps.setString(1, token);
			try(ResultSet results = ps.executeQuery()) {
				if(results.next()) {
					update(token);
					return results.getLong("user_fk");
				}
			}
		}
		catch(final SQLException ex) {
			ex.printStackTrace();
		}
		throw new InvalidToken();
	}

	public boolean delete(final String token) throws SQLException {
		try(
				Connection connection = connectionProvider.getConnection();
				PreparedStatement ps = connection.prepareStatement(DELETE_SESSION_QUERY);) {
			ps.setString(1, token);
			final int res = ps.executeUpdate();
			return res > 0;
		}
	}

	private void update(final String token) throws SQLException {
		try(
				Connection connection = connectionProvider.getConnection();
				PreparedStatement ps = connection.prepareStatement(UPDATE_LAST_USE_QUERY);) {
			ps.setString(1, token);
			ps.executeUpdate();
		}
	}
}
