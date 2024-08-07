package com.dnd.accompany.domain.accompany.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dnd.accompany.domain.accompany.entity.AccompanyBoard;
import com.dnd.accompany.domain.accompany.entity.AccompanyTag;
import com.dnd.accompany.domain.accompany.infrastructure.AccompanyTagRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccompanyTagService {
	private final AccompanyTagRepository accompanyTagRepository;

	@Transactional
	public void save(AccompanyBoard accompanyBoard, List<String> tagNames) {
		List<AccompanyTag> tags = tagNames.stream()
			.map(tagName -> AccompanyTag.builder()
				.accompanyBoard(accompanyBoard)
				.name(tagName)
				.build())
			.collect(Collectors.toList());
		accompanyTagRepository.saveAll(tags);
	}

	@Transactional
	public void deleteByBoardId(Long boardId) {
		accompanyTagRepository.deleteByAccompanyBoardId(boardId);
	}
}
