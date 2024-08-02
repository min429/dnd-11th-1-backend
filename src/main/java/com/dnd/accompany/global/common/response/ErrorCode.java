package com.dnd.accompany.global.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

	BAD_REQUEST(MatripConstant.BAD_REQUEST, "GLOBAL-400", "잘못된 요청입니다."),
	ACCESS_DENIED(MatripConstant.FORBIDDEN, "GLOBAL-403", "접근 권한이 없습니다."),
	INTERNAL_SERVER(MatripConstant.INTERNAL_SERVER_ERROR, "GLOBAL-500", "서버 내부 오류입니다."),

	// ---- 유저 ---- //
	USER_NOT_FOUND(MatripConstant.NOT_FOUND, "USER-001", "존재하지 않는 회원입니다."),

	// ---- 토큰 ---- //
	INVALID_TOKEN(MatripConstant.UNAUTHORIZED, "TOKEN-001", "유효하지 않은 토큰입니다."),
	TOKEN_EXPIRED(MatripConstant.UNAUTHORIZED, "TOKEN-002", "만료된 토큰입니다."),
	REFRESH_TOKEN_NOT_FOUND(MatripConstant.NOT_FOUND, "TOKEN-003", "리프레시 토큰을 찾을 수 없습니다."),
	REFRESH_TOKEN_EXPIRED(MatripConstant.UNAUTHORIZED, "TOKEN-004", "만료된 리프레시 토큰입니다."),

	// ---- 로그인 ---- //
	INVALID_PROVIDER(MatripConstant.BAD_REQUEST, "LOGIN-001", "유효하지 않은 로그인 수단입니다."),
	INVALID_OAUTH_TOKEN(MatripConstant.BAD_REQUEST, "LOGIN-002", "유효하지 않은 OAuth 토큰입니다."),

	// ---- 네트워크 ---- //
	HTTP_CLIENT_REQUEST_FAILED(MatripConstant.INTERNAL_SERVER_ERROR, "NETWORK-001", "서버 요청에 실패하였습니다.");

	private final Integer status;
	private final String code;
	private final String message;
}
