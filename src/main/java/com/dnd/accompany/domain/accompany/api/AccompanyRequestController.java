package com.dnd.accompany.domain.accompany.api;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.dnd.accompany.domain.accompany.api.dto.PageResponse;
import com.dnd.accompany.domain.accompany.api.dto.ReadAccompanyResponse;
import com.dnd.accompany.domain.accompany.api.dto.SendedAccompany;
import com.dnd.accompany.domain.accompany.service.AccompanyRequestService;
import com.dnd.accompany.domain.accompany.service.AccompanyServiceFacade;
import com.dnd.accompany.domain.auth.dto.jwt.JwtAuthentication;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "AccompanyRequest")
@RestController
@RequestMapping("api/v1/accompany/requests")
@RequiredArgsConstructor
public class AccompanyRequestController {

	private final AccompanyServiceFacade accompanyServiceFacade;
	private final AccompanyRequestService accompanyRequestService;

	@Operation(summary = "보낸 동행 신청서 목록 조회")
	@GetMapping("/sended")
	public ResponseEntity<PageResponse<SendedAccompany>> readAllSended(
		@PageableDefault(
			sort = {"updatedAt", "createdAt"},
			direction = Sort.Direction.DESC) Pageable pageable,
		@AuthenticationPrincipal JwtAuthentication user) {
		return ResponseEntity.ok(accompanyRequestService.getAllSendedAccompanies(pageable, user.getId()));
	}

	@Operation(summary = "받은 동행 신청서 목록 조회")
	@GetMapping("/received")
	public ResponseEntity<PageResponse<SendedAccompany>> readAllReceived(
		@PageableDefault(
			sort = {"updatedAt", "createdAt"},
			direction = Sort.Direction.DESC) Pageable pageable,
		@AuthenticationPrincipal JwtAuthentication user) {
		return ResponseEntity.ok(accompanyRequestService.getAllSendedAccompanies(pageable, user.getId()));
	}

	@Operation(summary = "동행 신청서 조회")
	@GetMapping("/{id}")
	public ResponseEntity<ReadAccompanyResponse> read(
		@PathVariable(name = "id") Long requestId,
		@AuthenticationPrincipal JwtAuthentication user) {
		return ResponseEntity.ok(accompanyServiceFacade.getRequestDetail(requestId, user.getId()));
	}
}
