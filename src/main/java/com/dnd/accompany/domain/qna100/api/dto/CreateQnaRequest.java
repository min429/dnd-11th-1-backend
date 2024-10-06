package com.dnd.accompany.domain.qna100.api.dto;

import static java.util.stream.Collectors.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.dnd.accompany.domain.qna100.entity.Qna100;

import jakarta.validation.constraints.NotNull;

public record CreateQnaRequest(
	@NotNull List<Qna> qnas
) {

	public List<Qna100> toEntityList(Long userId, List<Qna> qnas) {
		return qnas.stream()
			.map(qna -> Qna100.builder()
				.userId(userId)
				.question(qna.questions())
				.answer(qna.answers())
				.build())
			.collect(toList());
	}
}
