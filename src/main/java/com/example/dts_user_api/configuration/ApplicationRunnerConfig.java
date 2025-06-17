package com.example.dts_user_api.configuration;

import com.example.dts_user_api.entity.User;
import com.example.dts_user_api.repositorys.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationRunnerConfig {
    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository){
        return args -> {
            if(userRepository.findByUsername("admin").isEmpty()){
                User user = new User().builder()
                        .name("admin")
                        .username("admin")
                        .password("admin")
                        .email("admin@gmail.com")
                        .role(true)
                        .status(true)
                        .isDeleted(false)
                        .phone("0338954899")
                        .avatar("âsfadsfdasf")
                        .build();

                PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
                user.setPassword(passwordEncoder.encode(user.getPassword()));

                userRepository.save(user);
            }
        };
    }
}
