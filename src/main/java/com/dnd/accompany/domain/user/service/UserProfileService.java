package com.dnd.accompany.domain.user.service;

import com.dnd.accompany.domain.user.dto.CreateUserProfileRequest;
import com.dnd.accompany.domain.user.dto.UpdateUserProfileImageRequest;
import com.dnd.accompany.domain.user.dto.UpdateUserProfileRequest;
import com.dnd.accompany.domain.user.dto.UserProfileDetailResponse;
import com.dnd.accompany.domain.user.dto.UserProfileResponse;
import com.dnd.accompany.domain.user.entity.User;
import com.dnd.accompany.domain.user.entity.UserImage;
import com.dnd.accompany.domain.user.entity.UserProfile;
import com.dnd.accompany.domain.user.exception.UserProfileAlreadyExistsException;
import com.dnd.accompany.domain.user.infrastructure.UserImageRepository;
import com.dnd.accompany.domain.user.infrastructure.UserProfileRepository;
import com.dnd.accompany.domain.user.infrastructure.UserRepository;
import com.dnd.accompany.global.common.exception.NotFoundException;
import com.dnd.accompany.global.common.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final UserImageRepository userImageRepository;
    private final UserRepository userRepository;

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

    @Transactional
    public Long updateProfile(Long userId, UpdateUserProfileRequest request) {
        User user = getUser(userId);

        user.updateUser(request.nickname(), request.profileImageUrl());

        UserProfile userProfile = getUserProfile(userId);

        userProfile.updateUserProfile(
                request.description(),
                request.gender(),
                request.birthYear(),
                request.travelPreferences(),
                request.travelStyles(),
                request.foodPreferences(),
                request.socialMediaLink()
        );

        return user.getId();
    }

    @Transactional
    public Long updateUserProfileImages(Long userId, UpdateUserProfileImageRequest request) {
        getUser(userId);

        userImageRepository.deleteAllByUserId(userId);

        List<UserImage> userImages = request.imageUrls()
                .stream()
                .map(url -> UserImage.of(userId, url))
                .toList();

        userImageRepository.saveAll(userImages);

        return userId;
    }

    @Transactional(readOnly = true)
    public UserProfileDetailResponse findUserProfileDetails(Long userId) {
        User user = getUser(userId);
        UserProfile userProfile = getUserProfile(userId);
        List<String> imageUrls = userImageRepository.findAllByUserId(userId)
                .stream()
                .map(image -> image.getImageUrl())
                .toList();

        return UserProfileDetailResponse.from(user, userProfile, imageUrls);
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    private UserProfile getUserProfile(Long userId) {
        return userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.PROFILE_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public UserProfileResponse findUserProfile(Long userId) {
        User user = getUser(userId);
        UserProfile userProfile = getUserProfile(userId);

        return UserProfileResponse.from(user, userProfile);
    }
}
