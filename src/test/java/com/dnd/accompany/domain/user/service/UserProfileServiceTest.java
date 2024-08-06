package com.dnd.accompany.domain.user.service;

import com.dnd.accompany.domain.user.dto.CreateUserProfileRequest;
import com.dnd.accompany.domain.user.entity.UserProfile;
import com.dnd.accompany.domain.user.entity.enums.FoodPreference;
import com.dnd.accompany.domain.user.entity.enums.Gender;
import com.dnd.accompany.domain.user.entity.enums.TravelPreference;
import com.dnd.accompany.domain.user.entity.enums.TravelStyle;
import com.dnd.accompany.domain.user.exception.UserProfileAlreadyExistsException;
import com.dnd.accompany.domain.user.infrastructure.UserProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.dnd.accompany.domain.user.entity.enums.FoodPreference.*;
import static com.dnd.accompany.domain.user.entity.enums.TravelPreference.*;
import static com.dnd.accompany.domain.user.entity.enums.TravelStyle.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserProfileServiceTest {

    @Mock
    private UserProfileRepository userProfileRepository;

    @InjectMocks
    private UserProfileService userProfileService;

    @DisplayName("유저 프로필을 생성할 때")
    @Nested
    class profile {

        private Long userId = 100L;
        private CreateUserProfileRequest createUserProfileRequest;

        @BeforeEach
        void setup() {
            createUserProfileRequest = new CreateUserProfileRequest(
                    2000,
                    Gender.MALE,
                    List.of(DRAWN_TO, PUBLIC_MONEY, QUICKLY, LEISURELY),
                    List.of(ACTIVITY, HEALING, CAFE_TOUR, SHOPPING),
                    List.of(MEAT, RICE, COFFEE, FAST_FOOD)
            );
        }

        @DisplayName("신규 생성인 경우 정상 생성된다.")
        @Test
        void success() {
            //given
            given(userProfileRepository.existsById(anyLong()))
                    .willReturn(false);

            //when
            userProfileService.createUserProfile(userId, createUserProfileRequest);

            //then
            verify(userProfileRepository).save(any(UserProfile.class));
        }

        @DisplayName("이미 프로필이 존재하는 경우 예외가 발생한다.")
        @Test
        void fail() {
            //given
            given(userProfileRepository.existsById(anyLong()))
                    .willReturn(true);

            //when & then
            assertThrows(UserProfileAlreadyExistsException.class,
                    () -> userProfileService.createUserProfile(userId, createUserProfileRequest));
        }
    }
}