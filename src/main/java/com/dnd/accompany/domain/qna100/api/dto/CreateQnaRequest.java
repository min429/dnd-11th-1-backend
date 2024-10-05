package com.dnd.accompany.domain.qna100.api.dto;

import static java.util.stream.Collectors.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.dnd.accompany.domain.qna100.entity.Qna;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateQnaRequest(
	List<@Size(max = 2000) String> questions,
	List<@Size(max = 2000) String> answers
) {

	public List<Qna> toEntityList(Long userId) {
		return IntStream.range(0, questions.size())
			.mapToObj(i -> Qna.builder()
				.userId(userId)
				.question(questions.get(i))
				.answer(answers.get(i))
				.build())
			.collect(toList());
	}
}
