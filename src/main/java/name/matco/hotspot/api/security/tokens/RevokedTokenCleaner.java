package name.matco.hotspot.api.security.tokens;

import java.time.ZonedDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.inject.Inject;

import org.jooq.DSLContext;

import static name.matco.hotspot.model.jooq.Tables.REVOKED_TOKEN;

public class RevokedTokenCleaner {

	private static final int TOKEN_CHECK_INTERVAL = 60; //in seconds

	@Inject
	private DSLContext dsl;

	private final ScheduledExecutorService cleaner;

	public RevokedTokenCleaner() {
		cleaner = Executors.newSingleThreadScheduledExecutor();

		final Runnable clean = () -> {
			final var result = dsl.deleteFrom(REVOKED_TOKEN).where(REVOKED_TOKEN.EXPIRATION_DATE.greaterThan(ZonedDateTime.now())).execute();
			if(result > 0) {
				Logger.getLogger(ScheduledExecutorService.class.getName()).log(Level.INFO, "Cleaner removed " + result + " revoked token(s).");
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
