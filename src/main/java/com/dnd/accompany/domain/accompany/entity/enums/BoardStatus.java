package com.dnd.accompany.domain.accompany.entity.enums;

public enum BoardStatus {
	RECRUITING("모집중"),
	RECRUITMENT_COMPLETED("모집완료"),
	REMOVED("게시글 삭제됨");

	private String description;

	BoardStatus(String description) {
		this.description = description;
	}
}
