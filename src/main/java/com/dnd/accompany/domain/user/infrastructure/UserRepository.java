package com.dnd.accompany.domain.user.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dnd.accompany.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
