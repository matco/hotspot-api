package name.matco.hotspot.services.datasource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.zaxxer.hikari.HikariDataSource;

import name.matco.hotspot.api.App;

public class ConnectionProviderDb implements ConnectionProvider {

	private HikariDataSource ds;

	public ConnectionProviderDb() {
		final Properties properties = App.getAppProperties();
		ds = new HikariDataSource();
		ds.setJdbcUrl(String.format("jdbc:mariadb://%s:%s/%s?characterEncoding=utf-8&useUnicode=true&useLegacyDatetimeCode=false&serverTimezone=GMT&useSSL=false", properties.getProperty("db.host"), properties.getProperty("db.port"), properties.getProperty("db.name")));
		ds.setUsername(properties.getProperty("db.user"));
		ds.setPassword(properties.getProperty("db.password"));
	}

	public Connection getConnection() throws SQLException {
		return ds.getConnection();
	}

}
