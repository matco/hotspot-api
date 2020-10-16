package name.matco.hotspot.api.security.tokens;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.inject.Inject;

import name.matco.hotspot.services.datasource.ConnectionProvider;

public class RevokedTokenCleaner {

	private static final int TOKEN_CHECK_INTERVAL = 60; //in seconds

	@Inject
	private ConnectionProvider connectionProvider;

	private ScheduledExecutorService cleaner;

	public RevokedTokenCleaner() {
		cleaner = Executors.newSingleThreadScheduledExecutor();

		final Runnable clean = () -> {
			try(
					Connection connection = connectionProvider.getConnection();
					PreparedStatement ps = connection.prepareStatement("delete from revoked_token where expiration_date > now()")) {
				final int result = ps.executeUpdate();
				if(result > 0) {
					Logger.getLogger(ScheduledExecutorService.class.getName()).log(Level.INFO, "Cleaner removed " + result + " revoked token(s).");
				}
			}
			catch(final SQLException ex) {
				ex.printStackTrace();
			}
		};

		cleaner.scheduleAtFixedRate(clean, 0, TOKEN_CHECK_INTERVAL, TimeUnit.SECONDS);
		Logger.getLogger(ScheduledExecutorService.class.getName()).log(Level.INFO, "Revoked token cleaner started.");
	}

	public void stop() {
		cleaner.shutdown();
		Logger.getLogger(ScheduledExecutorService.class.getName()).log(Level.INFO, "Revoked token cleaner stopped.");
	}
}
