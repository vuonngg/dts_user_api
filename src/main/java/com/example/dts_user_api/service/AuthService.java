package com.example.dts_user_api.service;

import com.example.dts_user_api.entity.User;
import com.example.dts_user_api.entity.UserDtoLogin;
import com.example.dts_user_api.repositorys.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;

    boolean login (UserDtoLogin userDtoLogin){
        User user = userRepository.findByUsername(userDtoLogin.getUsername()).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND,"Sai tài khoản hoặc mật khẩu"));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        return passwordEncoder.matches(userDtoLogin.getPassword(),user.getPassword());
    }

    public String createUser(User user) {
        String username = user.getUsername();
        String email = user.getEmail();

        Optional<User> optionalUsername = userRepository.findByUsername(username);
        Optional<User> optionalEmail = userRepository.findByEmail(email);

        if (optionalUsername.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vui lòng đổi username khác");
        }else if(optionalEmail.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Email đã được sử dụng");
        }

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setIsDeleted(false);
        user.setStatus(true);
        user.setRole(false);
        userRepository.save(user);
        return "Tạo tài khoản thành công";
    }


}
