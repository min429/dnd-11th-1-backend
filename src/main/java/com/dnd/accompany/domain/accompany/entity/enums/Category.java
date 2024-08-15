package com.dnd.accompany.domain.accompany.entity.enums;

public enum Category {
	FULL("전체동행"), PART("부분동행"), LODGING("숙박공유"), TOUR("투어동행"), MEAL("식사동행");

	private String description;

	Category(String description) {
		this.description = description;
	}
}
