package name.matco.hotspot.api.security.tokens;

import java.util.Optional;

public interface RevokedTokenRepository {

	Optional<RevokedToken> getByToken(String token);

	void save(RevokedToken revokedToken);
}
