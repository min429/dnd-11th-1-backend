package com.dnd.accompany.domain.user.service;

import com.dnd.accompany.domain.auth.dto.AuthUserInfo;
import com.dnd.accompany.domain.auth.dto.DeleteUserRequest;
import com.dnd.accompany.domain.auth.oauth.dto.OAuthUserInfo;
import com.dnd.accompany.domain.user.entity.User;
import com.dnd.accompany.domain.user.entity.UserProfile;
import com.dnd.accompany.domain.user.infrastructure.UserProfileRepository;
import com.dnd.accompany.domain.user.infrastructure.UserRepository;
import com.dnd.accompany.global.common.exception.NotFoundException;
import com.dnd.accompany.global.common.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;

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

    @Transactional
    public void delete(Long userId, DeleteUserRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        userProfileRepository.deleteById(userId);
        user.delete(request.reason());
    }
}
