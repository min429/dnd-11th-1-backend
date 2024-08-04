package com.dnd.accompany.domain.auth.api;

import com.dnd.accompany.domain.auth.dto.AuthUserInfo;
import com.dnd.accompany.domain.auth.dto.Tokens;
import com.dnd.accompany.domain.auth.oauth.dto.LoginRequest;
import com.dnd.accompany.domain.auth.oauth.dto.OAuthUserDataResponse;
import com.dnd.accompany.domain.auth.oauth.dto.OAuthUserInfo;
import com.dnd.accompany.domain.auth.oauth.service.OAuthService;
import com.dnd.accompany.domain.auth.service.TokenService;
import com.dnd.accompany.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final TokenService tokenService;
    private final OAuthService oAuthService;
    private final UserService userService;


    @Operation(summary = "로그인")
    @GetMapping("/sign-in")
    public ResponseEntity<Tokens> signIn(LoginRequest loginRequest) {
        OAuthUserDataResponse oAuthUserData = oAuthService.login(loginRequest);

        OAuthUserInfo oAuthUserInfo = OAuthUserInfo.from(oAuthUserData);

        AuthUserInfo authUserInfo = userService.getOrRegister(oAuthUserInfo);

        Tokens tokens = tokenService.createTokens(authUserInfo);

        return ResponseEntity.ok(tokens);
    }
}
