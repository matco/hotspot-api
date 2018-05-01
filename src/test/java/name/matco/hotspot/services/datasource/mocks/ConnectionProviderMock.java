package name.matco.hotspot.services.datasource.mocks;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

import com.zaxxer.hikari.HikariDataSource;

import name.matco.hotspot.services.datasource.ConnectionProvider;

public class ConnectionProviderMock implements ConnectionProvider {

	private HikariDataSource ds;

	public ConnectionProviderMock() {
		ds = new HikariDataSource();
		//generate unique database name for each instance
		final UUID uuid = UUID.randomUUID();
		ds.setJdbcUrl(String.format("jdbc:h2:mem:test%s;MODE=MYSQL", uuid));
	}

	@Override
	public Connection getConnection() throws SQLException {
		return ds.getConnection();
	}

}
