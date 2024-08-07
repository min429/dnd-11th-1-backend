package com.dnd.accompany.domain.accompany.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dnd.accompany.domain.accompany.entity.AccompanyBoard;
import com.dnd.accompany.domain.accompany.entity.AccompanyImage;
import com.dnd.accompany.domain.accompany.infrastructure.AccompanyImageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccompanyImageService {
	private final AccompanyImageRepository accompanyImageRepository;

	@Transactional
	public void save(AccompanyBoard accompanyBoard, List<String> imageUrls) {
		List<AccompanyImage> images = imageUrls.stream()
			.map(imageUrl -> AccompanyImage.builder()
				.accompanyBoard(accompanyBoard)
				.imageUrl(imageUrl)
				.build())
			.collect(Collectors.toList());
		accompanyImageRepository.saveAll(images);
	}

	@Transactional
	public void deleteByBoardId(Long boardId) {
		accompanyImageRepository.deleteByAccompanyBoardId(boardId);
	}
}
