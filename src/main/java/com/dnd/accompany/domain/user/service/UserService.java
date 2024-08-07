package com.dnd.accompany.domain.user.service;

import com.dnd.accompany.domain.auth.dto.AuthUserInfo;
import com.dnd.accompany.domain.auth.oauth.dto.OAuthUserInfo;
import com.dnd.accompany.domain.user.entity.User;
import com.dnd.accompany.domain.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public AuthUserInfo getOrRegister(OAuthUserInfo oauthUserInfo) {
        User user = userRepository
                .findUserByProviderAndOauthId(oauthUserInfo.getProvider(), oauthUserInfo.getOauthId())
                .orElseGet(() -> registerUser(oauthUserInfo));

        return new AuthUserInfo(user.getId());
    }

    @Transactional
    public User registerUser(OAuthUserInfo oauthUserInfo) {
        return userRepository.save(User.of(
                oauthUserInfo.getNickname(),
                oauthUserInfo.getProvider(),
                oauthUserInfo.getOauthId(),
                oauthUserInfo.getProfileImageUrl()
        ));
    }
}
