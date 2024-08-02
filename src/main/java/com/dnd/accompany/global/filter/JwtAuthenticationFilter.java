package com.dnd.accompany.global.filter;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import java.io.IOException;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.dnd.accompany.domain.auth.dto.jwt.JwtAuthenticationToken;
import com.dnd.accompany.domain.auth.service.TokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JWT 인증 필터로, accessToken을 검증합니다.
 * 토큰이 유효할 경우 유효한 인증 객체를 SecurityContextHolder에 넣어줍니다.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private static final String BEARER_TYPE = "Bearer";
	private final TokenService tokenService;

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		String accessToken = getAccessToken(request);

		if (accessToken != null) {
			JwtAuthenticationToken authentication = tokenService.getAuthenticationByAccessToken(accessToken);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}

		filterChain.doFilter(request, response);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		return request.getRequestURI().endsWith("tokens");
	}

	private String getAccessToken(HttpServletRequest request) {
		String authHeaderValue = request.getHeader(AUTHORIZATION);
		if (authHeaderValue != null) {
			return extractToken(authHeaderValue);
		}

		return null;
	}

	private String extractToken(String authHeaderValue) {
		if (authHeaderValue.toLowerCase().startsWith(BEARER_TYPE.toLowerCase())) {
			return authHeaderValue.substring(BEARER_TYPE.length()).trim();
		}

		return null;
	}
}
