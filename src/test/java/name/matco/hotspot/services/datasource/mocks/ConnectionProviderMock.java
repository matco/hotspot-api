package name.matco.hotspot.services.datasource.mocks;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import name.matco.hotspot.services.datasource.ConnectionProvider;

public class ConnectionProviderMock implements ConnectionProvider {

	private ComboPooledDataSource cpds;

	public ConnectionProviderMock() throws PropertyVetoException {
		cpds = new ComboPooledDataSource();
		cpds.setDriverClass("org.h2.Driver");
		//generate unique database name for each instance
		final UUID uuid = UUID.randomUUID();
		cpds.setJdbcUrl(String.format("jdbc:h2:mem:test%s;MODE=MYSQL", uuid));
	}

	@Override
	public Connection getConnection() throws SQLException {
		return cpds.getConnection();
	}

}
