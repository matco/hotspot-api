package name.matco.hotspot.api.security.tokens;

import java.io.UnsupportedEncodingException;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import name.matco.hotspot.model.User;

public class JWTService {

	private final static String JWT_ISSUER = "api";
	private final static String JWT_SECRET = "PbgPpNArhjMm6acR8Du4WqJstsC9cqmv";//RandomStringUtils.randomAlphanumeric(32);

	private final Algorithm algorithm;
	private final JWTVerifier verifier;

	public JWTService() throws IllegalArgumentException, UnsupportedEncodingException {
		algorithm = Algorithm.HMAC256(JWT_SECRET);
		verifier = JWT.require(algorithm).withIssuer(JWT_ISSUER).build();
	}

	public String create(final User user) {
		return JWT.create().withIssuer(JWT_ISSUER).withClaim("user", user.getEmail()).sign(algorithm);
	}

	public DecodedJWT verify(final String token) throws JWTVerificationException {
		return verifier.verify(token);
	}

}
