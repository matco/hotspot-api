package name.matco.hotspot.repositories.mocks;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import name.matco.hotspot.api.security.tokens.RevokedToken;
import name.matco.hotspot.api.security.tokens.RevokedTokenRepository;

public class RevokedTokenRepositoryMock implements RevokedTokenRepository {

	private final Set<RevokedToken> revokedTokens = new HashSet<>();

	@Override
	public Optional<RevokedToken> getByToken(final String token) {
		return revokedTokens.stream().filter(t -> t.token().equals(token)).findAny();
	}

	@Override
	public void save(final RevokedToken revokedToken) {
		revokedTokens.add(revokedToken);
	}

}
