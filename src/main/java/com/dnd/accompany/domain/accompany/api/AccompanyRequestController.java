package com.dnd.accompany.domain.accompany.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dnd.accompany.domain.accompany.api.dto.PageRequest;
import com.dnd.accompany.domain.accompany.api.dto.PageResponse;
import com.dnd.accompany.domain.accompany.api.dto.ReadAccompanyResponse;
import com.dnd.accompany.domain.accompany.api.dto.ReceivedAccompany;
import com.dnd.accompany.domain.accompany.api.dto.SendedAccompany;
import com.dnd.accompany.domain.accompany.service.AccompanyRequestService;
import com.dnd.accompany.domain.accompany.service.AccompanyServiceFacade;
import com.dnd.accompany.domain.auth.dto.jwt.JwtAuthentication;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "AccompanyRequest")
@RestController
@RequestMapping("api/v1/accompany/requests")
@RequiredArgsConstructor
public class AccompanyRequestController {

	private final AccompanyServiceFacade accompanyServiceFacade;
	private final AccompanyRequestService accompanyRequestService;

	@Operation(summary = "보낸 동행 신청서 목록 조회")
	@PostMapping("/sended")
	public ResponseEntity<PageResponse<SendedAccompany>> readAllSended(
		@RequestBody @Valid PageRequest request,
		@AuthenticationPrincipal JwtAuthentication user) {
		return ResponseEntity.ok(accompanyRequestService.getAllSendedAccompanies(request, user.getId()));
	}

	@Operation(summary = "받은 동행 신청서 목록 조회")
	@PostMapping("/received")
	public ResponseEntity<PageResponse<ReceivedAccompany>> readAllReceived(
		@RequestBody @Valid PageRequest request,
		@AuthenticationPrincipal JwtAuthentication user) {
		return ResponseEntity.ok(accompanyRequestService.getAllReceivedAccompanies(request, user.getId()));
	}

	@Operation(summary = "동행 신청서 조회")
	@GetMapping("/{id}")
	public ResponseEntity<ReadAccompanyResponse> read(
		@PathVariable(name = "id") Long requestId,
		@AuthenticationPrincipal JwtAuthentication user) {
		return ResponseEntity.ok(accompanyServiceFacade.getRequestDetail(requestId, user.getId()));
	}

	@Operation(summary = "동행 신청 취소")
	@PostMapping("/{id}")
	public ResponseEntity<Void> cancel(
		@PathVariable("id") Long requestId,
		@AuthenticationPrincipal JwtAuthentication user) {
		accompanyRequestService.deleteRequest(requestId, user.getId());
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "동행 신청 수락")
	@PostMapping("/approve/{id}")
	public ResponseEntity<Void> approve(
		@PathVariable("id") Long requestId,
		@AuthenticationPrincipal JwtAuthentication user) {
		accompanyServiceFacade.approveRequest(requestId, user.getId());
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "동행 신청 거절")
	@PostMapping("/decline/{id}")
    public ResponseEntity<Void> decline(
        @PathVariable("id") Long requestId,
        @AuthenticationPrincipal JwtAuthentication user) {
		accompanyServiceFacade.declineRequest(requestId, user.getId());
		return ResponseEntity.ok().build();
	}
}
