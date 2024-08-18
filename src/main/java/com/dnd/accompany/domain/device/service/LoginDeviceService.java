package com.dnd.accompany.domain.device.service;

import com.dnd.accompany.domain.device.api.dto.RegisterDeviceRequest;
import com.dnd.accompany.domain.device.entity.LoginDevice;
import com.dnd.accompany.domain.device.infrastructure.LoginDeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LoginDeviceService {

    private final LoginDeviceRepository loginDeviceRepository;

    @Transactional
    public String register(Long userId, RegisterDeviceRequest request) {
        loginDeviceRepository.findLoginDeviceByUserId(userId)
                .ifPresentOrElse(
                        loginDevice -> {
                            loginDevice.update(request.deviceToken(), LocalDateTime.now());
                        },
                        () -> {
                            LoginDevice newLoginDevice = LoginDevice.builder()
                                    .userId(userId)
                                    .deviceToken(request.deviceToken())
                                    .loginAt(LocalDateTime.now())
                                    .build();
                            loginDeviceRepository.save(newLoginDevice);
                        }
                );

        return request.deviceToken();
    }
}
