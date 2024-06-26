package name.matco.hotspot.api.security.tokens;

import java.time.Instant;

import jakarta.validation.constraints.NotNull;

public record RevokedToken(
	@NotNull String token,
	@NotNull Instant expirationDate,
	@NotNull long userFk
) {
	//nothing to do here
}
