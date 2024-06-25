package name.matco.hotspot.services.datasource;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

public interface ConnectionProvider {

	DataSource getDataSource();

	Connection getConnection() throws SQLException;

}
