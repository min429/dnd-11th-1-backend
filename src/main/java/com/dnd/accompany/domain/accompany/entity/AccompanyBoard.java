package com.dnd.accompany.domain.accompany.entity;

import static com.dnd.accompany.domain.accompany.entity.enums.BoardStatus.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.dnd.accompany.domain.accompany.entity.enums.BoardStatus;
import com.dnd.accompany.domain.accompany.entity.enums.Category;
import com.dnd.accompany.domain.accompany.entity.enums.PreferredAge;
import com.dnd.accompany.domain.accompany.entity.enums.PreferredGender;
import com.dnd.accompany.domain.accompany.entity.enums.Region;
import com.dnd.accompany.domain.common.entity.TimeBaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "accompany_boards")
@SQLRestriction("deleted = false")
@SQLDelete(sql = "UPDATE accompany_boards SET deleted = true WHERE id = ?")
public class AccompanyBoard extends TimeBaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String title;

	@Column(
		length = 2000,
		nullable = false
	)
	private String content;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Region region;

	@Column(nullable = false)
	private LocalDateTime startDate;

	@Column(nullable = false)
	private LocalDateTime endDate;

	@Column(nullable = false)
	private Long headCount;

	@Column(nullable = false)
	private Long capacity;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private BoardStatus boardStatus;

	@Builder.Default
	@ElementCollection(targetClass = Category.class)
	@Enumerated(EnumType.STRING)
	private List<Category> categories = new ArrayList<>();

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private PreferredAge preferredAge;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private PreferredGender preferredGender;

	private boolean deleted = Boolean.FALSE;

	public void remove(){
		this.boardStatus = REMOVED;
	}

	public void addHeadCount(){
		headCount++;

		if(headCount == capacity){
			boardStatus = RECRUITMENT_COMPLETED;
		}
	}
}
