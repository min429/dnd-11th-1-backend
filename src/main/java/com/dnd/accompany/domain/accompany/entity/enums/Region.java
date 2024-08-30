package com.dnd.accompany.domain.accompany.entity.enums;

public enum Region {
	SEOUL("서울"),
	GYEONGGI_INCHEON("경기·인천"),
	CHUNGCHEONG_DAEJEON_SEJONG("충청·대전·세종"),
	GANGWON("강원"),
	JEOLLA_GWANGJU("전라·광주"),
	GYEONGSANG_DAEGU_ULSAN("경상·대구·울산"),
	BUSAN("부산"),
	JEJU("제주");

	private String description;

	Region(String description) {
		this.description = description;
	}

	public String description() {
		return description;
	}

	public static Region from(String keyword) {
		for (Region region : Region.values()) {
			if (region.description().contains(keyword)) {
				return region;
			}
		}
		return null;
	}
}
