package com.dnd.accompany.domain.accompany.api.dto;

import java.util.List;

public record PageResponse<T>(
	boolean hasNext,
	List<T> data
) {
}