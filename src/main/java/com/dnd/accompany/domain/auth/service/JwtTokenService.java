package com.dnd.accompany.domain.auth.service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.dnd.accompany.domain.auth.exception.ExpiredTokenException;
import com.dnd.accompany.domain.auth.exception.InvalidTokenException;
import com.dnd.accompany.global.common.response.ErrorCode;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenService {

	private static final long MILLI_SECOND = 1000L;

	private final String issuer;
	private final String secretKey;
	private final long accessTokenExpirySeconds;

	public JwtTokenService(
		@Value("${jwt.issuer}") String issuer,
		@Value("${jwt.secret-key}") String secretKey,
		@Value("${jwt.expiry-seconds.access-token}") long accessTokenExpirySeconds
	) {
		this.issuer = issuer;
		this.secretKey = secretKey;
		this.accessTokenExpirySeconds = accessTokenExpirySeconds;
	}

	public String getAccessToken(Long userId) {
		Map<String, Object> claims = Map.of("userId", userId);
		return this.createAccessToken(claims);
	}

	public String createAccessToken(Map<String, Object> claims) {
		Date now = new Date();
		Date expiredDate = new Date(now.getTime() + accessTokenExpirySeconds * MILLI_SECOND);

		return Jwts.builder()
			.setIssuer(issuer)
			.setClaims(claims)
			.setIssuedAt(now)
			.setExpiration(expiredDate)
			.signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
			.compact();
	}

	public Claims getClaims(String token) {
		return Jwts.parserBuilder()
			.setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
			.build()
			.parseClaimsJws(token)
			.getBody();
	}

	public void validateToken(String token) {
		try {
			Jwts.parserBuilder()
				.setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
				.build()
				.parseClaimsJws(token);
		} catch (ExpiredJwtException e) {
			throw new ExpiredTokenException(ErrorCode.TOKEN_EXPIRED);
		} catch (JwtException | IllegalArgumentException e) {
			throw new InvalidTokenException(ErrorCode.INVALID_TOKEN);
		}
	}
}
