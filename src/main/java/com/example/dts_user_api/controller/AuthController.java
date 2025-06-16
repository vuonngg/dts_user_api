package com.example.dts_user_api.controller;

import com.example.dts_user_api.entity.User;
import com.example.dts_user_api.entity.UserDtoLogin;
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

//    @PostMapping("/login")
//    public String login(@Valid @RequestBody UserDtoLogin userDtoLogin){
//
//
//
//    }

    @PostMapping("/signup")
    public ResponseEntity<String> addUser(@Valid @RequestBody User user){
        authService.createUser(user);
        return ResponseEntity.ok("Thêm người dùng thành công");
    }
}
