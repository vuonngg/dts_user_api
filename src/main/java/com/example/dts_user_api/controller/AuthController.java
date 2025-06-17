package com.example.dts_user_api.controller;

import com.example.dts_user_api.dto.AuthResponse;
import com.example.dts_user_api.dto.UserLoginDto;
import com.example.dts_user_api.dto.UserSigupDto;
import com.example.dts_user_api.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody UserLoginDto userLoginDto){
        AuthResponse authResponse = authService.login(userLoginDto);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> addUser(@Valid @RequestBody UserSigupDto userSigupDto){
        authService.createUser(userSigupDto);
        return ResponseEntity.ok("Thêm người dùng thành công");
    }
}
