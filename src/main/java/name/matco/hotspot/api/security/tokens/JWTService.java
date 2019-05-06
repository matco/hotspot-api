package name.matco.hotspot.api.security.tokens;

import java.time.ZonedDateTime;
import java.util.Date;

import javax.inject.Inject;

import org.apache.commons.lang3.RandomStringUtils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import name.matco.hotspot.model.User;

public class JWTService {

	@Inject
	private RevokedTokenRepository revokedTokenRepository;

	private final static String JWT_ISSUER = "api";
	private final static String JWT_SECRET = RandomStringUtils.randomAlphanumeric(32);
	private final static long JWT_EXPIRATION_VALIDITY_DAYS = 30;

	private final Algorithm algorithm;
	private final JWTVerifier verifier;

	public JWTService() throws IllegalArgumentException {
		algorithm = Algorithm.HMAC256(JWT_SECRET);
		verifier = JWT.require(algorithm).withIssuer(JWT_ISSUER).build();
	}

	public String create(final User user) {
		final var expirationDate = ZonedDateTime.now().plusDays(JWT_EXPIRATION_VALIDITY_DAYS);
		return JWT.create()
				.withIssuer(JWT_ISSUER)
				.withClaim("user", user.getEmail())
				.withExpiresAt(Date.from(expirationDate.toInstant()))
				.sign(algorithm);
	}

	public DecodedJWT verify(final String token) throws JWTVerificationException, InvalidToken {
		//check among revoken token
		if(revokedTokenRepository.getByToken(token).isPresent()) {
			throw new InvalidToken();
		}
		return verifier.verify(token);
	}

	public boolean revoke(final User user, final String token) {
		final DecodedJWT jwt = verifier.verify(token);
		final RevokedToken revokedToken = new RevokedToken(token, jwt.getExpiresAt().toInstant(), user.getPk());
		revokedTokenRepository.save(revokedToken);
		return true;
	}

}
