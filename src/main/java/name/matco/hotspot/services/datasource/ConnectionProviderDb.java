package name.matco.hotspot.services.datasource;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import name.matco.hotspot.api.App;

public class ConnectionProviderDb implements ConnectionProvider {

	private ComboPooledDataSource cpds;

	public ConnectionProviderDb() throws PropertyVetoException {
		final Properties properties = App.getAppProperties();
		cpds = new ComboPooledDataSource();
		cpds.setDriverClass("com.mysql.cj.jdbc.Driver");
		cpds.setJdbcUrl(String.format("jdbc:mysql://%s:%s/%s?characterEncoding=utf-8&useUnicode=true&useLegacyDatetimeCode=false&serverTimezone=GMT&useSSL=false", properties.getProperty("db.host"), properties.getProperty("db.port"), properties.getProperty("db.name")));
		cpds.setUser(properties.getProperty("db.user"));
		cpds.setPassword(properties.getProperty("db.password"));
	}

	public Connection getConnection() throws SQLException {
		return cpds.getConnection();
	}

}
