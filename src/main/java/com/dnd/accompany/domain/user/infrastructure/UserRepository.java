package com.dnd.accompany.domain.user.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dnd.accompany.domain.user.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByProviderAndOauthId(String provider, String oauthId);
}
