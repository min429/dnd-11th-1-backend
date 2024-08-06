package com.dnd.accompany.domain.user.service;

import com.dnd.accompany.domain.user.dto.CreateUserProfileRequest;
import com.dnd.accompany.domain.user.entity.UserProfile;
import com.dnd.accompany.domain.user.exception.UserProfileAlreadyExistsException;
import com.dnd.accompany.domain.user.infrastructure.UserProfileRepository;
import com.dnd.accompany.global.common.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;

    @Transactional
    public void createUserProfile(Long userId, CreateUserProfileRequest createUserProfileRequest) {
        validateDuplicateProfile(userId);

        UserProfile userProfile = UserProfile.builder()
                .userId(userId)
                .birthYear(createUserProfileRequest.birthYear())
                .gender(createUserProfileRequest.gender())
                .travelPreferences(createUserProfileRequest.travelPreferences())
                .travelStyles(createUserProfileRequest.travelStyles())
                .foodPreferences(createUserProfileRequest.foodPreferences())
                .build();

        userProfileRepository.save(userProfile);
    }

    @Transactional(readOnly = true)
    public boolean existByUserId(Long userId) {
        return userProfileRepository.existsById(userId);
    }

    private void validateDuplicateProfile(Long userId) {
        if (userProfileRepository.existsById(userId)) {
            throw new UserProfileAlreadyExistsException(ErrorCode.PROFILE_ALREADY_EXISTS);
        }
    }
}
