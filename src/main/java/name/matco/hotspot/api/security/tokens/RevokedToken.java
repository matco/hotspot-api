package name.matco.hotspot.api.security.tokens;

import java.time.Instant;

import jakarta.validation.constraints.NotNull;

public class RevokedToken {

	@NotNull
	private final String token;

	@NotNull
	private final Instant expirationDate;

	@NotNull
	private final long userFk;

	public RevokedToken(final String token, final Instant expirationDate, final long userFk) {
		this.token = token;
		this.expirationDate = expirationDate;
		this.userFk = userFk;
	}

	public final String getToken() {
		return token;
	}

	public final long getUserFk() {
		return userFk;
	}

	public final Instant getExpirationDate() {
		return expirationDate;
	}

}
