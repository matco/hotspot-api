package name.matco.hotspot.helpers;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jooq.DSLContext;

public class InitDatabase {

	private static final Logger LOGGER = LogManager.getLogger(InitDatabase.class.getName());

	public static void createDatabase(final DSLContext context) throws IOException {
		LOGGER.info("Creating database [begin]");
		final long t = System.currentTimeMillis();

		final var script = InitDatabase.class.getResourceAsStream("/create_tables.sql");
		final var sql = new String(script.readAllBytes(), StandardCharsets.UTF_8);

		context.execute(sql);

		LOGGER.info("Creating database [end] ({}ms)", System.currentTimeMillis() - t);
	}

}
