package com.dnd.accompany.domain.accompany.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateAccompanyRequest(
	@NotNull Long boardId,
	@NotNull @Size(max = 1500) String introduce,
	@NotNull String chatLink
) {
}
