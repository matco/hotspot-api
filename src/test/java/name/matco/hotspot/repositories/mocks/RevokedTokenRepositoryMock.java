package name.matco.hotspot.repositories.mocks;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import name.matco.hotspot.api.security.tokens.RevokedToken;
import name.matco.hotspot.api.security.tokens.RevokedTokenRepository;

public class RevokedTokenRepositoryMock implements RevokedTokenRepository {

	private Set<RevokedToken> revokedTokens = new HashSet<>();

	@Override
	public Optional<RevokedToken> getByToken(String token) {
		return revokedTokens.stream().filter(t -> t.getToken().equals(token)).findAny();
	}

	@Override
	public boolean save(RevokedToken revokedToken) {
		return revokedTokens.add(revokedToken);
	}

}
