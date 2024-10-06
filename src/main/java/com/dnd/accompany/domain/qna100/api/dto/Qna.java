package com.dnd.accompany.domain.qna100.api.dto;

import jakarta.validation.constraints.Size;

public record Qna(
	@Size(max = 2000) String questions,
	@Size(max = 2000) String answers
) {
}
