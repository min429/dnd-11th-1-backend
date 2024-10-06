package com.dnd.accompany.domain.qna100.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dnd.accompany.domain.auth.dto.jwt.JwtAuthentication;
import com.dnd.accompany.domain.qna100.api.dto.CreateQnaRequest;
import com.dnd.accompany.domain.qna100.service.QnaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "QnA 100")
@RequiredArgsConstructor
@RequestMapping("api/v1/qna100s")
@RestController
public class QnaController {

	private final QnaService qnaService;

	@Operation(summary = "백문백답 모두 저장(교체)")
	@PutMapping
	public ResponseEntity<Void> saveAll(
		@RequestBody @Valid CreateQnaRequest request,
		@AuthenticationPrincipal JwtAuthentication user) {
		qnaService.saveAll(user.getId(), request);
		return ResponseEntity.ok().build();
	}
}
