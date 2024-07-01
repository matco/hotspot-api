package name.matco.hotspot.helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import name.matco.hotspot.services.datasource.ConnectionProvider;

public class InitDatabase {

	private static final Logger LOGGER = LogManager.getLogger(InitDatabase.class.getName());

	private static void importFile(final Connection connection, final InputStream stream) {
		try(
			Statement statement = connection.createStatement();
			//specify charset manually because this test may be launched from Maven and OS may not have utf-8 set as the default charset
			BufferedReader input = new BufferedReader(new InputStreamReader(stream, Charset.forName("utf-8")))
		) {
			final StringBuilder sql = new StringBuilder();
			String line = null;
			boolean comment = false;
			int counter = 0;
			try {
				while((line = input.readLine()) != null) {
					//empty lines
					//inline comment
					if(line.trim().isEmpty() || line.startsWith("--")) {
						continue;
					}
					//start multiline comment
					if(line.startsWith("/*") && !line.startsWith("/*!")) {
						comment = true;
					}
					//stop multiline comment
					if(comment && line.endsWith("*/")) {
						comment = false;
						continue;
					}
					if(!comment) {
						sql.append(line.trim());
						if(line.endsWith(";")) {
							String query = sql.toString();
							//remove features not supported by test database
							query = StringUtils.replace(query, "engine=InnoDB", "");
							query = StringUtils.replace(query, "engine=Memory", "");
							query = StringUtils.replace(query, "if not exists", "");
							query = StringUtils.replace(query, "collate=utf8_bin", "");
							query = StringUtils.replace(query, "on update current_timestamp", "");
							//execute query
							LOGGER.debug("Executing query {}", query);
							statement.execute(query);
							counter++;
							//reset builder
							sql.delete(0, sql.length());
						}
					}
				}
			}
			catch(final Exception e) {
				LOGGER.error("Error with current query: {}", sql.toString());
				e.printStackTrace();
			}
			LOGGER.info("{} queries done", counter);
		}
		catch(SQLException | IOException e) {
			LOGGER.catching(Level.ERROR, e);
		}
	}

	public static void createDatabase(final ConnectionProvider connectionProvider) throws SQLException {
		LOGGER.info("Creating database [begin]");
		final long t = System.currentTimeMillis();

		try(Connection connection = connectionProvider.getConnection()) {
			importFile(connection, InitDatabase.class.getResourceAsStream("/create_tables.sql"));
		}

		LOGGER.info("Creating database [end] ({}ms)", System.currentTimeMillis() - t);
	}

}
