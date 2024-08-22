package com.dnd.accompany.domain.accompany.api;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dnd.accompany.domain.accompany.api.dto.AccompanyBoardThumbnail;
import com.dnd.accompany.domain.accompany.api.dto.CreateAccompanyBoardRequest;
import com.dnd.accompany.domain.accompany.api.dto.CreateAccompanyBoardResponse;
import com.dnd.accompany.domain.accompany.api.dto.CreateAccompanyRequest;
import com.dnd.accompany.domain.accompany.api.dto.PageResponse;
import com.dnd.accompany.domain.accompany.api.dto.ReadAccompanyBoardResponse;
import com.dnd.accompany.domain.accompany.entity.enums.Region;
import com.dnd.accompany.domain.accompany.service.AccompanyBoardService;
import com.dnd.accompany.domain.accompany.service.AccompanyRequestService;
import com.dnd.accompany.domain.accompany.service.AccompanyServiceFacade;
import com.dnd.accompany.domain.auth.dto.jwt.JwtAuthentication;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "AccompanyBoard")
@RestController
@RequestMapping("api/v1/accompany/boards")
@RequiredArgsConstructor
public class AccompanyBoardController {

	private final AccompanyBoardService accompanyBoardService;
	private final AccompanyRequestService accompanyRequestService;
	private final AccompanyServiceFacade accompanyServiceFacade;

	@Operation(summary = "동행글 생성")
	@PostMapping
	public ResponseEntity<CreateAccompanyBoardResponse> create(
		@AuthenticationPrincipal JwtAuthentication user,
		@RequestBody @Valid CreateAccompanyBoardRequest request) {
		return ResponseEntity.ok(accompanyServiceFacade.createBoard(user.getId(), request));
	}

	@Operation(summary = "동행글 목록 조회")
	@GetMapping
	public ResponseEntity<PageResponse<AccompanyBoardThumbnail>> readAll(
		@PageableDefault(
			sort = {"updatedAt", "createdAt"},
			direction = Sort.Direction.DESC) Pageable pageable,
		@RequestParam(value = "region", required = false) Region region) {
		return ResponseEntity.ok(accompanyBoardService.getAllBoards(pageable, region));
	}

	@Operation(summary = "동행글 상세 조회")
	@GetMapping("/{id}")
	public ResponseEntity<ReadAccompanyBoardResponse> read(@PathVariable Long id) {
		return ResponseEntity.ok(accompanyServiceFacade.getBoardDetail(id));
	}

	@Operation(summary = "동행 신청")
	@PostMapping("/request")
	public ResponseEntity<Void> request(
		@AuthenticationPrincipal JwtAuthentication user,
		@RequestBody @Valid CreateAccompanyRequest request) {
		accompanyRequestService.save(user.getId(), request);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "동행글 삭제(동행 기록까지 삭제)")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> delete(
		@AuthenticationPrincipal JwtAuthentication user,
		@PathVariable Long id) {
		accompanyServiceFacade.deleteBoard(user.getId(), id);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "동행글 삭제(게시글만 삭제)")
	@PostMapping("/remove/{id}")
	public ResponseEntity<Void> remove(
		@AuthenticationPrincipal JwtAuthentication user,
		@PathVariable Long id) {
		accompanyServiceFacade.removeBoard(user.getId(), id);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "동행 기록 조회")
	@GetMapping("/records")
	public ResponseEntity<PageResponse<AccompanyBoardThumbnail>> readAllRecords(
		@PageableDefault(
			sort = {"createdAt"},
			direction = Sort.Direction.DESC) Pageable pageable,
		@AuthenticationPrincipal JwtAuthentication user) {
		return ResponseEntity.ok(accompanyBoardService.getAllRecords(pageable, user.getId()));
	}
}
