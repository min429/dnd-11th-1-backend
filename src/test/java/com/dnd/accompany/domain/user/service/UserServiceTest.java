package com.dnd.accompany.domain.user.service;

import com.dnd.accompany.domain.auth.dto.AuthUserInfo;
import com.dnd.accompany.domain.auth.oauth.dto.OAuthUserDataResponse;
import com.dnd.accompany.domain.auth.oauth.dto.OAuthUserInfo;
import com.dnd.accompany.domain.user.entity.User;
import com.dnd.accompany.domain.user.infrastructure.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private OAuthUserDataResponse oAuthUserDataResponse;

    private OAuthUserInfo oauthUserInfo;

    @BeforeEach
    void setup() {
        oAuthUserDataResponse = OAuthUserDataResponse.builder()
                .provider("KAKAO")
                .nickname("TESTER1")
                .oauthId("KA-123")
                .profileImageUrl("https://")
                .build();

        oauthUserInfo = OAuthUserInfo.from(oAuthUserDataResponse);
    }

    @Test
    @DisplayName("getOrRegister 호출 시 User 정보가 없다면 유저 데이터를 저장한다.")
    void success() {
        //given
        User newUser = User.of(
                oauthUserInfo.getNickname(),
                oauthUserInfo.getProvider(),
                oauthUserInfo.getOauthId(),
                oauthUserInfo.getProfileImageUrl()
        );

        ReflectionTestUtils.setField(newUser, "id", 1L);

        when(userRepository.findUserByProviderAndOauthId(oauthUserInfo.getProvider(), oauthUserInfo.getOauthId())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(newUser);

        //when
        AuthUserInfo result = userService.getOrRegister(oauthUserInfo);

        //then
        verify(userRepository).save(any(User.class)); // Ensure save was called
        assertEquals(newUser.getId(), result.getUserId());
    }

    @Test
    @DisplayName("getOrRegister 호출 시 User 정보가 있다면 유저 데이터를 저장하지 않는다.")
    void success2() {
        //given
        User existingUser = User.of(
                oauthUserInfo.getNickname(),
                oauthUserInfo.getProvider(),
                oauthUserInfo.getOauthId(),
                oauthUserInfo.getProfileImageUrl()
        );

        ReflectionTestUtils.setField(existingUser, "id", 1L);

        when(userRepository.findUserByProviderAndOauthId(oauthUserInfo.getProvider(), oauthUserInfo.getOauthId()))
                .thenReturn(Optional.of(existingUser));

        //when
        AuthUserInfo result = userService.getOrRegister(oauthUserInfo);

        //then
        verify(userRepository, never()).save(any(User.class));
        assertEquals(existingUser.getId(), result.getUserId());
    }
}