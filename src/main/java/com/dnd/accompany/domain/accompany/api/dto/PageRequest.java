package com.dnd.accompany.domain.accompany.api.dto;

import java.time.LocalDateTime;

import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.lang.Nullable;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.core.types.dsl.StringTemplate;

import io.swagger.v3.oas.annotations.media.Schema;

public record PageRequest(
	@Schema(nullable = true) String cursor,
	@Schema(nullable = true) Integer size
) {

	public PageRequest {
		if (size == null) {
			size = 10;
		}
	}

	public static BooleanBuilder cursorCondition(String cursor, DateTimePath<LocalDateTime> updatedAt, NumberPath<Long> id) {
		BooleanBuilder builder = new BooleanBuilder();

		if (cursor == null) return builder;

		StringTemplate nextCursor = Expressions.stringTemplate(
			"CONCAT(DATE_FORMAT({0}, '%Y%m%d%H%i%S'), LPAD(CAST({1} AS STRING), 6, '0'))",
			updatedAt,
			id
		);
		builder.and(nextCursor.lt(cursor));

		return builder;
	}
}
