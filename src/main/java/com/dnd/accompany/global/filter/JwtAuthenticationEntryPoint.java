package com.dnd.accompany.global.filter;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import java.io.IOException;

import org.apache.commons.lang3.CharEncoding;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.dnd.accompany.global.common.response.ErrorCode;
import com.dnd.accompany.global.common.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JwtAuthenticationFilter에서 사용자 인증에 실패한 경우 발생하는 401 예외를 핸들링합니다.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private static final String ERROR_LOG_MESSAGE = "[ERROR] {} : {}";

	private final ObjectMapper objectMapper;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException authException) throws IOException {
		log.info(ERROR_LOG_MESSAGE, authException.getClass().getSimpleName(), authException.getMessage());
		response.setStatus(UNAUTHORIZED.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding(CharEncoding.UTF_8);
		response.getWriter()
			.write(objectMapper.writeValueAsString(
				ErrorResponse.from(ErrorCode.ACCESS_DENIED)
			));
	}
}
