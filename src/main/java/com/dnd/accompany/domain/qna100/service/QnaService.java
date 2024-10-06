package com.dnd.accompany.domain.qna100.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dnd.accompany.domain.qna100.api.dto.CreateQnaRequest;
import com.dnd.accompany.domain.qna100.entity.Qna100;
import com.dnd.accompany.domain.qna100.infrastructure.QnaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QnaService {

	private final QnaRepository qnaRepository;

	@Transactional
	public void saveAll(Long userId, CreateQnaRequest request){
		List<Qna100> qna100s = request.toEntityList(userId, request.qnas());
		qnaRepository.deleteAllByUserId(userId);
		qnaRepository.saveAll(qna100s);
	}
}
