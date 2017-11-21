package name.matco.hotspot.services.datasource;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionProvider {

	Connection getConnection() throws SQLException;

}
