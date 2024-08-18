package com.dnd.accompany.domain.device.api;

import com.dnd.accompany.domain.auth.dto.jwt.JwtAuthentication;
import com.dnd.accompany.domain.device.api.dto.RegisterDeviceRequest;
import com.dnd.accompany.domain.device.service.LoginDeviceService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/devices")
@RequiredArgsConstructor
public class LoginDeviceController {

    private final LoginDeviceService loginDeviceService;

    @Operation(summary = "FCM 기기 고유 식별자 등록")
    @PostMapping("/token")
    public ResponseEntity<String> register(@AuthenticationPrincipal JwtAuthentication user,
                                           @RequestBody RegisterDeviceRequest request) {

        String token = loginDeviceService.register(user.getId(), request);
        return ResponseEntity.ok(token);
    }
}
